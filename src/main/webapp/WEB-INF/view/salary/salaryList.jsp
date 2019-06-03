<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>salaryList</title>
</head>
<body>

<div id="container">
		<%@include file="../header.jsp"%>
		<div class="bodyFrame">
			<div class="bodyfixed">
				<div class="oneDepth">
					<!-- oneDepth에 적힐 내용이 들어감 ex)매장관리 -->
					<p>아르바이트</p>
				</div>
				<!-- div class=oneDepth -->
				<div class="twoDepth">
					<!-- onDepth 안에 있는 twoDepth가 들어감 ex)1depth가 매장관리일 경우 a 태그에 적힐 내용은 일정관리, 재고, 발주 등  -->
					<ul class="nav nav-tabs">
						<li class="nav-item">
		    			 <a class="nav-link" data-toggle="tab" id="timesheet">TimeSheet</a>
		  				</li>
		  				<li class="nav-item">
		    			 <a class="nav-link" data-toggle="tab" id="albaLists">아르바이트</a>
		  				</li>
		  				<li class="nav-item">
		    			 <a class="nav-link active" data-toggle="tab" href="#home">급여</a>
		  				</li>
					</ul>
				<div class="tab-content">

 <script type="text/javascript">
$(function(){
	$("#timesheet").click(function(){
		location.href="./selTimeSheet.do";
	});	
	$("#albaLists").click(function(){
		location.href="./selAlbaList.do";
	});
});

</script> 

<%--  ${albaLists} --%>

<form action="#" method="get">

<!-- <button id="prev">◀</button> -->
<input id="viewMonth" type="month" name="getMonth" value="${month}" onchange="monthGet()"/>
<!-- <button id="next">▶</button> -->

</form>

<table class="table table-hover">
	<tr class="table-primary">
		<th>No.</th><th>이름</th><th>근무 시간 (주간)</th><th>근무 시간(야간,추가)</th>
		<th>시급</th><th>급여</th><th>퇴직금</th><th>은행명</th><th>계좌번호</th>
	</tr>
	
	<c:forEach var="dto" items="${albaLists}" varStatus="vs">
		<tr>
			<td>${vs.count}</td>
			<td>${dto.alba_name}</td>
			<td>${dto.alba_phone}</td>
			<td>${dto.alba_address}</td>
			<td>${dto.alba_timesal}</td>
			<td>${dto.alba_delflag}원</td>
			<td>-</td>
			<td>${dto.alba_bank}</td>
			<td>${dto.alba_account}</td>
		</tr>
	</c:forEach>

</table>

</div><!-- div class=tab-content -->
				</div><!-- div class twoDepth -->
			</div><!-- div class=bodyfixed -->
		</div><!-- div class=bodyFrame -->
		<%@include file="../footer.jsp"%>
	</div>

</body>
<script type="text/javascript">

function monthGet() {
	var getMonth = document.getElementsByName("getMonth")[0].value;
	
// 	alert(getMonth);
	
	var frm = document.forms[0];
	frm.action = "./salary.do?getMonth="+getMonth;
	frm.submit();
}

</script>

</html>





