package cn.hncu.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class Gzip2Filter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//在这里需要把 response 换成MyResponse, response.getOutputStream 换成 MyOutputStream
		//这里我们知道 response 对象其实是 HttpServletResponse类对象，所有强转以便后面操作。
		HttpServletResponse resp = (HttpServletResponse) response;
		
		MyResponse myResponse = new MyResponse(resp);
		//放行参数的用myResponse
		chain.doFilter(request, myResponse); //放行
		//能到这里说明servlet 已经运行完 doGet/doPost 方法
		//这时候可以 通过 myResponse获取封装在它内部的 字节内存流对象，把原数据获取出来进行压缩
		 ByteArrayOutputStream baos = myResponse.getBaos();
		 byte[] src = baos.toByteArray();
		 
		 System.out.println( "压缩前长度："+src.length );
		 
		 //////////下面开始进行字节数据压缩///////////
		 //1 创建一个字节内存流，用来存放压缩后的数据
		 ByteArrayOutputStream destBaos = new ByteArrayOutputStream();
		 //2 创建 GZIPOutputStream,并且把 创建的字节内存流传过去，接收压缩后的数据
		 GZIPOutputStream gzipOs = new GZIPOutputStream(destBaos);
		 //3 把 原字节数据写入 gzipOs 进行压缩
		 gzipOs.write(src);
		 //4 写完一定要刷缓存！！！！！！！！
		 gzipOs.close(); //gzipOs.flush(); //调close()内部自动刷缓存，而且还可以防止内存泄漏
		 //5 从存储 压缩完的数据的 destBaos中获取数据写给客户端
		 byte[] dest = destBaos.toByteArray();
		 System.out.println( "压缩后长度："+dest.length );
		 //6 写给客户端前 一定要设置响应头 ！！！  --- 注意 写给客户端时用的是：response对象
		 resp.setHeader("Content-Encoding", "gzip");
		 //7通过字节流输出给客户端.
		 resp.getOutputStream().write(dest);
	}

	@Override
	public void destroy() {
	}
	
	class MyResponse extends HttpServletResponseWrapper{
		//用来存储 servlet中 write时的原数据
		private ByteArrayOutputStream baos;
		
		private PrintWriter writer;
		
		//有个细节 当访问的是 jsp文件时，需要把writer中的数据刷一下，因为jspWriter没有自带自动刷缓存///////
		public ByteArrayOutputStream getBaos() {
			writer.close();
			return baos;
		}
		
		public MyResponse(HttpServletResponse response) {
			super(response);
			baos = new ByteArrayOutputStream();
		}

		
		//////////把原装的PrintWriter的类对象 替换掉//////////
		@Override
		public PrintWriter getWriter() throws IOException {
			//这里设置自带刷缓存只对Servlet中的字符输出流有效，对jsp的字符输出流是无法影响的。
			writer = new PrintWriter( baos,true);
			return writer;
		}
		/////////////////////////////////////////////////
	}
}

