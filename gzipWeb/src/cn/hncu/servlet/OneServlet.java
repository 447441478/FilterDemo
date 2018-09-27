package cn.hncu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class OneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		for( int i =0; i< 1000;i++) {
			sb.append("Hello world!");
		}
		byte[] src = sb.toString().getBytes("utf-8");
		//response 其实是 cn.hncu.filter.MyResponse类的myResponse对象
		ServletOutputStream out = response.getOutputStream();
		//out 其实是  cn.hncu.filter.MyServletOutputStream类的  myServletOutputStream对象
		out.write(src);
		
	}

}
