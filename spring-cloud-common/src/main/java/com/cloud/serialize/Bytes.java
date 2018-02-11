package com.cloud.serialize;
/**
 * @author summer
 */
public class Bytes {
	private static final char[] BASE16 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',	'f' };
	private static final int MASK4 = 0x0f;

	/**
	 * byte array copy.
	 * 
	 * @param src src.
	 * @param length new length.
	 * @return new byte array.
	 */
	public static byte[] copyOf(byte[] src, int length) {
		byte[] dest = new byte[length];
		System.arraycopy(src, 0, dest, 0, Math.min(src.length, length));
		return dest;
	}

	/**
	 * to hex string.
	 * 
	 * @param bs byte array.
	 * @return hex string.
	 */
	public static String bytes2hex(byte[] bs) {
		return bytes2hex(bs, 0, bs.length);
	}

	/**
	 * to hex string.
	 * 
	 * @param bs byte array.
	 * @param off offset.
	 * @param len length.
	 * @return hex string.
	 */
	public static String bytes2hex(byte[] bs, int off, int len) {
		if (off < 0) {
            throw new IndexOutOfBoundsException("bytes2hex: offset < 0, offset is " + off);
        }
		if (len < 0) {
            throw new IndexOutOfBoundsException("bytes2hex: length < 0, length is " + len);
        }
		if (off + len > bs.length) {
            throw new IndexOutOfBoundsException("bytes2hex: offset + length > array length.");
        }

		byte b;
		int r = off, w = 0;
		char[] cs = new char[len * 2];
		for (int i = 0; i < len; i++) {
			b = bs[r++];
			cs[w++] = BASE16[b >> 4 & MASK4];
			cs[w++] = BASE16[b & MASK4];
		}
		return new String(cs);
	}
}
