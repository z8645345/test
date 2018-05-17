package ch03;

import ch03.Cmd;

public class Main {
	
	public static void main(String[] args) {
		Cmd cmd = new Cmd(args);
		if (cmd.getVersionFlag()) {
			System.out.println("version 0.0.1");	
		} else if (cmd.getHelpFlag() || "".equals(cmd.getClazz())) {
			cmd.printUsage();	
		} else {
			cmd.startJVM();	
		}
	}
}