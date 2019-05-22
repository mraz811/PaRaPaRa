package com.happy.para.ctrl;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.happy.para.dto.AlbaDto;
import com.happy.para.dto.OwnerDto;

@Controller
public class AlbaCtrl {

	
	
	// 알바 등록 폼으로 
	@RequestMapping(value="/albaRegiForm.do", method=RequestMethod.GET)
	public String albaRegiForm() {
		return "/alba/albaRegiForm";
	}
	
	// 알바 등록 쿼리
	@RequestMapping(value="/albaRegi.do", method=RequestMethod.POST)
	public String albaRegi(HttpSession session, AlbaDto albaDto) {
		OwnerDto ownDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = ownDto.getStore_code();
		
		
		return "";
	}
	
	
}
