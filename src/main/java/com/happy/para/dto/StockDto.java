package com.happy.para.dto;

import java.io.Serializable;
import java.util.List;

public class StockDto implements Serializable {
	
	/**
	 * 재고 DTO
	 */
	private static final long serialVersionUID = 7958314995715576594L;
	private int stock_seq;
	private String store_code;
	private String stock_name;
	private int stock_qty;
	private List<StockDto> slists;
	private String item_name;
	private String item_delflag;
	private String stock_delflag;
		
	private ItemDto itemDto;
	
	
	public StockDto() {
	}
	
	public StockDto(int stock_seq, String store_code, String stock_name, int stock_qty, List<StockDto> slists,
			String item_name, String item_delflag, String stock_delflag, ItemDto itemDto) {
		super();
		this.stock_seq = stock_seq;
		this.store_code = store_code;
		this.stock_name = stock_name;
		this.stock_qty = stock_qty;
		this.slists = slists;
		this.item_name = item_name;
		this.item_delflag = item_delflag;
		this.stock_delflag = stock_delflag;
		this.itemDto = itemDto;
	}

	public String getStock_delflag() {
		return stock_delflag;
	}

	public void setStock_delflag(String stock_delflag) {
		this.stock_delflag = stock_delflag;
	}

	public int getStock_seq() {
		return stock_seq;
	}

	public void setStock_seq(int stock_seq) {
		this.stock_seq = stock_seq;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getStock_name() {
		return stock_name;
	}

	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}

	public int getStock_qty() {
		return stock_qty;
	}

	public void setStock_qty(int stock_qty) {
		this.stock_qty = stock_qty;
	}

	public ItemDto getItemDto() {
		return itemDto;
	}

	public void setItemDto(ItemDto itemDto) {
		this.itemDto = itemDto;
	}


	public List<StockDto> getSlists() {
		return slists;
	}


	public void setSlists(List<StockDto> slists) {
		this.slists = slists;
	}


	public String getItem_name() {
		return item_name;
	}


	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_delflag() {
		return item_delflag;
	}

	public void setItem_delflag(String item_delflag) {
		this.item_delflag = item_delflag;
	}

	@Override
	public String toString() {
		return "StockDto [stock_seq=" + stock_seq + ", store_code=" + store_code + ", stock_name=" + stock_name
				+ ", stock_qty=" + stock_qty + ", slists=" + slists + ", item_name=" + item_name + ", item_delflag="
				+ item_delflag + ", stock_delflag=" + stock_delflag + ", itemDto=" + itemDto + "]";
	}
}
