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
	
	// WebSocket Server가 열렸을 때 실행되는 method
	// WebSocket Servet에 접속한 각 사용자들에게 고유한 WebSocketSession을 부여
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("afterConnectionEstablished()");
		// 1:1 채팅을 위해 접속한 사용자의 id, 사용자와 채팅을 할 대상 id, 자신의 WebSocketSession 정보를 담는 DTO 객체
		WebSocketDto dto = new WebSocketDto();
		super.afterConnectionEstablished(session);
		System.out.println("session : " + session);
		System.out.println("session id : " + session.getId());
		System.out.println("session toString : " + session.toString());
		System.out.println("session Uri : " + session.getUri());
		// WebSocket Server에 접속할 때 요청된 uri 값
		String getUri = session.getUri().toString();
		System.out.println("index num = " + getUri.indexOf("="));
		// WebSocket Server 접속 시 uri에 명시한 접속자 ID
		String mySessionId = getUri.substring(getUri.indexOf("=") + 1, getUri.indexOf("&"));
		System.out.println("my Session Id = " + mySessionId);
		// WebSocket Server 접속 시 uri에 명시한 대상 ID
		String targetId = getUri.substring(getUri.lastIndexOf("=")+1,getUri.length());
		System.out.println("target Id = " + targetId);
		
		// WebSocket Server에 접속한 사용자의 고유한 WebSocketSession 정보를 set
		dto.setMySession(session);
		// WebSocket Server에 접속 시 요청한 uri 값에서 구분한 접속자 ID를 set
		dto.setSender(mySessionId);
		// WebSocket Server에 접속 시 요청한 uri 값에서 구분한 채팅 대상 ID를 set
		dto.setReciver(targetId);
		// WebSocketSession 객체에서 자동으로 무작위로 부여한 고유한 값인 id를 key, 자신의 Web Page ID를 value로 넣음
		sessionInfo.put(session.getId(), mySessionId);
		// Web Page ID를 key, 채팅에 대한 정보들이 담겨있는 WebSocketDto 객체를 value로 넣음
		map.put(mySessionId, dto);
	}
	
	
	// View에서 Server로 데이터를 전송할 때 실행되는 method
	// WebSocket Server에 접속할 때 부여받은 WebSocketSession과 Server에서 View로 메시지를 전송할 때와 View에서 Server로 전송한 메시지를 받아올 때 사용하는 객체인 TextMessage가 Argument로 선언 
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage()");
		// View에서 Server로 전송된 값
		String msg = message.getPayload();
		System.out.println("넘어온 메시지 : " + msg);
		// Server에서 View로 값을 전송할 때 JSON형태로 보내기 위해 생성한 JSON 객체
		JSONObject rstJson = new JSONObject();
		
		
		if(msg != null) {
			// WebSocket Server에 처음 접속했을 시에만 접속한 사람의 ID를 받아오기 위해 구분자를 사용해 확인함
			if(msg.indexOf("#$nick_") > -1) {
				// WebSocket Server에 접속한 사람의 ID를 String에 저장
				String initMsg = msg.replace("#$nick_", "");
				// afterConnectionEstablished가 실행되었을 시 map에 저장한 채팅에 관한 정보를 담고 있는 WebSocketDto 객체를 생성
				WebSocketDto dto = map.get(initMsg);
				System.out.println("WebSocketDto : " + dto);
				// Json 타입에 key를 "view", value를 접속한 사람의 id를 포함한 환영 메시지를 저장
				rstJson.put("view", "<font color='green' size='4px'>" + initMsg + "님이 입장하였습니다. </font>");
				// Json 타입에 key를 "auth", value를 사용자들이 채팅을 입력하는 것과 구분하기 위해 특정지은 값 "enterCht"으로 저장
				rstJson.put("auth", "enterChat");
				
				// 채팅 대상자가 접속하지 않았을 경우
				if(map.get(dto.getReciver())== null) {
//					System.out.println("행");
					// 자신의 session에게만 메시지를 보냄
					session.sendMessage(new TextMessage(rstJson.toJSONString()));
					// 채팅 대상자 ID를 자신의 채팅에 대한 정보가 담긴 WebSocketDto 객체에 set, 채팅 내용을 전송할 때 사용 
					dto.setChatTarget(dto.getReciver());
				}else {
					// 채팅 대상자가 접속했을 경우
//					System.out.println("헤");
					// 채팅 대상의 WebSocketDto를 생성
					WebSocketDto rDto = map.get(dto.getReciver());
					// 채팅 대상의 WebSocketDto에 설정된 receiver(받는 사람)의 id가 현재 메시지를 보낸 사람의 id와 같은 경우
					if(rDto.getReciver().equals(initMsg)) {
						// 채팅 대상의 WebSocketDto에 설정된 WebSocketSession를 통해 WebSocketSession 객체 생성
						WebSocketSession rSession = rDto.getMySession();
						// 채팅 대상에게도 환영 메시지 전송
						rSession.sendMessage(new TextMessage(rstJson.toJSONString()));
					}
					System.out.println("둘 다 접속 시");
	//				session.sendMessage(new TextMessage(rstJson.toJSONString()));
					// 자신의 WebSocketDto에 
					dto.setChatTarget(rDto.getSender());
				}
	//			WebSocketDto rDto = map.get(dto.getReciver());
	//			System.out.println(rDto);
	//			WebSocketSession tSession = rDto.getMySession();
			}else if(msg.indexOf("%^filename_") > -1) {
			
			}else {	
				// View에서 보낸 Json 형태의 메시지를 받기 위해 생성한 JSONObject 객체
				JSONObject obj = new JSONObject();
				// View에서 보낸 Json 형태의 메시지를 JSONObject 타입으로 만들어 주기 위해
				JSONParser json = new JSONParser();
				obj = (JSONObject) json.parse(msg);
				System.out.println(obj.toJSONString());
				
				// 보낸 사람의 권한
				String senderAuth = obj.get("auth").toString();
				
				// 보낸 사람의 ID
				String myId = obj.get("id").toString();
				
				// 보낸 메시지
				String sendMessage = obj.get("text").toString();
				
				System.out.println(senderAuth);
				System.out.println(myId);
				System.out.println(sendMessage);
				
				// 보낸 사람의 WebSocketDto 객체를 담기 위해 선언
				WebSocketDto sendDto = null;
				// 보낸 사람의 WebSocketSession 객체를 담기 위해 선언
				WebSocketSession sendSession = null;
				if(map.get(myId) != null) {
					System.out.println("널이 아니랍니당~");
					// afterConnectionEstablished가 실행될 시 map에 담았던 WebSocketDto 객체를 생성
					sendDto = map.get(myId);
					// sendDto에 담긴 WebSocketSession 객체 선언
					sendSession = sendDto.getMySession();
				}else {
					System.out.println("널이랍니당~");
					
				}
				System.out.println("SendDto 값들이영 : " + sendDto);
				System.out.println("SendSession 값들이영 : " + sendSession);
				if(senderAuth.equalsIgnoreCase("U")) {
					// 메시지를 보낼 때 설정했던 받는 사람 ID
					String target = obj.get("to").toString();
					System.out.println("보낸 사람이 업주일 때 받는 사람은 담당자 : " + target);
					// sendDto에 담긴 받는 사람 ID를 통해 map에 담긴 받는 사람 WebSocketDto 객체 생성
					WebSocketDto targetDto = map.get(sendDto.getReciver());
					System.out.println("targetDto 입니다 : " + targetDto);
					System.out.println("메시지에용" + sendMessage);
					rstJson.put("view", sendMessage);
					rstJson.put("auth", senderAuth);
					
					// 보낸 사람 WebSocketSession에 메시지 보냄
					sendSession.sendMessage(new TextMessage(rstJson.toString()));
					if(targetDto != null) {
						System.out.println("if문 안에서의 targerDto 값 : " + targetDto);
						// 채팅 대상 WebSocketDto에 담긴 채팅 상대가 바라보는 채팅 대상 ID
						String chatTarget = targetDto.getChatTarget();
						System.out.println("업주가 채팅 쳤을 시 chatTarget : " + chatTarget);
						System.out.println("내 아이디 : " + myId);
						// chatTarget과 내 ID가 같을 경우(즉 서로가 서로에게 채팅을 건 상황)
						// 업주는 한 명의 담당자와 채팅을 하지만(바라보는 대상이 한 명), 담당자는 여러 명의 업주와 채팅이 가능하므로 
						if(chatTarget != null && chatTarget.equals(myId)) {
							WebSocketSession targetSession = targetDto.getMySession();
							System.out.println("targetSession 입니다 : " + targetSession);
							// 보내는 사람이 선택한 채팅 상대에게 메시지 출력
							targetSession.sendMessage(new TextMessage(rstJson.toString()));
						}
					}
				}
				// 보내는 사람이 담당자일 경우 업주의 WebSocketDto에 reciver 변수에는 자신의 아이디만 있을 것이므로 위와 같은 조건은 필요치 않음
				if(senderAuth.equalsIgnoreCase("A")) {
					String target = obj.get("to").toString();
					System.out.println("보낸 사람이 담당자일 때 받는 사람은 업주 : " + target);
					
					rstJson.put("view", sendMessage);
					rstJson.put("auth", senderAuth);
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
