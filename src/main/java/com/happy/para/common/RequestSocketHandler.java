package com.happy.para.common;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.happy.para.dto.WebSocketDto;

@Component(value = "wsRequest.do")
public class RequestSocketHandler extends TextWebSocketHandler{

	private Map<String, WebSocketDto> map;
	
	private Map<String, String> sessionInfo ;
	public RequestSocketHandler() {
		sessionInfo = new HashMap<String, String>();
		map = new HashMap<String, WebSocketDto>();
	}
	

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("만솔만솔afterConnectionEstablished()");
		WebSocketDto dto = new WebSocketDto();
		super.afterConnectionEstablished(session);
		System.out.println("session : " + session);
		System.out.println("session id : " + session.getId());
		System.out.println("session toString : " + session.toString());
		System.out.println("session Uri : " + session.getUri());
		String getUri = session.getUri().toString();
		System.out.println("index num = " + getUri.indexOf("="));
		String mySessionId = getUri.substring(getUri.indexOf("=") + 1, getUri.indexOf("&"));
		System.out.println("my Session Id = " + mySessionId);
		String targetId = getUri.substring(getUri.lastIndexOf("=")+1,getUri.length());
		System.out.println("target Id = " + targetId);
		
		dto.setMySession(session);
		dto.setSender(mySessionId);
		dto.setReciver(targetId);
		sessionInfo.put(session.getId(), mySessionId);
		map.put(mySessionId, dto);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage()");
		String msg = message.getPayload();
		System.out.println("넘어온 메시지 : " + msg);
		JSONObject rstJson = new JSONObject();
		// TODO Auto-generated method stub
		super.handleTextMessage(session, message);
	}
 
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionClosed(session, status);
	}
	

}
