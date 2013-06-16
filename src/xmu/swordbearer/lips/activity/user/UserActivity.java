package xmu.swordbearer.lips.activity.user;

import xmu.swordbearer.lips.ui.tab.TationBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

public class UserActivity extends FragmentActivity {
	protected static final String TAG = "UserActivity";
	private ViewPager mViewPager;
	private UserPageAdapter mAdapter;
	private TationBar mTationBar;

	private class UserPageAdapter extends FragmentPagerAdapter {
		public UserPageAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return null;
		}

		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {}

	}
}
