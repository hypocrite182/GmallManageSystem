package com.gmall.View;

import com.example.gmall.R;
import com.gmall.Controllers.UserController;
import com.gmall.Utils.ViewUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
/*
 * 注册页面
 */
public class RegisterActivity extends Activity {

	private EditText etName=null;
	private EditText etPassword=null;
	private EditText etPasswords=null;
	private EditText etPhone=null;
	private EditText etSchool=null;
	private RadioButton rbnBoy=null;
	private RadioButton rbnGirl=null;
	private Button submit=null;
	private UserController uc=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		uc=new UserController(this.getApplicationContext());
		etName=(EditText)findViewById(R.id.edit_name);
		etPassword=(EditText)findViewById(R.id.edit_password);
		etPasswords=(EditText)findViewById(R.id.edit_passwords);
		etPhone=(EditText)findViewById(R.id.edit_phone);
		etSchool=(EditText)findViewById(R.id.edit_school);
		rbnBoy=(RadioButton)findViewById(R.id.male_rb);
		rbnGirl=(RadioButton)findViewById(R.id.famale_rb);
		submit=(Button)findViewById(R.id.btn_ture);
		rbnBoy.setChecked(true);
		rbnGirl.setChecked(false);
		View.OnClickListener clickListener=new ButtonClickListener();
		
		rbnBoy.setOnClickListener(clickListener);
		rbnGirl.setOnClickListener(clickListener);
		submit.setOnClickListener(clickListener);
	}
	
	class ButtonClickListener implements View.OnClickListener{

		@Override
		public void onClick(View arg0) {
			int clickId=arg0.getId();
			switch(clickId) {
			case R.id.btn_ture:
				String name=etName.getText().toString().trim();
				String password=etPassword.getText().toString().trim();
				String passwords=etPasswords.getText().toString().trim();
				String phone=etPhone.getText().toString().trim();
				String school=etSchool.getText().toString().trim();
				String gender=rbnBoy.isChecked()?"男":"女";
				if(name.equals("")) {
					ViewUtil.setBottomPrompt(RegisterActivity.this.getApplicationContext(), "请输入用户名");
					return;
				}
				if(password.equals("")) {
					ViewUtil.setBottomPrompt(RegisterActivity.this.getApplicationContext(), "请输入密码");
					return;
				}
				if(passwords.equals("")) {
					ViewUtil.setBottomPrompt(RegisterActivity.this.getApplicationContext(), "请确认密码");
					return;
				}
				if(phone.equals("")) {
					ViewUtil.setBottomPrompt(RegisterActivity.this.getApplicationContext(), "请输入电话号码");
					return;
				}
				if(school.equals("")) {
					ViewUtil.setBottomPrompt(RegisterActivity.this.getApplicationContext(), "请输入学校名称");
					return;
				}
				if(!password.equals(passwords)) {
					ViewUtil.setBottomPrompt(RegisterActivity.this.getApplicationContext(), "警告！两次输入密码不一致！");
					return;
				}
				try {
					uc.registerNewUser(name, passwords, phone, school, gender);
					ViewUtil.setBottomPrompt(RegisterActivity.this.getApplicationContext(), "恭喜你注册成功！");
					Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
					RegisterActivity.this.startActivity(intent);
					RegisterActivity.this.finish();
				} catch (Exception e) {
					ViewUtil.setBottomPrompt(RegisterActivity.this.getApplicationContext(), "注册失败！已经存在的用户名！");
					return;
				}
				break;
			case R.id.famale_rb:
				rbnGirl.setChecked(true);
				rbnBoy.setChecked(false);
				break;
			case R.id.male_rb:
				rbnBoy.setChecked(true);
				rbnGirl.setChecked(false);
				break;
			}
		}
		
	}
}
