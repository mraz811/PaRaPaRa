package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.FileDto;
import com.happy.para.dto.MenuDto;

@Repository
public class Menu_DaoImpl implements Menu_IDao{

	@Autowired 
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "para.menu.";
	
	//업주 선택 메뉴 조회
	@Override
	public List<MenuDto> ownerMenuList(Map<String, Object> map){
		return sqlSession.selectList(NS+"ownerMenuList", map);
	}
	
	//업주 메뉴 선택
	@Override
	public boolean ownerMenuChoice(Map<String, String> map) {
		return sqlSession.update(NS+"ownerMenuChoice", map) > 0 ? true:false;
	}
	
	//전체 메뉴 조회
	@Override
	public List<MenuDto> allMenu(MenuDto dto){
		return sqlSession.selectList(NS+"allMenu",dto);
	}
	
	//메뉴 상세 조회
	@Override
	public MenuDto detailMenu(String menu_seq) {
		return sqlSession.selectOne(NS+"detailMenu", menu_seq);
	}
	
	//담당자 메뉴 등록
	@Override
	public boolean insertMenu(MenuDto dto) {
		return sqlSession.insert(NS+"insertMenu", dto)>0?true:false;
	}
	
	//담당자 메뉴 이미지(파일) 등록
	@Override
	public boolean insertMenuFile(FileDto dto) {
		return sqlSession.insert(NS+"insertMenuFile", dto)>0?true:false;
	}
	
	// 담당자 메뉴 이미지(파일) 임시 등록
	@Override
	public boolean insertMenuTempFile(FileDto fDto) {
		return sqlSession.insert(NS+"insertMenuTempFile", fDto)>0?true:false;
	};
			
	// 담당자 메뉴 이미지(파일) 임시 등록된거 상세조회
	@Override
	public FileDto selTempFile(int file_seq) {
		return sqlSession.selectOne(NS+"selTempFile", file_seq);
	};
	
	// 담당자 메뉴 이미지(파일) 임시 등록된거 삭제, 스케줄러로 하루에 한번 실행 시킬꺼
	@Override
	public boolean deleteMenuTempFile() {
		return sqlSession.delete(NS+"deleteMenuTempFile")>0?true:false;
	};
	
	//담당자 메뉴 수정
	@Override
	public boolean modifyMenu(MenuDto dto) {
		return sqlSession.update(NS+"modifyMenu", dto)>0?true:false;
	}
	
	//담당자 메뉴 이미지(파일) 수정
	@Override
	public boolean modifyMenuFile(FileDto dto) {
		return sqlSession.update(NS+"modifyMenuFile", dto)>0?true:false;
	}
	
	//담당자 메뉴 삭제
	@Override
	public boolean deleteMenu(String menu_seq) {
		return sqlSession.update(NS+"deleteMenu", menu_seq)>0?true:false;
	}
	
	//담당자 메뉴 재판매
	@Override
	public boolean reSellMenu(String menu_seq) {
		return sqlSession.update(NS+"reSellMenu",menu_seq)>0?true:false;
	}
	
	//전체 메뉴 조회
	@Override
	public List<Integer> selAllMenu() {
		return sqlSession.selectList(NS+"selAllMenu");
	}
	
}
