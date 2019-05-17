package com.happy.para.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.ChatDto;
import com.happy.para.dto.OwnerDto;

@Service
public class Chat_ServiceImpl implements Chat_IService {
	
	@Autowired
	private Chat_IDao chat_IDao;
	
	private Logger logger = LoggerFactory.getLogger(Chat_ServiceImpl.class);
	
	@Override
	public List<OwnerDto> selectOwner(String admin_id) {
		logger.info("selectOwner Service : {}", admin_id);
		return chat_IDao.selectOwner(admin_id);
	}

	@Override
	public AdminDto selectAdmin(String admin_id) {
		logger.info("selectAdmin Service : {}", admin_id);
		return chat_IDao.selectAdmin(admin_id);
	}

	@Override
	public boolean updateChatContent(ChatDto dto) {
		logger.info("updateChatContent Service : {}", dto);
		return chat_IDao.updateChatContent(dto);
	}

	@Override
	public ChatDto selectChatRoom(String chat_title) {
		logger.info("selectOwner Service : {}", chat_title);
		return chat_IDao.selectChatRoom(chat_title);
	}

	@Override
	public boolean createChatRoom(String chat_title) {
		logger.info("selectOwner Service : {}", chat_title);
		return chat_IDao.createChatRoom(chat_title);
	}
	
	
	
}
