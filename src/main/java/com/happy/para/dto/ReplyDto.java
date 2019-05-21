package com.happy.para.dto;

import java.io.Serializable;

public class ReplyDto implements Serializable {
	
	/**
	 * 댓글 DTO
	 */
	private static final long serialVersionUID = 3755316305532385454L;
	private String reply_seq;
	private String notice_seq;
	private String reply_id;
	private String reply_regdate;
	private String reply_content;
	
	public ReplyDto() {
	}

	public ReplyDto(String reply_seq, String notice_seq, String reply_id, String reply_regdate, String reply_content) {
		super();
		this.reply_seq = reply_seq;
		this.notice_seq = notice_seq;
		this.reply_id = reply_id;
		this.reply_regdate = reply_regdate;
		this.reply_content = reply_content;
	}

	public String getReply_seq() {
		return reply_seq;
	}

	public void setReply_seq(String reply_seq) {
		this.reply_seq = reply_seq;
	}

	public String getNotice_seq() {
		return notice_seq;
	}

	public void setNotice_seq(String notice_seq) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ReplyDto [reply_seq=" + reply_seq + ", notice_seq=" + notice_seq + ", reply_id=" + reply_id
				+ ", reply_regdate=" + reply_regdate + ", reply_content=" + reply_content + "]";
	}

	
}
