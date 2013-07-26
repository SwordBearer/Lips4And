package xmu.swordbearer.lips.application;

import xmu.swordbearer.lips.bean.Authen;
import xmu.swordbearer.lips.bean.BriefAccountInfo;
import xmu.swordbearer.lips.utils.CacheUtil;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/** Created by SwordBearers on 13-6-13. */
public class LipsApplication extends Application {

	protected static final String PREF_ACCOUNT = "lips_pref_account";
	protected static final String PREF_KEY_TOKEN = "lips_account_token";
	protected static final String PREF_KEY_UID = "lips_account_uid";
	protected static final String CACHE_BRIEFINFO = "lips_cache_briefinfo";
	protected static final String CACHE_CATEGORIES = "lips_cache_categories";

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
		return (getAuthen(context) != null);
	}

	public static Authen getAuthen(Context context) {
		SharedPreferences pref = context.getSharedPreferences(PREF_ACCOUNT, Context.MODE_PRIVATE);
		String token = pref.getString(PREF_KEY_TOKEN, null);
		long uid = pref.getLong(PREF_KEY_UID, -1);
		if (token == null || uid == -1) {
			return null;
		}
		return new Authen(uid, token);
	}

	/**
	 * 保存用户认证信息
	 * 
	 * @param context
	 * @param uid
	 * @param token
	 */
	public static void saveAuthen(Context context, long uid, String token) {
		SharedPreferences pref = context.getSharedPreferences(PREF_ACCOUNT, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putLong(PREF_KEY_UID, uid);
		editor.putString(PREF_KEY_TOKEN, token);
		editor.commit();
	}

	/**
	 * 删除Authen
	 * 
	 * @param context
	 */
	private static void clearAuthen(Context context) {
		SharedPreferences pref = context.getSharedPreferences(PREF_ACCOUNT, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 注销
	 * 
	 * @param context
	 */
	public static void logout(Context context) {
		clearAuthen(context);
	}

}
