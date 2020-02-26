package com.gmall.Models;

import java.util.ArrayList;

import com.gmall.Utils.SystemDbConnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserAssist implements IUserAssist {
	private SQLiteDatabase db;
	
	private IGoodAssist ga;
	
	public UserAssist(Context context) {
		SystemDbConnection.setContext(context);
		SystemDbConnection con=new SystemDbConnection();
		db=con.getConnection();
		ga=new GoodAssist(context);
	}
	
	public void delete(int id) {
		db.delete("users", "id=?", new String[] {String.valueOf(id)});
	}
	
	public void update(int id,User data) {
		ContentValues cv=new ContentValues();
		cv.put("password", data.getPassword());
		cv.put("phone", data.getPhone());
		cv.put("gender", data.getGender());
		cv.put("school", data.getSchool());
		cv.put("headPhotoPath", data.getHeadPhotoPath());
		db.update("users", cv, "id=?", new String[] {String.valueOf(id)});
	}
	
	public void insert(User data) {
		String sql="insert into users(name,password,phone,gender,school,headPhotoPath) values ('%s','%s','%s','%s','%s','%s')";
		sql=String.format(sql, data.getName(),data.getPassword(),data.getPhone(),data.getGender(),data.getSchool(),data.getHeadPhotoPath());
		db.execSQL(sql);
	}
	
	public ArrayList<User> getUsers(String[] conditions){
		String sql="select * from users ";
		ArrayList<User> datas=new ArrayList<User>();
		if(conditions!=null) {
			String partSql="";
			for(int i=0;i<conditions.length;i++) {
				if(i==0) {
					partSql+=" where ( "+conditions[i]+" ) ";
				}
				else {
					partSql+=" and ( "+conditions[i]+" ) ";
				}
			}
			sql=sql+partSql;
		}
		
		Cursor c=null;
		try {
			c=db.rawQuery(sql, null);
		}catch(Exception e) {
			System.out.println(e);
		}
		while(c.moveToNext()) {
			int id=c.getInt(c.getColumnIndex("id"));
			String name=c.getString(c.getColumnIndex("name"));
			String password=c.getString(c.getColumnIndex("password"));
			String phone=c.getString(c.getColumnIndex("phone"));
			String gender=c.getString(c.getColumnIndex("gender"));
			String school=c.getString(c.getColumnIndex("school"));
			String headPhotoPath=c.getString(c.getColumnIndex("headPhotoPath"));
			ArrayList<Good> releaseGoods=ga.getGoods(new String[] {"userId="+id});
			User data=new User(name, password, phone, gender, school, headPhotoPath);
			data.setId(id);
			data.setReleaseGoods(releaseGoods);
			datas.add(data);
		}
		return datas;
	}

	public User getUserById(int id) {
		ArrayList<User> datas=getUsers(new String[] {"id="+id});
		return datas.get(0);
	}
	
	public User getUserByName(String userName) {
		ArrayList<User> datas=getUsers(new String[] {"name='"+userName+"'"});
		if(datas.size()==0) {
			return null;
		}
		else {
			return datas.get(0);
		}
	}
	
	public User getUserByNameAndPassword(String name,String password) {
		ArrayList<User> datas=getUsers(new String[] {"name='"+name+"'","password='"+password+"'"});
		if(datas.size()==0) {
			return null;
		}
		else {
			return datas.get(0);
		}
	}
}
