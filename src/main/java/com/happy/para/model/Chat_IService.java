package com.happy.para.model;

import java.util.List;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.ChatDto;
import com.happy.para.dto.FileDto;
import com.happy.para.dto.OwnerDto;

public interface Chat_IService {
	
	// 담당자가 채팅할 업주를 선택하기 위한 담당하는 업주 조회
	public List<OwnerDto> selectOwner(String admin_id);
	
	// 업주가 채팅할 담당자를 선택하기 위한 자신의 담당자 조회
	public AdminDto selectAdmin(String admin_id);
	
	// 채팅 내용을 업데이트
	public boolean updateChatContent(ChatDto dto);
	
	// 채팅방 조회
	public ChatDto selectChatRoom(String chat_title);
	
	// 채팅방 생성
	public boolean createChatRoom(String chat_title);
	
	// 파일 업로드
	public boolean uploadFile(FileDto dto);
	
	// 파일 조회
	public List<FileDto> selectFileList(String chat_seq);
	
}
