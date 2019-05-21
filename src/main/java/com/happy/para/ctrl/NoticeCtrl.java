package com.happy.para.ctrl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happy.para.dto.NoticeDto;
import com.happy.para.dto.PagingDto;
import com.happy.para.dto.ReplyDto;
import com.happy.para.model.Calendar_IService;
import com.happy.para.model.Notice_IService;

import net.sf.json.JSONObject;

@Controller
public class NoticeCtrl {



	@Autowired
	private Notice_IService noticeSer;
	
	
	@RequestMapping(value="/selNoticeList.do", method=RequestMethod.GET)
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
		
		return "notice/noticeList";
	}
	
	@RequestMapping(value="/regiNoticeForm.do", method=RequestMethod.GET)
	public String noticeWriteForm() {
		return "notice/noticeWriteForm";
	}
	
	@RequestMapping(value="/regiNotice.do", method=RequestMethod.POST)
	public String noticeWrite(NoticeDto dto) {
		dto.setNotice_id("작성자2"); // 나중에 세션으로 가져올거임
		boolean isc = noticeSer.noticeWrite(dto);
		
		return isc? "redirect:/selNoticeList.do" : "notice/noticeWriteForm";
	}
	
	@RequestMapping(value="/selNoticeDetail.do", method=RequestMethod.GET)
	public String noticeDetail(Model model, String notice_seq) {
		
		NoticeDto dto = noticeSer.noticeDetail(notice_seq);
		List<ReplyDto> Rlists = noticeSer.replyList(notice_seq);
		
		model.addAttribute("dto", dto);
		model.addAttribute("Rlists", Rlists);
		
		return "notice/noticeDetail";
	}
	
	@RequestMapping(value="/noticeModifyForm.do", method=RequestMethod.POST)
	public String noticeModifyForm(Model model, NoticeDto dto) {
		
		String notice_seq = dto.getNotice_seq();
		
		NoticeDto Ndto = noticeSer.noticeDetail(notice_seq);
		model.addAttribute("dto", Ndto);
		
		return "notice/noticeModifyForm";
	}
	
	@RequestMapping(value="/noticeModify.do", method=RequestMethod.POST)
	public String noticeModify(NoticeDto dto) {
		
		boolean isc = noticeSer.noticeModify(dto);
		
		return isc? "redirect:/selNoticeList.do" : "notice/noticeModifyForm";
	}
	
	@RequestMapping(value="/noticeDelete.do", method=RequestMethod.POST)
	public String noticeDelete(String notice_seq) {
		
		boolean isc = noticeSer.noticeDelete(notice_seq);
		
		return isc? "redirect:/selNoticeList.do" : "notice/noticeList";
	}
	
	@RequestMapping(value="/replyWrite.do", method=RequestMethod.POST)
	public String replyWrite(ReplyDto dto) {
		
//		System.out.println(dto.getNotice_seq());
//		System.out.println(dto.getReply_content());
		
		String notice_seq = dto.getNotice_seq();
		
		dto.setReply_id("임시작성자"); // 세션으로 받을거
		
		noticeSer.replyWrite(dto);
		
		return "redirect:/selNoticeDetail.do?notice_seq="+notice_seq;
		
	}

	@RequestMapping(value="/replyDel.do", method = RequestMethod.GET)
	public String replyDel(ReplyDto dto) {
		
		String reply_seq = dto.getReply_seq();
		String notice_seq = dto.getNotice_seq();
		
		System.out.println(reply_seq);
		System.out.println(notice_seq);
		
		noticeSer.replyDelete(reply_seq);
	
		return "redirect:/selNoticeDetail.do?notice_seq="+notice_seq;
	}
	
	@RequestMapping(value="/paging.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String paging(Model model, HttpSession session, PagingDto pDto) {
		
		JSONObject json = null;
		
		pDto.setTotal(noticeSer.noticeListRow());
		json = objectJson(noticeSer.noticeList(pDto), pDto);
		
		session.removeAttribute("row");
		session.setAttribute("row", pDto);
		return json.toString();
	}

//	"lists":{{seq:1},{title:tt},{}} << 이런식으로 담을거야 어레이 리스트로 담고 맴 형태로
	@SuppressWarnings({ "unchecked", "unused" })
	private JSONObject objectJson(List<NoticeDto> lists, PagingDto row) {
		JSONObject json = new JSONObject(); // 최종적으로 담는애는 여긴데
		JSONArray jLists = new JSONArray(); // 어레이리스트를 담을때는 여기에
		JSONObject jList = null; // 그냥 얘는 제이슨 타입으로

		// 게시글에 관련
		for (NoticeDto dto : lists) {
			jList = new JSONObject();
			jList.put("seq",dto.getNotice_seq());
			jList.put("id",dto.getNotice_id());
			jList.put("title",dto.getNotice_title());
			jList.put("content",dto.getNotice_content());
			jList.put("regdate",dto.getNotice_regdate());
			jList.put("delflag",dto.getNotice_delflag());

			jLists.add(jList);
		}          
		
		//페이징에 관련된
		jList = new JSONObject();
		jList.put("pageList",row.getPageList());
		jList.put("index",row.getIndex());
		jList.put("pageNum",row.getPageNum());
		jList.put("listNum",row.getListNum());
		jList.put("total",row.getTotal());
		jList.put("count",row.getCount());
		
		json.put("lists", jLists);
		json.put("row", jList);
		
		return json;
	}

}
