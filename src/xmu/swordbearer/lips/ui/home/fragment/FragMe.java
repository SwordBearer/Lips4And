package xmu.swordbearer.lips.ui.home.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.api.NetHelper;
import xmu.swordbearer.lips.api.OnRequestListener;
import xmu.swordbearer.lips.api.UserAPI;
import xmu.swordbearer.lips.application.LipsApplication;
import xmu.swordbearer.lips.bean.Authen;
import xmu.swordbearer.lips.bean.BaseNews;
import xmu.swordbearer.lips.bean.BriefAccountInfo;
import xmu.swordbearer.lips.ui.BaseFragment;
import xmu.swordbearer.lips.ui.UiHelper;
import xmu.swordbearer.lips.ui.relationship.RelationShipActivity;
import xmu.swordbearer.lips.ui.signin.LoginActivity;
import xmu.swordbearer.lips.ui.user.UserActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FragMe extends BaseFragment implements View.OnClickListener {
	protected static final String TAG = "PageMe";
	private View accoutContainer, accoutInfo;
	private Button btnLogin, btnLines, btnFollowers, btnFollowing;
	private TextView tvName, tvDesc;
	private ListView lvNews;
	private List<BaseNews> news;
	private BriefAccountInfo me = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_home_me, container, false);
		initViews(rootView);
		return rootView;
	}

	public void initViews(View rootView) {
		btnLogin = (Button) rootView.findViewById(R.id.me_btn_login);
		btnLines = (Button) rootView.findViewById(R.id.me_btn_lines);
		btnFollowers = (Button) rootView.findViewById(R.id.me_btn_followers);
		btnFollowing = (Button) rootView.findViewById(R.id.me_btn_following);
		accoutContainer = rootView.findViewById(R.id.me_account_container);
		accoutInfo = rootView.findViewById(R.id.me_account_info);
		tvName = (TextView) rootView.findViewById(R.id.me_name);
		tvDesc = (TextView) rootView.findViewById(R.id.me_desc);
		lvNews = (ListView) rootView.findViewById(R.id.lv_news);

		btnLogin.setOnClickListener(this);
		btnLines.setOnClickListener(this);
		btnFollowers.setOnClickListener(this);
		btnFollowing.setOnClickListener(this);
		accoutInfo.setOnClickListener(this);

		/* 如果存在Token，则使用Token去获取账户信息和最新消息 */
		me = LipsApplication.readCachedAccount(this.getActivity());
		updateAccountViews();
		if (LipsApplication.isLogin(this.getActivity())) {
			getMyInfo();
			getMyNews();
		} else {
			btnLogin.setVisibility(View.VISIBLE);
			accoutContainer.setVisibility(View.GONE);
		}
	}

	//
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == NetHelper.STATUS_GETACCOUNT_SUCCESS) {
				updateAccountViews();
			} else if (msg.what == NetHelper.STATUS_GETNEWS_SUCCESS) {
			} else if (msg.what == NetHelper.STATUS_GETNEWS_ERROR) {
			} else if (msg.what == NetHelper.STATUS_GETACCOUNT_ERROR) {
				UiHelper.showError(mContext, R.string.get_account_error);
				me = null;
				updateAccountViews();
			}
		}
	};
	private OnRequestListener getAccountListener = new OnRequestListener() {
		@Override
		public void onError(int statusCode) {
			handler.sendEmptyMessage(statusCode);
		}

		@Override
		public void onComplete(Object object) {
			JSONObject json = (JSONObject) object;
			Log.e(TAG, "得到me" + json);
			try {
				me = BriefAccountInfo.fromJSON(json);
				LipsApplication.saveCacheAccount(getActivity(), me);
				Log.e(TAG, "得到me" + me.toString());
				handler.sendEmptyMessage(NetHelper.STATUS_GETACCOUNT_SUCCESS);
			} catch (JSONException e) {
				onError(-1);
			}
		}
	};
	private OnRequestListener getNewsListener = new OnRequestListener() {
		@Override
		public void onError(int statusCode) {
			handler.sendEmptyMessage(statusCode);
		}

		@Override
		public void onComplete(Object object) {
			try {
				JSONArray jsonArray = (JSONArray) object;
				news = new ArrayList<BaseNews>();
				JSONArray objFans = jsonArray.getJSONArray(0);
				JSONArray objLikes = jsonArray.getJSONArray(1);
				JSONArray objSubs = jsonArray.getJSONArray(2);
				// merge the message
				handler.sendEmptyMessage(NetHelper.STATUS_GETNEWS_SUCCESS);
			} catch (JSONException e) {
				this.onError(-1);
			}
		}
	};

	private void updateAccountViews() {
		if (me == null) {
			btnLogin.setVisibility(View.VISIBLE);
			accoutContainer.setVisibility(View.GONE);
			return;
		}
		btnLogin.setVisibility(View.GONE);
		accoutContainer.setVisibility(View.VISIBLE);
		tvName.setText(me.getName());
		if (me.getDesc() == null) {
			tvDesc.setText(R.string.no_desc);
		} else {
			tvDesc.setText(me.getDesc());
		}
		btnLines.setText(getString(R.string.lines) + "  " + me.getLines());
		btnFollowers.setText(getString(R.string.followers) + "  " + me.getFollowers());
		btnFollowing.setText(getString(R.string.following) + "  " + me.getFollowing());
	}

	private void updateNewsListView() {
		if (news != null) {
			NewsAdapter adapter = new NewsAdapter(news);
			lvNews.setAdapter(adapter);
		} else {
			lvNews.setVisibility(View.GONE);
		}
	}

	private void getMyInfo() {
		final Authen authen = LipsApplication.getAuthen(getActivity());
		if (authen == null) {
			UiHelper.showError(mContext, R.string.not_login);
			return;
		}
		new Thread(new Runnable() {
			public void run() {

				UserAPI.briefInfo(mContext, authen.getUid(), authen.getToken(), getAccountListener);
			}
		}).start();
	}

	/** 获取最新动态 */
	private void getMyNews() {
		final Authen authen = LipsApplication.getAuthen(getActivity());
		if (authen == null) {
			UiHelper.showError(mContext, R.string.not_login);
			return;
		}
		new Thread(new Runnable() {
			public void run() {

				UserAPI.news(mContext, authen.getUid(), authen.getToken(), getNewsListener);
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		if (v == btnLogin) {
			getActivity().startActivity(new Intent(this.getActivity(), LoginActivity.class));
			getActivity().finish();
		} else if (v == accoutInfo) {
			getActivity().startActivity(new Intent(this.getActivity(), UserActivity.class));
		} else if (v == btnFollowers) {
			getActivity().startActivity(new Intent(this.getActivity(), RelationShipActivity.class));
		} else if (v == btnFollowing) {
			getActivity().startActivity(new Intent(this.getActivity(), RelationShipActivity.class));
		}
	}

	/** 如果登录成功就通知获取账号信息 */
	private class AccountBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			getMyInfo();
			getMyNews();
		}
	}

	private class NewsAdapter extends BaseAdapter {
		private List<BaseNews> news;

		public NewsAdapter(List<BaseNews> news) {
			this.news = news;
		}

		@Override
		public int getCount() {
			return news.size();
		}

		@Override
		public Object getItem(int position) {
			return news.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}

	}
}
