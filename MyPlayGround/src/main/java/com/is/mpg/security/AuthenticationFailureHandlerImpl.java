package com.is.mpg.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.is.mpg.commons.Codes;
import com.is.mpg.commons.Constants;
import com.is.mpg.dao.MemberMapper;
import com.is.mpg.vo.Member;

/**
 * 로그인 실패 핸들러
 * 
 * @author iskwon
 */
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationFailureHandlerImpl.class);
	
	@Autowired
	private MemberMapper memberMapper;
	
	/**
	 * 로그인 실패 시 원인 파악후 response.
	 */
	@Override
	public void onAuthenticationFailure(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException authnticationException) throws IOException, ServletException {
		
		String memberId = request.getParameter("memberId");
		logger.info("-> [Login Failure!(memberId = {})]", memberId);
		
		int cause = Constants.COMMON_SERVER_ERROR;
		// 각 케이스 별 원인 세팅
		if (authnticationException.getClass().equals(InternalAuthenticationServiceException.class)) { // 계정 없음
			cause = Constants.LOGIN_FAIL_MISMATCH;
		}
		else if (authnticationException.getClass().equals(BadCredentialsException.class)) { // 비밀번호 불일치
			Member member = memberMapper.getMemberOfId(memberId);
			if (member.getGradeCode() == Codes.USER_CODE)
				memberMapper.increasePasswordFailCnt(memberId); // 유저일 때만 비밀번호 실패 횟수 +1
			cause = Constants.LOGIN_FAIL_MISMATCH;
		}
		else if (authnticationException.getClass().equals(DisabledException.class)) { // 계정 Disable
			cause = Constants.LOGIN_FAIL_DISABLED;
		}
		else if (authnticationException.getClass().equals(AccountExpiredException.class)) { // 계정 만료
			cause = Constants.LOGIN_FAIL_ACCOUNT_EXPIRED;
		}
		else if (authnticationException.getClass().equals(CredentialsExpiredException.class)) { // 계정 권한 만료
			cause = Constants.LOGIN_FAIL_CREDENTIALS_EXPIRED;
		}
		else if (authnticationException.getClass().equals(LockedException.class)) { // 계정 잠김
			cause = Constants.LOGIN_FAIL_LOCKED;
		}
		else { // 그 외는 서버에러로 본다.
			logger.error("An error Occurred", authnticationException);
			cause = Constants.COMMON_SERVER_ERROR;
		}
		
		logger.info("<- [cause = {}]", cause);
		response.sendRedirect("/loginfail?cause=" + cause);
	}
	
}
