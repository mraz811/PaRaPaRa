package com.happy.para.dto;

import java.io.Serializable;

public class WebSocketDto implements Serializable {
	
	/**
	 * 채팅 DTO
	 */
	private static final long serialVersionUID = 8388471484915535998L;
	private int chat_seq;
	private String chat_title;
	private String chat_content;
	private int chat_scount;
	private int chat_rcount;
	private String chat_regdate;
	
	public WebSocketDto() {
		// TODO Auto-generated constructor stub
	}
	
	public WebSocketDto(String chat_title, String chat_content, int chat_scount, int chat_rcount) {
		super();
		this.chat_title = chat_title;
		this.chat_content = chat_content;
		this.chat_scount = chat_scount;
		this.chat_rcount = chat_rcount;
	}

	@Override
	public String toString() {
		return "WebSocketDto [chat_seq=" + chat_seq + ", chat_title=" + chat_title + ", chat_content=" + chat_content
				+ ", chat_scount=" + chat_scount + ", chat_rcount=" + chat_rcount + ", chat_regdate=" + chat_regdate
				+ "]";
	}

	public int getChat_seq() {
		return chat_seq;
	}

	public void setChat_seq(int chat_seq) {
		this.chat_seq = chat_seq;
	}

	public String getChat_title() {
		return chat_title;
	}

	public void setChat_title(String chat_title) {
		this.chat_title = chat_title;
	}

	public String getChat_content() {
		return chat_content;
	}

	public void setChat_content(String chat_content) {
		this.chat_content = chat_content;
	}

	public int getChat_scount() {
		return chat_scount;
	}

	public void setChat_scount(int chat_scount) {
		this.chat_scount = chat_scount;
	}

	public int getChat_rcount() {
		return chat_rcount;
	}

	public void setChat_rcount(int chat_rcount) {
		this.chat_rcount = chat_rcount;
	}

	public String getChat_regdate() {
		return chat_regdate;
	}

	public void setChat_regdate(String chat_regdate) {
		this.chat_regdate = chat_regdate;
	}
	
	

	
}
