package com.happy.para.model;

import java.util.List;

import com.happy.para.dto.ItemDto;

public interface Item_IService {
	
	// 품목 조회
	public List<ItemDto> itemList();
	
	// 품목 등록
	public boolean itemInsert(ItemDto dto);
	
	// 품목 수정
	public boolean itemModify(ItemDto dto);
	
	// 품목 삭제
	public boolean itemDelete(String item_seq);
	
	// 품목 상세 조회
	public ItemDto itemDetail(String item_seq);
	
	// 품목명 중복 체크를 위한 품목명 조회
	public int itemNameChk(String item_name);
	
	// 품목 검색
	public List<ItemDto> itemSearchList(String item_name);
}
