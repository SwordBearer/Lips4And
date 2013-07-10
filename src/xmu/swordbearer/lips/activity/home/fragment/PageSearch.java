package xmu.swordbearer.lips.activity.home.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.api.LineAPI;
import xmu.swordbearer.lips.api.OnRequestListener;
import xmu.swordbearer.lips.bean.Category;
import xmu.swordbearer.lips.ui.UiHelper;

import java.util.ArrayList;
import java.util.List;

public class PageSearch extends Fragment {
	private static final String TAG = "PageSearch";

	private EditText mEdSearch;
	private ImageButton mBtnSearch;

	private ListView lv;
	private List<xmu.swordbearer.lips.bean.Category> categories;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.e(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.frag_page_search, container, false);
		initViews(rootView);
		return rootView;
	}

	public void initViews(View rootView) {
		mEdSearch = (EditText) rootView.findViewById(R.id.frag_search_edit_search);
		mBtnSearch = (ImageButton) rootView.findViewById(R.id.frag_search_btn_search);
		lv = (ListView) rootView.findViewById(R.id.frag_search_listview);



		categories = new ArrayList<Category>();
		//
		getCategories();
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				UiHelper.showError(getActivity(), "获取分类错误");
				break;
			case 1:
				break;
			default:
				super.handleMessage(msg);
			}
		}
	};
	private OnRequestListener getCateListener = new OnRequestListener() {
		@Override
		public void onError(String response) {
			handler.sendEmptyMessage(0);
		}

		@Override
		public void onComplete(Object object) {
			categories.clear();
			categories.addAll((List<Category>) object);
			Log.e(TAG, "刚刚得到的Category条数有 " + categories.size());
			handler.sendEmptyMessage(1);
		}
	};

	private void getCategories() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				LineAPI.getCategories(getActivity(), getCateListener);
			}
		}).start();
	}
}
