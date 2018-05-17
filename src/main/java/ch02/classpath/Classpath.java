package ch02.classpath;

import java.io.File;

import ch02.entry.Entry;
import ch02.factory.EntryFactory;

/**
 * 类加载路径
 */
public class Classpath {

	private Entry bootClasspath;
	
	private Entry extClasspath;
	
	private Entry userClasspath;
	
	public Classpath(String jreOption, String cpOption) throws Exception {
		this.parseBootAndExtClasspath(jreOption);
		this.parseUserClsspath(cpOption);
	}
	
	/***
	 * 设置bootClasspath和extClasspath
	 * 将系统运行环境下的classpath的lib下面所有jar全部设置到bootClasspath中
	 * 将系统运行环境下的classpath的lib/ext下面所有jar全部设置到bootClasspath中
	 * @param jreOption
	 * @throws Exception
	 */
	private void parseBootAndExtClasspath(String jreOption) throws Exception {
		String jreDir = getJreDir(jreOption);
		String jreLibPath = jreDir + "/lib/*";
		this.bootClasspath = EntryFactory.getEntry(jreLibPath);
		// jre/lib/exit/*
		String jreExtPath = jreDir + "/lib/ext/*";
		this.extClasspath = EntryFactory.getEntry(jreExtPath);
	}
	
	/**
	 * 将用户指定的classpath的所有jar全部这是到userClasspath
	 * @param cpOption
	 * @throws Exception
	 */
	private void parseUserClsspath(String cpOption) throws Exception {
		if (cpOption == null || "".equals(cpOption)) {
			cpOption = ".";
		}
		this.userClasspath = EntryFactory.getEntry(cpOption);
	}
	
	/**
	 * 获取系统运行时classpath，用户指定了就使用用户指定的
	 * 用户未指定，当前目录下有jre文件夹只用当前路径
	 * 当前路径也没有jre文件夹使用JAVA_HOME环境变量
	 * 都没有抛出Can not find jre folder!
	 * @param jreOption
	 * @return
	 * @throws Exception
	 */
	private String getJreDir(String jreOption) throws Exception {
		if (jreOption != null && !"".equals(jreOption)) {
			return jreOption;
		}
		if (exist("./jre")) {
			return "./jre";
		}
		String javaHome = System.getenv("JAVA_HOME");
		if(javaHome != null && !"".equals(javaHome)) {
			return javaHome + "/jre";
		}
		throw new RuntimeException("Can not find jre folder!");
	}
	
	/**
	 * 判断文件夹是否存在
	 * @param path
	 * @return
	 */
	private boolean exist(String path) {
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 读取class文件
	 * 读取顺序bootClasspath、extClasspath、userClasspath
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public byte[] readClass(String className) throws Exception {
		className = className + ".class";
		byte[] data = this.bootClasspath.readClass(className);
		if (data != null) {
			return data;
		}
		data = this.extClasspath.readClass(className);
		if (data != null) {
			return data;
		}
		return this.userClasspath.readClass(className);
	}
	
	public String string() {
		return this.userClasspath.string();
	}
}
