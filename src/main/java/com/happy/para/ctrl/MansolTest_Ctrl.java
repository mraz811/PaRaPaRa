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
import com.happy.para.dto.RequestDto;
import com.happy.para.model.Menu_DaoImpl;
import com.happy.para.model.Request_DaoImpl;

@Controller
public class MansolTest_Ctrl {
	
	//메뉴
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
	
	//주문
	@Autowired
	private Request_DaoImpl rDao;
	
	@RequestMapping(value="/updateOrderState.do",method=RequestMethod.GET)
	public void updateOscode(RequestDto dto) {
		boolean isc = rDao.updateOscode(dto);
		System.out.println("주문 상태 코드 업데이트 성공 ? : "+isc);
	}
	
	@RequestMapping(value="/selRequestList.do",method=RequestMethod.GET)
	public void requestList(String store_code,String start,String end) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("store_code", store_code);
		map.put("start", start);
		map.put("end", end);
		List<RequestDto> lists = rDao.requestList(map);
		System.out.println("주문 완료,환불 : "+lists);
	}
	
	@RequestMapping(value="/selWaitRequest.do",method=RequestMethod.GET)
	public void requestListWait(String store_code) {
		List<RequestDto> lists = rDao.requestListWait(store_code);
		Map<String, String[]> map = new HashMap<String,String[]>();
		for (int i = 0; i < lists.size(); i++) {
			String[] menu_seq = lists.get(i).getRequest_menu().split(",");
			map.put("menu_seq_", menu_seq);
			String menuName = rDao.requestMenuName(map);
			System.out.println("주문 대기중 : "+lists.get(i));
			System.out.println("주문 대기중인 메뉴명 : "+menuName);
		}
	}
	
	@RequestMapping(value="/selWaitReqDetail.do",method=RequestMethod.GET)
	public void requestDetailWait(RequestDto dto) {
		RequestDto rDto = rDao.requestDetailWait(dto);
		System.out.println("주문 대기중 상세 : "+rDto);
	}
	
	@RequestMapping(value="/selMakeRequest.do",method=RequestMethod.GET)
	public void requestListMake(String store_code) {
		List<RequestDto> lists = rDao.requestListMake(store_code);
		Map<String, String[]> map = new HashMap<String,String[]>();
		for (int i = 0; i < lists.size(); i++) {
			String[] menu_seq = lists.get(i).getRequest_menu().split(",");
			map.put("menu_seq_", menu_seq);
			String menuName = rDao.requestMenuName(map);
			System.out.println("주문 제조중 : "+lists.get(i));
			System.out.println("주문 제조중인 메뉴명 : "+menuName);
		}
	}
	
	@RequestMapping(value="/selMakeReqDetail.do",method=RequestMethod.GET)
	public void requestDetailMake(RequestDto dto) {
		RequestDto rDto = rDao.requestDetailMake(dto);
		System.out.println("주문 제조중 상세 : "+rDto);
	}
	
	@RequestMapping(value="/regiCustomOrder.do",method=RequestMethod.GET)
	public void customOrder(RequestDto dto) {
		boolean isc = rDao.customOrder(dto);
		System.out.println("주문 등록 성공 ? : "+isc);
	}
}
