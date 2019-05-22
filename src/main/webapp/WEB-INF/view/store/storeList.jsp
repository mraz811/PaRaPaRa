<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/NoticeList.css">
</head>
<script type="text/javascript" src="./js/storeList.js"></script>
<body>
	<div class="container">
<%@include file="../header.jsp" %>
<%-- 	${lists} --%>
	${storeRow}
		<form action="#" id="frm" method="post">
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
			<input type="hidden" id="index" name="index" value="${storeRow.index}">
			<input type="hidden" id="pageNum" name="pageNum" value="${storeRow.pageNum}">
			<input type="hidden" id="listNum" name="listNum" value="${storeRow.listNum}">
			
			<div class="center">
				<ul class="pagination">
					<li><a href="#" onclick="pageFirst(${storeRow.pageList},${storeRow.pageList})">&laquo;</a></li>
					<li><a href="#" onclick="pagePre(${storeRow.pageNum},${storeRow.pageList})">&lsaquo;</a></li>
					<c:forEach var="i" begin="${storeRow.pageNum}" end="${storeRow.count}" step="1">
						<li><a href="#" onclick="pageIndex(${i})">${i}</a></li>
					</c:forEach>
					<li><a href="#" onclick="pageNext(${storeRow.pageNum},${storeRow.total},${storeRow.listNum},${storeRow.pageList})">&rsaquo;</a></li>
					<li><a href="#" onclick="pageLast(${storeRow.pageNum},${storeRow.total},${storeRow.listNum},${storeRow.pageList})">&raquo;</a></li>
				</ul>
			</div>
			<div>
				<input type="button" value="매장등록" onclick="location.href='./regiStoreForm.do'">
			</div>
		</form>

<%@include file="../footer.jsp" %>
</div>
</body>
</html>