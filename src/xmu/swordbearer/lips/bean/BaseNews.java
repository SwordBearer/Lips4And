package xmu.swordbearer.lips.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseNews {
    protected static final int NEWS_TYPE_FANS = 0;
    protected static final int NEWS_TYPE_LIKES = 1;
    protected static final int NEWS_TYPE_SUBS = 2;
    protected int type;
    private long userb;
    private String userbname;
    private String birth;

    public BaseNews(JSONObject json) throws JSONException {
        if (json.has("userb")) {
            this.userb = json.getLong("userb");
        }
        if (json.has("birth")) {
            this.birth = json.getString("birth");
        }
        if (json.has("userbname")) {
            this.userbname = json.getString("userbname");
        }
    }

    public int getType() {
        return type;
    }

    public long getUserb() {
        return userb;
    }

    public String getUserbname() {
        return userbname;
    }

    public String getBirth() {
        return birth;
    }
}
