package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.MenuDto;
import com.happy.para.dto.RequestDto;

@Repository
public class Request_DaoImpl {
	
	@Autowired 
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "para.request.";
	
	public boolean updateOscode(RequestDto dto) {
		return sqlSession.update(NS+"updateOscode", dto) >0 ? true:false;
	}
	
	public List<RequestDto> requestList(Map<String, String> map){
		return sqlSession.selectList(NS+"requestList", map);
	}
	
	public List<RequestDto> requestListWait(String store_code){
		return sqlSession.selectList(NS+"requestListWait", store_code);
	}
	
	public RequestDto requestDetailWait(RequestDto dto){
		return sqlSession.selectOne(NS+"requestDetailWait", dto);
	} 
	
	public List<RequestDto> requestListMake(String store_code){
		return sqlSession.selectList(NS+"requestListMake", store_code);
	}
	
	public RequestDto requestDetailMake(RequestDto dto){
		return sqlSession.selectOne(NS+"requestDetailMake", dto);
	} 
	
	public boolean customOrder(RequestDto dto) {
		return sqlSession.insert(NS+"customOrder", dto) > 0?true:false;
	}
	
	public String requestMenuName(Map<String, String[]> map) {
		String menuName = "";
		List<MenuDto> lists = sqlSession.selectList(NS+"requestMenuName", map);
		for (int i = 0; i < lists.size(); i++) {
			menuName += lists.get(i).getMenu_name();
		}
		return menuName;
	}
}
