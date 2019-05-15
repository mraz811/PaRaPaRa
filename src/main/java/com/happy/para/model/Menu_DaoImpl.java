package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.FileDto;
import com.happy.para.dto.MenuDto;

@Repository
public class Menu_DaoImpl {

	@Autowired 
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "para.menu.";
	
	public List<MenuDto> ownerMenuList(Map<String, String[]> map){
		return sqlSession.selectList(NS+"ownerMenuList", map);
	}
	
	public boolean ownerMenuChoice(Map<String, String> map) {
		return sqlSession.update(NS+"ownerMenuChoice", map) > 0 ? true:false;
	}
	
	public List<MenuDto> allMenu(){
		return sqlSession.selectList(NS+"allMenu");
	}
	
	public boolean insertMenu(MenuDto dto) {
		return sqlSession.insert(NS+"insertMenu", dto)>0?true:false;
	}
	
	public boolean insertMenuFile(FileDto dto) {
		return sqlSession.insert(NS+"insertMenuFile", dto)>0?true:false;
	}
	
	public boolean modifyMenu(MenuDto dto) {
		return sqlSession.update(NS+"modifyMenu", dto)>0?true:false;
	}
	
	public boolean modifyMenuFile(FileDto dto) {
		return sqlSession.update(NS+"modifyMenuFile", dto)>0?true:false;
	}
	
	public boolean deleteMenu(String menu_seq) {
		return sqlSession.update(NS+"deleteMenu", menu_seq)>0?true:false;
	}
	
	
}
