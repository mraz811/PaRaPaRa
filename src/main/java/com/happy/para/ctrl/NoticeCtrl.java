package com.happy.para.ctrl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.NoticeDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.dto.PagingDto;
import com.happy.para.dto.ReplyDto;
import com.happy.para.model.Notice_IService;

import net.sf.json.JSONObject;

@Controller
public class NoticeCtrl {

	@Autowired
	private Notice_IService noticeSer;
	
	// 공지사항 조회
	@RequestMapping(value="/selNoticeList.do", method=RequestMethod.GET)
	public String noticeList(HttpSession session, Model model) {
		
		System.out.println("selNoticeList 접속");
		
		PagingDto rowDto = null;
		List<NoticeDto> lists = null;

		if(session.getAttribute("noticRow")== null) {
			rowDto = new PagingDto();
		}else {
			rowDto = (PagingDto) session.getAttribute("noticRow");
		}

		rowDto.setTotal(noticeSer.noticeListRow());

		lists = noticeSer.noticeList(rowDto);

		model.addAttribute("lists", lists);
		model.addAttribute("noticRow", rowDto);
		
		return "notice/noticeList";
	}

	// 공지사항 등록 폼으로 이동
	@RequestMapping(value="/regiNoticeForm.do", method=RequestMethod.GET)
	public String noticeWriteForm(HttpSession session, Model model) {
		
		AdminDto aDto = (AdminDto)session.getAttribute("loginDto");
		model.addAttribute("admin_name", aDto.getAdmin_name());

		Date getDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("today", sdf.format(getDate));
		
		return "notice/noticeWriteForm";
	}
	
	// 공지사항 등록
	@RequestMapping(value="/regiNotice.do", method=RequestMethod.POST)
	public String noticeWrite(NoticeDto dto, String loginDtoAuth, Model model, HttpSession session) {
		
		if(!loginDtoAuth.equalsIgnoreCase("U")) { // 업주가 아닐경우
			AdminDto aDto = (AdminDto)session.getAttribute("loginDto");
			dto.setNotice_id(aDto.getAdmin_id()+"");
			dto.setNotice_name(aDto.getAdmin_name());
		}
		
		boolean isc = noticeSer.noticeWrite(dto);
		
		return isc? "redirect:/selNoticeList.do" : "notice/noticeWriteForm";
	}
	
	// 공지사항 상세 조회
	@RequestMapping(value="/selNoticeDetail.do", method=RequestMethod.GET)
	public String noticeDetail(Model model, String notice_seq, HttpSession session, String loginDtoAuth) {
		
		NoticeDto dto = noticeSer.noticeDetail(notice_seq);
		List<ReplyDto> Rlists = noticeSer.replyList(notice_seq);
		
		System.out.println("loginDtoAuth?!!!!!!!!"+loginDtoAuth);
		
		// loginDtoAuth A or U 접속 ID 받고, ID에 따른 수정, 삭제 버튼 화면에 띄워주기
		
		if(loginDtoAuth.equalsIgnoreCase("U")) { // 업주
			OwnerDto oDto = (OwnerDto)session.getAttribute("loginDto");			
			model.addAttribute("owner_id", oDto.getOwner_id()+"");
		}else { // 담당자, 관리자
			AdminDto aDto = (AdminDto)session.getAttribute("loginDto");
			model.addAttribute("admin_id", aDto.getAdmin_id()+"");
			System.out.println("admin_id"+aDto.getAdmin_id()+"");
		}
		
		model.addAttribute("dto", dto);
		model.addAttribute("Rlists", Rlists);
		
		return "notice/noticeDetail";
	}
	
	// 공지사항 수정 폼으로 이동
	@RequestMapping(value="/noticeModifyForm.do", method=RequestMethod.POST)
	public String noticeModifyForm(Model model, NoticeDto dto) {

		String notice_seq = dto.getNotice_seq();

		NoticeDto nDto = noticeSer.noticeDetail(notice_seq);
		model.addAttribute("dto", nDto);

		return "notice/noticeModifyForm";
	}
	
