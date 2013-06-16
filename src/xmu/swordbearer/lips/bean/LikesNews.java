package xmu.swordbearer.lips.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class LikesNews extends BaseNews {
	private long lineid;
	private String lineContent;

	public LikesNews(JSONObject json) throws JSONException {
		super(json);
		this.type = NEWS_TYPE_LIKES;
		if (json.has("lid")) {
			this.lineid = json.getLong("lid");
		}
		if (json.has("lcon")) {
			this.lineContent = json.getString("lcon");
		}
	}

	public long getLineid() {
		return lineid;
	}

	public String getLineContent() {
		return lineContent;
	}
}
