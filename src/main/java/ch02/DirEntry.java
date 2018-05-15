package ch02;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 目录形式的类路径
 */
public class DirEntry implements Entry {
	
	/** 目录的绝对路径 */
	private String absDir;
	
	public DirEntry(String path) {
		File absDirPath = new File(path);
		this.absDir = absDirPath.toString();
	}

	/**
	 * 寻找和加载class文件
	 * @param className 文件名称 示例：Test.class
	 * @return class文件的字节数组
	 */
	public byte[] readClass(String className) throws Exception {
		File file = new File(this.absDir + "/" + className);
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
	}

	/**
	 * 返回变量的字符串表示
	 * @return 字符串格式的变量
	 */
	public String string() {
		return this.absDir;
	}
	
	public static void main(String[] args) {
		DirEntry de = new DirEntry("D:\\zengjia");
		try {
			byte[] bs = de.readClass("Cmd.class");
			for (byte b : bs) {
				String hex = Integer.toHexString(b & 0xFF);
	            if (hex.length() == 1)
	            {
	                hex = '0' + hex;
	            }
	            System.out.print(hex.toUpperCase() + " ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
