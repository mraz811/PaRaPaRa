package com.happy.para.common;

import java.util.List;

import com.happy.para.dto.NoticeDto;


public class NoticeInputList {

	private List<NoticeDto> lists;
	
	public void setLists(List<NoticeDto> lists) {
		this.lists = lists;
	}
	
	// 날짜 포멧 변경(2019-04-04 2:11:12.0) -> 2019-04-04
	public static String dateFormat(String date) {
		
		System.out.println(date);
		
		
		return date.substring(0, date.indexOf(" "));
	}
	
	// 제목 포멧
	private String titleFormat(int depth) {
		StringBuffer sb = new StringBuffer();
		for(int i =0; i < depth; i++) {
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return sb.toString();
	}
	
	private String listFormat(NoticeDto dto) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<tr><td>"+dto.getNotice_seq()+"</td>");
		sb.append("<td><a href='./noticeDetail.do?notice_seq="+dto.getNotice_seq()+"'>"+dto.getNotice_title()+"</a></td>");
		sb.append("<td>"+dto.getNotice_id()+"</td>");
		sb.append("<td>"+dateFormat(dto.getNotice_regdate())+"</td></tr>");
		
		return sb.toString();
	}
	
	public String getListformat() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lists.size(); i++) {
			sb.append(listFormat(lists.get(i)));
		}
		return sb.toString();
	}
	
	
}
