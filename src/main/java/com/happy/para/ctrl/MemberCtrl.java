package com.happy.para.ctrl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.model.Member_IService;

@Controller
public class MemberCtrl {
	//***************** 조민지 - 회원(업주,담당자)관리를 위한 컨트롤러 ***************// 

		@Autowired
		private Member_IService memService;
		
		// main 페이지로 보내주는 메소드
		@RequestMapping(value="/main.do",method=RequestMethod.GET)
		public String toMain() {
			return "main";
		}
		
		// 로그인 페이지로 보내주는 메소드
		@RequestMapping(value="/loginForm.do", method=RequestMethod.GET)
		public String loginForm() {
			return "/member/loginForm";
		}
	
		// 아이디, 비밀번호, 권한(관리자/업주)를 받아와서 로그인을 실행시켜주는 메소드
		@RequestMapping(value="/login.do", method=RequestMethod.POST)
		public String login(String id, String pw, String auth, HttpSession session) {
			System.out.printf("로그인 컨트롤러 : %s %s %s\n",id,pw,auth);
			if(auth.equalsIgnoreCase("U")) {
				// 업주권한 로그인 시
				OwnerDto owner = new OwnerDto(id, pw);
				OwnerDto ownerDto = memService.ownerLogin(owner);
				System.out.println(ownerDto);
				// 로그인 성공 시 OwnerDto 반환, 실패시 null 반환하므로
				if(ownerDto!=null) {
					session.setAttribute("loginDto", ownerDto);
					// *************나중에 메인 페이지 어떻게 할건지 보고 수정***************
					return "redirect:/main.do";
				}
				
			} else if(auth.equalsIgnoreCase("A")) { 
				//관리자 권한 로그인 시
				AdminDto admin = new AdminDto(Integer.parseInt(id), pw);
				AdminDto adminDto = memService.adminLogin(admin);
				System.out.println(adminDto);
				
				if(adminDto!=null) {
					session.setAttribute("loginDto", adminDto);
					return "redirect:/main.do";
				} 
			}
			
			return "redirect:/loginForm.do";
		}
		
		// 담당자/업주 로그아웃 메소드
		@RequestMapping(value="/logout.do", method=RequestMethod.GET)
		public String logout(HttpSession session, String auth) {
			// 화면에서 auth 값을 받아와서 
			if(auth.equalsIgnoreCase("A")) { // 담당자 권한 로그아웃
				AdminDto aDto = (AdminDto) session.getAttribute("loginDto");
				if(aDto!=null) {
					session.removeAttribute("loginDto");
					System.out.println("담당자 로그아웃");
				}
			} else if(auth.equalsIgnoreCase("U")) { // 업주 권한 로그아웃
				OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
				if(oDto!=null) {
					session.removeAttribute("loginDto");
					System.out.println("업주 로그아웃");
				}
			}
			return "redirect:/loginForm.do";
		}
		
		// 담당자 회원 등록 페이지로 보내주는 메소드
		@RequestMapping(value="adminRegiForm.do", method=RequestMethod.GET)
		public String adminRegiForm() {
			return "/member/adminRegiForm";
		}
		
		// **********수정 중 ********* 담당자 회원 등록 테스트 중  
		@RequestMapping(value="/adminRegi.do", method=RequestMethod.GET)
		public String adminRegi(AdminDto aDto) {
			memService.adminRegister(aDto);
			return "redirect:/selAdminList.do";
		}
		
		// 엄주 회원 등록 페이지로 보내주는 메소드
		@RequestMapping(value="ownerRegiForm.do", method=RequestMethod.GET)
		public String ownerRegiForm() {
			return "/member/ownerRegiForm";
		}
		
		//  **********수정 중 ********* 업주 회원 등록
		@RequestMapping(value="/ownerRegi.do", method=RequestMethod.GET)
		public String ownerRegi(OwnerDto oDto) {
			memService.ownerRegister(oDto);
			return "redirect:/selOwnerList.do";
		}
		
		
		
}
