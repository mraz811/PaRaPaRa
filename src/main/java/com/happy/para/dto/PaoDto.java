package com.happy.para.dto;

public class PaoDto {
	/**
	 * 	발주, 발주상태코드 테이블에 대한 DTO
	 */
	private int pao_seq;	// 발주 번호
	private String pao_date;	// 발주 날짜
	private int ps_code;	// 발주 상태 코드
	private String ps_name;	// 상태명
	private String store_code;	// 매장 코드
	private String store_name;	// 매장 이름
	

	public PaoDto() {
		// TODO Auto-generated constructor stub
	}

	public int getPao_seq() {
		return pao_seq;
	}

	public void setPao_seq(int pao_seq) {
		this.pao_seq = pao_seq;
	}

	public String getPao_date() {
		return pao_date;
	}

	public void setPao_date(String pao_date) {
		this.pao_date = pao_date;
	}

	public int getPs_code() {
		return ps_code;
	}

	public void setPs_code(int ps_code) {
		this.ps_code = ps_code;
	}

	public String getPs_name() {
		return ps_name;
	}

	public void setPs_name(String ps_name) {
		this.ps_name = ps_name;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	@Override
	public String toString() {
		return "PaoDto [pao_seq=" + pao_seq + ", pao_date=" + pao_date + ", ps_code=" + ps_code + ", ps_name=" + ps_name
				+ ", store_code=" + store_code + ", store_name=" + store_name + "]";
	}
	
	
}
