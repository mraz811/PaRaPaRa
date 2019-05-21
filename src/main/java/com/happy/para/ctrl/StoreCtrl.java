package com.happy.para.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.PagingDto;
import com.happy.para.dto.StoreDto;
import com.happy.para.model.Store_IService;

@Controller
public class StoreCtrl {

	private Logger logger = LoggerFactory.getLogger(StoreCtrl.class);
	
	@Autowired
	private Store_IService storeService;
	
//	@RequestMapping(value="/selStoreList.do", method=RequestMethod.GET)
//	public String locStoreList(HttpSession session, Model model) {
//		AdminDto aDto = (AdminDto) session.getAttribute("loginDto");
//		String loc_code= aDto.getLoc_code();
//		logger.info("storeList Controller : {}", loc_code);
//		StoreDto dto = new StoreDto();
//		if(loc_code.equalsIgnoreCase("all")) {
//			System.out.println("관리자");
//		}else {
//			dto.setLoc_code(loc_code);
//		}
//		System.out.println(loc_code);
//		List<StoreDto> lists = storeService.storeList(dto);
//		model.addAttribute("lists", lists);
//		return "store/kakaoStoreList";
//	}
	
	
	@RequestMapping(value="/selStoreList.do", method=RequestMethod.GET)
	public String pagingStore(HttpSession session, Model model) {
		PagingDto rowDto = null;
		List<StoreDto> lists = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		AdminDto aDto = (AdminDto)session.getAttribute("loginDto");
		if(session.getAttribute("storeRow")==null) {
			rowDto = new PagingDto();
		}else {
			rowDto = (PagingDto) session.getAttribute("storeRow");
		}
		
		map.put("admin_id", aDto.getAdmin_id());
		map.put("start", rowDto.getStart());
		map.put("end", rowDto.getEnd());
		
		lists = storeService.storeListPaging(map);
		model.addAttribute("lists", lists);
		model.addAttribute("row", rowDto);
		
		return "store/storeList";
	}
	
}
