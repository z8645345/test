package ch02;
public class Cmd {
	private boolean helpFlag;
	private boolean versionFlag;
	private String cpOption;
	private String xJreOption;
	private String clazz;
	private String[] args;
	
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
		if (args.length > 0) {
			args0 = args[0];
			if (args0.equals("-help") || args[0].equals("-?")) {
				this.helpFlag = true;
			} else if (args0.equals("-version")) {
				this.versionFlag = true;
			} else {
				int i = 0;
				if (args0.equals("-classpath") || args0.equals("-cp")) {
					this.cpOption = args[1];
					if ("-xJreOption".equals(args[2])) {
						xJreOption = args[3];
						i = 4;
					} else {
						i = 2;
					}
				} else if ("-xJreOption".equals(args0)) {
					xJreOption = args[1];
					i = 2;
				}
				this.clazz = args[i];
				if (args.length > i + 1) {
					this.args = new String[args.length - (i + 1)];
					for (int j = 0; j < this.args.length; j ++) {
						this.args[j] = args[i + 1 + j];	
					}
				}
			}
		}
	}
	
	public void printUsage() {
		System.out.println("Usage: " + args0 + " [-options] class [args...]");
	}
	
	public void startJVM() {
		StringBuffer argsStr = new StringBuffer();
		if (this.args != null && this.args.length > 0) {
			for (String arg : this.args) {
				argsStr.append(arg).append(", ");
			}
		}
		try {
			Classpath classpath = new Classpath(xJreOption, cpOption);
			byte[] data = classpath.readClass(this.clazz);
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