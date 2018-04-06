package com.cloud.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;

/**
 * mainly for hbase rowid
 * @author acerge(acerge@163.com)
 * @since 7:43:31 PM Aug 13, 2015
 */
public class BinaryUtil {
	public static byte[] getBytes1(Integer... nums) {
		byte[] result = new byte[4 * nums.length];
		for (int i = 0; i < nums.length; i++) {
			byte[] src = toBytes(nums[i]);
			System.arraycopy(src, 0, result, i * 4, 4);
		}
		return result;
	}

	public static byte[] getBytes2(Long num1, Integer num2) {
		byte[] result = new byte[12];
		byte[] src = toBytes(num1);
		System.arraycopy(src, 0, result, 0, 8);
		byte[] src2 = toBytes(num2);
		System.arraycopy(src2, 0, result, 8, 4);
		return result;
	}
	
	public static byte[] getBytes3(Long num1, Long num2, Integer num3) {
		byte[] result = new byte[20];
		byte[] src = toBytes(num1);
		System.arraycopy(src, 0, result, 0, 8);
		byte[] src2 = toBytes(num2);
		System.arraycopy(src2, 0, result, 8, 8);
		byte[] src3 = toBytes(num3);
		System.arraycopy(src3, 0, result, 16, 4);
		return result;
	}

	public static String getIdHex(Long id) {
		return Hex.encodeHexString(toBytes(id));
	}

	public static Long convertIdHexToLong(String idHex) throws DecoderException {
		byte[] hexbytes = Hex.decodeHex(idHex.toCharArray());
		return ByteBuffer.wrap(hexbytes).asLongBuffer().get();
	}

	public static byte[] toBytes(long val) {
		byte[] b = new byte[8];
		for (int i = 7; i > 0; i--) {
			b[i] = (byte) val;
			val >>>= 8;
		}
		b[0] = (byte) val;
		return b;
	}

	/**
	 * Convert an int value to a byte array. Big-endian. Same as what
	 * DataOutputStream.writeInt does.
	 * 
	 * @param val
	 *            value
	 * @return the byte array
	 */
	public static byte[] toBytes(int val) {
		byte[] b = new byte[4];
		for (int i = 3; i > 0; i--) {
			b[i] = (byte) val;
			val >>>= 8;
		}
		b[0] = (byte) val;
		return b;
	}

}
