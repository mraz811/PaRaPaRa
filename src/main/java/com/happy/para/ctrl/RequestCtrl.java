package com.happy.para.ctrl;

import java.text.SimpleDateFormat;
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

import com.happy.para.dto.MenuDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.dto.PagingDto;
import com.happy.para.dto.RequestDto;
import com.happy.para.model.Menu_IService;
import com.happy.para.model.Request_IService;


@Controller
public class RequestCtrl {

	// 주문
	@Autowired
	private Request_IService request_IService;
	
	@Autowired
	private Menu_IService menu_IService;

	// 업주 : 주문 상태 변경
	@RequestMapping(value = "/updateOrderState.do", method = RequestMethod.POST, produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String updateOscode(String request_seq, String os_code, HttpSession session) {
		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		RequestDto rDto = new RequestDto();
		rDto.setRequest_seq(Integer.parseInt(request_seq));
		rDto.setStore_code(oDto.getStore_code());
		rDto.setOs_code(Integer.parseInt(os_code));
		System.out.println("=======넘겨받은 주문 번호 : " + request_seq);
		System.out.println("=======바뀔 주문 상태 코드 : " + os_code);
		boolean isc = request_IService.updateOscode(rDto);
		System.out.println("주문 상태 코드 업데이트 성공 ? : " + isc);
		return isc ? "성공" : "실패";
	}

	// 업주 : 주문 완료, 환불 내역 조회
	@RequestMapping(value = "/selRequestList.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String requestList(HttpSession session, Model model, String os_code) {
		System.out.println("os_code받음??" + os_code);
		if (os_code != null && os_code.equals("4")) {
			os_code = null;
		}
		PagingDto rowDto = new PagingDto();
		Map<String, String> map = new HashMap<String, String>();
		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		String start1 = oDto.getOwner_start();
		String start2 = start1.substring(0, 4);
		String start3 = start1.substring(5, 7);
		String start4 = start1.substring(8, 10);
		String start = start2 + start3 + start4;
		System.out.println("@@@@시작일@@@@" + start);
//		Date date = new Date();
//		SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");
//		String tempEnd = day.format(date);
//		int a = Integer.parseInt(tempEnd) + 1;
//		String end = Integer.toString(a);
//		System.out.println("@@@@종료일@@@@" + end);
		map.put("store_code", store_code);
		map.put("start", start);
//		map.put("end", end);
		map.put("dayStart", start);
//		map.put("dayEnd", end);
		map.put("pageStart", rowDto.getStart() + "");
		map.put("pageEnd", rowDto.getEnd() + "");
		map.put("os_code", os_code);
		rowDto.setTotal(request_IService.selectTotalRequest(map));
		List<RequestDto> lists = request_IService.requestListPaging(map);
		System.out.println("주문 완료,환불 : " + lists);
		System.out.println("주문 완료,환불 크기 : " + lists.size());

		Map<String, Object> map1 = new HashMap<>();

		for (int i = 0; i < lists.size(); i++) { // 1:2,2:1,3:2,4:3,5:4,
			String temp = lists.get(i).getRequest_menu();
			int tempLen = temp.length();
			int templenChange = temp.replaceAll(",", "").length();
			int arraySize = tempLen - templenChange;

			String[] menu = new String[arraySize];
			String[] cnt = new String[arraySize];
			StringTokenizer temp1 = new StringTokenizer(temp, ",");
			int num = 0;
			while (temp1.hasMoreTokens()) {
				String str1 = temp1.nextToken();
				int idx = str1.indexOf(":");
				menu[num] = str1.substring(0, idx);
				cnt[num] = str1.substring(idx + 1);
				num++;
			}
			String[] menu_name = new String[menu.length];
			map1.put("menu_seq_", menu);
			menu_name = request_IService.requestMenuName(map1);
			String request_menu = "";
			for (int j = 0; j < menu_name.length; j++) {
				System.out.println("메뉴명"+menu_name[j]);
				request_menu += menu_name[j] + cnt[j] + ",";
			}
			System.out.println(request_menu);
			System.out.println(request_menu.length());
			System.out.println(request_menu.lastIndexOf(","));
			lists.get(i).setMenu_name(request_menu.substring(0, request_menu.lastIndexOf(",")));
			System.out.println(lists.get(i));
			System.out.println(Arrays.toString(menu) + "메뉴");
			System.out.println(Arrays.toString(cnt) + "갯수");
		}

		session.removeAttribute("os_code");
		session.setAttribute("os_code", os_code);
		model.addAttribute("requestList", lists);
		model.addAttribute("requestRow", rowDto);
		return "/request/requestList";
	}

	// 업주 : 주문 완료,환불 내역 조회 페이징
	@RequestMapping(value = "/selRequestListPaging.do", method = RequestMethod.POST, produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String requestPaging(HttpSession session, Model model, PagingDto pDto) {
		JSONObject json = null;
		Map<String, String> map = new HashMap<String, String>();
		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		String start1 = oDto.getOwner_start();
		String start2 = start1.substring(0, 4);
		String start3 = start1.substring(5, 7);
		String start4 = start1.substring(8, 10);
		String start = start2 + start3 + start4;
		System.out.println("@@@@시작일@@@@" + start);
//		Date date = new Date();
//		SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");
//		String tempEnd = day.format(date);
//		int a = Integer.parseInt(tempEnd) + 1;
//		String end = Integer.toString(a);
//		System.out.println("@@@@종료일@@@@" + end);

		String os_code = (String) session.getAttribute("os_code");
		map.put("store_code", store_code);
		map.put("dayStart", start);
//		map.put("dayEnd", end);
		map.put("pageStart", pDto.getStart() + "");
		map.put("pageEnd", pDto.getEnd() + "");
		map.put("os_code", os_code);
		pDto.setTotal(request_IService.selectTotalRequest(map));
		List<RequestDto> lists = request_IService.requestListPaging(map);
		System.out.println("주문 완료,환불 : " + lists);
		System.out.println("주문 완료,환불 크기 : " + lists.size());

		Map<String, Object> map1 = new HashMap<>();

		for (int i = 0; i < lists.size(); i++) { // 1:2,2:1,3:2,4:3,5:4
			String temp = lists.get(i).getRequest_menu();
			int tempLen = temp.length();
			int templenChange = temp.replaceAll(",", "").length();
			int arraySize = tempLen - templenChange;

			String[] menu = new String[arraySize];
			String[] cnt = new String[arraySize];
			StringTokenizer temp1 = new StringTokenizer(temp, ",");
			int num = 0;
			while (temp1.hasMoreTokens()) {
				String str1 = temp1.nextToken();
				int idx = str1.indexOf(":");
				menu[num] = str1.substring(0, idx);
				cnt[num] = str1.substring(idx + 1);
				num++;
			}
			String[] menu_name = new String[menu.length];
			map1.put("menu_seq_", menu);
			menu_name = request_IService.requestMenuName(map1);
			String request_menu = "";
			for (int j = 0; j < menu_name.length; j++) {
				request_menu += menu_name[j] + cnt[j] + ",";
			}
			lists.get(i).setMenu_name(request_menu.substring(0, request_menu.lastIndexOf(",")));
			System.out.println(lists.get(i));
			System.out.println(Arrays.toString(menu) + "메뉴");
			System.out.println(Arrays.toString(cnt) + "갯수");
		}
		json = objectJsonPaging(lists, pDto);
		session.removeAttribute("requestRow");
		session.setAttribute("requestRow", pDto);
		return json.toString();
	}

	// JSONArray 형태로 페이징 처리된 매장 리스트를 담을 예정
	@SuppressWarnings("unchecked")
	private JSONObject objectJsonPaging(List<RequestDto> lists, PagingDto requestRow) {
		JSONObject json = new JSONObject(); // 최종적으로 담는애는 여긴데
		JSONArray jLists = new JSONArray(); // 어레이리스트를 담을때는 여기에
		JSONObject jList = null; // 그냥 얘는 제이슨 타입으로

		for (RequestDto dto : lists) {
			jList = new JSONObject();
			jList.put("rnum", dto.getRnum());
			jList.put("request_seq", dto.getRequest_seq());
			jList.put("menu_name", dto.getMenu_name());
			jList.put("request_price", dto.getRequest_price());
			jList.put("request_time", dto.getRequest_time());
			jList.put("request_bank", dto.getRequest_bank());
			jList.put("request_account", dto.getRequest_account());
			jList.put("store_code", dto.getStore_code());
			jList.put("os_code", dto.getOs_code());

			jLists.add(jList);
		}

		jList = new JSONObject();
		jList.put("pageList", requestRow.getPageList());
		jList.put("index", requestRow.getIndex());
		jList.put("pageNum", requestRow.getPageNum());
		jList.put("listNum", requestRow.getListNum());
		jList.put("total", requestRow.getTotal());
		jList.put("count", requestRow.getCount());

		json.put("requestList", jLists);
		json.put("requestRow", jList);

		return json;
	}
	
	//업주 : 주문 완료,환불 내역 상세 조회
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/selRequestDetail.do", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject requestDetail(HttpSession session,String request_seq,String os_code) {
		System.out.println("========" + request_seq);
		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		RequestDto dto = new RequestDto();
		dto.setStore_code(oDto.getStore_code());
		dto.setRequest_seq(Integer.parseInt(request_seq));
		dto.setOs_code(Integer.parseInt(os_code));
		RequestDto rDto = request_IService.requestDetail(dto);
		System.out.println("메뉴 이름 넣기전 : " + rDto);

		Map<String, Object> map = new HashMap<>();

		String temp = rDto.getRequest_menu();
		int tempLen = temp.length();
		int templenChange = temp.replaceAll(",", "").length();
		int arraySize = tempLen - templenChange;

		String[] menu = new String[arraySize];
		String[] cnt = new String[arraySize];
		StringTokenizer temp1 = new StringTokenizer(temp, ",");
		int num = 0;
		while (temp1.hasMoreTokens()) {
			String str1 = temp1.nextToken();
			int idx = str1.indexOf(":");
			menu[num] = str1.substring(0, idx);
			cnt[num] = str1.substring(idx + 1);
			num++;
		}
		String[] menu_name = new String[menu.length];
		map.put("menu_seq_", menu);
		menu_name = request_IService.requestMenuName(map);
		String request_menu = "";
		for (int j = 0; j < menu_name.length; j++) {
			request_menu += menu_name[j] + cnt[j] + ",";
		}
		rDto.setMenu_name(request_menu.substring(0, request_menu.lastIndexOf(",")));

		JSONObject json = new JSONObject();
		json.put("menu", rDto);
		System.out.println("주문 제조중 상세 : " + rDto);
		return json;
	}
	// 업주 : 주문 현황 페이지, 고객주문,주문 내역에서 셀렉트 박스 골라도 여기로 옴
		@RequestMapping(value = "/selRequestStatus.do", method = RequestMethod.GET)
		public String requestListStatus(HttpSession session, Model model) {

			System.out.println("들어옴");
			OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
			String store_code = oDto.getStore_code();
			Date date = new Date();
			SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");
			String start = day.format(date);
			System.out.println(day.format(date));
			Map<String, String> map = new HashMap<>();
			int start111 = Integer.parseInt(start);
			start = Integer.toString(start111);
			System.out.println("==============시작일 : " + start);
			map.put("store_code", store_code);
			map.put("start", start);
			
			List<RequestDto> waitLists = request_IService.requestListWait(map);
			Map<String, Object> map1 = new HashMap<>();

			for (int i = 0; i < waitLists.size(); i++) { // 1:2,2:1,3:2,4:3,5:4
				String temp = waitLists.get(i).getRequest_menu();
				int tempLen = temp.length();
				int templenChange = temp.replaceAll(",", "").length();
				int arraySize = tempLen - templenChange;

				String[] menu = new String[arraySize];
				String[] cnt = new String[arraySize];
				StringTokenizer temp1 = new StringTokenizer(temp, ",");
				int num = 0;
				while (temp1.hasMoreTokens()) {
					String str1 = temp1.nextToken();
					int idx = str1.indexOf(":");
					menu[num] = str1.substring(0, idx);
					cnt[num] = str1.substring(idx + 1);
					num++;
				}
				String[] menu_name = new String[menu.length];
				map1.put("menu_seq_", menu);
				menu_name = request_IService.requestMenuName(map1);
				String request_menu = "";
				for (int j = 0; j < menu_name.length; j++) {
					request_menu += menu_name[j] + cnt[j] + ",";
				}
				waitLists.get(i).setMenu_name(request_menu.substring(0, request_menu.lastIndexOf(",")));
				System.out.println(waitLists.get(i));
				System.out.println(Arrays.toString(menu) + "메뉴");
				System.out.println(Arrays.toString(cnt) + "갯수");
				System.out.println("dfdlkflnclvncxlncklnvkcvnlnlvn");
			}

			List<RequestDto> makeLists = request_IService.requestListMake(map);

			Map<String, Object> map2 = new HashMap<>();
			for (int i = 0; i < makeLists.size(); i++) { // 1:4,2:3,3:1
				String temp = makeLists.get(i).getRequest_menu();
				int tempLen = temp.length();
				int templenChange = temp.replaceAll(",", "").length();
				int arraySize = tempLen - templenChange;

				String[] menu = new String[arraySize];
				String[] cnt = new String[arraySize];
				StringTokenizer temp1 = new StringTokenizer(temp, ",");
				int num = 0;
				while (temp1.hasMoreTokens()) {
					String str1 = temp1.nextToken();
					int idx = str1.indexOf(":");
					menu[num] = str1.substring(0, idx);
					cnt[num] = str1.substring(idx + 1);
					num++;
				}
				String[] menu_name = new String[menu.length];
				map2.put("menu_seq_", menu);
				menu_name = request_IService.requestMenuName(map2);
				String request_menu = "";
				for (int j = 0; j < menu_name.length; j++) {
					request_menu += menu_name[j] + cnt[j] + ",";
				}
				makeLists.get(i).setMenu_name(request_menu.substring(0, request_menu.lastIndexOf(",")));
				System.out.println(makeLists.get(i));
				System.out.println(Arrays.toString(menu) + "메뉴");
				System.out.println(Arrays.toString(cnt) + "갯수");
				System.out.println("ccccccccccccccccccccccccccccccccccc");

			}
			
			String menu_category = "주메뉴";
			
			if (session.getAttribute("owner_menu") == null) {
				System.out.println("loginDto 세션에 값 있을때");
				String owner_menu = oDto.getOwner_menu();
				String[] menu_seq = new String[owner_menu.split(",").length];
				for (int i = 0; i < owner_menu.split(",").length; i++) {
					menu_seq[i] = owner_menu.split(",")[i];
					System.out.print(owner_menu.split(",")[i]);
				}
				System.out.println("길이" + owner_menu.split(",").length);
				Map<String, Object> ownerMap = new HashMap<String, Object>();
				ownerMap.put("menu_seq_", menu_seq);
				ownerMap.put("menu_category", menu_category);
				List<MenuDto> lists = menu_IService.ownerMenuList(ownerMap);
				System.out.println("업주가 선택한 메뉴 : " + lists.toString());
				model.addAttribute("menuList", lists);
			} else {
				System.out.println("owner_menu 세션에 값 있을때");
				String owner_menu = (String) session.getAttribute("owner_menu");
				String[] menu_seq = new String[owner_menu.split(",").length];
				for (int i = 0; i < owner_menu.split(",").length; i++) {
					menu_seq[i] = owner_menu.split(",")[i];
					System.out.print(owner_menu.split(",")[i]);
				}
				System.out.println("길이" + owner_menu.split(",").length);
				Map<String, Object> ownerMap = new HashMap<String, Object>();
				ownerMap.put("menu_seq_", menu_seq);
				ownerMap.put("menu_category", menu_category);
				List<MenuDto> lists = menu_IService.ownerMenuList(ownerMap);
				System.out.println("업주가 선택한 메뉴 : " + lists.toString());
				model.addAttribute("menuList", lists);
			}

			model.addAttribute("waitLists", waitLists);
			model.addAttribute("makeLists", makeLists);
			return "/request/requestStatus";
		}
	

	// 업주 : 주문 현황 페이지 REST
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/selRequestStatusRest.do", method = { RequestMethod.GET, RequestMethod.POST })
//	@ResponseBody
//	public JSONObject requestListStatusRest(HttpSession session,String[] menu_seq,String[] menu_cnt,String[] menu_price) {
//		JSONObject json = new JSONObject();
//		
//		OwnerDto oDto = (OwnerDto)session.getAttribute("loginDto");
//		
//		RequestDto dto = new RequestDto();
//		
//		if(menu_seq != null) {
//			dto.setStore_code(oDto.getStore_code()); //세션에서 스토어코드 가져와서 셋팅
//			
//			String request_menu = "";
//			for (int i = 0; i < menu_seq.length; i++) {
//				request_menu += menu_seq[i]+":"+menu_cnt[i]+",";
//			}
//			dto.setRequest_menu(request_menu); // 1:3,2:4, 형식의 주문 메뉴 생성해서 셋팅해줌
//			
//			int totalPrice = 0;
//			for (int i = 0; i < menu_price.length; i++) {
//				totalPrice += Integer.parseInt(menu_price[i]); 
//			}
//			dto.setRequest_price(totalPrice); // 메뉴 가격 다합쳐서 셋팅해줌
//			
//			dto.setRequest_bank("우리은행");
//			dto.setRequest_account("1002-651-065588");
//			
//			boolean isc = request_IService.customOrder(dto);
//			System.out.println("주문 등록 성공 ? : " + isc);
//			
//			json.put("success", "주문이 완료되었습니다");
//		}else if(menu_seq == null) {
//			
//			System.out.println("들어옴");
//			String store_code = oDto.getStore_code();
//			Date date = new Date();
//			SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");
//			String start = day.format(date);
//			System.out.println(day.format(date));
//			Map<String, String> map = new HashMap<>();
//			int start111 = Integer.parseInt(start);
//			start = Integer.toString(start111);
//			System.out.println("==============시작일 : " + start);
//			map.put("store_code", store_code);
//			map.put("start", start);
//			int end = Integer.parseInt(start) + 1;
//			map.put("end", Integer.toString(end));
//			System.out.println("===========종료일 : " + end);
//			List<RequestDto> waitLists = request_IService.requestListWait(map);
//			Map<String, Object> map1 = new HashMap<>();
//			
//			for (int i = 0; i < waitLists.size(); i++) { // 1:2,2:1,3:2,4:3,5:4
//				String temp = waitLists.get(i).getRequest_menu();
//				int tempLen = temp.length();
//				int templenChange = temp.replaceAll(",", "").length();
//				int arraySize = tempLen - templenChange;
//				
//				String[] menu = new String[arraySize];
//				String[] cnt = new String[arraySize];
//				StringTokenizer temp1 = new StringTokenizer(temp, ",");
//				int num = 0;
//				while (temp1.hasMoreTokens()) {
//					String str1 = temp1.nextToken();
//					int idx = str1.indexOf(":");
//					menu[num] = str1.substring(0, idx);
//					cnt[num] = str1.substring(idx + 1);
//					num++;
//				}
//				String[] menu_name = new String[menu.length];
//				map1.put("menu_seq_", menu);
//				menu_name = request_IService.requestMenuName(map1);
//				String request_menu = "";
//				for (int j = 0; j < menu_name.length; j++) {
//					request_menu += menu_name[j] + cnt[j] + ",";
//				}
//				waitLists.get(i).setMenu_name(request_menu.substring(0, request_menu.lastIndexOf(",")));
//				System.out.println(waitLists.get(i));
//				System.out.println(Arrays.toString(menu) + "메뉴");
//				System.out.println(Arrays.toString(cnt) + "갯수");
//				System.out.println("dfdlkflnclvncxlncklnvkcvnlnlvn");
//			}
//			
//			List<RequestDto> makeLists = request_IService.requestListMake(map);
//			
//			Map<String, Object> map2 = new HashMap<>();
//			for (int i = 0; i < makeLists.size(); i++) { // 1:4,2:3,3:1
//				String temp = makeLists.get(i).getRequest_menu();
//				int tempLen = temp.length();
//				int templenChange = temp.replaceAll(",", "").length();
//				int arraySize = tempLen - templenChange;
//				
//				String[] menu = new String[arraySize];
//				String[] cnt = new String[arraySize];
//				StringTokenizer temp1 = new StringTokenizer(temp, ",");
//				int num = 0;
//				while (temp1.hasMoreTokens()) {
//					String str1 = temp1.nextToken();
//					int idx = str1.indexOf(":");
//					menu[num] = str1.substring(0, idx);
//					cnt[num] = str1.substring(idx + 1);
//					num++;
//				}
//				String[] menu_name = new String[menu.length];
//				map2.put("menu_seq_", menu);
//				menu_name = request_IService.requestMenuName(map2);
//				String request_menu = "";
//				for (int j = 0; j < menu_name.length; j++) {
//					request_menu += menu_name[j] + cnt[j] + ",";
//				}
//				makeLists.get(i).setMenu_name(request_menu.substring(0, request_menu.lastIndexOf(",")));
//				System.out.println(makeLists.get(i));
//				System.out.println(Arrays.toString(menu) + "메뉴");
//				System.out.println(Arrays.toString(cnt) + "갯수");
//				System.out.println("ccccccccccccccccccccccccccccccccccc");
//				
//			}
//			
//			json = restJson(waitLists, makeLists);
//		}
//		
//		
//		
//		return json;
//	}
//	//REST 
//	@SuppressWarnings("unchecked")
//	private JSONObject restJson(List<RequestDto> waitLists,List<RequestDto> makeLists) {
//		JSONObject json = new JSONObject();
//		JSONArray wLists = new JSONArray();
//		JSONArray mLists = new JSONArray();
//		JSONObject jList = null;
//		for (int i = 0; i < waitLists.size(); i++) {
//			jList = new JSONObject();
//			jList.put("rnum", waitLists.get(i).getRnum());
//			jList.put("menu_name", waitLists.get(i).getMenu_name());
//			jList.put("request_seq", waitLists.get(i).getRequest_seq());
//			jList.put("request_menu", waitLists.get(i).getRequest_menu());
//			jList.put("request_time", waitLists.get(i).getRequest_time());
//			jList.put("os_code", waitLists.get(i).getOs_code());
//
//			wLists.add(jList);
//		}
//		json.put("wait", wLists);
//		for (int i = 0; i < makeLists.size(); i++) {
//			jList = new JSONObject();
//			jList.put("rnum", makeLists.get(i).getRnum());
//			jList.put("menu_name", makeLists.get(i).getMenu_name());
//			jList.put("request_seq", makeLists.get(i).getRequest_seq());
//			jList.put("request_menu", makeLists.get(i).getRequest_menu());
//			jList.put("request_time", makeLists.get(i).getRequest_time());
//			jList.put("os_code", makeLists.get(i).getOs_code());
//
//			mLists.add(jList);
//		}
//		json.put("make", mLists);
//		System.out.println(json + "ㄱㄱㄱㄱㄱㄱ");
//		System.out.println(json.toString() + "ㄴㄴㄴㄴㄴㄴ");
//		return json;
//	}

	// 업주 : 주문 대기중 상세조회
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selWaitReqDetail.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject requestDetailWait(HttpSession session, String request_seq) {
		System.out.println("========" + request_seq);
		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		RequestDto dto = new RequestDto();
		dto.setStore_code(oDto.getStore_code());
		dto.setRequest_seq(Integer.parseInt(request_seq));
		RequestDto rDto = request_IService.requestDetailWait(dto);
		System.out.println("메뉴 이름 넣기전 : " + rDto);

		Map<String, Object> map = new HashMap<>();

		String temp = rDto.getRequest_menu();
		int tempLen = temp.length();
		int templenChange = temp.replaceAll(",", "").length();
		int arraySize = tempLen - templenChange;

		String[] menu = new String[arraySize];
		String[] cnt = new String[arraySize];
		StringTokenizer temp1 = new StringTokenizer(temp, ",");
		int num = 0;
		while (temp1.hasMoreTokens()) {
			String str1 = temp1.nextToken();
			int idx = str1.indexOf(":");
			menu[num] = str1.substring(0, idx);
			cnt[num] = str1.substring(idx + 1);
			num++;
		}
		String[] menu_name = new String[menu.length];
		map.put("menu_seq_", menu);
		menu_name = request_IService.requestMenuName(map);
		String request_menu = "";
		for (int j = 0; j < menu_name.length; j++) {
			request_menu += menu_name[j] + cnt[j] + ",";
		}
		rDto.setMenu_name(request_menu.substring(0, request_menu.lastIndexOf(",")));

		JSONObject json = new JSONObject();
		json.put("makeMenu", rDto);
		System.out.println("주문 제조중 상세 : " + rDto);
		return json;
	}

	// 업주 : 주문 제조중 상세조회
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selMakeReqDetail.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject requestDetailMake(HttpSession session, String request_seq) {
		System.out.println("========" + request_seq);
		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		RequestDto dto = new RequestDto();
		dto.setStore_code(oDto.getStore_code());
		dto.setRequest_seq(Integer.parseInt(request_seq));
		RequestDto rDto = request_IService.requestDetailMake(dto);

		Map<String, Object> map = new HashMap<String, Object>();
		String temp = rDto.getRequest_menu();
		int tempLen = temp.length();
		int templenChange = temp.replaceAll(",", "").length();
		int arraySize = tempLen - templenChange;

		String[] menu = new String[arraySize];
		String[] cnt = new String[arraySize];
		StringTokenizer temp1 = new StringTokenizer(temp, ",");
		int num = 0;
		while (temp1.hasMoreTokens()) {
			String str1 = temp1.nextToken();
			int idx = str1.indexOf(":");
			menu[num] = str1.substring(0, idx);
			cnt[num] = str1.substring(idx + 1);
			num++;
		}
		String[] menu_name = new String[menu.length];
		map.put("menu_seq_", menu);
		menu_name = request_IService.requestMenuName(map);
		String request_menu = "";
		for (int j = 0; j < menu_name.length; j++) {
			request_menu += menu_name[j] + cnt[j] + ",";
		}
		rDto.setMenu_name(request_menu.substring(0, request_menu.lastIndexOf(",")));

		JSONObject json = new JSONObject();
		json.put("makeMenu", rDto);
		System.out.println("주문 제조중 상세 : " + rDto);
		return json;
	}

	// 고객 주문 입력
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/regiCustomOrder.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject customOrder(HttpSession session,String nick,String[] menu_seq,String[] menu_cnt,String[] menu_price) {
		JSONObject json = new JSONObject();
		
		System.out.println("스토어 코드 : "+nick);
		
		RequestDto dto = new RequestDto();

		dto.setStore_code(nick);
		
		String request_menu = "";
		for (int i = 0; i < menu_seq.length; i++) {
			request_menu += menu_seq[i]+":"+menu_cnt[i]+",";
		}
		dto.setRequest_menu(request_menu); // 1:3,2:4, 형식의 주문 메뉴 생성해서 셋팅해줌
		
		int totalPrice = 0;
		System.out.println(Arrays.toString(menu_price));
		for (int i = 0; i < menu_price.length; i++) {
			totalPrice += Integer.parseInt(menu_price[i]); 
		}
		dto.setRequest_price(totalPrice); // 메뉴 가격 다합쳐서 셋팅해줌
		
		dto.setRequest_bank("우리은행");
		dto.setRequest_account("1002-651-065588");
		
		System.out.println("주문 메뉴 : "+request_menu);
		System.out.println("주무 가격 : "+totalPrice);
		System.out.println("집어 넣을 DTO : "+dto);
		
		RequestDto result  = request_IService.customOrder(dto);
		System.out.println("주문 등록 성공 ? : " + result);
		
		json.put("success", "주문이 완료되었습니다");
		json.put("rDto", result);
		return json;
	}
	
	//고객 주문 화면
	@RequestMapping(value = "/customAllMenuList.do", method = RequestMethod.GET)
	public String customMenuList(HttpSession session,Model model) {
		OwnerDto oDto = (OwnerDto)session.getAttribute("loginDto");
		String menu_category = "주메뉴";
		
		if (session.getAttribute("owner_menu") == null) {
			System.out.println("loginDto 세션에 값 있을때");
			String owner_menu = oDto.getOwner_menu();
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
				System.out.print(owner_menu.split(",")[i]);
			}
			System.out.println("길이" + owner_menu.split(",").length);
			Map<String, Object> ownerMap = new HashMap<String, Object>();
			ownerMap.put("menu_seq_", menu_seq);
			ownerMap.put("menu_category", menu_category);
			List<MenuDto> lists = menu_IService.ownerMenuList(ownerMap);
			System.out.println("업주가 선택한 메뉴 : " + lists.toString());
			model.addAttribute("menuList", lists);
		} else {
			System.out.println("owner_menu 세션에 값 있을때");
			String owner_menu = (String) session.getAttribute("owner_menu");
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
				System.out.print(owner_menu.split(",")[i]);
			}
			System.out.println("길이" + owner_menu.split(",").length);
			Map<String, Object> ownerMap = new HashMap<String, Object>();
			ownerMap.put("menu_seq_", menu_seq);
			ownerMap.put("menu_category", menu_category);
			List<MenuDto> lists = menu_IService.ownerMenuList(ownerMap);
			System.out.println("업주가 선택한 메뉴 : " + lists.toString());
			model.addAttribute("menuList", lists);
		}
		return "customRequest";
	}

	// 고객 : 카테고리 버튼 눌럿을때
	@RequestMapping(value = "/CselAllMenuList.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject CallMenu(HttpSession session,String menu_category) {
		OwnerDto oDto = (OwnerDto)session.getAttribute("loginDto");
		JSONObject json = null;
		if (session.getAttribute("owner_menu") == null) {
			System.out.println("loginDto 세션에 값 있을때");
			String owner_menu = oDto.getOwner_menu();
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
				System.out.print(owner_menu.split(",")[i]);
			}
			System.out.println("길이" + owner_menu.split(",").length);
			Map<String, Object> ownerMap = new HashMap<String, Object>();
			ownerMap.put("menu_seq_", menu_seq);
			ownerMap.put("menu_category", menu_category);
			List<MenuDto> lists = menu_IService.ownerMenuList(ownerMap);
			System.out.println("업주가 선택한 메뉴 : " + lists.toString());
			json = objectJson(lists);
		} else {
			System.out.println("owner_menu 세션에 값 있을때");
			String owner_menu = (String) session.getAttribute("owner_menu");
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
				System.out.print(owner_menu.split(",")[i]);
			}
			System.out.println("길이" + owner_menu.split(",").length);
			Map<String, Object> ownerMap = new HashMap<String, Object>();
			ownerMap.put("menu_seq_", menu_seq);
			ownerMap.put("menu_category", menu_category);
			List<MenuDto> lists = menu_IService.ownerMenuList(ownerMap);
			System.out.println("업주가 선택한 메뉴 : " + lists.toString());
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
			jList.put("file_seq", lists.get(i).getFileDto().getFile_seq());
			jList.put("file_tname", lists.get(i).getFileDto().getFile_tname());
			jList.put("file_rname", lists.get(i).getFileDto().getFile_rname());
			jList.put("file_rurl", lists.get(i).getFileDto().getFile_rurl());
			jList.put("file_aurl", lists.get(i).getFileDto().getFile_aurl());
			jList.put("menu_seq", lists.get(i).getMenu_seq());
			jList.put("menu_name", lists.get(i).getMenu_name());
			jList.put("menu_price", lists.get(i).getMenu_price());
			jList.put("menu_category", lists.get(i).getMenu_category());

			jLists.add(jList);
		}
		json.put("choiceMenu", jLists);
		System.out.println(json + "가가가");
		System.out.println(json.toString() + "나나나");
		return json;
	}
}
