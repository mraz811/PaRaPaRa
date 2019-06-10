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
	
	@SuppressWarnings("resource")
	@RequestMapping(value="/socketOpen.do", method=RequestMethod.GET)
	public String chattingRoom(String id, String auth, String store_code, HttpSession session, Model model) {
		System.out.println("채팅방 조회 및 생성을 위한 업주/담당자 ID : " + id);
		System.out.println("채팅방 조회 및 생성을 위한 업주/담당자 auth : " + auth);
		ChatDto chatDto = null;
		String store_codeTwo = "";
		StringBuffer content = null;
		if(auth.equalsIgnoreCase("A")) {
			store_codeTwo = store_code;
			System.out.println("담당자로 로그인 시 StoreCode : " + store_codeTwo);
			ChatDto cDto = chatService.selectChatRoom(store_code);
			if(cDto == null) {
				boolean isc = chatService.createChatRoom(store_code);
				System.out.println("채팅방이 없을 시 생성 완료 : " + isc);
				chatDto = chatService.selectChatRoom(store_code);
				System.out.println("채팅방 생성 후 맨들어 진 ChatDto : " + chatDto);
			}else {
				System.out.println("채팅방이 있을 시 ChatDto : " + cDto);
				try {
					File uFile = new File(path + "\\" + store_codeTwo + "_U.txt");
					File aFile = new File(path + "\\" + store_codeTwo + "_A.txt");
					
					FileInputStream uStream = new FileInputStream(uFile);
					FileInputStream aStream = new FileInputStream(aFile);
					System.out.println("업주가 친 채팅 : " + uStream.available());
					System.out.println("담당자가 친 채팅 : " + aStream.available());
					String line = "";
					if (uStream.available() <= aStream.available()) {
						byte[] readBuffer = new byte[aStream.available()];
						while ((aStream.read(readBuffer))!=-1) {
							content = new StringBuffer(new String(readBuffer));
							System.out.println("각 line : " + line);
						}
						System.out.println(content);
					}else {
						byte[] readBuffer = new byte[uStream.available()];
						while(uStream.read(readBuffer)!= -1) {
						}
						line = new String(readBuffer);
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
		if(auth.equalsIgnoreCase("U")) {
			OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
			
			store_codeTwo = oDto.getStore_code();
			System.out.println("업주로 로그인 시 StoreCode : " + store_codeTwo);
			System.out.println("세션에 담긴 oDto 정보 : " + oDto);
			ChatDto cDto = chatService.selectChatRoom(store_codeTwo);
			
			if(cDto == null) {
				boolean isc = chatService.createChatRoom(store_codeTwo);
				System.out.println("채팅방이 없을 시 생성 완료 : " + isc);
				chatDto = chatService.selectChatRoom(store_codeTwo);
				System.out.println("채팅방 생성 후 맨들어 진 ChatDto : " + chatDto);
				
			}else {
				System.out.println("채팅방이 있을 시 ChatDto : " + cDto);
				chatDto = chatService.selectChatRoom(store_codeTwo);
				System.out.println("채팅방이 있을 시 ChatDto : " + cDto);
				try {
					File uFile = new File(path + "\\" + store_codeTwo + "_U.txt");
					File aFile = new File(path + "\\" + store_codeTwo + "_A.txt");
					
					FileInputStream uStream = new FileInputStream(uFile);
					FileInputStream aStream = new FileInputStream(aFile);
					System.out.println("업주가 친 채팅 : " + uStream.available());
					System.out.println("담당자가 친 채팅 : " + aStream.available());
					String line = "";
					if (uStream.available() >= aStream.available()) {
						byte[] readBuffer = new byte[uStream.available()];
						while ((uStream.read(readBuffer))!=-1) {
							content = new StringBuffer(new String(readBuffer));
							System.out.println("각 line : " + line);
						}
						System.out.println(content);
					}else {
						byte[] readBuffer = new byte[aStream.available()];
						while(aStream.read(readBuffer)!= -1) {
						}
						line = new String(readBuffer);
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
		return "common/socket";
	}
	
	@RequestMapping(value="/chatContentUpdate.do", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String updateChat(String chatTitle, String content, String auth) {
		try {
			File fileDir = new File(path);
			if(!fileDir.exists()) {
				fileDir.mkdirs();
			}
			System.out.println("content : " + content);
			File chatFile = new File(path + "\\" + chatTitle+"_"+auth+".txt");
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
		dto.setChat_content(path + "\\" + chatTitle+".txt");

		System.out.println(content);

		boolean isc = chatService.updateChatContent(dto);
		System.out.println(isc);
		return isc == true ? "성공" : "실패";
	}
	
	
	@SuppressWarnings("resource")
	@RequestMapping(value="/poiPao.do", method=RequestMethod.GET)
	public ModelAndView poiPaoDownload(String store_code, String pao_seq) {
		
		Map<String, String> map = new HashMap<>();
		map.put("store_code", store_code);		
		map.put("pao_seq", pao_seq);
		String excelPath = "C:\\paoExcel";
		System.out.println("pao_seq 값 : " + pao_seq);
		System.out.println("store_code 값 : " + store_code);
		PaoDto paoDto = paoService.paoDetail(map);	// 발주
		logger.info("apache Poi 발주 : {}", paoDto);
		List<ItemDto> piLists = paoService.paoPiDetail(pao_seq);	// 발주한 품목
		
		Workbook workBook = new HSSFWorkbook();
		Sheet sheet = workBook.createSheet("발주내역");
		Row row = null;
		Cell cell = null;
		int rowNo = 4;
		CellStyle headStyle = workBook.createCellStyle();
	    headStyle.setBorderTop(BorderStyle.THIN);
	    headStyle.setBorderBottom(BorderStyle.THIN);
	    headStyle.setBorderLeft(BorderStyle.THIN);
	    headStyle.setBorderRight(BorderStyle.THIN);
	    
	    headStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	    headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    headStyle.setAlignment(HorizontalAlignment.CENTER);
	    
	    CellStyle bodyStyleCenter =  workBook.createCellStyle();
	    bodyStyleCenter.setBorderTop(BorderStyle.THIN);
	    bodyStyleCenter.setBorderBottom(BorderStyle.THIN);
	    bodyStyleCenter.setBorderLeft(BorderStyle.THIN);
	    bodyStyleCenter.setBorderRight(BorderStyle.THIN);
	    bodyStyleCenter.setAlignment(HorizontalAlignment.CENTER);
	    
	    CellStyle bodyStyle = workBook.createCellStyle();
	    bodyStyle.setBorderTop(BorderStyle.THIN);
	    bodyStyle.setBorderBottom(BorderStyle.THIN);
	    bodyStyle.setBorderLeft(BorderStyle.THIN);
	    bodyStyle.setBorderRight(BorderStyle.THIN);
	    
	    row = sheet.createRow(0);
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 4));
	    sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 4));
	    
	    cell= row.createCell(0);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("발주번호");
	    cell= row.createCell(1);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("매장명");
	    cell= row.createCell(2);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("발주상태");
	    cell= row.createCell(3);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("날짜");
	    cell= row.createCell(4);
	    cell.setCellStyle(headStyle);
		
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellStyle(bodyStyleCenter);
	    cell.setCellValue(pao_seq);
	    cell= row.createCell(1);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue(paoDto.getStore_name());
	    cell= row.createCell(2);
	    cell.setCellStyle(bodyStyle);
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
	    int cnt = 1;
	    int qtySum = 0;
	    int totalPrice = 0;
	    for (ItemDto dto : piLists) {
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
			qtySum += dto.getPi_qty();
			totalPrice += dto.getPi_qty() * dto.getItem_price();
		}
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
	    
	    for (int i = 0; i < 5; i++) {
	    	sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i)+512);
		}
	    
	    
	    System.out.println("등록일 : " + paoDto.getPao_date());
	    System.out.println("매장코드 : " + paoDto.getStore_code());
	    System.out.println("등록일 바뀐거 : " + paoDto.getPao_date().replaceAll("-", "_").replaceAll(" ", "_"));
	    String pathFinal = excelPath+"\\"+paoDto.getStore_code()+"_"+paoDto.getPao_date().replaceAll("-", "_").replaceAll(" ", "_")+"_pao.xls";
	    System.out.println("서버에 파일이 저장될 경로" + pathFinal);
	    File xlsFile = null;
	    try {
//	    	xls
	    	File fileDir = new File(excelPath);
	    	if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
	    	
	    	xlsFile = new File(excelPath+"\\"+paoDto.getStore_code()+"_"+pao_seq+"_pao.xls");
	    	System.out.println("파일이 되나?");
	    	
	    	FileOutputStream excelFile = new FileOutputStream(xlsFile);
	    	System.out.println("아웃풋스트림이 되나");
	    	
	    	workBook.write(excelFile);
	    	System.out.println("완료가 되나?");
	    }catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		}
	    
	    return new ModelAndView("download","downloadFile", xlsFile);
	}
	
	
	@SuppressWarnings("resource")
	@RequestMapping(value="/poiTimeSheet.do", method=RequestMethod.GET)
	public ModelAndView poiTimeSheetDownload(HttpSession session, TimeDto dto, String ts_date) {
		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		System.out.println("로그인 업주의 store_code : "+store_code);
		String excelPath = "C:\\timeSheetExcel";
		
		Workbook workBook = new HSSFWorkbook();
		Sheet sheet = workBook.createSheet("timesheet");
	
		Row row = null;
		Cell cell = null;
		int rowNo = 0;
		CellStyle headStyle = workBook.createCellStyle();
	    headStyle.setBorderTop(BorderStyle.THIN);
	    headStyle.setBorderBottom(BorderStyle.THIN);
	    headStyle.setBorderLeft(BorderStyle.THIN);
	    headStyle.setBorderRight(BorderStyle.THIN);
	    
	    headStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	    headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    headStyle.setAlignment(HorizontalAlignment.CENTER);
	    
	    CellStyle bodyStyle = workBook.createCellStyle();
	    bodyStyle.setBorderTop(BorderStyle.THIN);
	    bodyStyle.setBorderBottom(BorderStyle.THIN);
	    bodyStyle.setBorderLeft(BorderStyle.THIN);
	    bodyStyle.setBorderRight(BorderStyle.THIN);
	    bodyStyle.setAlignment(HorizontalAlignment.CENTER);
	    
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("이름");
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

		Map<String, String> map = new HashMap<String, String>();
		map.put("store_code", store_code);
		map.put("ts_date", ts_date);
		List<TimeDto> lists = timeSer.tsPoiList(map);
		System.out.println(lists);
		for (TimeDto tDto : lists) {
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);
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
			
//		}
		
		
		for (int i = 0; i < 5; i++) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i)+512);
		}
		
		
	    File xlsFile = null;
	    String pathFinal = excelPath+"\\"+oDto.getStore_code()+"_"+ts_date.replaceAll("-", "_")+"_timesheet.xls";
	    System.out.println("파일이 저장될 경로 : " + pathFinal);
	    try {
//	    	xls
	    	File fileDir = new File(excelPath);
	    	if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
	    	
	    	xlsFile = new File(excelPath+"\\"+oDto.getStore_code()+"_"+ts_date.replaceAll("-", "_")+"_timesheet.xls");
	    	
	    	FileOutputStream fileOut = new FileOutputStream(xlsFile);
	    	workBook.write(fileOut);
	    	
	    }catch (Exception e) {
			// TODO: handle exception
		}
	    
	    return new ModelAndView("download","downloadFile", xlsFile);
	}
	
}
