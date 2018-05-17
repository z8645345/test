package ch02.entry.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import ch02.entry.Entry;

/**
 * 目录形式的类路径
 */
public class DirEntry implements Entry {
	
	/** 目录的绝对路径 */
	private String absDir;
	
	public DirEntry(String path) {
		File absDirPath = new File(path);
		this.absDir = absDirPath.getAbsolutePath();
	}

	/**
	 * 寻找和加载class文件
	 * @param className 文件名称 示例：Test.class
	 * @return class文件的字节数组
	 */
	public byte[] readClass(String className) throws Exception {
		File file = new File(this.absDir + "/" + className);
		System.out.println(file.getAbsolutePath());
		if (file.exists()) {
			try (FileInputStream fis = new FileInputStream(file);
					ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);) {
				byte[] b = new byte[1000];  
	            int n;  
	            while ((n = fis.read(b)) != -1) {  
	                bos.write(b, 0, n);  
	            }
	            return bos.toByteArray();
			} catch (Exception e) {
				throw e;
			}
		} else {
			return null;
		}
	}

	/**
	 * 返回变量的字符串表示
	 * @return 字符串格式的变量
	 */
	public String string() {
		return this.absDir;
	}
	
}
