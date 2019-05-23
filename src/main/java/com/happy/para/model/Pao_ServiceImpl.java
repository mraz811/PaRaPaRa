package com.happy.para.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happy.para.dto.ItemDto;
import com.happy.para.dto.PaoDto;
import com.happy.para.dto.StockDto;

@Service
public class Pao_ServiceImpl implements Pao_IService {

	private Logger logger = LoggerFactory.getLogger(Pao_ServiceImpl.class);
	
	@Autowired
	private Pao_IDao pao_IDao;
	
	// 업주 : 발주 리스트 조회
	@Override
	public List<PaoDto> paoList(String store_code) {
		logger.info("paoList Service : {} ", store_code);
		return pao_IDao.paoList(store_code);
	}

	// 업주 : 발주 상태 선택 조회
	@Override
	public List<PaoDto> paoSelectStatus(Map<String, Object> map) {
		logger.info("paoSelectStatus : {}", map);
		return pao_IDao.paoSelectStatus(map);
	}
	
	// 업주 : 매장 발주 날짜 선택 조회
	@Override
	public List<PaoDto> paoSelectDate(Map<String, String> map) {
		logger.info("paoSelectDate : {}", map);
		return pao_IDao.paoSelectDate(map);
	}

	// 업주 : 발주 신청 시 재고 목록 조회
	@Override
	public List<StockDto> paoStockList(String store_code) {
		logger.info("paoStockList : {}", store_code);
		return pao_IDao.paoStockList(store_code);
	}

	// 업주 : 발주 신청(발주 테이블 INSERT, 발주 품목 테이블 INSERT)
	@Transactional
	@Override
	public boolean paoRequest(Map<String, String> paoMap, ItemDto dto, int cnt) {
		logger.info("paoRequest : {}{}", paoMap, dto);
		
		boolean isc = pao_IDao.paoInsert(paoMap);	// 발주 테이블 INSERT
		
		if(isc) {
			for (int i = 0; i < cnt; i++) {
				Map<String, String> piMap = new HashMap<>();
				
				piMap.put("item_seq", dto.getItem_seqs()[i]);	// 발주 품목에 있는 각각의 재고번호
				piMap.put("pi_qty", dto.getPi_qtys()[i]);	// 발주 품목에 있는 각각의 수량
				
				isc = pao_IDao.piInsert(piMap);	// 발주한 품목의 갯수 만큼 발주 품목 테이블 INSERT
			}
		}
		
		return isc;
	}


}
