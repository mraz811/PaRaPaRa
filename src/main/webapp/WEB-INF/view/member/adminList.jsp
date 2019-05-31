<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>담당자 전체조회</title>
<style type="text/css">
.admin_table{
	margin: 5px 0px 10px 0px;
	width: 1018px;
	height: 280px;
}
.center{
	width: 330px;
	position: relative;
}

.regianddel{
	position : absolute;
	margin-bottom: 16px;
	bottom: 5px;
	width: 160px;
	right: 10px;
/* 	float: right; */
/* 	position: relative; */
}
</style>
</head>
<body>
<div id="container">
	<%@include file="../header.jsp"%>
	<script type="text/javascript" src="./js/member/paging.js"></script>
	<script type="text/javascript" src="./js/member/admin.js"></script>
<%-- 	<br> 페이징dto : ${row} --%>
<%-- 	<br> 담당자 리스트 : ${adminList} --%>
<%-- 	<br> loc입력시 리스트 :${adminLocList} --%>
<%-- 	<br> 퇴사자 : ${delAdminList} --%>

	<div class="bodyFrame">
	<div class="bodyfixed">
		<div class="oneDepth">
			<p>
				회원관리
			</p>
		</div>
		
		<div class="twoDepth">
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link active" data-toggle="tab" id="selAdminList">담당자</a>
  				</li>
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" id="leftOnes">퇴사자</a>
  				</li>
			</ul>
				<script type="text/javascript">
				$(function(){
					$("#leftOnes").click(function(){
						location.href="./selAdminList.do?delflag=Y";
					});
					
					$("#selAdminList").click(function(){
						location.href="./selAdminList.do";
					});
					
				});
				</script>
			<div align="right">
				<select class="form-control" onchange="selectToAdList(this.value)" style="width: 150px; margin: 5px 1px 0px;" >
					<option>지역선택</option>
			<c:if test="${adminList ne null || adminLocList ne null}">	
					<option value="ALL">전 체</option>
					<option value="SEOUL">서울시</option>
					<option value="INCHEON">인천시</option>
			</c:if>
				</select>
			</div>
			<div class="tab-content" align="center">
			<!-- 담당자 전체 조회 -->
			<c:if test="${adminList ne null}">
				<form action="" method="post">
					<div class="admin_table">
						<table class="table table-hover" id="adminList">
							<tr class="table-primary">
								<th width="80px;"></th>
								<th width="120px;">사번</th>
								<th width="130px;">담당자명</th>
								<th width="150px;">전화번호</th>
								<th width="300px;">이메일</th>
								<th width="130px;">지역명</th>
								<th width="120px;">지역코드</th>
							</tr>
	
							<c:forEach var="ad" items="${adminList}" varStatus="vs">
								<tr>
									<td align="center"><input type="radio" name="admin_id"	value="${ad.admin_id}"></td>
									<td>${ad.admin_id}</td>
									<td>${ad.admin_name}</td>
									<td>${ad.admin_phone}</td>
									<td>${ad.admin_email}</td>
									<td>${ad.loc_name}</td>
									<td>${ad.loc_code}</td>
								</tr>
							</c:forEach>
	
						</table>
					</div>
					<!-- 등록/삭제 버튼 -->
					<div class="regianddel">
						<input class="btn btn-outline-success" type="button" value="등　록" onclick="toAdminRegi()"> 
						<input class="btn btn-outline-warning" type="button" value="삭　제" onclick="delAdmin()">
					</div>
				</form>
			</c:if>
	
	
			<!-- 담당자 지역별 조회 -->
			<c:if test="${adminLocList ne null}">
				<form action="" method="post">
					<div class="admin_table">
						<table class="table table-hover">
							<tr class="table-primary">
								<th width="80px;"></th>
								<th width="120px;">사번</th>
								<th width="130px;">담당자명</th>
								<th width="150px;">전화번호</th>
								<th width="300px;">이메일</th>
								<th width="130px;">지역명</th>
								<th width="120px">지역코드</th>
							</tr>
	
							<c:forEach var="ad" items="${adminLocList}" varStatus="vs">
								<tr>
									<td align="center"><input type="radio" name="admin_id" value="${ad.admin_id}"></td>
									<td>${ad.admin_id}</td>
									<td>${ad.admin_name}</td>
									<td>${ad.admin_phone}</td>
									<td>${ad.admin_email}</td>
									<td>${ad.loc_name}</td>
									<td>${ad.loc_code}</td>
								</tr>
							</c:forEach>
	
						</table>
					</div>
					<!-- 등록/삭제 버튼 -->
					<div class="regianddel">
						<input class="btn btn-outline-success" type="button" value="등　록" onclick="toAdminRegi()"> 
						<input class="btn btn-outline-warning" type="button" value="삭　제" onclick="delAdmin()">
					</div>
				</form>
			</c:if>
	
	
			<!-- 퇴사한 담당자 조회 -->
			<c:if test="${delAdminList ne null}">
			<script type="text/javascript">
				$("#leftOnes").attr("class", "nav-link active");
				$("#selAdminList").attr("class", "nav-link");
			</script>
				<div class="admin_table">
					<table class="table table-hover">
						<tr class="table-primary">
							<th width="80px;"></th>
							<th width="120px;">사번</th>
							<th width="130px;">담당자명</th>
							<th width="150px;">전화번호</th>
							<th width="300px;">이메일</th>
							<th width="130px;">지역명</th>
							<th width="120px">지역코드</th>
						</tr>
	
						<c:forEach var="ad" items="${delAdminList}" varStatus="vs">
							<tr>
								<td></td>
								<td>${ad.admin_id}</td>
								<td>${ad.admin_name}</td>
								<td>${ad.admin_phone}</td>
								<td>${ad.admin_email}</td>
								<td>${ad.loc_name}</td>
								<td>${ad.loc_code}</td>
							</tr>
						</c:forEach>
	
					</table>
				</div>
			</c:if>
	
	
	<%-- 		${adminRow.index} ${adminRow.pageNum} ${adminRow.listNum} ${adminRow.count} --%>
	
		
			<!-- 페이징 처리 -->
			<form action="#" id="pagingForm" method="post">
				<input type="hidden" name="index" id="index" value="${adminRow.index}">
				<input type="hidden" name="pageNum" id="pageNum" value="${adminRow.pageNum}"> 
				<input type="hidden" name="listNum"	id="listNum" value="${adminRow.listNum}">
				<input type="hidden" id="chkCons" name="chkCons" value="${chkCons}">
				<div class="center">
					<ul class="pagination">
						<li class="page-item">
						 <a class="page-link" href="#" onclick="pageFirst(${adminRow.pageList},${adminRow.pageList})">&laquo;</a>
						</li>
						<li class="page-item">
						 <a class="page-link" href="#" onclick="pagePre(${adminRow.pageNum},${adminRow.pageList})">&lsaquo;</a>
						</li>
		
						<c:forEach var="i" begin="${adminRow.pageNum}" end="${adminRow.count}" step="1">
							<li class="page-item">
							 <a class="page-link" href="#" onclick="pageIndex(${i})">${i}</a>
							</li>
						</c:forEach>
		
						<li class="page-item">
						 <a class="page-link" href="#" onclick="pageNext(${adminRow.pageNum},${adminRow.total},${adminRow.listNum},${adminRow.pageList})">&rsaquo;</a>
						</li>
						<li class="page-item">
						 <a class="page-link" href="#" onclick="pageLast(${adminRow.pageNum},${adminRow.total},${adminRow.listNum},${adminRow.pageList})">&raquo;</a>
						</li>
					</ul>
				</div>
			</form>
				
			</div><!-- tap content -->
		
		
		</div> <!-- twoDepth -->

	</div>	<!-- bodyfixed -->
	</div>
	<%@include file="../footer.jsp"%>
