<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아르바이트 전체조회</title>
<style type="text/css">
.center{
	width: 330px;
	position: relative;
}
.regianddel{
	width: 160px;
	position: relative;
}

</style>
</head>
<body>
<%-- ${albaList}<br> --%>
<%-- ${albaRow} --%>

<div id="container">

<%@include file="../header.jsp" %>
	<div class="bodyFrame">
	<div class="bodyfixed">
		<div class="oneDepth">
		
		</div>
		<div class="twoDepth">
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#home">메인테스트</a>
  				</li>
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#profile">두번째탭</a>
  				</li>
			</ul>
			<div class="tab-content">
				<!-- 각자 내용들.. -->
				<table class="table table-hover">
					<tr class="table-secondary">
						<th></th>
						<th>이름</th>
						<th>전화번호</th>
						<th>주소</th>
						<th>시급</th>
						<th>은행명</th>
						<th>계좌번호</th>
						<th>근무시작일</th>
						<th></th>
					</tr>
					<c:forEach var="alba" items="${albaList}" varStatus="vs">
					<tr>
						<td><input type="radio" name="alba_seq" value="${alba_seq}"></td>
						<td>${alba.alba_name}</td>
						<td>${alba.alba_phone}</td>
						<td>${alba.alba_address}</td>
						<td>${alba.alba_timesal}</td>
						<td>${alba.alba_bank}</td>
						<td>${alba.alba_account}</td>
						<td>${fn:substring(alba.alba_regdate,0,10)}</td>
						<td><input class="btn btn-secondary" type="button" value="수정하기" onclick="modAlba('${alba_seq}')"></td>
					</tr>
					</c:forEach>
				</table>
				
				<div class="center">
					<ul class="pagination">
						<li class="page-item">
						 <a class="page-link" href="#" onclick="pageFirst(${albaRow.pageList},${albaRow.pageList})">&laquo;</a>
						</li>
						<li class="page-item">
						 <a class="page-link" href="#" onclick="pagePre(${albaRow.pageNum},${albaRow.pageList})">&lsaquo;</a>
						</li>
		
						<c:forEach var="i" begin="${albaRow.pageNum}" end="${albaRow.count}" step="1">
							<li class="page-item">
							 <a class="page-link" href="#" onclick="pageIndex(${i})">${i}</a>
							</li>
						</c:forEach>
		
						<li class="page-item">
						 <a class="page-link" href="#" onclick="pageNext(${albaRow.pageNum},${albaRow.total},${albaRow.listNum},${albaRow.pageList})">&rsaquo;</a>
						</li>
						<li class="page-item">
						 <a class="page-link" href="#" onclick="pageLast(${albaRow.pageNum},${albaRow.total},${albaRow.listNum},${albaRow.pageList})">&raquo;</a>
						</li>
					</ul>
				</div>
				<div class="regianddel">
					<input class="btn btn-outline-success" type="button" value="등록하기" onclick="toAlbaRegi()">
					<input class="btn btn-outline-warning" type="button" value="삭제하기" onclick="delAlba()">
				</div>
			
			</div> <!-- tab-content -->
			
		</div>
	</div>
	</div>
<%@include file="../footer.jsp" %>

</div>

</body>
<script type="text/javascript">
// 알바 등록 폼으로
var toAlbaRegi = function(){
	location.href="./albaRegiForm.do";	
};

// 알바 삭제하기
var delAlba = function(){
	
	
};

</script>
</html>