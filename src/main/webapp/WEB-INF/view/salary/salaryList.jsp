<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>급여</title>
<style type="text/css">

#tableHeader{
	margin-top: 5px;
	margin-bottom: 0px;
}

#salAll{
	position: relative;
}

#menu{
	position: relative;
	width: 1020px;
	text-align: center;
}

th, tr, td{
	margin: 0px;
	padding: 0px;
 	width:100px;
}

#viewMonth{
	border: none;
	outline: none;
	text-align: center;
	font-size:20px;
	width: 100px;
}

</style>
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
		    			 <a class="nav-link active" data-toggle="tab" href="#home" style="border: 1px solid rgb(21,140,186);"><strong>급여</strong></a>
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

// document.write(thisMonth.substr( 0, 4 ));
// document.write(thisMonth.substr( 5, 2 ));

// 전 달로 이동
function prevCalendar() {
	
	var thisMonth = document.getElementsByName("getMonth")[0].value;
	
// 	alert("thisMonth year > "+thisMonth.substr( 0, 4 ));
// 	alert("thisMonth month > "+thisMonth.substr( 5, 7 ));
	var date = new Date(thisMonth.substr( 0, 4 ), thisMonth.substr( 5, 7 ) -1);
// 	alert("바뀌기 전 값 : " + date);
	var before = new Date(date.getFullYear(), date.getMonth() - 1);
	var getBeforeMonth = before.getFullYear() + "-" + (before.getMonth() + 1); 
// 	alert("바뀐 값 : " + getBeforeMonth);
	var getMonth = document.getElementsByName("getMonth")[0].value;
	getMonth = getBeforeMonth;
	
	monthGet(getMonth);
}

// 다음달로 이동
function nextCalendar() {
	
	var thisMonth = document.getElementsByName("getMonth")[0].value;
	
	var date = new Date(thisMonth.substr( 0, 4 ), thisMonth.substr( 5, 7 ) -1);
// 	alert("바뀌기 전 값 : " + date);
	var before = new Date(date.getFullYear(), date.getMonth() + 1);
	var getAfterMonth = before.getFullYear() + "-" + (before.getMonth() + 1); 
// 	alert("바뀐 값 : " + getAfterMonth);
	var getMonth = document.getElementsByName("getMonth")[0].value;
	getMonth = getAfterMonth; // 2019-11
	
	monthGet(getMonth);
}

</script> 

<div id="salAll">
	<div id="menu">
		<a href="#" onclick="prevCalendar()" style="text-decoration: none;">◀</a>
		<input id="viewMonth" name="getMonth" value="${month}"/> <!-- 컨트롤러에서 현재 달을 화면으로 보냄 -->
		<a href="#" onclick="nextCalendar()" style="text-decoration: none;">▶</a>
	</div>

	<div>
		<table id="tableHeader" class="table">
				<tr class="table-primary">
					<th style="width:55px;">No.</th><th style="text-align: center; width: 50px;">이름</th>
					<th style="padding-left:30px;">근무 시간 (주간)</th>
					<th style="padding-left:30px;">근무 시간 (야간)</th>
					<th style="width:90px; padding-left:35px;">시급</th>
					<th style="padding-left:25px;">급여</th><th style="padding-left:20px;">은행명</th>
					<th style="padding-left:30px;">계좌번호</th>
				</tr>
		</table>
	</div>

	<div style="overflow-y: auto; height: 337px;">
		<table class="table table-hover" style="overflow-y: auto;">
			<c:forEach var="dto" items="${albaLists}" varStatus="vs">
				<tr style="padding:0px;">
					<td style="width:50px;">${vs.count}</td>
					
					<!-- 아르바이트 이름이 4자리 이상일 경우 뒤에 .. 을 넣고 3자리까지만 표현 -->
					<c:if test="${fn:length(dto.alba_name) > 3}">
						<td style="text-align: center; width:62px;">${fn:substring(dto.alba_name,0,3)}..</td>
					</c:if>
					<c:if test="${fn:length(dto.alba_name) <= 3}">
						<td style="text-align: center; width:62px;">${dto.alba_name}</td>					
					</c:if>
					
					<!-- ex) .5 -> 경우 앞에 0을 붙여준다 -->
					<c:if test="${fn:substring(dto.alba_phone,0,1) == '.'}">
						<td style="text-align: center;">0${dto.alba_phone}</td>
					</c:if>
					<c:if test="${fn:substring(dto.alba_phone,0,1) != '.'}">
						<td style="text-align: center;">${dto.alba_phone}</td>
					</c:if>
					<c:if test="${fn:substring(dto.alba_address,0,1) == '.'}">
						<td style="text-align: center;">0${dto.alba_address}</td>
					</c:if>
					<c:if test="${fn:substring(dto.alba_address,0,1) != '.'}">
						<td style="text-align: center;">${dto.alba_address}</td>
					</c:if>
					
					<td style="padding-left:30px;">${dto.alba_timesal}원</td>
					
					<!-- 한달 급여가 alba_delflag 에 담김 -->
					<td>${dto.alba_delflag}원</td>
					<td>${dto.alba_bank}</td>
					<td>${dto.alba_account}</td>
				</tr>
			</c:forEach>
		</table>	
	</div>
</div>

</div><!-- div class=tab-content -->
				</div><!-- div class twoDepth -->
			</div><!-- div class=bodyfixed -->
		</div><!-- div class=bodyFrame -->
		<%@include file="../footer.jsp"%>
	</div>

</body>
<script type="text/javascript">

function monthGet(getMonth) {
// 	alert("monthGet > "+getMonth);
	location.href="./salary.do?getMonth="+getMonth;
// 	alert("monthGet2 > "+getMonth);
}

</script>

</html>





