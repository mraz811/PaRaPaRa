package com.happy.para.dto;

import java.io.Serializable;

public class ChatDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3405201211888504355L;
	private String chat_seq;
	private String chat_title;
	private String chat_content;
	private int chat_scount;
	private int chat_rcount;
	private String chat_regdate;
	
	public ChatDto() {
	}

	@Override
	public String toString() {
		return "ChatDto [chat_seq=" + chat_seq + ", chat_title=" + chat_title + ", chat_content=" + chat_content
				+ ", chat_scount=" + chat_scount + ", chat_rcount=" + chat_rcount + ", chat_regdate=" + chat_regdate
				+ "]";
	}

	public String getChat_seq() {
		return chat_seq;
	}

	public void setChat_seq(String chat_seq) {
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
