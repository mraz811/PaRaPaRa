<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	response.setContentType("text/html; charset=UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업주:수익/지출 통계</title>
<style type="text/css">
	#money,#menu{
		width: 500px;
		float: left;
	}
	
</style>
</head>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
function getFormatDate(date){ 
	var year = date.getFullYear();	//yyyy 
	var month = (1 + date.getMonth());	//M 
	var month = month >= 10 ? month : '0' + month;	// month 두자리로 저장 
	var day = date.getDate();	//d 
	day = day >= 10 ? day : '0' + day;	//day 두자리로 저장
	return year + '' + month + '' + day;
}
function selectDay(){
	start = document.getElementById("start").value;
	end = document.getElementById("end").value;
	
	var year = end.substring(0, 4);
	var month = end.substring(5, 7)
	var day = end.substring(8,10);
	var endDay = new Date(year,month-1,day,0,0,0);
	
	endDay.setDate(endDay.getDate()+1);
	endDayString = getFormatDate(endDay);
	
	$.ajax({
		url : "./ownerStatsIn.do",
		type : "get",
		async : true,
		data : {"start":start,"end":endDayString},
		dataType : "json",
		success : function(obj){
			google.charts.load('current', {packages : [ 'corechart' ]});
			google.charts.setOnLoadCallback(drawChart);
			function drawChart() {
				var data = new google.visualization.DataTable(obj.jstr);
				var options = {
						title : '업주 수익/지출 통계',
		 				hAxis : {title : obj.store_name,
		 					 textStyle: {
		 	 		            fontSize: 14,
		 	 		            color: '#053061',
		 	 		            bold: true,
		 	 		            italic: false
		 	 		          },
		 	 		          titleTextStyle: {
		 	 		            fontSize: 18,
		 	 		            color: '#053061',
		 	 		            bold: true,
		 	 		            italic: false
		 	 		          }
		 					},
		 				
				};
				var data2 = new google.visualization.DataTable(obj.jstr2);
				var options2 = {
						title : '업주 판매 메뉴 통계',
				};
				var chart = new google.visualization.ColumnChart(document.getElementById('money'));
			    chart.draw(data,options);
			    var chart = new google.visualization.PieChart(document.getElementById('menu'));
			    chart.draw(data2,options2);
			}
		},error : function(obj){
			alert("등록에 실패하였습니다."); //성공햇는데 error로 넘어옴 ㅡㅡ; producer 없애고 JSONObject로 던져서 해결함
		}
	});
	
	
}	
</script>
<body>
<div id="container">
	<%@include file="../header.jsp" %>
	<div class="bodyFrame">
	<div class="bodyfixed">
		<div class="oneDepth">
		통계
		</div>
		<div class="twoDepth">
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#">수익/지출 통계</a>
  				</li>
			</ul>
	<div id="selectDate">
		<input id="start" type="date" value="시작일"/>~<input id="end" type="date" value="종료일"/>
		<input type="button" value="선택완료" onclick="selectDay()"/>
	</div>
	<div id="money">
	</div>
	<div id="menu" style="width: 500px; float: left;">
	</div>
		</div>
	</div>
	</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>