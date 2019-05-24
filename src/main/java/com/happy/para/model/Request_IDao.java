package com.happy.para.model;

import java.util.List;
import java.util.Map;

import com.happy.para.dto.RequestDto;


public interface Request_IDao {

	// 주문 상태 업데이트
	public boolean updateOscode(RequestDto dto);

	// 주문 완료,환불 내역 전체 조회
	public List<RequestDto> requestList(Map<String, String> map);

	// 주문상태가 대기 중인 주문 조회
	public List<RequestDto> requestListWait(Map<String, String> map);

	// 주문상태가 대기 중인 주문 상세 조회
	public RequestDto requestDetailWait(RequestDto dto);

	// 주문상태가 제조 중인 주문 조회
	public List<RequestDto> requestListMake(Map<String, String> map);

	// 주문상태가 제조 중인 주문 상세 조회
	public RequestDto requestDetailMake(RequestDto dto);

	// 고객 주문 시 주문 등록
	public boolean customOrder(RequestDto dto);

	// 메뉴 번호에 따른 메뉴이름 찾기
	public String[] requestMenuName(Map<String, Object> map);
	
	//페이징
	public List<RequestDto> requestListPaging(Map<String, String> map);
	
	//전체 주문 갯수
	public int selectTotalRequest(String store_code);
	
}
