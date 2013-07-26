package xmu.swordbearer.lips.bean;

import org.json.JSONException;
import org.json.JSONObject;

/** Created by SwordBearer on 13-6-24. */
public class Line extends BaseBean {
	private static final long serialVersionUID = 1L;

	private long id;
	private long uid;
	private long clipid;
	private String birth;
	private String content;
	private String author;

	public Line(JSONObject jo) throws JSONException {
		this.id = jo.getLong("id");
		this.uid = jo.getLong("uid");
		this.clipid = jo.getLong("clipid");
		this.birth = jo.getString("birth");
		this.content = jo.getString("content");
		this.author = jo.getString("author");
	}

	public long getId() {
		return id;
	}

	public long getUid() {
		return uid;
	}

	public long getClipid() {
		return clipid;
	}

	public String getBirth() {
		return birth;
	}

	public String getContent() {
		return content;
	}

	public String getAuthor() {
		return author;
	}

}
