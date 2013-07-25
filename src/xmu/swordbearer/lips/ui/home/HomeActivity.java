package xmu.swordbearer.lips.ui.home;

import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.ui.home.fragment.FragMe;
import xmu.swordbearer.lips.ui.home.fragment.FragSearch;
import xmu.swordbearer.lips.ui.home.fragment.FragStart;
import xmu.swordbearer.lips.ui.tactionbar.TactionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class HomeActivity extends FragmentActivity {
	protected static final String TAG = "HomeActivity";
	private ViewPager mViewPager;
	private StartPageAdapter mAdapter;
	private TactionBar mTactionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mTactionBar = (TactionBar) findViewById(R.id.home_tactionbar);
		mViewPager = (ViewPager) findViewById(R.id.home_pager);
		mAdapter = new StartPageAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
		mTactionBar.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int index = mTactionBar.getCheckedIndex();
				Log.e(TAG, "onCheckedChanged index " + index);
				if (mViewPager.getCurrentItem() != index)
					mViewPager.setCurrentItem(index);
			}
		});
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				Log.e(TAG, "pager position" + position);
				super.onPageSelected(position);
				mTactionBar.setCurrentTab(position);
			}
		});
		mTactionBar.setCurrentTab(0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			int currentPage = mViewPager.getCurrentItem();
			Log.e(TAG, "currentPage " + currentPage);
			if (currentPage != 0) {
				mViewPager.setCurrentItem(0);
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private class StartPageAdapter extends FragmentPagerAdapter {
		public StartPageAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {
			switch (index) {
			case 0:
				return new FragStart();
			case 1:
				return new FragSearch();
			case 2:
				return new FragMe();
			}
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {}
	}
}
