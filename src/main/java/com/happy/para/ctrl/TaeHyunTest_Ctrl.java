package com.happy.para.ctrl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.ChatDto;
import com.happy.para.dto.ItemDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.model.Chat_IService;
import com.happy.para.model.Item_IService;

@Controller
public class TaeHyunTest_Ctrl {
	
	private Logger logger = LoggerFactory.getLogger(TaeHyunTest_Ctrl.class);
	
	@Autowired
	private Item_IService itemService;
	
	@Autowired
	private Chat_IService chatService;
	
	@RequestMapping(value="/selItemList.do", method=RequestMethod.GET)
	public String selectItemList() {
		List<ItemDto> lists = itemService.itemList();
		logger.info("Select ItemList Controller : {}",lists);
		return "";
	}
	
	@RequestMapping(value="/addItem.do", method=RequestMethod.GET)
	public String addToItem(String item_name, String item_price) {
		
		logger.info("Add Item Controller : {}, {}",item_name,item_price);
		
		ItemDto dto = new ItemDto();
		
		dto.setItem_name(item_name);
		dto.setItem_price(Integer.parseInt(item_price));
		logger.info("Add Item Controller setDto : {}",dto);
		boolean isc = itemService.itemInsert(dto);
		if (isc) {
			System.out.println("insert 완료");
		}else {
			System.out.println("insert 실패");
		}
		
		return "";
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
	
	@RequestMapping(value="/chatList.do", method=RequestMethod.GET)
	public String selectChatList(String auth, Model model, HttpSession session) {
		if(auth.equalsIgnoreCase("A")) {
			AdminDto aDto = (AdminDto) session.getAttribute("loginDto");
			List<OwnerDto> lists = chatService.selectOwner(aDto.getAdmin_id()+"");
			model.addAttribute("lists", lists);
		}
		if(auth.equalsIgnoreCase("U")) {
			OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
			AdminDto aDto = chatService.selectAdmin(oDto.getAdmin_id()+"");
			model.addAttribute("adminDto", aDto);
		}
		return "common/chattingList";
	}
	
	@RequestMapping(value="/socketOpen.do", method=RequestMethod.GET)
	public String chattingRoom(String id, String auth, String store_code, HttpSession session, Model model) {
		System.out.println("채팅방 조회 및 생성을 위한 업주/담당자 ID : " + id);
		System.out.println("채팅방 조회 및 생성을 위한 업주/담당자 auth : " + auth);
		ChatDto chatDto = null;
		String store_codeTwo = "";
		if(auth.equalsIgnoreCase("A")) {
			store_codeTwo = store_code;
			System.out.println("담당자로 로그인 시 StoreCode : " + store_codeTwo);
			ChatDto cDto = chatService.selectChatRoom(store_code);
			if(cDto == null) {
				boolean isc = chatService.createChatRoom(store_code);
				System.out.println("채팅방이 없을 시 생성 완료 : " + isc);
				chatDto = chatService.selectChatRoom(store_code);
				System.out.println("채팅방 생성 후 맨들어 진 ChatDto : " + chatDto);
			}else {
				System.out.println("채팅방이 있을 시 ChatDto : " + cDto);
				chatDto = chatService.selectChatRoom(store_code);
			}
		}
		if(auth.equalsIgnoreCase("U")) {
			OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
			
			store_codeTwo = oDto.getStore_code();
			System.out.println("업주로 로그인 시 StoreCode : " + store_codeTwo);
			System.out.println("세션에 담긴 oDto 정보 : " + oDto);
			ChatDto cDto = chatService.selectChatRoom(store_codeTwo);
			if(cDto == null) {
				boolean isc = chatService.createChatRoom(store_codeTwo);
				System.out.println("채팅방이 없을 시 생성 완료 : " + isc);
				chatDto = chatService.selectChatRoom(store_codeTwo);
				System.out.println("채팅방 생성 후 맨들어 진 ChatDto : " + chatDto);
				
			}else {
				System.out.println("채팅방이 있을 시 ChatDto : " + cDto);
				chatDto = chatService.selectChatRoom(store_codeTwo);
			}
		}		
		chatDto.setChat_content("하위");
		System.out.println("if문 밖에서 찍어본 Store_code : " + store_codeTwo);
		model.addAttribute("store_code", store_codeTwo);
		model.addAttribute("chatDto", chatDto);
		model.addAttribute("target", id);
		return "common/socket";
	}
	
	
}
