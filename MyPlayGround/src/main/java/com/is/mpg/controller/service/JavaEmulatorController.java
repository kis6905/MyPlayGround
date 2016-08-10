package com.is.mpg.controller.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.is.mpg.service.JavaEmulatorService;

@Controller
@RequestMapping(value = "/service/**")
public class JavaEmulatorController {
	
	private static final Logger logger = LoggerFactory.getLogger(JavaEmulatorController.class);
	
	@Autowired
	private JavaEmulatorService javaEmulatorServiceImpl;
	
	/**
	 * 자바 실행기
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/runjava", method = RequestMethod.POST, produces = "application/json; charset=UTF8;")
	@ResponseBody
	public String postRunJava(
			@RequestParam(value = "javaCode") String javaCode,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		
		logger.info("----------> [memberId = {}] [javaCode = {}]", memberId, javaCode);
		
		String result = javaEmulatorServiceImpl.runJava(javaCode, session.getServletContext().getRealPath("/resources"));
		
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("result", result);
		
		logger.info("<---------- [jsonResult = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
	
}
