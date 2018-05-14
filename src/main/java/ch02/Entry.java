package ch02;

/**
 * 类路径
 * @author zengjia
 *
 */
public interface Entry {

	public final String pathListSeparator = ";";
	
	public byte[] readClass(String className);
	
	public String string();
}
