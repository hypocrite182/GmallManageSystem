package com.gmall.Utils;

import java.util.ArrayList;

import com.gmall.Models.Good;
import com.gmall.Models.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DataToJsonUtil {
	public static String userToJson(User u) {
		JSONObject json = new JSONObject();
		if (u != null) {
			JSONArray releaseGoods = new JSONArray();
			json.put("id", u.getId());
			json.put("name", u.getName());
			json.put("password", u.getPassword());
			json.put("phone", u.getPhone());
			json.put("gender", u.getGender());
			json.put("school", u.getSchool());
			json.put("headPhotoPath", u.getHeadPhotoPath());
			for (Good good : u.getReleaseGoods()) {
				JSONObject data = new JSONObject();
				data.put("id", good.getId());
				data.put("name", good.getName());
				data.put("money", good.getMoney());
				data.put("typeName", good.getTypeName());
				data.put("contact", good.getContact());
				data.put("photoPath", good.getPhotoPath());
				data.put("userId", good.getUserId());
				data.put("information", good.getInformation());
				releaseGoods.add(data);
			}
			json.put("releaseGoods", releaseGoods);
		}
		return json.toString();
	}

	public static String goodsToJson(ArrayList<Good> goods) {
		JSONArray releaseGoods = new JSONArray();
		for (Good good : goods) {
			JSONObject data = new JSONObject();
			data.put("id", good.getId());
			data.put("name", good.getName());
			data.put("money", good.getMoney());
			data.put("typeName", good.getTypeName());
			data.put("contact", good.getContact());
			data.put("photoPath", good.getPhotoPath());
			data.put("userId", good.getUserId());
			data.put("information", good.getInformation());
			releaseGoods.add(data);
		}
		return releaseGoods.toString();
	}
}
