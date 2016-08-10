package com.is.mpg.interceptor;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * Websocket Interceptor 
 * 
 * @author iskwon
 */
public class ChattingWebsocketInterceptor extends HttpSessionHandshakeInterceptor {
	
	/**
	 * 이 메소드가 구현되어 있어야 Websocket Handler로 HttpSession이 넘어간다.
	 */
	@Override
	public boolean beforeHandshake(
			ServerHttpRequest request,
	        ServerHttpResponse response, 
	        WebSocketHandler wsHandler,
	        Map<String, Object> attributes) throws Exception {
	    return super.beforeHandshake(request, response, wsHandler, attributes);
	}
	
	@Override
	public void afterHandshake(
			ServerHttpRequest request,
			ServerHttpResponse response,
			WebSocketHandler wsHandler,
			Exception ex) {
		super.afterHandshake(request, response, wsHandler, ex);
	}
	
}
