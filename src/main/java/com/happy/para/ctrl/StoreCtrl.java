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
	
	@RequestMapping(value="/selStoreDetail.do", method=RequestMethod.GET)
	public String detailStore(String store_code, Model model) {
		System.out.println("매장상세 조회를 위한 store_code 값 : " + store_code);
		StoreDto dto = storeService.storeDetail(store_code);
		logger.info("select Store Detail Controller : {}", dto);
		model.addAttribute("dto", dto);
		return "store/storeDetail";
	}
	
	@RequestMapping(value="/regiStoreForm.do", method=RequestMethod.GET)
	public String addStoreForm(HttpSession session, Model model) {
		AdminDto aDto = (AdminDto) session.getAttribute("loginDto");
		System.out.println("로그인 된 담당자 DTO : " + aDto);
		String store_code = storeService.selectMaxStoreCode(aDto.getLoc_code());
		int cnt = 0;
		if(store_code == null) {
			store_code = aDto.getLoc_code()+"_01";
		}else {
			String subStoreCode = store_code.substring(store_code.indexOf("_")+1);
			System.out.println("잘린 store_code : " + subStoreCode);
			cnt = Integer.parseInt(subStoreCode) + 1;
			System.out.println(cnt);
			if(cnt/10 == 0) {
				store_code = aDto.getLoc_code() + "_0"+cnt;
			}else {
				store_code = aDto.getLoc_code() + "_"+cnt;
			}
		}
		System.out.println("완성된 이후 store_code : " + store_code);
		model.addAttribute("store_code", store_code);
		return "store/storeRegForm";
	}
	
	//regiStore.do
	@RequestMapping(value="/regiStore.do", method=RequestMethod.POST)
	public String addStore(StoreDto sDto) {
		logger.info("insert Store Controller : {}", sDto);
		boolean isc = storeService.storeInsert(sDto);
		System.out.println("매장 등록 완료 : "+isc);
		return "redirect:/selStoreList.do";
	}
	
	
	
//	@RequestMapping(value="/regiStoreForm.do", method=RequestMethod.GET)
//	public String addStore(HttpSession session, Model model) {
//		AdminDto aDto = (AdminDto) session.getAttribute("loginDto");
//		System.out.println("로그인 된 담당자 DTO : " + aDto);
//		int cnt = storeService.storeRow(aDto.getAdmin_id()+"");
//		String store_code = aDto.getLoc_code()+"_";
//		System.out.println("완성되기 전 store_code : " + store_code);
//		if(cnt == 0) {
//			store_code += "01";
//		}else if(cnt/10 == 0) {
//			store_code += "0"+cnt;
//		}else {
//			store_code += cnt;
//		}
//		System.out.println("완성된 이후 store_code : " + store_code);
//		model.addAttribute("store_code", store_code);
//		return "store/storeRegForm";
//	}
	
	@RequestMapping(value="/storeModiForm.do", method=RequestMethod.GET)
	public String modStoreForm(String store_code, Model model) {
		System.out.println("매장상세 조회를 위한 store_code 값 : " + store_code);
		StoreDto dto = storeService.storeDetail(store_code);
		logger.info("select Store Detail Controller : {}", dto);
		model.addAttribute("dto", dto);
		return "store/storeModForm";
	}
	
	@RequestMapping(value="/storeModi.do", method=RequestMethod.POST)
	public String modStore(StoreDto sDto, Model model) {
		logger.info("modify Store Controller : {}", sDto);
		boolean isc = storeService.storeModify(sDto);
		System.out.println("매장 수정완료 : " + isc);
		StoreDto dto = storeService.storeDetail(sDto.getStore_code());
		model.addAttribute("dto", dto);
		return "store/storeDetail";
	}
	
	//delStore.do
	@RequestMapping(value="/delStore.do", method=RequestMethod.GET)
	public String deleteStore(String store_code) {
		logger.info("delete Store Controller : {}", store_code);
		boolean isc = storeService.storeDelete(store_code);
		System.out.println("매장 삭제 완료 : " + isc);
		return "redirect:/selStoreList.do";
	}
}
