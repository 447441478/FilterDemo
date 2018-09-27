package cn.hncu.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取请求参数
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String strTime = request.getParameter("time");
		
		//按理这里应该访问service 这里模拟了
		if ( name != null && name.length()>0 && name.equals(pwd) ) { //如果用户名和密码一致就可以登录
			//把用户信息存入session
			request.getSession().setAttribute("user", name);
			
			//把用户名和密码放入cookie-autoLogin中，按理这些信息应该进行加密，这里略
			//放入前先进行编码
			name = URLEncoder.encode(name,"utf-8");
			pwd = URLEncoder.encode(pwd,"utf-8");
			Cookie cookie = new Cookie("autoLogin", name+"#"+pwd); //"$#$"为分割符
			
			//这里需要设置一下路径！！！ 权限控制。
			cookie.setPath(request.getContextPath());
			
			//设置cookie过期时间，单位秒。
			int secondOfDay = 60*60*24; //一天的秒数
			cookie.setMaxAge( secondOfDay* Integer.valueOf( strTime ) );
			
			response.addCookie(cookie);
		}else {
			//按理这里的返回信息应是service处理业务后的反馈，这里模拟了。
			request.getSession().setAttribute("error", "用户名不存在！！！");
		}
		//重定向到主页
		response.sendRedirect(request.getContextPath());
		
	}

}
