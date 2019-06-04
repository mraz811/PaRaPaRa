package com.happy.para.ctrl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.ItemDto;
import com.happy.para.dto.NoticeDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.dto.PagingDto;
import com.happy.para.dto.PaoDto;
import com.happy.para.dto.StockDto;
import com.happy.para.model.Pao_IService;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

@Controller
public class PaoCtrl {
	
	@Autowired
	private Pao_IService paoService;
	
	/*
	@RequestMapping(value = "/test.do", method = RequestMethod.GET)
	public void test(Model model) {
		URL url = null;
		URLConnection connection = null;
		StringBuilder responseBody = new StringBuilder();
		try {
			url = new URL("https://pay.toss.im/api/v1/payments");
			connection = url.openConnection();
			connection.addRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			connection.setDoInput(true);

			org.json.simple.JSONObject jsonBody = new org.json.simple.JSONObject();
			jsonBody.put("orderNo", "2015072012211");
			jsonBody.put("amount", 10000);
			jsonBody.put("amountTaxFree", 0);
			jsonBody.put("productDesc", "테스트 결제");
			jsonBody.put("apiKey", "sk_test_apikey1234567890");
		    jsonBody.put("autoExecute", "true");
		    jsonBody.put("resultCallback", "http://localhost:8099/PaRaPaRa/");
		    jsonBody.put("retUrl", "http://YOUR-SITE.COM/ORDER-CHECK?orderno=1");
		    jsonBody.put("retCancelUrl", "http://YOUR-SITE.COM/close");

			BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
			
		    bos.write(jsonBody.toJSONString().getBytes(StandardCharsets.UTF_8));
			bos.flush();
			bos.close();

			
		    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
			String line = null;
			while ((line = br.readLine()) != null) {
				responseBody.append(line);
			}
			br.close();
		} catch (Exception e) {
			responseBody.append(e);
		}
		System.out.println(responseBody.toString());
	}
	*/
	
	// 업주 : 발주 리스트 조회(페이징)
	@RequestMapping(value="/selPaoList.do", method=RequestMethod.GET)
	public String paoList(Model model, HttpSession session) {
		OwnerDto loginDto = (OwnerDto) session.getAttribute("loginDto");	// 세션 정보 받아옴
		String store_code = loginDto.getStore_code();	// 로그인한 업주의 매장코드
		
		System.out.println("=== 넘겨받은 매장코드 === : "+store_code);
		
		PagingDto rowDto = null;
		
		if(session.getAttribute("paoRow") == null) {
			rowDto = new PagingDto();
		}else {
			rowDto = (PagingDto) session.getAttribute("paoRow");
		}
		
		// 자신의 발주 내역 갯수를 set(페이징 처리를 위해)
		rowDto.setTotal(paoService.paoListRow(store_code));

		Map<String, Object> map = new HashMap<>();
		
		map.put("store_code", store_code);
		map.put("start", rowDto.getStart());
		map.put("end", rowDto.getEnd());
		
		List<PaoDto> paoLists = paoService.paoList(map);	// 발주 내역

		System.out.println(paoLists);

		model.addAttribute("store_code", store_code);
		model.addAttribute("paoLists", paoLists);
		model.addAttribute("paoRow", rowDto);
		model.addAttribute("loginDto", loginDto);
		return "pao/paoList";
	}
	
	// 담당자 : 발주 리스트 조회(페이징)
	@RequestMapping(value="/selAdminPaoList.do", method=RequestMethod.GET)
	public String adminPaoList(Model model, HttpSession session) {
		AdminDto loginDto = (AdminDto) session.getAttribute("loginDto");	// 세션 정보 받아옴
		String store_code = loginDto.getLoc_code();	// 로그인한 담당자의 지역코드
			
		System.out.println("=== 넘겨받은 지역코드 === : "+store_code);
		
		PagingDto rowDto = null;
		
		if(session.getAttribute("paoRow") == null) {
			rowDto = new PagingDto();
		}else {
			rowDto = (PagingDto) session.getAttribute("paoRow");
		}
		rowDto.setTotal(paoService.adminPaoListRow(store_code));
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("store_code", store_code);
		map.put("start", 1);
		map.put("end", 5);
			
		List<PaoDto> paoLists = paoService.adminPaoList(map);	// 발주내역

		System.out.println(paoLists);
			
		model.addAttribute("store_code", store_code);
		model.addAttribute("paoLists", paoLists);
		model.addAttribute("paoRow", rowDto);
		model.addAttribute("loginDto", loginDto);
		return "pao/paoList";
	}

