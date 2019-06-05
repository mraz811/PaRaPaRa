package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.para.dto.StockDto;

@Service
public class Stock_ServiceImpl implements Stock_IService {

	@Autowired
	Stock_IDao stockDao;

	@Override
	public List<StockDto> stockList() {
		return stockDao.stockList();
	}
	
	@Override
	public List<String> selStore() {
		return stockDao.selStore();
	}

	@Override
	public List<StockDto> stockOne(String store_code) {
		return stockDao.stockOne(store_code);
	}
	
	@Override
	public boolean stockModify(StockDto dto) {
		return stockDao.stockModify(dto);
	}

	@Override
	public boolean stockAdd(StockDto dto) {
		return stockDao.stockAdd(dto);
	}
	
	@Override
	public boolean stockDelete(String store_code) {
		return stockDao.stockDelete(store_code);
	}
	
	@Override
	public boolean stockDeleteStore(String store_code) {
		return stockDao.stockDeleteStore(store_code);
	}

	@Override
	public List<StockDto> stockSearchList(Map<String, String> map) {
		return stockDao.stockSearchList(map);
	}
	
}
