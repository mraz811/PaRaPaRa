<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>담당자 전체조회</title>
</head>
<script type="text/javascript" src="js/paging.js"></script>
<body>
<%@include file="../header.jsp" %>
<br>
페이징dto : ${row}<br>
담당자 리스트 : ${adminList}<br>
loc입력시 리스트 :${adminLocList}<br>
퇴사자 : ${delAdminList}

<div id="container">
	<!-- 담당자 전체 조회 -->
	<c:if test="${adminList ne null}">
		<div>
			<table id="adminList">
				<tr>
					<th><input type="checkbox" id="chkAll"></th>
					<th>사번</th>
					<th>담당자명</th>
					<th>전화번호</th>
					<th>이메일</th>
					<th>지역명</th>
				</tr>	
		
				<c:forEach var="ad" items="${adminList}" varStatus="vs">
				<tr>
					<td><input type="checkbox"></td>
					<td>${ad.admin_id}</td>		
					<td>${ad.admin_name}</td>		
					<td>${ad.admin_phone}</td>		
					<td>${ad.admin_email}</td>
					<td>${ad.loc_code}</td>		
				</tr>
				</c:forEach>
		
			</table>
		</div>
	</c:if>	
	<!-- 담당자 지역별 조회 -->
	<c:if test="${adminLocList ne null}">
		<div>
			<table>
				<tr>
					<th><input type="checkbox" id="chkAll"></th>
					<th>사번</th>
					<th>담당자명</th>
					<th>전화번호</th>
					<th>이메일</th>
					<th>지역명</th>
				</tr>
				
				<c:forEach var="ad" items="${adminLocList}" varStatus="vs">
				<tr>
					<td><input type="checkbox"></td>
					<td>${ad.admin_id}</td>		
					<td>${ad.admin_name}</td>		
					<td>${ad.admin_phone}</td>		
					<td>${ad.admin_email}</td>
					<td>${ad.loc_code}</td>		
				</tr>
				</c:forEach>
				
			</table>
		</div>
	</c:if>
	
	<!-- 퇴사한 담당자 조회 -->
	<c:if test="${delAdminList ne null}">
		<div>
			<table>
				<tr>
					<th><input type="checkbox" id="chkAll"></th>
					<th>사번</th>
					<th>담당자명</th>
					<th>전화번호</th>
					<th>이메일</th>
					<th>지역명</th>
				</tr>
				
				<c:forEach var="ad" items="${delAdminList}" varStatus="vs">
				<tr>
					<td><input type="checkbox"></td>
					<td>${ad.admin_id}</td>		
					<td>${ad.admin_name}</td>		
					<td>${ad.admin_phone}</td>		
					<td>${ad.admin_email}</td>
					<td>${ad.loc_code}</td>		
				</tr>
				</c:forEach>
				
			</table>
		</div>
	</c:if>
	
${row.index}
${row.pageNum}
${row.listNum}
${row.count}

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
</html>