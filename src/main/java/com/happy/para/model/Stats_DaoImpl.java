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
public class Stats_DaoImpl implements Stats_IDao {

	@Autowired
	private SqlSessionTemplate sqlSession;

	private final String NS = "para.stats.";

	// 업주 : 수익 통계
	@Override
	public int ownerStatsIncome(Map<String, String> map) {
		int n = 0;
		if (sqlSession.selectOne(NS + "ownerStatsIncome", map) == null) {
			n = 0;
		} else {
			n = sqlSession.selectOne(NS + "ownerStatsIncome", map);
		}
		return n;
	}

	// 업주 : 지출 통계
	@Override
	public int ownerStatsOutcome(Map<String, String> map) {
		int n = 0;
		if (sqlSession.selectOne(NS + "ownerStatsOutcome", map) == null) {
			n = 0;
		} else {
			n = sqlSession.selectOne(NS + "ownerStatsOutcome", map);
		}
		return n;
	}

	// 업주 : 상위판매메뉴 통계
	@Override
	public Map<String, List<String>> ownerStatsMenu(Map<String, String> map) {
		Map<String, List<String>> resultMap = new HashMap<String, List<String>>();
		List<RequestDto> lists = sqlSession.selectList(NS + "ownerStatsMenu", map);
		System.out.println(lists);

		// 주문된 메뉴명을 담을 리스트
		List<String> menuList = new ArrayList<String>();
		// 메뉴 별 주문 수량을 담을 리스트
		List<String> cntList = new ArrayList<String>();

		// 메뉴명의 중복 제거를 위한 리스트
		List<String> menuList2 = new ArrayList<String>();
		// 중복 제거된 메뉴명에 따른 수량을 담을 리스트
		List<String> cntList2 = new ArrayList<String>();

		// 1:1,2:1,3:1, 로 디비에 저장되어있는 주문메뉴를 문자열 자르기를 통해 정제하는 로직
		for (int i = 0; i < lists.size(); i++) {
			String reMenu = lists.get(i).getRequest_menu();
			int reMenuLen = reMenu.length();
			int reMenuLenChange = reMenu.replaceAll(",", "").length();
			int arraySize = reMenuLen - reMenuLenChange;

			String[] menu = new String[arraySize];
			String[] cnt = new String[arraySize];
			StringTokenizer st = new StringTokenizer(reMenu, ",");
			int num = 0;
			while (st.hasMoreTokens()) {
				String str1 = st.nextToken();
				int idx = str1.indexOf(":");
				menu[num] = str1.substring(0, idx);
				cnt[num] = str1.substring(idx + 1);
				num++;
			}
			for (int j = 0; j < menu.length; j++) {
				String m = sqlSession.selectOne(NS + "findMenuName", menu[j]);
				if (m != null) {
					menuList.add(j, m);
					cntList.add(j, cnt[j]);
					System.out.println(menu[j]);
					System.out.println(menuList);
				}
			}
		}
		// 주문 메뉴명을 중복제거하고 메뉴명에 따른 주문 수량을 입력하는 로직
		String menuName = "";
		int count = 0;
		List<String> name = sqlSession.selectList(NS + "selectAllMenu");
		System.out.println("전체메뉴명 : " + name);
		System.out.println("주문메뉴명 : " + menuList);
		for (int j = 0; j < name.size(); j++) {
			for (int i = 0; i < menuList.size(); i++) {
				if (menuList.contains(name.get(j))) {
					menuName = name.get(j);
					if (menuList.get(i).equals(name.get(j))) {
						count += Integer.parseInt(cntList.get(i));
					}
				}
			}
			if (menuList.contains(name.get(j))) {
				menuList2.add(menuName);
				cntList2.add(Integer.toString(count));
				menuName = "";
				count = 0;
			}
		}
		// 최종 결과물이 담긴 리스트
		String[] tempMenuList = new String[menuList2.size()];
		for (int i = 0; i < tempMenuList.length; i++) {
			tempMenuList[i] = menuList2.get(i);
		}
		String[] tempCntList = new String[cntList2.size()];
		for (int i = 0; i < tempCntList.length; i++) {
			tempCntList[i] = cntList2.get(i);
		}

		// 주문 메뉴 수량이 많은 순으로 정렬시키는 로직
		Map<String, String[]> tempMap = bubble_sort(tempMenuList, tempCntList, tempCntList.length);
		for (int i = 0; i < tempCntList.length; i++) {
			System.out.println(tempMap.get("cntList")[i]);
			System.out.println(tempMap.get("menuList")[i]);
		}
		List<String> resultMenu = new ArrayList<String>();
		List<String> resultCnt = new ArrayList<String>();
		if (tempCntList.length > 5) {
			for (int i = 0; i < 5; i++) {
				resultMenu.add(tempMap.get("menuList")[i]);
				resultCnt.add(tempMap.get("cntList")[i]);
			}
		} else {
			for (int i = 0; i < tempCntList.length; i++) {
				resultMenu.add(tempMap.get("menuList")[i]);
				resultCnt.add(tempMap.get("cntList")[i]);
			}
		}

		resultMap.put("menu", resultMenu);
		resultMap.put("cnt", resultCnt);
		System.out.println("바뀌기전 카운트 : " + cntList);
		System.out.println("메뉴메뉴 : " + menuList2);
		System.out.println("바뀐후 카운트 : " + cntList2);
		return resultMap;
	}

