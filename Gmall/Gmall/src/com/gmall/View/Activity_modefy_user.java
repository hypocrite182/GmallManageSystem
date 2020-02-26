package com.gmall.View;

import com.example.gmall.R;
import com.gmall.Controllers.UserController;
import com.gmall.Models.User;
import com.gmall.Utils.ViewUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
/*
 * 修改个人信息页面
 */
public class Activity_modefy_user extends Activity {
	private EditText cellphonenumber;
	private EditText school_name;
	private RadioButton button;
	private RadioGroup radioGroup;
	private Button button2;
	private UserController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_modefy_user);
		controller = new UserController(Activity_modefy_user.this);
		cellphonenumber = (EditText) findViewById(R.id.edit_phone);
		school_name = (EditText) findViewById(R.id.edit_school);
		radioGroup = (RadioGroup) findViewById(R.id.sex_rg);

		User current = controller.getUser();
		cellphonenumber.setText(current.getPhone());
		school_name.setText(current.getSchool());

		if (current.getGender().equals("男")) {
			radioGroup.check(R.id.male_rb);
		} else {
			radioGroup.check(R.id.famale_rb);
		}

		
		button2 = (Button) findViewById(R.id.btn_ture);
		ButtonListener buttonListener = new ButtonListener();
		button2.setOnClickListener(buttonListener);
	}

	public class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			button = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
			String phonenumber = cellphonenumber.getText().toString();
			String schoolname = school_name.getText().toString();
			String buttonString = button.getText().toString();
			if (phonenumber.equals("") || schoolname.equals("")) {
				ViewUtil.setBottomPrompt(Activity_modefy_user.this, "请补全信息！");
				return;
			} else {
				controller.updateUserInfo(phonenumber, schoolname, buttonString);
				ViewUtil.setBottomPrompt(Activity_modefy_user.this,"修改成功");
				Intent intent = new Intent(Activity_modefy_user.this, MainActivity.class);
				intent.putExtra("id", 4);
				Activity_user.frag.getActivity().finish();
				startActivity(intent);
				Activity_modefy_user.this.finish();
			}
		}

	}
}
