package com.happy.para.ctrl;

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
import com.happy.para.model.Alba_IService;
import com.happy.para.model.Timesheet_IService;

@Controller
public class AlbaCtrl {

	@Autowired
	private Alba_IService alService;
	
	@Autowired
	private Timesheet_IService tsService;
	
	// 아르바이트 등록 폼으로 
	@RequestMapping(value="/albaRegiForm.do", method=RequestMethod.GET)
	public String albaRegiForm() {
		return "/alba/albaRegForm";
	}
	
	// 아르바이트 등록
	@RequestMapping(value="/albaRegi.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public void albaRegi(HttpSession session, AlbaDto albaDto) {
		OwnerDto ownDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = ownDto.getStore_code();
		albaDto.setStore_code(store_code);
		System.out.println("------------------------\n"+albaDto+"\n----------------------");
		alService.albaRegister(albaDto);
	}
	
	@RequestMapping(value="/selAlbaList.do", method=RequestMethod.GET)
	public String selAlbaList(HttpSession session, Model model) {
		OwnerDto ownDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = ownDto.getStore_code();

		List<AlbaDto> alists = tsService.tsAlba(store_code);
		System.out.println(alists);
		model.addAttribute("albaList", alists);
		
		return "/alba/albaList";
	}
	
	// 아르바이트 삭제
	@RequestMapping(value="/delAlba.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String delAlba(String alba_seq) {
		System.out.println(alba_seq);
		alService.albaDelete(alba_seq);
		return "redirect:/selAlbaList.do";
	}
	
	// 아르바이트 정보 수정 폼으로
	@RequestMapping(value="/albaModiForm.do", method=RequestMethod.GET)
	public String albaModiForm(String alba_seq, Model model) {
		AlbaDto albaDetail = alService.getAlbaDetail(alba_seq);
		model.addAttribute("alba", albaDetail);
		return "/alba/albaModForm";
	}
	
	// 아르바이트 정보 수정
	@RequestMapping(value ="/albaModi.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public void albaModi(AlbaDto aDto) {
		System.out.println(aDto);
		alService.albaModify(aDto);
	}
	
	// 아르바이트 상세조회
	@RequestMapping(value="/albaDetail.do", method=RequestMethod.GET)
	public String getAlbaDetail(String alba_seq, Model model) {
		AlbaDto alDto = alService.getAlbaDetail(alba_seq);
		model.addAttribute("alba",alDto);
		return "/alba/albaDetail";
	}
	
}
