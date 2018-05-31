package com.cloud.user.dao.dao.member.impl;

import com.cloud.user.dao.dao.member.MemberDao;
import com.cloud.user.dao.mapper.user.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/** @author summer 2018/5/31 */
@Repository
public class MemberDaoImpl implements MemberDao {
  @Autowired private MemberMapper memberMapper;
}
