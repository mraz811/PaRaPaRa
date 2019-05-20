package com.happy.para.model;

import java.util.List;
import java.util.Map;

import com.happy.para.dto.PaoDto;
import com.happy.para.dto.StockDto;

public interface Pao_IDao {

	// 업주 : 발주 리스트 조회
	public List<PaoDto> paoList(String store_code);
	
	// 업주 : 발주 상태 선택 조회
	public List<PaoDto> paoSelectStatus(Map<String, Object> map);
	
	// 업주 : 매장 발주 날짜 선택 조회
	public List<PaoDto> paoSelectDate(Map<String, String>map);
	
	// 업주 : 발주 신청 시 재고 목록 조회
	public List<StockDto> paoStockList(String store_code);
}
