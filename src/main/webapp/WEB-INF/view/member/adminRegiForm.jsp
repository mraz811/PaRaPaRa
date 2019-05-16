<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>담당자 등록 페이지</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
</head>
<body>

<div id="container">
	
	<!-- 담당자 등록 form -->
	<form id="adRegiForm" action="./adminRegi.do" method="post">
		<div>
			<span>사번</span><br>
			<input type="text" name="admin_id" placeholder="사번(아이디)" required="required" maxlength="8">
			
			<br><span>비밀번호</span><br>	
			<input type="password" name="admin_pw" placeholder="비밀번호" required="required" maxlength="20">
		
			<br><span>비밀번호 확인</span><br>	
			<input type="password" name="pwChk" placeholder="비밀번호 확인" required="required" maxlength="20">
		
			<br><span>담당자명</span><br>	
			<input type="text" name="admin_name" placeholder="이름" required="required" maxlength="20">
			
			<br><span>전화번호</span><br>	
			<input type="text" name="admin_phone" placeholder="연락처" required="required" maxlength="20">
		
			<br><span>이메일</span><br>	
			<input type="text" name="admin_email" placeholder="이메일" required="required" maxlength="80">
			
			<br/><span>지역코드</span><br/>
			<select name="loc_sido" onchange="selSigungu()">
				<option value="SEOUL">서울시</option>
				<option value="INCHEON">인천시</option>
			</select>	
			
			<select name="loc_sigungu">
				<option value="01">강남구</option>
				<option value="02">강동구</option>
				<option value="03">강북구</option>
				<option value="04">강서구</option>
				<option value="05">관악구</option>
				<option value="06">광진구</option>
				<!-- ...옵션 25개... -->
			</select>
		</div>
		<div>	
			<input type="submit" value="등록 완료" onsubmit="return adRegiChk()">
			<input type="button" value="취소" onclick="regiCancel()">
		</div>
	</form>


</div>


</body>
<script type="text/javascript">

$(function(){
	// 회원등록 폼 작성시 유효값 확인 필요
	
});

// select 박스로 시/도 선택시 시/군/구 바꾸어주어야 함.
var selSigungu = function(){
	
}

// 등록 취소 버튼클릭 시 실행할 함수
var regiCancel = function(){
	
	
}

// 담당자 회원 등록 버튼 클릭 시 실행할 함수
var adRegiChk = function(){
	
}

</script>
</html>