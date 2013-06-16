package xmu.swordbearer.lips.activity.home;

import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.activity.home.fragment.PageMe;
import xmu.swordbearer.lips.activity.home.fragment.PageSearch;
import xmu.swordbearer.lips.activity.home.fragment.PageStart;
import xmu.swordbearer.lips.ui.tab.TationBar;
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
	private TationBar mTationBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mTationBar = (TationBar) findViewById(R.id.tactionbar);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new StartPageAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
		mTationBar.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int index = mTationBar.getCheckedIndex();
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
				mTationBar.setCurrentTab(position);
			}
		});
		mViewPager.setCurrentItem(1);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			int currentPage = mViewPager.getCurrentItem();
			Log.e(TAG, "currentPage " + currentPage);
			if (currentPage != 1) {
				mViewPager.setCurrentItem(1);
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
				return new PageSearch();
			case 1:
				return new PageStart();
			case 2:
				return new PageMe();
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
