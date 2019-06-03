package com.happy.para.model;

import java.util.List;
import java.util.Map;

import com.happy.para.dto.ItemDto;
import com.happy.para.dto.PaoDto;
import com.happy.para.dto.StockDto;

public interface Pao_IService {

	// 업주 : 발주 리스트 조회(페이징)
	public List<PaoDto> paoList(Map<String, Object> map);
	
	// 업주 : 발주 리스트 갯수(페이징)
	public int paoListRow(String store_code);
	
	// 담당주 : 담당 지역의 매장 발주 리스트 조회(페이징)
	public List<PaoDto> adminPaoList(Map<String, Object> map);
	
	// 업주 : 지역의 매장 발주 리스트 갯수(페이징)
	public int adminPaoListRow(String store_code);
	
	// 업주 : 발주 상태 선택 조회 및 매장 발주 날짜 선택 조회(페이징)
	public List<PaoDto> paoSelectStatusDate(Map<String, Object> map);
	
	// 담당자 : 발주 상태 선택 조회 및  매장 발주 날짜 선택 조회(페이징)
	public List<PaoDto> adimPaoSelectStatusDate(Map<String, Object> map);
	
	// 업주 : 매장 발주 상태 선택 조회 및 매장 발주 날짜 선택 조회한 발주 내역 갯수(페이징)
	public int paoStatusListRow(Map<String, Object> map);
	
	// 담당자 : 매장 발주 상태 선택 조회 및 매장 발주 날짜 선택 조회한 발주 내역 갯수(페이징)
	public int adminPaoStatusListRow(Map<String, Object> map);

	// 업주 : 발주 상세보기(발주)
	public PaoDto paoDetail(Map<String, String> map);
	
	// 업주 : 발주 상세보기(발주품목)
	public List<ItemDto> paoPiDetail(String pao_seq);
	
	// 업주 : 발주 신청 시 재고 목록 조회
	public List<StockDto> paoStockList(String store_code);
	
	// 업주 : 발주 신청(발주 테이블 INSERT, 발주 품목 테이블 INSERT)
	public boolean paoRequest(Map<String, String> map, ItemDto dto, int cnt);
	
	// 담당자 : 발주 대기 승인 처리
	public boolean approvePao(String pao_seq);
	
	// 업주 : 발주 승인 완료 처리
	public boolean completePao(String pao_seq);
	
	// 업주 : 발주 대기 취소 처리
	public boolean canclePao(String pao_seq);
}
