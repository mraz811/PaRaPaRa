package com.happy.para.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	// 리눅스 기준으로 파일 경로를 작성 ( 루트 경로인 /으로 시작한다. )
	// 윈도우라면 workspace의 드라이브를 파악하여 JVM이 알아서 처리해준다.
	// 따라서 workspace가 C드라이브에 있다면 C드라이브에 upload 폴더를 생성해 놓아야 한다.
	private static final String SAVE_PATH = "upload";
	private static final String PREFIX_URL = "upload\\";
	
	public Map<String, String> restore(MultipartFile multipartFile,String relativePath) {
		String aurl = "C:\\Users\\PC\\git\\PaRaPaRa\\src\\main\\webapp\\"; //절대 경로
		String rurl = null; //상대 경로
		Map<String, String> map = new HashMap<String,String>();
 		try {
			// 파일 정보
			String originFilename = multipartFile.getOriginalFilename();
			String extName
				= originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
			Long size = multipartFile.getSize();
			
			// 서버에서 저장 할 파일 이름
			String saveFileName = genSaveFileName(extName);
			
			System.out.println("originFilename : " + originFilename);
			System.out.println("extensionName : " + extName);
			System.out.println("size : " + size);
			System.out.println("saveFileName : " + saveFileName);
			
			writeFile(multipartFile, saveFileName, relativePath, aurl);
			rurl = relativePath + PREFIX_URL + saveFileName;
			aurl = aurl + PREFIX_URL + saveFileName;
			map.put("file_aurl", aurl); //절대 경로
			map.put("file_rurl", rurl); //상대 경로
			map.put("file_tname", saveFileName);
			map.put("file_rname", originFilename);
			map.put("file_size", size.toString());
		}
		catch (IOException e) {
			// 원래라면 RuntimeException 을 상속받은 예외가 처리되어야 하지만
			// 편의상 RuntimeException을 던진다.
			// throw new FileUploadException();	
			throw new RuntimeException(e);
		}
		return map;
	}
	
	
	// 현재 시간을 기준으로 파일 이름 생성
	private String genSaveFileName(String extName) {
		String fileName = "";
		
		Calendar calendar = Calendar.getInstance();
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += extName;
		
		return fileName;
	}
	
	
	// 파일을 실제로 write 하는 메서드
	private boolean writeFile(MultipartFile multipartFile, String saveFileName, String relativePath,String absolutPath)
								throws IOException{
		boolean result = false;

		byte[] data = multipartFile.getBytes();
		FileOutputStream fosRelative = new FileOutputStream(relativePath+SAVE_PATH + "\\" + saveFileName);
		fosRelative.write(data);
		fosRelative.close();
		System.out.println("저장된 상대 경로 : " + relativePath + SAVE_PATH + "\\" + saveFileName);
		FileOutputStream fosAbsolut = new FileOutputStream(absolutPath + SAVE_PATH + "\\" + saveFileName);
		fosAbsolut.write(data);
		fosAbsolut.close();
		System.out.println("저장된 절대 경로 : " + absolutPath + SAVE_PATH + "\\" + saveFileName);
		return result;
	}
}