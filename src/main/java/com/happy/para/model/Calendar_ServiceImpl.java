package com.happy.para.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.para.dto.CalDto;

@Service
public class Calendar_ServiceImpl implements Calendar_IService {

	@Autowired
	Calendar_IDao calDao;
	
	@Override
	public int calRgister(CalDto dto) {
		return calDao.calRgister(dto);
	}

	@Override
	public int calModify(CalDto dto) {
		return calDao.calModify(dto);
	}

	@Override
	public int calDelete(String cal_seq) {
		return calDao.calDelete(cal_seq);
	}

	@Override
	public List<CalDto> calList(String store_code) {
		return calDao.calList(store_code);
	}

	@Override
	public CalDto calDetail(CalDto dto) {
		return calDao.calDetail(dto);
	}
	
	
}
