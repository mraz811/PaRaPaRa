package com.happy.para.ctrl;

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

import com.google.gson.Gson;
import com.happy.para.dto.AdminDto;
import com.happy.para.dto.GoogleChartDTO;
import com.happy.para.dto.OwnerDto;
import com.happy.para.dto.RequestDto;
import com.happy.para.dto.StoreDto;
import com.happy.para.model.Member_IService;
import com.happy.para.model.Stats_IService;
import com.happy.para.model.Store_IService;

@Controller
public class StatsCtrl {

	// 통계
	@Autowired
	private Stats_IService stats_IService;

	@Autowired
	private Store_IService store_IService;

	@Autowired
	private Member_IService member_IService;

	@RequestMapping(value = "/ownerStats.do", method = RequestMethod.GET)
	public String ownerStats() {
		return "/stats/statsOwner";
	}

	// 아작스로 통계에 필요한 값 생성해서 보내는 거
	@RequestMapping(value = "/ownerStatsIn.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> ownerStatsIncome(Model model, HttpSession session, String start, String end) {
		String start1 = start.substring(0, 4);
		String start2 = start.substring(5, 7);
		String start3 = start.substring(8, 10);
		start = start1 + start2 + start3;

		Map<String, String> map = new HashMap<String, String>();
		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		map.put("store_code", store_code);
		map.put("start", start);
		map.put("end", end);
		int incomeMoney = stats_IService.ownerStatsIncome(map);
		System.out.println("@@@@@@@@@@@@@@@" + incomeMoney);

		Map<String, String> mapp = new HashMap<String, String>();

		if (incomeMoney == 0) {
			mapp.put("stats", "no");
		} else {
			int outcomeMoney = stats_IService.ownerStatsOutcome(map);
			Map<String, List<String>> resultMap = stats_IService.ownerStatsMenu(map);

			GoogleChartDTO jdata = new GoogleChartDTO();
			jdata.addColumn("stats", "통계", "", "string");
			jdata.addColumn("money", "단위(원)", "", "number");
			jdata.addRow("수익", incomeMoney);
			jdata.addRow("지출", outcomeMoney);
			System.out.println("업주 수익/지출 통계에 쓸 값 : " + incomeMoney + ":");
			Gson gs = new Gson();
			String jstr = gs.toJson(jdata);

			GoogleChartDTO jdata2 = new GoogleChartDTO();
			jdata2.addColumn("stats", "통계", "", "string");
			jdata2.addColumn("count", "단위(개)", "", "number");
			for (int i = 0; i < resultMap.get("menu").size(); i++) {
				jdata2.addRow(resultMap.get("menu").get(i), Integer.parseInt(resultMap.get("cnt").get(i)));
			}
			System.out.println("업주 메뉴 통계에 쓸 값 : " + resultMap);
			String jstr2 = gs.toJson(jdata2);

			StoreDto sDto = store_IService.storeDetail(store_code);
			String store_name = sDto.getStore_name();

			mapp.put("jstr", jstr);
			mapp.put("jstr2", jstr2);
			mapp.put("store_name", store_name);
		}

		return mapp;
	}

	// 담당자 수익 통계 화면으로
	@RequestMapping(value = "/adminStats.do", method = RequestMethod.GET)
	public String adminStats(HttpSession session, Model model) {
		AdminDto aDto = (AdminDto) session.getAttribute("loginDto");
		System.out.println(aDto);
		List<OwnerDto> lists = member_IService.ownerListAll(aDto.getLoc_code());
		model.addAttribute("ownerList", lists);
		return "/stats/statsAdmin";
	}

	// 관리자 수익 통계 화면으로
	@RequestMapping(value = "/superStats.do", method = RequestMethod.GET)
	public String superStats(HttpSession session, Model model) {
		AdminDto aDto = (AdminDto) session.getAttribute("loginDto");
		System.out.println(aDto);
		List<AdminDto> lists = member_IService.adminListAll();
		model.addAttribute("adminList", lists);
		return "/stats/statsSuper";
	}

