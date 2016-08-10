package com.is.mpg.service.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

import com.is.mpg.vo.ChattingRoom;

/**
 * 전체적인 채팅 방 Manager
 * 
 * @author iskwon
 */
public class ChattingRoomManager {
	
	private static ChattingRoomManager chatttingRoomManager = new ChattingRoomManager(); 
	
	public static ChattingRoomManager getInstance() {
		return chatttingRoomManager;
	}
	
	// key: WebSocketSession / value: roomId
	// 채팅방 나갈 시 어떤 채팅방에 들어와있는지 빨리 찾기 위해 WebSocketSession, roomId를 매핑시켜 보관한다.
	private static Map<WebSocketSession, String> memberRoomMap = new HashMap<WebSocketSession, String>();
	private static List<ChattingRoom> roomList = new ArrayList<ChattingRoom>();

	public boolean putMemberRoomMap(WebSocketSession session, String roomId) {
		return memberRoomMap.put(session, roomId) != null; // 리턴 값 의미 없음
	}
	
	public boolean removeMemberRoomMap(WebSocketSession session) {
		return memberRoomMap.remove(session) != null; // 리턴 값 의미 없음
	}
	
	public String getRoomIdOfMember(WebSocketSession session) {
		return memberRoomMap.get(session);
	}
	
	public boolean addRoomList(ChattingRoom chattingRoom) {
		return roomList.add(chattingRoom);
	}
	
	public List<ChattingRoom> getChattingRoomList() {
		return roomList;
	}
	
	public ChattingRoom getChattingRoomOfRoomId(String roomId) {
		for (ChattingRoom cr : roomList) {
			if (roomId.equals(cr.getRoomId()))
				return cr;
		}
		return null;
	}
	
	public boolean removeChattingRoom(ChattingRoom chattingRoom) {
		return roomList.remove(chattingRoom);
	}
}
