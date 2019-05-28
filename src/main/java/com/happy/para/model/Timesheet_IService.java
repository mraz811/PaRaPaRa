package com.happy.para.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.happy.para.dto.AlbaDto;
import com.happy.para.dto.TimeDto;

public interface Timesheet_IService {

	// 전체 timesheet 조회 // 파라미터값 추후 수정 필요
	public List<TimeDto> tsListAll(Map<String, String> map);

	// 매장별, 알바별 timesheet 조회
	public List<TimeDto> tsList(TimeDto dto);

	// 일별 알바별 datetime list로 가져오기
	public List<TimeDto> tsDatetimeList(TimeDto dto);
	
	// timesheet 추가
	public boolean tsRegister(TimeDto dto);

	// timesheet 수정
	public boolean tsModify(TimeDto dto);

	// timesheet 삭제
	public boolean tsDelete(TimeDto dto);
	
	// Store_code 별 알바 조회
	public List<AlbaDto> tsAlba(String store_code);
	
}
