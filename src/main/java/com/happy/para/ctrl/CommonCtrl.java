package com.happy.para.ctrl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.ChatDto;
import com.happy.para.dto.FileDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.model.Chat_IService;

@Controller
public class CommonCtrl {
	
	private Logger logger = LoggerFactory.getLogger(CommonCtrl.class);
	
	@Autowired
	private Chat_IService chatService;
	
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
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
		System.out.println("if문 밖에서 찍어본 Store_code : " + store_codeTwo);
		System.out.println("if문 밖에서 찍어본 chatDto : " + chatDto);
		System.out.println("if문 밖에서 찍어본 targer : " + id);
		model.addAttribute("store_code", store_codeTwo);
		model.addAttribute("chatDto", chatDto);
		model.addAttribute("target", id);
		return "common/socket";
	}
	
	@RequestMapping(value="/chatContentUpdate.do", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String updateChat(String chatTitle, String content) {
		ChatDto dto = new ChatDto();
		// 받은 chatmember 값을 정렬하기 위해 배열로 만듦
		dto.setChat_title(chatTitle);
		dto.setChat_content(content);

		System.out.println(content);

		boolean isc = chatService.updateChatContent(dto);
		System.out.println(isc);
		return isc == true ? "성공" : "실패";
	}
	
	@RequestMapping(value="/regiFile.do", method=RequestMethod.POST, produces="application/text; charset-utf-8;")
	@ResponseBody
	public String fileUpload(FileDto dto,  MultipartHttpServletRequest mtsRequest, String chat_seq) throws IOException {
		logger.info("file Upload Controller");
		boolean isc = false;
		Iterator<String> itr = mtsRequest.getFileNames();
		System.out.println("파일이름 : " + itr);
		String originalName = null;
		while(itr.hasNext()) {
			MultipartFile file = mtsRequest.getFile(itr.next());
			originalName = file.getOriginalFilename();
			String savedName = "";
			System.out.println("Orginal Name : " + originalName);
			// 이름이 겹치지 않기위해 랜덤 생성
			UUID uuid = UUID.randomUUID();
			savedName = uuid.toString()+"_"+originalName;
			File dir = new File(uploadPath);
			File target = new File(uploadPath, savedName);
			// 폴더가 없다면 폴더를 생성
			if(!dir.exists()) {
				dir.mkdirs();
			}
			
			// 파일을 서버에 저장
			FileCopyUtils.copy(file.getBytes(), target);
			
			// argument로 받아온 dto에 파일 이름이 들어가 있지 않아서 직접 set
			dto.setFile_rname(originalName);
			dto.setFile_tname(savedName);
			isc = chatService.uploadFile(dto);
			System.out.println("파일업로드 성공 : " + isc);
		}
		return uploadPath + "\\" + originalName;
	}
	
}