	// 업주, 담당자 : 페이지 숫자를 눌렀을 때(페이징)
	@RequestMapping(value="/paoPaging.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String paging(Model model, HttpSession session, PagingDto pDto, String loginDtoAuth, String paoStatus, String startDate, String endDate) {
		System.out.println("=== 넘겨받은 권한 === : "+loginDtoAuth);
		System.out.println("=== 넘겨받은 발주상태코드 === : "+paoStatus);
		System.out.println("=== 넘겨받은 시작날짜 === : "+startDate);
		System.out.println("=== 넘겨받은 종료날짜 === : "+endDate);

		JSONObject json = null;
		
		String store_code = "";
		String[] statusLists = paoStatus.split(",");	// 발주 상태 코드를 Pao.xml에서 처리해주기 위해 배열의 형태로 만듦
		
		Map<String, Object> map = new HashMap<>();
		
		if (loginDtoAuth.equalsIgnoreCase("U")){ // 업주
			OwnerDto loginDto = (OwnerDto) session.getAttribute("loginDto");
			store_code = loginDto.getStore_code();	// 업주의 store_code를 사용
			
			// 날짜를 선택하지 않았을 때 보여줄 발주 내역
			if( (paoStatus.equals("") || paoStatus==null) && (startDate.equals("") || startDate==null) && (endDate.equals("") || endDate==null) ) {	
				pDto.setTotal(paoService.paoListRow(store_code));	// 자신의 전체 발주 내역 갯수를 set(페이징 처리를 위해)
				
				map.put("store_code", store_code);
				map.put("start", pDto.getStart());
				map.put("end", pDto.getEnd());
				
				json = objectJson(paoService.paoList(map), pDto);	// 발주 내역을 JSONObject 타입으로 만듦
			}else {	// 선택한 조건에 따른 발주 내역을 보여줌
				
				// 시작날짜를 선택하지 않았을 때
				if(startDate.equals("") || startDate==null) {
					startDate = "1000-01-01";
				}
				// 종료날짜를 선택하지 않았을 때
				if(endDate.equals("") || endDate==null) {
					endDate = "9999-12-31";
				}
				// 둘다 선택하지 않았을 때
				if( (startDate.equals("") || startDate==null) && (endDate.equals("") || endDate==null)) {
					startDate = "1000-01-01";
					endDate = "9999-12-31";
				}
				
				// 조건에 맞는 발주 갯수를 구하기 위한 Map
				Map<String, Object> cntMap = new HashMap<>();
				cntMap.put("store_code", store_code);
				cntMap.put("status_list", statusLists);
				cntMap.put("startDate", startDate);
				cntMap.put("endDate", endDate);
				
				pDto.setTotal(paoService.paoStatusListRow(cntMap));	// 조건에 맞는 발주 갯수로 전체 수를 set(페이징 처리를 위해) 
				
				map.put("store_code", store_code);
				map.put("status_list", statusLists);
				map.put("startDate", startDate);
				map.put("endDate", endDate);
				map.put("start", pDto.getStart());
				map.put("end", pDto.getEnd());
				
				json = objectJson(paoService.paoSelectStatusDate(map), pDto);	// 조건에 맞는 발주 내역을 JSONObject 타입으로 만듦
			}
			
		}
		if(loginDtoAuth.equalsIgnoreCase("A")) { // 담당자
			AdminDto loginDto = (AdminDto) session.getAttribute("loginDto");
			store_code = loginDto.getLoc_code();	// 담당자의loc_code를 사용
			System.out.println("========================= 스토어 코드 : "+store_code);
			// 날짜를 선택하지 않았을 때 보여줄 발주 내역
			if( (paoStatus.equals("") || paoStatus==null) && (startDate.equals("") || startDate==null) && (endDate.equals("") || endDate==null) ) {	
				pDto.setTotal(paoService.adminPaoListRow(store_code));	// 담당 지역 매장의 전체 발주 내역 갯수를 set(페이징 처리를 위해)
				
				map.put("store_code", store_code);
				map.put("start", pDto.getStart());
				map.put("end", pDto.getEnd());
				
				json = objectJson(paoService.adminPaoList(map), pDto);	// 발주 내역을 JSONObject 타입으로 만듦
			}else {
				// 시작날짜를 선택하지 않았을 때
				if(startDate.equals("") || startDate==null) {
					startDate = "1000-01-01";
				}
				// 종료날짜를 선택하지 않았을 때
				if(endDate.equals("") || endDate==null) {
					endDate = "9999-12-31";
				}
				// 둘다 선택하지 않았을 때
				if( (startDate.equals("") || startDate==null) && (endDate.equals("") || endDate==null)) {
					startDate = "1000-01-01";
					endDate = "9999-12-31";
				}
				
				// 조건에 맞는 발주 갯수를 구하기 위한 Map
				Map<String, Object> cntMap = new HashMap<>();
				cntMap.put("store_code", store_code);
				cntMap.put("status_list", statusLists);
				cntMap.put("startDate", startDate);
				cntMap.put("endDate", endDate);
				
				pDto.setTotal(paoService.adminPaoStatusListRow(cntMap));	// 담당 지역 매장에 대해 조건에 맞는  발주 내역 갯수를 set(페이징 처리를 위해)
				
				map.put("store_code", store_code);
				map.put("status_list", statusLists);
				map.put("startDate", startDate);
				map.put("endDate", endDate);
				map.put("start", pDto.getStart());
				map.put("end", pDto.getEnd());
				
				json = objectJson(paoService.adimPaoSelectStatusDate(map), pDto);	// 조건에 맞는 발주 내역을 JSONObject 타입으로 만듦
			}
			
		}
		
		// 기존 발주 갯수를 지우고 현재 발주 갯수를 set  
		session.removeAttribute("paoRow");
		session.setAttribute("paoRow", pDto);
		System.out.println("json.toString() : "+json.toString());
		return json.toString();
	}
	
