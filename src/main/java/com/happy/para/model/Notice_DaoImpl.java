package com.happy.para.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.NoticeDto;
import com.happy.para.dto.PagingDto;
import com.happy.para.dto.ReplyDto;

@Repository
public class Notice_DaoImpl implements Notice_IDao {

	@Autowired
	SqlSessionTemplate sqlSession;

// notice	
	
	@Override
	public boolean noticeWrite(NoticeDto dto) {
		int n = sqlSession.insert("para.notice.noticeWrite", dto);
		return n>0?true:false;
	}

	@Override
	public boolean noticeModify(NoticeDto dto) {
		int n = sqlSession.update("para.notice.noticeModify", dto);
		return n>0?true:false;
	}

	@Override
	public boolean noticeDelete(String notice_seq) {
		int n = sqlSession.update("para.notice.noticeDelete", notice_seq);
		return n>0?true:false;
	}

	@Override
	public List<NoticeDto> noticeList(PagingDto dto) {
		return sqlSession.selectList("para.notice.noticeList", dto);
	}

	@Override
	public int noticeListRow() {
		return sqlSession.selectOne("para.notice.noticeListRow");
	}

	@Override
	public NoticeDto noticeDetail(String notice_seq) {
		return sqlSession.selectOne("para.notice.noticeDetail", notice_seq);
	}

	
// notice_reply
	
	
	@Override
	public List<ReplyDto> replyList(String notice_seq) {
		return sqlSession.selectList("para.reply.replyList", notice_seq);
	}

	@Override
	public boolean replyWrite(ReplyDto dto) {
		int n = sqlSession.insert("para.reply.replyWrite", dto);
		return n>0?true:false;
	}

	@Override
	public boolean replyDelete(String reply_seq) {
		int n = sqlSession.delete("para.reply.replyDelete", reply_seq);
		return n>0?true:false;
	}
	
	
	
	
}
