package com.happy.para.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.para.dto.NoticeDto;
import com.happy.para.dto.PagingDto;
import com.happy.para.dto.ReplyDto;

@Service
public class Notice_ServiceImpl implements Notice_IService {

	@Autowired
	Notice_IDao noticeDao;

	@Override
	public boolean noticeWrite(NoticeDto dto) {
		return noticeDao.noticeWrite(dto);
	}

	@Override
	public boolean noticeModify(NoticeDto dto) {
		return noticeDao.noticeModify(dto);
	}

	@Override
	public boolean noticeDelete(String notice_seq) {
		return noticeDao.noticeDelete(notice_seq);
	}

	@Override
	public List<NoticeDto> noticeList(PagingDto dto) {
		return noticeDao.noticeList(dto);
	}

	@Override
	public int noticeListRow() {
		return noticeDao.noticeListRow();
	}

	@Override
	public NoticeDto noticeDetail(String notice_seq) {
		return noticeDao.noticeDetail(notice_seq);
	}

	@Override
	public List<ReplyDto> replyList(String notice_seq) {
		return noticeDao.replyList(notice_seq);
	}

	@Override
	public boolean replyWrite(ReplyDto dto) {
		return noticeDao.replyWrite(dto);
	}

	@Override
	public boolean replyDelete(String reply_seq) {
		return noticeDao.replyDelete(reply_seq);
	}
	
	
	
	
}
