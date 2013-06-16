package xmu.swordbearer.lips.activity.line;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.api.LineAPI;
import xmu.swordbearer.lips.api.NetHelper;
import xmu.swordbearer.lips.api.OnRequestListener;
import xmu.swordbearer.lips.bean.Clip;

import java.util.ArrayList;
import java.util.List;

public class AddLineActivity extends Activity implements View.OnClickListener {
    private ImageButton btnBack, btnSend;
    private Spinner spinner;
    private EditText editText;
    private List<Clip> clipList = new ArrayList<Clip>();
    private long clipid = -1;
    private String content;
    private ClipAdapter clipAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what== NetHelper.STATUS_GETCLIP_ERROR){

            }else if(msg.what==NetHelper.STATUS_GETCLIP_SUCCESS){
                clipAdapter.notifyDataSetChanged();
            }
        }
    };
    private OnRequestListener getClipListener = new OnRequestListener() {
        @Override
        public void onError(String msg) {
        }

        @Override
        public void onComplete(Object object) {
            clipList = (List<Clip>) object;
        }
    };
    private OnRequestListener sendLineListener = new OnRequestListener() {
        @Override
        public void onError(String msg) {
        }

        @Override
        public void onComplete(Object object) {
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
//        editText = (EditText) findViewById(R.id.addline_content);
//
//        clipAdapter = new ClipAdapter();
//        spinner.setAdapter(clipAdapter);
//        //
        btnBack.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
//
//        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                clipid = clipList.get(position).getId();
//            }
//        });
//
//        getClips();
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                LineAPI.getClips(AddLineActivity.this, getClipListener);
            }
        }).start();
    }

    private void sendLine() {
        content = editText.getText().toString().trim();
        if (content.length() > 300 || content.length() == 0) {
            Toast.makeText(this, "发布失败:文字内容不能超过300字", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "正在发布...", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LineAPI.addLine(AddLineActivity.this, clipid, content, sendLineListener);
            }
        }).start();
    }

    private class ClipAdapter extends BaseAdapter {
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
            TextView textView = new TextView(AddLineActivity.this);
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
