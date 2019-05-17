package com.happy.para.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.para.dto.PaoDto;

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

}
