package com.happy.para.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.happy.para.dto.FileDto;
import com.happy.para.dto.MenuDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.model.Menu_DaoImpl;

@Controller
public class MansolTest_Ctrl {
	
	@Autowired
	private Menu_DaoImpl mDao;
	
	@RequestMapping(value="/choiceMenuList.do",method=RequestMethod.GET)
	public void ownerMenuList(String[] menu_seq) {
		Map<String, String[]> map = new HashMap<String,String[]>();
		map.put("menu_seq_", menu_seq);
		List<MenuDto> lists = mDao.ownerMenuList(map);
		System.out.println("업주가 선택한 메뉴 : "+lists.toString());
	}
	
	@RequestMapping(value="/menuChoice.do",method=RequestMethod.GET)
	public void ownerMenuChoice(String owner_menu,String owner_seq) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("owner_menu",owner_menu);
		map.put("owner_seq",owner_seq);
		boolean isc = mDao.ownerMenuChoice(map);
		System.out.println("업주의 메뉴 선택 성공? : "+isc);
	}
	
	@RequestMapping(value="/selAllMenuList.do",method=RequestMethod.GET)
	public void allMenu() {
		List<MenuDto> lists = mDao.allMenu();
		System.out.println("전체 메뉴 조회 : "+lists);
	}
	
	@RequestMapping(value="/regiNewMenu.do",method=RequestMethod.GET)
	public void insertMenu(MenuDto dto) {
		boolean isc = mDao.insertMenu(dto);
		System.out.println("담당자 메뉴 등록 성공? : "+isc);
	}
	
	@RequestMapping(value="/regiNewMenu2.do",method=RequestMethod.GET)
	public void insertMenuFile(FileDto dto) {
		boolean isc = mDao.insertMenuFile(dto);
		System.out.println("담당자 메뉴 이미지 등록 성공? : "+isc);
	}
	
	@RequestMapping(value="/menuModi.do",method=RequestMethod.GET)
	public void modifyMenu(MenuDto dto) {
		boolean isc = mDao.modifyMenu(dto);
		System.out.println("담당자 메뉴 수정 성공? : "+isc);
	}
	
	@RequestMapping(value="/menuModi2.do",method=RequestMethod.GET)
	public void modifyMenuFile(FileDto dto) {
		boolean isc = mDao.modifyMenuFile(dto);
		System.out.println("담당자 메뉴 이미지 수정 성공? : "+isc);
	}
	
	@RequestMapping(value="/delMenu.do",method=RequestMethod.GET)
	public void deleteMenu(String menu_seq) {
		boolean isc = mDao.deleteMenu(menu_seq);
		System.out.println("담당자 메뉴 삭제 성공? : "+isc);
	}
	
}
