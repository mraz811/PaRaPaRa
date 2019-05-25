<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="container">
<%@include file="../header.jsp" %>
	<div class="bodyFrame">
	<div class="bodyfixed">
		<div class="oneDepth">
			<!-- oneDepth에 적힐 내용이 들어감 ex)매장관리 -->
			<p class="text-primary" style="font-size: 40px;">채팅</p>
			
		</div> <!-- div class=oneDepth -->
		<div class="twoDepth">
			<!-- onDepth 안에 있는 twoDepth가 들어감 ex)1depth가 매장관리일 경우 a 태그에 적힐 내용은 일정관리, 재고, 발주 등  -->
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#home">채팅목록</a>
  				</li>
			</ul>
			<div class="tab-content">
	<c:if test="${loginDto.auth eq 'A'}">
		<c:forEach items="${lists}" var="list">	
			<a href="./socketOpen.do?id=${list.owner_id}&auth=${loginDto.auth}&store_code=${list.store_code}">${list.owner_id}</a>
		</c:forEach>
	</c:if>
	<c:if test="${loginDto.auth eq 'U'}">
		<a href="./socketOpen.do?id=${adminDto.admin_id}&auth=${loginDto.auth}">${adminDto.admin_id}</a>
	</c:if>
	</div> <!-- div class=tab-content -->
</div> <!-- div class twoDepth -->
	</div> <!-- div class=bodyfixed -->
	</div> <!-- div class=bodyFrame -->
<%@include file="../footer.jsp" %>
</div>
</body>
</html>