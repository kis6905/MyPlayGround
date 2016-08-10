package com.is.mpg.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);
	
	/**
	 * 에러 페이지 이동
	 */
	@RequestMapping(value = "/error/{errorCode}")
	public String getError(
			@PathVariable(value = "errorCode") String errorCode,
			ModelMap modelMap) {
		
		logger.info("-> [errorCode = {}]", errorCode);
		
		modelMap.addAttribute("errorCode", errorCode);
		
		logger.info("<- []");
		return "error/error";
	}
	
}
