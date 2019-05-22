package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.para.dto.TimeDto;

@Service
public class Timesheet_ServiceImpl implements Timesheet_IService {

	@Autowired
	private Timesheet_IDao timeDao;

	@Override
	public List<TimeDto> tsListAll() {
		return timeDao.tsListAll();
	}

	@Override
	public List<TimeDto> tsList(TimeDto dto) {
		return timeDao.tsList(dto);
	}
	
	@Override
	public List<String> tsDatetimeList(TimeDto dto) {
		return timeDao.tsDatetimeList(dto);
	}

	@Override
	public boolean tsRegister(TimeDto dto) {
		return timeDao.tsRegister(dto);
	}

	@Override
	public boolean tsModify(TimeDto dto) {
		return timeDao.tsModify(dto);
	}

	@Override
	public boolean tsDelete(TimeDto dto) {
		return timeDao.tsDelete(dto);
	}


	
	
	
}
