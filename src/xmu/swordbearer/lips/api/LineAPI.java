package xmu.swordbearer.lips.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import xmu.swordbearer.lips.bean.Category;
import xmu.swordbearer.lips.bean.Clip;
import xmu.swordbearer.lips.utils.CacheUtil;

public class LineAPI extends ClientAPI {
	private static final String TAG = "LineAPI";


    public static void saveCacheCategories(Context context,List<Category> categories){
        CacheUtil.saveCache(context,CACHE_CATEGORIES,categories);
    }

    public static List<Category> readCacheCategories(Context context){
        return (List<Category>)CacheUtil.readCache(context,CACHE_CATEGORIES);
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

    public static void getClips(Context context,OnRequestListener listener){
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
        try {
            String response = NetHelper.httpPost(NetHelper.METHOD_CLIP, params);
            Log.e(TAG, "getClips返回的字符串是 " + response);
            if (response == null) {
                listener.onError("获取文集失败");
                return;
            }
            JSONObject jsonObject = new JSONObject(response);
            int status = jsonObject.getInt("status");
            if(status==NetHelper.STATUS_GETCLIP_SUCCESS){
                String data = jsonObject.getString("data");
                JSONArray ja = new JSONArray(data);
                List<Clip> clips = Clip.fromJSON(ja);
                listener.onComplete(clips);
            }else{
                listener.onError("获取文集失败");
            }
        } catch (Exception e) {
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
		params.put("content", uid);
		try {
			String response = NetHelper.httpPost(NetHelper.METHOD_BRIEFINFO, params);
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
}
