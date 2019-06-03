package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.MenuDto;
import com.happy.para.dto.RequestDto;


@Repository
public class Request_DaoImpl implements Request_IDao{
	
	@Autowired 
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "para.request.";
	
	//주문 상태 업데이트
	@Override
	public boolean updateOscode(RequestDto dto) {
		return sqlSession.update(NS+"updateOscode", dto) >0 ? true:false;
	}
	
	//주문 완료,환불 내역 전체 조회
	@Override
	public List<RequestDto> requestList(Map<String, String> map){
		return sqlSession.selectList(NS+"requestList", map);
	}
	
	//주문 완료,환불 내역 상세 조회
	@Override
	public RequestDto requestDetail(RequestDto dto) {
		return sqlSession.selectOne(NS+"requestDetail", dto);
	}
	
	//주문상태가 대기 중인 주문 조회
	@Override
	public List<RequestDto> requestListWait(Map<String, String> map){
		return sqlSession.selectList(NS+"requestListWait", map);
	}
	
	//주문상태가 대기 중인 주문 상세 조회
	@Override
	public RequestDto requestDetailWait(RequestDto dto){
		return sqlSession.selectOne(NS+"requestDetailWait", dto);
	} 
	
	//주문상태가 제조 중인 주문 조회
	@Override
	public List<RequestDto> requestListMake(Map<String, String> map){
		return sqlSession.selectList(NS+"requestListMake", map);
	}
	
	//주문상태가 제조 중인 주문 상세 조회
	@Override
	public RequestDto requestDetailMake(RequestDto dto){
		return sqlSession.selectOne(NS+"requestDetailMake", dto);
	} 
	
	//고객 주문 시 주문 등록
	@Override
	public boolean customOrder(RequestDto dto) {
		return sqlSession.insert(NS+"customOrder", dto) > 0?true:false;
	}
	
	//고객 주문 시 대기중 조회
	@Override
	public RequestDto requestCustomWait(Map<String, String> map) {
		return sqlSession.selectOne(NS+"requestCustomWait", map);
	}
	
	//메뉴 번호에 따른 메뉴이름 찾기
	@Override
	public String[] requestMenuName(Map<String, Object> map) {
		List<MenuDto> lists = sqlSession.selectList(NS+"requestMenuName", map);
		String[] menuName = new String[lists.size()];
		for (int i = 0; i < lists.size(); i++) {
			menuName[i] = lists.get(i).getMenu_name()+" ";
		}
		return menuName;
	}
	//페이징
	@Override
	public List<RequestDto> requestListPaging(Map<String, String> map) {
		return sqlSession.selectList(NS+"requestListPaging", map);
	}
	
	//전체 주문 갯수
	@Override
	public int selectTotalRequest(Map<String, String> map) {
		return sqlSession.selectOne(NS+"selectTotalRequest",map);
	}
}
