package com.happy.para.ctrl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.ChatDto;
import com.happy.para.dto.ItemDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.dto.PaoDto;
import com.happy.para.dto.TimeDto;
import com.happy.para.model.Chat_IService;
import com.happy.para.model.Pao_IService;
import com.happy.para.model.Timesheet_IService;

@Controller
public class CommonCtrl {
	
	//  로그를 찍기위해 선언
	private Logger logger = LoggerFactory.getLogger(CommonCtrl.class);
	// 채팅 내용이 저장될 절대경로
	private final String path = "C:\\chatting";
	
	@Autowired
	private Timesheet_IService timeSer;
	
	@Autowired
	private Chat_IService chatService;
	
	@Autowired
	private Pao_IService paoService;
	
	@Resource(name="chatUploadPath")
	private String chatUploadPath;
	
	
	// 권한에 따른 채팅 목록 조회
	@RequestMapping(value="/chatList.do", method=RequestMethod.GET)
	public String selectChatList(String auth, Model model, HttpSession session) {
		if(auth.equalsIgnoreCase("A")) {
			AdminDto aDto = (AdminDto)session.getAttribute("loginDto");
			System.out.println("담당자 DTO : " + aDto);
			System.out.println("담당자 id : " + aDto.getAdmin_id()+"");
			List<OwnerDto> lists = chatService.selectOwner(aDto.getAdmin_id()+"");
			logger.info("list : {}", lists);
			model.addAttribute("lists", lists);
		}
		if(auth.equalsIgnoreCase("U")) {
			OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
			AdminDto dto = chatService.selectAdmin(oDto.getAdmin_id()+"");
			model.addAttribute("adminDto", dto);
		}
		return "common/chattingList";
	}
	
	
	// 채팅방 입장
	// 고유값인 store_code로 채팅방을 조회
	// 채팅방이 없을 시 DB에 생성
	// 채팅 내역은 db에 담으려고 했으나 html 태그까지 전부 들어가므로 server에 있는 txt파일을 조회
	@SuppressWarnings("resource")
	@RequestMapping(value="/socketOpen.do", method=RequestMethod.GET)
	public String chattingRoom(String id, String auth, String store_code, HttpSession session, Model model) {
		System.out.println("채팅방 조회 및 생성을 위한 업주/담당자 ID : " + id);
		System.out.println("채팅방 조회 및 생성을 위한 업주/담당자 auth : " + auth);
		ChatDto chatDto = null;
		String store_codeTwo = "";
		StringBuffer content = null;
		// 담당자가 채팅방 입장 시
		if(auth.equalsIgnoreCase("A")) {
			store_codeTwo = store_code;
			System.out.println("담당자로 로그인 시 StoreCode : " + store_codeTwo);
			// 채팅방이 db에 있는 지 조회
			ChatDto cDto = chatService.selectChatRoom(store_code);
			// 채팅방이 없을 시
			if(cDto == null) {
				// 채팅방 생성
				boolean isc = chatService.createChatRoom(store_code);
				System.out.println("채팅방이 없을 시 생성 완료 : " + isc);
				chatDto = chatService.selectChatRoom(store_code);
				System.out.println("채팅방 생성 후 맨들어 진 ChatDto : " + chatDto);
			}else {
				System.out.println("채팅방이 있을 시 ChatDto : " + cDto);
				try {
					// 업주가 보는 관점에서의 채팅 내용
					// 자신이 작성한 내용은 우측에, 상대방이 작성한 내용은 좌측에 있어야 함으로 나누어서 저장
					File uFile = new File(path + "\\" + store_codeTwo + "_U.txt");
					// 담당자가 보는 관점에서의 채팅 내용
					File aFile = new File(path + "\\" + store_codeTwo + "_A.txt");
					
					// txt 파일을 읽어옴
					FileInputStream uStream = new FileInputStream(uFile);
					FileInputStream aStream = new FileInputStream(aFile);
					
					// 담당자가 없을 때 업주가 채팅을 칠 수 있으므로 파일의 크기를 비교하여 담당자가 친 채팅 내용의 크기가 더 작을 시 div의 class를 바꿔줄 예정
					System.out.println("업주가 친 채팅 : " + uStream.available());
					System.out.println("담당자가 친 채팅 : " + aStream.available());
					String line = "";
					// 업주가 친 채팅이 담당자가 친 채팅 내용의 크기보다 작을 시
					if (uStream.available() <= aStream.available()) {
						// 바이트 단위로 받아오기 위해 담당자가 친 채팅 내용의 크기만큼 바이트 변수 선언
						byte[] readBuffer = new byte[aStream.available()];
						// 더 이상 읽어 올 글이 없을 때
						while ((aStream.read(readBuffer))!=-1) {
							content = new StringBuffer(new String(readBuffer));
							System.out.println("각 line : " + line);
						}
						System.out.println(content);
					}else {
						// 업주가 담당자가 채팅바에 없을 때 채팅을 친 경우
						// 업주의 채팅 내용의 크기만큼 바이트 배열 선언
						byte[] readBuffer = new byte[uStream.available()];
						
						while(uStream.read(readBuffer)!= -1) {
						}
						// String 타입에 대입
						line = new String(readBuffer);
						// 업주가 보는 관점의 채팅이므로 form-me를 form-other로 바꾸어 줘야함
						line = line.replace("div class=\"form-me\"", "temp$%@");
						line = line.replace("div class=\"form-other\"", "div class=\"form-me\"");
						line = line.replace("temp$%@", "div class=\"form-other\"");
						content = new StringBuffer(line);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				chatDto = chatService.selectChatRoom(store_code);
			}
		}
		// 업주가 채팅방 입장 시
		if(auth.equalsIgnoreCase("U")) {
			// 업주가 로그인 시 session에 담긴 store_code를 받아오기 위해 OwnerDto 객체 생성
			OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
			
			store_codeTwo = oDto.getStore_code();
			System.out.println("업주로 로그인 시 StoreCode : " + store_codeTwo);
			System.out.println("세션에 담긴 oDto 정보 : " + oDto);
			// 채팅방 조회
			ChatDto cDto = chatService.selectChatRoom(store_codeTwo);
			
			// 채팅방 없을 시 
			if(cDto == null) {
				// 채팅방 생성
				boolean isc = chatService.createChatRoom(store_codeTwo);
				System.out.println("채팅방이 없을 시 생성 완료 : " + isc);
				chatDto = chatService.selectChatRoom(store_codeTwo);
				System.out.println("채팅방 생성 후 맨들어 진 ChatDto : " + chatDto);
				
			}else {
				// 채팅방 있을 시
				System.out.println("채팅방이 있을 시 ChatDto : " + cDto);
				chatDto = chatService.selectChatRoom(store_codeTwo);
				System.out.println("채팅방이 있을 시 ChatDto : " + cDto);
				try {
					// 업주가 보는 관점에서의 채팅 내용
					// 자신이 작성한 내용은 우측에, 상대방이 작성한 내용은 좌측에 있어야 함으로 나누어서 저장
					File uFile = new File(path + "\\" + store_codeTwo + "_U.txt");
					// 담당자가 보는 관점에서의 채팅 내용
					File aFile = new File(path + "\\" + store_codeTwo + "_A.txt");
					
					// txt 파일을 읽어옴
					FileInputStream uStream = new FileInputStream(uFile);
					FileInputStream aStream = new FileInputStream(aFile);
					
					// 업주가 없을 때 담당자가 채팅을 칠 수 있으므로 파일의 크기를 비교하여 업주가 친 채팅 내용의 크기가 더 작을 시 div의 class를 바꿔줄 예정
					System.out.println("업주가 친 채팅 : " + uStream.available());
					System.out.println("담당자가 친 채팅 : " + aStream.available());
					String line = "";
					// 업주가 친 채팅 내용이 더 클 시
					if (uStream.available() >= aStream.available()) {
						// 바이트 단위로 읽어오므로 업주가 친 채팅 내용의 바이트 크기만큼 바이트 배열 크기 선언
						byte[] readBuffer = new byte[uStream.available()];
						// 더 이상 읽어 올 글이 없을 때
						while ((uStream.read(readBuffer))!=-1) {
							content = new StringBuffer(new String(readBuffer));
							System.out.println("각 line : " + line);
						}
						System.out.println(content);
					}else {
						// 업주가 채팅방에 없을 때 담당자가 채팅을 친 경우
						// 담당자의 채팅 내용의 크기가 더 큼
						// 담당자의 채팅 내용의 바이트 크기만큼 바이트 배열 선언
						byte[] readBuffer = new byte[aStream.available()];
						while(aStream.read(readBuffer)!= -1) {
						}
						line = new String(readBuffer);
						// 담당자가 보는 관점의 채팅이므로 form-me를 form-other로 바꾸어 줘야함
						line = line.replace("div class=\"form-me\"", "temp$%@");
						
						line = line.replace("div class=\"form-other\"", "div class=\"form-me\"");
						line = line.replace("temp$%@", "div class=\"form-other\"");
						content = new StringBuffer(line);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
		System.out.println("if문 밖에서 찍어본 Store_code : " + store_codeTwo);
		System.out.println("if문 밖에서 찍어본 chatDto : " + chatDto);
		System.out.println("if문 밖에서 찍어본 targer : " + id);
		System.out.println("if문 밖에서 찍어본 content : " + content);
		model.addAttribute("store_code", store_codeTwo);
		model.addAttribute("chatDto", chatDto);
		model.addAttribute("content", content);
		model.addAttribute("target", id);
		return "common/chattingDetail";
	}
	
	// 채팅입력 시 호출되는 메소드
	// 권한에 따라 채팅 내용 따로 local에 저장
	@RequestMapping(value="/chatContentUpdate.do", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String updateChat(String chatTitle, String content, String auth) {
		try {
			File fileDir = new File(path);
			// 채팅 내용을 저장할 폴더가 없을 시 폴더 생성
			if(!fileDir.exists()) {
				fileDir.mkdirs();
			}
			System.out.println("content : " + content);
			// store_code_권한 형식으로 채팅 내용을 권한에 따라 나누어 저장
			File chatFile = new File(path + "\\" + chatTitle+"_"+auth+".txt");
			// 로컬에 파일 작성
			BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(chatFile));
			bufferWriter.write(content);
			bufferWriter.flush();
			bufferWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		ChatDto dto = new ChatDto();
		// 받은 chatmember 값을 정렬하기 위해 배열로 만듦
		dto.setChat_title(chatTitle);
		// 채팅 위치를 db에 저장
		dto.setChat_content(path + "\\" + chatTitle+".txt");

		System.out.println(content);
		
		
		boolean isc = chatService.updateChatContent(dto);
		System.out.println(isc);
		return isc == true ? "성공" : "실패";
	}
	
	
	// 발주내역을 excel 파일로 저장하기 위한 apache Poi Api
	@SuppressWarnings("resource")
	@RequestMapping(value="/poiPao.do", method=RequestMethod.GET)
	public ModelAndView poiPaoDownload(String store_code, String pao_seq) {
		
		
		// 발주는 발주에 대한 정보, 발주품목에 대한 정보가 있음
		// 발주에 대한 정보를 가져오기 위한 map 선언
		Map<String, String> map = new HashMap<>();
		map.put("store_code", store_code);		
		map.put("pao_seq", pao_seq);
		String excelPath = "C:\\paoExcel";
		System.out.println("pao_seq 값 : " + pao_seq);
		System.out.println("store_code 값 : " + store_code);
		PaoDto paoDto = paoService.paoDetail(map);	// 발주
		logger.info("apache Poi 발주 : {}", paoDto);
		List<ItemDto> piLists = paoService.paoPiDetail(pao_seq);	// 발주한 품목
		
		// excel 파일 생성
		Workbook workBook = new HSSFWorkbook();
		// 생성한 excel 파일에 sheet 생성, sheet 명은 "발주내역"
		Sheet sheet = workBook.createSheet("발주내역");
		
		// 한 sheet에 한 Row 단위로 생성
		Row row = null;
		// 한 Row에 한 Cell 단위로 생성
		Cell cell = null;
		// 발주 품목에 대한 부분이 시작하는 Row의 index 번호 선언
		int rowNo = 4;
		
		// 헤더의 CellStyle을 선언
		CellStyle headStyle = workBook.createCellStyle();
		// 각 Cell의 Border를 선언, top, bottom, left, right에 모두 선 표시
	    headStyle.setBorderTop(BorderStyle.THIN);
	    headStyle.setBorderBottom(BorderStyle.THIN);
	    headStyle.setBorderLeft(BorderStyle.THIN);
	    headStyle.setBorderRight(BorderStyle.THIN);
	    
	    // 헤더는 노란색으로 색 채움
	    headStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	    headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    // 헤더의 내용은 중앙정렬
	    headStyle.setAlignment(HorizontalAlignment.CENTER);
	    
	    // body 중 중앙 정렬을 하는 cell 스타일
	    CellStyle bodyStyleCenter =  workBook.createCellStyle();
	    // 헤더와 같이 모든 선 표시
	    bodyStyleCenter.setBorderTop(BorderStyle.THIN);
	    bodyStyleCenter.setBorderBottom(BorderStyle.THIN);
	    bodyStyleCenter.setBorderLeft(BorderStyle.THIN);
	    bodyStyleCenter.setBorderRight(BorderStyle.THIN);
	    // 내용 중앙 정렬
	    bodyStyleCenter.setAlignment(HorizontalAlignment.CENTER);
	    
	    // 중앙 정렬하지 않는 body 스타일
	    CellStyle bodyStyle = workBook.createCellStyle();
	    // 헤더와 같이 모든 선 표시
	    bodyStyle.setBorderTop(BorderStyle.THIN);
	    bodyStyle.setBorderBottom(BorderStyle.THIN);
	    bodyStyle.setBorderLeft(BorderStyle.THIN);
	    bodyStyle.setBorderRight(BorderStyle.THIN);
	    
	    // 한 sheet의 0번째 index 번호의 row를 생성
	    row = sheet.createRow(0);
	    // cell 병합 선언, 처음 두 개의 파라미터는 Row의 index 번호
	    // 뒤의 두 개의 파라미터는 Cell의 index 번호 
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 4));
	    sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 4));
	    
	    // 0번재 index의 row에 0번째 index의 cell 생성
	    cell= row.createCell(0);
	    // 헤더 부분이므로 헤더 스타일을 적용
	    cell.setCellStyle(headStyle);
	    // 값은 발주번호를 지정
	    cell.setCellValue("발주번호");
	    // 0번재 index의 row에 1번째 index의 cell 생성
	    cell= row.createCell(1);
	    // 헤더 부분이므로 헤더 스타일을 적용
	    cell.setCellStyle(headStyle);
	    // 값은 매장명을 지정
	    cell.setCellValue("매장명");
	    // 0번재 index의 row에 2번째 index의 cell 생성
	    cell= row.createCell(2);
	    // 헤더 부분이므로 헤더 스타일을 적용
	    cell.setCellStyle(headStyle);
	    // 값은 발주상태를 지정
	    cell.setCellValue("발주상태");
	    // 0번재 index의 row에 3번째 index의 cell 생성
	    cell= row.createCell(3);
	    // 헤더 부분이므로 헤더 스타일을 적용
	    cell.setCellStyle(headStyle);
	    // 값은 날짜를 지정
	    cell.setCellValue("날짜");
	    // 0번재 index의 row에 4번째 index의 cell 생성
	    cell= row.createCell(4);
	    // 값은 병합되는 cell이므로 지정하지 않음
	    cell.setCellStyle(headStyle);
		
	    
	    // 한 sheet의 1번째 index 번호의 row를 생성
		row = sheet.createRow(1);
		// 1번재 index의 row에 0번째 index의 cell 생성
		cell = row.createCell(0);
		// body 부분이므로 body 스타일을 적용
		cell.setCellStyle(bodyStyleCenter);
		// 값은 조회한 발주 정보의 발주 번호를 지정
	    cell.setCellValue(pao_seq);
	    // 위와 동일
	    cell= row.createCell(1);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue(paoDto.getStore_name());
	    cell= row.createCell(2);
	    cell.setCellStyle(bodyStyle);
	    // 각 발주 상태 코드에 맞는 값 지정
	    if(paoDto.getPs_code() == 1) {
	    	cell.setCellValue("발주대기");
	    }
	    if(paoDto.getPs_code() == 2) {
	    	cell.setCellValue("발주승인");
	    }
	    if(paoDto.getPs_code() == 3) {
	    	cell.setCellValue("발주완료");
	    }
	    if(paoDto.getPs_code() == 0) {
	    	cell.setCellValue("발주취소");
	    }
	    cell= row.createCell(3);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue(paoDto.getPao_date());
	    cell= row.createCell(4);
	    cell.setCellStyle(bodyStyle);
	    
	    // 발주 품목의 헤더 정보를 위한 row 생성
	    // 위와 동일한 방식으로 진행
	    row = sheet.createRow(3);
	    cell= row.createCell(0);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("번호");
	    cell= row.createCell(1);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("품목명");
	    cell= row.createCell(2);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("수량");
	    cell= row.createCell(3);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("가격");
	    cell= row.createCell(4);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("합계금액");
	    // 발주 품목의 순번을 지정해주기 위해 선언
	    int cnt = 1;
	    // 총 발주 품목의 갯수를 저장하기 위해 선언
	    int qtySum = 0;
	    // 발주 품목의 총 가격을 저장하기 위해 선언
	    int totalPrice = 0;
	    // 발주 품목이 담긴 piLists를 각 ItemDto 객체에 담음
	    for (ItemDto dto : piLists) {
	    	// 위에 선언한 rowNo를 증가 시킴
	    	// 방식은 위와 동일
	    	row = sheet.createRow(rowNo++);
	    	cell= row.createCell(0);
	    	cell.setCellStyle(bodyStyleCenter);
	    	cell.setCellValue(cnt++);
	    	cell= row.createCell(1);
	    	cell.setCellStyle(bodyStyle);
	    	cell.setCellValue(dto.getItem_name());
	    	cell= row.createCell(2);
	    	cell.setCellStyle(bodyStyle);
	    	cell.setCellValue(dto.getPi_qty());
	    	cell= row.createCell(3);
	    	cell.setCellStyle(bodyStyle);
	    	cell.setCellValue(dto.getItem_price());
	    	cell= row.createCell(4);
	    	cell.setCellStyle(bodyStyle);
	    	cell.setCellValue(dto.getPi_qty() * dto.getItem_price());
	    	// 발주 품목 총 갯수를 누적
			qtySum += dto.getPi_qty();
			// 발주 품목의 총 가격을 누적
			totalPrice += dto.getPi_qty() * dto.getItem_price();
		}
	    
	    // body와 한줄 띄워주기 위해 ++rowNo 사용
	    // 방식은 위와 동일
	    row = sheet.createRow(++rowNo);
	    cell = row.createCell(0);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("합계");
	    cell = row.createCell(1);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("수량");
	    cell = row.createCell(2);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue(qtySum);
	    cell = row.createCell(3);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("총금액");
	    cell = row.createCell(4);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue(totalPrice);
	    
	    // cell 크기를 자동 정렬해주기 위한 반복문
	    // 범위는 최대 열 수
	    for (int i = 0; i < 5; i++) {
	    	// 자동 크기 맞춤을 할 열 번호
	    	sheet.autoSizeColumn(i);
	    	// 자동 크기 맞춤을 할 열 번호 당 크기
	    	// 512를 + 해준 이유는 딱 맞으면 여유가 없기 때문에 추가 해줌
			sheet.setColumnWidth(i, sheet.getColumnWidth(i)+512);
		}
	    
	    
	    System.out.println("등록일 : " + paoDto.getPao_date());
	    System.out.println("매장코드 : " + paoDto.getStore_code());
	    System.out.println("등록일 바뀐거 : " + paoDto.getPao_date().replaceAll("-", "_").replaceAll(" ", "_"));
	    String pathFinal = excelPath+"\\"+paoDto.getStore_code()+"_"+paoDto.getPao_date().replaceAll("-", "_").replaceAll(" ", "_")+"_pao.xls";
	    System.out.println("서버에 파일이 저장될 경로" + pathFinal);
	    File xlsFile = null;
	    try {
	    	// excel 파일이 저장될 경로
	    	File fileDir = new File(excelPath);
	    	// 폴더가 없을 시
	    	if (!fileDir.exists()) {
	    		// 폴더 생성
				fileDir.mkdirs();
			}
	    	
	    	// 발주를 신청한 매장 코드 번호_발주 번호_pao를 제목으로 파일 저장
	    	xlsFile = new File(excelPath+"\\"+paoDto.getStore_code()+"_"+pao_seq+"_pao.xls");
	    	System.out.println("파일이 되나?");
	    	
	    	FileOutputStream excelFile = new FileOutputStream(xlsFile);
	    	System.out.println("아웃풋스트림이 되나");
	   
	    	// 서버에 저장
	    	workBook.write(excelFile);
	    	System.out.println("완료가 되나?");
	    }catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		}
	    // download를 할 시 접속한 사용자에게 파일이 다운로드가 되도록 함
	    // com.happy.para.common package에 DownloadView bean Class 호출 
	    return new ModelAndView("download","downloadFile", xlsFile);
	}
	
	
	// 아르바이트의 일별 타임시트 excel 파일 apache Poi Api를 사용하여 다운로드
	@SuppressWarnings("resource")
	@RequestMapping(value="/poiTimeSheet.do", method=RequestMethod.GET)
	public ModelAndView poiTimeSheetDownload(HttpSession session, TimeDto dto, String ts_date) {
		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		System.out.println("로그인 업주의 store_code : "+store_code);
		String excelPath = "C:\\timeSheetExcel";
		
		// excel 파일 생성
		Workbook workBook = new HSSFWorkbook();
		// 생성한 excel파일에 sheet 생성 
		// sheet 명은 timesheet
		Sheet sheet = workBook.createSheet("timesheet");
		
		// 한 sheet에 한 Row 단위로 생성
		Row row = null;
		// 한 Row에 한 Cell 단위로 생성
		Cell cell = null;
		// header 부터 body 끝까지 row의 index 번호를 위해 선언
		int rowNo = 0;
		// header cell 의 style 지정
		CellStyle headStyle = workBook.createCellStyle();
		// 각 header cell의 선을 지정
		// 모든 선 표시
	    headStyle.setBorderTop(BorderStyle.THIN);
	    headStyle.setBorderBottom(BorderStyle.THIN);
	    headStyle.setBorderLeft(BorderStyle.THIN);
	    headStyle.setBorderRight(BorderStyle.THIN);
	    
	    // 각 header cell을 노란색으로 채움
	    headStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	    headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    // 각 header cell은 중앙 정렬
	    headStyle.setAlignment(HorizontalAlignment.CENTER);
	    
	    // 각 body cell의 style 지정
	    CellStyle bodyStyle = workBook.createCellStyle();
	    // 각 body cell의 선을 지정
	    // 모든 선 표시
	    bodyStyle.setBorderTop(BorderStyle.THIN);
	    bodyStyle.setBorderBottom(BorderStyle.THIN);
	    bodyStyle.setBorderLeft(BorderStyle.THIN);
	    bodyStyle.setBorderRight(BorderStyle.THIN);
	    // 각 body cell은 중앙 정렬
	    bodyStyle.setAlignment(HorizontalAlignment.CENTER);
	    
	    // index 번호가 0번인 row 생성
	    // header 내용이 들어갈 예정
	    row = sheet.createRow(rowNo++);
	    // 0번째 index 번호의 row에 0번째 index 번호의 cell 생성
	    cell = row.createCell(0);
	    // header 부분이므로 headStyle 적용
	    cell.setCellStyle(headStyle);
	    // 값은 이름
	    cell.setCellValue("이름");
	    // 위와 동일
	    cell = row.createCell(1);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("근무일");
	    cell = row.createCell(2);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("근무시간");
	    cell = row.createCell(3);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("주간 근무 시간");
	    cell = row.createCell(4);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("야간 근무 시간");
		dto.setTs_date(ts_date);								
		
		// 매장 별 일 별 아르바이트의 근무 시간 조회
		Map<String, String> map = new HashMap<String, String>();
		map.put("store_code", store_code);
		map.put("ts_date", ts_date);
		List<TimeDto> lists = timeSer.tsPoiList(map);
		System.out.println(lists);
		for (TimeDto tDto : lists) {
			// 위에 header 부분에서 rowNo가 1로 변함
			// 1번째 index 번호의 row를 생성
			// 1씩 index 번호가 증가함
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);
			// body 부분이므로 bodyStyle을 지정
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(tDto.getTs_seq());
			cell = row.createCell(1);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(tDto.getTs_date());
			cell = row.createCell(2);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(tDto.getTs_datetime());
			cell = row.createCell(3);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(tDto.getTs_daywork());
			cell = row.createCell(4);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(tDto.getTs_nightwork());
		}
		
		
		// 각 열의 cell들의 크기를 자동 지정
		for (int i = 0; i < 5; i++) {
			// 0번 부터 4번째 열의 cell size를 자동으로 지정하겠다는 의미
			sheet.autoSizeColumn(i);
			// 0번 부터 4번째 열의 cell size에서 512만큼 추가함
			// 딱 맞으면 값이 조금 잘리는 경우가 있어서 여유를 줌
			sheet.setColumnWidth(i, sheet.getColumnWidth(i)+512);
		}
		
		
	    File xlsFile = null;
	    // stoer_code_작성일_timesheet 이름으로 파일 작성
	    // 작성일이 2019-06-12 이런 형식으로 되어있기 때문에 - 를 _ 로 바꿈
	    String pathFinal = excelPath+"\\"+oDto.getStore_code()+"_"+ts_date.replaceAll("-", "_")+"_timesheet.xls";
	    System.out.println("파일이 저장될 경로 : " + pathFinal);
	    try {
	    	File fileDir = new File(excelPath);
	    	// 폴더 경로가 없을 시
	    	if (!fileDir.exists()) {
	    		// 폴더 생성
				fileDir.mkdirs();
			}
	    	
//	    	xlsFile = new File(excelPath+"\\"+oDto.getStore_code()+"_"+ts_date.replaceAll("-", "_")+"_timesheet.xls");
	    	// 위에서 지정한 최종 경로로 파일 저장
	    	xlsFile = new File(pathFinal);
	    	
	    	FileOutputStream fileOut = new FileOutputStream(xlsFile);
	    	// server에 저장
	    	workBook.write(fileOut);
	    	
	    }catch (Exception e) {
			// TODO: handle exception
		}
	    
	    // 사용자 local에 저장
	    return new ModelAndView("download","downloadFile", xlsFile);
	}
	
}
