package ch02;

import java.io.File;

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
	
	private void parseBootAndExtClasspath(String jreOption) throws Exception {
		String jreDir = getJreDir(jreOption);
		String jreLibPath = jreDir + "/lib/*";
		this.bootClasspath = EntryFactory.getEntry(jreLibPath);
		// jre/lib/exit/*
		String jreExtPath = jreDir + "/lib/ext/*";
		this.extClasspath = EntryFactory.getEntry(jreExtPath);
	}
	
	private void parseUserClsspath(String cpOption) throws Exception {
		if (cpOption == null || "".equals(cpOption)) {
			cpOption = ".";
		}
		this.userClasspath = EntryFactory.getEntry(cpOption);
	}
	
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
