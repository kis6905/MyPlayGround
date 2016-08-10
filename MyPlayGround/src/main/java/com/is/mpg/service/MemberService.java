package com.is.mpg.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

import com.is.mpg.vo.Member;

@Service
public interface MemberService {
	
	public boolean checkId(String memberId);
	public boolean checkNickname(String nickname);
	public boolean joinMember(Member member) throws Exception;
	public List<Member> getSearchMemberList(String search, String memberId);
	public JSONArray getSearchMemberListToJSONArray(String search, String memberId);
	
}
