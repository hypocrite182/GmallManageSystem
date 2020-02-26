package com.gmall.Models;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.gmall.Utils.CurrentServletUtil;
import com.gmall.Utils.DBUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class GoodAssist {
	private static Connection db = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static DBUtil dbu = new DBUtil(CurrentServletUtil.servletContext);

	private GoodAssist() {
	}

	public static void executeNoQuery(String sql, Object[] params)
			throws Exception {
		db = dbu.getConnection();
		ps = (PreparedStatement) db.prepareStatement(sql);

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
		}
		ps.execute();
	}

	public static ResultSet executeQuery(String sql, Object[] params)
			throws Exception {
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

	public static void insert(Good data) {
		String sql = "insert into goods(name,money,typeName,contact,photoPath,information,userId) values ('%s',%f,'%s','%s','%s','%s',%d)";
		sql = String.format(sql, data.getName(), data.getMoney(),
				data.getTypeName(), data.getContact(), data.getPhotoPath(),
				data.getInformation(), data.getUserId());
		try {
			executeNoQuery(sql, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void delete(int id) {
		try {
			executeNoQuery("delete from goods where id=?", new Object[] { id });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void update(int id, Good data) {
		try {
			executeNoQuery(String.format(
					"update goods set name='%s',money=%f,typeName='%s',contact='%s',photoPath='%s',information='%s' where id=?",
					data.getName(), data.getMoney(), data.getTypeName(),
					data.getContact(), data.getPhotoPath(),
					data.getInformation()), new Object[] { id });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Good> getGoods(String[] conditions) {
		ArrayList<Good> datas = new ArrayList<Good>();
		String sql = "select * from goods ";
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

		sql += " order by releaseTime desc ";
		ResultSet c;
		try {
			c = executeQuery(sql, null);

			while (c.next()) {
				int id = c.getInt("id");
				String name = c.getString("name");
				double money = c.getDouble("money");
				String typeName = c.getString("typeName");
				String contact = c.getString("contact");
				String photoPath = c.getString("photoPath");
				String information = c.getString("information");
				int userId = c.getInt("userId");
				Good data = new Good(name, money, typeName, contact, photoPath,
						userId, information);
				data.setId(id);
				datas.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}

	public static ArrayList<Good> searchGoods(String key) {
		String condition = " name like '%" + key + "%' or money like '%" + key
				+ "%' or typeName like '%" + key + "%'";
		ArrayList<Good> datas = getGoods(new String[] { condition });
		return datas;
	}

	public static ArrayList<Good> getTypeOfGoods(String typeName) {
		return getGoods(new String[] { "typeName='" + typeName + "'" });
	}

	public static Good getGoodById(int id) {
		ArrayList<Good> datas = getGoods(
				new String[] { "id=" + String.valueOf(id) });
		return datas.get(0);
	}
}
