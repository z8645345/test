package ch02.factory;

import ch02.entry.Entry;
import ch02.entry.impl.CompositeEntry;
import ch02.entry.impl.DirEntry;
import ch02.entry.impl.ZipEntry;

/**
 * EntryFactor工场
 */
public class EntryFactory {

	/**
	 * 根据参数创建不同类型的Entry
	 * 如果多个路径使用;隔开使用CompositeEntry(path)（其中每个的都是*通配）
	 * 如果使用*通配使用CompositeEntry(path)
	 * 如果是.jar或者.zip结尾的使用ZipEntry(path)
	 * 其他情况使用DirEntry(path)
	 * com.zengjia.test.*;com.zengjia.test1.* 使用CompositeEntry(path, true)
	 * com.zengjia.test.*使用CompositeEntry(path)
	 * com.zengjia.test.a.jar/a.zip使用ZipEntry(path)
	 * com.zengjia.test使用DirEntry(path)
	 * @param path
	 * @return Entry对象
	 */
	public static Entry getEntry(String path) {
		if (path.contains(Entry.PATH_LIST_SEPARATOR)) {
			return new CompositeEntry(path);
		}
		if (path.endsWith("*")) {
			return new CompositeEntry(path, true);
		}
		if (path.endsWith(".jar") || path.endsWith(".JAR") 
				|| path.endsWith(".zip") || path.endsWith(".ZIP")) {
			return new ZipEntry(path);
		}
		return new DirEntry(path);
	}
}
