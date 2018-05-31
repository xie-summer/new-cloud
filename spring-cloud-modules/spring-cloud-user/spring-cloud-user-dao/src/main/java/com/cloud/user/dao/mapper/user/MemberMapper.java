package com.cloud.user.dao.mapper.user;

import com.cloud.user.dao.model.user.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMapper {
  int deleteByPrimaryKey(Long memberId);

  int insert(Member record);

  int insertSelective(Member record);

  /**
   * @param memberId
   * @return
   */
  Member selectByPrimaryKey(Long memberId);

  int updateByPrimaryKeySelective(Member record);

  int updateByPrimaryKey(Member record);
}
