package com.happy.para.dto;

import java.io.Serializable;

public class RequestDto implements Serializable {
	
	/**
	 * 주문 DTO
	 */
	private static final long serialVersionUID = -2123126722438382555L;
	private int request_seq;
	private String request_time;
	private String request_menu;
	private int request_price;
	private int os_code;
	private String store_code;
	private String request_bank;
	private String request_account;
	
	public RequestDto() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "RequestDto [request_seq=" + request_seq + ", request_time=" + request_time + ", request_menu="
				+ request_menu + ", request_price=" + request_price + ", os_code=" + os_code + ", store_code="
				+ store_code + ", request_bank=" + request_bank + ", request_account=" + request_account + "]";
	}

	public int getRequest_seq() {
		return request_seq;
	}

	public void setRequest_seq(int request_seq) {
		this.request_seq = request_seq;
	}

	public String getRequest_time() {
		return request_time;
	}

	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}

	public String getRequest_menu() {
		return request_menu;
	}

	public void setRequest_menu(String request_menu) {
		this.request_menu = request_menu;
	}

	public int getRequest_price() {
		return request_price;
	}

	public void setRequest_price(int request_price) {
		this.request_price = request_price;
	}

	public int getOs_code() {
		return os_code;
	}

	public void setOs_code(int os_code) {
		this.os_code = os_code;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getRequest_bank() {
		return request_bank;
	}

	public void setRequest_bank(String request_bank) {
		this.request_bank = request_bank;
	}

	public String getRequest_account() {
		return request_account;
	}

	public void setRequest_account(String request_account) {
		this.request_account = request_account;
	}

}
