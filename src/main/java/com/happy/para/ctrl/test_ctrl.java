package com.happy.para.ctrl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.happy.para.dto.AlbaDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.dto.TimeDto;
import com.happy.para.model.Timesheet_IService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class test_ctrl {

	@Autowired
	private Timesheet_IService timeSer;
	
	@RequestMapping(value="/test.do", method=RequestMethod.GET)
	public String test() {
		return "test";
	}
	
	@RequestMapping(value="/poiTest.do", method=RequestMethod.GET)
	public String poiTest(HttpSession session, TimeDto dto, String ts_date) {
		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		String store_code = oDto.getStore_code();
		System.out.println("로그인 업주의 store_code : "+store_code);
		String excelPath = "C:\\testExcel";
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
	    try {
//	    	xls
	    	File fileDir = new File(excelPath);
	    	if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
	    	
	    	File xlsFile = new File(excelPath+"\\"+oDto.getStore_code()+"-"+ts_date+".xls");
	    	FileOutputStream fileOut = new FileOutputStream(xlsFile);
	    	workBook.write(fileOut);
	    	
	    }catch (Exception e) {
			// TODO: handle exception
		}
	    
	    

		return null;
	}
	
	
}
