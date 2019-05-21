<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
window.onload = function () {
	document.getElementById('currentDate').value = new Date().toISOString().substring(0, 10);
	var ts_date = document.getElementById('currentDate').value;
	alert(ts_date);	
}

</script>
<body>

	<form action="#">

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
    <script src="js/sample.js"></script>
	
	
</body>
</html>