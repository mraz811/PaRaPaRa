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
	width: 120px;
	height: 30px;
	margin: 10px auto 10px 10px;
	
	font-size: 20px; 
	background-color: RGB(21,140,186); 
	color:white; 
	font-weight: bold; 
	padding: 0px 10px; 
	text-align: center;
	border-radius: 0.2em;
}

#waiting {
	width: 120px;
	height: 30px;
	margin: 10px 360px 10px auto;
	
	font-size: 20px; 
	background-color: RGB(21,140,186); 
	color:white; 
	font-weight: bold; 
	padding: 0px 10px; 
	text-align: center;
	border-radius: 0.2em;
}

#makingList {
	width: 490px;
	height: 255px;
	overflow-x: hidden;
	overflow-y: scroll;
}

#waitingList {
	width: 490px;
	height: 255px;
	overflow-x: hidden;
	overflow-y: scroll;
}

#makingDetail {
	width: 490px;
	height: 130px;
	top: 420px;
	position: absolute;
}

#waitingDetail {
	width: 490px;
	height: 130px;
	left: 550px;
	top: 420px;
	position: absolute;
}
#waitMenu,#makeMenu{
	cursor: pointer;
}
#requestlist{
	cursor: pointer;
}


	#changeCategory{
		position: absolute;
		left: 40px;
		top: 30px;
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
		overflow-y: scroll;
		overflow-x: hidden;
/* 		float: left; */
		position: absolute;
		left: 40px;
		top: 50px;
	}
	#requestStatus{
		width: 500px;
		height: 440px;
/* 		float: left; */
		overflow-y: scroll;
		overflow-x: hidden;
		position: absolute;
		left: 760px;
		top: 30px;
	}
	#resultDiv{
		width: 100px;
		height: 100px;
/* 		float: left; */
		position: absolute;
		left: 760px;
		top: 480px;
	}
	div.twoDepth{
		width: 1050px;
		height: 435px;
	}
	#mainMenu,#sideMenu,#drink{
		width: 160px;
		height: 40px;
		padding-top: 1px;	
	}
	.menuImg{
		width: 110px;
		height: 110px;
	}
	.downBtn,.upBtn{
		width: 25px;
		height: 25px;
		font-size: 10px;
		font-weight: bold;
		color: black;
		background-color: white;
	}
#sView,#cView{
	width: 300px;
	height: 300px;
	font-size: 40pt;
	align-content: center;
}

#choiceView{
	width: 1020px;
	height: 300px;
}
div.twoDepth{
	width: 1020px;
	height: 460px;
}
</style>
</head>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/requestStatus.js"></script>
<script type="text/javascript" src="./js/customOrder.js"></script>
<script type="text/javascript" src="./js/requestSocket.js"></script>
<script type="text/javascript">
window.onload = function (){
	document.getElementById("request").style.display = "none";
	document.getElementById("custom").style.display = "none";
}
</script>
<body>
	<div id="container">
		<%@include file="../header.jsp"%>
		
			<div class="bodyFrame">
				<div class="bodyfixed">
					<div class="oneDepth"><p>주　문</p></div>
					<div class="twoDepth">
						<ul class="nav nav-tabs">
							<li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#" style="border: 1px solid rgb(21,140,186);"><strong>주문</strong></a></li>
							<li class="nav-item"><a id="requestlist" class="nav-link" data-toggle="tab" onclick="selRequestList()">주문내역</a></li>
						</ul>
					<div id="choiceView" align="center">
						<input id="sView" class="btn btn-outline-primary" type="button" value="주문현황" onclick="choiceViewStatus('${loginDto.owner_id}','${loginDto.store_code}')"/>
						<input id="cView" class="btn btn-outline-success" type="button" value="고객주문" onclick="changeViewCustom('${loginDto.store_code}','${loginDto.owner_id}')"/>
					</div>
					<div id="request">
						<div id="make">
							<div id="making">
								<p>제조중</p>
							</div>
							<div id="makingList">
								<table class="table">
