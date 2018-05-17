package ch03;

public class ClassFile {

	// uint32 magic
	uint16 minorVersion;
	uint16 majorVersion;
	ConstanPool constantPool;
	uint16 accessFlags;
	uint16 thisClass;
	uint16 superClass;
	uint16[] interfaces;
	MemberInfo[] fields;
	MemberInfo[] methods;
	AttributeInfo[] attributes;
	
	public ClassFile(byte[] classData) {
		ClassReader cr = new ClassReader(classData);
		this.read(cr);
	}
	
	public void read(ClassReader reader) {
		this.readAndCheckMagic(reader);
		this.readAndCheckVersion(reader);
		this.constantPool = readConstantPool(reader);
		this.accessFlags = reader.readUint16();
		this.thisClass = reader.readUint16();
		this.superClass = reader.readUint16();
		this.interfaces = reader.readUint16s();
		this.fields = readMembers(reader, this.constantPool);
		this.methods = readMembers(reader, this.constantPool);
		this.attributes = readAttributes(reader, this.constantPool);
	}
	
	public void readAndCheckMagic(ClassReader reader) {
		uint32 magic = reader.readUint32();
		if (0xCAFEBABE != magic) {
			System.err.println("java.lang.ClassFormatError:magic!");
			System.exit(-1);
		}
	}
	
	public void readAndCheckVersion(ClassReader reader) {
		
	}
	
	public uint16 getMinorVersion() {
		return this.minorVersion;
	}
	
	public uint16 getMajorVersion() {
		return this.majorVersion
	}
	
	public ConstantPool getConstantPool() {
		return this.constantPool;
	}
	
	public uint16 getAccessFlags() {
		return this.accessFlags;
	}
	
	public MemberInfo[] getFields() {
		return this.fields;
	}
	
	public MemberInfo[] getMethods() {
		return this.methods;
	}
	
	public String getClassName() {
		return this.constantPool.getClassName(this.thisClass);
	}
	
	public String getSuperClassName() {
		if (this.superClass > 0) {
			return this.constantPool.getClassName(this.superClass);
		}
		return ""; // 只有java.lang.Object没有父类
	}
	
	public String[] getInterfaceNames() {
		String[] interfaceNames = new String[this.interfaces.length];
		for (int i = 0; i < this.interfaces.length; i ++) {
			uint16 cpIndex = this.interfaces[i];
			interfaceNames[i] = this.constantPool.getClassName(cpIndex);
		}
		return interfaceNames;
	}
}
