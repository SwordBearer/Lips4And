package xmu.swordbearer.lips.activity.signin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.api.UserAPI;
import xmu.swordbearer.lips.api.OnRequestListener;
import xmu.swordbearer.lips.ui.UiHelper;

public class SignupActivity extends Activity implements OnClickListener {
    private EditText etName, etDesc, etEmail, etPassword;
    private RadioGroup rgGender;
    private Button btnSubmit;
    private String account, password;
    private OnRequestListener signupListener = new OnRequestListener() {
        @Override
        public void onError(String message) {
            Message msg = handler.obtainMessage();
            msg.what = 0;
            msg.obj = message;
            handler.sendMessage(msg);
        }

        @Override
        public void onComplete(Object obj) {
            UserAPI.logout(SignupActivity.this);
            Intent login = new Intent(SignupActivity.this, LoginActivity.class);
            login.putExtra(LoginActivity.EXTRA_ACCOUNT, account);
            login.putExtra(LoginActivity.EXTRA_PASSWORD, password);
            startActivity(login);
            finish();
        }

    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    UiHelper.showError(SignupActivity.this, msg.obj.toString());
                    break;
                case 1:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etName = (EditText) findViewById(R.id.signup_name);
        etDesc = (EditText) findViewById(R.id.signup_desc);
        etEmail = (EditText) findViewById(R.id.signup_email);
        etPassword = (EditText) findViewById(R.id.signup_password);
        rgGender = (RadioGroup) findViewById(R.id.signup_gender);
        btnSubmit = (Button) findViewById(R.id.signup_btn_submit);

        btnSubmit.setOnClickListener(this);
    }

    public void signup() {
        final String name = etName.getText().toString().trim();
        final String desc = etDesc.getText().toString().trim();
        account = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        final int type = 0;

        if (name == null || name.length() < 3 || name.length() > 20) {
            UiHelper.showError(this, R.string.valid_name);
            etName.requestFocus();
            return;
        }
        if (desc.length() > 200) {
            UiHelper.showError(this, R.string.valid_desc);
            etDesc.requestFocus();
            return;
        }
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                int gender = rgGender.getCheckedRadioButtonId();
                if (gender == R.id.signup_gender_m) {
                    gender = 1;
                } else if (gender == R.id.signup_gender_f) {
                    gender = 2;
                } else {
                    gender = 3;
                }
                UserAPI.signup(name, desc, account, password, gender, type, signupListener);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        if (v == btnSubmit) {
            signup();
        }
    }
}
