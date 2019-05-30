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
		width: 500px;
		height : 390px;
		margin-top : 40px;
		overflow: scroll;
		float: left;
	}
	#requestStatus{
		width: 500px;
		height: 380px;
		float: left;
		overflow: scroll;
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
</style>
</head>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript">
	function makeMenuDetail(request_seq,rnum){
		$.ajax({
			url : "./selMakeReqDetail.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq},
			dataType : "json",
			success : function(obj){
				var makingDetail = document.getElementById("makingDetail");
				makingDetail.innerHTML = "<div>"+rnum+"</div>"
										  +"<div>"+obj.makeMenu.request_time+"</div>"
										  +"<div>"+obj.makeMenu.menu_name+"</div>";
			},error : function(obj){
				alert("관리자에게 문의해주세요"); 
			}
		})
	}
	function waitMenuDetail(request_seq,rnum){
		$.ajax({
			url : "./selWaitReqDetail.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq},
			dataType : "json",
			success : function(obj){
				var waitingDetail = document.getElementById("waitingDetail");
				waitingDetail.innerHTML = "<div>"+rnum+"</div>"
										  +"<div>"+obj.makeMenu.request_time+"</div>"
										  +"<div>"+obj.makeMenu.menu_name+"</div>";
			},error : function(obj){
				alert("관리자에게 문의해주세요"); 
			}
		})
	}
	function changeStatusCode3(request_seq){
		var os_code = "3";
		$.ajax({
			url : "./updateOrderState.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq,"os_code":os_code},
			success : function(obj){
				location.reload();
			},error : function(obj){
				alert("관리자에게 문의해주세요"); 
			}
		})
	}
	function changeStatusCode2(request_seq){
		var os_code = "2";
		$.ajax({
			url : "./updateOrderState.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq,"os_code":os_code},
			success : function(obj){
				location.reload();
			},error : function(obj){
				alert("관리자에게 문의해주세요"); 
			}
		})
	}
	function changeStatusCode0(request_seq){
		var os_code = "0";
		$.ajax({
			url : "./updateOrderState.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq,"os_code":os_code},
			success : function(obj){
				location.reload();
			},error : function(obj){
				alert("관리자에게 문의해주세요"); 
			}
		})
	}
	function selRequestList(){
		location.href="./selRequestList.do";
	}
