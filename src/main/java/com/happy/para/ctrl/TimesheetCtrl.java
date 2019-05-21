package com.happy.para.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happy.para.dto.TimeDto;
import com.happy.para.model.Timesheet_IService;

@Controller
public class TimesheetCtrl {

	@Autowired
	private Timesheet_IService timeSer;
	
	/*
	@RequestMapping(value="/timeSheet.do", method=RequestMethod.GET)
	public String timeSheet() {
		
		System.out.println("들어왔닝?");
		
		return "timeSheet";
	}	
	
	@RequestMapping(value="/timeAdd.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String timeAdd(TimeDto dto, String sTime, String eTime, String ts_date) {
	
		// 시간 차이 계산
		int sTimeF = Integer.parseInt(sTime.split(":")[0]); // 03
		int sTimeE = Integer.parseInt(sTime.split(":")[1]); //	00
		int eTimeF = Integer.parseInt(eTime.split(":")[0]); //	04
		int eTimeE = Integer.parseInt(eTime.split(":")[1]); //	30
		int timeM = eTimeE-sTimeE;
		
//		#{alba_seq}, #{ts_date}, #{ts_datetime}, #{ts_workhour}
		dto.setTs_datetime(sTime+"-"+eTime); // 03:00-04:30
		dto.setTs_date(ts_date);
		dto.setAlba_seq(55); // 이름을 가져와서 seq 를 찾음
		if(timeM == 30) {
			timeM = 5;
			dto.setTs_workhour(String.valueOf(eTimeF-sTimeF)+"."+String.valueOf(timeM));
		}else {
			dto.setTs_workhour(String.valueOf(eTimeF-sTimeF)+"."+String.valueOf(timeM));
		}
		
		System.out.println("sTime!!!!!!!!"+sTime);
		System.out.println("ts_date!!!!!!!!"+ts_date);
		
		timeSer.tsRegister(dto);
		
		return "timeSheet";
	}
	
	*/
	
}
