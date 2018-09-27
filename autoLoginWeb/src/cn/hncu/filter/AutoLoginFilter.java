package cn.hncu.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class AutoLoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override //帮助用户进行自动登录
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		//当用户打开浏览器访问本系统时
		if( req.getSession().getAttribute("user") == null ) {
			//获取cookies
			Cookie[] cookies = req.getCookies();
			if( cookies != null ) {
				//遍历cookies
				for (Cookie cookie : cookies) {
					//当存在存放用户登录信息的 cookie 时
					if( "autoLogin".equals(cookie.getName()) ) {
						System.out.println(cookie.getValue());
						String[] values = cookie.getValue().split("#");
						String name = null;
						String pwd = null;
						System.out.println(  values.length );
						if( values.length == 2 ) {
							//进行数据解码
							name = URLDecoder.decode(values[0], "utf-8");
							pwd = URLDecoder.decode(values[1], "utf-8");
						}
						System.out.println(name+","+pwd);
						//按理这里应该访问service进行登录处理，这里模拟了
						if( name != null && name.equals(pwd) ) {
							req.getSession().setAttribute("user", name);
						}
						break;
					}
				}
			}
		}
		//无论如何这里都要放行，因为这个过滤器只是帮助用户自动登录！！！
		chain.doFilter(request, response); //放行
	}

	@Override
	public void destroy() {
	}

}
