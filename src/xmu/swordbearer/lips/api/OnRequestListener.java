package xmu.swordbearer.lips.api;

public interface OnRequestListener {
	public void onError(int statusCode);

	public void onComplete(Object object);

}
