package com.happy.para.model;

import java.util.List;

import com.happy.para.dto.NoticeDto;
import com.happy.para.dto.PagingDto;
import com.happy.para.dto.ReplyDto;

public interface Notice_IService {

// Notice
	
	// 공지사항 등록
	public boolean noticeWrite(NoticeDto dto);
	// 공지사항 수정
	public boolean noticeModify(NoticeDto dto);
	// 공지사항 삭제
	public boolean noticeDelete(String notice_seq);	
	// 공지사항 조회(Paging)
	public List<NoticeDto> noticeList(PagingDto dto);
	// 공지사항 조회(Paging 글 목록 Total)
	public int noticeListRow();
	// 공지사항 상세 조회
	public NoticeDto noticeDetail(String notice_seq);

// Notice_reply

	// 공지사항 댓글 조회
	public List<ReplyDto> replyList(String notice_seq);
	// 공지사항 댓글 등록
	public boolean replyWrite(ReplyDto dto);
	// 공지사항 댓글 삭제
	public boolean replyDelete(String reply_seq);
	
}
