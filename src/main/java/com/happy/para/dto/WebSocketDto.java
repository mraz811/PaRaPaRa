package com.happy.para.dto;

import java.io.Serializable;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketDto implements Serializable {
	
	/**
	 * 채팅 DTO
	 */
	private static final long serialVersionUID = 8388471484915535998L;
	private String sender; 
	private String reciver;
	private String chatTarget;
	private String originalId;
	private WebSocketSession mySession;
	
	public WebSocketDto() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "WebSocketDto [sender=" + sender + ", reciver=" + reciver + ", chatTarget=" + chatTarget
				+ ", originalId=" + originalId + ", mySession=" + mySession + "]";
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public String getChatTarget() {
		return chatTarget;
	}

	public void setChatTarget(String chatTarget) {
		this.chatTarget = chatTarget;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public WebSocketSession getMySession() {
		return mySession;
	}

	public void setMySession(WebSocketSession mySession) {
		this.mySession = mySession;
	}
	
	
	
	

	
}
