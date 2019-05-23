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
<div id="container">
<%@include file="../header.jsp" %>

<%-- 업주 리스트: ${ownerlist} --%>
	<div class="bodyFrame">
		<table id="ownerList">
			<tr>
				<th style="width: 150px">사업자번호</th>
				<th>업주명</th>
				<th>전화번호</th>
				<th>이메일</th>
				<th>매장명</th>
				<th>계약시작</th>
				<th>계약종료</th>
				<th></th>
			</tr>	
		</table>
						
		<c:forEach var="ow" items="${ownerlist}" varStatus="vs">

		<form action="#" method="post" >
		<input type="hidden" name="owner_seq" value="${ow.owner_seq}">
		<table>
			<tr>
				<td style="width: 150px">${ow.owner_id}</td>		
				<td>${ow.owner_name}</td>		
				<td>${ow.owner_phone}</td>		
				<td>${ow.owner_email}</td>
				<!-- 매장 코드 매장명으로 변환해주어야 함. 쿼리 추가 필요할수도 -->
				<td>${ow.store_code}</td>
				<td>${fn:substring(ow.owner_start,0,10)}</td>
				<c:if test="${ow.owner_end eq null}">
					<td><input id="owner_end${vs.index}" name="owner_end" type="date"></td>
					<td><input type="button" value="계약종료" onclick="finContract('${vs.index}','${ow.owner_start}')"></td>	
				</c:if>
				<c:if test="${ow.owner_end ne null}">
					<td>${fn:substring(ow.owner_end,0,10)}</td>
					<td></td>	
				</c:if>
			</tr>
		</table>
		</form>


			</c:forEach>
	
	</div>

	<div>
		<input type="button" value="등록" onclick="toOwnerRegi()">
	</div>


		<!-- 페이징 처리 기능은 화면 템플릿 추가 후 추가할 예정 -->
		<input type="hidden" name="index" id="index" value="${ownerRow.index}">
		<input type="hidden" name="pageNum" id="pageNum" value="${ownerRow.pageNum}"> 
		<input type="hidden" name="listNum"	id="listNum" value="${ownerRow.listNum}">

		<div class="center">
			<ul class="pagination">
				<li class="page-item">
				 <a class="page-link" href="#" onclick="pageFirst(${ownerRow.pageList},${ownerRow.pageList})">&laquo;</a>
				</li>
				<li class="page-item">
				 <a class="page-link" href="#" onclick="pagePre(${ownerRow.pageNum},${ownerRow.pageList})">&lsaquo;</a>
				</li>

				<c:forEach var="i" begin="${ownerRow.pageNum}" end="${ownerRow.count}" step="1">
					<li class="page-item">
					 <a class="page-link" href="#" onclick="pageIndex(${i})">${i}</a>
					</li>
				</c:forEach>

				<li class="page-item">
				 <a class="page-link" href="#" onclick="pageNext(${ownerRow.pageNum},${ownerRow.total},${ownerRow.listNum},${ownerRow.pageList})">&rsaquo;</a>
				</li>
				<li class="page-item">
				 <a class="page-link" href="#" onclick="pageLast(${ownerRow.pageNum},${ownerRow.total},${ownerRow.listNum},${ownerRow.pageList})">&raquo;</a>
				</li>
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
var finContract = function(frmNo, start){
	var owner_end = document.getElementById("owner_end"+frmNo).value;
	
// 	alert(frmNo);
// 	alert(owner_end);
	
	if(owner_end==""){
		swal("계약 종료 실패", "계약종료일을 입력하면 종료됩니다.");
// 		alert("빈칸");
	} else if(!compareDate(start, owner_end)){
		swal("계약 종료 실패", "종료일은 계약일이전일 수 없습니다..");
	} else {
		// 계약종료일 등록 완료 시
		var isc = confirm("계약 종료 시 되돌릴 수 없습니다.");
		if(isc){
			var frm = document.forms[frmNo];
			frm.action = "./delOwner.do";
			frm.methd = "post";
			frm.submit();
		}
	}
};

// 날짜 비교 메서드
function compareDate(start, end){
	var startY = start.split("-")[0];
	var startM = start.split("-")[1];
	var startD = start.split("-")[2];
	
	var endY = end.split("-")[0];
	var endM = end.split("-")[1];
	var endD = end.split("-")[2];

	var owner_start = new Date(startY, startM-1, startD);
	var owner_end = new Date(endY, endM-1, endD);
	
	// true
// 	alert(owner_end - owner_start > 0);
	
	return owner_end - owner_start > 0;
}

</script>

</html>