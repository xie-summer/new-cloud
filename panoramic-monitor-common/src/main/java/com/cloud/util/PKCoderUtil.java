package com.cloud.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
/**
 * @author summer
 */
public final class PKCoderUtil {
	private static transient String Algorithm = "DES"; // 定义 加密算法,可用 DES,DESede(TripleDES),Blowfish
	private static transient byte[] DEFAULT_KEY={-72,-16,-79,-22,-79,-32,-48,-76};
	private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(PKCoderUtil.class);

	public static final String encodePK(long pk){
		return encodePK(pk, DEFAULT_KEY);
	}
	public static final String encodePK(Long pk, byte[] skey){
		byte[] bytes = long2bytes(pk);
		byte[] encoded = encode(bytes, skey);
		String result = byte2hex(encoded);
		return result;
	}
	private static final byte[] long2bytes(long num){
		//舍弃最高位一个字节
		byte[] b=new byte[7];
		for(int i=1;i<8;i++){
			b[i-1]=(byte)(num>>>(56-i*8));
		}
		return b;
	}
	public static final String encodePK(String pkStr){
		Long pk = Long.valueOf(pkStr);
		return encodePK(pk);
	}
	public static final String encodeString(String original, byte[] skey){
		String result = null;
		byte[] pkbytes;
		try {
			pkbytes = original.getBytes("gbk");
			byte[] encoded = encode(pkbytes, skey);
			result = byte2hex(encoded);
		} catch (Exception e) {
			DB_LOGGER.error(original + LoggerUtils.getExceptionTrace(e, 10));
		}
		return result;
	}
	public static final String encodeString(String original){
		return encodeString(original, DEFAULT_KEY);
	}
	public static final String decodeString(String encrypt){
		return decodeString(encrypt, DEFAULT_KEY);
	}
	public static final String decodeString(String encodedHex, byte[] skey){
		byte[] encoded = hex2byte(encodedHex);
		byte[] decoded = decode(encoded,skey);
		try {
			String s = new String(decoded, "gbk");
			return s;
		} catch (Exception e) {
			DB_LOGGER.error(encodedHex + ":" + e.getClass().getName());
			return null;
		}
	}

	/** // 加密
	 * @param num
	 * @return
	 */

