package com.happy.para.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.PaoDto;

@Repository
public class Pao_DaoImpl implements Pao_IDao {

	@Autowired 
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "para.pao.";
	
	// 업주 : 발주 리스트 조회
	@Override
	public List<PaoDto> paoList(String store_code) {
		return sqlSession.selectList(NS+"paoList", store_code);
	}

}
