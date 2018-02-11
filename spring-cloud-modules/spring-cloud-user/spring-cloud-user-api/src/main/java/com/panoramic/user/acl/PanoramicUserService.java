package com.panoramic.user.acl;


import com.cloud.core.Service;
import com.monitor.dto.user.PanoramicUserInfo;
import com.monitor.model.user.PanoramicUser;

/**
 * Created by @author xuegang on 2017/12/22.
 */
public interface PanoramicUserService extends Service<PanoramicUser> {

	/**
	 * 用户基本信息获取
	 * @param userName
	 * @param password
	 * @return
	 */
	PanoramicUserInfo getUserInfo(String userName,String password);

	/**
	 * 系统登入
	 * @param userName
	 * @param password
	 * @return
	 */
	PanoramicUserInfo loginWeb(String userName,String password);
	
	/**
	 * 系统登出
	 * @param userName
	 * @return
	 */
	PanoramicUserInfo logoutWeb(String userName);
}
