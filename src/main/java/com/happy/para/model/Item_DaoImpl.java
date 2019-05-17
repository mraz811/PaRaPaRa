package com.happy.para.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.ItemDto;

@Repository
public class Item_DaoImpl implements Item_IDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "para.item.";
	
	// 전체 품목 조회
	@Override
	public List<ItemDto> itemList() {
		return sqlSession.selectList(NS+"itemList");
	}
	
	// 품목 등록
	@Override
	public boolean itemInsert(ItemDto dto) {
		return sqlSession.insert(NS+"itemInsert", dto) > 0 ? true:false;
	}

	// 품목 수정
	@Override
	public boolean itemModify(ItemDto dto) {
		return sqlSession.update(NS+"itemModify", dto) > 0 ? true:false;
	}
	
	// 품목 삭제
	@Override
	public boolean itemDelete(String item_seq) {
		return sqlSession.delete(NS+"itemDelete", item_seq) > 0 ? true:false;
	}

}
