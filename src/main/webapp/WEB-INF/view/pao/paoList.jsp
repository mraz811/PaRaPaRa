<%@page import="java.util.List"%>
<%@page import="com.happy.para.dto.PaoDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업주 발주 리스트</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
</head>
<body>
	<div id="selectDate">
		날짜 선택 <input type="date" id="startDate"> ~ <input type="date" id="endDate"> <input type="button" value="검색">
	</div>
	${paoLists}
	<div id="paoList">
		<table>
			<tr>
				<th>발주번호</th><th>매장명</th><th>발주상태</th><th>날짜</th>
			</tr>
			
			<c:forEach var="dto" items="${paoLists}">
					<tr>
						<td>${dto.pao_seq}</td>
						<td>${dto.store_code}</td>
						<td>${dto.ps_code}</td>
						<td>${dto.pao_date}</td>
					</tr>
			</c:forEach>
			
		</table>
	</div>
	
	<div>
	
	</div>
</body>
</html>