<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%@include file="../header.jsp" %>



<div id="container">
	<div id="title">마이페이지 로그인</div>

<c:if test="${loginDto.auth eq 'A'}">
	
	<form action="./toMypage.do" method="post">
		<input type="hidden" name="auth" value="${loginDto.auth}">
		<input type="hidden" name="id" value="${loginDto.admin_id}">
		<span>아이디</span><br> 
		${loginDto.admin_id}<br>
		<br>비밀번호<br>
		<input type="password" id="inputPw" name="pw" placeholder="비밀번호를 입력하세요" required="required" >
		<br>
		<input type="submit" value="확인">
		<input type="button" value="취소" onclick="javascript:history.back(-1)">
	</form>

</c:if>
		
<c:if test="${loginDto.auth eq 'U'}">
	
	<form action="./toMypage.do" method="post">
		<input type="hidden" name="auth" value="${loginDto.auth}">
		<input type="hidden" name="id" value="${loginDto.owner_id}">
		<span>아이디</span><br> 
		${loginDto.owner_id}<br>
		<br>비밀번호<br>
			<input type="password" id="inputPw" name="pw" placeholder="비밀번호를 입력하세요" required="required" >
		<br>
		<input type="submit" value="확인">
		<input type="button" value="취소" onclick="javascript:history.back(-1)">
	</form>

</c:if>
		


</div>




<%@include file="../footer.jsp" %>


</body>
</html>