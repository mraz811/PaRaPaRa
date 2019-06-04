package com.happy.para.model;

import java.util.List;
import java.util.Map;

import com.happy.para.dto.RequestDto;

public interface Stats_IDao {

	// 업주 : 수익 통계
	public int ownerStatsIncome(Map<String, String> map);

	// 업주 : 지출 통계
	public int ownerStatsOutcome(Map<String, String> map);

	// 업주 : 상위판매메뉴 통계
	public Map<String, List<String>> ownerStatsMenu(Map<String, String> map);

	// 관리자,담당자 : 수익통계
	public RequestDto adminStatsIncome(Map<String, Object> map);

	// 관리자,담당자 : 상위판매메뉴 통계
	public Map<String, List<String>> adminStatsMenu(Map<String, Object> map);
	
}
