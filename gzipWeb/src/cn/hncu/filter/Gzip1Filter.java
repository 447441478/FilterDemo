package cn.hncu.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class Gzip1Filter implements Filter{

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
		public ByteArrayOutputStream getBaos() {
			return baos;
		}
		
		public MyResponse(HttpServletResponse response) {
			super(response);
			baos = new ByteArrayOutputStream();
		}
		
		//////////把原装的ServletOutputStream的类对象 改装成MyServletOutputStream类对象//////////
		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			MyServletOutputStream myServletOutputStream = new MyServletOutputStream( super.getOutputStream(),baos);
			return myServletOutputStream;
		}
		//////////////////////////////////////////////////////////////////////
	}
	class MyServletOutputStream extends ServletOutputStream{
		private ServletOutputStream sos;
		//用来存储 servlet中 write时的原数据
		private ByteArrayOutputStream baos;

		protected MyServletOutputStream(ServletOutputStream sos,ByteArrayOutputStream baos) {
			this.sos = sos;
			this.baos = baos;
		}

		@Override
		public boolean isReady() {
			return sos.isReady();
		}

		@Override
		public void setWriteListener(WriteListener arg0) {
			sos.setWriteListener(arg0);
		}
		
		//////////把write方法修改了////////
		@Override
		public void write(int b) throws IOException {
			baos.write(b);
		}
		///////////////////////////////
		
	}
}

