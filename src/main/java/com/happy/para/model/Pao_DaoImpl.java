package com.happy.para.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.PaoDto;
import com.happy.para.dto.StockDto;

@Repository
public class Pao_DaoImpl implements Pao_IDao {

	@Autowired 
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "para.pao.";
	
	// 업주 : 발주 리스트 조회
	@Override
	public List<PaoDto> paoList(String store_code) {
		return sqlSession.selectList(NS+"paoList", store_code);
	}

	// 업주 : 발주 상태 선택 조회
	@Override
	public List<PaoDto> paoSelectStatus(Map<String, Object> map) {
		return sqlSession.selectList(NS+"paoSelectStatus", map);
	}
	
	// 업주 : 매장 발주 날짜 선택 조회
	@Override
	public List<PaoDto> paoSelectDate(Map<String, String> map) {
		return sqlSession.selectList(NS+"paoSelectDate", map);
	}

	// 업주 : 발주 신청 시 재고 목록 조회
	@Override
	public List<StockDto> paoStockList(String store_code) {
		return sqlSession.selectList(NS+"paoStockList", store_code);
	}

	

}
