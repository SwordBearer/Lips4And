package xmu.swordbearer.lips.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class NetHelper {
	public static final String URI_PREFIX2 = "http://xlips.sinaapp.com/mobile.php/";
	public static final String URI_PREFIX = "http://192.168.1.101/lips/mobile.php/";
	public static final String METHOD_SIGNUP = URI_PREFIX + "users/signup";
	public static final String METHOD_LOGIN = URI_PREFIX + "users/login";
	public static final String METHOD_BRIEFINFO = URI_PREFIX + "users/briefinfo";
	public static final String METHOD_NEWS = URI_PREFIX + "users/news";// 获取用户最新动态

    public static final String METHOD_CATEGORY = URI_PREFIX + "line/category";
    public static final String METHOD_CLIP = URI_PREFIX + "line/clip";

    private static final int BUFFER_SIZE = 1024;
	/* 账号相关的100起头 */
	public static final int STATUS_SIGNUP_SAME = 101;// 该账号已经注册
	public static final int STATUS_SIGNUP_ERROR = 102;// 其他注册错误
	public static final int STATUS_SIGNUP_SUCCESS = 103;// 注册成功
	public static final int STATUS_LOGIN_ERROR_NOUSER = 104;// 没有该账户
	public static final int STATUS_LOGIN_ERROR_WRONG = 105;// 账户密码不匹配
	public static final int STATUS_LOGIN_SUCCESS = 106;// 登录成功
	//
	public static final int STATUS_GETACCOUNT_ERROR = 107;
	public static final int STATUS_GETACCOUNT_SUCCESS = 108;

	public static final int STATUS_AUTHORIZED_FAILED = 109;// 账户认证错误
	/**/
	public static final int STATUS_GETNEWS_ERROR = 201;
	public static final int STATUS_GETNEWS_SUCCESS = 202;

	/* 添加Line */
    public static final int STATUS_ADDLINE_TOOLONG = 301;// 文本内容太长，最长不超过500
	public static final int STATUS_ADDLINE_EEROR = 302;
	public static final int STATUS_ADDLINE_SUCCESS = 303;

	public static final int STATUS_DELLINE_ERROR = 304;
	public static final int STATUS_DELLINE_SUCCESS = 305;


    /* category */
    public static final int STATUS_GETCATEGORY_ERROR = 401;
    public static final int STATUS_GETCATEGORY_SUCCESS = 402;
    /*clip*/
    public static final int STATUS_GETCLIP_ERROR=501;
    public static final int STATUS_GETCLIP_SUCCESS=502;

	/**/

	/**
	 * 执行HttpPost方法
	 * 
	 * @param uri
	 * @param post_params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String httpPost(String uri, Map<String, Object> post_params) throws ClientProtocolException, IOException {
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setEntity(new UrlEncodedFormEntity(createNameValuePair(post_params), HTTP.UTF_8));
		HttpResponse response = httpClient.execute(httpPost);
		if (response != null) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				try {
					result = InputStreamTOString(entity.getContent());
					result = result.substring(result.indexOf("{"), result.length());
				} catch (Exception e) {
					return null;
				}
			}
		}
		return result;
	}

	/**
	 * InputStream转化成String
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	private static String InputStreamTOString(InputStream in) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);
		data = null;
		return new String(outStream.toByteArray());
	}

	/**
	 * 生成post参数对
	 * 
	 * @param map 提交的参数键值对
	 * @return
	 */
	private static ArrayList<NameValuePair> createNameValuePair(Map<String, Object> map) {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (String name : map.keySet()) {
			pairs.add(new BasicNameValuePair(name, String.valueOf(map.get(name))));
		}
		return pairs;
	}
}
