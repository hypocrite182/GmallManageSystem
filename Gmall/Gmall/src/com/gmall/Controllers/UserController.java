package com.gmall.Controllers;

import java.util.ArrayList;

import com.gmall.Models.Good;
import com.gmall.Models.HttpUserAssist;
import com.gmall.Models.IUserAssist;
import com.gmall.Models.User;
import com.gmall.Models.UserAssist;
import com.gmall.Utils.LoginUtil;
import com.gmall.Utils.UserIDUtil;

import android.content.Context;

public class UserController {
	private IUserAssist ua;
	
	public UserController(Context context) {
		ua=new UserAssist(context);
	}
	
	public User getUser() {
		return ua.getUserById(UserIDUtil.id);
	}
	
	public void registerNewUser(String name,String password,String phone,String school,String gender)throws Exception {
		User data=ua.getUserByName(name);
		if(data!=null) {
			throw new Exception("已存在的用户名");
		}
		data=new User(name, password, phone, gender, school, "");
		ua.insert(data);
	}
	
	public boolean loginUser(String name,String password) {
		User data=ua.getUserByNameAndPassword(name, password);
		if(data!=null) {
			UserIDUtil.id=data.getId();
			LoginUtil lu=new LoginUtil();
			lu.noteLoginState(UserIDUtil.id);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void exitUser() {
		UserIDUtil.id=0;
		LoginUtil lu=new LoginUtil();
		lu.exitLoginState();
	}
	
	public void updateHeadPhoto(String headPhotoPath) {
		int userId=UserIDUtil.id;
		User data=ua.getUserById(userId);
		data.setHeadPhotoPath(headPhotoPath);
		ua.update(userId, data);
	}
	
	public ArrayList<Good> getReleaseGoods(){
		int userId=UserIDUtil.id;
		User u=ua.getUserById(userId);
		return u.getReleaseGoods();
	}
	
	public void updateUserInfo(String phone,String school,String gender) {
		int userId=UserIDUtil.id;
		User data=ua.getUserById(userId);
		data.setPhone(phone);
		data.setSchool(school);
		data.setGender(gender);
		ua.update(userId, data);
	}
	
	public void updatePassword(String password) {
		int userId=UserIDUtil.id;
		User data=ua.getUserById(userId);
		data.setPassword(password);
		ua.update(userId, data);
	}
}
