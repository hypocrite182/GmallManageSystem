package com.gmall.Controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmall.Models.Good;
import com.gmall.Models.User;
import com.gmall.Models.UserAssist;
import com.gmall.Utils.CurrentServletUtil;
import com.gmall.Utils.DataToJsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CurrentServletUtil.servletContext=this.getServletContext();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String flag=request.getParameter("flag");
		if(flag.equals("getUserByNameAndPassword")) {
			String userName = request.getParameter("name");
			String userPassword = request.getParameter("password");
			User u=UserAssist.getUserByNameAndPassword(userName, userPassword);
			response.getWriter().write(DataToJsonUtil.userToJson(u));
			return;
		}
		if (flag.equals("getUserById")) {
			int id=Integer.parseInt(request.getParameter("id"));
			User u=UserAssist.getUserById(id);
			String result=DataToJsonUtil.userToJson(u);
			response.getWriter().write(result);
			return;
		}
		if(flag.equals("getUserByName")) {
			String name=request.getParameter("name");
			User u=UserAssist.getUserByName(name);
			response.getWriter().write(DataToJsonUtil.userToJson(u));
			return;
		}
		if(flag.equals("delete")) {
			int id=Integer.parseInt(request.getParameter("id"));
			UserAssist.delete(id);
			return;
		}
		if(flag.equals("update")) {
			String password=request.getParameter("password");
			String phone=request.getParameter("phone");
			String gender=request.getParameter("gender");
			String school=request.getParameter("school");
			String headPhotoPath=request.getParameter("headPhotoPath");
			int id=Integer.parseInt(request.getParameter("id"));
			UserAssist.update(id, new User("", password, phone, gender, school, headPhotoPath));
			return;
		}
		if(flag.equals("insert")) {
			String name=request.getParameter("name");
			String password=request.getParameter("password");
			String phone=request.getParameter("phone");
			String gender=request.getParameter("gender");
			String school=request.getParameter("school");
			String headPhotoPath=request.getParameter("headPhotoPath");
			User u=new User(name, password, phone, gender, school, headPhotoPath);
			UserAssist.insert(u);
			return;
		}	
	}
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CurrentServletUtil.servletContext=this.getServletContext();
		doGet(request, response);
	}

}
