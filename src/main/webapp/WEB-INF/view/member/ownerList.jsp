<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업주 조회 페이지</title>
</head>
<script type="text/javascript" src="./js/paging.js"></script>

<body>
<%@include file="../header.jsp" %>

업주 리스트: ${ownerlist}
<div id="container">
	<div>
		<table id="ownerList">
			<tr>
				<th>사업자번호</th>
				<th>업주명</th>
				<th>전화번호</th>
				<th>이메일</th>
				<th>매장명</th>
				<th>계약시작</th>
				<th>계약종료</th>
				<th></th>
			</tr>	
	
			<c:forEach var="ow" items="${ownerlist}" varStatus="vs">
			<tr>
				<td>${ow.owner_id}</td>		
				<td>${ow.owner_name}</td>		
				<td>${ow.owner_phone}</td>		
				<td>${ow.owner_email}</td>
				<!-- 매장 코드 매장명으로 변환해주어야 함. 쿼리 추가 필요할수도 -->
				<td>${ow.store_code}</td>
				<td>${fn:substring(ow.owner_start,0,10)}</td>
				<c:if test="${ow.owner_end eq null}">
					<td> - </td>
					<td><input type="button" value="계약종료" onclick="finContract()"></td>	
				</c:if>
				<c:if test="${ow.owner_end ne null}">
					<td>${fn:substring(ow.owner_end,0,10)}</td>
					<td></td>	
				</c:if>
			</tr>
			</c:forEach>
	
		</table>
	</div>

	<div>
		<input type="button" value="등록" onclick="toOwnerRegi()">
	</div>


<!-- 페이징 처리 기능은 화면 템플릿 추가 후 추가할 예정 -->
	<input type="hidden" name="index" id="index" value="${row.index}">
	<input type="hidden" name="pageNum" id="pageNum" value="${row.pageNum}">
	<input type="hidden" name="listNum" id="listNum" value="${row.listNum}">
	
	<div class="center">
		<ul class="pagination">
			<li><a href="#" onclick="pageFirst(${row.pageList},${row.pageList})">&laquo;</a></li>
			<li><a href="#" onclick="pagePre(${row.pageNum},${row.pageList})">&lsaquo;</a></li>
				
			<c:forEach var="i" begin="${row.pageNum}" end="${row.count}" step="1" >
				<li><a href="#" onclick="pageIndex(${i})">${i}</a></li>
			</c:forEach>

			<li><a href="#" onclick="pageNext(${row.pageNum},${row.total},${row.listNum},${row.pageList})">&rsaquo;</a></li>
			<li><a href="#" onclick="pageLast(${row.pageNum},${row.total},${row.listNum},${row.pageList})">&raquo;</a></li>
		</ul>
	</div>
	
</div>

<%@include file="../footer.jsp" %>

</body>
<script type="text/javascript">

// 업주 등록
var toOwnerRegi = function(){
	location.href="./ownerRegiForm.do";
};

// 업주 계약 종료 시 
var finContract = function(){
	
};

</script>

</html>