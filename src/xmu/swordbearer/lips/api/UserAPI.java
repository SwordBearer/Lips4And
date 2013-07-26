package xmu.swordbearer.lips.api;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class UserAPI extends ClientAPI {
	private static final String TAG = "UserAPI";

	/**
	 * login the server
	 * 
	 * @param email
	 * @param password
	 * @param listener
	 */
	public static void login(String email, String password, OnRequestListener listener) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		params.put("pwd", password);
		Log.e(TAG, "登录的参数是 " + params);
		try {
			String response = NetHelper.httpPost(NetHelper.METHOD_LOGIN, params);
			Log.e(TAG, "登录返回的字符串是 " + response);
			if (response == null) {
				listener.onError(NetHelper.STATUS_LOGIN_FAILED);
				return;
			}
			JSONObject jsonObject = new JSONObject(response);
			int status = jsonObject.getInt("status");
			if (status == NetHelper.STATUS_LOGIN_SUCCESS) {
				JSONObject jo = new JSONObject(jsonObject.getString("data"));
				listener.onComplete(jo);
			} else {
				listener.onError(status);
			}
		} catch (Exception e) {
			listener.onError(NetHelper.STATUS_LOGIN_FAILED);
			e.printStackTrace();
		}
	}

	/**
	 * 用户注册
	 * 
	 * @param username 用户名
	 * @param desc 简介
	 * @param email 账号
	 * @param password 密码
	 * @param gender 性别
	 * @param type 账户类型[0 自己注册，1新浪围脖]
	 * @param listener
	 */
	public static void signup(String username, String desc, String email, String password, int gender, int type, OnRequestListener listener) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uname", username);
		params.put("udesc", desc);
		params.put("email", email);
		params.put("pwd", password);
		params.put("gender", gender);
		params.put("type", type);
		try {
			Log.e(TAG, "注册提交的参数是 " + params);
			String response = NetHelper.httpPost(NetHelper.METHOD_SIGNUP, params);
			Log.e(TAG, "注册返回的字符串是 " + response);
			if (response == null) {
				listener.onError(NetHelper.STATUS_SIGNUP_ERROR);
				return;
			}
			JSONObject jsonObject = new JSONObject(response);
			int status = jsonObject.getInt("status");
			if (status == NetHelper.STATUS_SIGNUP_SUCCESS) {
				listener.onComplete(null);
			} else {
				listener.onError(status);
			}
		} catch (Exception e) {
			listener.onError(NetHelper.STATUS_SIGNUP_ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * 获取当前登录用户的简要账户信息 读取保存的 uid和token，然后提交获取账号信息
	 * 
	 * @param context
	 * @param uid
	 * @param listener
	 */
	public static void briefInfo(Context context, long uid, String token, OnRequestListener listener) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uid", uid);
			params.put("token", token);
			Log.e(TAG, "getBriefInfo提交的参数是 " + params);
			String response = NetHelper.httpPost(NetHelper.METHOD_BRIEFINFO, params);
			Log.e(TAG, "getBriefInfo返回的字符串是 " + response);
			if (response == null) {
				listener.onError(NetHelper.STATUS_GETACCOUNT_ERROR);
				return;
			}
			JSONObject jsonObject = new JSONObject(response);
			int status = jsonObject.getInt("status");
			if (status == NetHelper.STATUS_GETACCOUNT_SUCCESS) {
				JSONArray ja = jsonObject.getJSONArray("data");
				listener.onComplete(ja.getJSONObject(0));
			} else {
				listener.onError(status);
			}
		} catch (Exception e) {
			listener.onError(NetHelper.STATUS_GETACCOUNT_ERROR);
		}
	}

	/**
	 * 获取当前登录用户的最新消息
	 * 
	 * @param context
	 * @param listener
	 */
	public static void news(Context context, long uid, String token, OnRequestListener listener) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uid", uid);
			params.put("token", token);
			Log.e(TAG, "getNews提交的参数是 " + params);
			String response = NetHelper.httpPost(NetHelper.METHOD_NEWS, params);
			Log.e(TAG, "getNews返回的字符串是 " + response);
			if (response == null) {
				listener.onError(NetHelper.STATUS_GETNEWS_ERROR);
				return;
			}
			JSONObject jsonObject = new JSONObject(response);
			int status = jsonObject.getInt("status");
			if (status == NetHelper.STATUS_GETNEWS_SUCCESS) {
				listener.onComplete(jsonObject.getJSONArray("data"));
			} else {
				listener.onError(status);
			}
		} catch (Exception e) {
			listener.onError(NetHelper.STATUS_GETNEWS_ERROR);
		}
	}

}
