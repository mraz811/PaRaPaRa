package com.happy.para.dto;

import java.io.Serializable;

public class NoticeDto implements Serializable {
	
	/**
	 * 공지사항 DTO
	 */
	private static final long serialVersionUID = -998168435916756534L;
	private int notice_seq;
	private String notice_title;
	private String notice_id;
	private String notice_regdate;
	private String notice_content;
	private String notice_delflag;
	
	public NoticeDto() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "NoticeDto [notice_seq=" + notice_seq + ", notice_title=" + notice_title + ", notice_id=" + notice_id
				+ ", notice_regdate=" + notice_regdate + ", notice_content=" + notice_content + ", notice_delflag="
				+ notice_delflag + "]";
	}
	
	public int getNotice_seq() {
		return notice_seq;
	}

	public void setNotice_seq(int notice_seq) {
		this.notice_seq = notice_seq;
	}

	public String getNotice_title() {
		return notice_title;
	}

	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}

	public String getNotice_id() {
		return notice_id;
	}

	public void setNotice_id(String notice_id) {
		this.notice_id = notice_id;
	}

	public String getNotice_regdate() {
		return notice_regdate;
	}

	public void setNotice_regdate(String notice_regdate) {
		this.notice_regdate = notice_regdate;
	}

	public String getNotice_content() {
		return notice_content;
	}

	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}

	public String getNotice_delflag() {
		return notice_delflag;
	}

	public void setNotice_delflag(String notice_delflag) {
		this.notice_delflag = notice_delflag;
	}
	
}
