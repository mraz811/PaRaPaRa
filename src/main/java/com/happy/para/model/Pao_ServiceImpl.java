package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
