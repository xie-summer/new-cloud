package com.cloud.serialize;

import java.io.IOException;
/**
 * @author summer
 */
public interface DataOutput {

	/**
	 * Write boolean.
	 * 
	 * @param v value.
	 * @throws IOException
	 */
	void writeBool(boolean v) throws IOException;

	/**
	 * Write byte.
	 * 
	 * @param v value.
	 * @throws IOException
	 */
	void writeByte(byte v) throws IOException;

	/**
	 * Write short.
	 * 
	 * @param v value.
	 * @throws IOException
	 */
	void writeShort(short v) throws IOException;

	/**
	 * Write integer.
	 * 
	 * @param v value.
	 * @throws IOException
	 */
	void writeInt(int v) throws IOException;

	/**
	 * Write long.
	 * 
	 * @param v value.
	 * @throws IOException
	 */
	void writeLong(long v) throws IOException;

	/**
	 * Write float.
	 * 
	 * @param v value.
	 * @throws IOException
	 */
	void writeFloat(float v) throws IOException;

	/**
	 * Write double.
	 * 
	 * @param v value.
	 * @throws IOException
	 */
	void writeDouble(double v) throws IOException;

	/**
	 * 
	 * @param v value.
	 * @throws IOException
	 */
	void writeUTF(String v) throws IOException;

	/**
	 * Write byte array.
	 * 
	 * @param v value.
	 * @throws IOException
	 */
	void writeBytes(byte[] v) throws IOException;

	/**
	 * Write byte array.
	 * 
	 * @param v value.
	 * @param off offset.
	 * @param len length.
	 * @throws IOException
	 */
	void writeBytes(byte[] v, int off, int len) throws IOException;

	/**
	 * Flush buffer.
	 * 
	 * @throws IOException
	 */
	void flushBuffer() throws IOException;
}