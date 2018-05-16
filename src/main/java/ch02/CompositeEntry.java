package ch02;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 由更小的Entry组成，表示成Entry[]
 */
public class CompositeEntry implements Entry {
	
	private List<Entry> entryList = new ArrayList<>();
	
	public CompositeEntry(String pathList) {
		this(pathList, false);
	}
	
	public CompositeEntry(String pathList, boolean isWildcardEntry) {
		if (isWildcardEntry) {
			String bashDir = pathList.substring(0, pathList.length() - 1); // 去除*
			File file = new File(bashDir);
			getBashDirEntry(file);
		} else {
			String[] pathArray = pathList.split(PATH_LIST_SEPARATOR);
			for (int i = 0; i < pathArray.length; i ++) {
				Entry entry = EntryFactory.getEntry(pathArray[i]);
				entryList.add(entry);
			}
		}
	}
	
	// 遍历指定文件夹根目录下的文件  
    private void getBashDirEntry(File bashDir) {  
        // 每个文件夹遍历都会调用该方法  
        File[] files = bashDir.listFiles();  
  
        if (files == null || files.length == 0) {  
            return;  
        }
        for (File f : files) {
            if (f.isDirectory()) { 
            	// 如果是一个目录什么都不做，通配符不匹配子目录的JAR文件
            } else {  
                if (f.getName().endsWith(".jar") || f.getName().endsWith(".JAR")
                		|| f.getName().endsWith(".zip") || f.getName().endsWith(".ZIP")) {
                	Entry entry = new ZipEntry(f.getPath());
                	entryList.add(entry);
                }
            }  
        }
    }  

	@Override
	public byte[] readClass(String className) throws Exception {
		for (Entry entry : entryList) {
			byte[] data = entry.readClass(className);
			if (data != null) {
				return data;
			}
		}
		return null;
	}

	@Override
	public String string() {
		StringBuffer string = new StringBuffer();
		for (Entry entry : entryList) {
			string.append(entry.string()).append(PATH_LIST_SEPARATOR);
		}
		return string.substring(0, string.length() - 1);
	}

}
