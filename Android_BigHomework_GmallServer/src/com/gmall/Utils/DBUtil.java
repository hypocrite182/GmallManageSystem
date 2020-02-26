package com.gmall.Utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.mysql.jdbc.*;

public class DBUtil {
	private static Connection con;

	public DBUtil(ServletContext sc) {
		if (con == null) {
			try {
				Class.forName(sc.getInitParameter("DriverName"));
				String URL = sc.getInitParameter("DatabaseUrl");
				String user = sc.getInitParameter("UserName");
				String password = sc.getInitParameter("UserPassword");
				con = (Connection) DriverManager.getConnection(URL, user, password);
			} catch (Exception ex) {
				throw new RuntimeException(ex + "数据库连接失败");
			}
		}
	} // 构造方法私有化避免这个类被创建对象，这个类只作为一个工具类使用

	public Connection getConnection() {
		return con; // 要获得数据库连接对象，只需要利用这个工具类直接调用这个getConnection方法即可
	}

	public void close(Connection con, Statement stat, ResultSet rs) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException se) {
			}
		}
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException se) {
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException se) {
			}
		}
	} // 使用完数据库再使用这个方法释放资源
	// （可以再添加这个close方法重载，Statement参数换为PreparedStatement，或者省略ResultSet参数等）
}
