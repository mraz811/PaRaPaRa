package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.AlbaDto;

@Repository
public class Alba_DaoImpl implements Alba_IDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String ALNS = "para.alba.";

	@Override
	public int albaRegister(AlbaDto albaDto) {
		return sqlSession.insert(ALNS+"albaRegister", albaDto);
	}

	@Override
	public int albaModify(AlbaDto albaDto) {
		return sqlSession.update(ALNS+"albaModify", albaDto);
	}

	@Override
	public List<AlbaDto> albaList(Map<String, String> map) {
		return sqlSession.selectList(ALNS+"albaList", map);
	}

	@Override
	public int albaListRow(String store_code) {
		return sqlSession.selectOne(ALNS+"albaListRow", store_code);
	}

	@Override
	public int albaDelete(String alba_seq) {
		return sqlSession.update(ALNS+"albaDelete", alba_seq);
	}
	
}
