package com.gmall.View;

import com.example.gmall.R;
import com.gmall.Controllers.UserController;
import com.gmall.Models.User;
import com.gmall.Models.UserAssist;
import com.gmall.Utils.ViewUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/*
 * 修改密码页面
 */
public class Activity_modefy_password extends Activity {

	private EditText original_password;
	private EditText newpassword;
	private EditText anewpassword;
	private Button button;
	private User user;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_activity_modefy_password);
			original_password=(EditText)findViewById(R.id.edit_name);
			newpassword=(EditText)findViewById(R.id.edit_password);
			anewpassword=(EditText)findViewById(R.id.edit_passwords);
			button=(Button)findViewById(R.id.btn_ture);
			ButtonListener buttonListener=new ButtonListener();
			button.setOnClickListener(buttonListener);
		}
		public class ButtonListener implements OnClickListener{

			@Override
			public void onClick(View v) {
				String passwordString=original_password.getText().toString();
				String newpaString=newpassword.getText().toString();
				String anewpaString=anewpassword.getText().toString();
				UserController control=new UserController(Activity_modefy_password.this);
			    user=control.getUser();
			    if(passwordString.trim().equals("")||newpaString.trim().equals("")||anewpaString.trim().equals("")) {
			    	ViewUtil.setBottomPrompt(Activity_modefy_password.this,"请补全信息!");
			    	return;
			    }
		        if(user.getPassword().equals(passwordString)){
		        	 if(newpaString.equals(anewpaString)){
		        		 control.updatePassword(newpaString);
		        		 ViewUtil.setBottomPrompt(Activity_modefy_password.this,"修改成功");
		        		Intent intent = new Intent(Activity_modefy_password.this, MainActivity.class);
		 				intent.putExtra("id", 4);
		 				Activity_user.frag.getActivity().finish();
		 				startActivity(intent);
		 				Activity_modefy_password.this.finish();
		        	 }else{
		        		 ViewUtil.setBottomPrompt(Activity_modefy_password.this,"两次输入密码不一致");
		        	 }
		         }else{
		        	 ViewUtil.setBottomPrompt(Activity_modefy_password.this,"原密码错误");
		         }
			}
			
		}
}