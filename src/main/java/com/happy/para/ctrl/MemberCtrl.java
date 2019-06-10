package com.happy.para.ctrl;

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

import com.happy.para.common.TempKey;
import com.happy.para.dto.AdminDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.dto.PagingDto;
import com.happy.para.model.Member_IService;
import com.happy.para.model.Menu_IService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class MemberCtrl {
	//**********************************************************//	
	//				조민지 - 회원(업주,담당자)관리를 위한 컨트롤러			// 
	//**********************************************************//	
		@Autowired
		private Member_IService memService;
		
		@Autowired
		private Menu_IService menu_IService;
		
		@Autowired
		private TempKey tempKey;
		
		// main 페이지로 보내주는 메소드
		@RequestMapping(value="/main.do",method=RequestMethod.GET)
		public String toMain() {
			return "main";
		}
		
	//**********************************************************//	
	//						로그인/로그아웃							//
	//**********************************************************//
		// 로그인 페이지로 보내주는 메소드
		@RequestMapping(value="/loginForm.do", method=RequestMethod.GET)
		public String loginForm() {
			return "/member/loginForm";
		}

		// 로그인 실행 전 ajax를 이용한 로그인 가능성 여부 검토
		@RequestMapping(value="/loginChk.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
		@ResponseBody
		public String loginChk(String id, String pw, String auth){
			System.out.println("들어옴"+id+":"+pw+":"+auth);
			if(auth.equalsIgnoreCase("U")) {
				OwnerDto oDto = new OwnerDto(id,pw);
				OwnerDto owner = memService.ownerLogin(oDto);
				return (owner==null)? "실패":"성공";
			} else {
				AdminDto aDto = new AdminDto(Integer.parseInt(id),pw);
				AdminDto admin = memService.adminLogin(aDto);
				return (admin==null)? "실패":"성공";
			}
			
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
				
			} else if(auth.equalsIgnoreCase("A") || auth.equalsIgnoreCase("S")) { 
				//담당자 권한 로그인 시
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
			if(auth.equalsIgnoreCase("A") || auth.equalsIgnoreCase("S")) { // 담당자 권한 로그아웃
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
	
	//**********************************************************//	
	//						담당자/업주 회원등록						//
	//**********************************************************//	
		// 담당자 회원 등록 페이지로 보내주는 메소드
		@RequestMapping(value="adminRegiForm.do", method=RequestMethod.GET)
		public String adminRegiForm() {
			return "/member/adminRegiForm";
		}
		
		// 담당자 회원 등록 메소드
		@RequestMapping(value="/adminRegi.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
		@ResponseBody
		public String adminRegi(AdminDto aDto, String loc_sido, String loc_sigungu) {
			String loc_code = loc_sido + loc_sigungu;
			aDto.setLoc_code(loc_code);
			
			System.out.println("--------------"+aDto+"_---------------");
			
			int n = memService.adminRegister(aDto);
			// ajax return 값
			return (n>0)? "성공":"실패";
			
		}
		
		// 업주 회원 등록 페이지로 보내주는 메소드 (업주 등록 여부 0인 매장코드 리스트 전송)
		@RequestMapping(value="ownerRegiForm.do", method=RequestMethod.GET)
		public String ownerRegiForm(Model model, HttpSession session) {
			AdminDto adto = (AdminDto)session.getAttribute("loginDto");
			List<String> store_code = memService.selStoreCodeList(adto.getAdmin_id()+"");
			model.addAttribute("store_code", store_code);
			return "/member/ownerRegiForm";
		}
		
		// 업주 회원 등록 메소드 (실행 시 업주 등록, 해당 매장 코드의 매장 업주등록여부 1로 업데이트)
		@RequestMapping(value="/ownerRegi.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
		@ResponseBody
		public String ownerRegi(Model model, OwnerDto oDto, String loc_code) {
			System.out.println(oDto);
			List<Integer> owner_menu = menu_IService.selAllMenu();
//			System.out.println(owner_menu);
			String owner_menuInput = ""; 
			for (int i = 0; i < owner_menu.size(); i++) {
				owner_menuInput += owner_menu.get(i)+",";
			}
//			System.out.println(owner_menuInput + "_______fot문 밖"); 
			oDto.setOwner_menu(owner_menuInput);
			int n = memService.ownerRegister(oDto);
//			
			return (n>0)? "성공":"실패";
		}
	
		// 담당자 회원 등록시 ajax를 통한 아이디 중복 검사
		@RequestMapping(value="/admIdChk.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
		@ResponseBody
		public String admIdChk(String admin_id) {
			int n = memService.adminIdDupleChk(admin_id);
			return (n==0)? "사용 가능한 아이디":"사용 불가능한 아이디";
		}
		
		// 업주 회원 등록시 ajax를 통한 아이디 중복 검사
		@RequestMapping(value="/ownIdChk.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
		@ResponseBody
		public String ownIdChk(String owner_id) {
			int n = memService.ownerIdDupleChk(owner_id);
			return (n==0)? "사용 가능":"사용 불가능";
		}	
				
	//**********************************************************//
	//						마이페이지 및 정보수정						//	
	//**********************************************************//	
		
		// 마이페이지 로그인 페이지로
		@RequestMapping(value="/pwCheckForm.do", method=RequestMethod.GET)
		public String pwCheckForm() {
			return "/member/myPageLogin";
		}
		
		// 담당자/업주의 마이페이지 비밀번호 확인 후 마이 페이지로
		@RequestMapping(value="/toMypage.do", method=RequestMethod.POST)
		public String toMypage(String id, String pw, String auth, Model model) {
			System.out.println(id+":"+pw+":"+auth);
			
			if(auth.equalsIgnoreCase("A") || auth.equalsIgnoreCase("S")) {
				AdminDto aMyDto = new AdminDto(Integer.parseInt(id), pw);
				AdminDto aDto = memService.adminLogin(aMyDto);
				if(aDto!=null) {
					System.out.println("비밀번호 일치");
					model.addAttribute("aDto", aDto);
					return "/member/myPage";
				}
			} else if(auth.equalsIgnoreCase("U")) {
				OwnerDto oMyDto = new OwnerDto(id, pw);
				OwnerDto oDto = memService.ownerLogin(oMyDto);
				if(oDto != null) {
					model.addAttribute("oDto", oDto);
					return "/member/myPage";
				}
			} 
			
			System.out.println("비밀번호 불일치");
			return "redirect:/pwCheckForm.do";
		}
		
		// 마이페이지에서 수정된 정보만 수정해 줍니다		
		@RequestMapping(value="/adminModi.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
		@ResponseBody
		public String adminModi(AdminDto aDto) {
			System.out.println("입력 받은 값 : "+aDto);
			// 비밀번호를 입력받지 않은 경우 비밀번호를 null 처리 해준다. 
			if(aDto.getAdmin_pw()==""){
				aDto.setAdmin_pw(null);
			}
			int n = memService.adminModify(aDto);
			// 수정 후에 세션 값이 변경이 되지 않기 때문에 로그아웃 후 재로그인 합니다.
//			return "redirect:/logout.do?auth=A";
			return (n>0)?"성공":"실패";
		}
		
		// 마이페이지에서 수정된 정보만 수정해 줍니다		
		@RequestMapping(value="/ownerModi.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
		@ResponseBody
		public String ownerModi(OwnerDto oDto) {
			System.out.println("입력 받은 값: "+oDto);
			
			if(oDto.getOwner_pw()=="") {
				oDto.setOwner_pw(null);
			}
			int n = memService.ownerModify(oDto);
//			return "redirect:/logout.do?auth=U";
			return (n>0)?"성공":"실패";
		}
		
		//**********************************************************//	
		//					비밀번호 찾기 후 이메일전송						//
		//**********************************************************//	
		
		// 비밀번호 찾기 (아이디, 이메일 입력) 페이지로 보냄
		@RequestMapping(value="/findPwForm.do", method=RequestMethod.GET)
		public String findPwForm() {
			return "/member/findPwForm";
		}
		
		// 비밀번호 아이디/이메일 유효 검사 (ajax)
		@RequestMapping(value="/findPwChk.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
		@ResponseBody
		public String findPwChk(String id, String email, String auth) {
			//findPwChkAdmin	//findPwChkOwner
			System.out.println(id+"///"+email+"////"+auth);
			
			if(auth.equalsIgnoreCase("U")) {
				Map<String, String> owmap = new HashMap<String, String>();
				owmap.put("owner_id", id);
				owmap.put("owner_email", email);
				int n = memService.findPwChkOwner(owmap);
				return (n>0)? "성공":"실패";
			} else {
				Map<String, String> admap = new HashMap<String, String>();
				admap.put("admin_id", id);
				admap.put("admin_email", email);
				int n= memService.findPwChkAdmin(admap);
				return (n>0)? "성공":"실패";
			}
			
		}
		
		// 아이디, 이메일을 받아서 비밀번호 임시 생성 후 이메일로 보내주는 메소드
		@RequestMapping(value="/findPw.do", method=RequestMethod.POST)
		public String findPw(String auth, String id, String email) {
			System.out.printf("입력받은 값: auth %s, id %s, email %s",auth, id, email);

			if(auth.equalsIgnoreCase("A")) {
				Map<String, String> admap = new HashMap<String, String>();
				admap.put("admin_id", id);
				admap.put("admin_email", email);
				// temp 키로 8자리 랜덤 비밀번호 생성
				String tempPw = tempKey.getKey(8, false);
				admap.put("temp_pw", tempPw);
				
				memService.findAdminPw(admap);
				
			} else if(auth.equalsIgnoreCase("U")) {
				Map<String, String> owmap = new HashMap<String, String>();
				owmap.put("owner_id", id);
				owmap.put("owner_email", email);
				// temp 키로 8자리 랜덤 비밀번호 생성
				String tempPw = tempKey.getKey(8, false);
				owmap.put("temp_pw", tempPw);
				System.out.println("업데이트 전 map : " + owmap);
				int n = memService.findOwnerPw(owmap);
				System.out.println("업데이트 된 후 값 : " + n);
			}
			
			return "redirect:/loginForm.do";
		}
		
		//**********************************************************//	
		//				담당자 조회 페이징(ajax)-지역별 + 퇴사자 조회			//
		//**********************************************************//
		
		// 담당자 페이징
		@RequestMapping(value="/adminPaging.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
		@ResponseBody
		public String adminPaging(Model model, String chkCons , PagingDto pageDto) {
			JSONObject json = null;
			Map<String, String> map = new HashMap<String, String>();
			if(chkCons.equalsIgnoreCase("N") || chkCons.equalsIgnoreCase("Y")) {
				pageDto.setTotal(memService.adminListRow(chkCons));
				map.put("start", pageDto.getStart()+"");
				map.put("end", pageDto.getEnd()+"");
				map.put("admin_delflag", chkCons);
				json = adminJson(memService.adminList(map), pageDto);
			} else { 
				pageDto.setTotal(memService.adminLocListRow(chkCons));
				map.put("start", pageDto.getStart()+"");
				map.put("end", pageDto.getEnd()+"");
				map.put("loc_sido", chkCons);
				json = adminJson(memService.adminLocList(map), pageDto);
			}
			System.out.println(json.toString());
			return json.toString();
		}
		
		// JSONArray 형태로 페이징 처리된 담당자 리스트를 담을 예정
		private JSONObject adminJson(List<AdminDto> adLists, PagingDto pDto) {
			JSONObject json = new JSONObject(); // 최종 담을 애
			JSONArray jLists = new JSONArray(); // arraylist 담을 애
			JSONObject jList = null; // 그냥 제이슨 타입으로
			
			for (AdminDto dto : adLists) {
				// 새로 new 해서 키 중복 시 값 없어지지 않게
				jList = new JSONObject();
				jList.put("admin_id", dto.getAdmin_id());
				jList.put("admin_name", dto.getAdmin_name());
				jList.put("admin_phone", dto.getAdmin_phone());
				jList.put("admin_email", dto.getAdmin_email());
				jList.put("loc_name", dto.getLoc_name());
				jList.put("loc_code", dto.getLoc_code());
				
				// json 형태의 값들을 list에 담아줌
				jLists.add(jList);
			}
			
			jList = new JSONObject();
			jList.put("pageList",pDto.getPageList());
			jList.put("index",pDto.getIndex());
			jList.put("pageNum",pDto.getPageNum());
			jList.put("listNum",pDto.getListNum());
			jList.put("total",pDto.getTotal());
			jList.put("count",pDto.getCount());
			
			json.put("adLists", jLists);
			json.put("adPaging", jList);
			
			return json;
			
		}
		
		// 담당자 전체 조회 (페이징 사용)
		@RequestMapping(value="/selAdminList.do", method=RequestMethod.GET)
		public String selAdminList(Model model, String loc_sido, String delflag) {
			
			System.out.println("loc_sido : "+loc_sido +"\n delflag: "+delflag);
			
			// 담당자 전체 조회 
			if(loc_sido==null && (delflag==null || delflag.equalsIgnoreCase("N"))) {
				
				PagingDto pagingDto = new PagingDto();
				pagingDto.setTotal(memService.adminListRow("N"));
				Map<String, String> map = new HashMap<String, String>();
				map.put("start", pagingDto.getStart()+"");
				map.put("end", pagingDto.getEnd()+"");
				map.put("admin_delflag", "N");
				List<AdminDto> adminList = memService.adminList(map);
			
				model.addAttribute("adminList", adminList);
				model.addAttribute("adminRow", pagingDto);
				model.addAttribute("chkCons", "N");
			
				return "/member/adminList";
			
			// 지역별 담당자 전체 조회
			} else if(loc_sido!=null){
				PagingDto pagingDto = new PagingDto();
				pagingDto.setTotal(memService.adminLocListRow(loc_sido));
				Map<String, String> map = new HashMap<String, String>();
				map.put("start", pagingDto.getStart()+"");
				map.put("end", pagingDto.getEnd()+"");
				map.put("loc_sido", loc_sido);
				List<AdminDto> adminLocList = memService.adminLocList(map);
				
				model.addAttribute("adminLocList", adminLocList);
				model.addAttribute("adminRow", pagingDto);
				model.addAttribute("chkCons", loc_sido);

				return "/member/adminList";
				
			// 퇴사자 delflag='Y'조회
			} else {
				PagingDto pagingDto = new PagingDto();
				pagingDto.setTotal(memService.adminListRow("Y"));
				Map<String, String> map = new HashMap<String, String>();
				map.put("start", pagingDto.getStart()+"");
				map.put("end", pagingDto.getEnd()+"");
				map.put("admin_delflag", "Y");
				List<AdminDto> delAdminList = memService.adminList(map);
				
				model.addAttribute("delAdminList", delAdminList);
				model.addAttribute("adminRow", pagingDto); 
				model.addAttribute("chkCons", "Y");
				
				return "/member/adminList";
			}
		}
		
		//**********************************************************//	
		//						업주 조회 페이징(ajax)						//
		//**********************************************************//		
		@RequestMapping(value="/ownerPaging.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
		@ResponseBody
		public String ownerPaging(HttpSession session, PagingDto pagingDto, String loc_code) {
			if(loc_code==null) {
				AdminDto ad = (AdminDto) session.getAttribute("loginDto");
				loc_code = ad.getLoc_code();
			}
			JSONObject json = null;
			Map<String, String> map = new HashMap<String, String>();
			pagingDto.setTotal(memService.ownerListRow(loc_code));
			map.put("start", pagingDto.getStart()+"");
			map.put("end", pagingDto.getEnd()+"");
			map.put("loc_code", loc_code);
			json = ownerJson(memService.ownerList(map),pagingDto);
			json.put("listSize", pagingDto.getTotal());
			
			session.removeAttribute("ownerRow");
			session.setAttribute("ownerRow", pagingDto);
			
			System.out.println(json.toString());
			return json.toString();
		}
		
		private JSONObject ownerJson(List<OwnerDto> ownerList, PagingDto pDto) {
			JSONObject json = new JSONObject();
			JSONArray jLists = new JSONArray();
			JSONObject jList = null;
			
			for (OwnerDto dto : ownerList) {
				jList = new JSONObject();
				jList.put("owner_seq", dto.getOwner_seq());
				jList.put("owner_id", dto.getOwner_id());
				jList.put("owner_name", dto.getOwner_name());
				jList.put("owner_phone", dto.getOwner_phone());
				jList.put("owner_email", dto.getOwner_email());
				jList.put("store_code", dto.getStore_code());
				jList.put("owner_start", dto.getOwner_start());
				jList.put("owner_end", dto.getOwner_end());
				jList.put("store_name", dto.getStore_name());
//				jList.put("listSize", ownerList.size());
				jLists.add(jList);
			}
			
			jList = new JSONObject();
			jList.put("pageList",pDto.getPageList());
			jList.put("index",pDto.getIndex());
			jList.put("pageNum",pDto.getPageNum());
			jList.put("listNum",pDto.getListNum());
			jList.put("total",pDto.getTotal());
			jList.put("count",pDto.getCount());
			
			json.put("owLists", jLists);
			json.put("owPaging", jList);
//			json.put("listSize", ownerList.size());
			
			return json;
		}

		// 업주 전체 조회 (페이징 사용)
		@RequestMapping(value="/selOwnerList.do", method=RequestMethod.GET)
		public String selOwnerList(HttpSession session, Model model, String loc_code) {
			
			if(loc_code==null) {
				AdminDto ad = (AdminDto) session.getAttribute("loginDto");
				loc_code = ad.getLoc_code();
			}
			PagingDto pagingDto = null;
			
//			// 주석 풀면 SESSION에 페이지 정보 저장 가능하지만 그냥 첫페이지로 보냅니다.
//			if(session.getAttribute("ownerRow")==null) {
				pagingDto = new PagingDto();
//			}else {
//				pagingDto = (PagingDto) session.getAttribute("ownerRow");
//			}
			
			pagingDto.setTotal(memService.ownerListRow(loc_code));
			Map<String, String> map = new HashMap<String, String>();
			map.put("start", pagingDto.getStart()+"");
			map.put("end", pagingDto.getEnd()+"");
			map.put("loc_code", loc_code);
			List<OwnerDto> ownerlist = memService.ownerList(map);
			
			model.addAttribute("ownerlist", ownerlist);
			model.addAttribute("ownerRow", pagingDto);
			
			return "/member/ownerList";
		}
		
		
		//**********************************************************//	
		//			삭제 -  담당자(delflag)/업주(계약종료일) update				//
		//**********************************************************//		
		
		// 담당자 삭제 메소드 delflag->Y
		@RequestMapping(value="/delAdmin.do", method= {RequestMethod.POST,RequestMethod.GET})
		public String delAdmin(String admin_id) {
			memService.adminDelete(admin_id);
			return "redirect:/selAdminList.do";
		}
		
		// 업주 삭제 메소드 (계약종료일 업데이트)
		@RequestMapping(value="/delOwner.do", method= RequestMethod.POST)
		public String delOwner(HttpSession session, String owner_end, String owner_seq) {
//			session에서 loc_code받아오기
			AdminDto ad = (AdminDto) session.getAttribute("loginDto");
			Map<String, String> map = new HashMap<String, String>();
			map.put("owner_end", owner_end);
			map.put("owner_seq", owner_seq);
			
			memService.ownerDelete(map);
			return "redirect:/selOwnerList.do?loc_code="+ad.getLoc_code();
		}
		
		// 업주 계약 연장 메소드 (계약종료일 초기화)
		@RequestMapping(value="/cancelDelOwner.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
		@ResponseBody
		public void cancelDelOwner(String owner_seq) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("owner_seq", owner_seq);
			map.put("owner_end", "");
//			System.out.println(map);
			memService.ownerDelete(map);
		}
		
		// 고객 주문 화면에서 나가기 위해 비밀번호 확인 page
		@RequestMapping(value="/requestChk.do", method=RequestMethod.GET)
		public String requestChk() {
			return "/member/chkPw";
		}
		
}
