package com.is.mpg.vo;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.web.socket.WebSocketSession;

public class ChattingRoom extends CommonVo {
	
	private String roomId			= null;
	private String roomName			= null;
	
	// 채팅방에 들어와 있는 Member List
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	
	public ChattingRoom(String roomId, String roomName, String registeredDate) {
		this.roomId = roomId;
		this.roomName = roomName;
		super.setRegisteredDate(registeredDate);
	}
	
	public String getRoomId() {
		return roomId;
	}
	
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public String getRegisteredDate() {
		return super.getRegisteredDate();
	}
	
	public void setRegisteredDate(String registeredDate) {
		super.setRegisteredDate(registeredDate);
	}
	
	public List<String> getMemberIdList() {
		List<String> memberIdList = new ArrayList<String>();
		
		for (WebSocketSession session : sessionList)
			memberIdList.add((String) session.getAttributes().get("MEMBER_ID"));
		
		return memberIdList;
	}
	
	public List<WebSocketSession> getSessionList() {
		return sessionList;
	}
	
	public boolean addMember(WebSocketSession session) {
		return sessionList.add(session);
	}
	
	public boolean removeMember(WebSocketSession session) {
		return sessionList.remove(session);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() {
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("roomId", roomId);
		jsonResult.put("roomName", roomName);
		jsonResult.put("registeredDate", getRegisteredDate());
		jsonResult.put("memberCount", sessionList.size());
		return jsonResult;
	}

	@Override
	public String toString() {
		return "ChattingRoom [roomId=" + roomId
				+ ", roomName=" + roomName
				+ ", registeredDate=" + getRegisteredDate()
				+ ", sessionListSize=" + sessionList.size()
				+ "]";
	}
}
