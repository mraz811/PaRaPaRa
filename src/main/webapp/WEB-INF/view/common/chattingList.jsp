<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${loginDto.auth eq 'A'}">
		<c:forEach items="${lists}" var="list">	
			<a href="./socketOpen.do?id=${list.owner_id}&auth=${loginDto.auth}&store_code=${list.store_code}">${list.owner_id}</a>
		</c:forEach>
	</c:if>
	<c:if test="${loginDto.auth eq 'U'}">
		<a href="./socketOpen.do?id=${adminDto.admin_id}&auth=${loginDto.auth}">${adminDto.admin_id}</a>
	</c:if>
	
</body>
</html>