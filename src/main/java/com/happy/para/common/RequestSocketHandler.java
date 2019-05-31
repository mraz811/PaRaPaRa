package com.happy.para.common;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
		dto.setSender(mySessionId); // 999-99-9999
		dto.setReciver(targetId); // SEOUL01_06
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
		
		JSONObject obj = new JSONObject();
		JSONParser json = new JSONParser();
		obj = (JSONObject) json.parse(msg);
		System.out.println(obj.toJSONString());
		
		String menu = obj.get("text").toString();
		System.out.println("전달받은 메뉴 !!!"+menu);
		
		String myId = obj.get("id").toString();
		System.out.println("보내는 사람 아이디!!!!"+myId);
		
		String reciver = obj.get("to").toString();
		System.out.println("받는 사람 아이디!!!!"+reciver);
		
		
		//주문 현황 화면에 맞게 String으로 넘겨받은값 포맷 해주는거
		int menuLen = menu.length();
		int menuLenChange = menu.replaceAll(":", "").length();
		int arraySize = menuLen - menuLenChange;
		
		String[] menu_seq = new String[arraySize];
		String[] menu_name = new String[arraySize];
		String[] menu_cnt = new String[arraySize];
		String[] menu_price = new String[arraySize];
		String[] time = new String[arraySize];
		
		StringTokenizer st = new StringTokenizer(menu, ":");
		
		int num = 0;
		while (st.hasMoreTokens()) {
			String str = st.nextToken();
			String[] temp = str.split(",");
			menu_seq[num] = temp[0];
			menu_name[num] = temp[1];
			menu_cnt[num] = temp[2];
			menu_price[num] = temp[3];
			time[num] = temp[4];
			num++;
		}
		
		String request_menu = ""; //주문 메뉴
		for (int i = 0; i < menu_name.length; i++) {
			request_menu += menu_name[i]+" "+menu_cnt[i]+",";
		}
		int temp_price = 0;
		for (int i = 0; i < menu_price.length; i++) {
			temp_price += Integer.parseInt(menu_price[i]);
		}
		String request_price = Integer.toString(temp_price);//주문 메뉴 총가격
		String request_time = time[0];//주문 시간  hh:MM:ss
		////////////////////////////////////////////////// 
		
		rstJson.put("request_menu", request_menu);
		rstJson.put("request_price", request_price);
		rstJson.put("request_time", request_time);
		
		
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
		
		WebSocketDto reciveDto = null;
		reciveDto = map.get(sendDto.getReciver()); // 999-99-99999
		
//		if(reciveDto.getReciver().equals(myId)) {
//			webso
//		}
		
		String target = obj.get("to").toString();
		System.out.println("보낸 사람이 담당자일 때 받는 사람은 업주 : " + target);
		
//		rstJson.put("view", sendMessage);
//		rstJson.put("auth", senderAuth);
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
 
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionClosed(session, status);
	}
	

}
