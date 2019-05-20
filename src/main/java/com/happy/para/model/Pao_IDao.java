package com.happy.para.model;

import java.util.List;

import com.happy.para.dto.PaoDto;

public interface Pao_IDao {

	// 업주 : 발주 리스트 조회
	public List<PaoDto> paoList(String store_code);
}
