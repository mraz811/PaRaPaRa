package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.para.dto.AlbaDto;
import com.happy.para.dto.TimeDto;

@Service
public class Timesheet_ServiceImpl implements Timesheet_IService {

	@Autowired
	private Timesheet_IDao timeDao;

	@Override
	public List<TimeDto> tsListAll(Map<String, String> map) {
		return timeDao.tsListAll(map);
	}

	@Override
	public List<TimeDto> tsList(TimeDto dto) {
		return timeDao.tsList(dto);
	}

	@Override
	public List<AlbaDto> tsDatetimeList(Map<String, Object> map) {
		return timeDao.tsDatetimeList(map);
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

	@Override
	public List<AlbaDto> tsAlba(String store_code) {
		return timeDao.tsAlba(store_code);
	}

	@Override
	public TimeDto salaryView(Map<String, String> map) {
		return timeDao.salaryView(map);
	}

}
