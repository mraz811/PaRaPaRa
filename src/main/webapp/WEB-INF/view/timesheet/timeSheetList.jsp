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
<%-- 
${objWW}
<hr>
${objLists}
<hr>
${today}
<hr>
${lists}
 
 --%>
  
 		<div id="container">
		<%@include file="../header.jsp"%>
		<div class="bodyFrame">
			<div class="bodyfixed">
				<div class="oneDepth">
					<!-- oneDepth에 적힐 내용이 들어감 ex)매장관리 -->
				
				</div>
				<!-- div class=oneDepth -->
				<div class="twoDepth">
					<!-- onDepth 안에 있는 twoDepth가 들어감 ex)1depth가 매장관리일 경우 a 태그에 적힐 내용은 일정관리, 재고, 발주 등  -->
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link" data-toggle="tab"
							href="#home">CALENDAR</a></li>
					</ul>
					<div class="tab-content">

  

	<form action="#" method="post">

	파라파라 TIMESHEET
	<hr>
	<input type="date" id='currentDate' name="ts_date" value="${today}">
	<hr>

	</form>

    <div id="test"></div>
    <button id="addRow">AddRow</button>
    <button id="colorChange">Change Random Color</button>
    <button id="getData">getData</button>
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

var shiftObj = ${timeArr};

let obj = {
  // Beginning Time
  startTime: "01:00",
  // Ending Time
  endTime: "25:00",
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