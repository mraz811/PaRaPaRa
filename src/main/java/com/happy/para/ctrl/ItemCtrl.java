package com.happy.para.ctrl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happy.para.dto.ItemDto;
import com.happy.para.model.Item_IService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class ItemCtrl {

	private Logger logger = LoggerFactory.getLogger(ItemCtrl.class);
	
	@Autowired
	private Item_IService itemService;
	
	// 품목 전체 조회
	@RequestMapping(value="/selItemList.do", method=RequestMethod.GET)
	public String itemList(Model model) {
		List<ItemDto> lists = itemService.itemList();
		logger.info("Select ItemList Controller : {}",lists);
		model.addAttribute("itemLists", lists);
		return "item/itemList";
	}
	
	// 품목 등록 창으로 이동
	@RequestMapping(value="/regItem.do", method=RequestMethod.GET)
	public String addToItemForm() {
		return "item/itemRegForm";
	}
	
	// 품목이 등록되었을 때 새 창을 닫고 품목 전체 조회창을 새로고침하기 때문에 ajax처리 
	@RequestMapping(value="/addItem.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String addToItem(String item_name, String item_price) {
		
		logger.info("Add Item Controller : {}, {}",item_name,item_price);
		
		ItemDto dto = new ItemDto();
		
		dto.setItem_name(item_name);
		dto.setItem_price(Integer.parseInt(item_price));
		logger.info("Add Item Controller setDto : {}",dto);
		boolean isc = itemService.itemInsert(dto);
		JSONObject json = new JSONObject();
		if(isc) {
			json.put("isc", "true");
		}else {
			json.put("isc", "false");
		}
		return json.toString();
		
	}
	
	// 품목 수정 창을 띄워줌, 수정할 품목의 정보를 가져가기 위한 쿼리를 실행
	@RequestMapping(value="/itemModiForm.do", method=RequestMethod.GET)
	public String modItemForm(String item_seq, Model model) {
		logger.info("Modify Item Controller : {}", item_seq);
		List<ItemDto> lists = itemService.itemList();
		ItemDto dto = itemService.itemDetail(item_seq);
		
		JSONObject json = new JSONObject(); // 최종적으로 담는애는 여긴데
		JSONArray jLists = new JSONArray(); // 어레이리스트를 담을때는 여기에
		JSONObject jList = null; // 그냥 얘는 제이슨 타입으로
		
		for (ItemDto iDto : lists) {
			jList = new JSONObject();
			if (!dto.getItem_name().equals(iDto.getItem_name())) {
				jList.put("item_name", iDto.getItem_name());
			}
			
			jLists.add(jList);
		}
		json.put("nameList", jLists);
		System.out.println("품목 이름 전체 조회"+json.toString());
		System.out.println("품목 상세 조회 : " + dto);
		model.addAttribute("nameListJson", json.toString());
		model.addAttribute("dto", dto);
		
		return "item/itemModForm";
	}
	
	// 품목이 수정된 후 띄워진 창을 닫고 전체조회 페이지를 새로고침하기위해 ajax 처리
	@RequestMapping(value="/itemModi.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String modItem(String item_seq, String item_price, String item_name) {
		ItemDto dto = new ItemDto();
		dto.setItem_seq(Integer.parseInt(item_seq));
		dto.setItem_price(Integer.parseInt(item_price));
		dto.setItem_name(item_name);
		logger.info("Add Item Controller setDto : {}", dto);
		
		boolean isc = itemService.itemModify(dto);
		if (isc) {
			System.out.println("modify 완료");
		}else {
			System.out.println("modify 실패");
		}
		
		
		return "";
	}
	
	// 품목이 삭제된 후 품목 전체를 다시 화면에 출력해주기 위한 ajax 처리
	@RequestMapping(value="/delItem.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String deleteItem(String item_seq) {
		logger.info("delete Item Controller : {}",item_seq);
		
		boolean isc = itemService.itemDelete(item_seq);
		if(isc) {
			System.out.println("delete 완료");
		}else {
			System.out.println("delete 완료");
		}
		JSONObject json = new JSONObject();
		JSONArray jLists = new JSONArray();
		JSONObject jList = null;
		List<ItemDto> lists = itemService.itemList();
		for (ItemDto dto : lists) {
			jList = new JSONObject();
			jList.put("item_seq", dto.getItem_seq());
			jList.put("item_name", dto.getItem_name());
			jList.put("item_price", dto.getItem_price());
			
			jLists.add(jList);
		}
		json.put("lists", jLists);
		return json.toString();
	}
	
	
	// 품목명 검색 후 화면에 출력 
	@RequestMapping(value="/searchItem.do", method=RequestMethod.GET, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String searchItemList(String item_name) {
		logger.info("search item List controller : {}" + item_name);
		// 품목 검색 시 나온 JSON 타입의 List들을 하나로 묶어 보내기 위해 선언
		JSONObject json = new JSONObject();
		// 품목 검색 시 나온 List를 JSON 형태로 만들기 위해 선언
		JSONArray jLists = new JSONArray();
		// JSONArray에 쿼리 실행 후 나온 ItemDto 값 하나하나를 JSONArray에 담기위해 선언
		JSONObject jList = null;
		// 품목 검색
		List<ItemDto> lists = itemService.itemSearchList(item_name.trim());
		// 검색된 품목의 갯수(검색된 값이 없을 시 처리를 위하여)
		int countList = lists.size();
		System.out.println(lists);
		for (ItemDto dto : lists) {
			// JSONObject 객체를 계속해서 새로 만들기 위해
			// 객체를 새로 만들지 않으면 key가 같기 때문에 맨 마지막 값만이 JSONArray에 입력됨
			jList = new JSONObject();
			jList.put("item_seq", dto.getItem_seq());
			jList.put("item_name", dto.getItem_name());
			jList.put("item_price", dto.getItem_price());
			
			// 검색시 나온 JSONObject 객체 하나하나를 JSONArray에 담아줌
			jLists.add(jList);
		}
		
		// JSONArray에 담긴 품목 검색된 값들과 검색된 품목 갯수를 담아줌
		json.put("lists", jLists);
		json.put("count", countList);
		return json.toString();
	}
	
	// 품목 등록 시 중복되는 품목명이 없도록 하기위해
	@RequestMapping(value="/itemValid.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String itemNameChk(String item_name) {
		// 입력한 품목명을 조건으로 넣어 count(*)가 1일 때 중복되는 이름이 있다는 것임
		// 0일 때 사용 가능, 0이 아닐 때 사용 불가능 return
		// return 값으로 화면에서 처리
		int n = itemService.itemNameChk(item_name.trim());
		return (n==0)? "사용 가능":"사용 불가능";
	}
	
	
	
}
