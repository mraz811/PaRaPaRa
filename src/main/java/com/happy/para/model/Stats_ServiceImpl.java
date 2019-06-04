package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Stats_ServiceImpl implements Stats_IService {

	private Logger logger = LoggerFactory.getLogger(Stats_ServiceImpl.class);
	
	@Autowired
	private Stats_IDao stats_IDao;
	
	// 업주 : 수익 통계
	@Override
	public int ownerStatsIncome(Map<String, String> map) {
		logger.info("ownerStatsIncome Service : {} ", map);
		return stats_IDao.ownerStatsIncome(map);
	}

	// 업주 : 지출 통계
	@Override
	public int ownerStatsOutcome(Map<String, String> map) {
		logger.info("ownerStatsOutcome Service : {} ", map);
		return stats_IDao.ownerStatsOutcome(map);
	}

	// 업주 : 상위판매메뉴 통계
	@Override
	public Map<String, List<String>> ownerStatsMenu(Map<String, String> map) {
		logger.info("ownerStatsMenu Service : {} ", map);
		return stats_IDao.ownerStatsMenu(map);
	}

	// 관리자,담당자 : 수익통계
	@Override
	public int adminStatsIncome(Map<String, Object> map) {
		logger.info("adminStatsIncome Service : {} ", map);
		return stats_IDao.adminStatsIncome(map);
	}

	// 관리자,담당자 : 상위판매메뉴 통계
	@Override
	public Map<String, List<String>> adminStatsMenu(Map<String, Object> map) {
		logger.info("adminStatsMenu Service : {} ", map);
		return stats_IDao.adminStatsMenu(map);
	}

}
