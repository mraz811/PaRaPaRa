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
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>업주 발주 신청</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
</head>
<body>
	${stockLists}
	
	<div id="stockList">
		<input type='hidden' id='store_code' value='${stockList[0].store_code}'>
		<table>
			<thead>
				<tr>
					<th>재고번호</th><th>재고명</th><th>현재상태</th><th>가격</th><th>추가버튼</th>
				</tr>
			</thead>
			<tbody>		
				<c:choose>
					<c:when test="${empty stockLists}">
						<tr>
							<td id="noList" colspan="4">-- 작성된 글이 없습니다 --</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="dto" items="${stockLists}">
							<tr>
								<%-- <td>${dto.pao_seq}</td>
								<td>${dto.store_name}</td>
								<td>${dto.ps_name}</td>
								<td>${dto.pao_date}</td> --%>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</body>
</html>