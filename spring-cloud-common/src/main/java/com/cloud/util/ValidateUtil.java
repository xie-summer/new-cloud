package com.cloud.util;

import com.cloud.support.ErrorCode;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
/**
 * @author summer
 */
public class ValidateUtil implements Util4Script {
	private static Set<String> simplePass = new TreeSet<>();
	static{
		try(InputStream is = ValidateUtil.class.getClassLoader().getResourceAsStream("simplepass.txt")){
			List<String> lines = IOUtils.readLines(is);
			simplePass.addAll(lines);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static final List<String> ydMobile = Arrays.asList(new String[]{"139","138","137","136","135","134",
			"147", "150", "151", "152","157","158","159", "182", "183", "187", "188", "184", "178"});

	public static final List<String> ltMobile = Arrays.asList(new String[]{"130","131","132","155","156","185","186","176", "145"});

	public static final List<String> dxMobile = Arrays.asList(new String[]{"133","153","177","180","181","189"});

	public static boolean isYdMobile(String mobile){
		return ydMobile.contains(mobile.substring(0, 3));
	}
	public static boolean isEmail(String email){
		if(StringUtils.isBlank(email)) {
            return false;
        }
		//return StringUtil.regMatch3(email, "^[^\\s@]*[a-z0-9]+[a-z0-9._-]+@(?:[a-z0-9_-]+\\.)+[a-z0-9_-]+[^\\s]*$", true);
		//return StringUtil.regMatch3(email, "^([\\w]+([-\\.]*[\\w]+)*)+@[\\w-]+(\\.[\\w-]+)+$", true);
		return StringUtil.regMatch(email, "^[A-Z0-9._-]+@[A-Z0-9_-]+(\\.[A-Z0-9_-]+)*(\\.[A-Z]{2,4})+$", true);
	}
	public static boolean isMobile(String mobile) {
		if(StringUtils.isBlank(mobile)) {
            return false;
        }
		return StringUtil.regMatch(mobile, "^1[34578]{1}\\d{9}$", true);
	}
	public static boolean isNumber(String number) {
		return StringUtils.isNotBlank(number) && StringUtils.isNumeric(number);
	}
	public static boolean isNumber(String number, int minLength, int maxLength) {
		return isNumber(number) && number.length() >= minLength && number.length()<= maxLength;
	}
	public static boolean isIDCard(String number){
		return StringUtil.regMatch(number, "^(\\d{15}|\\d{17}[0-9xX]{1})$", false);
	}
	public static ErrorCode validatePassword(String pass1, String pass2){
		if(StringUtils.isBlank(pass1) || StringUtils.isBlank(pass2)){
			return  ErrorCode.getFailure("密码必须!");
		}
		if(!StringUtils.equals(pass1, pass2)){
			return  ErrorCode.getFailure("两次输入的密码不一致!");
		}
		if(!ValidateUtil.isPassword(pass1)){
			return  ErrorCode.getFailure("密码格式不正确,只能是字母，数字，英文标点，长度6—14位！");
		}
		if(ValidateUtil.isSimplePass(pass1)){
			return ErrorCode.getFailure("密码过于简单，请重新输入！");
		}
		return ErrorCode.SUCCESS;

	}
	private static boolean isSimplePass(String plainPass){
		if(StringUtils.isNumeric(plainPass)) {
			return true;
		}
		String md5 = StringUtil.md5(plainPass);
		return simplePass.contains(md5);
	}
	/**
	 * 数字、下划线、字母的标识符
	 * @param variable
	 * @param length1
	 * @param length2
	 * @return
	 */
	public static boolean isVariable(String variable, int length1, int length2) {
		if(StringUtils.isBlank(variable)) {
            return false;
        }
		return StringUtil.regMatch(variable, "^\\w{" + length1 + "," + length2 + "}$", true);
	}
	/**
	 * 数字、下划线、字母、中文的标识符
	 * @param variable
	 * @param length1
	 * @param length2
	 * @return
	 */
	public static boolean isCNVariable(String variable, int length1, int length2) {
		if(StringUtils.isBlank(variable)) {
            return false;
        }
		return StringUtil.regMatch(variable, "^[\\w+$\\u4e00-\\u9fa5]{" + length1 + "," + length2 +"}$", true);
	}
	public static boolean isPhone(String phone){
		if(StringUtils.isBlank(phone)) {
            return false;
        }
		return StringUtil.regMatch(phone, "^[0-9,-]{6,24}$", true);
	}
	public static boolean isPostCode(String postcode) {
		return StringUtil.regMatch(postcode, "^[0-9]{6}$", true);
	}
	private static char[] cncharRange = new char[]{'\u4e00','\u9fa5'};
	private static String spcharList = "～！￥…（）——：“《》？/，、；‘’“”【】·。★☆○●◎◇◆□■△▲※→←↑↓";
	/**
	 * 验证新闻内容是否有非法字符
	 * @param content
	 * @return
	 */
	public static String validateNewsContent(String spchar, String content){
		if(StringUtils.isBlank(spchar)) {
            spchar = spcharList;
        }
		String result = "";
		if(StringUtils.isBlank(content)) {
            return result;
        }
		char c = 'a';
		for(int i=0, length=content.length();i<length; i++){
			c = content.charAt(i);
			if(c < 128 || c>=cncharRange[0] && c<=cncharRange[1] || spchar.indexOf(c)>=0) {
                continue;
            }
			result += "[" + i + "=" + c + "]";
		}
		return result;
	}
	public static boolean isPassword(String password) {
		int len = StringUtils.length(password);
		return StringUtils.isAsciiPrintable(password) && len >=6 && len <=14;
	}
}