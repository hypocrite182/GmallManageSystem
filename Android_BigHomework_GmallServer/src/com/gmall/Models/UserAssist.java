package com.gmall.Models;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gmall.Utils.CurrentServletUtil;
import com.gmall.Utils.DBUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class UserAssist {
	private static Connection db = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static DBUtil dbu = new DBUtil(CurrentServletUtil.servletContext);

	private UserAssist() {
	}
	
	private String resultSetToJson(ResultSet rs) throws SQLException,JSONException  
	{  
	   JSONArray array = new JSONArray();  
	    
	   // 获取列数  
	   ResultSetMetaData metaData = rs.getMetaData();  
	   int columnCount = metaData.getColumnCount();  
	    
	   // 遍历ResultSet中的每条数据  
	    while (rs.next()) {  
	        JSONObject jsonObj = new JSONObject();  
	         
	        // 遍历每一列  
	        for (int i = 1; i <= columnCount; i++) {  
	            String columnName =metaData.getColumnLabel(i);  
	            String value = rs.getString(columnName);  
	            jsonObj.put(columnName, value);  
	        }   
	        array.add(jsonObj); 
	    }  
	    
	   return array.toString();  
	}  

	public static void executeNoQuery(String sql, Object[] params) throws Exception {
		db = dbu.getConnection();
		ps = (PreparedStatement) db.prepareStatement(sql);

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
		}
		ps.execute();
	}

	public static ResultSet executeQuery(String sql, Object[] params) throws Exception {
		db = dbu.getConnection();
		ps = (PreparedStatement) db.prepareStatement(sql);

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
		}
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public static void delete(int id) {
		try {
			executeNoQuery("delete from users where id=?", new Object[] { id });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void update(int id, User data) {
		try {
			executeNoQuery(String.format(
					"update users set password='%s',phone='%s',gender='%s',school='%s',headPhotoPath='%s' where id=?",
					data.getPassword(), data.getPhone(), data.getGender(), data.getSchool(), data.getHeadPhotoPath()),
					new Object[] { id });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insert(User data) {
		String sql = "insert into users(name,password,phone,gender,school,headPhotoPath) values ('%s','%s','%s','%s','%s','%s')";
		sql = String.format(sql, data.getName(), data.getPassword(), data.getPhone(), data.getGender(),
				data.getSchool(), data.getHeadPhotoPath());
		try {
			executeNoQuery(sql, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ArrayList<User> getUsers(String[] conditions) {
		String sql = "select * from users ";
		ArrayList<User> datas = new ArrayList<User>();
		if (conditions != null) {
			String partSql = "";
			for (int i = 0; i < conditions.length; i++) {
				if (i == 0) {
					partSql += " where ( " + conditions[i] + " ) ";
				} else {
					partSql += " and ( " + conditions[i] + " ) ";
				}
			}
			sql = sql + partSql;
		}

		ResultSet rs = null;
		try {
			rs = executeQuery(sql, null);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String phone = rs.getString("phone");
				String gender = rs.getString("gender");
				String school = rs.getString("school");
				String headPhotoPath = rs.getString("headPhotoPath");

				ArrayList<Good> releaseGoods = GoodAssist.getGoods(new String[] { "userId=" + id });
				User data = new User(name, password, phone, gender, school, headPhotoPath);
				data.setId(id);
				data.setReleaseGoods(releaseGoods);
				datas.add(data);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return datas;
	}

	public static User getUserById(int id) {
		ArrayList<User> datas = getUsers(new String[] { "id=" + id });
		return datas.get(0);
	}

	public static User getUserByName(String userName) {
		ArrayList<User> datas = getUsers(new String[] { "name='" + userName + "'" });
		if (datas.size() == 0) {
			return null;
		} else {
			return datas.get(0);
		}
	}

	public static User getUserByNameAndPassword(String name, String password) {
		ArrayList<User> datas = getUsers(new String[] { "name='" + name + "'", "password='" + password + "'" });
		if (datas.size() == 0) {
			return null;
		} else {
			return datas.get(0);
		}
	}
}
