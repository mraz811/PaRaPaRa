package com.happy.para.ctrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.happy.para.dto.TimeDto;
import com.happy.para.model.Timesheet_IService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class TimesheetCtrl {

	@Autowired
	private Timesheet_IService timeSer;
	
	@RequestMapping(value="/selTS.do", method=RequestMethod.GET)
	public String timeSheet() {
		
		System.out.println("나 여깄엉");
		
		return "timesheet/timeSheetList";
	}
	
	
	@RequestMapping(value="/selTimeSheet.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String timeSheet(TimeDto dto, int alba_seq, String ts_date, Model model) {
		
		System.out.println("들어왔닝?");
		
		dto.setAlba_seq(alba_seq);
		dto.setTs_date(ts_date);
		
		System.out.println(alba_seq);
		System.out.println(ts_date);
		
		List<TimeDto> lists = timeSer.tsList(dto);
		
		String ts_datetime = lists.get(0).getTs_datetime();
		System.out.println("이게 모야!!!!!"+ts_datetime);
		
		// ajax 데이터 반환
		Map<String,String> mapl = new HashMap<String,String>();
		mapl.put("alba_seq", Integer.toString(alba_seq));
		// 알바 seq 로 알바 이름 검색 해서 넣어주기 
		mapl.put("alba_name", "이슬");		
		mapl.put("ts_date", ts_date);
		mapl.put("ts_datetime", ts_datetime);
		
		JSONObject obj = JSONObject.fromObject(mapl);
		//{"alba_name":"이슬","ts_datetime":"05:30-08:30","ts_date":"2019-05-21","alba_seq":"55"}
		System.out.println(obj.toString());
		
		List<String> timeList = timeSer.tsDatetimeList(dto);
// 		[05:30-08:30, 04:00-07:30, 03:30-11:30, 04:30-07:30, 22:30-00:30]
		System.out.println(timeList);
		
		// json object > json array 로 변경
		
		Map<String,String> timeObj = new HashMap<String,String>();
		timeObj.put("1", lists.get(0).getTs_datetime());
		timeObj.put("2", lists.get(1).getTs_datetime());

		JSONArray timeAr = new JSONArray();
		timeAr.add(timeObj);

		// ??
		Gson gson = new Gson();
		String rr = gson.toJson(timeAr);
		System.out.println("이게 rr 이얌"+rr);
		
		JSONObject objW = new JSONObject();
		objW.put("이슬", timeAr); // 키에 알바 이름

		JSONObject objWW = new JSONObject();
		objWW.put("999", objW); // 키에 알바 seq
		
		//{"999":{"이슬":[{"1":"03:30-07:30","2":"01:30-02:30"}]}}
		System.out.println("히ㅡ히ㅡ히희흐히"+objWW.toString());
		
		model.addAttribute("objWW", objWW);
		
		return objWW.toString();
	}
	
	@RequestMapping(value="/regiTimeSheet.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String timeAdd(TimeDto dto, String sTime, String eTime, String ts_date) {
	
		// 시간 차이 계산
		int sTimeF = Integer.parseInt(sTime.split(":")[0]); // 03
		int sTimeE = Integer.parseInt(sTime.split(":")[1]); //	00
		int eTimeF = Integer.parseInt(eTime.split(":")[0]); //	04
		int eTimeE = Integer.parseInt(eTime.split(":")[1]); //	30
		int timeM = eTimeE-sTimeE;
		
		// 22:30-00:30 같은 경우 연산이 달라지는 것을 확인 해 주어야 함
		
//		#{alba_seq}, #{ts_date}, #{ts_datetime}, #{ts_workhour}
		dto.setTs_datetime(sTime+"-"+eTime); // 03:00-04:30
		dto.setTs_date(dto.getTs_date());
		dto.setAlba_seq(55); // 이름을 가져와서 seq 를 찾음
		if(timeM == 30) {
			timeM = 5;
			dto.setTs_workhour(String.valueOf(eTimeF-sTimeF)+"."+String.valueOf(timeM));
		}else {
			dto.setTs_workhour(String.valueOf(eTimeF-sTimeF)+"."+String.valueOf(timeM));
		}

		System.out.println("sTime!!!!!!!!"+sTime);
		
		timeSer.tsRegister(dto);
		
		return "timesheet/timeSheetList";
		
	}
	

}



