package xmu.swordbearer.lips.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class WrapLayout extends ViewGroup {
	private int mCellWidth = 30;
	private int mCellHeight = 120;

	public WrapLayout(Context context) {
		super(context);
	}

	public WrapLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WrapLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	public void addChild(String childName) {
		TextView tv = new TextView(getContext());
		tv.setBackgroundResource(android.R.color.black);
		tv.setText(childName);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		int x = getChildCount() - 1;
		this.addView(tv, params);

		TableLayout tableLayout;
		GridLayout g;
		

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int cellWidth = mCellWidth;
		int cellHeight = mCellHeight;
		int columns = (r - l) / cellWidth;
		if (columns < 0) {
			columns = 1;
		}
		int x = 0;
		int y = 0;
		int i = 0;
		int count = getChildCount();
		for (int j = 0; j < count; j++) {
			final View childView = getChildAt(j);
			// 获取子控件Child的宽高
			int w = childView.getMeasuredWidth();
			int h = childView.getMeasuredHeight();
			// 计算子控件的顶点坐标
			int left = x + ((cellWidth - w) / 2);
			int top = y + ((cellHeight - h) / 2);
			// int left = x;
			// int top = y;
			// 布局子控件
			childView.layout(left, top, left + w, top + h);

			if (i >= (columns - 1)) {
				i = 0;
				x = 0;
				y += cellHeight;
			} else {
				i++;
				x += cellWidth;

			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
