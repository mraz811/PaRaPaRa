<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
</head>
<body>


<div id="container">
<form id="findPwForm" action="./findPw.do" method="post">
		<label><input type="radio" name="auth" value="U" checked="checked"> 업주 </label>		
		<label><input type="radio" name="auth" value="A"> 담당자 </label>	
		<br>
		<input type="text" id="inputId" name="id" placeholder="아이디를 입력하세요" required="required" >
		<span id="idresult"></span><br>
		<input type="text" id="inputEmail" name="email" placeholder="이메일을 입력하세요" required="required" >
		<br>
		<input type="submit" value="임시 비밀번호 받기" >
		<input type="button" value="취소" onclick="javascript:history.back(-1)">
		
	</form>
</div>

<%@include file="../footer.jsp" %>

</body>
</html>