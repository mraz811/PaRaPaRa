<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/TimeTable.css"/>
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
		    			 <a class="nav-link active" data-toggle="tab" href="#home">TimeSheet</a>
		  				</li>
		  				<li class="nav-item">
		    			 <a class="nav-link" data-toggle="tab" id="albaLists">아르바이트</a>
		  				</li>
		  				<li class="nav-item">
		    			 <a class="nav-link" data-toggle="tab" id="salary">급여</a>
		  				</li>
					</ul>
					<div class="tab-content">

 <script type="text/javascript">
$(function(){
	$("#albaLists").click(function(){
		location.href="./selAlbaList.do";
	});
	$("#salary").click(function(){
		location.href="./salary.do";
	});	
});
function timeSheetDownload() {
	var currentDate = document.getElementById("currentDate").value;
	location.href = "./poiTimeSheet.do?ts_date="+currentDate;
}
</script> 

	<form action="#" method="get">

	<input type="date" id='currentDate' name="ts_date" value="${today}" onchange="changheDate()">
<!-- 	<button onclick="changheDate()">날짜 변경</button> -->
	<input type="button" value="엑셀로 다운로드" onclick="timeSheetDownload()">
	</form>

    <div id="test"></div>
<!--     <button id="addRow">AddRow</button> -->
<!--     <button id="colorChange">Change Random Color</button> -->
<!--     <button id="getData">getData</button> -->
    <script src="js/jquery-3.2.1.min.js"></script>
    <script src="js/createjs.min.js"></script>
    <script src="js/TimeTable.js"></script>
	
	
	 
		</div><!-- div class=tab-content -->
				</div><!-- div class twoDepth -->
			</div><!-- div class=bodyfixed -->
		</div><!-- div class=bodyFrame -->
		<%@include file="../footer.jsp"%>
	</div>
	
</body>

<script type="text/javascript">

// document.getElementById("currentDate").value = new Date().toISOString().substring(0, 10);

function changheDate() {
	
	var ts_date = document.getElementById("currentDate").value;
	
// 	alert(ts_date);
	
	var frm = document.forms[0];
	frm.action = "./selTimeSheet.do?ts_date="+ts_date;
	frm.submit();
	
}

var shiftObj = ${timeArr};

let obj = {
  // Beginning Time
  startTime: "01:00",
  // Ending Time
  endTime: "24:00",
  // Time to divide(minute)
  divTime: "30",
  // Time Table
  shift: shiftObj,
  // Other options
  option: {
	// workTime include time not displaying
      workTime: true,
//       bgcolor: ["#158cba"],
      bgcolor: ["#00FFFF"],
      // Set true when using TimeTable inside of BootStrap class row
      useBootstrap: true
  }
};

//Call Time Table
var instance = new TimeTable(obj);
console.time("time"); // eslint-disable-line
instance.init("#test");  
console.timeEnd("time");// eslint-disable-line

 </script>
</html>