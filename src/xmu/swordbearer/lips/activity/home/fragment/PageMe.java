package xmu.swordbearer.lips.activity.home.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.activity.relationship.RelationShipActivity;
import xmu.swordbearer.lips.activity.signin.LoginActivity;
import xmu.swordbearer.lips.activity.user.UserActivity;
import xmu.swordbearer.lips.api.NetHelper;
import xmu.swordbearer.lips.api.OnRequestListener;
import xmu.swordbearer.lips.api.UserAPI;
import xmu.swordbearer.lips.bean.BaseNews;
import xmu.swordbearer.lips.bean.BriefAccountInfo;
import xmu.swordbearer.lips.ui.UiHelper;

import java.util.ArrayList;
import java.util.List;

public class PageMe extends Fragment implements View.OnClickListener {
    protected static final String TAG = "PageMe";
    private View accoutInfo;
    private Button btnLogin, btnLines, btnFollowers, btnFollowing;
    private TextView tvName, tvDesc;
    private ListView lvNews;
    private List<BaseNews> news;
    private BriefAccountInfo me = null;
    //
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == NetHelper.STATUS_GETACCOUNT_SUCCESS) {
                updateInfoViews();
            } else if (msg.what == NetHelper.STATUS_GETNEWS_SUCCESS) {
            } else if (msg.what == NetHelper.STATUS_GETNEWS_ERROR) {
            } else if (msg.what == NetHelper.STATUS_GETACCOUNT_ERROR) {
                UiHelper.showError(PageMe.this.getActivity(), "获取账号信息失败");
                me = null;
                updateInfoViews();
            }
        }
    };
    private OnRequestListener getAccountListener = new OnRequestListener() {
        @Override
        public void onError(String msg) {
            handler.sendEmptyMessage(NetHelper.STATUS_GETACCOUNT_ERROR);
        }

        @Override
        public void onComplete(Object object) {
            JSONObject json = (JSONObject) object;
            Log.e(TAG, "得到me" + json);
            try {
                me = BriefAccountInfo.fromJSON(json);
                UserAPI.saveCacheAccount(getActivity(), me);
                Log.e(TAG, "得到me" + me.toString());
                handler.sendEmptyMessage(NetHelper.STATUS_GETACCOUNT_SUCCESS);
            } catch (JSONException e) {
                onError("");
            }
        }
    };
    private OnRequestListener getNewsListener = new OnRequestListener() {
        @Override
        public void onError(String message) {
            handler.sendEmptyMessage(NetHelper.STATUS_GETNEWS_ERROR);
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
                this.onError("");
                e.printStackTrace();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_page_me, container, false);
        initViews(rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void initViews(View rootView) {
        btnLogin = (Button) rootView.findViewById(R.id.me_btn_login);
        btnLines = (Button) rootView.findViewById(R.id.me_btn_lines);
        btnFollowers = (Button) rootView.findViewById(R.id.me_btn_followers);
        btnFollowing = (Button) rootView.findViewById(R.id.me_btn_following);
        accoutInfo = rootView.findViewById(R.id.me_account_container);
        tvName = (TextView) rootView.findViewById(R.id.me_name);
        tvDesc = (TextView) rootView.findViewById(R.id.me_desc);
        lvNews = (ListView) rootView.findViewById(R.id.lv_news);

        btnLogin.setOnClickListener(this);
        btnLines.setOnClickListener(this);
        btnFollowers.setOnClickListener(this);
        btnFollowing.setOnClickListener(this);
        accoutInfo.setOnClickListener(this);

		/* 如果存在Token，则使用Token去获取账户信息和最新消息 */
        me = UserAPI.readCachedAccount(this.getActivity());
        updateInfoViews();
        if (UserAPI.isLogin(this.getActivity())) {
            getMyInfo();
            getMyNews();
        } else {
            btnLogin.setVisibility(View.VISIBLE);
            accoutInfo.setVisibility(View.GONE);
        }
    }

    private void updateInfoViews() {
        if (me == null) {
            return;
        }
        btnLogin.setVisibility(View.GONE);
        accoutInfo.setVisibility(View.VISIBLE);
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
        new Thread(new Runnable() {
            public void run() {
                UserAPI.briefInfo(PageMe.this.getActivity(), getAccountListener);
            }
        }).start();
    }

    /** 获取最新动态 */
    private void getMyNews() {
        new Thread(new Runnable() {
            public void run() {
                UserAPI.news(PageMe.this.getActivity(), getNewsListener);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            getActivity().startActivity(new Intent(this.getActivity(), LoginActivity.class));
        } else if (v == accoutInfo) {
            getActivity().startActivity(new Intent(this.getActivity(), UserActivity.class));
        } else if (v == btnFollowers) {
            getActivity().startActivity(new Intent(this.getActivity(), UserActivity.class));
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