<!-- 									<thead> -->
<!-- 										<tr> -->
<!-- 											<td style="width: 60px; height: 28px">번호</td> -->
<!-- 											<td style="width: 270px; height: 28px">주문메뉴명</td> -->
<!-- 											<td style="width: 100px; height: 28px">주문시간</td> -->
<!-- 											<td style="width: 55px; height: 28px">완료</td> -->
<!-- 										</tr> -->
<!-- 									</thead> -->
									<tbody id="makeBody">
										<c:forEach begin="0" end="${fn:length(makeLists)}" items="${makeLists}" var="make" varStatus="vs">
											<tr>
												<td style="width: 60px;" >${make.rnum}</td>
												<td id="makeMenu" style="width: 270px;" onclick="makeMenuDetail(${make.request_seq},${make.rnum})">
													<c:choose>
														<c:when test="${fn:length(make.menu_name) > 20}">
															${fn:substring(make.menu_name,0,20)}...
														</c:when>
														<c:otherwise>
															${make.menu_name}
														</c:otherwise>
													</c:choose>
												</td>
												<td style="width: 100px;">${fn:substring(make.request_time,11,19)}</td>
												<td style="width: 55px; padding: 8px 0px;"><input style="width: 40px; height: 28px; padding: 2px 2px;" class="btn btn-outline-success" type="button" value="완료" onclick="changeStatusCode3(this,${make.request_seq})" /></td>
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
<!-- 									<thead> -->
<!-- 										<tr> -->
<!-- 											<td width="60px">번호</td> -->
<!-- 											<td width="220px;">주문메뉴명</td> -->
<!-- 											<td width="90px;">주문시간</td> -->
<!-- 											<td style="width: 45px; padding: 12px 0px;">제조</td> -->
<!-- 											<td style="width: 45px; padding: 12px 0px;">환불</td> -->
<!-- 										</tr> -->
<!-- 									</thead> -->
									<tbody id="waitBody">
										<c:forEach begin="0" end="${fn:length(waitLists)}" items="${waitLists}" var="wait" varStatus="vs">
											<tr>
												<td width="60px;">${wait.rnum}</td>
												<td id="waitMenu" width="220px;" onclick="waitMenuDetail(${wait.request_seq},${wait.rnum})">
													<c:choose>
														<c:when test="${fn:length(wait.menu_name) > 16}">
															${fn:substring(wait.menu_name,0,16)}...
														</c:when>
														<c:otherwise>
															${wait.menu_name}
														</c:otherwise>
													</c:choose>
												</td>
												<td width="90px;" >${fn:substring(wait.request_time,11,19)}</td>
												<td style="width: 45px; padding: 8px 0px;"><input style="width: 40px; height: 28px; padding: 2px 2px;" class="btn btn-outline-primary" type="button" value="제조" onclick="changeStatusCode2(this,'${wait.request_seq}!${wait.rnum}!${wait.menu_name}!${fn:substring(wait.request_time,11,19)}')" /></td>
												<td style="width: 45px; padding: 8px 0px;"><input style="width: 40px; height: 28px; padding: 2px 2px;" class="btn btn-outline-warning" type="button" value="환불" onclick="changeStatusCode0(this,${wait.request_seq})"/></td>
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
		<div id="changeCategory">
			<input class="btn btn-outline-primary" id="mainMenu" name="menu_category" type="button" value="주메뉴" onclick="mainMenu()" /> 
			<input class="btn btn-outline-primary" id="sideMenu" name="menu_category" type="button" value="사이드메뉴" onclick="sideMenu()" /> 
			<input class="btn btn-outline-primary" id="drink" name="menu_category" type="button" value="음료" onclick="drinkMenu()" />
		</div>
		<div class="tab-content">
			<div id="menuList">
				<c:forEach begin="0" end="${fn:length(menuList)}"
					items="${menuList}" var="menu" varStatus="vs">
					<div id="menu${vs.count}" class="menu">
						<img class="menuImg" src="${menu.fileDto.file_rurl}" alt="" /> 
						<input id="mmenu${vs.count}" type="hidden" value="${menu.fileDto.file_rurl}" /> 
						<input class="btn btn-outline-primary" id="${menu.menu_seq}" type="button" name="addButton" value="추가" onclick="addMenu('menu${vs.count},${menu.menu_seq},${menu.menu_name},${menu.menu_price}')" />
						<br>${menu.menu_name}&nbsp;&nbsp;${menu.menu_price}</div>
				</c:forEach>
			</div>
			<form id="regiForm" action="./regiCustomOrder.do" method="get">
				<div id="requestStatus">
					<table class="table">
						<thead>
							<tr>
								<td style="width: 80px;">메뉴 이미지</td>
								<td style="width: 70px;">메뉴명</td>
								<td style="width: 60px;">수량</td>
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
					<table id="totalCal" class="table">
						<tr class="table-info" style="height: 53.6px;">
							<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;총금액</th>
							<th>
								<label id="totalResultPrice" style="width: 85px;"></label>
								<input id="totalPrice" type="hidden" class="txt" name="totalMenuPrice" value="0" readonly="readonly" style="text-align: right;background-color: highlight;width: 70px;">
							</th>
						</tr>
						<tr>
							<th>
								<input type="button" class="btn btn-outline-success" value="주문 완료" onclick="customRequest()" />
							</th>
							<th>
								<input type="button" class="btn btn-outline-warning" value="주문 취소" onclick="cancelRequest()" /> 
							</th>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div><!-- 고객 주문 -->
	<input type="hidden" id="targetId" value="${loginDto.owner_id}"/>
	<input type="hidden" id="nick" value="${loginDto.store_code}"/>
</body>
</html>