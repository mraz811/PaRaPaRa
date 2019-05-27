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
	
	
	@RequestMapping(value="/selItemList.do", method=RequestMethod.GET)
	public String itemList(Model model) {
		List<ItemDto> lists = itemService.itemList();
		logger.info("Select ItemList Controller : {}",lists);
		model.addAttribute("itemLists", lists);
		return "item/itemList";
	}
	
	@RequestMapping(value="/regItem.do", method=RequestMethod.GET)
	public String addToItemForm() {
		return "item/itemRegForm";
	}
	
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
	
	@RequestMapping(value="/itemModiForm.do", method=RequestMethod.GET)
	public String modItemForm(String item_seq, Model model) {
		logger.info("Modify Item Controller : {}", item_seq);
		
		ItemDto dto = itemService.itemDetail(item_seq);
		System.out.println("품목 상세 조회 : " + dto);
		model.addAttribute("dto", dto);
		
		return "item/itemModForm";
	}
	
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
	
	
}
