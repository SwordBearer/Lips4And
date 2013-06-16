package xmu.swordbearer.lips.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class UserAccount {

	private long uid;
	private String name;
	private String desc;
	private int gender;
	private String birth;
	private int grade;

	public static UserAccount fromJSON(JSONObject json) throws JSONException {
		UserAccount user = new UserAccount();
		user.uid = json.getLong("id");
		user.name = json.getString("uname");
		user.desc = json.getString("udesc");
		user.gender = json.getInt("gender");
		user.birth = json.getString("birth");
		user.grade = json.getInt("grade");
		return user;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}
