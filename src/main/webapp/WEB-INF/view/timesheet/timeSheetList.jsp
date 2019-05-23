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

${objWW}
<hr>
${objLists}
<hr>
${today}
<hr>
${lists}

<!-- 	<form action="./regiTimeSheet.do" method="post"> -->
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
    <!--Sample code when use TimeTable.js-->
<!--     <script src="js/sample.js"></script> -->
	
</body>

<script type="text/javascript">

var shiftObj = ${timeArAr};
// var shiftObj = ${objWW};
// var shiftObj = { "55":{"태":[{"1":"03:30-07:30","2":"01:30-02:30"}]} };
// var shiftObj = { "44":{"태2":[{"1":"00:00-00:00"}]} };

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
      bgcolor: ["#00FFFF"],
      // {index :  name, : index: name,,..}
      // selectBox index and shift index should be same
      // Give randome if shift index was not in selectBox index
      selectBox: {
          "35" : "Jason Paige 01051798468",
          "18" : "Mr.Jason"
      },
      // Set true when using TimeTable inside of BootStrap class row
      useBootstrap: false,
  }
};

//Call Time Table
var instance = new TimeTable(obj);
console.time("time"); // eslint-disable-line
instance.init("#test");
console.timeEnd("time");// eslint-disable-line

 </script>
</html>