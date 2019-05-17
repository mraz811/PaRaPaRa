package com.happy.para.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.happy.para.dto.PaoDto;
import com.happy.para.model.Pao_IService;

@Controller
public class PaoCtrl {
	
	@Autowired
	private Pao_IService paoService;
	
	@RequestMapping(value="/selPaoList.do",method=RequestMethod.GET)
	public String paoList(String store_code, Model model) {
		System.out.println("=== 넘겨받은 매장코드 === : "+store_code);
		List<PaoDto> paoLists = paoService.paoList(store_code);
		System.out.println(paoLists);
		
		model.addAttribute("paoLists", paoLists);
		return "pao/paoList";
	}
	
}
