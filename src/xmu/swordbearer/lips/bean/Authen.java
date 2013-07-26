package xmu.swordbearer.lips.bean;

public class Authen {
	private long uid;
	private String token;

	public Authen(long uid, String token) {
		this.uid = uid;
		this.token = token;
	}

	public long getUid() {
		return uid;
	}

	public String getToken() {
		return token;
	}
}
