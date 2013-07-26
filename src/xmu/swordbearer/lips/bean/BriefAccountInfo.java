package xmu.swordbearer.lips.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 账户简要信息
 * 
 * @author SwordBearers
 */
public class BriefAccountInfo extends BaseBean {
	private static final long serialVersionUID = 1L;

	private long uid = -1;
	private String name = null;
	private String desc = null;
	private String email = null;
	private int gender = 0;
	private int grade = 0;
	private int lines = 0;
	private int followers = 0;
	private int following = 0;

	private BriefAccountInfo() {}

	public static BriefAccountInfo fromJSON(JSONObject json) throws JSONException {
		BriefAccountInfo me = new BriefAccountInfo();
		me.uid = json.getLong("id");
		me.name = json.getString("uname");
		me.desc = json.getString("udesc");
		me.gender = json.getInt("gender");
		me.grade = json.getInt("grade");

		if (json.has("lines")) {
			me.lines = json.getInt("lines");
		}
		if (json.has("followers")) {
			me.followers = json.getInt("followers");
		}
		if (json.has("following")) {
			me.following = json.getInt("following");
		}
		return me;
	}

	public long getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public String getEmail() {
		return this.email;
	}

	public int getGender() {
		return gender;
	}

	public int getGrade() {
		return grade;
	}

	public int getLines() {
		return lines;
	}

	public int getFollowers() {
		return followers;
	}

	public int getFollowing() {
		return following;
	}

	@Override
	public String toString() {
		return this.uid + " " + this.name + " " + this.desc + " " + this.email + " " + this.gender + " " + this.grade;
	}

}