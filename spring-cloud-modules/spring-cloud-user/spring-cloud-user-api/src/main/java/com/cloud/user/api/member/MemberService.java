package com.cloud.user.api.member;

import com.cloud.user.dao.domain.dto.user.PanoramicUserInfo;

/** Created by @author summer on 2017/12/22. */
public interface MemberService {

  /**
   * 用户基本信息获取
   *
   * @param userName
   * @param password
   * @return
   */
  PanoramicUserInfo getUserInfo(String userName, String password);

  /**
   * 系统登入
   *
   * @param userName
   * @param password
   * @return
   */
  PanoramicUserInfo loginWeb(String userName, String password);

  /**
   * 系统登出
   *
   * @param userName
   * @return
   */
  PanoramicUserInfo logoutWeb(String userName);
}
