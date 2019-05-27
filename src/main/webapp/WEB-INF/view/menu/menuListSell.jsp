<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
		width: 940px;
		height : 350px;
		margin-top : 40px;
		margin-left: 65px;
		margin-right: 35px;
		overflow: scroll;
	}
	#container{
		width: 1020px;
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
	#choiceMenu{
		width: 100px;
		height: 40px;
	}
	#regiMenu{
		width: 100px;
		height: 40px;
	}
	.menuImg{
		width: 110px;
		height: 110px;
	}
	#insert{
		float: right;
	}
	#modiMenu{
		float: left;
	}
	#delMenu{
		float: right;
	}
	#checkbox{
		float: left;
	}
	#cancelMenu{
		width: 120px;
		height: 40px;
	}
</style>
</head>
<script type="text/javascript">
function checkAllDel(bool){
	var checks = document.getElementsByName("cancel_menu_seq");
//	alert(checks.length);
	for (var i = 0; i < checks.length; i++) {
		checks[i].checked = bool;
	}
}
function cancelMenu(){
//	alert("작동");
	var checks = document.getElementsByName("cancel_menu_seq");
	var c = 0;
	for (var i = 0; i < checks.length; i++) {
		if(checks[i].checked){
			c++;
		}
	}
	if(c > 0){
		var frm = document.getElementById("frm");
		frm.action = "./menuCancel.do";
		frm.method = "post";
		frm.submit();
		return true;
	}else{
		alert("선택된 메뉴가 없습니다.");
		return false;
	}
}
function mainMenu(){
	var menu_category = "주메뉴";
	var menuList = document.getElementById("menuList");
	$.ajax({
		url : "./choiceMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						htmlText += "<div class=\"menu\" ><input id=\"checkbox\" name=\"cancel_menu_seq\" type=\"checkbox\" value=\""+menu.menu_seq+"\"/><img class=\"menuImg\" src=\"./masolimg/img.png\" alt=\"\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
			alert("관리자에게 문의해주세요"); 
		}
	})
}
function sideMenu(){
	var menu_category = "사이드메뉴";
	$.ajax({
		url : "./choiceMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						htmlText += "<div class=\"menu\" ><input id=\"checkbox\" name=\"cancel_menu_seq\" type=\"checkbox\" value=\""+menu.menu_seq+"\"/><img class=\"menuImg\" src=\"./masolimg/img.png\" alt=\"\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
			alert("관리자에게 문의해주세요"); 
		}
	})
}
function drinkMenu(){
	var menu_category = "음료";
	$.ajax({
		url : "./choiceMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						htmlText += "<div class=\"menu\" ><input id=\"checkbox\" name=\"cancel_menu_seq\" type=\"checkbox\" value=\""+menu.menu_seq+"\"/><img class=\"menuImg\" src=\"./masolimg/img.png\" alt=\"\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
			alert("관리자에게 문의해주세요"); 
		}
	})
}
function ownerAllMenuList(){
	location.href="./ownerAllMenuList.do";
}

</script>
<body>
	<div id="container">
		<%@include file="../header.jsp"%>
		<div class="bodyFrame">
			<div class="bodyfixed">
				<div class="oneDepth">메뉴</div>
				<div class="twoDepth">
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#">판매 메뉴</a></li>
						<li class="nav-item"><a class="nav-link active" data-toggle="tab" onclick="ownerAllMenuList()">전체 메뉴</a></li>
					</ul>
						<input id="mainMenu" name="menu_category" type="button" value="주메뉴" onclick="mainMenu()" />
						<input id="sideMenu" name="menu_category" type="button" value="사이드메뉴" onclick="sideMenu()" /> 
						<input id="drink" name="menu_category" type="button" value="음료" onclick="drinkMenu()" />
					<div id="insert">
						<input type="checkbox" onclick="checkAllDel(this.checked)" />전체선택
						<input id="cancelMenu" type="button" value="판매 메뉴에서 삭제" onclick="cancelMenu()" />
					</div>
					<form id="frm" action="./menuCancel.do" method="post" onsubmit="return cancelMenu()">
						<div id="menuList">
							<c:forEach begin="0" end="${fn:length(menuList)}" items="${menuList}" var="menu" varStatus="vs">
								<div class="menu">
									<input id="checkbox" name="cancel_menu_seq" type="checkbox" value="${menu.menu_seq}" />
									<img class="menuImg" src="./masolimg/img.png" alt="" /><br>
									${menu.menu_name}&nbsp;&nbsp;${menu.menu_price}
								</div>
							</c:forEach>
						</div>
					</form>
				</div>
			</div>
		</div>
		<%@include file="../footer.jsp"%>
	</div>
</body>
</html>