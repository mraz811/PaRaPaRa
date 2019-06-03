package com.happy.para.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.ItemDto;
import com.happy.para.dto.PagingDto;
import com.happy.para.dto.PaoDto;
import com.happy.para.dto.StockDto;

@Repository
public class Pao_DaoImpl implements Pao_IDao {

	@Autowired 
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "para.pao.";
	
	// 업주 : 발주 리스트 조회(페이징)
	@Override
	public List<PaoDto> paoList(Map<String, Object> map) {
		return sqlSession.selectList(NS+"paoList", map);
	}
	
	// 업주 : 발주 리스트 갯수(페이징)
	@Override
	public int paoListRow(String store_code) {
		return sqlSession.selectOne(NS+"paoListRow", store_code);
	}
	
	// 담당주 : 담당지역의 매장 발주 리스트 조회(페이징)
	@Override
	public List<PaoDto> adminPaoList(Map<String, Object> map) {
		return sqlSession.selectList(NS+"adminPaoList", map);
	}
	
	// 업주 : 담당 지역의 매장 발주 리스트 갯수(페이징)
	@Override
	public int adminPaoListRow(String store_code) {
		return sqlSession.selectOne(NS+"adminPaoListRow", store_code);
	}

	// 업주 : 발주 상태 선택 조회 및 매장 발주 날짜 선택 조회(페이징)
	@Override
	public List<PaoDto> paoSelectStatusDate(Map<String, Object> map) {
		return sqlSession.selectList(NS+"paoSelectStatusDate", map);
	}
	
	// 담당자 : 발주 상태 선택 조회 및  매장 발주 날짜 선택 조회(페이징)
	@Override
	public List<PaoDto> adimPaoSelectStatusDate(Map<String, Object> map) {
		return sqlSession.selectList(NS+"adimPaoSelectStatusDate", map);
	}
	
	// 업주 : 매장 발주 상태 선택 조회 및 매장 발주 날짜 선택 조회한 발주 내역 갯수(페이징)
	@Override
	public int paoStatusListRow(Map<String, Object> map) {
		return sqlSession.selectOne(NS+"paoStatusListRow", map);
	}
	
	// 담당자 : 매장 발주 상태 선택 조회 및 매장 발주 날짜 선택 조회한 발주 내역 갯수(페이징)
	@Override
	public int adminPaoStatusListRow(Map<String, Object> map) {
		return sqlSession.selectOne(NS+"adminPaoStatusListRow", map);
	}
	
	// 업주 : 발주 상세보기(발주)
	@Override
	public PaoDto paoDetail(Map<String, String> map) {
		return sqlSession.selectOne(NS+"paoDetail", map);
	}
	
	// 업주 : 발주 상세보기(발주품목)
	@Override
	public List<ItemDto> paoPiDetail(String pao_seq) {
		return sqlSession.selectList(NS+"paoPiDetail", pao_seq);
	}
	
	// 업주 : 발주 신청 시 재고 목록 조회
	@Override
	public List<StockDto> paoStockList(String store_code) {
		return sqlSession.selectList(NS+"paoStockList", store_code);
	}

	// 업주 : 발주 신청(발주 테이블 INSERT)
	@Override
	public boolean paoInsert(Map<String, String> map) {
		return (sqlSession.insert(NS+"paoInsert", map) == 1) ? true : false;
	}

	// 업주 : 발주 신청(발주 품목 테이블 INSERT)
	@Override
	public boolean piInsert(Map<String, String> map) {
		// TODO Auto-generated method stub
		return (sqlSession.insert(NS+"piInsert", map) == 1) ? true : false;
	}

	// 담당자 : 발주 대기 승인 처리
	@Override
	public boolean approvePao(String pao_seq) {
		return (sqlSession.update(NS+"approvePao", pao_seq) == 1) ? true : false;
	}

	// 업주 : 발주 승인 완료 처리
	@Override
	public boolean completePao(String pao_seq) {
		return (sqlSession.update(NS+"completePao", pao_seq) == 1) ? true : false;
	}

	// 업주 : 발주 대기 취소 처리
	@Override
	public boolean canclePao(String pao_seq) {
		return (sqlSession.update(NS+"canclePao", pao_seq) == 1) ? true : false;
	}



}
