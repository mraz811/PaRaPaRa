package com.happy.para.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.para.dto.RequestDto;


@Service
public class Request_ServiceImpl implements Request_IService{

	private Logger logger = LoggerFactory.getLogger(Request_ServiceImpl.class);
	
	@Autowired
	private Request_IDao request_IDao;

	// 주문 상태 업데이트
	@Override
	public boolean updateOscode(RequestDto dto) {
		logger.info("updateOscode Service : {} ", dto);
		return request_IDao.updateOscode(dto);
	}

	// 주문 완료,환불 내역 전체 조회
	@Override
	public List<RequestDto> requestList(Map<String, String> map) {
		logger.info("requestList Service : {} ", map);
		return request_IDao.requestList(map);
	}
	
	//주문 완료,환불 내역 상세 조회
	@Override
	public RequestDto requestDetail(RequestDto dto) {
		return request_IDao.requestDetail(dto);
	}

	// 주문상태가 대기 중인 주문 조회
	@Override
	public List<RequestDto> requestListWait(Map<String, String> map) {
		logger.info("requestListWait Service : {} ", map);
		return request_IDao.requestListWait(map);
	}

	// 주문상태가 대기 중인 주문 상세 조회
	@Override
	public RequestDto requestDetailWait(RequestDto dto) {
		logger.info("requestDetailWait Service : {} ", dto);
		return request_IDao.requestDetailWait(dto);
	}

	// 주문상태가 제조 중인 주문 조회
	@Override
	public List<RequestDto> requestListMake(Map<String, String> map) {
		logger.info("requestListMake Service : {} ", map);
		return request_IDao.requestListMake(map);
	}

	// 주문상태가 제조 중인 주문 상세 조회
	@Override
	public RequestDto requestDetailMake(RequestDto dto) {
		logger.info("requestDetailMake Service : {} ", dto);
		return request_IDao.requestDetailMake(dto);
	}

	// 고객 주문 시 주문 등록
	@Override
	public RequestDto customOrder(RequestDto dto) {
		logger.info("customOrder Service : {} ", dto);
		boolean isc = request_IDao.customOrder(dto);
		RequestDto result = new RequestDto();
		Map<String, String> map = new HashMap<String,String>();
		map.put("request_seq", Integer.toString(dto.getRequest_seq()));
		map.put("store_code", dto.getStore_code());
		
		Date date = new Date();
		SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");
		String start = day.format(date);
		System.out.println(day.format(date));
		map.put("start", start);
		
		if(isc) {
			result = request_IDao.requestCustomWait(map);
		}
		return result;
	}

	// 메뉴 번호에 따른 메뉴이름 찾기
	@Override
	public String[] requestMenuName(Map<String, Object> map) {
		logger.info("requestMenuName Service : {} ", map);
		return request_IDao.requestMenuName(map);
	}
	
	//페이징
	@Override
	public List<RequestDto> requestListPaging(Map<String, String> map) {
		return request_IDao.requestListPaging(map);
	}
	
	//전체 주문 갯수
	@Override
	public int selectTotalRequest(Map<String, String> map) {
		return request_IDao.selectTotalRequest(map);
	}
}
