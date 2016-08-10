package com.is.mpg.controller.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.is.mpg.commons.Utility;
import com.is.mpg.service.modules.ChattingRoomManager;
import com.is.mpg.vo.ChattingRoom;

/**
 * WebSocketHandler
 * 
 * @author iskwon
 */
public class ChattingWebsocketHandler extends TextWebSocketHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ChattingWebsocketHandler.class);
	
	public static final int CONNECT_OK 	= 0;
	public static final int CONNECT_NOT_OK = 1;
	
	public static final String KIND_OF_MESSAGE_JOIN = "join";
	public static final String KIND_OF_MESSAGE_CHATTING = "chatting";
	
	// 접속하는 사용자에 대한 정보 및 채팅 방 정보 관리
	private ChattingRoomManager chattingRoomManager;

	public ChattingWebsocketHandler() {
		super();
		chattingRoomManager = ChattingRoomManager.getInstance();
	}

	// 클라이언트에서 접속을 하여 성공할 경우 발생하는 이벤트
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		
		// Websocket Interceptor가 구현되어 있어야 session.getAttributes()를 통해 HttpSession에 저장된 값을 가져올 수 있다.
		Map<String, Object> map = session.getAttributes();
		String memberId = (String) map.get("MEMBER_ID");
		String nickName = (String) map.get("NICKNAME");
		
		logger.info("-> [sessionId = {}], [memberId = {}], [nickName = {}]", new Object[] { session.getId(), memberId, nickName });
		
		// WebSocekt 연결이 되었을땐 할게 없다.
		
//		SessionManager.getInstance().add(session);
		
		logger.info("<- []");
	}

	// 클라이언트에서 send를 이용해서 메시지 발송을 한 경우 이벤트 핸들링
	@SuppressWarnings("unchecked")
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
		
		String sessionId = session.getId();
		String payloadMessage = textMessage.getPayload();
		logger.info("-> [payloadMessage = {}], [sessionId = {}]", payloadMessage, sessionId);
		
		JSONObject jsonObj = (JSONObject) new JSONParser().parse(payloadMessage);
		JSONObject jsonResult = new JSONObject();
		
		Map<String, Object> map = session.getAttributes();
		String memberId = (String) map.get("MEMBER_ID");
		String nickname = (String) map.get("NICKNAME");
		
		String kind = (String) jsonObj.get("kind");
		String roomId = (String) jsonObj.get("roomId");
		String message = null;
		
		ChattingRoom chattingRoom = chattingRoomManager.getChattingRoomOfRoomId(roomId);
		
		switch (kind) {
		case KIND_OF_MESSAGE_JOIN:
			if (chattingRoom == null) { // 채팅 방이 삭제된 경우
				jsonResult.put("kind", "empty");
			}
			else {
				jsonResult.put("kind", "noti"); // 클라이언트에서 알림인지 채팅 메시지인지 구분하기 위한 구분자
				jsonResult.put("roomName", chattingRoom.getRoomName());
				chattingRoomManager.putMemberRoomMap(session, roomId);
				chattingRoom.addMember(session);
				message = nickname + "님이 입장하셨습니다.";
			}
			break;
		case KIND_OF_MESSAGE_CHATTING:
			jsonResult.put("kind", "chatting"); // 클라이언트에서 알림인지 채팅 메시지인지 구분하기 위한 구분자
			message = (String) jsonObj.get("message");
			break;
		default: // 아무것도 하지 않는다.
			jsonResult.put("kind", "");
			break;
		}
		
		List<WebSocketSession> memberListOfChattingRoom = chattingRoom.getSessionList();
		
		jsonResult.put("sessionId", sessionId);
		jsonResult.put("memberId", memberId);
		jsonResult.put("nickname", nickname);
		jsonResult.put("message", message);
		jsonResult.put("time", Utility.getCurrentDateToString("HH:mm"));
		jsonResult.put("memberCount", memberListOfChattingRoom.size());
		
		for (WebSocketSession client : memberListOfChattingRoom)
			client.sendMessage(new TextMessage(jsonResult.toString()));
		
		logger.info("<- [jsonResult = {}]", jsonResult.toString());
	}

	// 클라이언트에서 연결을 종료할 경우 발생하는 이벤트
	@SuppressWarnings("unchecked")
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		
		Map<String, Object> map = session.getAttributes();
		String memberId = (String) map.get("MEMBER_ID");
		String nickName = (String) map.get("NICKNAME");
		String roomId = chattingRoomManager.getRoomIdOfMember(session); // 멤버가 참여하고 있는 roomId
		
		logger.info("-> [sessionId = {}], [memberId = {}], [nickName = {}], [roomId = {}]", new Object[] { session.getId(), memberId, nickName, roomId });
		
		chattingRoomManager.removeMemberRoomMap(session); // roomId를 가져온 후 MemberRoomMap에서 삭제
		ChattingRoom chattingRoom = chattingRoomManager.getChattingRoomOfRoomId(roomId);
		chattingRoom.removeMember(session); // 채팅 방 참여 멤버에서 자신을 삭제
		List<WebSocketSession> memberListOfChattingRoom = chattingRoom.getSessionList(); // 자신을 삭제한 후 리스트를 가져온다.
		
		int memberCount = memberListOfChattingRoom.size();
		if (memberCount > 0) {
			JSONObject jsonResult = new JSONObject();
			jsonResult.put("memberId", memberId);
			jsonResult.put("nickName", nickName);
			jsonResult.put("kind", "noti"); // 클라이언트에서 알림인지 채팅 메시지인지 구분하기 위한 구분자
			jsonResult.put("message", nickName + "님이 퇴장하셨습니다.");
			jsonResult.put("memberCount", memberCount);
			
			for (WebSocketSession client : memberListOfChattingRoom)
				client.sendMessage(new TextMessage(jsonResult.toString()));
			
			logger.info("<- [jsonResult = {}]", jsonResult.toString());
		}
		else {
			// 채팅 방 인원이 0명이면 채팅 방 삭제
			chattingRoomManager.removeChattingRoom(chattingRoom);
			logger.info("<- [Removed Chatting Room!]");
		}
	}

}