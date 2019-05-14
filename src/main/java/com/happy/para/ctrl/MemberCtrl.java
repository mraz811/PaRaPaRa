package com.happy.para.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.happy.para.model.Member_IService;

@Controller
public class MemberCtrl {
	//***************** 조민지 - 회원(업주,담당자)관리를 위한 컨트롤러 ***************// 

		@Autowired
		private Member_IService memService;
		
		// 로그인 페이지로 보내주는 메소드
		@RequestMapping(value="/loginForm.do", method=RequestMethod.GET)
		public String loginForm() {
			return "/member/loginForm";
		}
	
	
}
