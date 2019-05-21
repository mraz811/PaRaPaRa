package com.happy.para.common;

import java.util.HashMap;
import java.util.Map;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.happy.para.dto.WebSocketDto;


@Component(value = "wsChat.do")
public class MySocketHandler extends TextWebSocketHandler{

	private Map<String, WebSocketDto> map;
	
	private Map<String, String> sessionInfo ;
	public MySocketHandler() {
		sessionInfo = new HashMap<String, String>();
		map = new HashMap<String, WebSocketDto>();
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("afterConnectionEstablished()");
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
		
		if(msg != null) {
			if(msg.indexOf("#$nick_") > -1) {
				String initMsg = msg.replace("#$nick_", "");
				WebSocketDto dto = map.get(initMsg);
				System.out.println("WebSocketDto : " + dto);
				rstJson.put("view", "<font color='green' size='4px'>" + initMsg + "님이 입장하였습니다. </font>");
				if(map.get(dto.getReciver())== null) {
					System.out.println("행");
					session.sendMessage(new TextMessage(rstJson.toJSONString()));
				}else {
					System.out.println("헤");
					WebSocketDto rDto = map.get(dto.getReciver());
					if(rDto.getReciver().equals(initMsg)) {
						WebSocketSession rSession = rDto.getMySession();
						rSession.sendMessage(new TextMessage(rstJson.toJSONString()));
					}
					System.out.println("둘 다 접속 시");
	//				session.sendMessage(new TextMessage(rstJson.toJSONString()));
					dto.setChatTarget(rDto.getSender());
				}
	//			WebSocketDto rDto = map.get(dto.getReciver());
	//			System.out.println(rDto);
	//			WebSocketSession tSession = rDto.getMySession();
			}else if(msg.indexOf("%^filename_") > -1) {
			
			}else {	
				JSONObject obj = new JSONObject();
				JSONParser json = new JSONParser();
				obj = (JSONObject) json.parse(msg);
				System.out.println(obj.toJSONString());
				
				String senderAuth = obj.get("auth").toString();
				
				String myId = obj.get("id").toString();
				
				String sendMessage = obj.get("text").toString();
				
				System.out.println(senderAuth);
				System.out.println(myId);
				System.out.println(sendMessage);
				
				WebSocketDto sendDto = null;
				WebSocketSession sendSession = null;
				if(map.get(myId) != null) {
					System.out.println("널이 아니랍니당~");
					sendDto = map.get(myId);
					sendSession = sendDto.getMySession();
				}else {
					System.out.println("널이랍니당~");
					
				}
				System.out.println("SendDto 값들이영 : " + sendDto);
				System.out.println("SendSession 값들이영 : " + sendSession);
				if(senderAuth.equalsIgnoreCase("U")) {
					String target = obj.get("to").toString();
					System.out.println("보낸 사람이 업주일 때 받는 사람은 담당자 : " + target);
					WebSocketDto targetDto = map.get(sendDto.getReciver());
					System.out.println("targetDto 입니다 : " + targetDto);
					System.out.println("메시지에용" + sendMessage);
					rstJson.put("view", sendMessage);
					sendSession.sendMessage(new TextMessage(rstJson.toString()));
					if(targetDto != null) {
						String chatTarget = targetDto.getChatTarget();
						System.out.println("업주가 채팅 쳤을 시 chatTarget : " + chatTarget);
						System.out.println("내 아이디 : " + myId);
						if(chatTarget != null && chatTarget.equals(myId)) {
							WebSocketSession targetSession = targetDto.getMySession();
							System.out.println("targetSession 입니다 : " + targetSession);
							targetSession.sendMessage(new TextMessage(rstJson.toString()));
						}
					}
				}
				if(senderAuth.equalsIgnoreCase("A")) {
					String target = obj.get("to").toString();
					System.out.println("보낸 사람이 담당자일 때 받는 사람은 업주 : " + target);
					
					rstJson.put("view", sendMessage);
					if(map.get(target) == null) {
						System.out.println("상대방이 접속하지 않음");
						sendSession.sendMessage(new TextMessage(rstJson.toString()));
					}else {
						WebSocketDto rDto = map.get(target);
						System.out.println(rDto);
						WebSocketSession rSession = rDto.getMySession();
						sendSession.sendMessage(new TextMessage(rstJson.toString()));
						rSession.sendMessage(new TextMessage(rstJson.toString()));
						
					}
					
				}
				
			}
		} 
		
	}
	
	


	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		String sessionId = sessionInfo.get(session.getId());
		map.remove(sessionId);
//		String sessionId = session
	}

}
