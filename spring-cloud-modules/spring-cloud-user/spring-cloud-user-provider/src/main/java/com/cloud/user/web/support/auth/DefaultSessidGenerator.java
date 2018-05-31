//package com.cloud.web.support.auth;
//
//import com.cloud.util.StringUtil;
//import org.springframework.security.core.Authentication;
//
//public class DefaultSessidGenerator implements SessidGenerator{
//	/**
//	 * @param min
//	 * @return
//	 */
//	@Override
//	public String generateSessid(Authentication auth){
//		String key = StringUtil.getRandomString(24);
//		String ukey = key + StringUtil.md5(key + "testKeyMemberLogon", 8);
//		return ukey;
//	}
//
//}
