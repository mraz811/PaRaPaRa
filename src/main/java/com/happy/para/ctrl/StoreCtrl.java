package com.happy.para.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.PagingDto;
import com.happy.para.dto.StoreDto;
import com.happy.para.model.Stock_IService;
import com.happy.para.model.Store_IService;

import net.sf.json.JSONObject;

@Controller
public class StoreCtrl {

	private Logger logger = LoggerFactory.getLogger(StoreCtrl.class);
	
	@Autowired
	private Store_IService storeService;
	
	@Autowired
	private Stock_IService stockService;
	
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
		rowDto.setTotal(storeService.storeListRow(aDto.getAdmin_id()+""));
		map.put("admin_id", aDto.getAdmin_id());
		map.put("start", rowDto.getStart());
		map.put("end", rowDto.getEnd());
		
		lists = storeService.storeListPaging(map);
		model.addAttribute("lists", lists);
		model.addAttribute("storeRow", rowDto);
		
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
		String loc_code = aDto.getLoc_code();
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
		model.addAttribute("loc_code", loc_code);
		return "store/storeRegForm";
	}
//	@RequestMapping(value="/regiStoreForm.do", method=RequestMethod.GET)
//	public String addStoreForm(HttpSession session, Model model) {
//		AdminDto aDto = (AdminDto) session.getAttribute("loginDto");
//		System.out.println("로그인 된 담당자 DTO : " + aDto);
//		String store_code = storeService.selectMaxStoreCode(aDto.getLoc_code());
//		int cnt = 0;
//		if(store_code == null) {
//			store_code = aDto.getLoc_code()+"_01";
//		}else {
//			String subStoreCode = store_code.substring(store_code.indexOf("_")+1);
//			System.out.println("잘린 store_code : " + subStoreCode);
//			cnt = Integer.parseInt(subStoreCode) + 1;
//			System.out.println(cnt);
//			if(cnt/10 == 0) {
//				store_code = aDto.getLoc_code() + "_0"+cnt;
//			}else {
//				store_code = aDto.getLoc_code() + "_"+cnt;
//			}
//		}
//		System.out.println("완성된 이후 store_code : " + store_code);
//		model.addAttribute("store_code", store_code);
//		return "store/storeRegForm";
//	}
	
	//regiStore.do
	@RequestMapping(value="/regiStore.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String addStore(StoreDto sDto) {
		logger.info("insert Store Controller : {}", sDto);
		boolean isc = storeService.storeInsert(sDto);
		System.out.println("매장 등록 완료 : "+isc);
		JSONObject json = new JSONObject();
		if(isc) {
			json.put("isc", "true");
		}else {
			json.put("isc", "false");
		}
		return json.toString();
	}
//	@RequestMapping(value="/regiStore.do", method=RequestMethod.POST)
//	public String addStore(StoreDto sDto) {
//		logger.info("insert Store Controller : {}", sDto);
//		boolean isc = storeService.storeInsert(sDto);
//		System.out.println("매장 등록 완료 : "+isc);
//		return "redirect:/selStoreList.do";
//	}
	
	
	
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
	
	@RequestMapping(value="/storeModi.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String modStore(StoreDto sDto, Model model) {
		logger.info("modify Store Controller : {}", sDto);
		boolean isc = storeService.storeModify(sDto);
		System.out.println("매장 수정완료 : " + isc);
		StoreDto dto = storeService.storeDetail(sDto.getStore_code());
		JSONObject json = new JSONObject();
		json.put("store_name", dto.getStore_name());
		json.put("store_code", dto.getStore_code());
		json.put("loc_code", dto.getLoc_code());
		json.put("store_phone", dto.getStore_phone());
		json.put("store_address", dto.getStore_address());
		json.put("admin_id", dto.getAdmin_id());
//		model.addAttribute("dto", dto);
		System.out.println("수정된 매장 : " + json.toString());
		return json.toString();
	}
	
	//delStore.do
	@RequestMapping(value="/delStore.do", method=RequestMethod.GET)
	public String deleteStore(String store_code) {
		logger.info("delete Store Controller : {}", store_code);
		boolean isc = storeService.storeDelete(store_code);
		System.out.println("매장 삭제 완료 : " + isc);
		
		boolean delStock = stockService.stockDeleteStore(store_code);
		System.out.println("매장삭제 시 삭제된 매장의 재고 삭제 : " + delStock);
		
		
		return "redirect:/selStoreList.do";
	}
	
	@RequestMapping(value="/storePaging.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	//매장정보 페이징 처리
	public String pagingStore(Model model, HttpSession session, PagingDto pDto) {
		JSONObject json = null;
		AdminDto aDto = (AdminDto) session.getAttribute("loginDto");
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		pDto.setTotal(storeService.storeListRow(aDto.getAdmin_id()+""));
		map.put("admin_id", aDto.getAdmin_id());
		map.put("start", pDto.getStart());
		map.put("end", pDto.getEnd());
		json = objectJson(storeService.storeListPaging(map), pDto);
		
		session.removeAttribute("storeRow");
		session.setAttribute("storeRow", pDto);
		return json.toString();
	}
	
	// JSONArray 형태로 페이징 처리된 매장 리스트를 담을 예정
	private JSONObject objectJson(List<StoreDto> lists , PagingDto storeRow) {
		JSONObject json = new JSONObject(); // 최종적으로 담는애는 여긴데
		JSONArray jLists = new JSONArray(); // 어레이리스트를 담을때는 여기에
		JSONObject jList = null; // 그냥 얘는 제이슨 타입으로
		
		for (StoreDto dto : lists) {
			jList = new JSONObject();
			jList.put("store_code", dto.getStore_code());
			jList.put("store_name", dto.getStore_name());
			jList.put("store_address", dto.getStore_address());
			jList.put("store_phone", dto.getStore_phone());
			
			jLists.add(jList);
		}
		
		jList = new JSONObject();
		jList.put("pageList",storeRow.getPageList());
		jList.put("index",storeRow.getIndex());
		jList.put("pageNum",storeRow.getPageNum());
		jList.put("listNum",storeRow.getListNum());
		jList.put("total",storeRow.getTotal());
		jList.put("count",storeRow.getCount());
		
		json.put("lists", jLists);
		json.put("storeRow", jList);
		
		return json;
	}
}
