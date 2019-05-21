package com.happy.para.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.happy.para.dto.StockDto;
import com.happy.para.model.Stock_IService;


@Controller
public class StockCtrl {
	
	// 이거 수정 안함!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 
/*	@Autowired
	private Stock_IService stockSer;

	@RequestMapping(value="/stockList.do", method=RequestMethod.GET)
	public String StockList(Model model) {
		
		// 임시 테스트
		String store_code = "STORE123";
		
		// 전체 store_code 조회
		List<String> storeLists = stockSer.selStore();
		
		// 새 매장이면 등록된 게 없어서 아이템 목록 가져온 다음
		// 화면에 뿌리고 인풋을 그 매장으로 새로 넣어주어야 한다.

		// true 면 재고 데이터가 있는 매장, false 는 데이터가 없는 매장.(새로 넣어줘야 함)
		if(storeLists.contains(store_code)) {
			return "redirect:/stockListOne.do?store_code="+store_code;
		}else {
			// 새매장일때 리스트 뿌려주는 테스트
			List<StockDto> lists = ser.stockOne(store_code);
			List<ItemDto> itemList = ser.itemList();
			model.addAttribute("itemList", itemList);
			model.addAttribute("lists", lists);
		}
		
//		List<StockDto> lists = ser.stockOne(store_code);
//		model.addAttribute("lists", lists);
		
		model.addAttribute("store_code", store_code);
		
		return "newStockList";
	}
	
	@RequestMapping(value="/stockListOne.do", method=RequestMethod.GET)
	public String StockListOne(Model model, StockDto dto) {
	
		String store_code = dto.getStore_code();
		
// 아이템 Dto/ 서비스 수정
		
		List<ItemDto> itemList = ser.itemList();
		
		List<StockDto> lists = ser.stockOne(store_code);
		
		model.addAttribute("lists", lists);
		model.addAttribute("itemList", itemList);
		model.addAttribute("store_code", store_code);
		
		 
		return "stockList";
	}
	
	@RequestMapping(value="/stockQtyModi.do", method=RequestMethod.POST)
	public String StockModi(StockDto dto, ItemDto iDto) {
												
		// 화면에 뿌려진 store_code 가져옴 , 굳이 리스트 화면에 뿌리지 않고 세션에서 가져오면 됨
		String store_code = dto.getStore_code();

		dto.setStore_code(store_code);

		for (int i = 1; i < dto.getSlists().size(); i++) {
			if(dto.getSlists().get(i).getStock_seq() == "") {
				dto.setStock_name(dto.getSlists().get(i).getStock_name());
				dto.setStock_qty(dto.getSlists().get(i).getStock_qty());			
				stockSer.stockAdd(dto);
			}else {
				dto.setStock_seq(dto.getSlists().get(i).getStock_seq());
	            dto.setStock_name(dto.getSlists().get(i).getStock_name());
				dto.setStock_qty(dto.getSlists().get(i).getStock_qty());			
				stockSer.stockModify(dto);				
			}
		}
		return "redirect:/stockListOne.do?store_code="+store_code;
	}
	
	@RequestMapping(value="/stockQtyAdd.do", method=RequestMethod.POST)
	public String StockAdd(StockDto dto, ItemDto iDto) {
												
		// 화면에 뿌려진 store_code 가져옴 , 굳이 리스트 화면에 뿌리지 않고 세션에서 가져오면 됨
		String store_code = dto.getStore_code();

		dto.setStore_code(store_code);

		for (int i = 1; i < dto.getSlists().size(); i++) {
				dto.setStock_name(iDto.getIlists().get(i).getItem_name());
				dto.setStock_qty(dto.getSlists().get(i).getStock_qty());			
				stockSer.stockAdd(dto);
		}
		return "redirect:/stockListOne.do?store_code="+store_code;
	}
	
	@RequestMapping(value="/stockDel.do", method=RequestMethod.GET)
	public String stockDel(StockDto dto, String stock_seq) {
		
		String store_code = dto.getStore_code();
		
		stockSer.stockDelete(stock_seq);
		
		return "redirect:/stockListOne.do?store_code="+store_code;
	}
	*/
	
}
