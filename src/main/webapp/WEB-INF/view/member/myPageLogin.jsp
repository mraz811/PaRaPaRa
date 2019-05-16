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
	
	<form action="./toMypage" method="post">
		<input type="hidden" name="admin_id" value="${loginDto.admin_id}">
			<input type="password" id="inputPw" name="admin_pw" placeholder="비밀번호를 입력하세요" required="required" >
		<br>
		<input type="submit" value="확인">
		<input type="button" value="취소">
	</form>

</c:if>
		
<c:if test="${loginDto.auth eq 'U'}">
	
	<form action="./toMypage" method="post">
		<input type="hidden" name="owner_id" value="${loginDto.owner_id}">
			<input type="password" id="inputPw" name="owner_pw" placeholder="비밀번호를 입력하세요" required="required" >
		<br>
		<input type="submit" value="확인">
		<input type="button" value="취소">
	</form>

</c:if>
		


</div>




<%@include file="../footer.jsp" %>


</body>
</html>