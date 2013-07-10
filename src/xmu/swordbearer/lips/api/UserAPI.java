package xmu.swordbearer.lips.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import xmu.swordbearer.lips.bean.BriefAccountInfo;
import xmu.swordbearer.lips.utils.CacheUtil;

import java.util.HashMap;
import java.util.Map;

public class UserAPI extends ClientAPI {
    private static final String TAG = "UserAPI";

    public static boolean saveCacheAccount(Context context, BriefAccountInfo info) {
        return CacheUtil.saveCache(context, CACHE_BRIEFINFO, info);
    }

    public static BriefAccountInfo readCachedAccount(Context context) {
        return (BriefAccountInfo) CacheUtil.readCache(context, CACHE_BRIEFINFO);
    }

    /**
     * 检测是否已经保存了登录信息
     *
     * @return
     */
    public static boolean isLogin(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_ACCOUNT, Context.MODE_PRIVATE);
        String token = pref.getString(PREF_KEY_TOKEN, null);
        return token != null;
    }

    public static long getUID(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_ACCOUNT, Context.MODE_PRIVATE);
        return pref.getLong(PREF_KEY_UID, -1);
    }

    /**
     * 保存Token
     *
     * @param context
     * @param uid
     * @param token
     */
    public static void saveToken(Context context, long uid, String token) {
        SharedPreferences pref = context.getSharedPreferences(PREF_ACCOUNT, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putLong(PREF_KEY_UID, uid);
        editor.putString(PREF_KEY_TOKEN, token);
        editor.commit();
    }

    /**
     * 删除Token
     *
     * @param context
     */
    private static void cleanToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_ACCOUNT, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.remove(PREF_KEY_TOKEN);
        editor.commit();
    }

    /**
     * 注销
     *
     * @param context
     */
    public static void logout(Context context) {
        cleanToken(context);
    }

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
                listener.onError("登录失败");
                return;
            }
            JSONObject jsonObject = new JSONObject(response);
            int status = jsonObject.getInt("status");
            if (status == NetHelper.STATUS_LOGIN_SUCCESS) {
                JSONObject jo = new JSONObject(jsonObject.getString("data"));
                listener.onComplete(jo);
            } else if (status == NetHelper.STATUS_LOGIN_ERROR_NOUSER) {
                listener.onError("没有该账号");
            } else {
                listener.onError("登录密码错误");
            }
        } catch (Exception e) {
            listener.onError("未知错误");
            e.printStackTrace();
        }
    }

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param desc     简介
     * @param email    账号
     * @param password 密码
     * @param gender   性别
     * @param type     账户类型[0 自己注册，1新浪围脖]
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
                listener.onError("注册失败 response is null");
                return;
            }
            JSONObject jsonObject = new JSONObject(response);
            int status = jsonObject.getInt("status");
            if (status == NetHelper.STATUS_SIGNUP_SUCCESS) {
                listener.onComplete(null);
            } else if (status == NetHelper.STATUS_SIGNUP_SAME) {
                listener.onError("该账号已经被注册");
            } else {
                listener.onError("注册失败");
            }
        } catch (Exception e) {
            listener.onError("注册失败 exception");
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
    public static void briefInfo(Context context, OnRequestListener listener) {
        SharedPreferences pref = context.getSharedPreferences(PREF_ACCOUNT, Context.MODE_PRIVATE);
        String token = pref.getString(PREF_KEY_TOKEN, null);
        long uid = pref.getLong(PREF_KEY_UID, -1);
        if (token == null || uid == -1) {
            listener.onError("");
            return;
        }
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            params.put("token", token);
            Log.e(TAG, "getBriefInfo提交的参数是 " + params);
            String response = NetHelper.httpPost(NetHelper.METHOD_BRIEFINFO, params);
            Log.e(TAG, "getBriefInfo返回的字符串是 " + response);
            if (response == null) {
                listener.onError("获取账号信息失败");
                return;
            }
            JSONObject jsonObject = new JSONObject(response);
            int status = jsonObject.getInt("status");
            if (status == NetHelper.STATUS_AUTHORIZED_FAILED) {
                listener.onError("账户认证错误，请重新登录");
            } else if (status == NetHelper.STATUS_GETACCOUNT_SUCCESS) {
                JSONArray ja = jsonObject.getJSONArray("data");
                listener.onComplete(ja.getJSONObject(0));
            } else {
                listener.onError("获取账号信息失败");
            }
        } catch (Exception e) {
            listener.onError("获取账号信息失败");
        }
    }

    /**
     * 获取当前登录用户的最新消息
     *
     * @param context
     * @param listener
     */
    public static void news(Context context, OnRequestListener listener) {
        SharedPreferences pref = context.getSharedPreferences(PREF_ACCOUNT, Context.MODE_PRIVATE);
        String token = pref.getString(PREF_KEY_TOKEN, null);
        long uid = pref.getLong(PREF_KEY_UID, -1);
        if (token == null || uid == -1) {
            listener.onError("");
            return;
        }
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            params.put("token", token);
            Log.e(TAG, "getNews提交的参数是 " + params);
            String response = NetHelper.httpPost(NetHelper.METHOD_NEWS, params);
            Log.e(TAG, "getNews返回的字符串是 " + response);
            if (response == null) {
                listener.onError("获取最新动态失败");
                return;
            }
            JSONObject jsonObject = new JSONObject(response);
            int status = jsonObject.getInt("status");
            if (status == NetHelper.STATUS_AUTHORIZED_FAILED) {
                listener.onError("账户认证错误，请重新登录");
            } else if (status == NetHelper.STATUS_GETNEWS_SUCCESS) {
                listener.onComplete(jsonObject.getJSONArray("data"));
            } else {
                listener.onError("获取最新动态失败");
            }
        } catch (Exception e) {
            listener.onError("获取最新动态失败");
        }
    }

}
