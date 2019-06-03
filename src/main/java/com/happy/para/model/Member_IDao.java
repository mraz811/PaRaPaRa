package com.happy.para.model;

import java.util.List;
import java.util.Map;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.OwnerDto;

public interface Member_IDao {

	// 담당자(관리자) 로그인
	public AdminDto adminLogin(AdminDto aDto);
	
	// 업주 로그인
	public OwnerDto ownerLogin(OwnerDto oDto);
	
	// 담당자 비밀번호 찾기: 아이디, 이메일을 입력받아 존재 시, 임시 비밀번호를 생성해서 이메일로 보냄
	public int findAdminPw(Map<String, String> map);
	
	// 업주 비밀번호 찾기: 아이디, 이메일을 입력받아 존재 시, 임시 비밀번호를 생성해서 이메일로 보냄
	public int findOwnerPw(Map<String, String> map);
	
	// 담당자 회원 등록
	public int adminRegister(AdminDto aDto);
	
	// 업주 회원 등록
	public int ownerRegister(OwnerDto oDto);
	
	// 담당자 정보 수정: 새비밀번호, 이름, 전화번호, 이메일 수정 가능 (구분:admin_id)
	public int adminModify(AdminDto aDto);
	
	// 업주 정보 수정:  새비밀번호, 이름, 전화번호, 이메일 수정 가능 (구분:owner_seq)
	public int ownerModify(OwnerDto oDto);
	
	// 담당자 전체 조회 - 페이징
	public List<AdminDto> adminList(Map<String, String> map);
	
	// 담당자 지역별 조회 - 페이징
	public List<AdminDto> adminLocList(Map<String, String> map);
	
	// 업주 전체 조회 - 페이징
	public List<OwnerDto> ownerList(Map<String, String> map);
	
	// 페이징을 위한 전체 담당자 수 조회
	public int adminListRow(String admin_delflag);

	// 페이징을 위한 지역별 담당자 수 조회
	public int adminLocListRow(String loc_sido);
	
	// 페이징을 위한 전체 업주 조회
	public int ownerListRow(String loc_code);
	
	// 담당자 삭제 (진짜 삭제 아닌 DELFLAG Y로 업데이트)
	public int adminDelete(String admin_id);
	
	// 업주 삭제 (진짜 삭제 아닌, 계약 종료일 업데이트)
	public int ownerDelete(Map<String, String> map);
	
	// 업주 등록 화면에서 매장코드 옵션 주기 위한 쿼리
	public List<String> selStoreCodeList();
	
	// 업주 등록 시 매장 업주등록여부 업데이트 (owner_reg, store_code) 필요
	public int storeOwnerRegi(Map<String, String> map);
	
	// 담당자 등록 시 사번 중복 검사
	public int adminIdDupleChk(String admin_id);
	
	// 업주 등록시 사업자 번호 중복 검사
	public int ownerIdDupleChk(String owner_id);
	
	// 비밀번호 찾기 시 아이디, 이메일 정보 유효한지 ajax
	public int findPwChkAdmin(Map<String, String> map);
	
	// 비밀번호 찾기 시 아이디, 이메일 정보 유효한지 ajax
	public int findPwChkOwner(Map<String, String> map);
	
	// 담당하는 업주 전체 조회
	public List<OwnerDto> ownerListAll(String loc_code);
}
