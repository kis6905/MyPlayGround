package com.is.mpg.controller.main;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.is.mpg.service.MyServiceService;
import com.is.mpg.vo.MyService;

@Controller
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private MyServiceService myServiceServiceImpl;
	
	/**
	 * 메인 페이지 이동
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String getMain(ModelMap modelMap) {
		
		logger.info("-> []");
		
		List<MyService> myServiceList = myServiceServiceImpl.getMyServiceList();
		modelMap.addAttribute("myServiceList", myServiceList);
		
		logger.info("<- [myServiceListSize = {}]", myServiceList.size());
		return "main/main";
	}
	
}
