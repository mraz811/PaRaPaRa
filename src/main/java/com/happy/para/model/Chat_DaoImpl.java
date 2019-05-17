package com.happy.para.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.ChatDto;
import com.happy.para.dto.OwnerDto;

@Repository
public class Chat_DaoImpl implements Chat_IDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "para.chat.";
	
	@Override
	public List<OwnerDto> selectOwner(String admin_id) {
		return sqlSession.selectList(NS+"selectOwner", admin_id);
	}

	@Override
	public AdminDto selectAdmin(String admin_id) {
		return sqlSession.selectOne(NS+"selectAdmin", admin_id);
	}

	@Override
	public boolean updateChatContent(ChatDto dto) {
		return sqlSession.update(NS+"updateChatContent", dto) > 0 ? true : false;
	}

	@Override
	public ChatDto selectChatRoom(String chat_title) {
		return sqlSession.selectOne(NS+"selectChatRoom", chat_title);
	}

	@Override
	public boolean createChatRoom(String chat_title) {
		return sqlSession.insert(NS+"createChatRoom", chat_title) > 0 ? true : false;
	}

}
