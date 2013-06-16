package xmu.swordbearer.lips.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class SubscribeNews extends BaseNews {
	private long clipid;
	private String clipName;

	public SubscribeNews(JSONObject json) throws JSONException {
		super(json);
		this.type = NEWS_TYPE_SUBS;
		if (json.has("cid")) {
			this.clipid = json.getLong("cid");
		}
		if (json.has("cname")) {
			this.clipName = json.getString("cname");
		}
	}

	public long getClipid() {
		return clipid;
	}

	public String getClipName() {
		return clipName;
	}

}
