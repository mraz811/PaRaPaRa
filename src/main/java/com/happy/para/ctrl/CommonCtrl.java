package com.happy.para.ctrl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.AlbaDto;
import com.happy.para.dto.ChatDto;
import com.happy.para.dto.FileDto;
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
			List<OwnerDto> lists = chatService.selectOwner(aDto.getAdmin_id()+"");
			model.addAttribute("lists", lists);
		}
		if(auth.equalsIgnoreCase("U")) {
			OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
			AdminDto dto = chatService.selectAdmin(oDto.getAdmin_id()+"");
			model.addAttribute("adminDto", dto);
		}
		return "common/chattingList";
	}
	
	@RequestMapping(value="/socketOpen.do", method=RequestMethod.GET)
	public String chattingRoom(String id, String auth, String store_code, HttpSession session, Model model) {
		System.out.println("채팅방 조회 및 생성을 위한 업주/담당자 ID : " + id);
		System.out.println("채팅방 조회 및 생성을 위한 업주/담당자 auth : " + auth);
		ChatDto chatDto = null;
		String store_codeTwo = "";
		String content = null;
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
				File file = new File(path + "\\" + store_code + ".txt");
				try {
					FileInputStream inputStream = new FileInputStream(file);
					byte[] readBuffer = new byte[inputStream.available()];
					while(inputStream.read(readBuffer)!= -1) {
					}
					content = new String(readBuffer);
					
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
				File file = new File(path + "\\" + store_codeTwo + ".txt");
				try {
					FileInputStream inputStream = new FileInputStream(file);
					byte[] readBuffer = new byte[inputStream.available()];
					while(inputStream.read(readBuffer)!= -1) {
					}
					content = new String(readBuffer);
					
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
	public String updateChat(String chatTitle, String content) {
		try {
			File fileDir = new File(path);
			if(!fileDir.exists()) {
				fileDir.mkdirs();
			}
			File chatFile = new File(path + "\\" + chatTitle+".txt");
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
	
	@RequestMapping(value="/regiFile.do", method=RequestMethod.POST, produces="application/text; charset-utf-8;")
	@ResponseBody
	public String fileUpload(FileDto dto,  MultipartHttpServletRequest mtsRequest, String chat_seq) throws IOException {
		logger.info("file Upload Controller");
		boolean isc = false;
		Iterator<String> itr = mtsRequest.getFileNames();
		System.out.println("파일이름 : " + itr);
		String originalName = null;
		while(itr.hasNext()) {
			MultipartFile file = mtsRequest.getFile(itr.next());
			originalName = file.getOriginalFilename();
			String savedName = "";
			System.out.println("Orginal Name : " + originalName);
			// 이름이 겹치지 않기위해 랜덤 생성
			UUID uuid = UUID.randomUUID();
			savedName = uuid.toString()+"_"+originalName;
			File dir = new File(chatUploadPath);
			File target = new File(chatUploadPath, savedName);
			// 폴더가 없다면 폴더를 생성
			if(!dir.exists()) {
				dir.mkdirs();
			}
			
			// 파일을 서버에 저장
			FileCopyUtils.copy(file.getBytes(), target);
			
			// argument로 받아온 dto에 파일 이름이 들어가 있지 않아서 직접 set
			dto.setFile_rname(originalName);
			dto.setFile_tname(savedName);
			isc = chatService.uploadFile(dto);
			System.out.println("파일업로드 성공 : " + isc);
		}
		return chatUploadPath + "\\" + originalName;
	}
	
	@RequestMapping(value="/poiPao.do", method=RequestMethod.GET)
	public ModelAndView poiPaoDownload(String store_code, String pao_seq) {
		
		Map<String, String> map = new HashMap<>();
		map.put("store_code", store_code);		
		map.put("pao_seq", pao_seq);
		String excelPath = "C:\\paoExcel";
		System.out.println("pao_seq 값 : " + pao_seq);
		System.out.println("store_code 값 : " + store_code);
		PaoDto paoDto = paoService.paoDetail(map);	// 발주
		
		List<ItemDto> piLists = paoService.paoPiDetail(pao_seq);	// 발주한 품목
		
		Workbook workBook = new HSSFWorkbook();
		Sheet sheet = workBook.createSheet("pao");
		Row row = null;
		Cell cell = null;
		int rowNo = 4;
		CellStyle headStyle = workBook.createCellStyle();
	    headStyle.setBorderTop(BorderStyle.THIN);
	    headStyle.setBorderBottom(BorderStyle.THIN);
	    headStyle.setBorderLeft(BorderStyle.THIN);
	    headStyle.setBorderRight(BorderStyle.THIN);
	    
//	    headStyle.setFillBackgroundColor(HSSFColorPredefined.YELLOW.getIndex());
	    headStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	    headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    headStyle.setAlignment(HorizontalAlignment.CENTER);
	    
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
		cell.setCellStyle(bodyStyle);
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
	    	cell.setCellStyle(bodyStyle);
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
	
	
	@RequestMapping(value="/poiTimeSheet.do", method=RequestMethod.GET)
	public ModelAndView poiTimeSheetDownload(HttpSession session, TimeDto dto, String ts_date) {
		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		System.out.println("로그인 업주의 store_code : "+store_code);
		String excelPath = "C:\\timeSheetExcel";
		List<AlbaDto> albaLists = timeSer.tsAlba(store_code);
		System.out.println("로그인 업주의 albaLists : "+albaLists);

		Date getDate = new Date();
		String today = getDate.toString();
		System.out.println("현재날짜 : "+ today);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("현재날짜 : "+ sdf.format(getDate));
		System.out.println("today : "+sdf.format(getDate));
		
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
	    
//	    headStyle.setFillBackgroundColor(HSSFColorPredefined.YELLOW.getIndex());
	    headStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	    headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    headStyle.setAlignment(HorizontalAlignment.CENTER);
	    
	    CellStyle bodyStyle = workBook.createCellStyle();
	    bodyStyle.setBorderTop(BorderStyle.THIN);
	    bodyStyle.setBorderBottom(BorderStyle.THIN);
	    bodyStyle.setBorderLeft(BorderStyle.THIN);
	    bodyStyle.setBorderRight(BorderStyle.THIN);
	    
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("타임시트 번호");
	    cell = row.createCell(1);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("알바 번호");
	    cell = row.createCell(2);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("근무일");
	    cell = row.createCell(3);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("근무시간");
	    cell = row.createCell(4);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("주간 근무 시간");
	    cell = row.createCell(5);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("야간 근무 시간");
//		JSONObject timeObj = null;
//		String timeArr = "";
		
		for (int i = 0; i < albaLists.size(); i++) {

//			JSONArray timeAr = new JSONArray();

			dto.setAlba_seq(albaLists.get(i).getAlba_seq());
			
			if(ts_date == null) {
				dto.setTs_date(sdf.format(getDate));				
//				model.addAttribute("today", sdf.format(getDate));
			}else {
				dto.setTs_date(ts_date);								
//				model.addAttribute("today", ts_date);
			}

			List<TimeDto> lists = timeSer.tsList(dto);
			System.out.println(lists);
			for (TimeDto tDto : lists) {
				row = sheet.createRow(rowNo++);
				cell = row.createCell(0);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(tDto.getTs_seq());
				cell = row.createCell(1);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(tDto.getAlba_seq());
				cell = row.createCell(2);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(tDto.getTs_date());
				cell = row.createCell(3);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(tDto.getTs_datetime());
				cell = row.createCell(4);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(tDto.getTs_daywork());
				cell = row.createCell(5);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(tDto.getTs_nightwork());
			}
			
		}
		int albaRow = 0;
		Sheet alba = workBook.createSheet("아르바이트");
		row = alba.createRow(albaRow++);
	    cell = row.createCell(0);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("알바 번호");
	    cell = row.createCell(1);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("이름");
	    cell = row.createCell(2);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("전화번호");
	    cell = row.createCell(3);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("주소");
	    cell = row.createCell(4);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("시급");
	    cell = row.createCell(5);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("은행명");
	    cell = row.createCell(6);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("계좌번호");
	    cell = row.createCell(7);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("퇴사여부");
	    cell = row.createCell(8);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("근무 시작일");
	    for (AlbaDto aDto : albaLists) {
	    	row = alba.createRow(albaRow++);
	    	cell = row.createCell(0);
	    	cell.setCellStyle(bodyStyle);
	    	cell.setCellValue(aDto.getAlba_seq());
	    	cell = row.createCell(1);
	    	cell.setCellStyle(bodyStyle);
	    	cell.setCellValue(aDto.getAlba_name());
	    	cell = row.createCell(2);
	    	cell.setCellStyle(bodyStyle);
	    	cell.setCellValue(aDto.getAlba_phone());
	    	cell = row.createCell(3);
	    	cell.setCellStyle(bodyStyle);
	    	cell.setCellValue(aDto.getAlba_address());
	    	cell = row.createCell(4);
	    	cell.setCellStyle(bodyStyle);
	    	cell.setCellValue(aDto.getAlba_timesal());
	    	cell = row.createCell(5);
	    	cell.setCellStyle(bodyStyle);
	    	cell.setCellValue(aDto.getAlba_bank());
	    	cell = row.createCell(6);
	    	cell.setCellStyle(bodyStyle);
	    	cell.setCellValue(aDto.getAlba_account());
	    	cell = row.createCell(7);
	    	cell.setCellStyle(bodyStyle);
	    	if (aDto.getAlba_delflag().equalsIgnoreCase("Y")) {
	    		cell.setCellValue("퇴사자");
	    	}else {
	    		cell.setCellValue("입사자");
	    	}
	    	cell = row.createCell(8);
	    	cell.setCellStyle(bodyStyle);
	    	cell.setCellValue(aDto.getAlba_regdate());
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