	// 페이징 시 처리해줄 JSONObject
	//	"paoLists" : {{pao_seq:1}, {store_code:SEOUL01_06}, ...} -> ArrayList로 담고 Map의 형태로 만듦
	@SuppressWarnings({ "unchecked", "unused" })
	private JSONObject objectJson(List<PaoDto> paoLists, PagingDto paoRow) {
		JSONObject json = new JSONObject(); // 반환할 최종 JSONObject
		JSONArray jLists = new JSONArray(); // ArrayList를 담을 때
		JSONObject jList = null; // json 타입

		// 발주 리스트
		for (PaoDto dto : paoLists) {
			jList = new JSONObject();
			jList.put("pao_seq",dto.getPao_seq());
			jList.put("store_code",dto.getStore_code());
			jList.put("store_name",dto.getStore_name());
			jList.put("ps_code",dto.getPs_code());
			jList.put("ps_name",dto.getPs_name());
			jList.put("pao_date",dto.getPao_date());

			jLists.add(jList);
		}          
		
		// 페이징
		jList = new JSONObject();
		jList.put("pageList", paoRow.getPageList());
		jList.put("index", paoRow.getIndex());
		jList.put("pageNum", paoRow.getPageNum());
		jList.put("listNum", paoRow.getListNum());
		jList.put("total", paoRow.getTotal());
		jList.put("count", paoRow.getCount());
		
		// JSONObject에 paoLists와 paoRow로 넣어줌
		json.put("paoLists", jLists);
		json.put("paoRow", jList);
		
		return json;
	}
	
	
	// 업주 : 발주 상태 선택 조회 및 매장 발주 날짜 선택 조회(페이징)
	@RequestMapping(value="/paoStatusAjax.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String paoSelectStatus(HttpSession session, String store_code, String status, String startDate, String endDate, String loginDtoAuth, Model model) {
		System.out.println("=== 넘겨받은 권한 === : "+loginDtoAuth);
		System.out.println("=== 넘겨받은 매장코드 === : "+store_code);
		System.out.println("=== 넘겨받은 발주 상태 코드 === : "+status);
		System.out.println("=== 넘겨받은 발주 시작일 === : "+startDate);
		System.out.println("=== 넘겨받은 발주 종료일 === : "+endDate);
		
		JSONObject json = null;
		
		String[] statusLists = status.split(",");	// 발주 상태 코드를 Pao.xml에서 처리해주기 위해 배열의 형태로 만듦
		
		// 시작날짜를 선택하지 않았을 때
		if(startDate.equals("") || startDate==null) {
			startDate = "1000-01-01";
		}
		// 종료날짜를 선택하지 않았을 때
		if(endDate.equals("") || endDate==null) {
			endDate = "9999-12-31";
		}
		// 모두 선택하지 않았을 때
		if( (startDate.equals("") || startDate==null) && (endDate.equals("") || endDate==null)) {
			startDate = "1000-01-01";
			endDate = "9999-12-31";
		}
		
		PagingDto rowDto = null;
		
		if(session.getAttribute("paoRow") == null) {
			rowDto = new PagingDto();
		}else {
			rowDto = (PagingDto) session.getAttribute("paoRow");
		}
		
		
		if (loginDtoAuth.equalsIgnoreCase("U")){ // 업주
			OwnerDto loginDto = (OwnerDto) session.getAttribute("loginDto");
			store_code = loginDto.getStore_code();	// 업주의 store_code를 사용
			
			// 조건에 맞는 발주 갯수를 구하기 위한 Map
			Map<String, Object> cntMap = new HashMap<>();
			cntMap.put("store_code", store_code);
			cntMap.put("status_list", statusLists);
			cntMap.put("startDate", startDate);
			cntMap.put("endDate", endDate);
			
			rowDto.setTotal(paoService.paoStatusListRow(cntMap));	// 조건에 맞는 발주 갯수로 전체 수를 set(페이징 처리를 위해) 
			
			// 조건에 맞는 발주 내역을 구하기 위한 Map
			Map<String, Object> map = new HashMap<>();
			
			map.put("store_code", store_code);
			map.put("status_list", statusLists);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("start", rowDto.getStart());
			map.put("end", rowDto.getEnd());
			
			List<PaoDto> paoLists = paoService.paoSelectStatusDate(map);	// 조건에 맞는 발주 내역
		
			System.out.println(paoLists);

			json = objectJson(paoLists, rowDto);	// 조건에 맞는 발주 내역과 그 갯수를 JSONObject 타입으로 만듦
		}
		if (loginDtoAuth.equalsIgnoreCase("A")){ // 담당자
			AdminDto loginDto = (AdminDto) session.getAttribute("loginDto");
			store_code = loginDto.getLoc_code();	// 담당자의 loc_code를 사용
			
			// 조건에 맞는 발주 갯수를 구하기 위한 Map
			Map<String, Object> cntMap = new HashMap<>();
			cntMap.put("store_code", store_code);
			cntMap.put("status_list", statusLists);
			cntMap.put("startDate", startDate);
			cntMap.put("endDate", endDate);
						
			rowDto.setTotal(paoService.adminPaoStatusListRow(cntMap));	// 조건에 맞는 발주 갯수로 전체 수를 set(페이징 처리를 위해) 
						
			// 조건에 맞는 발주 내역을 구하기 위한 Map
			Map<String, Object> map = new HashMap<>();
						
			map.put("store_code", store_code);
			map.put("status_list", statusLists);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("start", rowDto.getStart());
			map.put("end", rowDto.getEnd());
						
			List<PaoDto> paoLists = paoService.adimPaoSelectStatusDate(map);
					
			System.out.println(paoLists);

			json = objectJson(paoLists, rowDto);	// 조건에 맞는 발주 내역과 그 갯수를 JSONObject 타입으로 만듦			
		}
		
		
		
		// 기존 발주 갯수를 지우고 현재 발주 갯수를 set  
		session.removeAttribute("paoRow");
		session.setAttribute("paoRow", rowDto);
		
		//obj.put("paoLists", paoLists);
		//model.addAttribute("paoLists", paoLists);
		//model.addAttribute("paoRow", rowDto);
		
		return json.toString();
	}
	
