package com.is.mpg.controller.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.is.mpg.commons.Constants;
import com.is.mpg.service.ChattingService;
import com.is.mpg.vo.ChattingRoom;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping(value = "/service/**")
public class ChattingController {
	
	private static final Logger logger = LoggerFactory.getLogger(ChattingController.class);
	
	@Autowired
	private ChattingService chattingServiceImpl;
	
	/**
	 * 채팅 방 목록 리턴
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/chatting/list", method = RequestMethod.POST)
	@ResponseBody
	public String postChattingList() {
		
		logger.info("-> []");

		JSONObject jsonResult = new JSONObject();
		int result = Constants.NOT_OK;
		try {
			jsonResult.put("chattingRoomList", chattingServiceImpl.getChattingListToJsonArray());
			result = Constants.OK;
		} catch (Exception e) {
			logger.error("[An error occurred!]", e);
		}
		
		jsonResult.put("result", result);
		
		logger.info("<- [jsonResult = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
	
	/**
	 * 채팅 방 생성
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/chatting/create", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String postChattingCreate(
			@RequestParam(value = "roomName", required = true) String roomName) {
		
		logger.info("-> [roomName = {}]", roomName);

		JSONObject jsonResult = new JSONObject();
		ChattingRoom chattingRoom = chattingServiceImpl.createChattingRoom(roomName);
		jsonResult.put("result", chattingRoom != null ? Constants.OK : Constants.NOT_OK);
		jsonResult.put("chattingRoom", chattingRoom != null ? chattingRoom.toJSONObject() : null);
		
		logger.info("<- [jsonResult = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
	
}
