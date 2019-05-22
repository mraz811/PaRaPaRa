package com.happy.para.model;

import java.util.List;
import java.util.Map;

import com.happy.para.dto.StoreDto;

public interface Store_IService {
	
	// 지역별 매장 조회
	public List<StoreDto> storeList(StoreDto dto);
	
	// 매장 등록
	public boolean storeInsert(StoreDto dto);
	
	// 매장 수정
	public boolean storeModify(StoreDto dto);
	
	// 매장 삭제
	public boolean storeDelete(String store_code);
	
	// 담당자 별 매장 조회(페이징)
	public List<StoreDto> storeListPaging(Map<String, Integer> map);
	
	// 담당자 별 삭제되지 않은 매장 갯수
	public int storeListRow(String admin_id);
	
//	// 담당자 별 총 매장 갯수
//	public int storeRow(String admin_id);
	
	// 매장 상세 조회
	public StoreDto storeDetail(String store_code);
	
	// 담당자별 가장 큰 매장 코드 값
	public String selectMaxStoreCode(String loc_code);
}