	// 업주 : 발주 상세조회
	@RequestMapping(value="/paoDetailOpen.do", method=RequestMethod.GET)
	public String paoDetailView(String store_code, String pao_seq, Model model){
		System.out.println("=== 넘겨받은 매장코드 === : "+store_code);
		System.out.println("=== 넘겨받은 발주번호 === : "+pao_seq);
		
		Map<String, String> map = new HashMap<>();
		map.put("store_code", store_code);		
		map.put("pao_seq", pao_seq);
		
		PaoDto paoDto = paoService.paoDetail(map);	// 발주
		
		List<ItemDto> piLists = paoService.paoPiDetail(pao_seq);	// 발주한 품목
		
		System.out.println(paoDto);
		System.out.println(piLists);
		
		model.addAttribute("paoDto", paoDto);
		model.addAttribute("piLists", piLists);
		
		return "/pao/paoDetail";
	}
		
	// 업주 : 발주 신청 시 재고 목록 조회
	@RequestMapping(value="/paoRequestOpen.do", method=RequestMethod.GET, produces="application/text; charset=UTF-8")
	public String paoStockList(String store_code, Model model) {
		System.out.println("=== 넘겨받은 매장코드 === : "+store_code);
		List<StockDto> stockLists = paoService.paoStockList(store_code);
		System.out.println(stockLists);
		
		model.addAttribute("stockLists", stockLists);
		return "/pao/paoRequest";
	}
	
