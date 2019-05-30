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
	    
	    headStyle.setFillBackgroundColor(HSSFColorPredefined.YELLOW.getIndex());
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
	    cell.setCellValue("일 근무 시간");
//		JSONObject timeObj = null;
//		String timeArr = "";
		
		for (int i = 0; i < albaLists.size(); i++) {

			JSONArray timeAr = new JSONArray();

			dto.setAlba_seq(albaLists.get(i).getAlba_seq());
			
			if(ts_date == null) {
				dto.setTs_date(sdf.format(getDate));				
//				model.addAttribute("today", sdf.format(getDate));
			}else {
				dto.setTs_date(ts_date);								
//				model.addAttribute("today", ts_date);
			}

			List<TimeDto> lists = timeSer.tsList(dto);
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
				cell.setCellValue(tDto.getTs_workhour());
			}
			
		}

	    try {
	    	File xlsFile = new File("C:\\testExcel\\testExcel.xls");
	    	FileOutputStream fileOut = new FileOutputStream(xlsFile);
	    	workBook.write(fileOut);
	    	
	    }catch (Exception e) {
			// TODO: handle exception
		}
	    
	    

		return null;
	}
	
	
}
