<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<title>Insert title here</title>
<style type="text/css">
#container {
	width: 1020px;
	height: 435px;
	position: relative;
}

#make {
	width: 510px;
	height: 435px;
	position: absolute;
	left: 20px;
}

#wait {
	width: 510px;
	height: 435px;
	position: absolute;
	right: 0px;
}

#making {
	width: 140px;
	height: 20px;
	margin: 10px auto 10px 10px;
}

#waiting {
	width: 140px;
	height: 20px;
	margin: 10px 360px 10px auto;
}

#makingList {
	width: 490px;
	height: 270px;
	overflow-x: hidden;
	overflow-y: scroll;
}

#waitingList {
	width: 490px;
	height: 270px;
	overflow-x: hidden;
	overflow-y: scroll;
}

#makingDetail {
	width: 510px;
	height: 130px;
	top: 430px;
	position: absolute;
}

#waitingDetail {
	width: 510px;
	height: 130px;
	left: 550px;
	top: 430px;
	position: absolute;
}
#waitMenu,#makeMenu{
	cursor: pointer;
}
#requestlist{
	cursor: pointer;
}



.menu{
		width: 200px;
		height: 130px;
		float: left;
		margin-right: 30px;
		margin-bottom: 20px;
		text-align: center;
	}
	#menuList{
		width: 720px;
		height : 500px;
		margin-top : 40px;
		overflow: scroll;
		float: left;
	}
	#requestStatus{
		width: 700px;
		height: 450px;
		float: left;
		overflow: scroll;
	}
	#resultDiv{
		width: 560px;
		height: 100px;
		float: left;
	}
	div.twoDepth{
		width: 1050px;
		height: 435px;
	}
	#mainMenu{
		width: 140px;
		height: 20px;
		
	}
	#sideMenu{
		width: 140px;
		height: 20px;
	}
	#drink{
		width: 140px;
		height: 20px;
	}
	.menuImg{
		width: 110px;
		height: 110px;
	}
	.downBtn,.upBtn{
		width: 30px;
		height: 30px;
		font-size: 10px;
		font-weight: bold;
		color: black;
		background-color: white;
	}
