package com.happy.para.model;

import java.util.List;

import com.happy.para.dto.CalDto;

public interface Calendar_IDao {

	// 일정 등록
	public int calRgister(CalDto dto);
	
	// 일정 수정
	public int calModify(CalDto dto);
	
	// 일정 삭제
	public int calDelete(String cal_seq);
	
	// 일정 매장별 조회
	public List<CalDto> calList(String store_code);
	
	// 일정 매장별 상세 조회
	public CalDto calDetail(CalDto dto);
	
}
