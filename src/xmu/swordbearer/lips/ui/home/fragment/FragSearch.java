package xmu.swordbearer.lips.ui.home.fragment;

import xmu.swordbearer.lips.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class FragSearch extends Fragment {
	private static final String TAG = "PageSearch";

	private EditText mEdSearch;
	private ImageButton mBtnSearch;

	private ListView lv;

	// private List<xmu.swordbearer.lips.bean.Category> categories;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.e(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.frag_home_search, container, false);
		initViews(rootView);
		return rootView;
	}

	public void initViews(View rootView) {
		mEdSearch = (EditText) rootView.findViewById(R.id.frag_search_edit_search);
		mBtnSearch = (ImageButton) rootView.findViewById(R.id.frag_search_btn_search);
		lv = (ListView) rootView.findViewById(R.id.frag_search_listview);

		// categories = new ArrayList<Category>();
		//
		// getCategories();
	}

	// private Handler handler = new Handler() {
	//
	// @Override
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case NetHelper.STATUS_GETCATEGORY_ERROR:
	// UiHelper.showError(getActivity(), "获取分类错误");
	// break;
	// case NetHelper.STATUS_GETCATEGORY_SUCCESS:
	// break;
	// default:
	// super.handleMessage(msg);
	// }
	// }
	// };
	// private OnRequestListener getCateListener = new OnRequestListener() {
	// @Override
	// public void onError(int statusCode) {
	// handler.sendEmptyMessage(statusCode);
	// }
	//
	// @Override
	// public void onComplete(Object object) {
	// categories.clear();
	// categories.addAll((List<Category>) object);
	// Log.e(TAG, "刚刚得到的Category条数有 " + categories.size());
	// handler.sendEmptyMessage(NetHelper.STATUS_GETCATEGORY_SUCCESS);
	// }
	// };

	// private void getCategories() {
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// LineAPI.getCategories(m, getCateListener);
	// }
	// }).start();
	// }
}
