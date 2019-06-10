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

import com.happy.para.dto.ItemDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.dto.StockDto;
import com.happy.para.model.Item_IService;
import com.happy.para.model.Stock_IService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class StockCtrl {

	 
	@Autowired
	private Stock_IService stockSer;
	
	@Autowired
	private Item_IService itemSer;

	@RequestMapping(value="/selStock.do", method=RequestMethod.GET)
	public String StockList(Model model, HttpSession session) {
		
		// 임시 테스트
		OwnerDto oDto = (OwnerDto)session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		
		System.out.println("로그인한 oDto : "+oDto);
		System.out.println("로그인한 store_code : "+store_code);
		
		// 전체 store_code 조회
		List<String> storeLists = stockSer.selStore();
		
		
		// true 면 재고 데이터가 있는 매장, false 는 데이터가 없는 매장.(새로 넣어줘야 함)
		if(storeLists.contains(store_code)) {
			return "redirect:/selStockOne.do?store_code="+store_code;
		}else {
			// 새매장
			ItemDto iDto = new ItemDto();
			StockDto dto = new StockDto();

			List<StockDto> lists = stockSer.stockOne(store_code);
			List<ItemDto> itemList = itemSer.itemList();
			model.addAttribute("itemList", itemList);
			model.addAttribute("lists", lists);
			
			System.out.println("itemList > " + itemList);
			System.out.println("store_code > "+store_code);
			
			dto.setStore_code(store_code);
			
			for (int i = 0; i < itemList.size(); i++) {
				dto.setStock_name(itemList.get(i).getItem_name());
				dto.setStock_qty(0);			
				stockSer.stockAdd(dto);
			}

			
//			System.out.println("store_code1 > "+store_code);
			model.addAttribute("store_code", store_code);
			
		}
		
		return "redirect:/selStockOne.do";
	}
	
	@RequestMapping(value="/selStockOne.do", method=RequestMethod.GET)
	public String StockListOne(Model model, StockDto dto) {
	
		String store_code = dto.getStore_code();
		
		List<ItemDto> itemList = itemSer.itemList();
		List<StockDto> lists = stockSer.stockOne(store_code);
		
		System.out.println("selStockOne의 lists >"+lists);

		dto.setStore_code(store_code);

		model.addAttribute("lists", lists);
		model.addAttribute("itemList", itemList);
		model.addAttribute("store_code", store_code);
		
		return "stock/stockList";
	}
	
	@RequestMapping(value="/stockModi.do", method=RequestMethod.POST)
	public String StockModi(StockDto dto, ItemDto iDto) {
												
		// 화면에 뿌려진 store_code 가져옴 , 굳이 리스트 화면에 뿌리지 않고 세션에서 가져오면 됨
		String store_code = dto.getStore_code();

		dto.setStore_code(store_code);

		for (int i = 1; i < dto.getSlists().size(); i++) {
			if(dto.getSlists().get(i).getStock_seq() == 0) {
				dto.setStock_name(dto.getSlists().get(i).getStock_name());
				dto.setStock_qty(dto.getSlists().get(i).getStock_qty());			
				stockSer.stockAdd(dto);
				System.out.println("새로 추가된 품목"+dto.getSlists().get(i).getStock_name());
			}else {
				dto.setStock_seq(dto.getSlists().get(i).getStock_seq());
	            dto.setStock_name(dto.getSlists().get(i).getStock_name());
				dto.setStock_qty(dto.getSlists().get(i).getStock_qty());			
				stockSer.stockModify(dto);				
			}
		}
		return "redirect:/selStockOne.do?store_code="+store_code;
	}
	
	@RequestMapping(value="/delStock.do", method=RequestMethod.GET)
	public String stockDel(String stock_seq, String store_code) {
		
//		String store_code = dto.getStore_code();
		
		stockSer.stockDelete(stock_seq);
		
		System.out.println("delStock_store_code : "+store_code);

		return "redirect:/selStockOne.do?store_code="+store_code;
	}

	@RequestMapping(value="/searchStock.do", method=RequestMethod.GET, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String searchItemList(String stock_name, String store_code) {
		JSONObject json = new JSONObject();
		JSONArray jLists = new JSONArray();
		JSONObject jList = null;
		
		System.out.println("store_code > "+store_code);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("store_code", store_code);
		map.put("stock_name", stock_name);
//		map.put("item_name", item_name);
		
		List<StockDto> lists = stockSer.stockSearchList(map);
		int countList = lists.size();
		System.out.println(lists);
		for (StockDto dto : lists) {
			jList = new JSONObject();
			jList.put("stock_seq", dto.getStock_seq());
			jList.put("stock_name", dto.getStock_name());
			jList.put("stock_qty", dto.getStock_qty());
			jList.put("item_delflag", dto.getItem_delflag());
			jList.put("stock_delflag", dto.getStock_delflag());
			
			System.out.println("item_delflag > "+dto.getItem_delflag());
			System.out.println("stock_delflag > "+dto.getStock_delflag());
			
			jLists.add(jList);
		}
		json.put("lists", jLists);
		json.put("count", countList);
		return json.toString();
	}

}
