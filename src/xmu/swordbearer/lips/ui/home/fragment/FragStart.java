package xmu.swordbearer.lips.ui.home.fragment;

import org.json.JSONArray;

import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.adapter.LineAdapter;
import xmu.swordbearer.lips.api.LineAPI;
import xmu.swordbearer.lips.api.NetHelper;
import xmu.swordbearer.lips.api.OnRequestListener;
import xmu.swordbearer.lips.application.LipsApplication;
import xmu.swordbearer.lips.bean.Authen;
import xmu.swordbearer.lips.bean.LinesList;
import xmu.swordbearer.lips.ui.BaseFragment;
import xmu.swordbearer.lips.ui.UiHelper;
import xmu.swordbearer.lips.ui.line.AddLineActivity;
import xmu.swordbearer.lips.ui.widget.LiveListView;
import xmu.swordbearer.lips.ui.widget.LiveListView.OnMoreListener;
import xmu.swordbearer.lips.ui.widget.LiveListView.OnRefreshListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * 下拉刷新,点击加载更多控件
 * 
 * @author SwordBearer
 */
public class FragStart extends BaseFragment implements View.OnClickListener {

	private static final int MSG_GET_LINE_REFRESH = 0x01;
	private static final int MSG_GET_LINE_MORE = 0x02;
	private static final int MSG_GET_LINE_FAILED = 0x03;
	LineAdapter adapter = null;
	private View addView;
	private LiveListView lvLines;
	private LinesList linesList = new LinesList();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_home_start, container, false);
		initViews(rootView);
		return rootView;
	}

	public void initViews(View rootView) {
		addView = rootView.findViewById(R.id.frag_start_create);
		lvLines = (LiveListView) rootView.findViewById(R.id.frag_start_lv);
		lvLines.isShowHeader(true);
		lvLines.isShowFooter(true);
		addView.setOnClickListener(this);
		lvLines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {}
		});
		lvLines.setOnRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				getLines(1);
			}
		});
		lvLines.setOnMoreListener(new OnMoreListener() {
			public void onMore() {
				getLines(2);
			}
		});

		adapter = new LineAdapter(getActivity(), linesList.getLines());
		lvLines.setAdapter(adapter);
		getLines(1);
	}

	//
	private OnRequestListener listenerMore = new OnRequestListener() {
		@Override
		public void onError(int statusCode) {
			handler.sendEmptyMessage(statusCode);
		}

		@Override
		public void onComplete(Object object) {
			JSONArray ja = (JSONArray) object;
			linesList.append(ja);
			handler.sendEmptyMessage(MSG_GET_LINE_MORE);
		}
	};
	//
	private OnRequestListener listenerRefresh = new OnRequestListener() {
		@Override
		public void onError(int statusCode) {
			handler.sendEmptyMessage(statusCode);
		}

		@Override
		public void onComplete(Object object) {
			JSONArray ja = (JSONArray) object;
			int newCount = linesList.preappend(ja);
			Message msg = handler.obtainMessage();
			msg.what = MSG_GET_LINE_REFRESH;
			msg.arg1 = newCount;
			handler.sendMessage(msg);
		}
	};
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_GET_LINE_MORE:
				updateListView();
				lvLines.onMoreComplete();
				break;
			case MSG_GET_LINE_REFRESH:
				updateListView();
				showRefreshCount(msg.arg1);
				lvLines.onRefreshComplete();
				break;
			case MSG_GET_LINE_FAILED:
				lvLines.onMoreComplete();
				lvLines.onRefreshComplete();
				Toast.makeText(getActivity(), "获取数据错误!", Toast.LENGTH_LONG).show();
				break;
			}
		}
	};

	private void updateListView() {
		adapter.notifyDataSetChanged();
	}

	/** 显示最新数据条数 */
	private void showRefreshCount(int count) {}

	/**
	 * 加载Lines
	 * 
	 * @param type 加载类型(1:more 2:refresh)
	 */
	private void getLines(final int type) {
		if (!NetHelper.isNetworkConnected(mContext)) {
			UiHelper.showError(mContext, R.string.network_not_avalible);
			return;
		}
		final Authen authen = LipsApplication.getAuthen(mContext);
		if (authen == null) {
			UiHelper.showError(mContext, R.string.not_login);
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (type == 1)
					// 刷新：获取ID比 firstId大的
					LineAPI.getFriendsLines(mContext, authen.getUid(), authen.getToken(), linesList.getFirstId(), linesList.getLastId(), 1,
							listenerRefresh);
				else
					// 更多：获取ID比lastId小的
					LineAPI.getFriendsLines(mContext, authen.getUid(), authen.getToken(), -1, linesList.getLastId(), 2, listenerMore);
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		if (v == addView) {
			startActivity(new Intent(this.getActivity(), AddLineActivity.class));
		}
	}
}
