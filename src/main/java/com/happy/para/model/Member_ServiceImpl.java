package com.happy.para.model;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.happy.para.common.MailUtils;
import com.happy.para.dto.AdminDto;
import com.happy.para.dto.OwnerDto;

@Service
public class Member_ServiceImpl implements Member_IService {
	//***************** 조민지 - 회원(업주,담당자)관리를 위한 Service ***************// 

	private Logger logger = LoggerFactory.getLogger(Member_ServiceImpl.class);
	
	@Autowired
	private Member_IDao member_IDao;

	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public AdminDto adminLogin(AdminDto aDto) {
		logger.info("adminLogin Service : {} ", aDto);
		return member_IDao.adminLogin(aDto);
	}

	@Override
	public OwnerDto ownerLogin(OwnerDto oDto) {
		logger.info("ownerLogin Service : {} ", oDto);
		return member_IDao.ownerLogin(oDto);
	}

	@Override
	public int findAdminPw(Map<String, String> map) {
		logger.info("findAdminPw Service : {} ", map);
		// 임시 비밀번호를 이메일로 보내주어야 함
		
		// 메일 내용
		String mailContent = new StringBuffer().append("<h1>임시 비밀번호 안내</h1>")
				.append("<p>아래 비밀번호로 로그인 후 비밀번호 변경해주세요</p>")
				.append("임시 비밀번호: " + map.get("temp_pw")).toString();
		
		try {
			MailUtils sendMail = new MailUtils(mailSender);
			// 메일 제목
			sendMail.setSubject("[PaRaPaRa 비밀번호 찾기]");
			// 메일 내용(html)
			sendMail.setText(mailContent);
			// 메일 보내는 계정
			sendMail.setFrom("happyproject8811@gmail.com", "파라파라");
			// 메일 받는 사람
			sendMail.setTo(map.get("admin_email"));
			
			sendMail.send();
			
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return member_IDao.findAdminPw(map);
	}

	@Override
	public int findOwnerPw(Map<String, String> map) {
		logger.info("findOwnerPw Service : {} ", map);
		// 여기에서 임시 비밀번호를 이메일로 보내주어야 함
		String mailContent = new StringBuffer().append("<h1>임시 비밀번호 안내</h1>")
				.append("<p>아래 비밀번호로 로그인 후 비밀번호 변경해주세요</p>")
				.append("임시 비밀번호: " + map.get("temp_pw")).toString();
		
		try {
			MailUtils sendMail = new MailUtils(mailSender);
			// 메일 제목
			sendMail.setSubject("[PaRaPaRa 비밀번호 찾기]");
			// 메일 내용(html)
			sendMail.setText(mailContent);
			// 메일 보내는 계정
			sendMail.setFrom("happyproject8811@gmail.com", "파라파라");
			// 메일 받는 사람
			sendMail.setTo(map.get("owner_email"));
			
			sendMail.send();
			
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return member_IDao.findOwnerPw(map);
	}

	@Override
	public int adminRegister(AdminDto aDto) {
		logger.info("adminRegister Service : {} ", aDto);
		return member_IDao.adminRegister(aDto);
	}

	@Override
	public int ownerRegister(OwnerDto oDto) { 
		logger.info("ownerRegister Service : {} ", oDto);
		int n = member_IDao.ownerRegister(oDto);
		Map<String, String> map = new HashMap<String, String>();
		map.put("owner_reg", "1");
		map.put("store_code", oDto.getStore_code());
		n += member_IDao.storeOwnerRegi(map);
		return n;
	}

	@Override
	public int adminModify(AdminDto aDto) {
		logger.info("adminModify Service : {} ", aDto);
		return member_IDao.adminModify(aDto);
	}

	@Override
	public int ownerModify(OwnerDto oDto) {
		logger.info("ownerModify Service : {} ", oDto);
		return member_IDao.ownerModify(oDto);
	}

	@Override
	public List<AdminDto> adminList(Map<String, Integer> map) {
		logger.info("adminList Service : {}", map);
		return member_IDao.adminList(map);
	}

	@Override
	public List<AdminDto> adminLocList(Map<String, String> map) {
		logger.info("adminLocList Service : {}", map);
		return member_IDao.adminLocList(map);
	}

	@Override
	public List<OwnerDto> ownerList(Map<String, Integer> map) {
		logger.info("ownerList Service : {}", map);
		return member_IDao.ownerList(map);
	}
	
	@Override
	public int adminListRow() {
		return member_IDao.adminListRow();
	}
	
	@Override
	public int adminLocListRow(String loc_code) {
		return member_IDao.adminLocListRow(loc_code);
	}
	
	@Override
	public int ownerListRow(String loc_code) {
		return member_IDao.ownerListRow(loc_code);
	}

	@Override
	public int adminDelete(String admin_id) {
		logger.info("adminDelete Service : {}", admin_id);
		return member_IDao.adminDelete(admin_id);
	}

	@Override
	public int ownerDelete(Map<String, String> map) {
		// 업주 삭제(계약종료일) 시 필요값 owner_seq, owner_end
		return member_IDao.ownerDelete(map);
		
		// 업주 삭제 후 매장 업주등록코드 업데이트 시 필요값 owner_reg, store_code
		
	}

	@Override
	public List<String> selStoreCodeList() {
		return member_IDao.selStoreCodeList();
	}
	
}
