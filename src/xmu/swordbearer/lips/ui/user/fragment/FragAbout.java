package xmu.swordbearer.lips.ui.user.fragment;

import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.ui.BaseFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/** Created by SwordBearer on 13-6-22. */
public class FragAbout extends BaseFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_user_about, null, false);
		initViews(rootView);
		return rootView;
	}

	public void initViews(View rootView) {}

	@Override
	public void onClick(View v) {}
}