	// 담당자 수익 통계
	@RequestMapping(value = "/adminStatsIncome.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> adminStatsIncome(Model model, String[] store_code, String start, String end) {
		Map<String, String> mapp = new HashMap<String, String>();
		String start1 = start.substring(0, 4);
		String start2 = start.substring(5, 7);
		String start3 = start.substring(8, 10);
		start = start1 + start2 + start3;

		Map<String, Object> map = new HashMap<String, Object>();

		System.out.println(start);
		System.out.println(end);

		GoogleChartDTO jdata = new GoogleChartDTO();
		jdata.addColumn("stats", "통계", "", "string");
		jdata.addColumn("money", "단위(원)", "", "number");
		for (int i = 0; i < store_code.length; i++) {
			map.put("store_code", store_code[i]);
			map.put("start", start);
			map.put("end", end);
			RequestDto rDto = stats_IService.adminStatsIncome(map);
			if (rDto != null) {
				System.out.println("이거뭐냐응??" + rDto);
				jdata.addRow(rDto.getStoreDto().getStore_name(), rDto.getRequest_price());
			} else {

			}
		}
		Gson gs = new Gson();
		String jstr = gs.toJson(jdata);

		map.put("store_code_", store_code);
		map.put("start", start);
		map.put("end", end);

		GoogleChartDTO jdata2 = new GoogleChartDTO();
		Map<String, List<String>> resultMap = stats_IService.adminStatsMenu(map);
		jdata2.addColumn("stats", "통계", "", "string");
		jdata2.addColumn("count", "단위(개)", "", "number");
		for (int i = 0; i < resultMap.get("menu").size(); i++) {
			jdata2.addRow(resultMap.get("menu").get(i), Integer.parseInt(resultMap.get("cnt").get(i)));
		}
		System.out.println("업주 메뉴 통계에 쓸 값 : " + resultMap);
		String jstr2 = gs.toJson(jdata2);

		mapp.put("jstr", jstr);
		mapp.put("jstr2", jstr2);
		return mapp;
	}

	// 관리자 수익/메뉴 통계
	@RequestMapping(value = "/superStatsIncome.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> superStatsIncome(HttpSession session, String loc_code, Model model, String start,
			String end) {
		Map<String, String> mapp = new HashMap<String, String>();
		String start1 = start.substring(0, 4);
		String start2 = start.substring(5, 7);
		String start3 = start.substring(8, 10);
		start = start1 + start2 + start3;

		Map<String, Object> map = new HashMap<String, Object>();

		System.out.println(start);
		System.out.println(end);

		List<OwnerDto> lists = member_IService.ownerListAll(loc_code);
		String[] store_code = new String[lists.size()];

		for (int j = 0; j < lists.size(); j++) {
			store_code[j] = lists.get(j).getStore_code();
		}

		for (int i = 0; i < store_code.length; i++) {
			System.out.println(store_code[i]);
		}

		GoogleChartDTO jdata = new GoogleChartDTO();
		jdata.addColumn("stats", "통계", "", "string");
		jdata.addColumn("money", "단위(원)", "", "number");
		for (int i = 0; i < store_code.length; i++) {
			map.put("store_code", store_code[i]);
			map.put("start", start);
			map.put("end", end);
			RequestDto rDto = stats_IService.adminStatsIncome(map);
			if (rDto != null) {
				System.out.println("이거뭐냐응??" + rDto);
				jdata.addRow(rDto.getStoreDto().getStore_name(), rDto.getRequest_price());
			} else {

			}

		}
		Gson gs = new Gson();
		String jstr = gs.toJson(jdata);

		map.put("store_code_", store_code);
		map.put("start", start);
		map.put("end", end);

		GoogleChartDTO jdata2 = new GoogleChartDTO();
		Map<String, List<String>> resultMap = stats_IService.adminStatsMenu(map);
		jdata2.addColumn("stats", "통계", "", "string");
		jdata2.addColumn("count", "단위(개)", "", "number");
		for (int i = 0; i < resultMap.get("menu").size(); i++) {
			jdata2.addRow(resultMap.get("menu").get(i), Integer.parseInt(resultMap.get("cnt").get(i)));
		}
		System.out.println("업주 메뉴 통계에 쓸 값 : " + resultMap);
		String jstr2 = gs.toJson(jdata2);

		mapp.put("jstr", jstr);
		mapp.put("jstr2", jstr2);
		return mapp;
	}

}
