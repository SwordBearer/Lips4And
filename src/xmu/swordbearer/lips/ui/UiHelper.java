package xmu.swordbearer.lips.ui;

import xmu.swordbearer.lips.R;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class UiHelper {
	/****************** PopupWindow *************/
	/**
	 * 显示弹出的列表框
	 * 
	 * @param context
	 * @param ancher
	 * @param items
	 * @param bgDrawableId
	 * @param width
	 * @param height
	 * @param listener
	 * @return
	 */
	public static PopupWindow showPopupList(Context context, View ancher, String[] items, int bgDrawableId, int width, int height,
			OnItemClickListener listener) {
		PopupWindow popup = createPopupList(context, items, bgDrawableId, width, height, listener);
		popup.showAtLocation(ancher, Gravity.CENTER, 0, 0);
		return popup;
	}

	/**
	 * 显示下拉列表
	 * 
	 * @param context
	 * @param ancher
	 * @param items
	 * @param bgDrawableId
	 * @param width
	 * @param height
	 * @param listener
	 * @return
	 */
	public static PopupWindow showDropdownList(Context context, View ancher, String[] items, int bgDrawableId, int width, int height,
			OnItemClickListener listener) {
		PopupWindow popup = createPopupList(context, items, bgDrawableId, width, height, listener);
		popup.showAsDropDown(ancher);
		return popup;
	}

	/**
	 * 设置弹出列表菜单的选项，背景，长宽
	 * 
	 * @param itemsId菜单项数组的ID
	 * @param itemLayoutId一行布局的ID
	 * @param bgDrawableId背景ID
	 * @param width菜单宽度
	 * @param height菜单高度
	 */
	private static PopupWindow createPopupList(Context context, String[] items, int bgDrawableId, int width, int height, OnItemClickListener listener) {
		PopupWindow popup = new PopupWindow(context);
		ListView lv = new ListView(context);
		lv.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_single_choice, items));
		lv.setOnItemClickListener(listener);
		popup.setContentView(lv);
		popup.setFocusable(true);
		popup.setOutsideTouchable(true);
		popup.setBackgroundDrawable(context.getResources().getDrawable(bgDrawableId));
		popup.setWidth(width);
		popup.setHeight(height);
		popup.update();
		return popup;
	}

	/****************** Toast *************/
	public static void showError(Context context, int stringId) {
		Toast toast = Toast.makeText(context, stringId, Toast.LENGTH_LONG);
		toast.getView().setBackgroundResource(R.drawable.toast_bg_info);
		// TextView view = new TextView(context);
		// view.setBackgroundResource(R.drawable.toast_bg_info);
		// view.setText(stringId);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void showInfo(Context context, int stringId) {
		Toast toast = Toast.makeText(context, stringId, Toast.LENGTH_SHORT);
		toast.getView().setBackgroundResource(R.drawable.toast_bg_info);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
