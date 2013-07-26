package xmu.swordbearer.lips.ui.signin;

import org.json.JSONException;
import org.json.JSONObject;

import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.api.NetHelper;
import xmu.swordbearer.lips.api.OnRequestListener;
import xmu.swordbearer.lips.api.UserAPI;
import xmu.swordbearer.lips.application.LipsApplication;
import xmu.swordbearer.lips.ui.UiHelper;
import xmu.swordbearer.lips.ui.home.HomeActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {
	public static final String EXTRA_ACCOUNT = "account";
	public static final String EXTRA_PASSWORD = "password";
	private Button btnLogin, btnSignup, btnOther;
	private EditText etEmail, etPassword;
	// 登录监听器
	private OnRequestListener loginListener = new OnRequestListener() {
		@Override
		public void onError(int statusCode) {
			handler.sendEmptyMessage(statusCode);
		}

		@Override
		public void onComplete(Object obj) {
			try {
				JSONObject jsonObject = (JSONObject) obj;
				LipsApplication.saveAuthen(LoginActivity.this, jsonObject.getLong("id"), jsonObject.getString("token"));
				handler.sendEmptyMessage(NetHelper.STATUS_LOGIN_SUCCESS);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case NetHelper.STATUS_LOGIN_FAILED:
				UiHelper.showError(LoginActivity.this, R.string.login_error);
				break;
			case NetHelper.STATUS_LOGIN_PASSWD_WRONG:
				UiHelper.showError(LoginActivity.this, R.string.login_passwd_wrong);
				break;
			case NetHelper.STATUS_LOGIN_SUCCESS:
				UiHelper.showError(LoginActivity.this, R.string.login_success);
				// 登录成功，进入主页
				startActivity(new Intent(LoginActivity.this, HomeActivity.class));
				finish();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initViews();

		initData();
	}

	private void initViews() {
		etEmail = (EditText) findViewById(R.id.login_email);
		etPassword = (EditText) findViewById(R.id.login_password);
		btnLogin = (Button) findViewById(R.id.login_btn_login);
		btnOther = (Button) findViewById(R.id.login_btn_other);
		btnSignup = (Button) findViewById(R.id.login_btn_signup);

		btnSignup.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		btnOther.setOnClickListener(this);

	}

	/** 如果注册成功就开始登录 */
	private void initData() {
		Intent intent = this.getIntent();
		String account = intent.getStringExtra(EXTRA_ACCOUNT);
		String password = intent.getStringExtra(EXTRA_PASSWORD);
		if (account != null && password != null) {
			etEmail.setText(account);
			etPassword.setText(password);
			login(account, password);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == btnLogin) {
			String account = etEmail.getText().toString().trim();
			String password = etPassword.getText().toString().trim();
			if (account == null || account.length() < 7 || account.length() > 30) {
				UiHelper.showError(this, R.string.valid_email_size);
				etEmail.requestFocus();
				return;
			}
			if (password == null || password.length() < 4 || password.length() > 20) {
				UiHelper.showError(this, R.string.valid_password);
				etPassword.requestFocus();
				return;
			}
			login(account, password);
		} else if (v == btnSignup) {
			startActivity(new Intent(this, SignupActivity.class));
			finish();
		}
	}

	private void login(final String account, final String password) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				UserAPI.login(account, password, loginListener);
			}
		}).start();
	}

}
