package xmu.swordbearer.lips.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import xmu.swordbearer.lips.bean.Clip;
import android.content.Context;
import android.util.Log;

public class LineAPI extends ClientAPI {
	private static final String TAG = "LineAPI";

	/**
	 * 获取用户的语录
	 * 
	 * @param context
	 * @param listener
	 */
	public static void getClips(Context context, long uid, OnRequestListener listener) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		try {
			String response = NetHelper.httpPost(NetHelper.METHOD_CLIPS, params);
			if (response == null) {
				listener.onError(NetHelper.STATUS_GETCLIPS_ERROR);
				return;
			}
			JSONObject jsonObject = new JSONObject(response);
			int status = jsonObject.getInt("status");
			if (status == NetHelper.STATUS_GETCLIPS_SUCCESS) {
				String data = jsonObject.getString("data");
				JSONArray ja = new JSONArray(data);
				List<Clip> clips = Clip.fromJSON(ja);
				listener.onComplete(clips);
			} else {
				listener.onError(NetHelper.STATUS_GETCLIPS_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addLine(Context context, long uid, String token, long clipId, String content, OnRequestListener listener) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		params.put("clipid", clipId);
		params.put("content", content);
		try {
			String response = NetHelper.httpPost(NetHelper.METHOD_ADDLINE, params);
			Log.e(TAG, "addLine返回的字符串是 " + response);
			if (response == null) {
				listener.onError(NetHelper.STATUS_ADDLINE_EEROR);
				return;
			}
			JSONObject jsonObject = new JSONObject(response);
			if (!jsonObject.has("status")) {
				listener.onError(NetHelper.STATUS_ADDLINE_EEROR);
				return;
			}
			int status = jsonObject.getInt("status");
			if (status == NetHelper.STATUS_ADDLINE_SUCCESS) {
				listener.onComplete("");
			} else if (status == NetHelper.STATUS_AUTHORIZED_FAILED) {
				listener.onError(NetHelper.STATUS_AUTHORIZED_FAILED);
			} else {
				listener.onError(NetHelper.STATUS_ADDLINE_EEROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Lines 默认每次获取20条数据
	 * 
	 * @param context
	 * @param uid
	 * @param token
	 * @param firstId页面最上方的一条Line的ID 如果刷新，则获取ID比firstId大的Line
	 * @param lastId 如果获取"更多",则使firstId为-1
	 * @param flag 获取方式：1 REFRESH;2 MORE
	 * @param listener
	 */
	public static void getFriendsLines(Context context, long uid, String token, long firstId, long lastId, int flag, OnRequestListener listener) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		params.put("firstid", firstId);
		params.put("lastid", lastId);
		params.put("flag", flag);
		try {
			String response = NetHelper.httpPost(NetHelper.METHOD_FRIENDS_LINES, params);
			Log.e(TAG, "getFriendsLines 返回的字符串是 " + response);
			if (response == null) {
				listener.onError(NetHelper.STATUS_GETACCOUNT_ERROR);
				return;
			}
			JSONObject jsonObject = new JSONObject(response);
			int status = jsonObject.getInt("status");
			if (status == NetHelper.STATUS_GETLINES_SUCCESS) {
				String data = jsonObject.getString("data");
				JSONArray ja = new JSONArray(data);
				listener.onComplete(ja);
			} else {
				listener.onError(status);
			}
		} catch (Exception e) {
			listener.onError(NetHelper.STATUS_GETLINES_ERROR);
			e.printStackTrace();
		}
	}
}
