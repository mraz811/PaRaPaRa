package com.happy.para.model;

import java.util.List;
import java.util.Map;

import com.happy.para.dto.AlbaDto;

public interface Alba_IDao {

	// 아르바이트 정보 등록
	public int albaRegister(AlbaDto albaDto);
	
	// 아르바이트 수정
	public int albaModify(AlbaDto albaDto);
	
	// 아르바이트 전체 조회
	public List<AlbaDto> albaList(Map<String, String> map);
	
	// 페이징을 위한 아르바이트 전체 수 조회
	public int albaListRow(String store_code);
	
	// 아르바이트 삭제 (Delflag->Y)
	public int albaDelete(String alba_seq);
	
	
	
}
