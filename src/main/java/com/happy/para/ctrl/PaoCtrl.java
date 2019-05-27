package com.happy.para.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.ItemDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.dto.PaoDto;
import com.happy.para.dto.StockDto;
import com.happy.para.model.Pao_IService;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

@Controller
public class PaoCtrl {
	
	@Autowired
	private Pao_IService paoService;
	
	// 업주 : 발주 리스트 조회
	@RequestMapping(value="/selPaoList.do", method=RequestMethod.GET)
	public String paoList(Model model, HttpSession session) {
		OwnerDto loginDto = (OwnerDto) session.getAttribute("loginDto");	// 세션 정보 받아옴
		String store_code = loginDto.getStore_code();	// 로그인한 업주의 매장코드
		
		System.out.println("=== 넘겨받은 매장코드 === : "+store_code);
		
		List<PaoDto> paoLists = paoService.paoList(store_code);	// 발주내역

		System.out.println(paoLists);
		
		model.addAttribute("paoLists", paoLists);
		model.addAttribute("loginDto", loginDto);
		return "pao/paoList";
	}
	
	// 업주 : 발주 리스트 조회
	@RequestMapping(value="/selAdminPaoList.do", method=RequestMethod.GET)
	public String adminPaoList(Model model, HttpSession session) {
		AdminDto loginDto = (AdminDto) session.getAttribute("loginDto");	// 세션 정보 받아옴
		String store_code = loginDto.getLoc_code();	// 로그인한 담당자의 지역코드
			
		System.out.println("=== 넘겨받은 지역코드 === : "+store_code);
			
		List<PaoDto> paoLists = paoService.adminPaoList(store_code);	// 발주내역

		System.out.println(paoLists);
			
		model.addAttribute("paoLists", paoLists);
		model.addAttribute("loginDto", loginDto);
		return "pao/paoList";
	}

	
	// 업주 : 발주 상태 선택 조회 및 매장 발주 날짜 선택 조회
	@RequestMapping(value="/paoStatusAjax.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public String paoSelectStatus(String store_code, String status, String startDate, String endDate, Model model) {
		System.out.println("=== 넘겨받은 매장코드 === : "+store_code);
		System.out.println("=== 넘겨받은 발주 상태 코드 === : "+status);
		System.out.println("=== 넘겨받은 발주 시작일 === : "+startDate);
		System.out.println("=== 넘겨받은 발주 종료일 === : "+endDate);
		
		Map<String, Object> map = new HashMap<>();
		
		String[] statusLists = status.split(",");
		
		// 시작날짜를 선택하지 않았을 때
		if(startDate.equals("") || startDate==null) {
			startDate = "1000-01-01";
		}
		// 종료날짜를 선택하지 않았을 때
		if(endDate.equals("") || endDate==null) {
			endDate = "9999-12-31";
		}
		if( (startDate.equals("") || startDate==null) && (endDate.equals("") || endDate==null)) {
			startDate = "1000-01-01";
			endDate = "9999-12-31";
		}
		
		map.put("store_code", store_code);
		map.put("status_list", statusLists);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		List<PaoDto> paoLists = paoService.paoSelectStatusDate(map);
		System.out.println(paoLists);
		
		JSONObject obj = new JSONObject();
		//obj.putAll(map);
		obj.put("paoLists", paoLists);
		
		model.addAttribute("paoLists", paoLists);
		
		return obj.toString();
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
		
		List<ItemDto> piLists = paoService.paoPiDetail(pao_seq);	// 발주품목
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
	
	// 담당자 : 발주 대기 승인
	@RequestMapping(value="/approvePao.do", method=RequestMethod.POST, produces="application/text; charset=UTF-8")
	@ResponseBody
	public void adminApprovePao(String pao_seq) {
		System.out.println("=== 넘겨받은 발주번호 === : "+pao_seq);
		
		boolean isc = paoService.approvePao(pao_seq);
	}
	
	
}
