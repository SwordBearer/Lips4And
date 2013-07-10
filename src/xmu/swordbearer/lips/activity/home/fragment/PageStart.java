package xmu.swordbearer.lips.activity.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.activity.line.AddLineActivity;
import xmu.swordbearer.lips.adapter.LineAdapter;
import xmu.swordbearer.lips.api.LineAPI;
import xmu.swordbearer.lips.api.NetHelper;
import xmu.swordbearer.lips.api.OnRequestListener;
import xmu.swordbearer.lips.bean.LinesList;


public class PageStart extends Fragment implements View.OnClickListener {
    private static final int MSG_GET_LINE_MORE = 0x01;
    private static final int MSG_GET_LINE_REFRESH = 0x02;
    private static final int MSG_GET_LINE_FAILED = 0x03;
    LineAdapter adapter = null;
    private String TAG = "PageStart";
    private View addView;
    private ListView lvLines;
    private LinesList linesList = new LinesList();
    //
    private OnRequestListener listenerMore = new OnRequestListener() {
        @Override
        public void onError(String msg) {
            handler.sendEmptyMessage(MSG_GET_LINE_FAILED);
        }

        @Override
        public void onComplete(Object object) {
            JSONArray ja = (JSONArray) object;
            linesList.append(ja);
            handler.sendEmptyMessage(MSG_GET_LINE_MORE);
        }
    };
    //
    private OnRequestListener listenerRefresh = new OnRequestListener() {
        @Override
        public void onError(String msg) {
            handler.sendEmptyMessage(MSG_GET_LINE_FAILED);
        }

        @Override
        public void onComplete(Object object) {
            JSONArray ja = (JSONArray) object;
            int newCount = linesList.preappend(ja);
            Message msg = handler.obtainMessage();
            msg.what = MSG_GET_LINE_REFRESH;
            msg.arg1 = newCount;
            handler.sendMessage(msg);
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GET_LINE_MORE:
                    updateListView();
                    break;
                case MSG_GET_LINE_REFRESH:
                    updateListView();
                    showRefreshCount(msg.arg1);
                    break;
                case MSG_GET_LINE_FAILED:
                    Toast.makeText(getActivity(), "获取数据错误!", Toast.LENGTH_LONG).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private void updateListView() {
        adapter.notifyDataSetChanged();
    }

    /** 显示最新数据条数 */
    private void showRefreshCount(int count) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_page_start, container, false);
        initViews(rootView);
        return rootView;
    }

    public void initViews(View rootView) {
        addView = rootView.findViewById(R.id.frag_start_create);
        lvLines = (ListView) rootView.findViewById(R.id.frag_start_lv);
        addView.setOnClickListener(this);
        lvLines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        adapter = new LineAdapter(getActivity(), linesList.getLines());
        lvLines.setAdapter(adapter);

        getLines(2);
    }

    /** 加载更多 */
    private void getLines(int type) {
        if (NetHelper.isNetworkConnected(getActivity())) {
            if (type == 1) {//refresh
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LineAPI.getFriendsLines(getActivity(), 0, 0, listenerRefresh);
                    }
                }).start();
            } else if (type == 2) {//more
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LineAPI.getFriendsLines(getActivity(), 0, 0, listenerMore);
                    }
                }).start();
            }
        } else {
            Toast.makeText(getActivity(), "未连接到网络，无法获取最更多微博...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == addView) {
            startActivity(new Intent(this.getActivity(), AddLineActivity.class));
        }
    }
}
