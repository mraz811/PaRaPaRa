package com.happy.para.dto;

import java.io.Serializable;

public class AlbaDto implements Serializable {
	
	/**
	 * 아르바이트 DTO
	 */
	private static final long serialVersionUID = -6029400093014607393L;
	private int alba_seq;
	private String alba_name;
	private String alba_phone;
	private String alba_address;
	private int alba_timesal;
	private String alba_bank;
	private String alba_account;
	private String alba_delflag;
	private String alba_regdate;
	private String store_code;
	
	public AlbaDto() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AlbaDto [alba_seq=" + alba_seq + ", alba_name=" + alba_name + ", alba_phone=" + alba_phone
				+ ", alba_address=" + alba_address + ", alba_timesal=" + alba_timesal + ", alba_bank=" + alba_bank
				+ ", alba_account=" + alba_account + ", alba_delflag=" + alba_delflag + ", alba_regdate=" + alba_regdate
				+ ", store_code=" + store_code + "]";
	}

	public int getAlba_seq() {
		return alba_seq;
	}

	public void setAlba_seq(int alba_seq) {
		this.alba_seq = alba_seq;
	}

	public String getAlba_name() {
		return alba_name;
	}

	public void setAlba_name(String alba_name) {
		this.alba_name = alba_name;
	}

	public String getAlba_phone() {
		return alba_phone;
	}

	public void setAlba_phone(String alba_phone) {
		this.alba_phone = alba_phone;
	}

	public String getAlba_address() {
		return alba_address;
	}

	public void setAlba_address(String alba_address) {
		this.alba_address = alba_address;
	}


	public int getAlba_timesal() {
		return alba_timesal;
	}

	public void setAlba_timesal(int alba_timesal) {
		this.alba_timesal = alba_timesal;
	}

	public String getAlba_bank() {
		return alba_bank;
	}

	public void setAlba_bank(String alba_bank) {
		this.alba_bank = alba_bank;
	}

	public String getAlba_account() {
		return alba_account;
	}

	public void setAlba_account(String alba_account) {
		this.alba_account = alba_account;
	}

	public String getAlba_delflag() {
		return alba_delflag;
	}

	public void setAlba_delflag(String alba_delflag) {
		this.alba_delflag = alba_delflag;
	}

	public String getAlba_regdate() {
		return alba_regdate;
	}

	public void setAlba_regdate(String alba_regdate) {
		this.alba_regdate = alba_regdate;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}
	
}
