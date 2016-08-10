package com.is.mpg.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.is.mpg.commons.Codes;
import com.is.mpg.dao.ComCodeMapper;
import com.is.mpg.vo.ComCode;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ComCodeMapper comCodeMapper;
	
	/**
	 * 로그인 페이지 이동
	 */
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String getLogin() {
		
		logger.info("-> []");
		
		logger.info("<- []");
		return "login/login";
	}
	
	/**
	 * 로그 아웃 처리 및 로그인 페이지 이동
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String getLogout(HttpServletRequest request) {
		
		logger.info("-> []");
		
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
		
		logger.info("<- []");
		return "login/login";
	}
	
	/**
	 * 로그인 실패 페이지 이동
	 */
	@RequestMapping(value = "/loginfail", method = RequestMethod.GET)
	public String getLoginFail(
			@RequestParam(value = "cause", required = true) Integer cause,
			ModelMap modelMap) {
		
		logger.info("-> [cause = {}]", cause);
		
		ComCode comCode = new ComCode(Codes.INFO_CODE_GROUP, Codes.VALID_PASSWORD_CNT_CODE);
		Integer validPasswordFailCnt = Integer.parseInt(comCodeMapper.getComCode(comCode).getCodeValue());
		modelMap.put("cause", cause);
		modelMap.put("validPasswordFailCnt", validPasswordFailCnt);
		
		logger.info("<- [cause = {}], [validPasswordFailCnt = {}]", cause, validPasswordFailCnt);
		return "login/login_fail";
	}
	
	/**
	 * 세션 타임아웃 페이지 이동
	 */
	@RequestMapping(value = "/sessiontimeout", method = RequestMethod.GET)
	public String getSessionTimeout() {
		
		logger.info("-> []");
		
		logger.info("<- []");
		return "error/session_expired";
	}
	
}
