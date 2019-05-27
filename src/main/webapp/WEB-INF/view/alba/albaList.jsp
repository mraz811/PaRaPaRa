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
	float: right;
	position: relative;
}
.alba_table{
	margin: 10px 0px;
	width: 1010px;
	height: 340px;
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
			<p style="width: 170px; font-size: 30px; background-color: RGB(21,140,186); color:white; font-weight: bold; padding: 0px 10px; text-align: center; ">
				아르바이트
			</p>
		</div>
		<div class="twoDepth">
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" id="timesheet">TimeSheet</a>
  				</li>
  				<li class="nav-item">
    			 <a class="nav-link active" data-toggle="tab" href="#home">아르바이트</a>
  				</li>
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#profile">급여</a>
  				</li>
			</ul>
			
				<script type="text/javascript">
				$(function() {
					$("#timesheet").click(function() {
						location.href="./selTimeSheet.do";
					});
				});
				</script>
				
			<div class="tab-content" align="center">
				<!-- 각자 내용들.. -->
				<form action="" method="post">
				<div class="alba_table">
					<table class="table table-hover">
						<tr class="table-primary">
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
						<c:if test="${empty albaList}">
							<tr><td colspan="9" style="color: red; text-align: center;">등록된 아르바이트가 없습니다.</td></tr>
						</c:if>
						<c:forEach var="alba" items="${albaList}" varStatus="vs">
						<tr>
							<td><input type="radio" name="alba_seq" value="${alba.alba_seq}"></td>
							<td>${alba.alba_name}</td>
							<td>${alba.alba_phone}</td>
							<td>${alba.alba_address}</td>
							<td>${alba.alba_timesal}</td>
							<td>${alba.alba_bank}</td>
							<td>${alba.alba_account}</td>
							<td>${fn:substring(alba.alba_regdate,0,10)}</td>
							<td><input class="btn btn-secondary" type="button" value="수정하기" onclick="modiAlba('${alba.alba_seq}')"></td>
						</tr>
						</c:forEach>
					</table>
				</div>
				<div class="regianddel">
					<input class="btn btn-outline-success" type="button" value="등　록" onclick="toAlbaRegi()">
					<input class="btn btn-outline-warning" type="button" value="삭　제" onclick="delAlba()">
				</div>
				</form>
				
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
	window.open("./albaRegiForm.do", "아르바이트 등록",
			"width=500, height=700, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, left=500, top=150");
// 	location.href="./albaRegiForm.do";	
};

// 알바 수정하기
var modiAlba = function(seq){
// 	alert(seq);
	window.open("./albaModiForm.do?alba_seq="+seq, "아르바이트 정보 수정",
			"width=500, height=700, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, left=500, top=150");
	
}

// 알바 삭제하기
var delAlba = function(){
	var chk = $("input:radio[name=alba_seq]");
	var val = false;
	
// 	alert(chk.length); 5
	var chkVal = null;
	for (var i = 0; i < chk.length; i++) {
		if(chk[i].checked){
			chkVal = chk[i].value;
		}
	}	
// 	alert(chkVal);
	if(chkVal==null){
		swal("삭제 실패", "선택된 아르바이트가 없습니다.");
	}else{
		val = confrmDel(chkVal);
	}
};

function confrmDel(chkVal){
	swal({
		title: "삭제 확인",
		text: "정말 삭제하시겠습니까?",
		type: "warning",
		showCancelButton: true,
		confirmButtonColor: "lightgray",
		confirmButtonText: "취 소",
		cancelButtonText: "확 인",
		closeOnConfirm: false,
		closeOnCancel: false
	},
	function(isConfirm){
		if(isConfirm){ // confirmButtonText
			swal("취소", "아르바이트 정보 삭제가 취소 되었습니다.", "error");
// 			return false;
		} else{ // cancelButtonText
			// 확인 했을 때
			$.ajax({
				type: "POST",
				url: "./delAlba.do",
				data: "alba_seq="+chkVal,
				async : false,
				success: function (data) {
		        	swal({
						title: "삭제 완료", 
						text: "아르바이트 정보 삭제가 완료되었습니다", 
						type: "success"
					},
					function(){ 
						location.reload();
					});
				},
		        error: function (data) {
		        	swal("삭제 에러", "삭제 중 문제가 발생하였습니다.", "error");
		        }
			});
		}
	});
	
}

</script>
</html>