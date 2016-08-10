package com.is.mpg.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

import com.is.mpg.vo.ChattingRoom;

@Service
public interface ChattingService {
	
	public ChattingRoom createChattingRoom(String roomName);
	public List<ChattingRoom> getChattingList();
	public JSONArray getChattingListToJsonArray();
	
}
