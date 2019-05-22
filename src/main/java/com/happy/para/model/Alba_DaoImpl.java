package com.happy.para.model;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Alba_DaoImpl implements Alba_IDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String ALBANS = "para.alba.";
	
}
