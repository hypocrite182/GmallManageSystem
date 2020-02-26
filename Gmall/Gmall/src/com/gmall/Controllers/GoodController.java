package com.gmall.Controllers;

import java.util.ArrayList;

import com.gmall.Models.Good;
import com.gmall.Models.GoodAssist;
import com.gmall.Models.HttpGoodAssist;
import com.gmall.Models.IGoodAssist;
import com.gmall.Utils.UserIDUtil;

import android.content.Context;

public class GoodController {
	private IGoodAssist ga;
	
	public GoodController(Context context) {
		ga=new GoodAssist(context);
	}
	
	public Good getGoodById(int id) {
		return ga.getGoodById(id);
	}
	
	public ArrayList<Good> getAllGoods() {
		return ga.getGoods(null);
	}
	
	public ArrayList<Good> searchGoods(String key){
		return ga.searchGoods(key);
	}
	
	public ArrayList<Good> getTypeOfGoods(String typeName){
		return ga.getTypeOfGoods(typeName);
	}
	
	public void addNewGoods(String information,String photoPath,String name,double money,String typeName,String contact) {
		int userId=UserIDUtil.id;
		Good data=new Good(name, money, typeName, contact, photoPath, userId, information);
		ga.insert(data);
	}
	
	public void updateGood(int goodId,String information,String photoPath,String name,double money,String typeName,String contact) {
		Good data=ga.getGoodById(goodId);
		data.setInformation(information);
		data.setPhotoPath(photoPath);
		data.setName(name);
		data.setMoney(money);
		data.setTypeName(typeName);
		data.setContact(contact);
		ga.update(goodId, data);
	}
	
	public void deleteGood(int goodId) {
		ga.delete(goodId);
	}
}
