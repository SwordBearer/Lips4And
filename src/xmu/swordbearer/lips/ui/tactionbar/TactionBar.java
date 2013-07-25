package xmu.swordbearer.lips.ui.tactionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RadioGroup;

/**
 *
 */
public class TactionBar extends RadioGroup {

	public TactionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Tab newTab(Context context, int resid) {
		LayoutInflater inflater = LayoutInflater.from(context);
		return (Tab) inflater.inflate(resid, this, false);
	}

	/**
	 * 获取当前选中的RadioButton的index
	 * 
	 * @return
	 */
	public int getCheckedIndex() {
		int checkedIndex = -1;
		int count = this.getChildCount();
		for (int i = 0; i < count; i++) {
			Tab tab = (Tab) this.getChildAt(i);
			if (tab.isChecked()) {
				checkedIndex = i;
				break;
			}
		}
		return checkedIndex;
	}

	/**
	 * 设置此时的Tab
	 * 
	 * @param index
	 */
	public void setCurrentTab(int index) {
		Tab tab = (Tab) this.getChildAt(index);
		if (tab.isChecked())
			return;
		else {
			tab.setChecked(true);
		}
	}

}
