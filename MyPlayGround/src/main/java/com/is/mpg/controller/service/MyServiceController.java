package com.is.mpg.controller.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.is.mpg.service.MyServiceService;
import com.is.mpg.vo.MyService;

@Controller
@RequestMapping(value = "/service/**")
public class MyServiceController {
	
	// 서비스 페이지로 이동 시 jsp 이름 앞에 붙일 폴더 명
	private static final String PAGE_FOLDER_NAME = "service/";
	
	private static final Logger logger = LoggerFactory.getLogger(MyServiceController.class);
	
	@Autowired
	private MyServiceService myServiceServiceImpl;
	
	/**
	 * 서비스에 맞는 페이지 이동
	 */
	@RequestMapping(value = "/page/{serviceId}", method = RequestMethod.GET)
	public String getServicePage(
			@PathVariable(value = "serviceId") Integer serviceId,
			ModelMap modelMap) {
		
		logger.info("-> [serviceId = {}]", serviceId);
		
		MyService myService = myServiceServiceImpl.getMyService(serviceId);
		
		if (myService == null)
			return "redirect:/error/204";
		
		modelMap.addAttribute("myService", myService);
		
		logger.info("<- [myService = {}]", myService.toString());
		return PAGE_FOLDER_NAME + myService.getPageName();
	}
	
	
}
