package xmu.swordbearer.lips.ui.user;

import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.ui.tactionbar.TactionBar;
import xmu.swordbearer.lips.ui.user.fragment.FragAbout;
import xmu.swordbearer.lips.ui.user.fragment.FragClips;
import xmu.swordbearer.lips.ui.user.fragment.FragLikes;
import xmu.swordbearer.lips.ui.user.fragment.FragLines;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.RadioGroup;

/** Created by SwordBearer on 13-6-22. */
public class UserActivity extends FragmentActivity {
	protected static final String TAG = "UserActivity";
	private ViewPager mViewPager;
	private UserPageAdapter mAdapter;
	private TactionBar mTactionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_user);
		mViewPager = (ViewPager) findViewById(R.id.user_pager);
		mTactionBar = (TactionBar) findViewById(R.id.user_tactionbar);
		mAdapter = new UserPageAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
		mTactionBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int index = mTactionBar.getCheckedIndex();
				if (mViewPager.getCurrentItem() != index)
					mViewPager.setCurrentItem(index);
			}
		});
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				mTactionBar.setCurrentTab(position);
			}
		});
		mTactionBar.setCurrentTab(0);
	}

	private class UserPageAdapter extends FragmentPagerAdapter {
		public UserPageAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {
			switch (index) {
			case 0:
				return new FragClips();
			case 1:
				return new FragLines();
			case 2:
				return new FragLikes();
			case 3:
				return new FragAbout();
			}
			return null;
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {}

	}
}
