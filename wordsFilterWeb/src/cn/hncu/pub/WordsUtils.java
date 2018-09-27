package cn.hncu.pub;

import java.util.ArrayList;
import java.util.List;

/**
 * &emsp;&emsp;处理敏感词的工具
 * <br/><br/><b>CreateTime:</b><br/>&emsp;&emsp;&emsp; 2018年9月26日 下午12:25:11	
 * @author 宋进宇&emsp;<a href='mailto:447441478@qq.com'>447441478@qq.com</a>
 */
public class WordsUtils {
	private static List<String> list;
	static {
		//按理这里应该是从数据库读取出所有敏感词汇，这里就模拟了
		list = new ArrayList<String>();
		list.add("SB");
		list.add("骂人");
		list.add("习大大");
	}
	//对外关闭构造函数
	private WordsUtils() {
	}
	/**
	 * 获取敏感词汇集
	 * @return
	 */
	public static List<String> getWords(){
		return list;
	}
	/**
	 * 重新加载敏感词汇库
	 */
	public static void rebuild() {
		//按理这里应该访问service-->dao-->数据库，读取数据，这里模拟了
		System.out.println("敏感词汇加载完毕.");
	}
	/**
	 * 把敏感词汇进行过滤
	 * @param src 原数据
	 * @return 过滤过的数据
	 */
	public static String wordsFilter(String src) {
		for (String word : list) {
			src = src.replaceAll(word, "<font color='gray'>[和谐]</font>");
		}
		return src;
	}
	
}
