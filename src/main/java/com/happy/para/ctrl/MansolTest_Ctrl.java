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
import com.happy.para.dto.RequestDto;
import com.happy.para.model.Menu_IService;
import com.happy.para.model.Request_IService;
import com.happy.para.model.Stats_IService;

@Controller
public class MansolTest_Ctrl {
	
	//메뉴
	@Autowired
	private Menu_IService menu_IService;
	
	@RequestMapping(value="/choiceMenuList.do",method=RequestMethod.GET)
	public void ownerMenuList(String[] menu_seq) {
		Map<String, String[]> map = new HashMap<String,String[]>();
		map.put("menu_seq_", menu_seq);
		List<MenuDto> lists = menu_IService.ownerMenuList(map);
		System.out.println("업주가 선택한 메뉴 : "+lists.toString());
	}
	
	@RequestMapping(value="/menuChoice.do",method=RequestMethod.GET)
	public void ownerMenuChoice(String owner_menu,String owner_seq) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("owner_menu",owner_menu);
		map.put("owner_seq",owner_seq);
		boolean isc = menu_IService.ownerMenuChoice(map);
		System.out.println("업주의 메뉴 선택 성공? : "+isc);
	}
	
	@RequestMapping(value="/selAllMenuList.do",method=RequestMethod.GET)
	public void allMenu() {
		List<MenuDto> lists = menu_IService.allMenu();
		System.out.println("전체 메뉴 조회 : "+lists);
	}
	
	@RequestMapping(value="/regiNewMenu.do",method=RequestMethod.GET)
	public void insertMenu(MenuDto dto) {
		boolean isc = menu_IService.insertMenu(dto);
		System.out.println("담당자 메뉴 등록 성공? : "+isc);
	}
	
	@RequestMapping(value="/regiNewMenu2.do",method=RequestMethod.GET)
	public void insertMenuFile(FileDto dto) {
		boolean isc = menu_IService.insertMenuFile(dto);
		System.out.println("담당자 메뉴 이미지 등록 성공? : "+isc);
	}
	
	@RequestMapping(value="/menuModi.do",method=RequestMethod.GET)
	public void modifyMenu(MenuDto dto) {
		boolean isc = menu_IService.modifyMenu(dto);
		System.out.println("담당자 메뉴 수정 성공? : "+isc);
	}
	
	@RequestMapping(value="/menuModi2.do",method=RequestMethod.GET)
	public void modifyMenuFile(FileDto dto) {
		boolean isc = menu_IService.modifyMenuFile(dto);
		System.out.println("담당자 메뉴 이미지 수정 성공? : "+isc);
	}
	
	@RequestMapping(value="/delMenu.do",method=RequestMethod.GET)
	public void deleteMenu(String menu_seq) {
		boolean isc = menu_IService.deleteMenu(menu_seq);
		System.out.println("담당자 메뉴 삭제 성공? : "+isc);
	}
	
	//주문
	@Autowired
	private Request_IService request_IService;
	
	@RequestMapping(value="/updateOrderState.do",method=RequestMethod.GET)
	public void updateOscode(RequestDto dto) {
		boolean isc = request_IService.updateOscode(dto);
		System.out.println("주문 상태 코드 업데이트 성공 ? : "+isc);
	}
	
	@RequestMapping(value="/selRequestList.do",method=RequestMethod.GET)
	public void requestList(String store_code,String start,String end) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("store_code", store_code);
		map.put("start", start);
		map.put("end", end);
		List<RequestDto> lists = request_IService.requestList(map);
		System.out.println("주문 완료,환불 : "+lists);
	}
	
	@RequestMapping(value="/selWaitRequest.do",method=RequestMethod.GET)
	public void requestListWait(String store_code) {
		List<RequestDto> lists = request_IService.requestListWait(store_code);
		Map<String, String[]> map = new HashMap<String,String[]>();
		for (int i = 0; i < lists.size(); i++) {
			String[] menu_seq = lists.get(i).getRequest_menu().split(",");
			map.put("menu_seq_", menu_seq);
			String menuName = request_IService.requestMenuName(map);
			System.out.println("주문 대기중 : "+lists.get(i));
			System.out.println("주문 대기중인 메뉴명 : "+menuName);
		}
	}
	
	@RequestMapping(value="/selWaitReqDetail.do",method=RequestMethod.GET)
	public void requestDetailWait(RequestDto dto) {
		RequestDto rDto = request_IService.requestDetailWait(dto);
		System.out.println("주문 대기중 상세 : "+rDto);
	}
	
	@RequestMapping(value="/selMakeRequest.do",method=RequestMethod.GET)
	public void requestListMake(String store_code) {
		List<RequestDto> lists = request_IService.requestListMake(store_code);
		Map<String, String[]> map = new HashMap<String,String[]>();
		for (int i = 0; i < lists.size(); i++) {
			String[] menu_seq = lists.get(i).getRequest_menu().split(",");
			map.put("menu_seq_", menu_seq);
			String menuName = request_IService.requestMenuName(map);
			System.out.println("주문 제조중 : "+lists.get(i));
			System.out.println("주문 제조중인 메뉴명 : "+menuName);
		}
	}
	
	@RequestMapping(value="/selMakeReqDetail.do",method=RequestMethod.GET)
	public void requestDetailMake(RequestDto dto) {
		RequestDto rDto = request_IService.requestDetailMake(dto);
		System.out.println("주문 제조중 상세 : "+rDto);
	}
	
	@RequestMapping(value="/regiCustomOrder.do",method=RequestMethod.GET)
	public void customOrder(RequestDto dto) {
		boolean isc = request_IService.customOrder(dto);
		System.out.println("주문 등록 성공 ? : "+isc);
	}
	
	//통계
	@Autowired
	private Stats_IService stats_IService;
	
	@RequestMapping(value="/ownerStatsIn.do",method=RequestMethod.GET)
	public void ownerStatsIncome(String store_code,String start,String end) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("store_code", store_code);
		map.put("start", start);
		map.put("end", end);
		int n = stats_IService.ownerStatsIncome(map);
		System.out.println("업주 수익 통계에 쓸 값 : "+n);
	}
	
	@RequestMapping(value="/ownerStatsOut.do",method=RequestMethod.GET)
	public void ownerStatsOutcome(String store_code,String start,String end) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("store_code", store_code);
		map.put("start", start);
		map.put("end", end);
		int n = stats_IService.ownerStatsOutcome(map);
		System.out.println("업주 지출 통계에 쓸 값 : "+n);
	}
	
	@RequestMapping(value="/ownerStatsMenu.do",method=RequestMethod.GET)
	public void ownerStatsMenu(String store_code,String start,String end) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("store_code", store_code);
		map.put("start", start);
		map.put("end", end);
		Map<String, Integer> resultMap = stats_IService.ownerStatsMenu(map);
		
		System.out.println("업주 메뉴 통계에 쓸 값 : "+resultMap);
	}
	
	@RequestMapping(value="/adminStatsIn.do",method=RequestMethod.GET)
	public void adminStatsIncome(String[] store_code,String start,String end) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("store_code_", store_code);
		map.put("start", start);
		map.put("end", end);
		int n = stats_IService.adminStatsIncome(map);
		System.out.println("업주 수익 통계에 쓸 값 : "+n);
	}
	
	@RequestMapping(value="/adminStatsMenu.do",method=RequestMethod.GET)
	public void adminStatsMenu(String[] store_code,String start,String end) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("store_code_", store_code);
		map.put("start", start);
		map.put("end", end);
		Map<String, Integer> resultMap = stats_IService.adminStatsMenu(map);
		System.out.println("업주 메뉴 통계에 쓸 값 : "+resultMap);
	}
}
