package com.happy.para.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.RequestDto;

@Repository
public class Stats_DaoImpl implements Stats_IDao{
	
	@Autowired 
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "para.stats.";
	
	//업주 : 수익 통계
	@Override
	public int ownerStatsIncome(Map<String, String> map) {
		return sqlSession.selectOne(NS+"ownerStatsIncome", map);
	}
	
	//업주 : 지출 통계
	@Override
	public int ownerStatsOutcome(Map<String, String> map) {
		return sqlSession.selectOne(NS+"ownerStatsOutcome", map);
	}
	
	//업주 : 상위판매메뉴 통계
	@Override
	public Map<String, List<String>> ownerStatsMenu(Map<String, String> map){
		Map<String, List<String>> resultMap = new HashMap<String,List<String>>();
		List<RequestDto> lists = sqlSession.selectList(NS+"ownerStatsMenu", map);
		System.out.println(lists);
		List<String> menuList = new ArrayList<String>();
		List<String> cntList = new ArrayList<String>();
		
		List<String> menuList2 = new ArrayList<String>();
		List<String> cntList2 = new ArrayList<String>();
		
		for (int i = 0; i < lists.size(); i++) {
			String reMenu = lists.get(i).getRequest_menu();
			int reMenuLen = reMenu.length();
			int reMenuLenChange = reMenu.replaceAll(",", "").length();
			int arraySize = reMenuLen-reMenuLenChange;
			
			String[] menu = new String[arraySize];
			String[] cnt = new String[arraySize];
			StringTokenizer st = new StringTokenizer(reMenu, ",");
			int num = 0;
			while (st.hasMoreTokens()) {
				String str1 = st.nextToken();
				int idx = str1.indexOf(":");
				menu[num] = str1.substring(0, idx);
				cnt[num] = str1.substring(idx+1);
				num++;
			}
			for (int j = 0; j < menu.length; j++) {
				menuList.add(j, sqlSession.selectOne(NS+"findMenuName", menu[j]));  
				cntList.add(j, cnt[j]);
			}
		}
		String menuName = "";
		int count = 0;
		List<String> name = sqlSession.selectList(NS+"selectAllMenu");
		for (int j = 0; j < name.size(); j++) {
			for (int i = 0; i < menuList.size(); i++) {
				if(menuList.contains(name.get(j))) {
					menuName = name.get(j);
					if(menuList.get(i).equals(name.get(j))) {
						count += Integer.parseInt(cntList.get(i)); 
					}
				}
			}
			if(menuList.contains(name.get(j))) {
				menuList2.add(menuName);
				cntList2.add(Integer.toString(count));
				menuName = "";
				count = 0;
			}
		}
		resultMap.put("menu", menuList2);
		resultMap.put("cnt", cntList2);
		System.out.println("바뀌기전 카운트 : "+cntList);
		System.out.println("바뀐후 카운트 : "+cntList2);
		return resultMap;
	}
	
	//관리자,담당자 : 수익통계
	@Override
	public int adminStatsIncome(Map<String, Object> map) {
		return sqlSession.selectOne(NS+"adminStatsIncome", map);
	}
	
	//관리자,담당자 : 상위판매메뉴 통계
	@Override
	public Map<String, String> adminStatsMenu(Map<String, Object> map){
		Map<String, String> resultMap = new HashMap<String, String>();
		List<RequestDto> lists = sqlSession.selectList(NS+"adminStatsMenu", map);
		String menu_name = "";
		for (int i = 0; i < lists.size(); i++) {
			String reMenu = lists.get(i).getRequest_menu();
			int reMenuLen = reMenu.length();
			int reMenuLenChange = reMenu.replaceAll(",", "").length();
			int arraySize = reMenuLen-reMenuLenChange;
			
			String[] menu = new String[arraySize];
			String[] cnt = new String[arraySize];
			StringTokenizer st = new StringTokenizer(reMenu, ",");
			int num = 0;
			while (st.hasMoreTokens()) {
				String str1 = st.nextToken();
				int idx = str1.indexOf(":");
				menu[num] = str1.substring(0, idx);
				cnt[num] = str1.substring(idx+1);
				num++;
			}
			for (int j = 0; j < menu.length; j++) {
				menu_name = sqlSession.selectOne(NS+"findMenuName", menu[j]); 
				resultMap.put(menu_name, cnt[j]);
			}
		}
		return resultMap;
	}
	
}
