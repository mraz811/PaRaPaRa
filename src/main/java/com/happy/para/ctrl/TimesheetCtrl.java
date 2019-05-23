package com.happy.para.ctrl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happy.para.dto.AlbaDto;
import com.happy.para.dto.OwnerDto;
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
	
	@RequestMapping(value="/selTimeSheet.do", method=RequestMethod.GET)
	public String timeSheet(TimeDto dto, Model model, HttpSession session) {
		
		System.out.println("들어왔닝?");
		
//		int alba_seq = 55; // alba_seq 들어온 매장의 알바들 조회해서 담음
		
		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		System.out.println("로그인 업주의 store_code : "+store_code);
		
		List<AlbaDto> albaLists = timeSer.tsAlba(oDto.getStore_code());
		System.out.println("로그인 업주의 albaLists : "+albaLists);
		
		Date getDate = new Date();
        String today = getDate.toString();
        System.out.println("현재날짜 : "+ today);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("현재날짜 : "+ sdf.format(getDate));
        model.addAttribute("today", sdf.format(getDate));
		System.out.println("today : "+sdf.format(getDate));
		
		JSONObject timeObj = null;
		JSONArray timeAr = new JSONArray();
		JSONArray timeArAr = new JSONArray();
		
		for (int i = 0; i < albaLists.size(); i++) {
//			albaLists.get(i).getAlba_seq();
//			albaLists.get(i).getAlba_name();
			
			dto.setAlba_seq(albaLists.get(i).getAlba_seq());
			dto.setTs_date(sdf.format(getDate));

			List<TimeDto> lists = timeSer.tsList(dto);
			System.out.println("lists.size() ? "+lists.size());

//			{ "44":{"태2":[{"1":"00:00-00:00"}]} }
			
			if(lists.size() == 0) {
			
				timeObj = new JSONObject();
				timeObj.put("1", "00:00-00:00");
				
				timeAr.add(timeObj);
				
				JSONObject objW = new JSONObject();
				objW.put(albaLists.get(i).getAlba_name(), timeAr); // 키에 알바 이름
				
				JSONObject objWW = new JSONObject();
				objWW.put(albaLists.get(i).getAlba_seq(), objW); // 키에 알바 seq

				System.out.println("lists.size가 0 일때 : "+objWW.toString());			
				
				timeArAr.add(objWW);
			
			}else {
				
				for (TimeDto tDto : lists) {
					timeObj = new JSONObject();
					timeObj.put("1", tDto.getTs_datetime());
					
					timeAr.add(timeObj);
				}
				
				JSONObject objW = new JSONObject();
				objW.put(albaLists.get(i).getAlba_name(), timeAr); // 키에 알바 이름
				
				JSONObject objWW = new JSONObject();
				objWW.put(albaLists.get(i).getAlba_seq(), objW); // 키에 알바 seq

				System.out.println("히ㅡ히ㅡ히희흐히"+objWW.toString());			
				
				timeArAr.add(objWW);

			}
			
			model.addAttribute("timeArAr", timeArAr);
			System.out.println("timeArAr : "+timeArAr);
			model.addAttribute("lists", lists);
		}

//		return objWW.toString();
		return "timesheet/timeSheetList";
	}
	
	@RequestMapping(value="/regiTimeSheet.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String timeAdd(TimeDto dto, String name, String sTime, String eTime, String ts_date) {
	
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
		
		System.out.println("dto.getTs_date() ?"+dto.getTs_date());
		System.out.println("ts_date ?"+ts_date);
		
		dto.setAlba_seq(11); // 이름을 가져와서 seq 를 찾음
		
		if(timeM == 30) {
			timeM = 5;
			dto.setTs_workhour(String.valueOf(eTimeF-sTimeF)+"."+String.valueOf(timeM));
		}else {
			dto.setTs_workhour(String.valueOf(eTimeF-sTimeF)+"."+String.valueOf(timeM));
		}

		System.out.println("regiTimeSh_dto.getTs_date()"+dto.getTs_date());
		System.out.println("sTime!!!!!!!!"+sTime);
		
		boolean isc = timeSer.tsRegister(dto);
		
		JSONObject obj = new JSONObject();
		obj.put("isc", isc);
		
		return obj.toString();
		
	}
	
	@RequestMapping(value="/tsDelete.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public void timeDel(TimeDto dto, String ts_datetime, int alba_seq, String ts_date) {
		
		dto.setTs_datetime(ts_datetime);
		dto.setAlba_seq(alba_seq);
		dto.setTs_date(ts_date);
		
		boolean isc = timeSer.tsDelete(dto);
		
		System.out.println("성공이야?"+isc);
		
	}
	

}



