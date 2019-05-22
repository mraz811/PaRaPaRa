<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>담당자 전체조회</title>
</head>
<script type="text/javascript" src="./js/paging.js"></script>
<body>
<div id="container">
	<%@include file="../header.jsp"%>
<%-- 	<br> 페이징dto : ${row} --%>
<%-- 	<br> 담당자 리스트 : ${adminList} --%>
<%-- 	<br> loc입력시 리스트 :${adminLocList} --%>
<%-- 	<br> 퇴사자 : ${delAdminList} --%>

	<div class="bodyFrame">
	<div class="bodyfixed">
		<div class="oneDepth">
			
		</div>
		
		<div class="twoDepth">
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#home">담당자</a>
  				</li>
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#profile">Profile</a>
  				</li>
			</ul>
		
	
		<!-- 담당자 전체 조회 -->
		<c:if test="${adminList ne null}">
			<form action="./delAdmin.do" method="post" onsubmit="return delAdmin()">
				<div class="table-size">
					<table class="table table-hover" id="adminList">
						<tr class="table-secondary">
							<th></th>
							<th>사번</th>
							<th>담당자명</th>
							<th>전화번호</th>
							<th>이메일</th>
							<th>지역명</th>
						</tr>

						<c:forEach var="ad" items="${adminList}" varStatus="vs">
							<tr>
								<td><input type="radio" name="admin_id"	value="${ad.admin_id}"></td>
								<td>${ad.admin_id}</td>
								<td>${ad.admin_name}</td>
								<td>${ad.admin_phone}</td>
								<td>${ad.admin_email}</td>
								<td>${ad.loc_code}</td>
							</tr>
						</c:forEach>

					</table>
				</div>
				<!-- 등록/삭제 버튼 -->
				<div>
					<input type="button" value="등록" onclick="toAdminRegi()"> 
					<input type="submit" value="삭제">
				</div>
			</form>
		</c:if>


		<!-- 담당자 지역별 조회 -->
		<c:if test="${adminLocList ne null}">
			<form action="./delAdmin.do" method="post" onsubmit="return delAdmin()">
				<div>
					<table>
						<tr>
							<th></th>
							<th>사번</th>
							<th>담당자명</th>
							<th>전화번호</th>
							<th>이메일</th>
							<th>지역명</th>
						</tr>

						<c:forEach var="ad" items="${adminLocList}" varStatus="vs">
							<tr>
								<td><input type="radio" name="admin_id" value="${ad.admin_id}"></td>
								<td>${ad.admin_id}</td>
								<td>${ad.admin_name}</td>
								<td>${ad.admin_phone}</td>
								<td>${ad.admin_email}</td>
								<td>${ad.loc_code}</td>
							</tr>
						</c:forEach>

					</table>
				</div>
				<!-- 등록/삭제 버튼 -->
				<div>
					<input type="button" value="등록" onclick="toAdminRegi()"> 
					<input type="submit" value="삭제">
				</div>
			</form>
		</c:if>


		<!-- 퇴사한 담당자 조회 -->
		<c:if test="${delAdminList ne null}">
			<div>
				<table>
					<tr>
						<th>사번</th>
						<th>담당자명</th>
						<th>전화번호</th>
						<th>이메일</th>
						<th>지역명</th>
					</tr>

					<c:forEach var="ad" items="${delAdminList}" varStatus="vs">
						<tr>
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


		${adminRow.index} ${adminRow.pageNum} ${adminRow.listNum} ${adminRow.count}

		<!-- 페이징 처리 기능은 화면 템플릿 추가 후 추가할 예정 -->
		<input type="hidden" name="index" id="index" value="${adminRow.index}">
		<input type="hidden" name="pageNum" id="pageNum" value="${adminRow.pageNum}"> 
		<input type="hidden" name="listNum"	id="listNum" value="${adminRow.listNum}">

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
	
	</div> <!-- twoDepth -->

	</div>	<!-- bodyfixed -->

	</div>
	<%@include file="../footer.jsp"%>
</div>
</body>
<script type="text/javascript">

var toAdminRegi = function(){
	location.href="./adminRegiForm.do";
};


var delAdmin = function(){
	var chk = $("input:radio[name=admin_id]");
	var val = false;	
	
// 	alert(chk.length);
	var chkVal=null;
	for (var i = 0; i < chk.length; i++) {
		if(chk[i].checked){
// 			alert(chk[i].value);
			chkVal = chk[i].value;
		}
	}
// 	alert("for문 밖에서 확인한 값 : "+chkVal);
	if(chkVal==null){
		swal("삭제 실패","선택된 담당자가 없습니다.");
	}else {
		return confirm("정말로 삭제하시겠습니까?");
// 		val  = con();
	}
// 	alert(val);
return val;
};


// function con(){
// 	swal({
//         title: "Are you sure?",
//         text: "삭제 후 되돌릴 수 없습니다",
//         type: "warning",
//         showCancelButton: true,
// //	        confirmButtonColor: "#DD6B55",
// //	        confirmButtonText: "Yes, delete it!",
// //	        cancelButtonText: "No, cancel plx!",
//         closeOnConfirm: true,
//         closeOnCancel: true 
//     },
//     function(isConfirm) {
//         if (isConfirm) {
//           return true;
//         } else {
// 	       return false;
//         }
//     });
// };




</script>
</html>