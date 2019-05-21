package com.happy.para.dto;

import java.io.Serializable;

public class CalDto implements Serializable {

	/**
	 * 매장일정 DTO
	 */
	private static final long serialVersionUID = -6220919999289380973L;
	private String cal_seq;
	private char cal_id;
	private String cal_title;
	private String cal_content;
	private String cal_start;
	private String cal_end;
	private String store_code;
	
	public CalDto() {
	}

	public CalDto(String cal_seq, String cal_title, String cal_content, String cal_start, String cal_end) {
		super();
		this.cal_seq = cal_seq;
		this.cal_title = cal_title;
		this.cal_content = cal_content;
		this.cal_start = cal_start;
		this.cal_end = cal_end;
	}

	public CalDto(String cal_seq, char cal_id, String cal_title, String cal_content, String cal_start, String cal_end,
			String store_code) {
		super();
		this.cal_seq = cal_seq;
		this.cal_id = cal_id;
		this.cal_title = cal_title;
		this.cal_content = cal_content;
		this.cal_start = cal_start;
		this.cal_end = cal_end;
		this.store_code = store_code;
	}

	public String getCal_seq() {
		return cal_seq;
	}

	public void setCal_seq(String cal_seq) {
		this.cal_seq = cal_seq;
	}

	public char getCal_id() {
		return cal_id;
	}

	public void setCal_id(char cal_id) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CalDto [cal_seq=" + cal_seq + ", cal_id=" + cal_id + ", cal_title=" + cal_title + ", cal_content="
				+ cal_content + ", cal_start=" + cal_start + ", cal_end=" + cal_end + ", store_code=" + store_code
				+ "]";
	}

	
	
}
