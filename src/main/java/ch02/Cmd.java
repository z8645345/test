package ch02;

import ch02.classpath.Classpath;

public class Cmd {
	/** 是否显示帮助 */
	private boolean helpFlag;
	/** 是否显示版本号 */
	private boolean versionFlag;
	/** 用户指定的classpath路径 */
	private String cpOption;
	/** 运行环境的classpath路径，一般是环境变量JAVA_HOME */
	private String xJreOption;
	/** 要加载的类名不带.class */
	private String clazz;
	/** 参数列表 */
	private String[] args;
	
	/** 第一个参数 */
	private String args0;
	
	public boolean getHelpFlag() {
		return this.helpFlag;	
	}
	
	public boolean getVersionFlag() {
		return this.versionFlag;	
	}
	
	public String getClazz() {
		return this.clazz;
	}
	
	public Cmd(String[] args) {
		try {
			if (args.length > 0) {
				args0 = args[0];
				if (args0.equals("-help") || args[0].equals("-?")) { // 如果第一个参数是-help/? 则打印帮助信息，例如：java -help
					this.helpFlag = true;
				} else if (args0.equals("-version")) { // 如果第一个参数是-version 则打印版本信息, 例如：java -version
					this.versionFlag = true;
				} else {
					int i = 0;
					if (args0.equals("-classpath") || args0.equals("-cp")) { // 如果第一个参数是-classpath/cp则表示指定用户classpath路径
						this.cpOption = args[1];
						if ("-xJreOption".equals(args[2])) { // 如果第一个参数是-classpath/cp 第二个参数是-xJreOption，则表示指定运行环境classpath路径
							xJreOption = args[3];
							i = 4;
						} else {
							i = 2;
						}
					} else if ("-xJreOption".equals(args0)) { // 如果第一个参数是-xJreOption则表示指定用户classpath路径
						xJreOption = args[1];
						i = 2;
					}
					this.clazz = args[i];
					if (args.length > i + 1) { // 所以系统指令以外的参数 都作为用户输入参数列表
						this.args = new String[args.length - (i + 1)];
						for (int j = 0; j < this.args.length; j ++) {
							this.args[j] = args[i + 1 + j];	
						}
					}
				}
			}
		} catch (Exception e) {
			printUsage();
		}
	}
	
	public void printUsage() {
		System.out.println("Usage: " + args0 + " [-options] class [args...]");
	}
	
	public void startJVM() {
		try {
			// 1.获取所有Classpath
			Classpath classpath = new Classpath(xJreOption, cpOption);
			// 2.获取指定在所有Classpath中获取指定类型的数据
			String className = this.clazz.replace(".", "/");
			byte[] data = classpath.readClass(className);
			if (data == null) {
				System.err.println("not found class: " + this.clazz);
			} else {
				for (byte b : data) {
					String hex = Integer.toHexString(b & 0xFF);
		            if (hex.length() == 1)
		            {
		                hex = '0' + hex;
		            }
		            System.out.print(hex.toUpperCase() + " ");
				}
			}
		} catch (Exception e) {
			System.err.println("start final");
			e.printStackTrace();
		}
	}
}