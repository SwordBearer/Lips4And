package xmu.swordbearer.lips.ui;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class UiHelper {
	public static void showError(Context context, int stringId) {
		Toast toast = Toast.makeText(context, stringId, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void showError(Context context, String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void showInfo(Context context, int stringId) {
		Toast toast = Toast.makeText(context, stringId, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
