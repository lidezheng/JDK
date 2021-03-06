// This file is an automatically generated file, please do not edit this file, modify the WrapperGenerator.java file instead !

package sun.awt.X11;

import jdk.internal.misc.Unsafe;

import sun.util.logging.PlatformLogger;
public class XIMValuesList extends XWrapperBase { 
	private Unsafe unsafe = XlibWrapper.unsafe; 
	private final boolean should_free_memory;
	public static int getSize() { return 16; }
	public int getDataSize() { return getSize(); }

	long pData;

	public long getPData() { return pData; }


	public XIMValuesList(long addr) {
		log.finest("Creating");
		pData=addr;
		should_free_memory = false;
	}


	public XIMValuesList() {
		log.finest("Creating");
		pData = unsafe.allocateMemory(getSize());
		should_free_memory = true;
	}


	public void dispose() {
		log.finest("Disposing");
		if (should_free_memory) {
			log.finest("freeing memory");
			unsafe.freeMemory(pData); 
	}
		}
	public short get_count_values() { log.finest("");return (Native.getShort(pData+0)); }
	public void set_count_values(short v) { log.finest(""); Native.putShort(pData+0, v); }
	public long get_supported_values(int index) { log.finest(""); return Native.getLong(pData+8)+index*Native.getLongSize(); }
	public long get_supported_values() { log.finest("");return Native.getLong(pData+8); }
	public void set_supported_values(long v) { log.finest(""); Native.putLong(pData + 8, v); }


	String getName() {
		return "XIMValuesList"; 
	}


	String getFieldsAsString() {
		StringBuilder ret = new StringBuilder(80);

		ret.append("count_values = ").append( get_count_values() ).append(", ");
		ret.append("supported_values = ").append( get_supported_values() ).append(", ");
		return ret.toString();
	}


}



