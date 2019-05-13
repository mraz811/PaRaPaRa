package com.happy.para.dto;

import java.io.Serializable;

public class ReplyDto implements Serializable {
	
	/**
	 * 댓글 DTO
	 */
	private static final long serialVersionUID = 3755316305532385454L;
	private int reply_seq;
	private int notice_seq;
	private String reply_id;
	private String reply_regdate;
	private String reply_content;
	
	public ReplyDto() {
	}

	@Override
	public String toString() {
		return "ReplyDto [reply_seq=" + reply_seq + ", notice_seq=" + notice_seq + ", reply_id=" + reply_id
				+ ", reply_regdate=" + reply_regdate + ", reply_content=" + reply_content + "]";
	}

	public int getReply_seq() {
		return reply_seq;
	}

	public void setReply_seq(int reply_seq) {
		this.reply_seq = reply_seq;
	}

	public int getNotice_seq() {
		return notice_seq;
	}

	public void setNotice_seq(int notice_seq) {
		this.notice_seq = notice_seq;
	}

	public String getReply_id() {
		return reply_id;
	}

	public void setReply_id(String reply_id) {
		this.reply_id = reply_id;
	}

	public String getReply_regdate() {
		return reply_regdate;
	}

	public void setReply_regdate(String reply_regdate) {
		this.reply_regdate = reply_regdate;
	}

	public String getReply_content() {
		return reply_content;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	
}
