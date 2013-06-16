package xmu.swordbearer.lips.application;

import android.app.Application;
import xmu.swordbearer.lips.bean.BriefAccountInfo;

/** Created by SwordBearers on 13-6-13. */
public class LipsApplication extends Application {
    //代表当前登录用户
    private static BriefAccountInfo me;

    public static void login() {
    }

    public static BriefAccountInfo getMyAccount() {
        return me;
    }
}
