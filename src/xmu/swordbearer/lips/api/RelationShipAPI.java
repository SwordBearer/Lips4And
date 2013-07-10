package xmu.swordbearer.lips.api;

import android.content.Context;

/** Created by SwordBearers on 13-6-23. */
public class RelationShipAPI extends ClientAPI {
    /**
     * 获取两个用户之间的关系
     *
     * @param context
     * @param source_uid 原用户的id
     * @param target_uid 目标用户的id
     */
    public static void show(Context context, long source_uid, long target_uid, OnRequestListener listener) {
    }

    /**
     * 获取当前登录用户的关注列表
     * * @param context
     *
     * @param listener
     */
    public static void friends(Context context, long first_id, long last_id, OnRequestListener listener) {
    }

    public static void followers(Context context, long first_id, long last_id, OnRequestListener listener) {

    }

    public static void follow(Context context, long target_uid, OnRequestListener listener) {
    }

    public static void unfollow(Context context, long target_uid, OnRequestListener listener) {
    }
}
