package xmu.swordbearer.lips.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import xmu.swordbearer.lips.bean.Category;

public abstract class BaseCategoryAdapter extends BaseAdapter {

	protected LayoutInflater inflater;
	protected List<Category> list;

	public BaseCategoryAdapter(Context context, List<Category> list) {
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
