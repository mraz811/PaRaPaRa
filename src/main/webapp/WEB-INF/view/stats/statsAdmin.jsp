<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%request.setCharacterEncoding("UTF-8");%>
<%response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업주:수익/지출 통계</title>
<style type="text/css">
#frm{
	position: relative;
}
#viewStats{
	position: absolute;
	left: 610px;
}
#statsDiv {
	position: absolute;
	width:900px;
	height:350px;
 	left : 150px;
	top: 50px;
}

#stats {
	position: relative;
	width: 900px;
	height: 350px;
	right: 0px;
}

#menu {
	position: absolute;
	width: 330px;
	height: 350px;
	right: 15px;
}

#money {
	position: absolute;
	left: 110px; 
	width : 450px;
	height: 350px;
}

#choiceOwner {
	position: absolute;
	left:0px;
	width: 250px;
	height: 345px;
	overflow-y: scroll;
}

#selectDate {
	width: 400px;
	position: absolute;
	left: 250px;
	top: 0px;
}

#onclickStore1, #onclickStore2 {
	cursor: pointer;
}
span{
	position: absolute;
	left: 700px;
}

</style>
</head>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	function checkAllDel(bool) {
		var checks = document.getElementsByName("store_code");
		for (var i = 0; i < checks.length; i++) {
			checks[i].checked = bool;
		}
	}

	function getFormatDate(date) {
		var year = date.getFullYear(); //yyyy 
		var month = (1 + date.getMonth()); //M 
		var month = month >= 10 ? month : '0' + month; // month 두자리로 저장 
		var day = date.getDate(); //d 
		day = day >= 10 ? day : '0' + day; //day 두자리로 저장
		return year + '' + month + '' + day;
	}
	
	
	
	function adminStats() {
		var start = document.getElementById("start").value;
		var end = document.getElementById("end").value;
		
		var year = start.substring(0, 4);
		var month = start.substring(5, 7)
		var day = start.substring(8, 10);
		var startDay = new Date(year, month - 1, day, 0, 0, 0);

		var year = end.substring(0, 4);
		var month = end.substring(5, 7)
		var day = end.substring(8, 10);
		var endDay = new Date(year, month - 1, day, 0, 0, 0);
		
		endDay.setDate(endDay.getDate() + 1);
		var endDayString = getFormatDate(endDay);
		
		
		
		
		
		// 시작날짜를 선택하지 않았을 때
		if(start=="" || start==null) {
			start = "1000-01-01";
		}
		// 종료날짜를 선택하지 않았을 때
		if(end=="" || end==null) {
			endDay = new Date("9999","12","31",0,0,0);
			endDayString = "99991231";
		}
		// 모두 선택하지 않았을 때
		if( (start=="" || start==null) && (end=="" || end==null)) {
			start = "1000-01-01";
			endDay = new Date("9999","12","31",0,0,0);
			endDayString = "99991231";
		}

		var chkStart = getFormatDate(startDay);
		var chkEnd = getFormatDate(endDay);
		
		if(Number(chkStart) > Number(chkEnd)){
			swal("종료일은 시작일보다 이전일 수 없습니다.");
			return false;
		}
		
		var checks = document.getElementsByName("store_code");
		var checkedBox = "";
		for (var i = 0; i < checks.length; i++) {
			if (checks[i].checked) {
				checkedBox += checks[i].value + ",";
			}
		}
		if(checkedBox == ""){
			swal("매장을 선택해주세요.");
			return false;
		}
		var store_code = checkedBox;

		$.ajax({
					url : "./adminStatsIncome.do",
					type : "get",
					async : true,
					data : {
						"start" : start,
						"end" : endDayString,
						"store_code" : store_code
					},
					dataType : "json",
					success : function(obj) {
						if (obj.stats == "no") {
							swal("선택지점의 수익이 없습니다.");
						} else {

							google.charts.load('current', {
								packages : [ 'corechart' ]
							});
							google.charts.setOnLoadCallback(drawChart);
							function drawChart() {
								var data = new google.visualization.DataTable(obj.jstr);
								var options = {
									title : '업주 수익 통계',
									hAxis : {
										titleTextStyle : {
											fontSize : 18,
											color : '#053061',
											bold : true,
											italic : false
										}
									},
									vAxis: {format: 'Decimal',
										minValue:0,
										maxValue:0	
										},
									bar : {
										groupWidth : '40%' 
									}

								};
								var data2 = new google.visualization.DataTable(obj.jstr2);
								var options2 = {
									title : '선택 매장 판매 TOP 5 메뉴 통계',
								};
								var chart = new google.visualization.ColumnChart(
										document.getElementById('money'));
								chart.draw(data, options);
								var chart = new google.visualization.PieChart(
										document.getElementById('menu'));
								chart.draw(data2, options2);
							}
						}
					},
					error : function(obj) {
						alert("등록에 실패하였습니다."); //성공햇는데 error로 넘어옴 ㅡㅡ; producer 없애고 JSONObject로 던져서 해결함
					}
				});

	}
	function viewMenu(){
		location.href = "./adminStats2.do";
	}
</script>
<body>
	<div id="container">
		<%@include file="../header.jsp"%>
		<div class="bodyFrame">
			<div class="bodyfixed">
				<div class="oneDepth"><p>통　계</p></div>
				<div class="twoDepth">
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#" style="border: 1px solid rgb(21,140,186);"><strong>수익/메뉴 통계</strong></a></li>
					</ul>
					<form id="frm" action="#" method="post">
						<div id="selectDate">
							<strong>날짜 선택</strong> : <input id="start" type="date" value="시작일" />~~<input id="end" type="date" value="종료일" />
						</div>
						<fieldset>
							<div id="choiceOwner" class="form-group" >
								<div class="custom-control custom-checkbox">
									<table class="table table-hover">
										<thead>
											<tr>
												<th width="20px">
													<input id="allChk" class="custom-control-input" type="checkbox" value="전체선택" onclick="checkAllDel(this.checked)" />
													<label id="onclickStore1" class="custom-control-label" for="allChk">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;매장명</label>
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach begin="0" end="${fn:length(ownerList)}" items="${ownerList}" var="owner" varStatus="vs">
												<tr>
													<td colspan="2">
													<input id="chk${vs.count}" class="custom-control-input" name="store_code" type="checkbox" value="${owner.store_code}" />
													<label id="onclickStore2" class="custom-control-label" for="chk${vs.count}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${owner.store_name}</label>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</fieldset>
						<input id="viewStats" class="btn btn-outline-primary" type="button" value="통계보긔" onclick="adminStats()" />
						<span>※미선택 시 전체기간 조회</span>
						<div id="statsDiv">
							<div id="stats">
								<div id="money"></div>
								<div id="menu"></div>
							</div>
						</div>
					</form>

				</div>
			</div>
		</div>
		<%@include file="../footer.jsp"%>
	</div>
</body>
</html>