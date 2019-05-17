package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.para.dto.FileDto;
import com.happy.para.dto.MenuDto;

@Service
public class Menu_ServiceImpl implements Menu_IService{

	private Logger logger = LoggerFactory.getLogger(Menu_ServiceImpl.class);
	
	@Autowired
	private Menu_IDao menu_IDao;
	
	//업주 선택 메뉴 조회
	@Override
	public List<MenuDto> ownerMenuList(Map<String, String[]> map){
		logger.info("ownerMenuList Service : {} ", map);
		return menu_IDao.ownerMenuList(map);
	}
	
	//업주 메뉴 선택
	@Override
	public boolean ownerMenuChoice(Map<String, String> map) {
		logger.info("ownerMenuChoice Service : {} ", map);
		return menu_IDao.ownerMenuChoice(map);
	}
	
	//전체 메뉴 조회
	@Override
	public List<MenuDto> allMenu(MenuDto dto){
		logger.info("allMenu Service : {} ");
		return menu_IDao.allMenu(dto);
	}
	
	//담당자 메뉴 등록
	@Override
	public boolean insertMenu(MenuDto dto) {
		logger.info("insertMenu Service : {} ", dto);
		return menu_IDao.insertMenu(dto);
	}
	
	//담당자 메뉴 이미지(파일) 등록
	@Override
	public boolean insertMenuFile(FileDto dto) {
		logger.info("insertMenuFile Service : {} ", dto);
		return menu_IDao.insertMenuFile(dto);
	}
	
	//담당자 메뉴 수정
	@Override
	public boolean modifyMenu(MenuDto dto) {
		logger.info("modifyMenu Service : {} ", dto);
		return menu_IDao.modifyMenu(dto);
	}
	
	//담당자 메뉴 이미지(파일) 수정
	@Override
	public boolean modifyMenuFile(FileDto dto) {
		logger.info("modifyMenuFile Service : {} ", dto);
		return menu_IDao.modifyMenuFile(dto);
	}
	
	//담당자 메뉴 삭제
	@Override
	public boolean deleteMenu(String menu_seq) {
		logger.info("deleteMenu Service : {} ", menu_seq);
		return menu_IDao.deleteMenu(menu_seq);
	}
	
	
}
