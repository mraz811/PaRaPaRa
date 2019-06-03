<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	#choiceOwner{
		width: 300px;
		height: 390px;
		float: left;
		overflow-y: scroll;
	}
	#selectDate{
		width: 300px;
		height: 350px;
		float: left;
	}
	#onclickStore1,#onclickStore2{
		cursor: pointer;
	}
</style>
</head>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
function checkAllDel(bool){
	var checks = document.getElementsByName("menu_seq");
	for (var i = 0; i < checks.length; i++) {
		checks[i].checked = bool;
	}
}
function choiceMenu(){
	var checks = document.getElementsByName("store_code");
	var checkedBox = "";
	for (var i = 0; i < checks.length; i++) {
		if(checks[i].checked){
			checkedBox += checks[i].value+",";
		}
	}
}


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
			document.getElementById("choiceOwner").style.display = "none";
			document.getElementById("selectDate").style.display = "none";
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
	<form id="frm" action="#" method="post">
		<div id="choiceOwner">
			<table class="table table-hover">
				<thead>
					<tr>
						<th width="20px"><label id="onclickStore1"><input type="checkbox" value="전체선택" onclick="checkAllDel(this.checked)"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;매장명</label></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach begin="0" end="${fn:length(ownerList)}" items="${ownerList}" var="owner" varStatus="vs">
					<tr>
						<td colspan="2"><label id="onclickStore2"><input name="store_code" type="checkbox" value="${owner.store_code}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${owner.store_name}</label></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</div>
		<div id="selectDate">
			<input id="start" type="date" value="시작일"/>~<input id="end" type="date" value="종료일"/>
			<input type="button" value="선택완료"/>
		</div>
	</form>
	
	<input class="btn btn-outline-primary" type="button" value="통계보긔" onclick="adminStats()"/>
	
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