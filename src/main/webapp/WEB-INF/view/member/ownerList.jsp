<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업주 조회 페이지</title>
<style type="text/css">
.owner_table{
	margin: 5px 0px 10px 0px;
	width: 1010px;
	height: 350px;
}
.center{
	width: 330px;
	position: relative;
}
.regianddel{
	width: 80px;
	float: right;
	position: relative;
	margin-right: 16px;
}
</style>
</head>
<script type="text/javascript" src="./js/paging.js"></script>

<body>
<div id="container">
<%@include file="../header.jsp" %>

<%-- 업주 리스트: ${ownerlist} --%>
	<div class="bodyFrame">
	<div class="bodyfixed">
		<div class="oneDepth">
			<p style="width: 170px; font-size: 30px; background-color: RGB(21,140,186); color:white; font-weight: bold; padding: 0px 10px; text-align: center; ">
				회원관리
			</p>
		</div>
		<div class="twoDepth">
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#home">업　주</a>
  				</li>
			</ul>
			
			<div class="tab-content" align="center">
				<div class="owner_table">
				<table id="ownerList" class="table table-hover" style="margin-bottom: 0px;">
					<tr class="table-primary">
						<th width="110px">사업자번호</th>
						<th width="90px">업주명</th>
						<th width="120px">전화번호</th>
						<th width="190px">이메일</th>
						<th width="115px">매장명</th>
						<th width="120px">계약시작</th>
						<th width="165px">계약종료</th>
						<th width="100px"></th>
					</tr>	
				<c:if test="${empty ownerlist}">
					<tr><td colspan="8" style="color: red; text-align: center;" >등록된 업주가 없습니다.</td></tr>
				</c:if>
				</table>
				
				<c:forEach var="ow" items="${ownerlist}" varStatus="vs">
		
				<form action="#" method="post" >
				<input type="hidden" name="owner_seq" value="${ow.owner_seq}">
				<table class="table table-hover" style="margin-bottom: 0px; height: 59px;">
					<tr>
						<td width="110px">${ow.owner_id}</td>		
						<td width="90px">${ow.owner_name}</td>		
						<td width="120px">${ow.owner_phone}</td>		
						<td width="190px">${ow.owner_email}</td>
						<!-- 매장 코드 매장명으로 변환해주어야 함. 쿼리 추가 필요할수도 -->
						<td width="115px">${ow.store_code}</td>
						<td width="120px">${fn:substring(ow.owner_start,0,10)}</td>
						<c:if test="${ow.owner_end eq null}">
							<td width="165px"><input id="owner_end${vs.index}" name="owner_end" type="date"></td>
							<td width="100px"><input class="btn btn-secondary" type="button" value="계약종료" onclick="finContract('${vs.index}','${ow.owner_start}')"></td>	
						</c:if>
						<c:if test="${ow.owner_end ne null}">
							<td width="165px">${fn:substring(ow.owner_end,0,10)}</td>
							<td width="100px"></td>	
						</c:if>
					</tr>
				</table>
				</form>
		
				</c:forEach>
				
				</div><!-- owner_table -->	
					
				<div class="regianddel" align="right">
					<input style="width: 74px;" class="btn btn-outline-success" type="button" value="등　록" onclick="toOwnerRegi()">
				</div>
		
		
				<!-- 페이징 처리 기능은 화면 템플릿 추가 후 추가할 예정 -->
				<input type="hidden" name="index" id="index" value="${ownerRow.index}">
				<input type="hidden" name="pageNum" id="pageNum" value="${ownerRow.pageNum}"> 
				<input type="hidden" name="listNum"	id="listNum" value="${ownerRow.listNum}">
		
				<div class="center" align="center">
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

			</div><!-- tab-content -->

		</div> <!-- twoDepth -->
	</div><!-- bodyfixed -->
	</div><!-- bodyFrame -->
<%@include file="../footer.jsp" %>
</div>


</body>
<script type="text/javascript">

// 업주 등록
var toOwnerRegi = function(){
	window.open("./ownerRegiForm.do","업주 등록 페이지", 
	"width=500, height=700, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, left=500, top=150");
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