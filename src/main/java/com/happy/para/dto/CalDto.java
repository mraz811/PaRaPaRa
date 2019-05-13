package com.happy.para.dto;

import java.io.Serializable;

public class CalDto implements Serializable {

	/**
	 * 매장일정 DTO
	 */
	private static final long serialVersionUID = -6220919999289380973L;
	private int cal_seq;
	private String cal_id;
	private String cal_title;
	private String cal_content;
	private String cal_start;
	private String cal_end;
	private String store_code;
	private String cal_regdate;
	
	public CalDto() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CalDto [cal_seq=" + cal_seq + ", cal_id=" + cal_id + ", cal_title=" + cal_title + ", cal_content="
				+ cal_content + ", cal_start=" + cal_start + ", cal_end=" + cal_end + ", store_code=" + store_code
				+ ", cal_regdate=" + cal_regdate + "]";
	}

	public int getCal_seq() {
		return cal_seq;
	}

	public void setCal_seq(int cal_seq) {
		this.cal_seq = cal_seq;
	}

	public String getCal_id() {
		return cal_id;
	}

	public void setCal_id(String cal_id) {
		this.cal_id = cal_id;
	}

	public String getCal_title() {
		return cal_title;
	}

	public void setCal_title(String cal_title) {
		this.cal_title = cal_title;
	}

	public String getCal_content() {
		return cal_content;
	}

	public void setCal_content(String cal_content) {
		this.cal_content = cal_content;
	}

	public String getCal_start() {
		return cal_start;
	}

	public void setCal_start(String cal_start) {
		this.cal_start = cal_start;
	}

	public String getCal_end() {
		return cal_end;
	}

	public void setCal_end(String cal_end) {
		this.cal_end = cal_end;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getCal_regdate() {
		return cal_regdate;
	}

	public void setCal_regdate(String cal_regdate) {
		this.cal_regdate = cal_regdate;
	}
	
}
