package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.para.dto.StoreDto;

@Service
public class Store_ServiceImpl implements Store_IService {

	private Logger logger = LoggerFactory.getLogger(Store_ServiceImpl.class);
	
	@Autowired
	private Store_IDao storeDao;
	
	@Override
	public List<StoreDto> storeList(StoreDto dto) {
		logger.info("storeList Service : {}", dto);
		return storeDao.storeList(dto);
	}

	@Override
	public boolean storeInsert(StoreDto dto) {
		logger.info("storeInsert Service : {}", dto);
		return storeDao.storeInsert(dto);
	}

	@Override
	public boolean storeModify(StoreDto dto) {
		logger.info("storeModify Service : {}", dto);
		return storeDao.storeModify(dto);
	}

	@Override
	public boolean storeDelete(String store_code) {
		logger.info("storeDelete Service : {}", store_code);
		return storeDao.storeDelete(store_code);
	}

	@Override
	public List<StoreDto> storeListPaging(Map<String, Integer> map) {
		logger.info("storeListPaging Service : {}", map);
		return storeDao.storeListPaging(map);
	}

	@Override
	public int storeListRow(String admin_id) {
		logger.info("storeListRow Service : {}", admin_id);
		return storeDao.storeListRow(admin_id);
	}

	@Override
	public int storeRow(String admin_id) {
		logger.info("storeRow Service : {}", admin_id);
		return storeDao.storeRow(admin_id);
	}

}
