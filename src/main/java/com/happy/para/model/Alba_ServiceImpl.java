package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.para.dto.AlbaDto;

@Service
public class Alba_ServiceImpl implements Alba_IService {

	@Autowired
	private Alba_IDao alba_IDao;

	@Override
	public int albaRegister(AlbaDto albaDto) {
		return alba_IDao.albaRegister(albaDto);
	}

	@Override
	public int albaModify(AlbaDto albaDto) {
		return alba_IDao.albaModify(albaDto);
	}

	@Override
	public List<AlbaDto> albaList(Map<String, String> map) {
		return alba_IDao.albaList(map);
	}

	@Override
	public int albaListRow(String store_code) {
		return alba_IDao.albaListRow(store_code);
	}

	@Override
	public int albaDelete(String alba_seq) {
		return alba_IDao.albaDelete(alba_seq);
	}
	
	
}
