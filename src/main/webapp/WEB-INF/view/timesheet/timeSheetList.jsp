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
    <script src="js/sample.js"></script>
	
	<button onclick="intutDB()">값 넣기 테스트</button>
	
</body>

<script type="text/javascript">

window.onload = function () {
	document.getElementById("currentDate").defaultValue = new Date().toISOString().substring(0, 10);
	var ts_date = document.getElementById('currentDate').value;
}

function intutDB() {

	var alba_seq = "55"; // alba_seq 들어온 매장의 알바들 조회해서 담음
	var ts_date = "2019-05-22"; // 화면의 데이트 긁어오기
	
//	alert(alba_seq);
	
	$.ajax({
		url: "./selTimeSheet.do", //요청 url
		type: "post", // 전송 처리방식
		asyn: false, // true 비동기 false 동기
		data: { 'alba_seq' : alba_seq ,'ts_date': ts_date }, // 서버 전송 파라메터 
//		dataType: "json",
		success: function(obj){
			alert("성공");
			alert(obj);
			
			// "1" : { "Mrs. Tomato": [ {"1" : "09:00-12:00"},{"2" : "13:00-14:00"},] }, 
			//{"999":{"이슬":          [ {"1" : "03:30-07:30","2":"01:30-02:30"}]}}
			
//			shiftObj = obj;
			
// 			alert(shiftObj);
			
			
		}, error : function() {
			alert("실패!!");
			alert(obj);
		}
	});
	
}


</script>
</html>