package ch02;

import java.io.File;

/**
 * 类路径
 * @author zengjia
 *
 */
public interface Entry {

	/**
	 * 当前操作系统文件路径分隔符
	 */
	public static final String PATH_LIST_SEPARATOR = File.separator;
	
	/**
	 * 寻找和加载class文件
	 * @param className 文件名称 示例：Test.class
	 * @return class文件的字节数组
	 */
	public byte[] readClass(String className) throws Exception;
	
	/**
	 * 返回变量的字符串表示
	 * @return 字符串格式的变量
	 */
	public String string();
}
