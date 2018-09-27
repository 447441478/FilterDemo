package cn.hncu.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterFilter implements Filter{
	//字符编码
	private String charset;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("CharacterFilter初始化...");
		//读取web.xml配置初识化参数
		charset = config.getInitParameter("charset");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//进行请求的编码设置
		request.setCharacterEncoding(charset);
		//进行响应内容的编码设置
		response.setContentType("text/html;charset="+charset);
		System.out.println("CharacterFilter>>请求拦截...");
		chain.doFilter(request, response); //放行！！！
		System.out.println("CharacterFilter>>响应拦截...");
	}

	@Override
	public void destroy() {
		System.out.println("CharacterFilter销毁...");
	}

}
