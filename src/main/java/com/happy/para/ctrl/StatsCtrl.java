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
import com.happy.para.dto.GoogleChartDTO;
import com.happy.para.dto.OwnerDto;
import com.happy.para.dto.StoreDto;
import com.happy.para.model.Stats_IService;
import com.happy.para.model.Store_IService;

@Controller
public class StatsCtrl {

	// 통계
	@Autowired
	private Stats_IService stats_IService;

	@Autowired
	private Store_IService store_IService;

	// 통계 페이지로 이동
	@RequestMapping(value = "/ownerStats.do", method = RequestMethod.GET)
	public String ddd() {
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

		Map<String, String> mapp = new HashMap<String, String>();
		mapp.put("jstr", jstr);
		mapp.put("jstr2", jstr2);
		mapp.put("store_name", store_name);
		return mapp;
	}

	// 없앨꺼임
	@RequestMapping(value = "/ownerStatsOut.do", method = RequestMethod.GET)
	public void ownerStatsOutcome(HttpSession session, String start, String end) {
		Map<String, String> map = new HashMap<String, String>();
		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		map.put("store_code", store_code);
		map.put("start", start);
		map.put("end", end);
		int n = stats_IService.ownerStatsOutcome(map);
		System.out.println("업주 지출 통계에 쓸 값 : " + n);
	}

	// 이거 위에위에 꺼라 합쳐야 할듯, 메뉴 통계 값 나오는 컨트롤러
	@RequestMapping(value = "/ownerStatsMenu.do", method = RequestMethod.GET)
	public void ownerStatsMenu(HttpSession session, String start, String end) {
		Map<String, String> map = new HashMap<String, String>();
		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		map.put("store_code", store_code);
		map.put("start", start);
		map.put("end", end);

	}

	@RequestMapping(value = "/adminStatsIn.do", method = RequestMethod.GET)
	public void adminStatsIncome(String[] store_code, String start, String end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("store_code_", store_code);
		map.put("start", start);
		map.put("end", end);
		int n = stats_IService.adminStatsIncome(map);
		System.out.println("담당자 수익 통계에 쓸 값 : " + n);
	}

	@RequestMapping(value = "/adminStatsMenu.do", method = RequestMethod.GET)
	public void adminStatsMenu(String[] store_code, String start, String end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("store_code_", store_code);
		map.put("start", start);
		map.put("end", end);
		Map<String, String> resultMap = stats_IService.adminStatsMenu(map);
		System.out.println("담당자 메뉴 통계에 쓸 값 : " + resultMap);
	}

}
