<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/NoticeList.css">
</head>
<body>
<%@include file="../header.jsp" %>
	${lists}
	${row}
	<div class="container">
		<table class="table">
			<thead>
				<tr>
					<th>매장코드</th>
					<th>매장명</th>
					<th>주소</th>
					<th>전화번호</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${fn:length(lists) eq 0}">
						<tr>
							<th colspan="4">등록된 매장이 없습니다.</th>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${lists}" var="storeDto">
							<tr>
								<td>${storeDto.store_code}</td>
								<td><a href="./selStoreDetail.do?store_code=${storeDto.store_code}">${storeDto.store_name}</a></td>
								<td>${storeDto.store_address}</td>
								<td>${storeDto.store_phone}</td>
							</tr>
						</c:forEach>	
					</c:otherwise>
				</c:choose>
			
			</tbody>
		</table>
		<input type="hidden" id="index" name="index" value="${row.index}">
		<input type="hidden" id="pageNum" name="pageNum" value="${row.pageNum}">
		<input type="hidden" id="listNum" name="listNum" value="${row.listNum}">
		
		<div class="center">
			<ul class="pagination">
				<li><a href="#" onclick="pageFirst(${row.pageList},${row.pageList})">&laquo;</a></li>
				<li><a href="#" onclick="pagePre(${row.pageNum},${row.pageList})">&lsaquo;</a></li>
				<c:forEach var="i" begin="${row.pageNum}" end="${row.count}" step="1">
					<li><a href="#" onclick="pageIndex(${i})">${i}</a></li>
				</c:forEach>
				<li><a href="#" onclick="pageNext(${row.pageNum},${row.total},${row.listNum},${row.pageList})">&rsaquo;</a></li>
				<li><a href="#" onclick="pageLast(${row.pageNum},${row.total},${row.listNum},${row.pageList})">&raquo;</a></li>
			</ul>
		</div>
		<div>
			<input type="button" value="매장등록" onclick="location.href='./regiStoreForm.do'">
		</div>
	</div>

<%@include file="../footer.jsp" %>
</body>
</html>