	private Map<String, String[]> bubble_sort(String[] menuList, String[] cntList, int cntListLen) {
		int i, j, temp;
		String tempMenu = "";
		for (i = cntListLen - 1; i > 0; i--) {
			// 0 ~ (i-1)까지 반복
			for (j = 0; j < i; j++) {
				// j번째와 j+1번째의 요소가 크기 순이 아니면 교환
				if (Integer.parseInt(cntList[j]) < Integer.parseInt(cntList[j + 1])) {
					temp = Integer.parseInt(cntList[j]);
					cntList[j] = cntList[j + 1];
					cntList[j + 1] = Integer.toString(temp);
					tempMenu = menuList[j];
					menuList[j] = menuList[j + 1];
					menuList[j + 1] = tempMenu;
				}
			}
		}
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("cntList", cntList);
		map.put("menuList", menuList);
		return map;
	}

	// 관리자,담당자 : 수익통계
	@Override
	public RequestDto adminStatsIncome(Map<String, Object> map) {
		return sqlSession.selectOne(NS + "adminStatsIncome", map);
	}

	// 관리자,담당자 : 상위판매메뉴 통계
	@Override
	public Map<String, List<String>> adminStatsMenu(Map<String, Object> map) {
		Map<String, List<String>> resultMap = new HashMap<String, List<String>>();
		List<RequestDto> lists = sqlSession.selectList(NS + "adminStatsMenu", map);
		System.out.println("이거꼭봐야함@@@@@@@@@@" + lists);

		// 주문된 메뉴명을 담을 리스트
		List<String> menuList = new ArrayList<String>();
		// 메뉴 별 주문 수량을 담을 리스트
		List<String> cntList = new ArrayList<String>();

		// 메뉴명의 중복 제거를 위한 리스트
		List<String> menuList2 = new ArrayList<String>();
		// 중복 제거된 메뉴명에 따른 수량을 담을 리스트
		List<String> cntList2 = new ArrayList<String>();

		// 1:1,2:1,3:1, 로 디비에 저장되어있는 주문메뉴를 문자열 자르기를 통해 정제하는 로직
		for (int i = 0; i < lists.size(); i++) {
			String reMenu = lists.get(i).getRequest_menu();
			int reMenuLen = reMenu.length();
			int reMenuLenChange = reMenu.replaceAll(",", "").length();
			int arraySize = reMenuLen - reMenuLenChange;

			String[] menu = new String[arraySize];
			String[] cnt = new String[arraySize];
			StringTokenizer st = new StringTokenizer(reMenu, ",");
			int num = 0;
			while (st.hasMoreTokens()) {
				String str1 = st.nextToken();
				int idx = str1.indexOf(":");
				menu[num] = str1.substring(0, idx);
				cnt[num] = str1.substring(idx + 1);
				num++;
			}
			for (int j = 0; j < menu.length; j++) {
				String m = sqlSession.selectOne(NS + "findMenuName", menu[j]);
				if (m != null) {
					menuList.add(j, m);
					cntList.add(j, cnt[j]);
					System.out.println(menu[j]);
					System.out.println(menuList);
				}
			}
		}
		// 주문 메뉴명을 중복제거하고 메뉴명에 따른 주문 수량을 입력하는 로직
		String menuName = "";
		int count = 0;
		List<String> name = sqlSession.selectList(NS + "selectAllMenu");
		System.out.println("전체메뉴명 : " + name);
		System.out.println("주문메뉴명 : " + menuList);
		for (int j = 0; j < name.size(); j++) {
			for (int i = 0; i < menuList.size(); i++) {
				if (menuList.contains(name.get(j))) {
					menuName = name.get(j);
					if (menuList.get(i).equals(name.get(j))) {
						count += Integer.parseInt(cntList.get(i));
					}
				}
			}
			if (menuList.contains(name.get(j))) {
				menuList2.add(menuName);
				cntList2.add(Integer.toString(count));
				menuName = "";
				count = 0;
			}
		}
		// 최종 결과물이 담긴 리스트
		String[] tempMenuList = new String[menuList2.size()];
		for (int i = 0; i < tempMenuList.length; i++) {
			tempMenuList[i] = menuList2.get(i);
		}
		String[] tempCntList = new String[cntList2.size()];
		for (int i = 0; i < tempCntList.length; i++) {
			tempCntList[i] = cntList2.get(i);
		}

		// 주문 메뉴 수량이 많은 순으로 정렬시키는 로직
		Map<String, String[]> tempMap = bubble_sort(tempMenuList, tempCntList, tempCntList.length);
		for (int i = 0; i < tempCntList.length; i++) {
			System.out.println(tempMap.get("cntList")[i]);
			System.out.println(tempMap.get("menuList")[i]);
		}
		List<String> resultMenu = new ArrayList<String>();
		List<String> resultCnt = new ArrayList<String>();
		if (tempCntList.length > 5) {
			for (int i = 0; i < 5; i++) {
				resultMenu.add(tempMap.get("menuList")[i]);
				resultCnt.add(tempMap.get("cntList")[i]);
			}
		} else {
			for (int i = 0; i < tempCntList.length; i++) {
				resultMenu.add(tempMap.get("menuList")[i]);
				resultCnt.add(tempMap.get("cntList")[i]);
			}
		}

		resultMap.put("menu", resultMenu);
		resultMap.put("cnt", resultCnt);
		System.out.println("바뀌기전 카운트 : " + cntList);
		System.out.println("바뀐후 카운트 : " + cntList2);
		return resultMap;
	}

}
