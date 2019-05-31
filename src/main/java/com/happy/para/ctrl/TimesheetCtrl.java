package com.happy.para.ctrl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
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

import com.happy.para.common.DateModule;
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

		System.out.println("화면에서 가져온 eTime > "+eTime);
		
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

		String ts_datetime = sTime+"-"+eTime; // 03:00-04:30 형태로 만든다.
		
		System.out.println("ts_start > "+sTime);
		System.out.println("ts_end > "+eTime);

		Map<String, String> map = new HashMap<String,String>();
		map.put("ts_start", sTime);

		if(eTime.equalsIgnoreCase("00:00")) { // 쿼리 계산시 date 는 24:00 입력 안됨
			eTime = "23:59";
			map.put("ts_end", eTime);
			System.out.println("00:00 일때 수정된 eTime > "+eTime);
		}else {
			map.put("ts_end", eTime);			
		}
		
		TimeDto workTimeDto = timeSer.salaryView(map);
		
		System.out.println("workTimeDto > "+workTimeDto);
		
		Double earlyWork = workTimeDto.getEarlywork();
		Double dayWork = workTimeDto.getDaywork();
		Double nightWork = workTimeDto.getNightwork();
		
		if(earlyWork.equals(hour)) {
			dayWork = 0.0;
		}else if(nightWork.equals(hour)) {
			dayWork = 0.0;
		}
		
		dto.setTs_daywork(dayWork);
		dto.setTs_nightwork(earlyWork+nightWork);
		
		dto.setTs_datetime(ts_datetime);
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
	public String salary(HttpSession session, TimeDto dto, Model model) throws ParseException {
		
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
		
		System.out.println("timeList.size() : "+timeList.size());
		
		// 타임시트랑 알바리스트랑 조인해서 한번에 가져올거임 쿼리수정 필요!
		/*
		for (int i = 0; i < albaLists.size(); i++) {
			dto.setAlba_seq(albaLists.get(i).getAlba_seq());
			System.out.println("albaLists.get(i).getAlba_seq() : " + albaLists.get(i).getAlba_seq());
			
			// timesheet 의 TS_DATE 중 화면의 월과 같은 애들만
			dto.setTs_date("2019-05"); // 화면값 긁어와서 넣어줄거여
			
			// 월별 알바별 timeList 조회
			List<TimeDto> workDto = timeSer.tsDatetimeList(dto);

			System.out.println("월별 알바별 datetime total : "+workDto);
		}
		*/	
		
		DateModule dateM = DateModule.getInstance();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		String getMonth = "2026-02"; // 화면에서 가져올 값
		String getMonthFeb = getMonth.substring(5);
		
		System.out.println("getMonthFeb > "+getMonthFeb);
		
		String underTen = "-";
		int setDate = 1;
		
		Date startDate = formatter.parse(getMonth+underTen+setDate);  // 첫 주 시작 날짜
		startDate = new Date(startDate.getTime()); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int dayNum = cal.get(Calendar.DAY_OF_WEEK); // 해당 월 1일의 첫번째 요일
		int lastNum = cal.getActualMaximum(cal.DAY_OF_MONTH);// 해당 월의 마지막 날짜
		
		System.out.println("dayNum > "+dayNum); 
		System.out.println("lastNum > "+lastNum);
		
		String[] wStartDate = null;
		String[] wLastDate = null;
		
		int chkCnt = 0;
		
		// 2월이면서 시작요일숫자가1이면서 끝나는요일숫자가 29가 아닌애들만 배열 크기 4
		if(getMonthFeb.equals("02") && dayNum==1 && lastNum!=29) {
			wStartDate = new String[4];
			wLastDate = new String[4];
			chkCnt = 1;
		}else {
			wStartDate = new String[5];
			wLastDate = new String[5];
		}
		
		wStartDate[0] = getMonth+dateM.changeDateFormat(Integer.toString(setDate));
		System.out.println("첫주 wStartDate > "+wStartDate[0]);
		
		int addDate = 7-dayNum; //3
		
		setDate = setDate+addDate;

		wLastDate[0] = getMonth+dateM.changeDateFormat(Integer.toString(setDate)); // 첫 주 마지막 날짜
		System.out.println("주 lastDate > "+wLastDate[0]);
		
		setDate = setDate+1;

		wStartDate[1] = getMonth+dateM.changeDateFormat(Integer.toString(setDate));
		
		int rCnt = 0;
		
		if(chkCnt==1) {
			rCnt = 3;
			wLastDate[3] = getMonth+"-"+lastNum;
		}else {
			rCnt = 4;
			wLastDate[4] = getMonth+"-"+lastNum;
		}
		
		for (int cnt = 1; cnt < rCnt;) {
			
			setDate = setDate+6;
			wLastDate[cnt] = getMonth+dateM.changeDateFormat(Integer.toString(setDate));
			
			cnt++;
			
			setDate = setDate+1;
			wStartDate[cnt] = getMonth+dateM.changeDateFormat(Integer.toString(setDate));			
		
		}

		System.out.println(Arrays.toString(wStartDate));
		System.out.println(Arrays.toString(wLastDate));

		return "salary/salaryList";
	}

}



