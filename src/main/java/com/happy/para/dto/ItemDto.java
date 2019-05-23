package com.happy.para.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ItemDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4189243757923100025L;
	private int item_seq;
	private String item_name;
	private int item_price;
	private int pi_seq;
	private int pi_qty;
	private int pao_seq;
	private List<ItemDto> ilists;
	private String[] item_seqs;
	private String[] pi_qtys;
	
	public ItemDto() {
		// TODO Auto-generated constructor stub
	}

	public ItemDto(int pi_seq, String[] item_seqs, String[] pi_qtys, int pao_seq) {
		super();
		this.pi_seq = pi_seq;
		this.item_seqs = item_seqs;
		this.pi_qtys = pi_qtys;
		this.pao_seq = pao_seq;
	}


	public int getItem_seq() {
		return item_seq;
	}

	public void setItem_seq(int item_seq) {
		this.item_seq = item_seq;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public int getItem_price() {
		return item_price;
	}

	public void setItem_price(int item_price) {
		this.item_price = item_price;
	}

	public int getPi_seq() {
		return pi_seq;
	}

	public void setPi_seq(int pi_seq) {
		this.pi_seq = pi_seq;
	}

	public int getPi_qty() {
		return pi_qty;
	}

	public void setPi_qty(int pi_qty) {
		this.pi_qty = pi_qty;
	}

	public int getPao_seq() {
		return pao_seq;
	}

	public void setPao_seq(int pao_seq) {
		this.pao_seq = pao_seq;
	}

	public List<ItemDto> getIlists() {
		return ilists;
	}

	public void setIlists(List<ItemDto> ilists) {
		this.ilists = ilists;
	}
	
	public String[] getItem_seqs() {
		return item_seqs;
	}

	public void setItem_seqs(String[] item_seqs) {
		this.item_seqs = item_seqs;
	}

	public String[] getPi_qtys() {
		return pi_qtys;
	}

	public void setPi_qtys(String[] pi_qtys) {
		this.pi_qtys = pi_qtys;
	}

	@Override
	public String toString() {
		return "ItemDto [item_seq=" + item_seq + ", item_name=" + item_name + ", item_price=" + item_price + ", pi_seq="
				+ pi_seq + ", pi_qty=" + pi_qty + ", pao_seq=" + pao_seq + ", ilists=" + ilists + ", item_seqs="
				+ Arrays.toString(item_seqs) + ", pi_qtys=" + Arrays.toString(pi_qtys) + "]";
	}
	
	
}
