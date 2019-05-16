package com.happy.para.ctrl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		
		// 담당자 회원 등록 메소드
		@RequestMapping(value="/adminRegi.do", method=RequestMethod.POST)
		public String adminRegi(AdminDto aDto, String loc_sido, String loc_sigungu) {
			
			String loc_code = loc_sido + loc_sigungu;
			aDto.setLoc_code(loc_code);
			
			System.out.println(aDto);
			int n = memService.adminRegister(aDto);
			// 담당자 회원 등록 성공시
			if(n>0) {
				return "redirect:/selAdminList.do";
			}
			// 실패 시 회원 등록 폼으로
			return "redirect:/adminRegiForm.do";
		}
		
		// 엄주 회원 등록 페이지로 보내주는 메소드 (업주 등록 여부 0인 매장코드 리스트 전송)
		@RequestMapping(value="ownerRegiForm.do", method=RequestMethod.GET)
		public String ownerRegiForm(Model model) {
			List<String> store_code = memService.selStoreCodeList();
			model.addAttribute("store_code", store_code);
			return "/member/ownerRegiForm";
		}
		
		// 업주 회원 등록 메소드 (실행 시 업주 등록, 해당 매장 코드의 매장 업주등록여부 1로 업데이트)
		@RequestMapping(value="/ownerRegi.do", method=RequestMethod.POST)
		public String ownerRegi(OwnerDto oDto) {
			System.out.println(oDto);
			memService.ownerRegister(oDto);
			return "redirect:/selOwnerList.do";
		}
		
		// 마이페이지 로그인 페이지로
		@RequestMapping(value="/pwCheckForm.do", method=RequestMethod.GET)
		public String pwCheckForm() {
			return "/member/myPageLogin";
		}
		
		// 마이페이지 비밀번호 확인 후 마이 페이지로
		@RequestMapping(value="/toMypage.do", method=RequestMethod.POST)
		public String toMypage(String id, String pw, String auth) {
	System.out.println(id+":"+pw+":"+auth);
			
			if(auth.equalsIgnoreCase("A")) {
				AdminDto aMyDto = new AdminDto(Integer.parseInt(id), pw);
				AdminDto aDto = memService.adminLogin(aMyDto);
				if(aDto!=null) {
					System.out.println("비밀번호 일치");
					return "/member/myPage";
				}
			} else if(auth.equalsIgnoreCase("U")) {
				OwnerDto oMyDto = new OwnerDto(id, pw);
				OwnerDto oDto = memService.ownerLogin(oMyDto);
				if(oDto != null) {
					return "/member/myPage";
				}
			} 
			
			System.out.println("비밀번호 불일치");
			return "redirect:/pwCheckForm.do";
		}
		
		
}
