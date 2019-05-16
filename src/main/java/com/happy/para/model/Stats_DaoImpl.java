package com.happy.para.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map<String, Integer> ownerStatsMenu(Map<String, String> map){
		Map<String, Integer> resultMap = new HashMap<String, Integer>(); //결과를 담아줄 Map(주문메뉴명,주문된 갯수)
		List<RequestDto> lists = sqlSession.selectList(NS+"ownerStatsMenu", map);
		String menu_name = ""; // 주문메뉴 이름을 담아줄 변수
		for (int i = 0; i < lists.size(); i++) {
			String[] reMenu = lists.get(i).getRequest_menu().split(","); // , 를 구분자로 입력된 메뉴번호를 잘라냄
			for (int j = 0; j < reMenu.length; j++) { 
				int count = 1; // 주문된 갯수를 담아줄 변수
				System.out.println("처음 카운트 : "+count);
				menu_name = sqlSession.selectOne(NS+"findMenuName", reMenu[j]); // 주문메뉴번호에 해당되는 메뉴명 조회 
				if(resultMap.containsKey(menu_name)) { // 주문메뉴가 Key값으로 존재하면 value인 주문된 갯수 증가되게 함
					count = resultMap.get(menu_name);
					System.out.println("카운트 증가하기전 : "+count);
					count++;
					resultMap.put(menu_name,count);
					System.out.println("두번째 카운트 : "+count);
				}else { // 존재하지 않으면 1로 입력되게함
					count = 1;
					resultMap.put(menu_name, count);
					System.out.println("세번째 카운트 : "+count);
				}
			}
		}
		return resultMap;
	}
	
	//관리자,담당자 : 수익통계
	@Override
	public int adminStatsIncome(Map<String, Object> map) {
		return sqlSession.selectOne(NS+"adminStatsIncome", map);
	}
	
	//관리자,담당자 : 상위판매메뉴 통계
	@Override
	public Map<String, Integer> adminStatsMenu(Map<String, Object> map){
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		List<RequestDto> lists = sqlSession.selectList(NS+"adminStatsMenu", map);
		String menu_name = "";
		for (int i = 0; i < lists.size(); i++) {
			String[] reMenu = lists.get(i).getRequest_menu().split(",");
			for (int j = 0; j < reMenu.length; j++) {
				int count = 1;
				System.out.println("처음 카운트 : "+count);
				menu_name = sqlSession.selectOne(NS+"findMenuName", reMenu[j]); 
				if(resultMap.containsKey(menu_name)) {
					count = resultMap.get(menu_name);
					System.out.println("카운트 증가하기전 : "+count);
					count++;
					resultMap.put(menu_name,count);
					System.out.println("두번째 카운트 : "+count);
				}else {
					count = 1;
					resultMap.put(menu_name, count);
					System.out.println("세번째 카운트 : "+count);
				}
			}
		}
		return resultMap;
	}
	
}
