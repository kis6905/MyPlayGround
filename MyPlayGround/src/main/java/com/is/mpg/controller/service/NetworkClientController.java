package com.is.mpg.controller.service;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/service/**")
public class NetworkClientController {
	
	private static final Logger logger = LoggerFactory.getLogger(NetworkClientController.class);
	
	@Autowired
    private RedisTemplate<String, String> redisTemplate;
	
    @Resource(name="redisTemplate")
    private ValueOperations<String, String> valueOps;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/network/request", method = RequestMethod.POST)
	@ResponseBody
	public String postNetworkRequest(
			@RequestParam(value = "text", required = true) String text) {
		
		logger.info("-> [text = {}]", text);
		
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("result", 0);
		
		Set<String> keys = redisTemplate.keys("*");
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		String value = valueOps.get(text);
		jsonResult.put("value", value);
		
		logger.info("<- [jsonResult = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
	
}
