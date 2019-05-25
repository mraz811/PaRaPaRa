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
	
	@RequestMapping(value="/addItem.do", method=RequestMethod.POST)
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
	
	@RequestMapping(value="/itemModi.do", method=RequestMethod.GET)
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
	
	@RequestMapping(value="/delItem.do", method=RequestMethod.GET)
	public String deleteItem(String item_seq) {
		logger.info("delete Item Controller : {}",item_seq);
		
		boolean isc = itemService.itemDelete(item_seq);
		if(isc) {
			System.out.println("delete 완료");
		}else {
			System.out.println("delete 완료");
		}
		
		return "";
	}
	
	
}
