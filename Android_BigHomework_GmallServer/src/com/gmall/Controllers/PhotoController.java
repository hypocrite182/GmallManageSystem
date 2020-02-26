package com.gmall.Controllers;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PhotoController
 */
@WebServlet("/PhotoController")
public class PhotoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static String photoName;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String photo=request.getParameter("photo");
		String flag=request.getParameter("flag");
		if(flag.equals("download")) {
			FileInputStream fis=new FileInputStream("D:\\phonepicure\\"+photo);
			byte[] b = new byte[1024*1024*10];
			int len = -1;
			while((len = fis.read(b)) != -1) {
				response.getOutputStream().write(b,0,len);
			}
			fis.close();
		}
		if(flag.equals("upload")) {
			this.photoName=photo;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletInputStream in=request.getInputStream();
		FileOutputStream fos=new FileOutputStream("D:\\phonepicure\\"+this.photoName,true);
		byte[] data=new byte[10*1024*1024];
		int len=-1;
		while((len=in.read(data))!=-1) {
			fos.write(data,0,len);
		}
		fos.close();
	}

}
