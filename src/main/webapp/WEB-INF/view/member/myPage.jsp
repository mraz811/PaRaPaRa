<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이 페이지</title>
</head>
<body>
<%@include file="../header.jsp" %>
${loginDto}

	<c:if test="${loginDto.auth eq 'A'}">
		담당자 정보 가져올 곳

	</c:if>
	
	
	<c:if test="${loginDto.auth eq 'U'}">
		업주 정보 가져올 곳
		
	</c:if>




<%@include file="../footer.jsp" %>
</body>
</html>