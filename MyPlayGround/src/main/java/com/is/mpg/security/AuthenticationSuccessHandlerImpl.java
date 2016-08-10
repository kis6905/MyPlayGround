package com.is.mpg.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.is.mpg.dao.MemberMapper;
import com.is.mpg.vo.Member;

/**
 * 로그인 성공 핸들러
 * 
 * @author iskwon
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);
	
	@Autowired
	private MemberMapper memberMapper;
	
	/**
	 * 로그인 성공 시 lastLoginDate를 업데이트 해준 후 페이지 이동.
	 */
	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request,
			HttpServletResponse response, 
			Authentication authentication) throws IOException, ServletException {
		
		String memberId = request.getParameter("memberId");
		logger.info("-> [Login Success!(memberId = {})]", memberId);
		
		logger.info("-> [sessionId = {}]", request.getSession(false).getId());
		
		Member member = memberMapper.getMemberOfId(memberId);
		HttpSession session = request.getSession(false);
		session.setAttribute("MEMBER_ID", member.getMemberId());
		session.setAttribute("NICKNAME", member.getNickname());
		
		// 로그인 성공 시 lastLoginDate = SYSDATE(), passwordFailCnt = 0 업데이트
		memberMapper.updateWhenLoginSuccess(memberId);
		
		logger.info("<- [redirect = /main]");
		response.sendRedirect("/main");
	}

}
