package com.cloud.serialize;

import java.io.IOException;
/**
 * @author summer
 */
public interface DataInput {

	/**
	 * Read boolean.
	 * 
	 * @return boolean.
	 * @throws
	 */
	boolean readBool() throws IOException;

	/**
	 * Read byte.
	 * 
	 * @return byte value.
	 * @throws
	 */
	byte readByte() throws IOException;

	/**
	 * Read short integer.
	 * 
	 * @return short.
	 * @throws
	 */
	short readShort() throws IOException;

	/**
	 * Read integer.
	 * 
	 * @return integer.
	 * @throws
	 */
	int readInt() throws IOException;

	/**
	 * Read long.
	 * 
	 * @return long.
	 * @throws
	 */
	long readLong() throws IOException;

	/**
	 * Read float.
	 * 
	 * @return float.
	 * @throws
	 */
	float readFloat() throws IOException;

	/**
	 * Read double.
	 * 
	 * @return double.
	 * @throws
	 */
	double readDouble() throws IOException;

	/**
	 * Read UTF-8 string.
	 * 
	 * @return string.
	 * @throws
	 */
	String readUTF() throws IOException;

	/**
	 * Read byte array.
	 * 
	 * @return byte array.
	 * @throws
	 */
	byte[] readBytes() throws IOException;
}