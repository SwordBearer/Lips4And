package xmu.swordbearer.lips.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import xmu.swordbearer.lips.bean.Clip;

import java.util.List;

public abstract class BaseClipAdapter extends BaseAdapter {

    protected LayoutInflater inflater;
    private List<Clip> list;

    public BaseClipAdapter(Context context, List list) {
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

}
