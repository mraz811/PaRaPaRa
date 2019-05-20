package com.happy.para.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happy.para.dto.FileDto;
import com.happy.para.dto.MenuDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.dto.RequestDto;
import com.happy.para.model.Menu_IService;
import com.happy.para.model.Request_IService;
import com.happy.para.model.Stats_IService;

@Controller
public class MansolTest_Ctrl {
	
	//메뉴
	@Autowired
	private Menu_IService menu_IService;
	
	//업주 : 판매 메뉴 처음 들어왔을때
	@RequestMapping(value="/ownerMenuList.do",method=RequestMethod.GET)
	public String ownerMenuList(Model model,HttpSession session) {
		String menu_category = "주메뉴";
		OwnerDto dto = (OwnerDto)session.getAttribute("loginDto");
		if(session.getAttribute("owner_menu")==null) {
			String owner_menu = dto.getOwner_menu();
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
				System.out.print(owner_menu.split(",")[i]);
			} 
			System.out.println("길이"+owner_menu.split(",").length);
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("menu_seq_", menu_seq);
			map.put("menu_category", menu_category);
			List<MenuDto> lists = menu_IService.ownerMenuList(map);
			System.out.println("업주가 선택한 메뉴 : "+lists.toString());
			model.addAttribute("menuList",lists);
		}else {
			String owner_menu = (String)session.getAttribute("owner_menu");
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
				System.out.print(owner_menu.split(",")[i]);
			} 
			System.out.println("길이"+owner_menu.split(",").length);
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("menu_seq_", menu_seq);
			map.put("menu_category", menu_category);
			List<MenuDto> lists = menu_IService.ownerMenuList(map);
			System.out.println("업주가 선택한 메뉴 : "+lists.toString());
			model.addAttribute("menuList",lists);
		}
		return "/menu/menu_list_sell";
	}
	//업주 : 판매 메뉴 리스트 2depth, 카테고리 버튼 눌렀을때
	@RequestMapping(value="/choiceMenuList.do",method=RequestMethod.POST)
	public String ownerMenuList(HttpSession session,Model model,String menu_category) {
		System.out.println("-----------------"+menu_category);
		OwnerDto dto = (OwnerDto)session.getAttribute("loginDto");
		if(session.getAttribute("owner_menu")==null) {
			String owner_menu = dto.getOwner_menu();
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
				System.out.print(owner_menu.split(",")[i]);
			} 
			System.out.println("길이"+owner_menu.split(",").length);
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("menu_seq_", menu_seq);
			map.put("menu_category", menu_category);
			List<MenuDto> lists = menu_IService.ownerMenuList(map);
			System.out.println("업주가 선택한 메뉴 : "+lists.toString());
			model.addAttribute("menuList",lists);
		}else {
			String owner_menu = (String)session.getAttribute("owner_menu");
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
				System.out.print(owner_menu.split(",")[i]);
			} 
			System.out.println("길이"+owner_menu.split(",").length);
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("menu_seq_", menu_seq);
			map.put("menu_category", menu_category);
			List<MenuDto> lists = menu_IService.ownerMenuList(map);
			System.out.println("업주가 선택한 메뉴 : "+lists.toString());
			model.addAttribute("menuList",lists);
		}
		
		return "/menu/menu_list_sell";
	}
	
	//업주 : 메뉴 삭제 버튼
	@RequestMapping(value="/menuCancel.do",method=RequestMethod.POST)
	public String ownerMenuCancel(HttpSession session,String[] cancel_menu_seq) {
		OwnerDto dto = (OwnerDto)session.getAttribute("loginDto");
		if(session.getAttribute("owner_menu")==null) {
			String owner_menu = dto.getOwner_menu();
			String owner_seq = Integer.toString(dto.getOwner_seq());
			System.out.println("지울 메뉴 번호 : "+cancel_menu_seq.toString());
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
			} 
			Map<String, Object> map1 = new HashMap<String,Object>();
			map1.put("menu_seq_", menu_seq);
			List<MenuDto> lists = menu_IService.ownerMenuList(map1);
			
			System.out.println("조회해올 현재 업주 선택메뉴 리스트 : "+lists.toString());
			
			List<MenuDto> detailLists = new ArrayList<>();
			for (int i = 0; i < cancel_menu_seq.length; i++) {
				detailLists.add(menu_IService.detailMenu(cancel_menu_seq[i]));
			}
			
			System.out.println("삭제할 메뉴 : "+detailLists.toString());
			System.out.println("업주 선택 메뉴 리스트 크기 : "+lists.size());
			System.out.println("삭제할 메뉴 번호 리스트 크기 : "+detailLists.size());
			for (int i = 0; i < lists.size(); i++) {
				for (int j = 0; j < detailLists.size(); j++) {
					if(lists.get(i).getMenu_seq()==detailLists.get(j).getMenu_seq()) {
						System.out.println("성공!!!!!!!!!!!!!!");
						lists.remove(lists.get(i));
						System.out.println("삭제된후 리스트 : "+lists.toString());
					}else {
						System.out.println("실패.............");
					}
				}
			}
			String newOwner_menu = "";
			for (int i = 0; i < lists.size(); i++) {
				newOwner_menu += lists.get(i).getMenu_seq()+",";
			}
			System.out.println("바뀔 업주 선택 메뉴 번호들 : "+newOwner_menu);
			System.out.println("-=-=-==-=-=-=-=-=-=-=-"+newOwner_menu);
			Map<String, String> map2 = new HashMap<String,String>();
			map2.put("owner_menu",newOwner_menu);
			map2.put("owner_seq",owner_seq); //세션에서 받아올꺼
			boolean isc = menu_IService.ownerMenuChoice(map2);
			System.out.println("업주의 메뉴 선택 성공? : "+isc);
			session.setAttribute("owner_menu", newOwner_menu);
		}else {
			String owner_menu = (String)session.getAttribute("owner_menu");
			String owner_seq = Integer.toString(dto.getOwner_seq());
			System.out.println("지울 메뉴 번호 : "+cancel_menu_seq.toString());
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
			} 
			Map<String, Object> map1 = new HashMap<String,Object>();
			map1.put("menu_seq_", menu_seq);
			List<MenuDto> lists = menu_IService.ownerMenuList(map1);
			
			System.out.println("조회해올 현재 업주 선택메뉴 리스트 : "+lists.toString());
			
			List<MenuDto> detailLists = new ArrayList<>();
			for (int i = 0; i < cancel_menu_seq.length; i++) {
				detailLists.add(menu_IService.detailMenu(cancel_menu_seq[i]));
			}
			
			System.out.println("삭제할 메뉴 : "+detailLists.toString());
			System.out.println("업주 선택 메뉴 리스트 크기 : "+lists.size());
			System.out.println("삭제할 메뉴 번호 리스트 크기 : "+detailLists.size());
			for (int i = 0; i < lists.size(); i++) {
				for (int j = 0; j < detailLists.size(); j++) {
					if(lists.get(i).getMenu_seq()==detailLists.get(j).getMenu_seq()) {
						System.out.println("성공!!!!!!!!!!!!!!");
						lists.remove(lists.get(i));
						System.out.println("삭제된후 리스트 : "+lists.toString());
					}else {
						System.out.println("실패.............");
					}
				}
			}
			String newOwner_menu = "";
			for (int i = 0; i < lists.size(); i++) {
				newOwner_menu += lists.get(i).getMenu_seq()+",";
			}
			System.out.println("바뀔 업주 선택 메뉴 번호들 : "+newOwner_menu);
			System.out.println("-=-=-==-=-=-=-=-=-=-=-"+newOwner_menu);
			Map<String, String> map2 = new HashMap<String,String>();
			map2.put("owner_menu",newOwner_menu);
			map2.put("owner_seq",owner_seq); //세션에서 받아올꺼
			boolean isc = menu_IService.ownerMenuChoice(map2);
			System.out.println("업주의 메뉴 선택 성공? : "+isc);
			session.removeAttribute("owner_menu");
			session.setAttribute("owner_menu", newOwner_menu);
		}
		
		return "redirect:/ownerMenuList.do";
	}
	
	//업주 : 전체 메뉴 처음들어왔을때
	@RequestMapping(value="/ownerAllMenuList.do",method=RequestMethod.GET)
	public String ownerMenuList(Model model) {
		String menu_category = "주메뉴";
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		System.out.println("전체 메뉴 조회 : "+lists);
		model.addAttribute("menuList", lists);
		return "/menu/menu_list_owner";
	}
	
	//업주 : 전체 메뉴 에서 카테고리 버튼 눌럿을때
	@RequestMapping(value="/OselAllMenuList.do",method=RequestMethod.POST)
	public String OallMenu(Model model,String menu_category) {
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		System.out.println("전체 메뉴 조회 : "+lists);
		model.addAttribute("menuList", lists);
		return "/menu/menu_list_owner";
	}
	
	//업주 : 판매 메뉴 선택  버튼
		@RequestMapping(value="/menuChoice.do",method=RequestMethod.POST)
		public String ownerMenuChoice(HttpSession session,String[] menu_seq) {
			String newOwner_menu = "";
			System.out.println(menu_seq[0]);
			for (int i = 0; i < menu_seq.length; i++) {
				newOwner_menu += menu_seq[i]+",";
			}
			OwnerDto oDto = (OwnerDto)session.getAttribute("loginDto");
			if(session.getAttribute("owner_menu")==null) {
				String owner_seq = Integer.toString(oDto.getOwner_seq());
				String oldOwnerMenu = oDto.getOwner_menu();
				Map<String, String> map = new HashMap<String,String>();
				map.put("owner_menu",oldOwnerMenu+newOwner_menu);
				map.put("owner_seq",owner_seq); //세션에서 받아올꺼
				boolean isc = menu_IService.ownerMenuChoice(map);
				System.out.println("업주의 메뉴 선택 성공? : "+isc);
				session.setAttribute("owner_menu", oldOwnerMenu+newOwner_menu);
			}else {
				String owner_seq = Integer.toString(oDto.getOwner_seq());
				String oldOwnerMenu = (String)session.getAttribute("owner_menu");
				Map<String, String> map = new HashMap<String,String>();
				map.put("owner_menu",oldOwnerMenu+newOwner_menu);
				map.put("owner_seq",owner_seq); //세션에서 받아올꺼
				boolean isc = menu_IService.ownerMenuChoice(map);
				System.out.println("업주의 메뉴 선택 성공? : "+isc);
				session.removeAttribute("owner_menu");
				session.setAttribute("owner_menu", oldOwnerMenu+newOwner_menu);
			}
			
			return "/menu/menu_list_owner";
		}
	//담당자 : 1depth로 처음 메뉴 들어왓을때
	@RequestMapping(value="/adminAllMenuList.do",method=RequestMethod.GET)
	public String adminMenuList(Model model) {
		String menu_category = "주메뉴";
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		System.out.println("전체 메뉴 조회 : "+lists);
		model.addAttribute("menuList", lists);
		return "/menu/menu_list_admin";
	}
	
	//담당자 : 카테고리 버튼 눌럿을때
	@RequestMapping(value="/AselAllMenuList.do",method=RequestMethod.POST)
	public String AallMenu(Model model,String menu_category) {
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		System.out.println("전체 메뉴 조회 : "+lists);
		model.addAttribute("menuList", lists);
		return "/menu/menu_list_admin";
	}
	
	@RequestMapping(value="/menuRegiForm.do",method=RequestMethod.GET)
	public String menuList() {
		return "/menu/menu_regiForm";
	}
	
	@RequestMapping(value="/regiNewMenu.do",method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String insertMenu(MenuDto dto) {
		boolean isc = menu_IService.insertMenu(dto);
		System.out.println("담당자 메뉴 등록 성공? : "+isc);
		return "/menu/menu_regiForm";
	}
	
	@RequestMapping(value="/regiNewMenu2.do",method=RequestMethod.GET)
	public void insertMenuFile(FileDto dto) {
		boolean isc = menu_IService.insertMenuFile(dto);
		System.out.println("담당자 메뉴 이미지 등록 성공? : "+isc);
	}
	
	@RequestMapping(value="/modifyMenuForm.do",method=RequestMethod.GET)
	public String detailMenu(Model model,String menu_seq) {
		MenuDto dto = menu_IService.detailMenu(menu_seq);
		model.addAttribute("menuDto", dto);
		return "/menu/menu_modiForm";
	}
	
	@RequestMapping(value="/menuModi.do",method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String modifyMenu(MenuDto dto,Model model) {
		boolean isc = menu_IService.modifyMenu(dto);
		System.out.println("담당자 메뉴 수정 성공? : "+isc);
		MenuDto mDto = menu_IService.detailMenu(Integer.toString(dto.getMenu_seq()));
		model.addAttribute("menuDto", mDto);
		return "/menu/menu_modiForm";
	}
	
	@RequestMapping(value="/menuModi2.do",method=RequestMethod.GET)
	public void modifyMenuFile(FileDto dto) {
		boolean isc = menu_IService.modifyMenuFile(dto);
		System.out.println("담당자 메뉴 이미지 수정 성공? : "+isc);
	}
	
	@RequestMapping(value="/delMenu.do",method=RequestMethod.GET)
	public String deleteMenu(String menu_seq,Model model) {
		System.out.println("넘겨받은 menu_seq : "+menu_seq);
		boolean isc = menu_IService.deleteMenu(menu_seq);
		System.out.println("담당자 메뉴 삭제 성공? : "+isc);
		String menu_category = "주메뉴";
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		System.out.println("전체 메뉴 조회 : "+lists);
		model.addAttribute("menuList", lists);
		return "/menu/menu_list_admin";
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
