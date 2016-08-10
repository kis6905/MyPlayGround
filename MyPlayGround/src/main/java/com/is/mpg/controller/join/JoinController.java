package com.is.mpg.controller.join;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.is.mpg.service.MemberService;
import com.is.mpg.vo.Member;

@Controller
@RequestMapping(value = "/join/**")
public class JoinController {
	
	private static final Logger logger = LoggerFactory.getLogger(JoinController.class);

	@Autowired
	private MemberService memberServiceImpl;
	
	/**
	 * 회원가입 화면 이동
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getJoin() {
		
		logger.info("-> []");
		
		logger.info("<- []");
		return "join/join";
	}
	
	/**
	 * 회원가입 처리 후 화면 이동
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String postJoin(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "nickname") String nickname,
			Model model) {
		
		logger.info("-> [id = {}], [nickname = {}]", id, nickname);
		
		Member member = new Member();
		member.setMemberId(id);
		member.setPassword(password);
		member.setNickname(nickname);
		
		boolean result = false;
		try {
			result = memberServiceImpl.joinMember(member);
		} catch (Exception e) {
			logger.error("~~ [An error occurred!]", e);
		}
		model.addAttribute("result", result);
		
		logger.info("<- [result = {}]", result);
		return "join/join_result";
	}
	
	/**
	 * ID 중복 확인
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/check/id", method = RequestMethod.POST)
	@ResponseBody
	public String postCheckId(@RequestParam(value = "id") String id) {
		
		logger.info("-> [id = {}]", id);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", memberServiceImpl.checkId(id));
		
		logger.info("<- [resultJson = {}]", resultJson.toString());
		return resultJson.toString();
	}
	
	/**
	 * 닉네임 중복 확인
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/check/nickname", method = RequestMethod.POST)
	@ResponseBody
	public String postCheckNickname(@RequestParam(value = "nickname") String nickname) {
		
		logger.info("-> [nickname = {}]", nickname);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", memberServiceImpl.checkNickname(nickname));
		
		logger.info("<- [resultJson = {}]", resultJson.toString());
		return resultJson.toString();
	}
	
}
