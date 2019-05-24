package com.happy.para.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happy.para.common.DateModule;
import com.happy.para.dto.CalDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.model.Calendar_IService;

import net.sf.json.JSONObject;

@Controller
public class CalendarCtrl {
	
	@Autowired
	private Calendar_IService calSer;
	
	@RequestMapping(value="/selCal.do", method=RequestMethod.GET)
	public String calMonthCtrl(Model model, HttpSession session, HttpServletRequest request) {	
		
		OwnerDto own = (OwnerDto) session.getAttribute("loginDto");
		String store_code = own.getStore_code();
		List<CalDto> lists = calSer.calList(store_code);

		model.addAttribute("lists", lists);
		model.addAttribute("store_code", store_code);
		
		// ????
		request.setAttribute("lists", lists);
		
		System.out.println("접속한 매장 코드 : "+store_code);
		System.out.println("접속한 매장의 calLists"+lists);

		return "calendar/calendarView";
	}
	
	@RequestMapping(value="/delCal.do", method=RequestMethod.POST)
	@ResponseBody
	public String deletecalCtrl(HttpServletRequest request) {

		String cal_seq = request.getParameter("cal_seq");
		
		int isc = calSer.calDelete(cal_seq);
				
		// ajax 데이터 반환
		Map<String,Integer> mapl = new HashMap<String,Integer>();
		mapl.put("isc", isc);	
		
		JSONObject obj = JSONObject.fromObject(mapl);
		
		System.out.println(obj.toString());
		
		return obj.toString();
	}
	
	@RequestMapping(value="/regiCal.do", method=RequestMethod.POST)
	@ResponseBody
	public String InsertCalCtrl(HttpServletRequest request) {
		
		// 일정 정보 가져오기 
		
		String calid = request.getParameter("calendarId");		
		char[] chcalid = calid.toCharArray();
		
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		String store_code = request.getParameter("store_code");
	
		//시작일 연산
		String start = null;	
		String startyear	= request.getParameter("startyear");		
		String startmonth	= request.getParameter("startmonth");	
		startmonth = DateModule.getInstance().changeDateFormat(startmonth);
		String startday		= request.getParameter("startday");	
		startday = DateModule.getInstance().changeDateFormat(startday);
		String starthours	= request.getParameter("starthours");	
		starthours = DateModule.getInstance().changeDateFormat(starthours);
		String startminutes	= request.getParameter("startminutes");	
		startminutes = DateModule.getInstance().changeDateFormat(startminutes);
		start = startyear+"-"+startmonth+"-"+startday+" "+starthours+":"+startminutes+":00";
		
		//종료일 연산
		String end = null;
		String endyear 		= request.getParameter("endyear");		
		String endmonth 	= request.getParameter("endmonth");
		endmonth = DateModule.getInstance().changeDateFormat(endmonth);
		String endday		= request.getParameter("endday");
		endday = DateModule.getInstance().changeDateFormat(endday);
		String endhours		= request.getParameter("endhours");
		endhours = DateModule.getInstance().changeDateFormat(endhours);
		String endminutes	= request.getParameter("endminutes");
		endminutes = DateModule.getInstance().changeDateFormat(endminutes);
		end = endyear+"-"+endmonth+"-"+endday+" "+endhours+":"+endminutes+":00";		
		
		// Service 실행 
		CalDto dto = new CalDto("", chcalid[0], title, content, start, end, store_code, "");
		calSer.calRgister(dto);	
		
		List<CalDto> lists = calSer.calList(store_code);
		System.out.println(lists);
		
		String cal_seq = lists.get(0).getCal_seq();
		
		// ajax 데이터 반환
		Map<String,String> mapl = new HashMap<String,String>();
		mapl.put("id", cal_seq);	//{ "id" : cal_seq}	
		JSONObject obj = JSONObject.fromObject(mapl); 	
	
		return obj.toString();
	}
	
	@RequestMapping(value="/calModi.do", method=RequestMethod.POST)
	@ResponseBody
	public String UpdateCalCtrl(HttpServletRequest request) {
		
			// 일정 정보 가져오기 		
			String id = request.getParameter("id");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String store_code = request.getParameter("store_code");
			
			//시작일 연산
			String start = null;	
			String startyear	= request.getParameter("startyear");		
			String startmonth	= request.getParameter("startmonth");	
			startmonth = DateModule.getInstance().changeDateFormat(startmonth);
			String startday		= request.getParameter("startday");	
			startday = DateModule.getInstance().changeDateFormat(startday);
			String starthours	= request.getParameter("starthours");	
			starthours = DateModule.getInstance().changeDateFormat(starthours);
			String startminutes	= request.getParameter("startminutes");	
			startminutes = DateModule.getInstance().changeDateFormat(startminutes);
			start = startyear+"-"+startmonth+"-"+startday+" "+starthours+":"+startminutes+":00";
			
			//종료일 연산
			String end = null;
			String endyear 		= request.getParameter("endyear");		
			String endmonth 	= request.getParameter("endmonth");
			endmonth = DateModule.getInstance().changeDateFormat(endmonth);
			String endday		= request.getParameter("endday");
			endday = DateModule.getInstance().changeDateFormat(endday);
			String endhours		= request.getParameter("endhours");
			endhours = DateModule.getInstance().changeDateFormat(endhours);
			String endminutes	= request.getParameter("endminutes");
			endminutes = DateModule.getInstance().changeDateFormat(endminutes);
			end = endyear+"-"+endmonth+"-"+endday+" "+endhours+":"+endminutes+":00";		
			
			List<CalDto> lists = calSer.calList(store_code);
			System.out.println(lists);
			
			String cal_seq = lists.get(0).getCal_seq();
				
			System.out.println("");
			
			// Service 실행 
			CalDto dto = new CalDto(cal_seq, title, content, start, end);
			calSer.calModify(dto);

			// ajax 데이터 반환
			Map<String,String> mapl = new HashMap<String,String>();
			mapl.put("id", cal_seq);		
			JSONObject obj = JSONObject.fromObject(mapl); // 제이슨 오브젝트로

		return obj.toString();
	}


	
}
