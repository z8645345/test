package ch02;

/**
 * EntryFactor工场
 */
public class EntryFactory {

	/**
	 * 根据参数创建不同类型的Entry
	 * @param path
	 * @return Entry对象
	 */
	public static Entry getEntry(String path) {
		if (path.contains(Entry.PATH_LIST_SEPARATOR)) {
			return new CompositeEntry(path);
		}
		if (path.endsWith("*")) {
			return new WildcardEntry(path);
		}
		if (path.endsWith(".jar") || path.endsWith(".JAR") 
				|| path.endsWith(".zip") || path.endsWith(".ZIP")) {
			return new ZipEntry(path);
		}
		return new DirEntry(path);
	}
}
