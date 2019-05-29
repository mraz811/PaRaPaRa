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
	
	// 업주 : 발주 리스트 조회(페이징)
	@Override
	public List<PaoDto> paoList(Map<String, Object> map) {
		logger.info("paoList Service : {} ", map);
		return pao_IDao.paoList(map);
	}
	
	// 업주 : 발주 리스트 갯수(페이징)
	@Override
	public int paoListRow(String store_code) {
		logger.info("paoListRow Service : {} ", store_code);
		return pao_IDao.paoListRow(store_code);
	}
	
	// 담당주 : 담당 지역의 매장 발주 리스트 조회(페이징)
	@Override
	public List<PaoDto> adminPaoList(Map<String, Object> map) {
		logger.info("adminPaoList Service : {} ", map);
		return pao_IDao.adminPaoList(map);
	}
	
	// 업주 : 담당 지역의 매장 발주 리스트 갯수(페이징)
	@Override
	public int adminPaoListRow(String store_code) {
		logger.info("adminPaoListRow Service : {} ", store_code);
		return pao_IDao.adminPaoListRow(store_code);
	}

	// 업주 : 발주 상태 선택 조회 및 매장 발주 날짜 선택 조회(페이징)
	@Override
	public List<PaoDto> paoSelectStatusDate(Map<String, Object> map) {
		logger.info("paoSelectStatusDate : {}", map);
		return pao_IDao.paoSelectStatusDate(map);
	}
	
	// 업주 : 매장 발주 상태 선택 조회 및 매장 발주 날짜 선택 조회한 발주 내역 갯수(페이징)
	@Override
	public int paoStatusListRow(Map<String, Object> map) {
		logger.info("paoStatusListRow : {}", map);
		return pao_IDao.paoStatusListRow(map);
	}
	
	// 담당자 : 매장 발주 상태 선택 조회 및 매장 발주 날짜 선택 조회한 발주 내역 갯수(페이징)
	@Override
	public int adminPaoStatusListRow(Map<String, Object> map) {
		logger.info("adminPaoStatusListRow : {}", map);
		return pao_IDao.adminPaoStatusListRow(map);
	}
	
	// 업주 : 발주 상세보기(발주)
	@Override
	public PaoDto paoDetail(Map<String, String> map) {
		logger.info("paoDetail : {}", map);
		return pao_IDao.paoDetail(map);
	}


	// 업주 : 발주 상세보기(발주품목)
	@Override
	public List<ItemDto> paoPiDetail(String pao_seq) {
		logger.info("paoPiDetail : {}", pao_seq);
		return pao_IDao.paoPiDetail(pao_seq);
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

	// 담당자 : 발주 대기 승인 처리
	@Override
	public boolean approvePao(String pao_seq) {
		logger.info("approvePao : {}", pao_seq);
		boolean isc = pao_IDao.approvePao(pao_seq);	// 발주 테이블의 발주상태코드 UPDATE
		
		return isc;
	}

	// 업주 : 발주 승인 완료 처리
	@Override
	public boolean completePao(String pao_seq) {
		logger.info("completePao : {}", pao_seq);
		boolean isc = pao_IDao.completePao(pao_seq);	// 발주 테이블의 발주상태코드 UPDATE
		
		return isc;
	}

	// 업주 : 발주 대기 취소 처리
	@Override
	public boolean canclePao(String pao_seq) {
		logger.info("canclePao : {}", pao_seq);
		boolean isc = pao_IDao.canclePao(pao_seq);	// 발주 테이블의 발주상태코드 UPDATE
		
		return isc;
	}


}
