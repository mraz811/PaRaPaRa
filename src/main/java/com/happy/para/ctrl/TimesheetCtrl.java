package com.happy.para.ctrl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.happy.para.dto.AlbaDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.dto.TimeDto;
import com.happy.para.model.Alba_IService;
import com.happy.para.model.Timesheet_IService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class TimesheetCtrl {

	@Autowired
	private Timesheet_IService timeSer;
	
	@Autowired
	private Alba_IService albaSer;

	@RequestMapping(value="/selTS.do", method=RequestMethod.GET)
	public String timeSheet() {

		System.out.println("selTS");

		return "timesheet/timeSheetList";
	}

	@RequestMapping(value="/selTimeSheet.do", method=RequestMethod.GET)
	public String timeSheet(TimeDto dto, Model model, HttpSession session, String ts_date) {
		
		System.out.println("TimeSheet");

		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		System.out.println("로그인 업주의 store_code : "+store_code);

		List<AlbaDto> albaLists = timeSer.tsAlba(store_code);
		System.out.println("로그인 업주의 albaLists : "+albaLists);

		Date getDate = new Date();
		String today = getDate.toString();
		System.out.println("현재날짜 : "+ today);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("현재날짜 : "+ sdf.format(getDate));
		System.out.println("today : "+sdf.format(getDate));

		JSONObject timeObj = null;
		String timeArr = "";
		
		for (int i = 0; i < albaLists.size(); i++) {

			JSONArray timeAr = new JSONArray();

			dto.setAlba_seq(albaLists.get(i).getAlba_seq());
			
			if(ts_date == null) {
				dto.setTs_date(sdf.format(getDate));				
				model.addAttribute("today", sdf.format(getDate));
			}else {
				dto.setTs_date(ts_date);								
				model.addAttribute("today", ts_date);
			}

			List<TimeDto> lists = timeSer.tsList(dto);
			System.out.println("lists.size() ? "+lists.size());
			System.out.println("lists ? "+lists);

			if(lists.size() == 0) {

				timeObj = new JSONObject();
				timeObj.put("6", "00:00-00:00");

				timeAr.add(timeObj);

				JSONObject objW = new JSONObject();
				objW.put(albaLists.get(i).getAlba_name(), timeAr); // 키에 알바 이름

				JSONObject objWW = new JSONObject();
				objWW.put(albaLists.get(i).getAlba_seq(), objW); // 키에 알바 seq

				System.out.println("해당 알바 근무 시간이 0 일때 : "+objWW.toString());			

				String objWWSub1 = objWW.toString().substring(1);
				String objWWSub2 = objWWSub1.substring (0, objWWSub1.length()-1);

				timeArr += objWWSub2+",";

			}else {
				for (TimeDto tDto : lists) {
					timeObj = new JSONObject();
					timeObj.put("6", tDto.getTs_datetime());
					timeAr.add(timeObj);
				}

				JSONObject objW = new JSONObject();
				objW.put(albaLists.get(i).getAlba_name(), timeAr); // 키에 알바 이름

				JSONObject objWW = new JSONObject();
				objWW.put(albaLists.get(i).getAlba_seq(), objW); // 키에 알바 seq

				System.out.println("해당 알바 근무 시간이 0이 아닐때 일때 : "+objWW.toString());			

				String objWWSub1 = objWW.toString().substring(1);
				String objWWSub2 = objWWSub1.substring (0, objWWSub1.length()-1);

				timeArr += objWWSub2+",";

			}

			model.addAttribute("lists", lists);
		}

		timeArr = "{"+timeArr+"}";

		model.addAttribute("timeArr", timeArr);
		System.out.println("timeArr : "+timeArr);

		System.out.println("로그인 업주의 albaLists : "+albaLists);
		
		return "timesheet/timeSheetList";
	}

	@RequestMapping(value="/regiTimeSheet.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String timeAdd(TimeDto dto, int index, String name, String sTime, String eTime, String ts_date) throws ParseException {

		DateFormat stringFormat = new SimpleDateFormat("hh:mm");

		Date sTimeStrF = stringFormat.parse(sTime);
		long workT = 0;

		int eTime00 = Integer.parseInt(eTime.split(":")[0]);
		int eTime00mm = Integer.parseInt(eTime.split(":")[1]);
		int sTime00 = Integer.parseInt(sTime.split(":")[0]);
		int sTime00mm = Integer.parseInt(sTime.split(":")[1]);

		if(eTime00 == 00) {
			eTime00 = 24;
			Date eTimeStrF00 = stringFormat.parse(eTime00+":"+eTime00mm);
			workT = eTimeStrF00.getTime() - sTimeStrF.getTime();
		}
		
		// 00~01 시 없앨거면 삭제해도 됨.
		else if (eTime00 == 01){
			eTime00 = 25;
			Date eTimeStrF00 = stringFormat.parse(eTime00+":"+eTime00mm);
			workT = eTimeStrF00.getTime() - sTimeStrF.getTime();			
		}
		else if (sTime00 == 00 && eTime00 == 00) {
			sTime00 = 24;
			eTime00 = 24;
			Date sTimeStrF00 = stringFormat.parse(sTime00+":"+sTime00mm);
			Date eTimeStrF00 = stringFormat.parse(eTime00+":"+eTime00mm);
			workT = eTimeStrF00.getTime() - sTimeStrF00.getTime();						
		// 여기 까지
			
		}else {
			Date eTimeStrF = stringFormat.parse(eTime);
			workT = eTimeStrF.getTime() - sTimeStrF.getTime();
		}

		System.out.println(sTimeStrF.getTime());

		long min = (((workT/1000*60)/60)/60*60)/60;

		String minToString = min+"";
		Double minToInt = Double.valueOf(minToString);
		Double hour = minToInt/60;
		
		System.out.println("hour_double:"+hour);

		dto.setTs_workhour(hour);

		//		#{alba_seq}, #{ts_date}, #{ts_datetime}, #{ts_workhour}
		dto.setTs_datetime(sTime+"-"+eTime); // 03:00-04:30
		dto.setTs_date(dto.getTs_date());

		System.out.println("dto.getTs_date() ?"+dto.getTs_date());
		System.out.println("ts_date ?"+ts_date);

		System.out.println("선택한 alba_seq"+index);

		dto.setAlba_seq(index); // 이름을 가져와서 seq 를 찾음

		System.out.println("regiTimeSh_dto.getTs_date()"+dto.getTs_date());

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

	@RequestMapping(value="/salary.do", method=RequestMethod.GET)
	public String salary(HttpSession session, TimeDto dto, Model model) {
		
		System.out.println("salary");

		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		System.out.println("로그인 업주의 store_code : "+store_code);

		List<AlbaDto> albaLists = timeSer.tsAlba(store_code);
		System.out.println("로그인 업주의 albaLists : "+albaLists);
		model.addAttribute("albaLists", albaLists);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("ts_date", "2019-05"); // 화면에서 값 받아와서 넣을거야!!!
		map.put("store_code", store_code);
		
		// 월별 매장별 timeList 조회
		List<TimeDto> timeList = timeSer.tsListAll(map);
		
		System.out.println("timeList.size() : "+timeList.size()); //7
		
		for (int i = 0; i < timeList.size(); i++) {

			dto.setAlba_seq(timeList.get(i).getAlba_seq());
			System.out.println("timeList.get(i).getAlba_seq() : " + timeList.get(i).getAlba_seq());
			
			// timesheet 의 TS_DATE 중 화면의 월과 같은 애들만
			dto.setTs_date("2019-05"); // 화면값 긁어와서 넣어줄거여
			
			// 월별 알바별 timeList 조회
			List<TimeDto> lists = timeSer.tsDatetimeList(dto);

			System.out.println("일별 알바별 datetime list : "+lists);
			
			String ts_datetime = timeList.get(i).getTs_datetime();
			Double ts_workhour = timeList.get(i).getTs_workhour();
			System.out.println("ts_datetime:"+ts_datetime); //15:00-17:30
			System.out.println("ts_workhour:"+ts_workhour);

//			int ts_datetimeHH = Integer.parseInt(ts_datetime.split(":")[0]);
			
		}
		
		
		
		String aaa = "22:00-02:30"; // 이것도 됨
		Double workT = 4.5;
		
		int aaaHH = Integer.parseInt(aaa.substring(0, 2)); //20
		int aaaMM = Integer.parseInt(aaa.substring(3, 5)); // 0
		System.out.println(aaaHH);
		System.out.println(aaaMM);
		
		int bbbHH = Integer.parseInt(aaa.substring(6, 8)); // 0
		int bbbMM = Integer.parseInt(aaa.substring(9, 11)); // 0
		System.out.println(bbbHH);
		System.out.println(bbbMM);
		
		// 주간-주간, 주간-야간, 야간-주간, 야간-야간, 주간-야간-주간, 야간-주간-야간
		
		// 주간-야간, 주간-주간
		if(((aaaHH<22 || aaaHH>=6) || (bbbHH<=22 || bbbHH>6)) // 주간-주간
			&&
			((aaaHH<22 || aaaHH>=6) || (bbbHH>=22 || bbbHH<=6))) { // 주간-야간
			
			if(bbbHH == 0) { // 00 시
				bbbHH = 24;
			}else if(bbbHH == 1) { // 1시
				bbbHH = 25;				
			}else if(bbbHH == 2) { // 2시
				bbbHH = 26;				
			}else if(bbbHH == 3) { // 3시
				bbbHH = 27;				
			}else if(bbbHH == 4) { // 4시
				bbbHH = 28;				
			}else if(bbbHH == 5) { // 5시
				bbbHH = 29;				
			}else if(bbbHH == 6) { // 6시
				bbbHH = 30;				
			}
			
			int nightTimeHH = 0;
			String nightTimeMM = "";

			if(bbbHH>21) { // 업무 종료 시간이 22시 이후
				nightTimeHH = bbbHH - 22;
			}else { // 업무 종료 시간이 22시 이전
				Double dayTime = workT;
				System.out.println("only dayTime : "+dayTime);
			}
			
			// 업무 종료 시간이 6시 30분일때 30분은 dayTime으로 넘긴다
			if(bbbHH != 30 && nightTimeHH != 0) { 
				if(bbbMM == 30) {
					nightTimeMM = ".5";
				}else {
					nightTimeMM = ".0";
				}
			}else {
				nightTimeMM = ".0";
			}
			
			if(nightTimeHH != 0) { // 업무 종료 시간이 22시 이후
				Double nightTime = Double.valueOf(nightTimeHH + nightTimeMM);
				System.out.println("nightTime : "+nightTime);
				
				Double DayTimeExNightTime = workT-nightTime;
				System.out.println("nigthTime 있을시 dayTime : "+DayTimeExNightTime);				
			}
			
			
		}

		
		return "salary/salaryList";
	}

}



