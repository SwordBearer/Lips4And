package xmu.swordbearer.lips.application;

import android.app.Application;
import xmu.swordbearer.lips.bean.BriefAccountInfo;

/** Created by SwordBearers on 13-6-13. */
public class LipsApplication extends Application {
    //代表当前登录用户
    private BriefAccountInfo me;

    /*每次打开都需要登陆*/
    public void login() {
    }

    public BriefAccountInfo getMyAccount() {
        return me;
    }

    public int getRelationship(long source_uid, long target_uid) {
        return 0;
    }
}
