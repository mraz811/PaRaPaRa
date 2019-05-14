package com.happy.para.dto;

import java.io.Serializable;

public class OwnerDto implements Serializable {
	
	/**
	 * 업주 DTO
	 */
	private static final long serialVersionUID = -4441648223131200889L;
	private int owner_seq;
	private String owner_id;
	private String owner_pw;
	private String owner_name;
	private String owner_phone;
	private String owner_email;
	private String auth;
	private String store_code;
	private String owner_start;
	private String owner_end;
	private String owner_menu;
	
	public OwnerDto() {
	}

	public OwnerDto(String owner_id, String owner_pw) {
		super();
		this.owner_id = owner_id;
		this.owner_pw = owner_pw;
	}

	@Override
	public String toString() {
		return "OwnerDto [owner_seq=" + owner_seq + ", owner_id=" + owner_id + ", owner_pw=" + owner_pw
				+ ", owner_name=" + owner_name + ", owner_phone=" + owner_phone + ", owner_email=" + owner_email
				+ ", auth=" + auth + ", store_code=" + store_code + ", owner_start=" + owner_start + ", owner_end="
				+ owner_end + ", owner_menu=" + owner_menu + "]";
	}

	public int getOwner_seq() {
		return owner_seq;
	}


	public void setOwner_seq(int owner_seq) {
		this.owner_seq = owner_seq;
	}


	public String getOwner_id() {
		return owner_id;
	}


	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}


	public String getOwner_pw() {
		return owner_pw;
	}


	public void setOwner_pw(String owner_pw) {
		this.owner_pw = owner_pw;
	}


	public String getOwner_name() {
		return owner_name;
	}


	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}


	public String getOwner_phone() {
		return owner_phone;
	}


	public void setOwner_phone(String owner_phone) {
		this.owner_phone = owner_phone;
	}


	public String getOwner_email() {
		return owner_email;
	}


	public void setOwner_email(String owner_email) {
		this.owner_email = owner_email;
	}


	public String getAuth() {
		return auth;
	}


	public void setAuth(String auth) {
		this.auth = auth;
	}


	public String getStore_code() {
		return store_code;
	}


	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}


	public String getOwner_start() {
		return owner_start;
	}


	public void setOwner_start(String owner_start) {
		this.owner_start = owner_start;
	}


	public String getOwner_end() {
		return owner_end;
	}


	public void setOwner_end(String owner_end) {
		this.owner_end = owner_end;
	}


	public String getOwner_menu() {
		return owner_menu;
	}


	public void setOwner_menu(String owner_menu) {
		this.owner_menu = owner_menu;
	}
	
}
