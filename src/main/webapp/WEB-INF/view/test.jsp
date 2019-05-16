<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>헤더 내용 테스트</title>
</head>
<body>

헤더에 들어갈 부분 테스트 중입니다.
 
로그인 세션: ${loginDto}

<c:if test="${loginDto.auth eq 'A'}">
<input type="button" value="마이페이지(담)" onclick="chkMyPage()" >
<input type="button" value="로그아웃" onclick="logout('${loginDto.auth}')">
</c:if>

<c:if test="${loginDto.auth eq 'U'}">
<input type="button" value="마이페이지(업)" onclick="chkMyPage()" >
<input type="button" value="로그아웃" onclick="logout('${loginDto.auth}')">

</c:if>





</body>
<script type="text/javascript">

function logout(auth){
	// 권한에 따른 로그아웃 실행
	location.href="./logout.do?auth="+auth;
}

var chkMyPage = function(){
	// MyPage 가기 위한 비밀번호 입력 페이지로
	location.href="./pwCheckForm.do";
}

</script>
</html>