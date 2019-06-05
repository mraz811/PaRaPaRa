package com.happy.para.model;

import java.util.List;
import java.util.Map;

import com.happy.para.dto.ItemDto;
import com.happy.para.dto.StockDto;

public interface Stock_IDao {

	// 재고 조회
	public List<StockDto> stockList();

	// 매장 조회 (신규 매장과 기존 매장을 구분하기 위해 필요)
	public List<String> selStore();

	// 매장별 재고 조회
	public List<StockDto> stockOne(String store_code);

	// 재고 수정
	public boolean stockModify(StockDto dto);

	// 재고 추가
	public boolean stockAdd(StockDto dto);

	// 재고 삭제
	public boolean stockDelete(String stock_seq);

	// 매장별 재고 삭제
	public boolean stockDeleteStore(String store_code);
	
	// 재고 검색
	public List<StockDto> stockSearchList(Map<String, String> map);
	
}
