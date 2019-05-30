package com.happy.para.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.AlbaDto;
import com.happy.para.dto.TimeDto;

@Repository
public class Timesheet_DaoImpl implements Timesheet_IDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<TimeDto> tsListAll(Map<String, String> map) {
		return sqlSession.selectList("para.timesheet.tsListAll", map);
	}

	@Override
	public List<TimeDto> tsList(TimeDto dto) {
		return sqlSession.selectList("para.timesheet.tsList", dto);
	}

	@Override
	public List<TimeDto> tsDatetimeList(TimeDto dto) {
		return sqlSession.selectList("para.timesheet.tsDatetimeList", dto);
	}

	@Override
	public boolean tsRegister(TimeDto dto) {
		int n = sqlSession.insert("para.timesheet.tsRegister", dto);
		return n>0?true:false;
	}

	@Override
	public boolean tsModify(TimeDto dto) {
		int n = sqlSession.update("para.timesheet.tsModify", dto);
		return n>0?true:false;
	}

	@Override
	public boolean tsDelete(TimeDto dto) {
		int n = sqlSession.delete("para.timesheet.tsDelete", dto);
		return n>0?true:false;
	}

	@Override
	public List<AlbaDto> tsAlba(String store_code) {
		return sqlSession.selectList("para.alba.albaTimeSheet", store_code);
	}

	@Override
	public TimeDto salaryView(Map<String, String> map) {
		return sqlSession.selectOne("para.timesheet.salaryView", map);
	}
	
	
}
