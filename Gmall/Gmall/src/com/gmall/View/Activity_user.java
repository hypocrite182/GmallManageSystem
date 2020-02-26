package com.gmall.View;

import android.support.v4.app.Fragment;

import java.io.File;

import com.example.gmall.R;
import com.gmall.Controllers.GoodController;
import com.gmall.Controllers.UserController;
import com.gmall.Models.User;
import com.gmall.Utils.PhotoSelectDialog;
import com.gmall.Utils.PhotoUtil;

import android.R.raw;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * 用户信息页
 */
public class Activity_user extends Fragment {

	public static Fragment frag;
	private Button user_out;
	private Button change_password;
	private Button change_info;
	private Button user_release;
	private Button user_sex;
	private Button user_school;
	private Button user_phone;
	private Button user_name;
	private Button change_pupo;
	private ImageView user_logo;
	private UserController ucon;
	private User user;
	private String photoPath;
	PhotoUtil p;
	private PhotoSelectDialog psd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_activity_user, null);
			ucon=new UserController(this.getActivity());
			frag=this;
			
			p=new PhotoUtil(this);
			user=ucon.getUser();
			user_name=(Button) view.findViewById(R.id.mine_name);
			user_phone=(Button) view.findViewById(R.id.mine_phone);
			user_school=(Button) view.findViewById(R.id.mine_school);
			user_sex=(Button) view.findViewById(R.id.mine_sex);
			user_release=(Button) view.findViewById(R.id.my_relese);
			change_info=(Button) view.findViewById(R.id.my_user);
			change_password=(Button) view.findViewById(R.id.my_password);
			user_out=(Button) view.findViewById(R.id.btn_exit);
			change_pupo=(Button) view.findViewById(R.id.my_photo);
			user_logo=(ImageView) view.findViewById(R.id.image_photoss);
			ButtonListenner listenner=new ButtonListenner();
			
			user_name.setText(user.getName());
			user_phone.setText(user.getPhone());
			user_school.setText(user.getSchool());
			user_sex.setText(user.getGender());
			user_name.setOnClickListener(listenner);
			user_phone.setOnClickListener(listenner);
			user_school.setOnClickListener(listenner);
			user_sex.setOnClickListener(listenner);
			user_release.setOnClickListener(listenner);
			change_info.setOnClickListener(listenner);
			change_password.setOnClickListener(listenner);
			user_out.setOnClickListener(listenner);
			if(!user.getHeadPhotoPath().equals("")) {
				user_logo.setImageURI(Uri.fromFile(new File(user.getHeadPhotoPath())));
			}
			change_pupo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					psd = new PhotoSelectDialog(getActivity(), new OnClickListener() {

						@Override
						public void onClick(View v) {
							int id = v.getId();
							switch (id) {
							case R.id.photo_take:
								p.startCameraAndCrop();
								break;
							case R.id.photo_album:
								p.selectPhotoAlbumAndCrop();
								break;
							}
						}
					});
					psd.showAtLocation(getActivity().findViewById(R.id.base),
							Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
				}
			});
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		int flag = p.OnActivityResultAccess(data, requestCode);
		if (flag == 3) {
			photoPath = p.getPhotoPath();
			UserController ucon = new UserController(getActivity());
			ucon.updateHeadPhoto(photoPath);
			user_logo.setImageURI(Uri.fromFile(new File(photoPath)));
			psd.dismiss();
		}
		else if(flag==2) {
			photoPath = p.getPhotoPath();
			UserController ucon = new UserController(getActivity());
			ucon.updateHeadPhoto(photoPath);
			user_logo.setImageURI(Uri.fromFile(new File(photoPath)));
			psd.dismiss();
		}
	}
	
	class ButtonListenner implements OnClickListener{

		@Override
		public void onClick(View v) {
			int id=v.getId();
			switch(id) {
			case R.id.mine_name:
			case R.id.mine_phone:
			case R.id.mine_school:
			case R.id.mine_sex:
			case R.id.my_user:
				Intent intent=new Intent();
				intent.setClass(getActivity(), Activity_modefy_user.class);
				startActivity(intent);
				break;
			case R.id.my_relese:
				Intent intent1=new Intent();
				intent1.setClass(getActivity(), Activity_myrelease.class);
				startActivity(intent1);
				break;
			case R.id.my_password:
				Intent intent2=new Intent();
				intent2.setClass(getActivity(), Activity_modefy_password.class);
				startActivity(intent2);
				break;
			case R.id.btn_exit:
				ucon.exitUser();
				Intent intent3=new Intent();
				intent3.setClass(getActivity(), LoginActivity.class);
				startActivity(intent3);
				getActivity().finish();
				break;
			case R.id.my_photo:
				break;
			}
		}
		
	}
}
