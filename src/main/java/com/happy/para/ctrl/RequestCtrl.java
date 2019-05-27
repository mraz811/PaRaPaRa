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
		Date date = new Date();
		SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");
		String tempEnd = day.format(date);
		int a = Integer.parseInt(tempEnd) + 1;
		String end = Integer.toString(a);
		System.out.println("@@@@종료일@@@@" + end);
		map.put("store_code", store_code);
		map.put("start", start);
		map.put("end", end);
		map.put("dayStart", start);
		map.put("dayEnd", end);
		map.put("pageStart", rowDto.getStart() + "");
		map.put("pageEnd", rowDto.getEnd() + "");
		map.put("os_code", os_code);
		rowDto.setTotal(request_IService.selectTotalRequest(map));
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
		Date date = new Date();
		SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");
		String tempEnd = day.format(date);
		int a = Integer.parseInt(tempEnd) + 1;
		String end = Integer.toString(a);
		System.out.println("@@@@종료일@@@@" + end);

		String os_code = (String) session.getAttribute("os_code");
		map.put("store_code", store_code);
		map.put("dayStart", start);
		map.put("dayEnd", end);
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

	// 업주 : 주문 현황 페이지, 주문 내역에서 셀렉트 박스 골라도 여기로 옴
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
		int end = Integer.parseInt(start) + 1;
		map.put("end", Integer.toString(end));
		System.out.println("===========종료일 : " + end);
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

		model.addAttribute("waitLists", waitLists);
		model.addAttribute("makeLists", makeLists);
		return "/request/requestStatus";
	}

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

	@RequestMapping(value = "/regiCustomOrder.do", method = RequestMethod.GET)
	public void customOrder(RequestDto dto) {
		boolean isc = request_IService.customOrder(dto);
		System.out.println("주문 등록 성공 ? : " + isc);
	}
	
	//고객 주문 화면
	@RequestMapping(value = "/customAllMenuList.do", method = RequestMethod.GET)
	public String customMenuList(Model model) {
		String menu_category = "주메뉴";
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		System.out.println("전체 메뉴 조회 : " + lists);
		model.addAttribute("menuList", lists);
		return "customRequest";
	}

	// 고객 : 카테고리 버튼 눌럿을때
	@RequestMapping(value = "/CselAllMenuList.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject CallMenu(String menu_category) {
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		System.out.println("전체 메뉴 조회 : " + lists);
		JSONObject json = null;
		json = objectJson(lists);
		return json;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject objectJson(List<MenuDto> lists) {
		JSONObject json = new JSONObject();
		JSONArray jLists = new JSONArray();
		JSONObject jList = null;
		for (int i = 0; i < lists.size(); i++) {
			jList = new JSONObject();
//				jList.put("file_seq", lists.get(i).getFileDto().getFile_seq());
//				jList.put("file_tname", lists.get(i).getFileDto().getFile_tname());
//				jList.put("file_rname", lists.get(i).getFileDto().getFile_rname());
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
	
	//주문 수량 및 종류 추가 할때
	@RequestMapping(value="/addMenu.do", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> addMenu(String menu_seq) {
		System.out.println("넘겨받은 menu_seq : "+menu_seq);
		Map<String, Object> map = new HashMap<String,Object>();
		MenuDto dto = menu_IService.detailMenu(menu_seq);
		System.out.println("넘겨줄 menu_name : "+dto.getMenu_name());
		map.put("menu_name", dto.getMenu_name());
		return map;
	}
}
