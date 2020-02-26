package com.gmall.Models;

import java.util.ArrayList;

import com.gmall.Utils.SystemDbConnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GoodAssist implements IGoodAssist {
	private SQLiteDatabase db;
	
	public GoodAssist(Context context) {
		SystemDbConnection.setContext(context);
		SystemDbConnection con=new SystemDbConnection();
		db=con.getConnection();
	}
	
	public void insert(Good data) {
		ContentValues cv=new ContentValues();
		cv.put("name", data.getName());
		cv.put("money", data.getMoney());
		cv.put("typeName", data.getTypeName());
		cv.put("contact", data.getContact());
		cv.put("photoPath", data.getPhotoPath());
		cv.put("information", data.getInformation());
		cv.put("userId",data.getUserId());
		db.insert("goods", null, cv);
	}
	
	public void delete(int id) {
		db.delete("goods", "id=?", new String[] {String.valueOf(id)});
	}
	
	public void update(int id,Good data) {
		ContentValues cv=new ContentValues();
		cv.put("name", data.getName());
		cv.put("money", data.getMoney());
		cv.put("typeName", data.getTypeName());
		cv.put("contact", data.getContact());
		cv.put("photoPath", data.getPhotoPath());
		cv.put("information", data.getInformation());
		db.update("goods", cv, "id=?", new String[] {String.valueOf(id)});
	}
	
	public ArrayList<Good> getGoods(String[] conditions){
		ArrayList<Good> datas=new ArrayList<Good>();
		String sql="select * from goods ";
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
		
		sql+=" order by releaseTime desc ";
		Cursor c=db.rawQuery(sql, null);
		while(c.moveToNext()) {
			int id=c.getInt(c.getColumnIndex("id"));
			String name=c.getString(c.getColumnIndex("name"));
			double money=c.getDouble(c.getColumnIndex("money"));
			String typeName=c.getString(c.getColumnIndex("typeName"));
			String contact=c.getString(c.getColumnIndex("contact"));
			String photoPath=c.getString(c.getColumnIndex("photoPath"));
			String information=c.getString(c.getColumnIndex("information"));
			int userId=c.getInt(c.getColumnIndex("userId"));
			Good data=new Good(name, money, typeName, contact, photoPath, userId, information);
			data.setId(id);
			datas.add(data);
		}
		return datas;
	}

	public ArrayList<Good> searchGoods(String key){
		String condition=" name like '%"+key+"%' or money like '%"+key+"%' or typeName like '%"+key+"%'";
		ArrayList<Good> datas=getGoods(new String[] {condition});
		return datas;
	}
	
	public ArrayList<Good> getTypeOfGoods(String typeName){
		return getGoods(new String[] {"typeName='"+typeName+"'"});
	}
	
	public Good getGoodById(int id) {
		ArrayList<Good> datas=getGoods(new String[] {"id="+String.valueOf(id)});
		return datas.get(0);
	}
}
