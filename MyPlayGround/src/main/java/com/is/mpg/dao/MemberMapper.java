package com.is.mpg.dao;

import java.util.List;
import java.util.Map;

import com.is.mpg.vo.Member;

/**
 * @author iskwon
 */
public interface MemberMapper {
	
	Member getMemberOfId(String memberId);
	List<Member> getSearchMemberList(Map<String, Object> map);
	Member getMemberOfNickname(String nickname);
	
	int insertMember(Member member);
	int updateWhenLoginSuccess(String memberId);
	int increasePasswordFailCnt(String memberId);
	
}
