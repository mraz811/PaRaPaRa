package com.happy.para.ctrl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.happy.para.dto.ItemDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.dto.StockDto;
import com.happy.para.model.Item_IService;
import com.happy.para.model.Stock_IService;


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
		
		// 새 매장이면 등록된 게 없어서 아이템 목록 가져온 다음
		// 화면에 뿌리고 인풋을 그 매장으로 새로 넣어주어야 한다.

		// true 면 재고 데이터가 있는 매장, false 는 데이터가 없는 매장.(새로 넣어줘야 함)
		if(storeLists.contains(store_code)) {
			return "redirect:/selStockOne.do?store_code="+store_code;
		}else {
			// 새매장일때 리스트 뿌려주는 테스트
			List<StockDto> lists = stockSer.stockOne(store_code);
			List<ItemDto> itemList = itemSer.itemList();
			model.addAttribute("itemList", itemList);
			model.addAttribute("lists", lists);
		}
		
//		List<StockDto> lists = ser.stockOne(store_code);
//		model.addAttribute("lists", lists);
		
		model.addAttribute("store_code", store_code);
		
		return "stock/newStockList";
	}
	
	@RequestMapping(value="/selStockOne.do", method=RequestMethod.GET)
	public String StockListOne(Model model, StockDto dto) {
	
		String store_code = dto.getStore_code();
		
// 아이템 Dto/ 서비스 수정
		
		List<ItemDto> itemList = itemSer.itemList();
		
		List<StockDto> lists = stockSer.stockOne(store_code);
		
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
//			if(dto.getSlists().get(i).getStock_seq() == "") {
			if(dto.getSlists().get(i).getItem_name() == null) {
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
		return "redirect:/selStockOne.do?store_code="+store_code;
	}
	
	@RequestMapping(value="/addStockItem.do", method=RequestMethod.POST)
	public String StockAdd(StockDto dto, ItemDto iDto) {
												
		// 화면에 뿌려진 store_code 가져옴 , 굳이 리스트 화면에 뿌리지 않고 세션에서 가져오면 됨
		String store_code = dto.getStore_code();

		dto.setStore_code(store_code);

		for (int i = 1; i < dto.getSlists().size(); i++) {
				dto.setStock_name(iDto.getIlists().get(i).getItem_name());
				dto.setStock_qty(dto.getSlists().get(i).getStock_qty());			
				stockSer.stockAdd(dto);
		}
		return "redirect:/selStockOne.do?store_code="+store_code;
	}
	
	@RequestMapping(value="/delStock.do", method=RequestMethod.GET)
	public String stockDel(StockDto dto, String stock_seq) {
		
		String store_code = dto.getStore_code();
		
		stockSer.stockDelete(stock_seq);
		
		return "redirect:/selStockOne.do?store_code="+store_code;
	}

	
}