function loadDoc() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var response = JSON.parse(this.responseText);
			$.each(response,function(key,value){
				if(key == "wait"){
					$.each(value,function(key,menu){
			  			$("#print").append();
					});
				}
			});
		}
	};
	xhttp.open("GET", "http://localhost:8091/PaRaPaRa/selRequestStatusRest.do", true);
	xhttp.setRequestHeader("waitList", "makeLists");
	xhttp.send();
}
///////////////////////////////////////////
//고객 주문
function mainMenu(){
	var menu_category = "주메뉴";
	var menuList = document.getElementById("menuList");
	$.ajax({
		url : "./CselAllMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			var cnt = 1;
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						htmlText += "<div id=\"menu"+cnt+"\" class=\"menu\"\"><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><input id=\"mmenu"+cnt+"\" type=\"hidden\" value=\""+menu.file_rurl+"\" /><input type=\"button\" value=\"추가\" onclick=\"addMenu('menu"+cnt+++","+menu.menu_seq+","+menu.menu_name+","+menu.menu_price+"')\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
			alert(obj); 
		}
	})
}
function sideMenu(){
	var menu_category = "사이드메뉴";
	$.ajax({
		url : "./CselAllMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			var cnt = 1;
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						htmlText += "<div id=\"menu"+cnt+"\" class=\"menu\"\"><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><input id=\"mmenu"+cnt+"\" type=\"hidden\" value=\""+menu.file_rurl+"\" /><input type=\"button\" value=\"추가\" onclick=\"addMenu('menu"+cnt+++","+menu.menu_seq+","+menu.menu_name+","+menu.menu_price+"')\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
			alert(obj); 
		}
	})
}
function drinkMenu(){
	var menu_category = "음료";
	$.ajax({
		url : "./CselAllMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			var cnt = 1;
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){ 
					$.each(value,function(key,menu){
						htmlText += "<div id=\"menu"+cnt+"\" class=\"menu\"\"><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><input id=\"mmenu"+cnt+"\" type=\"hidden\" value=\""+menu.file_rurl+"\" /><input type=\"button\" value=\"추가\" onclick=\"addMenu('menu"+cnt+++","+menu.menu_seq+","+menu.menu_name+","+menu.menu_price+"')\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
			alert(obj); 
		}
	})
}
function addMenu(menu){
	
	var menuInfo = menu.split(",");
	var newTr_id = menuInfo[0];
	var menu_seq = menuInfo[1]; //주문 메뉴 번호
	var menu_name = menuInfo[2]; //주문 메뉴 이름
	var menu_qty = 1; //처음 주문 메뉴 수량
	var menu_price = menuInfo[3]; //주문 메뉴 가격
	var sum_price = menuInfo[3]; //주문 메뉴 가격 합계
	var file_rurl = document.getElementById("m"+newTr_id).value;//이미지 파일 링크
	
	var newTr = document.createElement("tr"); //새로운 div 생성
    newTr.setAttribute("id", newTr_id);
	
	var mBody = document.getElementById("mBody");
	var currentLine = document.getElementById(newTr_id);
	currentLine.style.display = "none";
	
	mBody.appendChild(newTr).innerHTML = "<td>"
											+"<img class=\"menuImg\" src=\""+file_rurl+"\" alt=\"\"/>"
										+"</td>"
										+"<td>"
											+"<input type=\"hidden\" name=\"newTr_id\" value=\""+newTr_id+"\"/>"
											+"<input type=\"hidden\" name=\"menu_seq\" value=\""+menu_seq+"\"/>"
											+"<input type=\"text\" name=\"menu_name\" value=\""+menu_name+"\"/>"
										+"</td>"
										+"<td>"
											+"<input type=\"button\" class=\"downBtn\" value=\"-\" onclick=\"minus(this)\">"
											+"<input type=\"text\" name=\"menu_cnt\" value=\""+menu_qty+"\"/>"
											+"<input type=\"button\" class=\"upBtn\" value=\"+\" onclick=\"plus(this)\">"
										+"</td>"
										+"<td>"
											+"<input type=\"text\" name=\"menu_price\" value=\""+menu_price+"\"/>"
										+"</td>";
}
function customRequest() {
	var xhttp = new XMLHttpRequest();
	
	var seq = document.getElementsByName("menu_seq");
	var cnt = document.getElementsByName("menu_cnt");
	var price = document.getElementsByName("menu_price");
	
	var menu_seq = new Array();
	var menu_cnt = new Array();
	var menu_price = new Array();
	for (var i = 0; i < seq.length; i++) {
		menu_seq[i] = seq[i].value;
		menu_cnt[i] = cnt[i].value;
		menu_price[i] = price[i].value;
	}
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var response = JSON.parse(this.responseText);
			var newTr_id = document.getElementsByName("newTr_id");
			for (var i = 0; i < newTr_id.length; i++) {
				var temp = newTr_id[i].value;
				document.getElementById(temp).style.display = "block";
			}
			alert(response.success);
			document.getElementById("mBody").innerHTML = "";
			
		}
	};
	xhttp.open("POST", "http://localhost:8091/PaRaPaRa/selRequestStatusRest.do", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("menu_seq="+menu_seq+"&menu_cnt="+menu_cnt+"&menu_price="+menu_price);
}
///////////////////////////////////////////
function changeViewCustom(){
	document.getElementById("container").style.display = "none";
	document.getElementById("custom").style.display = "block";
	document.getElementById("dddd").style.display = "none";
}
window.onload = function(){
	document.getElementById("container").style.display = "block";
	document.getElementById("custom").style.display = "none";
}
</script>
<body>
	<div id="container">
		<%@include file="../header.jsp"%>
		
			<div class="bodyFrame">
				<div class="bodyfixed">
					<div class="oneDepth">주문</div>
					<div class="twoDepth">
						<ul class="nav nav-tabs">
							<li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#">주문현황</a></li>
							<li class="nav-item"><a id="requestlist" class="nav-link" data-toggle="tab" onclick="selRequestList()">주문내역</a></li>
						</ul>
					<div id="request">
						<div id="make">
							<div id="making">제조중</div>
							<div id="makingList">
								<table class="table">
									<tr>
										<td style="width: 60px; height: 28px">번호</td>
										<td style="width: 270px; height: 28px">주문메뉴명</td>
										<td style="width: 100px; height: 28px">주문시간</td>
										<td style="width: 55px; height: 28px">완료</td>
									</tr>
									<c:forEach begin="0" end="${fn:length(makeLists)}" items="${makeLists}" var="make" varStatus="vs">
										<tr>
											<td style="width: 60px; height: 28px" >${make.rnum}</td>
											<td id="makeMenu" style="width: 270px; height: 28px" onclick="makeMenuDetail(${make.request_seq},${make.rnum})">
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
											<td style="width: 55px; height: 28px"><input type="button" value="완료" onclick="changeStatusCode3(${make.request_seq})" /></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
						<div id="makingDetail">
						</div>
						<div id="wait">
							<div id="waiting">대기중</div>
							<div id="waitingList">
								<table class="table">
									<tr>
										<td style="width: 60px; height: 28px">번호</td>
										<td style="width: 220px; height: 28px">주문메뉴명</td>
										<td style="width: 100px; height: 28px">주문시간</td>
										<td style="width: 40px; height: 28px">제조</td>
										<td style="width: 40px; height: 28px">환불</td>
									</tr>
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
											<td style="width: 40px; height: 28px"><input type="button" value="제조" onclick="changeStatusCode2(${wait.request_seq})" /></td>
											<td style="width: 40px; height: 28px"><input type="button" value="환불" onclick="changeStatusCode0(${wait.request_seq})"/></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
						<div id="waitingDetail">
						<input type="button" value="rest" onclick="loadDoc()"/>
						</div>
					</div><!-- 주문 현황 -->
					<div id="dddd" style="top: 500px; position: absolute;">
						<input type="button" value="고객주문으로" onclick="changeViewCustom()"/>
					</div>
	
					</div>
				</div>
			</div>
	
		
		
		<%@include file="../footer.jsp"%>
	</div>
	<div id="custom">
		<input id="mainMenu" name="menu_category" type="button" value="주메뉴"
			onclick="mainMenu()" /> <input id="sideMenu" name="menu_category"
			type="button" value="사이드메뉴" onclick="sideMenu()" /> <input id="drink"
			name="menu_category" type="button" value="음료" onclick="drinkMenu()" />
		<div class="tab-content">
			<div id="menuList">
				<c:forEach begin="0" end="${fn:length(menuList)}"
					items="${menuList}" var="menu" varStatus="vs">
					<div id="menu${vs.count}" class="menu">
						<img class="menuImg" src="${menu.fileDto.file_rurl}" alt="" /> <input
							id="mmenu${vs.count}" type="hidden"
							value="${menu.fileDto.file_rurl}" /> <input type="button"
							value="추가"
							onclick="addMenu('menu${vs.count},${menu.menu_seq},${menu.menu_name},${menu.menu_price}')" />
						<br>${menu.menu_name}&nbsp;&nbsp;${menu.menu_price}</div>
				</c:forEach>
			</div>
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
			<form id="regiForm" action="./regiCustomOrder.do" method="get">
				<div id="requestStatus">
					<table>
						<tbody id="mBody">
						</tbody>
					</table>
				</div>
				<input type="button" class="btn btn-outline-success" value="주문 완료"
					onclick="customRequest()" />
				<!--  -->
			</form>
			<input type="button" class="btn btn-outline-warning" value="주문 취소"
				onclick="location.reload()" /> <input type="button" value="rest"
				onclick="loadDoc()" />
		</div>
	</div><!-- 고객 주문 -->
</body>
</html>