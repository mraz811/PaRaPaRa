package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happy.para.dto.FileDto;
import com.happy.para.dto.MenuDto;

@Service
public class Menu_ServiceImpl implements Menu_IService{

	private Logger logger = LoggerFactory.getLogger(Menu_ServiceImpl.class);
	
	@Autowired
	private Menu_IDao menu_IDao;
	
	//업주 선택 메뉴 조회
	@Override
	public List<MenuDto> ownerMenuList(Map<String, Object> map){
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
	
	//메뉴 상세조회
	@Override
	public MenuDto detailMenu(String menu_seq) {
		logger.info("allMenu Service : {} ");
		return menu_IDao.detailMenu(menu_seq);
	}
	
	//담당자 메뉴 등록
	//담당자 메뉴 이미지(파일) 등록
	@Transactional
	@Override
	public boolean insertMenu(MenuDto mDto,FileDto fDto) {
		logger.info("insertMenu Service : {}{}", mDto,fDto);
		boolean isc = menu_IDao.insertMenu(mDto);
		fDto.setMenu_seq(mDto.getMenu_seq());
		if(isc) {
			isc = menu_IDao.insertMenuFile(fDto);
		}
		return isc;
	}
	
	//담당자 메뉴 수정
	//담당자 메뉴 이미지(파일) 수정
	@Transactional
	@Override
	public boolean modifyMenu(MenuDto mDto,FileDto fDto) {
		logger.info("modifyMenu Service : {}{} ", mDto,fDto);
		boolean isc = menu_IDao.modifyMenu(mDto);
		fDto.setMenu_seq(mDto.getMenu_seq());
		if(isc) {
			isc = menu_IDao.modifyMenuFile(fDto);
		}
		return isc;
	}
	
	//담당자 메뉴 삭제
	@Override
	public boolean deleteMenu(String menu_seq) {
		logger.info("deleteMenu Service : {} ", menu_seq);
		return menu_IDao.deleteMenu(menu_seq);
	}
	
	//전체 메뉴 조회
	@Override
	public List<Integer> selAllMenu(){
		logger.info("selAllMenu Service");
		return menu_IDao.selAllMenu();
	}
	
	
}
