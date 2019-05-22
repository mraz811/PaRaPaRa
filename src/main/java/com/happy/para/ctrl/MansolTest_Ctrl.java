package com.happy.para.ctrl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
	@ResponseBody
	public JSONObject ownerMenuList(HttpSession session,String menu_category) {
		System.out.println("-----------------"+menu_category);
		OwnerDto dto = (OwnerDto)session.getAttribute("loginDto");
		JSONObject json = null;
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
			json = objectJson(lists);
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
			json = objectJson(lists);
		}
		return json;
	}
	@SuppressWarnings("unchecked")
	private JSONObject objectJson(List<MenuDto> lists) {
		JSONObject json = new JSONObject();
		JSONArray jLists = new JSONArray();
		JSONObject jList = null;
		for (int i = 0; i < lists.size(); i++) {
			jList = new JSONObject();
//			jList.put("file_seq", lists.get(i).getFileDto().getFile_seq());
//			jList.put("file_tname", lists.get(i).getFileDto().getFile_tname());
//			jList.put("file_rname", lists.get(i).getFileDto().getFile_rname());
			jList.put("menu_seq", lists.get(i).getMenu_seq());
			jList.put("menu_name", lists.get(i).getMenu_name());
			jList.put("menu_price", lists.get(i).getMenu_price());
			jList.put("menu_category", lists.get(i).getMenu_category());
			
			jLists.add(jList);
		}
		json.put("choiceMenu", jLists);
		System.out.println(json+"가가가");
		System.out.println(json.toString()+"나나나");
		return json;
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
	@ResponseBody
	public JSONObject OallMenu(String menu_category) {
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		System.out.println("전체 메뉴 조회 : "+lists);
		JSONObject json = null;
		json = objectJson(lists);
		return json;
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
				Map<String, String> map = new HashMap<String,String>();
				map.put("owner_menu",newOwner_menu);
				map.put("owner_seq",owner_seq); //세션에서 받아올꺼
				boolean isc = menu_IService.ownerMenuChoice(map);
				System.out.println("업주의 메뉴 선택 성공? : "+isc);
				session.setAttribute("owner_menu", newOwner_menu);
			}else {
				String owner_seq = Integer.toString(oDto.getOwner_seq());
				Map<String, String> map = new HashMap<String,String>();
				map.put("owner_menu",newOwner_menu);
				map.put("owner_seq",owner_seq); //세션에서 받아올꺼
				boolean isc = menu_IService.ownerMenuChoice(map);
				System.out.println("업주의 메뉴 선택 성공? : "+isc);
				session.removeAttribute("owner_menu");
				session.setAttribute("owner_menu", newOwner_menu);
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
	@ResponseBody
	public JSONObject AallMenu(String menu_category) {
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		System.out.println("전체 메뉴 조회 : "+lists);
		JSONObject json = null;
		json = objectJson(lists);
		return json;
	}
	
	@RequestMapping(value="/menuRegiForm.do",method=RequestMethod.GET)
	public String menuList() {
		return "/menu/menu_regiForm";
	}
	// 담당자 : 메뉴 등록
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
	// 담당자 : 메뉴 수정
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
	// 담당자 : 메뉴 삭제
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
	///////////////////////////////////////////////////////////////////////////
	//주문
	@Autowired
	private Request_IService request_IService;
	
	//만드는중
	@RequestMapping(value="/updateOrderState.do",method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String updateOscode(String request_seq,String os_code,HttpSession session) {
		OwnerDto oDto = (OwnerDto)session.getAttribute("loginDto");
		RequestDto rDto = new RequestDto();
		rDto.setRequest_seq(Integer.parseInt(request_seq));
		rDto.setStore_code(oDto.getStore_code());
		rDto.setOs_code(Integer.parseInt(os_code));
		System.out.println("=======넘겨받은 주문 번호 : "+request_seq);
		System.out.println("=======바뀔 주문 상태 코드 : "+os_code);
		boolean isc = request_IService.updateOscode(rDto);
		System.out.println("주문 상태 코드 업데이트 성공 ? : "+isc);
		return isc?"성공":"실패";
	}
	// 업주 : 주문 완료, 환불 내역 조회 20190522:날짜 집어넣는거 해야뎀
	@RequestMapping(value="/selRequestList.do",method=RequestMethod.GET)
	public String requestList(HttpSession session,Model model) {
		Map<String, String> map = new HashMap<String,String>();
		OwnerDto oDto = (OwnerDto)session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		String start1 = oDto.getOwner_start();
		String start2 = start1.substring(0, 4);
		String start3 = start1.substring(5,7);
		String start4 = start1.substring(8,10);
		String start = start2+start3+start4;
		System.out.println("@@@@시작일@@@@"+start);
		Date date = new Date();
		SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");
		String tempEnd = day.format(date);
		int a = Integer.parseInt(tempEnd)+1;
		String end = Integer.toString(a);
		System.out.println("@@@@종료일@@@@"+end);
		map.put("store_code", store_code);
		map.put("start", start);
		map.put("end", end);
		List<RequestDto> lists = request_IService.requestList(map);
		System.out.println("주문 완료,환불 : "+lists);
		System.out.println("주문 완료,환불 크기 : "+lists.size());
		
		Map<String, Object> map1 = new HashMap<>();
		
		for (int i = 0; i < lists.size(); i++) { //1:2,2:1,3:2,4:3,5:4
			String temp = lists.get(i).getRequest_menu();
			int tempLen = temp.length();
			int templenChange = temp.replaceAll(",","").length();
			int arraySize = tempLen-templenChange;
			
			String[] menu = new String[arraySize];
			String[] cnt = new String[arraySize];
			StringTokenizer temp1 = new StringTokenizer(temp,",");
			int num = 0;
			while(temp1.hasMoreTokens()) { 
				String str1 = temp1.nextToken();
				int idx = str1.indexOf(":");
				menu[num] = str1.substring(0,idx);
				cnt[num] = str1.substring(idx+1);
				num++;
			}
			String[] menu_name = new String[menu.length];
			map1.put("menu_seq_", menu);
			menu_name = request_IService.requestMenuName(map1);
			String request_menu = "";
			for (int j = 0; j < menu_name.length; j++) {
				request_menu += menu_name[j]+cnt[j]+",";
			}
			lists.get(i).setMenu_name(request_menu.substring(0, request_menu.lastIndexOf(",")));
			System.out.println(lists.get(i));
			System.out.println(Arrays.toString(menu) +"메뉴");
			System.out.println(Arrays.toString(cnt)+"갯수");
		}
		model.addAttribute("requestList",lists);
		return "/request/request_list";
	}
	//업주 : 주문 현황 페이지
	@RequestMapping(value="/selRequestStatus.do",method=RequestMethod.GET)
	public String requestListStatus(HttpSession session,Model model) {
		OwnerDto oDto = (OwnerDto)session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		Date date = new Date();
		SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");
		String start = day.format(date);
		System.out.println(day.format(date));
		Map<String, String> map = new HashMap<>();
		int start111 = Integer.parseInt(start)-1;
		start = Integer.toString(start111);
		System.out.println("==============시작일 : "+start);
		map.put("store_code", store_code);
		map.put("start", start);
		int end = Integer.parseInt(start)+2;
		map.put("end", Integer.toString(end));
		System.out.println("===========종료일 : "+end);
		List<RequestDto> waitLists = request_IService.requestListWait(map);
		Map<String, Object> map1 = new HashMap<>();
		
		for (int i = 0; i < waitLists.size(); i++) { //1:2,2:1,3:2,4:3,5:4
			String temp = waitLists.get(i).getRequest_menu();
			int tempLen = temp.length();
			int templenChange = temp.replaceAll(",","").length();
			int arraySize = tempLen-templenChange;
			
			String[] menu = new String[arraySize];
			String[] cnt = new String[arraySize];
			StringTokenizer temp1 = new StringTokenizer(temp,",");
			int num = 0;
			while(temp1.hasMoreTokens()) { 
				String str1 = temp1.nextToken();
				int idx = str1.indexOf(":");
				menu[num] = str1.substring(0,idx);
				cnt[num] = str1.substring(idx+1);
				num++;
			}
			String[] menu_name = new String[menu.length];
			map1.put("menu_seq_", menu);
			menu_name = request_IService.requestMenuName(map1);
			String request_menu = "";
			for (int j = 0; j < menu_name.length; j++) {
				request_menu += menu_name[j]+cnt[j]+",";
			}
			waitLists.get(i).setMenu_name(request_menu.substring(0, request_menu.lastIndexOf(",")));
			System.out.println(waitLists.get(i));
			System.out.println(Arrays.toString(menu) +"메뉴");
			System.out.println(Arrays.toString(cnt)+"갯수");
		}

		List<RequestDto> makeLists = request_IService.requestListMake(map);
		
		Map<String, Object> map2 = new HashMap<>();
		for (int i = 0; i < makeLists.size(); i++) { //1:4,2:3,3:1
			String temp = makeLists.get(i).getRequest_menu();
			int tempLen = temp.length();
			int templenChange = temp.replaceAll(",","").length();
			int arraySize = tempLen-templenChange;
			
			String[] menu = new String[arraySize];
			String[] cnt = new String[arraySize];
			StringTokenizer temp1 = new StringTokenizer(temp,",");
			int num = 0;
			while(temp1.hasMoreTokens()) { 
				String str1 = temp1.nextToken();
				int idx = str1.indexOf(":");
				menu[num] = str1.substring(0,idx);
				cnt[num] = str1.substring(idx+1);
				num++;
			}
			String[] menu_name = new String[menu.length];
			map2.put("menu_seq_", menu);
			menu_name = request_IService.requestMenuName(map2);
			String request_menu = "";
			for (int j = 0; j < menu_name.length; j++) {
				request_menu += menu_name[j]+cnt[j]+",";
			}
			makeLists.get(i).setMenu_name(request_menu.substring(0, request_menu.lastIndexOf(",")));
			System.out.println(makeLists.get(i));
			System.out.println(Arrays.toString(menu) +"메뉴");
			System.out.println(Arrays.toString(cnt)+"갯수");
			
		}
		
		model.addAttribute("waitLists",waitLists);
		model.addAttribute("makeLists",makeLists);
		return "/request/request_status";
	}
	//업주 : 주문 대기중 상세조회
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/selWaitReqDetail.do",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject requestDetailWait(HttpSession session,String request_seq) {
		System.out.println("========"+request_seq);
		OwnerDto oDto = (OwnerDto)session.getAttribute("loginDto");
		RequestDto dto = new RequestDto();
		dto.setStore_code(oDto.getStore_code());
		dto.setRequest_seq(Integer.parseInt(request_seq));
		RequestDto rDto = request_IService.requestDetailWait(dto);
		System.out.println("메뉴 이름 넣기전 : "+rDto);
		
		Map<String, Object> map = new HashMap<>();
		
		
		String temp = rDto.getRequest_menu();
		int tempLen = temp.length();
		int templenChange = temp.replaceAll(",","").length();
		int arraySize = tempLen-templenChange;
		
		String[] menu = new String[arraySize];
		String[] cnt = new String[arraySize];
		StringTokenizer temp1 = new StringTokenizer(temp,",");
		int num = 0;
		while(temp1.hasMoreTokens()) { 
			String str1 = temp1.nextToken();
			int idx = str1.indexOf(":");
			menu[num] = str1.substring(0,idx);
			cnt[num] = str1.substring(idx+1);
			num++;
		}
		String[] menu_name = new String[menu.length];
		map.put("menu_seq_", menu);
		menu_name = request_IService.requestMenuName(map);
		String request_menu = "";
		for (int j = 0; j < menu_name.length; j++) {
			request_menu += menu_name[j]+cnt[j]+",";
		}
		rDto.setMenu_name(request_menu.substring(0,request_menu.lastIndexOf(",")));
		
		
		JSONObject json = new JSONObject();
		json.put("makeMenu", rDto);
		System.out.println("주문 제조중 상세 : "+rDto);
		return json;
	}
	// 업주 : 주문 제조중 상세조회
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/selMakeReqDetail.do",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject requestDetailMake(HttpSession session,String request_seq) {
		System.out.println("========"+request_seq);
		OwnerDto oDto = (OwnerDto)session.getAttribute("loginDto");
		RequestDto dto = new RequestDto();
		dto.setStore_code(oDto.getStore_code());
		dto.setRequest_seq(Integer.parseInt(request_seq));
		RequestDto rDto = request_IService.requestDetailMake(dto);
		
		Map<String, Object> map = new HashMap<String, Object>();
		String temp = rDto.getRequest_menu();
		int tempLen = temp.length();
		int templenChange = temp.replaceAll(",","").length();
		int arraySize = tempLen-templenChange;
		
		String[] menu = new String[arraySize];
		String[] cnt = new String[arraySize];
		StringTokenizer temp1 = new StringTokenizer(temp,",");
		int num = 0;
		while(temp1.hasMoreTokens()) { 
			String str1 = temp1.nextToken();
			int idx = str1.indexOf(":");
			menu[num] = str1.substring(0,idx);
			cnt[num] = str1.substring(idx+1);
			num++;
		}
		String[] menu_name = new String[menu.length];
		map.put("menu_seq_", menu);
		menu_name = request_IService.requestMenuName(map);
		String request_menu = "";
		for (int j = 0; j < menu_name.length; j++) {
			request_menu += menu_name[j]+cnt[j]+",";
		}
		rDto.setMenu_name(request_menu.substring(0,request_menu.lastIndexOf(",")));
		
		
		JSONObject json = new JSONObject();
		json.put("makeMenu", rDto);
		System.out.println("주문 제조중 상세 : "+rDto);
		return json;
	}
	
	@RequestMapping(value="/regiCustomOrder.do",method=RequestMethod.GET)
	public void customOrder(RequestDto dto) {
		boolean isc = request_IService.customOrder(dto);
		System.out.println("주문 등록 성공 ? : "+isc);
	}
	///////////////////////////////////////////////////////////////////////////
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
