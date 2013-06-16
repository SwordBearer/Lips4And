package xmu.swordbearer.lips.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class FansNews extends BaseNews {
	public FansNews(JSONObject json) throws JSONException {
		super(json);
		this.type = NEWS_TYPE_FANS;
	}
}
