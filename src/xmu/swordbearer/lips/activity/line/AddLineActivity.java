package xmu.swordbearer.lips.activity.line;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.api.LineAPI;
import xmu.swordbearer.lips.api.NetHelper;
import xmu.swordbearer.lips.api.OnRequestListener;
import xmu.swordbearer.lips.bean.Clip;

import java.util.ArrayList;
import java.util.List;

public class AddLineActivity extends Activity implements View.OnClickListener {

    String TAG = "AddLineActivity";
    private ImageButton btnBack, btnSend;
    private Spinner spinner;
    private EditText editText;
    private ProgressDialog progressDialog;
    private ImageView ivLoadingClips;
    private Animation animLoading;
    //文集
    private List<Clip> clipList = new ArrayList<Clip>();
    private long clipid = -1;
    private String content;
    private ClipAdapter clipAdapter;
    //
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NetHelper.STATUS_GETCLIPS_ERROR:
                    ivLoadingClips.setVisibility(View.GONE);
                    ivLoadingClips.clearAnimation();
                    spinner.setVisibility(View.GONE);
                    //重置clipid
                    clipid = -1;
                    break;
                case NetHelper.STATUS_GETCLIPS_SUCCESS:
                    spinner.setVisibility(View.VISIBLE);
                    clipAdapter.notifyDataSetChanged();
                    ivLoadingClips.clearAnimation();
                    ivLoadingClips.setVisibility(View.GONE);
                    //当clips更新后，默认使clipid为第一条clip的ID
                    clipid = clipList.get(0).getId();
                    break;
                case NetHelper.STATUS_ADDLINE_EEROR:
                    Toast.makeText(AddLineActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                    break;
                case NetHelper.STATUS_ADDLINE_SUCCESS:
                    Toast.makeText(AddLineActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };
    private OnRequestListener sendLineListener = new OnRequestListener() {
        @Override
        public void onError(String msg) {
            handler.sendEmptyMessage(NetHelper.STATUS_ADDLINE_EEROR);
            progressDialog.dismiss();
        }

        @Override
        public void onComplete(Object object) {
            handler.sendEmptyMessage(NetHelper.STATUS_ADDLINE_SUCCESS);
            progressDialog.dismiss();
        }
    };
    /** 负责从网络获取Clip集合 */
    private OnRequestListener getClipListener = new OnRequestListener() {
        @Override
        public void onError(String msg) {
            Log.e(TAG, "onError " + msg);
            handler.sendEmptyMessage(NetHelper.STATUS_GETCLIPS_ERROR);
        }

        @Override
        public void onComplete(Object object) {
            clipList.clear();
            clipList.addAll((List<Clip>) object);
            handler.sendEmptyMessage(NetHelper.STATUS_GETCLIPS_SUCCESS);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_line);
//
        btnBack = (ImageButton) findViewById(R.id.addline_btn_back);
        btnSend = (ImageButton) findViewById(R.id.addline_btn_send);
        spinner = (Spinner) findViewById(R.id.addline_spinner_category);
        editText = (EditText) findViewById(R.id.addline_content);
        ivLoadingClips = (ImageView) findViewById(R.id.addline_loading);
//
        clipAdapter = new ClipAdapter(this);
        spinner.setAdapter(clipAdapter);
        btnBack.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clipid = clipList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                clipid = -1;
            }
        });

        animLoading = AnimationUtils.loadAnimation(this, R.anim.loading);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.progress_send_line);
        progressDialog.setCancelable(true);
        getClips();
    }

    @Override
    public void onClick(View v) {
        if (v == btnBack) {
            finish();
        } else if (v == btnSend) {
            sendLine();
        }
    }

    private void getClips() {
        ivLoadingClips.setVisibility(View.VISIBLE);
        ivLoadingClips.setAnimation(animLoading);
        spinner.setVisibility(View.GONE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                LineAPI.getClips(AddLineActivity.this, getClipListener);
            }
        }).start();
    }

    private void sendLine() {
        if (clipid == -1) {
            getClips();
            return;
        }
        content = editText.getText().toString().trim();
        if (content.length() > 300 || content.length() == 0) {
            Toast.makeText(this, "发布失败:文字内容不能超过300字", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LineAPI.addLine(AddLineActivity.this, clipid, content, sendLineListener);
            }
        }).start();
    }

    private class ClipAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public ClipAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return clipList.size();
        }

        @Override
        public Object getItem(int position) {
            return clipList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) inflater.inflate(R.layout.listitem_clip, null, false);
            Clip clip = clipList.get(position);
            textView.setText(clip.getName());
            return textView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
