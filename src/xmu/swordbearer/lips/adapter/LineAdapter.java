package xmu.swordbearer.lips.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.bean.Line;

import java.util.List;

/** Created by SwordBearer on 13-6-27. */
public class LineAdapter extends BaseAdapter {

    protected LayoutInflater inflater;
    private List<Line> list;

    public LineAdapter(Context context, List<Line> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LineViewHoder hoder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_line, null, false);
            hoder = new LineViewHoder();
            hoder.tvContent = (TextView) convertView.findViewById(R.id.listitem_line_content);
            hoder.btnAuthor = (Button) convertView.findViewById(R.id.listitem_line_author);
            hoder.btnComm = (Button) convertView.findViewById(R.id.listitem_line_comm);
            hoder.btnLike = (Button) convertView.findViewById(R.id.listitem_line_like);
            convertView.setTag(hoder);
        } else {
            hoder = (LineViewHoder) convertView.getTag();
        }
        Line line = list.get(position);
        hoder.tvContent.setText(line.getContent());
        hoder.btnAuthor.setText(line.getAuthor());
//        hoder.btnComm.setText(0 + "");
//        hoder.btnLike.setText(12 + "");
        return convertView;
    }

    public class LineViewHoder {
        public TextView tvContent;
        public Button btnAuthor, btnComm, btnLike;
    }
}