	// 업주 : 발주 신청(발주 테이블 INSERT)
	@RequestMapping(value="/paoRequest.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public void paoRequestInsert(String store_code, String[] item_seqs, String[] pi_qtys) {
		System.out.println("=== 넘겨받은 매장코드 === : "+store_code);
		System.out.println("=== 넘겨받은 재고번호 === : "+item_seqs);
		System.out.println("=== 넘겨받은 수량 === : "+pi_qtys);
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("store_code", store_code);
		
		ItemDto dto = new ItemDto(0, item_seqs, pi_qtys, 0);
		System.out.println("===================== 디티오 =============== : "+dto.getItem_seqs()[0]+" : "+dto.getPi_qtys()[0]);
		int cnt = item_seqs.length;	// 발주 품목 갯수
		
		// 트랜잭션 처리
		boolean isc = paoService.paoRequest(map, dto, item_seqs.length);
		
		if(isc) {
			System.out.println("발주 신청 완료!!");
		}else {
			System.out.println("발주 신청 실패..");
		}
		
		
	}
	
	// 담당자 : 발주 대기 승인 처리
	@RequestMapping(value="/approvePao.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public void adminApprovePao(String pao_seq) {
		System.out.println("=== 넘겨받은 발주번호 === : "+pao_seq);
		
		boolean isc = paoService.approvePao(pao_seq);
		
		if(isc) {
			System.out.println("발주 승인 완료!!");
		}else {
			System.out.println("발주 승인 실패..");
		}
	}
	
	// 업주 : 발주 승인 완료 처리
	@RequestMapping(value="/completePao.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public void ownerCompletePao(String pao_seq) {
		System.out.println("=== 넘겨받은 발주번호 === : "+pao_seq);
			
		boolean isc = paoService.completePao(pao_seq);
			
		if(isc) {
			System.out.println("발주 승인 완료!!");
		}else {
			System.out.println("발주 승인 실패..");
		}
	}
	
	// 업주 : 발주 대기 취소 처리
	@RequestMapping(value="/canclePao.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public void ownerCanclePao(String pao_seq) {
		System.out.println("=== 넘겨받은 발주번호 === : "+pao_seq);
			
		boolean isc = paoService.canclePao(pao_seq);
			
		if(isc) {
			System.out.println("발주 취소 완료!!");
		}else {
			System.out.println("발주 취소 실패..");
		}
	}
	
}
