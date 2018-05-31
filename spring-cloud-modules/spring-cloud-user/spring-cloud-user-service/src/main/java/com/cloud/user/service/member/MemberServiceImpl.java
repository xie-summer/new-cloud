package com.cloud.user.service.member;

import com.cloud.user.api.member.MemberService;
import com.cloud.user.dao.domain.dto.user.PanoramicUserInfo;
import com.cloud.user.dao.mapper.user.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** Created by @author summer on 2017/12/20. */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {

  @Autowired private MemberMapper memberMapper;

  @Override
  @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
  public PanoramicUserInfo getUserInfo(String userName, String password) {
    // 用户数据查询
    PanoramicUserInfo result = new PanoramicUserInfo();

    return result;
  }

  @Override
  @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
  public PanoramicUserInfo loginWeb(String userName, String password) {

    PanoramicUserInfo result = getUserInfo(userName, password);

    return null;
  }

  @Override
  public PanoramicUserInfo logoutWeb(String userName) {
    return null;
  }
}
