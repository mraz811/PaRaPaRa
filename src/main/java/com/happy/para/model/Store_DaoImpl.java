package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.StoreDto;

@Repository
public class Store_DaoImpl implements Store_IDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "para.store.";
	
	@Override
	public List<StoreDto> storeList(StoreDto dto) {
		return sqlSession.selectList(NS+"storeList", dto);
	}

	@Override
	public boolean storeInsert(StoreDto dto) {
		return sqlSession.insert(NS+"storeInsert", dto)>0 ? true:false;
	}

	@Override
	public boolean storeModify(StoreDto dto) {
		return sqlSession.update(NS+"storeModify", dto)>0 ? true:false;
	}

	@Override
	public boolean storeDelete(String store_code) {
		return sqlSession.update(NS+"storeDelete", store_code)>0 ? true:false;
	}

	@Override
	public List<StoreDto> storeListPaging(Map<String, Integer> map) {
		return sqlSession.selectList(NS+"storeListPaging", map);
	}

	@Override
	public int storeListRow(String admin_id) {
		return sqlSession.selectOne(NS+"storeListRow", admin_id);
	}

	@Override
	public int storeRow(String admin_id) {
		return sqlSession.selectOne(NS+"storeRow", admin_id);
	}

}
