package cn.hncu.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FirstFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("FirstFilter初始化...");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("FirstFilter>>请求拦截...");
		chain.doFilter(request, response); //放行！！！
		System.out.println("FirstFilter>>响应拦截...");
	}

	@Override
	public void destroy() {
		System.out.println("FirstFilter销毁...");
	}

}
