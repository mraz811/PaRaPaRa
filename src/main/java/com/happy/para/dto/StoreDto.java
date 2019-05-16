package com.happy.para.dto;

import java.io.Serializable;

public class StoreDto implements Serializable {
	
	/**
	 * 매장 DTO
	 */
	private static final long serialVersionUID = -7485400476816015687L;
	private String store_code;
	private String loc_code;
	private String store_phone;
	private String store_name;
	private String store_address;
	private int admin_id;
	private String store_delflag;
	private String owner_reg;
	
	public StoreDto() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "StoreDto [store_code=" + store_code + ", loc_code=" + loc_code + ", store_phone=" + store_phone
				+ ", store_name=" + store_name + ", store_address=" + store_address + ", admin_id=" + admin_id
				+ ", store_delflag=" + store_delflag + ", owner_reg=" + owner_reg + "]";
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getLoc_code() {
		return loc_code;
	}

	public void setLoc_code(String loc_code) {
		this.loc_code = loc_code;
	}

	public String getStore_phone() {
		return store_phone;
	}

	public void setStore_phone(String store_phone) {
		this.store_phone = store_phone;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getStore_address() {
		return store_address;
	}

	public void setStore_address(String store_address) {
		this.store_address = store_address;
	}

	public int getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}

	public String getStore_delflag() {
		return store_delflag;
	}

	public void setStore_delflag(String store_delflag) {
		this.store_delflag = store_delflag;
	}

	public String getOwner_reg() {
		return owner_reg;
	}

	public void setOwner_reg(String owner_reg) {
		this.owner_reg = owner_reg;
	}
	
}
