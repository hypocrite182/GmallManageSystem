package com.gmall.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmall.Models.Good;
import com.gmall.Models.GoodAssist;
import com.gmall.Utils.CurrentServletUtil;
import com.gmall.Utils.DataToJsonUtil;

/**
 * Servlet implementation class GoodController
 */
@WebServlet("/GoodController")
public class GoodController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CurrentServletUtil.servletContext=this.getServletContext();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String flag=request.getParameter("flag");
		if(flag.equals("insert")) {
			String name=request.getParameter("name");
			double money=Double.parseDouble(request.getParameter("money"));
			String typeName=request.getParameter("typeName");
			String contact=request.getParameter("contact");
			String photoPath=request.getParameter("photoPath");
			String information=request.getParameter("information");
			int userId=Integer.parseInt(request.getParameter("userId"));
			Good g=new Good(name, money, typeName, contact, photoPath, userId, information);
			GoodAssist.insert(g);
			return;
		}
		if(flag.equals("delete")) {
			int id=Integer.parseInt(request.getParameter("id"));
			GoodAssist.delete(id);
			return;
		}
		if(flag.equals("update")) {
			int id=Integer.parseInt(request.getParameter("id"));
			String name=request.getParameter("name");
			double money=Double.parseDouble(request.getParameter("money"));
			String typeName=request.getParameter("typeName");
			String contact=request.getParameter("contact");
			String photoPath=request.getParameter("photoPath");
			String information=request.getParameter("information");
			Good data=new Good(name, money, typeName, contact, photoPath, 0, information);
			GoodAssist.update(id, data);
			return;
		}
		if(flag.equals("getGoods")) {
			ArrayList<Good> datas=GoodAssist.getGoods(null);
			String result=DataToJsonUtil.goodsToJson(datas);
			response.getWriter().write(result);
			return;
		}
		if(flag.equals("searchGoods")) {
			String key=request.getParameter("key");
			ArrayList<Good> datas=GoodAssist.searchGoods(key);
			response.getWriter().write(DataToJsonUtil.goodsToJson(datas));
			return;
		}
		if(flag.equals("getTypeOfGoods")) {
			String typeName=request.getParameter("typeName");
			ArrayList<Good> datas=GoodAssist.getTypeOfGoods(typeName);
			response.getWriter().write(DataToJsonUtil.goodsToJson(datas));
			return;
		}
		if(flag.equals("getGoodById")) {
			int id=Integer.parseInt(request.getParameter("id"));
			Good data=GoodAssist.getGoodById(id);
			ArrayList<Good> datas=new ArrayList<>();
			datas.add(data);
			response.getWriter().write(DataToJsonUtil.goodsToJson(datas));
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
