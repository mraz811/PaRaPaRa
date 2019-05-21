package com.happy.para.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.CalDto;

@Repository
public class Calendar_DaoImpl implements Calendar_IDao{
	
	@Autowired
	SqlSessionTemplate sqlSession;

	@Override
	public int calRgister(CalDto dto) {
		return sqlSession.insert("para.calendar.calRgister", dto);
	}

	@Override
	public int calModify(CalDto dto) {
		return sqlSession.update("para.calendar.calModify", dto);
	}

	@Override
	public int calDelete(String cal_seq) {
		return sqlSession.delete("para.calendar.calDelete", cal_seq);
	}

	@Override
	public List<CalDto> calList(String store_code) {
		return sqlSession.selectList("para.calendar.calList", store_code);
	}

	@Override
	public CalDto calDetail(CalDto dto) {
		return sqlSession.selectOne("para.calendar.calDetail", dto);
	}
	
	
	
}
