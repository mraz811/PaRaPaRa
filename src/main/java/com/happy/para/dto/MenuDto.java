package com.happy.para.dto;

import java.io.Serializable;

public class MenuDto implements Serializable {
	
	/**
	 * 메뉴 DTO
	 */
	private static final long serialVersionUID = 7029859037591515817L;
	private int menu_seq;
	private String menu_name;
	private int menu_price;
	private String menu_category;
	private String menu_delflag;
	
	public MenuDto() {
	}

	@Override
	public String toString() {
		return "MenuDto [menu_seq=" + menu_seq + ", menu_name=" + menu_name + ", menu_price=" + menu_price
				+ ", menu_category=" + menu_category + ", menu_delflag=" + menu_delflag + "]";
	}

	public int getMenu_seq() {
		return menu_seq;
	}

	public void setMenu_seq(int menu_seq) {
		this.menu_seq = menu_seq;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public int getMenu_price() {
		return menu_price;
	}

	public void setMenu_price(int menu_price) {
		this.menu_price = menu_price;
	}

	public String getMenu_category() {
		return menu_category;
	}

	public void setMenu_category(String menu_category) {
		this.menu_category = menu_category;
	}

	public String getMenu_delflag() {
		return menu_delflag;
	}

	public void setMenu_delflag(String menu_delflag) {
		this.menu_delflag = menu_delflag;
	}

}
