<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<body>

${objWW}
<hr>

	<form action="./regiTimeSheet.do" method="post">

	파라파라 TIMESHEET
	<hr>
	<input type="date" id='currentDate' name="ts_date">
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
	
	<button id="intutShift" onclick="intutDB()">값 넣기 테스트</button>
	<button onclick="intutDB22()">값 넣기 테스트22</button>
	
</body>

<script type="text/javascript">

window.onload = function () {
	document.getElementById("currentDate").defaultValue = new Date().toISOString().substring(0, 10);
	var ts_date = document.getElementById('currentDate').value;
}

var shiftObj = ${objWW};

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