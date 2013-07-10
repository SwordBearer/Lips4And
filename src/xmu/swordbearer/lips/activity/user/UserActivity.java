package xmu.swordbearer.lips.activity.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.activity.user.fragment.PageAbout;
import xmu.swordbearer.lips.activity.user.fragment.PageClips;
import xmu.swordbearer.lips.activity.user.fragment.PageLikes;
import xmu.swordbearer.lips.activity.user.fragment.PageLines;
import xmu.swordbearer.lips.ui.tactionbar.TationBar;

/** Created by SwordBearer on 13-6-22. */
public class UserActivity extends FragmentActivity {
    protected static final String TAG = "UserActivity";
    private ViewPager mViewPager;
    private UserPageAdapter mAdapter;
    private TationBar mTationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_user);
        mViewPager = (ViewPager) findViewById(R.id.user_pager);
        mTationBar = (TationBar) findViewById(R.id.user_tactionbar);
        mAdapter = new UserPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTationBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int index = mTationBar.getCheckedIndex();
                if (mViewPager.getCurrentItem() != index) mViewPager.setCurrentItem(index);
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTationBar.setCurrentTab(position);
            }
        });
        mTationBar.setCurrentTab(0);
    }

    private class UserPageAdapter extends FragmentPagerAdapter {
        public UserPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            switch (index) {
                case 0:
                    return new PageClips();
                case 1:
                    return new PageLines();
                case 2:
                    return new PageLikes();
                case 3:
                    return new PageAbout();
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
