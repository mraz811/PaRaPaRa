package com.happy.para.model;

import java.util.List;
import java.util.Map;

import com.happy.para.dto.ItemDto;
import com.happy.para.dto.PaoDto;
import com.happy.para.dto.StockDto;

public interface Pao_IDao {

	// 업주 : 발주 리스트 조회
	public List<PaoDto> paoList(String store_code);
	
	// 업주 : 발주 상태 선택 조회 및  매장 발주 날짜 선택 조회
	public List<PaoDto> paoSelectStatusDate(Map<String, Object> map);

	// 업주 : 발주 신청 시 재고 목록 조회
	public List<StockDto> paoStockList(String store_code);
	
	// 업주 : 발주 상세보기
	public List<ItemDto> paoDetail(String pao_seq);
	
	// 업주 : 발주 신청(발주 테이블 INSERT)
	public boolean paoInsert(Map<String, String> map);
	
	// 업주 : 발주 신청(발주 품목 테이블 INSERT)
	public boolean piInsert(Map<String, String> map);
}
