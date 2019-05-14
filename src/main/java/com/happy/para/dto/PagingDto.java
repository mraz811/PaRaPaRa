package com.happy.para.dto;

import java.io.Serializable;

public class PagingDto implements Serializable{ 
	
	private static final long serialVersionUID = 430946724192269945L;
// 페이징 처리 할 예정

	private int pageList; // 출력할 페이지 번호 개수
	private int index; // 출력할 페이지 번호 
	private int pageNum; // 출력할 페이지 시작번호 (시작페이지)
	private int listNum; // 출력할 리스트 개수
	private int total; // 리스트 총 개수
	
	{
		pageList = 5;
		index = 0;
		pageNum = 1;
		listNum = 5;
	}
	
	public PagingDto() {
		super();
	}

	public PagingDto(String index, String pageNum, String listNum) {
		if(index != null) {
			this.index = Integer.parseInt(index);
		}
		if(pageNum != null) {
			this.pageNum = Integer.parseInt(pageNum);
		}
		if(listNum != null) {
			this.listNum = Integer.parseInt(listNum);
		}
	}
	
	public int getStart() {
		return index*listNum +1;
	}
	
	public int getEnd() {
		return (index*listNum)+listNum;
	}
	
	public int getCount() {
		int temp = total-listNum*(pageNum-1);
		int min = temp/listNum;
		int count = 0;
		if(temp%listNum !=0) {
			min++;
		}
		if(temp <= listNum) {
			count = pageNum;
		} else if(min <= pageList) {
			count = min+pageNum-1;
		} else {
			count = pageList+pageNum-1;
		}
		
		return count;
	}

	public int getPageList() {
		return pageList;
	}

	public void setPageList(int pageList) {
		this.pageList = pageList;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getListNum() {
		return listNum;
	}

	public void setListNum(int listNum) {
		this.listNum = listNum;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "PagingDto [pageList=" + pageList + ", index=" + index + ", pageNum=" + pageNum + ", listNum=" + listNum
				+ ", total=" + total + "]";
	}

	
}
