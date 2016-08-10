package com.is.mpg.service.impl;

import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.is.mpg.commons.Utility;
import com.is.mpg.service.ChattingService;
import com.is.mpg.service.modules.ChattingRoomManager;
import com.is.mpg.vo.ChattingRoom;

@Service(value = "chattingServiceImpl")
public class ChattingServiceImpl implements ChattingService {
	
	private static final Logger logger = LoggerFactory.getLogger(ChattingServiceImpl.class);
	
	private static ChattingRoomManager chattingRoomManager;

	public ChattingServiceImpl() {
		chattingRoomManager = ChattingRoomManager.getInstance();
	}
	
	/**
	 * 채팅 방 생성
	 */
	@Override
	public ChattingRoom createChattingRoom(String roomName) {
		try {
			String roomId = UUID.randomUUID().toString().replace("-", "");
			String registeredDate = Utility.getCurrentDateToString("yyyy-MM-dd HH:mm:ss");
			ChattingRoom chattingRoom = new ChattingRoom(roomId, roomName, registeredDate);
			chattingRoomManager.addRoomList(chattingRoom);
			return chattingRoom;
		} catch (Exception e) {
			logger.error("[An error occurred!]", e);
			return null;
		}
	}
	
	/**
	 * 채팅 방 목록 리턴
	 */
	@Override
	public List<ChattingRoom> getChattingList() {
		List<ChattingRoom> chattingRoomList = chattingRoomManager.getChattingRoomList();
		return chattingRoomList;
	}

	/**
	 * 채팅 방 목록을 JSONArray 형태로 리턴
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getChattingListToJsonArray() {
		JSONArray jsonArr = new JSONArray();
		List<ChattingRoom> chattingRoomList = getChattingList();
		for (ChattingRoom room : chattingRoomList)
			jsonArr.add(room.toJSONObject());
		return jsonArr;
	}
	
}
