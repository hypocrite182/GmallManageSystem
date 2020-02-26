package com.gmall.View;

import com.example.gmall.R;
import com.gmall.Controllers.UserController;
import com.gmall.Utils.LoginUtil;
import com.gmall.Utils.ViewUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/*
 * 登录页面
 */
public class LoginActivity extends Activity {
	
	private EditText etName;
	private EditText etPassword;
	private Button btnLogin;
	private Button btnRegister;
	private UserController uc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		if(LoginUtil.isLogined()) {
			Intent intent=new Intent(this,MainActivity.class);
			startActivity(intent);
			this.finish();
		}
		
		uc=new UserController(this.getApplicationContext());
		View.OnClickListener btnListener=new ButtonClickListener();
		etName=(EditText)findViewById(R.id.edit_phone);
		etPassword=(EditText)findViewById(R.id.edit_password);
		btnLogin=(Button)findViewById(R.id.btn_login);
		btnRegister=(Button)findViewById(R.id.btn_register);
		btnLogin.setOnClickListener(btnListener);
		btnRegister.setOnClickListener(btnListener);
	}
	
	class ButtonClickListener implements View.OnClickListener{

		@Override
		public void onClick(View arg0) {
			int clickId=arg0.getId();
			switch(clickId) {
			case R.id.btn_login:
				String name=etName.getText().toString();
				String password=etPassword.getText().toString();
				if(name.trim().equals("")) {
					ViewUtil.setBottomPrompt(LoginActivity.this.getApplicationContext(), "请输入用户名");
					return;
				}
				if(password.trim().equals("")) {
					ViewUtil.setBottomPrompt(LoginActivity.this.getApplicationContext(), "请输入密码");
					return;
				}
				if(uc.loginUser(name, password)) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Intent intent=new Intent(LoginActivity.this,MainActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
					return;
				}
				ViewUtil.setBottomPrompt(LoginActivity.this.getApplicationContext(), "用户或密码错误，请检查");
				break;
			case R.id.btn_register:
				Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(i);
				LoginActivity.this.finish();
				break;
			}
		}
	}
}