	public static final Long encodeNumer(long num){
		byte[] bytes = long2bytes(num);
		byte[] encoded = encode(bytes, DEFAULT_KEY);
		Long mask=0xffL;
		Long temp=0L;
		Long res=0L;
		for(int i=0;i<8;i++){
			res<<=8;
			temp=encoded[i]&mask;
			res|=temp;
		}
		return Math.abs(res);
	}
	private static final byte[] encode(byte[] input, byte[] key){
		SecretKey deskey = new SecretKeySpec(key, Algorithm);
		Cipher c1 = null;
		try {
			c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] cipherByte = c1.doFinal(input);
			return cipherByte;
		} catch (Exception e) {
			DB_LOGGER.error(e.getClass().getCanonicalName());
		}
		return null;
	}

	/** 解密
	 * @param input
	 * @param key
	 * @return
	 */

	private static byte[] decode(byte[] input, byte[] key) {
		SecretKey deskey = new SecretKeySpec(key, Algorithm);
		Cipher c1=null;
		try {
			c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			byte[] clearByte = c1.doFinal(input);
			return clearByte;
		} catch (Exception e) {
			DB_LOGGER.error(e.getClass().getCanonicalName());
		}
		return null;
	}

	/** 	// 字节码转换成16进制字符串
	 * @param bytes
	 * @return
	 */

	private static final String byte2hex(byte bytes[]) {
		StringBuilder retString = new StringBuilder();
		for (int i = 0; i < bytes.length; ++i) {
			retString.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF)).substring(1).toUpperCase());
		}
		return retString.toString();
	}

	/**	// 将16进制字符串转换成字节码
	 * @param hex
	 * @return
	 */

	private static final byte[] hex2byte(String hex) {
		byte[] bts = new byte[hex.length() / 2];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bts;
	}
	public static final String encryptString(String original, String key){
		String tmpKey = StringUtils.rightPad(key, 8, "经");
		try{
			byte[] keys = Arrays.copyOf(tmpKey.getBytes("utf-8"), 8);
			String str = original;
			return encodeString(str, keys);
		} catch (Exception e) {
			DB_LOGGER.error(original + ":" + key + ":" + e.getClass().getCanonicalName());
		}
		return null;
	}
	public static final String decryptString(String encryptStr, String key){
		if(StringUtils.isBlank(encryptStr)||encryptStr.length()%2==1) {
            return "";
        }
		try{
			String tmpKey = StringUtils.rightPad(key, 8, "经");
			byte[] keys = Arrays.copyOf(tmpKey.getBytes("utf-8"), 8);
			String str = decodeString(encryptStr, keys);
			return str;
		} catch (Exception e) {
			DB_LOGGER.error(encryptStr + ":" + e.getClass().getCanonicalName());
			return "";
		}
	}
	/**
	 * @param pKey 3DES密钥/24
	 * @param pIV 3DES向量/8
	 * @param pContent 加密明文
	 * @return
	 */
	public static String TriDesEncrypt(String pKey, String pIV, String pContent){
		String ret="";
		try {
			//初始化Cipher
			javax.crypto.spec.IvParameterSpec iv = new javax.crypto.spec.IvParameterSpec(pIV.getBytes());
			SecretKeySpec key = new SecretKeySpec(pKey.getBytes(),"DESede");
			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");

			//执行加密并做Base64编码
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);

			ret=Base64.encodeBase64String(cipher.doFinal(pContent.getBytes("utf-8")));

			return ret.replaceAll("\r\n","");
		} catch (Exception e) {
			DB_LOGGER.error(LoggerUtils.getExceptionTrace(e, 10));
			return "";
		}
	}
	public static String triDesDecrypt(String enc, String pKey, String pIV){
		String ret="";
		try {
			//初始化Cipher
			javax.crypto.spec.IvParameterSpec iv = new javax.crypto.spec.IvParameterSpec(pIV.getBytes());
			SecretKeySpec key = new SecretKeySpec(pKey.getBytes(),"DESede");
			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");

			//执行加密并做Base64编码
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			ret = new String(cipher.doFinal(Base64.decodeBase64(enc)), "UTF-8");
			return ret.replaceAll("\r\n","");
		} catch (Exception e) {
			DB_LOGGER.error(LoggerUtils.getExceptionTrace(e, 10));
			return "";
		}
	}
	/**
	 * RSA加密
	 * @param pKey RSA密钥
	 * @param pContent 加密明文
	 * @return
	 */
	public static String RSAEncrypt(String pKey, String pContent) {
		String ret="";
		try {
			//解析密钥得到公钥
			int startIndex=pKey.indexOf("<Modulus>")+9;
			int endIndex=pKey.indexOf("</Modulus>");

			byte[] b= Base64.decodeBase64(pKey.substring(startIndex, endIndex));

			String N = byte2hex(b);

			startIndex=pKey.indexOf("<Exponent>")+10;
			endIndex=pKey.indexOf("</Exponent>");

			b = Base64.decodeBase64(pKey.substring(startIndex, endIndex));

			String E = byte2hex(b);

			//初始化Cipher
			java.math.BigInteger bigN=new java.math.BigInteger(N,16);
			java.math.BigInteger bigE=new java.math.BigInteger(E,16);
			java.security.spec.RSAPublicKeySpec pubSpec=new java.security.spec.RSAPublicKeySpec(bigN,bigE);
			Cipher cipher= Cipher.getInstance("RSA/ECB/PKCS1Padding");

			//执行加密并做Base64编码
			cipher.init(Cipher.ENCRYPT_MODE, java.security.KeyFactory.getInstance("RSA").generatePublic(pubSpec));

			ret = Base64.encodeBase64String(cipher.doFinal(pContent.getBytes("utf-8")));

			return ret.replaceAll("\r\n","");
		} catch (Exception e) {
			DB_LOGGER.error(LoggerUtils.getExceptionTrace(e, 10));
			return "";
		}
	}
	/**
	 * @param keyStr 24字节，base64编码
	 * @param plainText 待加密的字符
	 * @param encoding
	 * @return 加密后用base64编码的字符串
	 */
	public static String encryptWithThiDES(String keyStr, String plainText, String encoding) {
		try{
			byte[] key = Base64.decodeBase64(keyStr);
			byte[] src = plainText.getBytes(encoding);
			byte[] result = encryptWithThiDES(key, src);
			return Base64.encodeBase64String(result).replaceAll("\r\n", "");
		} catch (Exception e) {
			DB_LOGGER.error(LoggerUtils.getExceptionTrace(e, 10));
		}
		return null;
	}
	/**
	 * @param keyStr 24字节，base64编码
	 * @param encrypt 加密后的字节用base64编码的字符串
	 * @param encoding 编码
	 * @return
	 * @throws Exception
	 */
	public static String decryptWithThiDES(String keyStr, String encrypt, String encoding) {
		try {
			byte[] key = Base64.decodeBase64(keyStr);
			byte[] src = Base64.decodeBase64(encrypt);
			byte[] result = decryptWithThiDES(key, src);
			return new String(result, encoding);
		} catch (Exception e) {
			DB_LOGGER.error(LoggerUtils.getExceptionTrace(e, 10));
		}
		return null;
	}
	public static byte[] encryptWithThiDES(byte[] key, byte[] src) throws Exception{
		SecretKey deskey = new SecretKeySpec(key, "DESede");
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] result = cipher.doFinal(src);
		return result;
	}
	public static byte[] decryptWithThiDES(byte[] key, byte[] encrypted) throws Exception {
		//生成密钥
		SecretKey deskey = new SecretKeySpec(key, "DESede");
		//解密
		Cipher c1 = Cipher.getInstance("DESede");
		c1.init(Cipher.DECRYPT_MODE, deskey);
		byte[] result = c1.doFinal(encrypted);
		return result;
	}
	public static String getRandomKey(int length){
		byte[] key = new byte[length];
		for(int i=0;i<length;i++){
			key[i] = (byte)(RandomUtils.nextInt() & 0xff);
		}
		return Base64.encodeBase64String(key);
	}
}
