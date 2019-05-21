package com.happy.para.ctrl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.happy.para.dto.NoticeDto;
import com.happy.para.dto.PagingDto;
import com.happy.para.dto.ReplyDto;
import com.happy.para.model.Calendar_IService;
import com.happy.para.model.Notice_IService;

@Controller
public class NoticeCtrl {

	/*

	@Autowired
	private Notice_IService noticeSer;
	
	
	@RequestMapping(value="/noticeList.do", method=RequestMethod.GET)
	public String noticeList(HttpSession session, Model model) {
		
		PagingDto rowDto = null;
		List<NoticeDto> lists = null;
		
		if(session.getAttribute("row")== null) {
			rowDto = new PagingDto();
		}else {
			rowDto = (PagingDto) session.getAttribute("row");
		}
		
		rowDto.setTotal(noticeSer.noticeListRow());

		lists = noticeSer.noticeList(rowDto);
		
		model.addAttribute("lists", lists);
		model.addAttribute("row", rowDto);
		
		return "noticeList";
	}
	
	@RequestMapping(value="/noticeWriteForm.do", method=RequestMethod.GET)
	public String noticeWriteForm() {
		return "noticeWriteForm";
	}
	
	@RequestMapping(value="/write.do", method=RequestMethod.POST)
	public String noticeWrite(NoticeDto dto) {
		dto.setNotice_id("작성자2"); // 나중에 세션으로 가져올거임
		boolean isc = noticeSer.noticeWrite(dto);
		
		return isc? "redirect:/noticeList.do" : "noticeWriteForm";
	}
	
	@RequestMapping(value="/noticeDetail.do", method=RequestMethod.GET)
	public String noticeDetail(Model model, String notice_seq) {
		
		NoticeDto dto = noticeSer.noticeDetail(notice_seq);
		List<ReplyDto> Rlists = noticeSer.replyList(notice_seq);
		
		model.addAttribute("dto", dto);
		model.addAttribute("Rlists", Rlists);
		
		return "noticeDetail";
	}
	
	@RequestMapping(value="/noticeModifyForm.do", method=RequestMethod.POST)
	public String noticeModifyForm(Model model, NoticeDto dto) {
		
		String notice_seq = dto.getNotice_seq();
		
		NoticeDto Ndto = noticeSer.noticeDetail(notice_seq);
		model.addAttribute("dto", Ndto);
		
		return "noticeModifyForm";
	}
	
	@RequestMapping(value="/noticeModify.do", method=RequestMethod.POST)
	public String noticeModify(NoticeDto dto) {
		
		boolean isc = noticeSer.noticeModify(dto);
		
		return isc? "redirect:/noticeList.do" : "noticeModifyForm";
	}
	
	@RequestMapping(value="/noticeDelete.do", method=RequestMethod.POST)
	public String noticeDelete(String notice_seq) {
		
		boolean isc = noticeSer.noticeDelete(notice_seq);
		
		return isc? "redirect:/noticeList.do" : "noticeList";
	}
	
	@RequestMapping(value="/replyWrite.do", method=RequestMethod.POST)
	public String replyWrite(ReplyDto dto) {
		
//		System.out.println(dto.getNotice_seq());
//		System.out.println(dto.getReply_content());
		
		String notice_seq = dto.getNotice_seq();
		
		dto.setReply_id("임시작성자"); // 세션으로 받을거
		
		noticeSer.replyWrite(dto);
		
		return "redirect:/noticeDetail.do?notice_seq="+notice_seq;
		
	}

	@RequestMapping(value="/replyDel.do", method = RequestMethod.GET)
	public String replyDel(ReplyDto dto) {
		
		String reply_seq = dto.getReply_seq();
		String notice_seq = dto.getNotice_seq();
		
		System.out.println(reply_seq);
		System.out.println(notice_seq);
		
		noticeSer.replyDelete(reply_seq);
	
		return "redirect:/noticeDetail.do?notice_seq="+notice_seq;
	}

	*/

}
