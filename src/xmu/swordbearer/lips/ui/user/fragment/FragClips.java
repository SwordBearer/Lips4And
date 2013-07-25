package xmu.swordbearer.lips.ui.user.fragment;

import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.api.UserAPI;
import xmu.swordbearer.lips.ui.BaseFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

/** Created by SwordBearer on 13-6-22. */
public class FragClips extends BaseFragment {
	private Button btnAddClips;
	private ListView lvClips;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_user_clips, container, false);
		return rootView;
	}

	@Override
	public void initViews(View rootView) {
		btnAddClips = (Button) rootView.findViewById(R.id.page_clips_add);
		lvClips = (ListView) rootView.findViewById(R.id.page_clips_lv);
		// 如果是当前用户,jiu，就显示添加按钮
		if (UserAPI.isLogin(getActivity())) {
		}
	}

	@Override
	public void onClick(View v) {
		if (v == btnAddClips) {
		} else if (v == lvClips) {
		}
	}
}
