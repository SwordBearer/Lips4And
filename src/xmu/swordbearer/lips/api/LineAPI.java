package xmu.swordbearer.lips.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xmu.swordbearer.lips.bean.Category;
import xmu.swordbearer.lips.bean.Clip;
import xmu.swordbearer.lips.utils.CacheUtil;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class LineAPI extends ClientAPI {
	private static final String TAG = "LineAPI";

	@Deprecated
	public static void saveCacheCategories(Context context, List<Category> categories) {
		CacheUtil.saveCache(context, CACHE_CATEGORIES, categories);
	}

	public static List<Category> readCacheCategories(Context context) {
		return (List<Category>) CacheUtil.readCache(context, CACHE_CATEGORIES);
	}

	/**
	 * 获得所有Line的分类，按照次序排列
	 * 
	 * @param context
	 * @param listener
	 */
	public static void getCategories(Context context, OnRequestListener listener) {
		try {
			String response = NetHelper.httpPost(NetHelper.METHOD_CATEGORY, new HashMap<String, Object>());
			Log.e(TAG, "getCategories 返回的字符串是 " + response);
			if (response == null) {
				listener.onError("getCategories失败");
				return;
			}
			JSONObject jsonObject = new JSONObject(response);
			int status = jsonObject.getInt("status");
			if (status == NetHelper.STATUS_GETCATEGORY_SUCCESS) {
				String data = jsonObject.getString("data");
				JSONArray ja = new JSONArray(data);
				List<Category> cates = Category.fromJSON(ja);
				listener.onComplete(cates);
			} else {
				listener.onError("getCategories失败");
			}
		} catch (Exception e) {

			listener.onError("getCategories未知错误");
			e.printStackTrace();
		}
	}

	/**
	 * 获取用户的语录
	 * 
	 * @param context
	 * @param listener
	 */
	public static void getClips(Context context, OnRequestListener listener) {
		SharedPreferences pref = context.getSharedPreferences(PREF_ACCOUNT, Context.MODE_PRIVATE);
		long uid = pref.getLong(PREF_KEY_UID, -1);
		if (uid == -1) {
			listener.onError("");
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		try {
			String response = NetHelper.httpPost(NetHelper.METHOD_CLIPS, params);
			if (response == null) {
				listener.onError("获取文集失败");
				return;
			}
			try {
				JSONObject jsonObject = new JSONObject(response);
				int status = jsonObject.getInt("status");
				if (status == NetHelper.STATUS_GETCLIPS_SUCCESS) {
					String data = jsonObject.getString("data");
					JSONArray ja = new JSONArray(data);
					List<Clip> clips = Clip.fromJSON(ja);
					listener.onComplete(clips);
				} else {
					listener.onError("获取文集失败");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addLine(Context context, long clipId, String content, OnRequestListener listener) {
		SharedPreferences pref = context.getSharedPreferences(PREF_ACCOUNT, Context.MODE_PRIVATE);
		String token = pref.getString(PREF_KEY_TOKEN, null);
		long uid = pref.getLong(PREF_KEY_UID, -1);
		if (token == null || uid == -1) {
			listener.onError("");
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		params.put("clipid", clipId);
		params.put("content", content);
		try {
			String response = NetHelper.httpPost(NetHelper.METHOD_ADDLINE, params);
			Log.e(TAG, "addLine返回的字符串是 " + response);
			if (response == null) {
				listener.onError("发布失败");
				return;
			}
			JSONObject jsonObject = new JSONObject(response);
			int status = jsonObject.getInt("status");
			if (status == NetHelper.STATUS_AUTHORIZED_FAILED) {
				listener.onError("账户认证错误，请登录后再添加");
			} else if (status == NetHelper.STATUS_ADDLINE_SUCCESS) {
				listener.onComplete("发布成功");
			} else {
				listener.onError("未知错误，添加成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取比first_id小，比last_id大的Lines 默认每次获取20条数据
	 * 
	 * @param context
	 * @param first_id
	 * @param last_id
	 * @param listener
	 */
	public static void getFriendsLines(Context context, long first_id, long last_id, OnRequestListener listener) {
		SharedPreferences pref = context.getSharedPreferences(PREF_ACCOUNT, Context.MODE_PRIVATE);
		String token = pref.getString(PREF_KEY_TOKEN, null);
		long uid = pref.getLong(PREF_KEY_UID, -1);
		if (token == null || uid == -1) {
			listener.onError("");
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		params.put("fid", first_id);
		params.put("lid", last_id);

		try {
			String response = NetHelper.httpPost(NetHelper.METHOD_FRIENDS_LINES, params);
			Log.e(TAG, "getFriendsLines 返回的字符串是 " + response);
			if (response == null) {
				listener.onError("获取最新动态失败");
				return;
			}
			try {
				JSONObject jsonObject = new JSONObject(response);
				int status = jsonObject.getInt("status");
				if (status == NetHelper.STATUS_AUTHORIZED_FAILED) {
					listener.onError("账户认证错误，请登录后再查看");
				} else if (status == NetHelper.STATUS_GETLINES_SUCCESS) {
					String data = jsonObject.getString("data");
					JSONArray ja = new JSONArray(data);
					listener.onComplete(ja);
				} else {
					listener.onError("未知错误，获取最新动态失败");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