</div>
</body>

<script type="text/javascript">
// 담당자 등록 폼으로
var toAdminRegi = function(){
	window.open("./adminRegiForm.do","담당자 등록", 
			"width=500, height=700, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, left=500, top=150");
};


// selectbox 선택시
function selectToAdList(val){
// 	alert(val);
	if(val=="ALL"){
		location.href="./selAdminList.do";
	} else {
		location.href="./selAdminList.do?loc_sido="+val;
	}
}

// 담당자 삭제시
var delAdmin = function(){
	var chk = $("input:radio[name=admin_id]");
	var val = false;	
	
// 	alert(chk.length);
	var chkVal=null;
	for (var i = 0; i < chk.length; i++) {
		if(chk[i].checked){
			chkVal = chk[i].value;
		}
	}
// 	alert(chkVal);
	if(chkVal==null){
		swal("삭제 실패","선택된 담당자가 없습니다.");
	}else {
		val = confirmDel(chkVal);
	}
};

function confirmDel(chkVal){
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
		if(isConfirm){
			swal("취소", "담당자 삭제가 취소 되었습니다.");
		} else{
			$.ajax({
				type: "POST",
				url: "./delAdmin.do",
				data: "admin_id="+chkVal,
				async: false,
				success: function(data){
					swal({
						title: "삭제 완료", 
						text: "담당자 정보 삭제가 완료되었습니다", 
						type: "success"
					},
					function(){ 
						location.reload();
					});
				},
				error: function(data){
					swal("삭제 에러", "삭제 중 문제가 발생하였습니다.", "error");
				}
			});
		}
	});
}

</script>
</html>