	// 공지사항 수정
	@RequestMapping(value="/noticeModify.do", method=RequestMethod.POST)
	public String noticeModify(HttpSession session, NoticeDto dto) {
		
		boolean isc = noticeSer.noticeModify(dto);

		return isc? "redirect:/selNoticeList.do" : "notice/noticeModifyForm";
	}
	
	// 공지사항 삭제
	@RequestMapping(value="/noticeDelete.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String noticeDelete(String notice_seq) {
		
		boolean isc = noticeSer.noticeDelete(notice_seq);
		
		return isc? "성공":"실패";
	}

	// 공지사항 댓글 등록
	@RequestMapping(value="/writeReply.do", method=RequestMethod.POST)
	public String writeReply(ReplyDto dto, HttpSession session, Model model, String loginDtoAuth) {

		System.out.println("loginDtoAuth? : "+loginDtoAuth);

		if(loginDtoAuth.equalsIgnoreCase("U")) { // 업주
			OwnerDto oDto = (OwnerDto)session.getAttribute("loginDto");			
			dto.setReply_id(oDto.getOwner_id());
			dto.setReply_name(oDto.getOwner_name());
			model.addAttribute("owner_id", oDto.getOwner_id()+"");
		}else { // 담당자, 관리자
			AdminDto aDto = (AdminDto)session.getAttribute("loginDto");
			dto.setReply_id(aDto.getAdmin_id()+"");
			dto.setReply_name(aDto.getAdmin_name());
			model.addAttribute("admin_id", aDto.getAdmin_id());			
		}
		
		dto.setNotice_seq(dto.getNotice_seq());
//		dto.setReply_content(reply_content);
		
		noticeSer.replyWrite(dto);

		return "redirect:/selNoticeDetail.do?notice_seq="+dto.getNotice_seq()+"&loginDtoAuth="+loginDtoAuth;
	}

	// 공지사항 댓글 삭제
	@RequestMapping(value="/delReply.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String delReply(String loginDtoAuth, String notice_seq, @RequestParam("reply_seq")String reply_seq) {

		noticeSer.replyDelete(reply_seq);
	
		return "redirect:/selNoticeDetail.do?notice_seq="+notice_seq+"&loginDtoAuth="+loginDtoAuth;
	}
	
	// 공지사항 페이징
	@RequestMapping(value="/noticePaging.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String paging(Model model, HttpSession session, PagingDto pDto) {
		
		JSONObject json = null;

		pDto.setTotal(noticeSer.noticeListRow());
		json = objectJson(noticeSer.noticeList(pDto), pDto);

		session.removeAttribute("noticRow");
		session.setAttribute("noticRow", pDto);
		System.out.println("json.toString() : "+json.toString());
		return json.toString();
	}

//	"lists":{{seq:1},{title:tt},{}}
	@SuppressWarnings({ "unchecked", "unused" })
	private JSONObject objectJson(List<NoticeDto> lists, PagingDto noticRow) {
		JSONObject json = new JSONObject(); // 최종적으로 담는애는 여긴데
		JSONArray jLists = new JSONArray(); // 어레이리스트를 담을때는 여기에
		JSONObject jList = null; // 그냥 얘는 제이슨 타입으로

		// 게시글에 관련
		for (NoticeDto dto : lists) {
			jList = new JSONObject();
			jList.put("rnum",dto.getRnum());
			jList.put("seq",dto.getNotice_seq());
			jList.put("id",dto.getNotice_id());
			jList.put("name",dto.getNotice_name());
			jList.put("title",dto.getNotice_title());
			jList.put("regdate",dto.getNotice_regdate());

			jLists.add(jList);
		}          
		
		//페이징에 관련된
		jList = new JSONObject();
		jList.put("pageList",noticRow.getPageList());
		jList.put("index",noticRow.getIndex());
		jList.put("pageNum",noticRow.getPageNum());
		jList.put("listNum",noticRow.getListNum());
		jList.put("total",noticRow.getTotal());
		jList.put("count",noticRow.getCount());
		
		json.put("lists", jLists);
		json.put("noticRow", jList);
		
		return json;
	}

}
