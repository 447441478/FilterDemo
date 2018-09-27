package cn.hncu.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import cn.hncu.pub.WordsUtils;

public class WordsFilter implements Filter {


	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//这里采用装饰模式，把HttpServletRequest改装成 MyRequest,然后用MyRequest进行方行
		MyRequest myRequest = new MyRequest((HttpServletRequest) request);
		chain.doFilter(myRequest, response); //放行
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
class MyRequest extends HttpServletRequestWrapper{

	public MyRequest(HttpServletRequest request) {
		super(request);
	}
	
	//改装 getParameter()方法，按理应该把重载的函数都改装了，这里就意思一下
	@Override 
	public String getParameter(String name) {
		String src = super.getParameter(name);
		//调用敏感词汇过滤工具进行过滤
		String dest = WordsUtils.wordsFilter(src);
		return dest;
	}
	
	
	
}
