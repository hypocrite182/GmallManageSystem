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
				throw new RuntimeException(ex + "���ݿ�����ʧ��");
			}
		}
	} // ���췽��˽�л���������౻�������������ֻ��Ϊһ��������ʹ��

	public Connection getConnection() {
		return con; // Ҫ������ݿ����Ӷ���ֻ��Ҫ�������������ֱ�ӵ������getConnection��������
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
	} // ʹ�������ݿ���ʹ����������ͷ���Դ
	// ��������������close�������أ�Statement������ΪPreparedStatement������ʡ��ResultSet�����ȣ�
}
