package com.happy.para.dto;

import java.io.Serializable;

public class AdminDto implements Serializable{
	
	/**
	 * 담당자 및 관리자 DTO
	 */
	private static final long serialVersionUID = 1021033342219462428L;
	private int admin_id;
	private String admin_pw;
	private String admin_name;
	private String admin_phone;
	private String admin_email;
	private String loc_code;
	private String auth;
	private String admin_delflag;
	
	public AdminDto() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AdminDto [admin_id=" + admin_id + ", admin_pw=" + admin_pw + ", admin_name=" + admin_name
				+ ", admin_phone=" + admin_phone + ", admin_email=" + admin_email + ", loc_code=" + loc_code + ", auth="
				+ auth + ", admin_delflag=" + admin_delflag + "]";
	}

	public int getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}

	public String getAdmin_pw() {
		return admin_pw;
	}

	public void setAdmin_pw(String admin_pw) {
		this.admin_pw = admin_pw;
	}

	public String getAdmin_name() {
		return admin_name;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}

	public String getAdmin_phone() {
		return admin_phone;
	}

	public void setAdmin_phone(String admin_phone) {
		this.admin_phone = admin_phone;
	}

	public String getAdmin_email() {
		return admin_email;
	}

	public void setAdmin_email(String admin_email) {
		this.admin_email = admin_email;
	}

	public String getLoc_code() {
		return loc_code;
	}

	public void setLoc_code(String loc_code) {
		this.loc_code = loc_code;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getAdmin_delflag() {
		return admin_delflag;
	}

	public void setAdmin_delflag(String admin_delflag) {
		this.admin_delflag = admin_delflag;
	}

}
