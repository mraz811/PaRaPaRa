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
<title>고객 주문 화면</title>
<style type="text/css">
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
<script type="text/javascript"></script>
<script type="text/javascript">
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
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						htmlText += "<div class=\"menu\"\"><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><input type=\"button\" value=\"추가\" onclick=\"addMenu("+menu.menu_seq+")\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
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
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						htmlText += "<div class=\"menu\"\"><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><input type=\"button\" value=\"추가\" onclick=\"addMenu("+menu.menu_seq+")\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
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
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						htmlText += "<div class=\"menu\"\"><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><input type=\"button\" value=\"추가\" onclick=\"addMenu("+menu.menu_seq+")\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
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
	var file_rurl = menuInfo[4]; //이미지 파일 링크
	
	var newTr = document.createElement("tr"); //새로운 div 생성
    newTr.setAttribute("id", newTr_id);
	
	var mBody = document.getElementById("mBody");
	
	mBody.appendChild(newTr).innerHTML = "<td>"
											+"<img class=\"menuImg\" src=\"\" alt=\"\"/>"
										+"</td>"
										+"<td>"
											+"<input type=\"hidden\" name=\"menu_seq\" value=\"\"/>"
											+"<input type=\"text\" name=\"menu_name\" value=\"\"/>"
										+"</td>"
										+"<td>"
											+"<input type=\"button\" class=\"downBtn\" value=\"-\" onclick=\"minus(this)\">"
											+"<input type=\"text\" name=\"menu_cnt\" value=\"\"/>"
											+"<input type=\"button\" class=\"upBtn\" value=\"+\" onclick=\"plus(this)\">"
										+"</td>"
										+"<td>"
											+"<input type=\"text\" name=\"menu_price\" value=\"\"/>"
										+"</td>";
}
</script>
<body>
	<div id="container">
		<%@include file="./header.jsp"%>
		<div class="bodyFrame">
			<div class="bodyfixed">
				<div class="oneDepth">
				광고
				</div>
				<!-- div class=oneDepth -->
				<div class="twoDepth">
					<input id="mainMenu" name="menu_category" type="button" value="주메뉴" onclick="mainMenu()"/>
					<input id="sideMenu" name="menu_category" type="button" value="사이드메뉴" onclick="sideMenu()"/>
					<input id="drink" name="menu_category" type="button" value="음료" onclick="drinkMenu()"/>
					<div class="tab-content">
						<div id="menuList" >
							<c:forEach begin="0" end="${fn:length(menuList)}" items="${menuList}" var="menu" varStatus="vs">
								<div class="menu">
									<img class="menuImg" src="${menu.fileDto.file_rurl}" alt=""/>
									<input type="button" value="추가" onclick="addMenu('menu${vs.count},${menu.menu_seq},${menu.menu_name},${menu.menu_price},${menu.fileDto.file_rurl}')"/>
									<br>${menu.menu_name}&nbsp;&nbsp;${menu.menu_price}</div>
							</c:forEach>
						</div>
							<table>
								<thead>
									<tr>
										<td>메뉴 이미지</td>
										<td>메뉴명</td>
										<td>수량</td>
										<td>가격</td>
									</tr>
								</thead>
							</table>
						<div id="requestStatus">
							<table>
								<tbody id="mBody">
									<tr>
										<td>
											<img class="menuImg" src="" alt=""/>
										</td>
										<td>
											<input type="hidden" name="menu_seq" value=""/>
											<input type="text" name="menu_name" value=""/>
										</td>
										<td>
											<input type="button" class="downBtn" value="-" onclick="minus(this)">
											<input type="text" name="menu_cnt" value=""/>
											<input type="button" class="upBtn" value="+" onclick="plus(this)">
										</td>
										<td>
											<input type="text" name="menu_price" value=""/>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<input type="button" class="btn btn-outline-warning" value="주문 취소" onclick="location.reload()"/>	
						<input type="button" class="btn btn-outline-success" value="주문 완료" onclick="customRequest()"/>
					</div>
					<!-- div class=tab-content -->

				</div>
				<!-- div class twoDepth -->
			</div>
			<!-- div class=bodyfixed -->
		</div>
		<!-- div class=bodyFrame -->
	</div>
	<!-- div id=container -->
</body>
</html>