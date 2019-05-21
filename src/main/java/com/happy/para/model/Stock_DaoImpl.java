package com.happy.para.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.StockDto;

@Repository
public class Stock_DaoImpl implements Stock_IDao {

	@Autowired
	SqlSessionTemplate sqlSession;
	
	@Override
	public List<StockDto> stockList() {
		return sqlSession.selectList("para.stock.stockList");
	}
	
	@Override
	public List<String> selStore() {
		return sqlSession.selectList("para.stock.selStore");
	}
	
	@Override
	public List<StockDto> stockOne(String store_code) {
		return sqlSession.selectList("para.stock.stockOne", store_code);
	}

	@Override
	public boolean stockModify(StockDto dto) {
		int n = sqlSession.update("para.stock.stockModify", dto);
		return n>0?true:false;
	}
	
	@Override
	public boolean stockAdd(StockDto dto) {
		int n = sqlSession.insert("para.stock.stockAdd", dto);
		return n>0?true:false;
	}

	@Override
	public boolean stockDelete(String stock_seq) {
		int n = sqlSession.delete("para.stock.stockDelete", stock_seq);
		return n>0?true:false;
	}
	
	@Override
	public boolean stockDeleteStore(String store_code) {
		int n = sqlSession.delete("para.stock.stockDeleteStore", store_code);
		return n>0?true:false;
	}
	
	
}
