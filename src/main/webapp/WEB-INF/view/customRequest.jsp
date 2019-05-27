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
		width: 720px;
		height : 390px;
		margin-top : 40px;
		overflow: scroll;
		float: left;
	}
	#requestStatus{
		width: 320px;
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
						htmlText += "<div class=\"menu\"\"><img class=\"menuImg\" src=\"./masolimg/img.png\" alt=\"\"/><input type=\"button\" value=\"추가\" onclick=\"addMenu("+menu.menu_seq+")\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
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
						htmlText += "<div class=\"menu\"\"><img class=\"menuImg\" src=\"./masolimg/img.png\" alt=\"\"/><input type=\"button\" value=\"추가\" onclick=\"addMenu("+menu.menu_seq+")\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
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
						htmlText += "<div class=\"menu\"\"><img class=\"menuImg\" src=\"./masolimg/img.png\" alt=\"\"/><input type=\"button\" value=\"추가\" onclick=\"addMenu("+menu.menu_seq+")\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
			alert(obj); 
		}
	})
}
function addMenu(menu_seq){
	$.ajax({
		url : "./addMenu.do",
		type : "post",
		async : true,
		data : {"menu_seq":menu_seq},
		success : function(obj){
			var htmlTable = "";
			$.each(obj,function(key,value){
				if(key=="menu_name"){
					htmlTable += "<tr>"
									+"<td style=\"width: 100px\">후라이드치킨</td>"
									+"<td style=\"width: 50px\">10</td>"
									+"<td style=\"width: 140px;\">180000원</td>"
								+"</tr>";
				}
			});
			$(".table > tbody").html(htmlTable);
		},error : function(obj){
			alert(obj); 
		}
	})
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
								<div class="menu"><img class="menuImg" src="./masolimg/img.png" alt=""/><input type="button" value="추가" onclick="addMenu(${menu.menu_seq})"/><br>${menu.menu_name}&nbsp;&nbsp;${menu.menu_price}</div>
							</c:forEach>
						</div>
						<div id="requestStatus">
							<table class="table">
								<thead>
									<tr>
										<td style="width: 150px">메뉴</td>
										<td style="width: 60px">수량</td>
										<td style="width: 100px;">가격</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td style="width: 100px">후라이드치킨</td>
										<td style="width: 50px">10</td>
										<td style="width: 140px;">180000원</td>
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