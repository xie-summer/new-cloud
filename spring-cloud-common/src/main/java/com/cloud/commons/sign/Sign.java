package com.cloud.commons.sign;

import com.cloud.commons.api.ApiSysParamConstants;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名类
 * @author quzhuping
 *
 */
public class Sign {

	/**
     * 对字符串进行MD5签名
     * @param text 明文
     * @param inputCharset 编码格式 UTF-8或GBK
     * @return 密文,32位16进制小写字符串
     */
    public static String md5Hex(String text, String inputCharset) {

        return DigestUtils.md5Hex(getContentBytes(text, inputCharset));

    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

	/**
	 * md5签名方法
	 * @param srcStr
	 * @return 返回32位16进制大写字符串
	 */
	public static String md5(String srcStr){
		return DigestUtils.md5Hex(srcStr).toUpperCase();
	}


	/**
	 * 对请求参数集进行MD5签名
	 * @param params 待签名的请求参数集
	 * @param secretCode 签名密码
	 * @return 返回null 或 32位16进制大写字符串
	 */
	public static String signMD5(Map<String, String> params, String secretCode){
		if(params == null || params.isEmpty()) {
            return "";
        }
		if(params instanceof TreeMap){
			return signMD5Inner((TreeMap<String, String>) params, secretCode);
		}else{
			TreeMap<String, String> treeMap = new TreeMap<String, String>();
			treeMap.putAll(params);
			return signMD5Inner(treeMap, secretCode);
		}
	}
	/**
	 * 对请求参数集进行MD5签名
	 * @param param 待签名的请求参数集
	 * @param secretCode 签名密码
	 * @return 返回32位16进制大写字符串
	 */
	private static String signMD5Inner(TreeMap<String, String> param, String secretCode){
		return DigestUtils.md5Hex(signStr(param, secretCode, false)).toUpperCase();
	}

	/**
	 * 将请求参数按key=value&key=valuesecretCode拼接
	 * <br/>排除key为sign和signmethod的key-value
	 * @param param 请求参数
	 * @param secretCode 签名密码
	 * @return
	 */
	public static String signStr(TreeMap<String, String> param, String secretCode, boolean startAppend){
		StringBuilder orgin = new StringBuilder();
		String value = "";
		for(String name : param.keySet()){
			//参与签名的值不包括参数中的签名值和签名方法
			if(!StringUtils.equalsIgnoreCase(name, ApiSysParamConstants.SIGN)
					&& !StringUtils.equalsIgnoreCase(name, ApiSysParamConstants.SIGNMETHOD)){
				value = param.get(name);
				if(StringUtils.isEmpty(value)){
					value = "";
				}
				orgin.append(name).append("=").append(value).append("&");
			}
		}
		if(startAppend){
			return secretCode+StringUtils.substringBeforeLast(orgin.toString(), "&");
		}
		return StringUtils.substringBeforeLast(orgin.toString(), "&") + secretCode;
	}
}
