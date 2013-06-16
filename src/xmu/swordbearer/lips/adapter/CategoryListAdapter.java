package xmu.swordbearer.lips.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.bean.Category;

import java.util.List;

public class CategoryListAdapter extends BaseCategoryAdapter {

	public CategoryListAdapter(Context context, List<Category> list) {
		super(context, list);
	}

	private class ListItemHoder {
		TextView tvName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemHoder hoder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listitem_category, null, false);
			hoder = new ListItemHoder();
			hoder.tvName = (TextView) convertView.findViewById(R.id.listitem_category_title);
			convertView.setTag(hoder);
		} else {
			hoder = (ListItemHoder) convertView.getTag();
		}
		Category cate = (Category) list.get(position);
		hoder.tvName.setText(cate.getName());
		return convertView;
	}
}
