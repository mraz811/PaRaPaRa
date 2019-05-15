package com.happy.para.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.RequestDto;

@Repository
public class Stats_DaoImpl {

	@Autowired 
	private SqlSessionTemplate session;
	
	private final String NS = "para.stats.";
	
	public int ownerStatsIncome(Map<String, String> map) {
		int n = 0;
		List<RequestDto> lists = session.selectList(NS+"ownerStatsIncome", map);
		for (int i = 0; i < lists.size(); i++) {
			n += Integer.parseInt(lists.get(i).toString()); 
		}
		return n;
	}
	
	public int ownerStatsOutcome(Map<String, String> map) {
		int n = 0;
		return n;
	}
	
	public Map<String, Integer> ownerStatsMenu(Map<String, String> map){
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		List<RequestDto> lists = session.selectList(NS+"ownerStatsMenu", map);
		String menu_name = "";
		int count = 1;
		for (int i = 0; i < lists.size(); i++) {
			String[] reMenu = lists.get(i).getRequest_menu().split(",");
			for (int j = 0; j < reMenu.length; j++) {
				menu_name = session.selectOne(NS+"findMenuName", reMenu[j]); 
				if(resultMap.containsKey(menu_name)) {
					resultMap.put(menu_name, count++);
				}else {
					resultMap.put(menu_name, count);
				}
			}
		}
		return resultMap;
	}
	
}
