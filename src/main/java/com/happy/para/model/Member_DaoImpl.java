package com.happy.para.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.happy.para.dto.AdminDto;
import com.happy.para.dto.OwnerDto;

@Repository
public class Member_DaoImpl implements Member_IDao {
	//***************** 조민지 - 회원(업주,담당자)관리를 위한 Dao ***************// 
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	private final String MNS = "para.member.";
	
	@Autowired
	private PasswordEncoder pwEncoder;
	
	@Override
	public AdminDto adminLogin(AdminDto aDto) {
		// 입력받은 담당자 사번을 이용해서 DB에 저장된 암호화 된 비밀번호를 가져온다.
		String securePw = sqlSession.selectOne(MNS+"selAdminPw", Integer.toString(aDto.getAdmin_id()));

		// 입력받은 비밀번호 값과 DB에 저장된 비밀번호 값이 같은지 확인 (일치:true, 불일치:false)
		if(pwEncoder.matches(aDto.getAdmin_pw(), securePw)) {
			// 같은 값이라면 입력받은 비밀번호를 저장된 비밀번호로 변경해 준다. 
			// (다시 pwEncoder를 사용하는 경우 입력값이 같더라도 변환된 비밀번호가 다를 수 있기 때문)
			aDto.setAdmin_pw(securePw);
			System.out.println("담당자 로그인 시도: 비밀번호 일치");
			return sqlSession.selectOne(MNS+"adminLogin", aDto);
		}
		// 로그인 실패 시 
		return null;
	}

	@Override
	public OwnerDto ownerLogin(OwnerDto oDto) {
		// 입력받은 업주의 사업자 번호를 이용하여 DB에 저장된 암호화 된 비밀번호를 가져온다.
		String securePw = sqlSession.selectOne(MNS+"selOwnerPw", oDto.getOwner_id());

		if(pwEncoder.matches(oDto.getOwner_pw(), securePw)) {
			oDto.setOwner_pw(securePw);
			System.out.println("업주 로그인 시도: 비밀번호 일치");
			return sqlSession.selectOne(MNS+"ownerLogin", oDto);
		}
		return null;
	}

	@Override
	public int findAdminPw(Map<String, String> map) {
		return sqlSession.selectOne(MNS+"findAdminPw", map);
	}

	@Override
	public int findOwnerPw(Map<String, String> map) {
		return sqlSession.selectOne(MNS+"findOwnerPw", map);
	}

	@Override
	public int adminRegister(AdminDto aDto) {
		// 담당자 등록시 입력받은 비밀번호 암호화 처리 후 저장
		String encodePw = pwEncoder.encode(aDto.getAdmin_pw());
		aDto.setAdmin_pw(encodePw);
		
		return sqlSession.insert(MNS+"adminRegister", aDto);
	}

	@Override
	public int ownerRegister(OwnerDto oDto) {
		// 업주 등록시 입력받은 비밀번호 암호화 처리 후 저장
		String encodePw = pwEncoder.encode(oDto.getOwner_pw());
		oDto.setOwner_pw(encodePw);
		
		return sqlSession.insert(MNS+"ownerRegister", oDto);
	}

	@Override
	public int adminModify(AdminDto aDto) {
		return sqlSession.update(MNS+"adminModify",	aDto);
	}

	@Override
	public int ownerModify(OwnerDto oDto) {
		return sqlSession.update(MNS+"ownerModify", oDto);
	}

	@Override
	public List<AdminDto> adminList(Map<String, Integer> map) {
		return sqlSession.selectList(MNS+"adminList", map);
	}

	@Override
	public List<AdminDto> adminLocList(Map<String, String> map) {
		return sqlSession.selectList(MNS+"adminLocList", map);
		
	}

	@Override
	public List<OwnerDto> ownerList(Map<String, Integer> map) {
		return sqlSession.selectList(MNS+"ownerList", map);
	}

	@Override
	public int adminListRow() {
		return sqlSession.selectOne(MNS+"adminListRow");
	}

	@Override
	public int adminLocListRow(String loc_code) {
		return sqlSession.selectOne(MNS+"adminLocListRow", loc_code);
	}

	@Override
	public int ownerListRow(String loc_code) {
		return sqlSession.selectOne(MNS+"ownerListRow", loc_code);
	}

	@Override
	public int adminDelete(String admin_id) {
		return sqlSession.update(MNS+"adminDelete", admin_id);
	}

	@Override
	public int ownerDelete(Map<String, String> map) {
		return sqlSession.update(MNS+"ownerDelete", map);
	}

	@Override
	public List<String> selStoreCodeList() {
		return sqlSession.selectList(MNS+"selStoreCodeList");
	}

	@Override
	public int storeOwnerRegi(Map<String, String> map) {
		return sqlSession.update(MNS+"storeOwnerRegi", map);
	}
	
}
