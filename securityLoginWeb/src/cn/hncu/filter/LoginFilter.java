package cn.hncu.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//强转为 Htpp的为后面操作方便
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		System.out.println("11111111");
		//当用户访问是需要进行拦截，只有用户登录后才可以访问。
		Object name = req.getSession().getAttribute("name");
		if( name != null ) { //当name不等于null时说明用户已经登录。放行！！！
			chain.doFilter(request, response); //放行
		}else { //如果用户没有登录，则提回主页。
			resp.sendRedirect(req.getContextPath());
		}
	}

	@Override
	public void destroy() {
	}

}