</style>
</head>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/requestStatus.js"></script>
<script type="text/javascript" src="./js/customOrder.js"></script>
<script type="text/javascript" src="./js/requestSocket.js"></script>
<script type="text/javascript">
function changeViewCustom(){
	document.getElementById("request").style.display = "none";
	document.getElementById("custom").style.display = "block";
	document.getElementById("container").style.display = "none";
	document.getElementById("choiceView").style.display = "none";
}
window.onload = function (){
	document.getElementById("request").style.display = "none";
	document.getElementById("custom").style.display = "none";
}
function choiceViewStatus(){
	document.getElementById("request").style.display = "block";
	document.getElementById("custom").style.display = "none";
	document.getElementById("choiceView").style.display = "none";
}
//////////////////////////////////////////////////////////////////
</script>
<body>
	<div id="container">
		<%@include file="../header.jsp"%>
		
			<div class="bodyFrame">
				<div class="bodyfixed">
					<div class="oneDepth">주문</div>
					<div class="twoDepth">
						<ul class="nav nav-tabs">
							<li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#">주문</a></li>
							<li class="nav-item"><a id="requestlist" class="nav-link" data-toggle="tab" onclick="selRequestList()">주문내역</a></li>
						</ul>
						<div id="choiceView">
							<input style="width: 200px;height: 200px;" type="button" value="주문현황" onclick="choiceViewStatus()"/>
							<input style="width: 200px;height: 200px;" type="button" value="고객주문" onclick="changeViewCustom()"/>
						</div>
					<div id="request">
						<div id="make">
							<div id="making">제조중</div>
							<div id="makingList">
								<table class="table">
									<thead>
										<tr>
											<td style="width: 60px; height: 28px">번호</td>
											<td style="width: 270px; height: 28px">주문메뉴명</td>
											<td style="width: 100px; height: 28px">주문시간</td>
											<td style="width: 55px; height: 28px">완료</td>
										</tr>
									</thead>
									<tbody id="makeBody">
										<c:forEach begin="0" end="${fn:length(makeLists)}" items="${makeLists}" var="make" varStatus="vs">
											<tr>
												<td style="width: 60px; height: 28px" >${make.rnum}</td>
												<td id="makeMenu" style="width: 270px; height: 28px;" onclick="makeMenuDetail(${make.request_seq},${make.rnum})">
													<c:choose>
														<c:when test="${fn:length(make.menu_name) > 20}">
															${fn:substring(make.menu_name,0,20)}...
														</c:when>
														<c:otherwise>
															${make.menu_name}
														</c:otherwise>
													</c:choose>
												</td>
												<td style="width: 100px; height: 28px" >${fn:substring(make.request_time,11,19)}</td>
												<td style="width: 55px; height: 28px"><input type="button" value="완료" onclick="changeStatusCode3(this,${make.request_seq})" /></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<div id="makingDetail">
						</div>
						<div id="wait">
							<div id="waiting">대기중</div>
							<div id="waitingList">
								<table class="table">
									<thead>
										<tr>
											<td style="width: 60px; height: 28px">번호</td>
											<td style="width: 220px; height: 28px">주문메뉴명</td>
											<td style="width: 100px; height: 28px">주문시간</td>
											<td style="width: 40px; height: 28px">제조</td>
											<td style="width: 40px; height: 28px">환불</td>
										</tr>
									</thead>
									<tbody id="waitBody">
										<c:forEach begin="0" end="${fn:length(waitLists)}" items="${waitLists}" var="wait" varStatus="vs">
											<tr>
												<td style="width: 60px; height: 28px" >${wait.rnum}</td>
												<td id="waitMenu" style="width: 220px; height: 28px" onclick="waitMenuDetail(${wait.request_seq},${wait.rnum})">
													<c:choose>
														<c:when test="${fn:length(wait.menu_name) > 16}">
															${fn:substring(wait.menu_name,0,16)}...
														</c:when>
														<c:otherwise>
															${wait.menu_name}
														</c:otherwise>
													</c:choose>
												</td>
												<td style="width: 100px; height: 28px" >${fn:substring(wait.request_time,11,19)}</td>
												<td style="width: 40px; height: 28px"><input type="button" value="제조" onclick="changeStatusCode2(this,'${wait.request_seq},${wait.rnum},${wait.menu_name},${fn:substring(wait.request_time,11,19)}')" /></td>
												<td style="width: 40px; height: 28px"><input type="button" value="환불" onclick="changeStatusCode0(this,${wait.request_seq})"/></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<div id="waitingDetail">
						</div>
					</div><!-- 주문 현황 -->
					</div>
				</div>
			</div>
	
		
		
		<%@include file="../footer.jsp"%>
	</div>
	<div id="custom" style="display: none;">
		<input id="mainMenu" name="menu_category" type="button" value="주메뉴"
			onclick="mainMenu()" /> <input id="sideMenu" name="menu_category"
			type="button" value="사이드메뉴" onclick="sideMenu()" /> <input id="drink"
			name="menu_category" type="button" value="음료" onclick="drinkMenu()" />
		<div class="tab-content">
			<div id="menuList">
				<c:forEach begin="0" end="${fn:length(menuList)}"
					items="${menuList}" var="menu" varStatus="vs">
					<div id="menu${vs.count}" class="menu">
						<img class="menuImg" src="${menu.fileDto.file_rurl}" alt="" /> 
						<input id="mmenu${vs.count}" type="hidden" value="${menu.fileDto.file_rurl}" /> 
						<input id="${menu.menu_seq}" type="button" name="addButton" value="추가" onclick="addMenu('menu${vs.count},${menu.menu_seq},${menu.menu_name},${menu.menu_price}')" />
						<br>${menu.menu_name}&nbsp;&nbsp;${menu.menu_price}</div>
				</c:forEach>
			</div>
			<form id="regiForm" action="./regiCustomOrder.do" method="get">
				<div id="requestStatus">
					<table>
						<thead>
							<tr>
								<td style="width: 120px;">메뉴 이미지</td>
								<td style="width: 120px;">메뉴명</td>
								<td style="width: 150px;">수량</td>
								<td style="width: 100px;">가격</td>
							</tr>
						</thead>
					</table>
					<table>
						<tbody id="mBody">
						</tbody>
					</table>
				</div>
				<div id="resultDiv">
					<table id="totalCal">
						<tr>
							<th colspan="2">합계</th>
							<th>총금액</th>
							<th>
								<input type="text" class="txt" name="totalMenuPrice" value="0" readonly="readonly" style="text-align: right;">원
							</th>
						</tr>
						<tr>
							<th colspan="3">
								<input type="button" class="btn btn-outline-success" value="주문 완료" onclick="customRequest()" />
							</th>
							<th colspan="3">
								<input type="button" class="btn btn-outline-warning" value="주문 취소" onclick="cancelRequest()" /> 
							</th>
						</tr>
					</table>
				</div>
			</form>
			
		</div>
	</div><!-- 고객 주문 -->
</body>
</html>