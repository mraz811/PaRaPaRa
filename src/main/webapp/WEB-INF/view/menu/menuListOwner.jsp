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
<title>전체 메뉴</title>
<link rel="stylesheet" type="text/css" href="./css/menu.css">
</head>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript">
function checkAllDel(bool){
	var checks = document.getElementsByName("menu_seq");
//	alert(checks.length);
	for (var i = 0; i < checks.length; i++) {
		checks[i].checked = bool;
	}
}
function choiceMenu(){
	var checks = document.getElementsByName("menu_seq");
	var checkedBox = "";
	for (var i = 0; i < checks.length; i++) {
		if(checks[i].checked){
			checkedBox += checks[i].value+",";
		}
	}
	var menu_seq = checkedBox;
	$.ajax({
		url : "./menuChoice.do",
		type : "post",
		async : true,
		data : {"menu_seqs":menu_seq},
		success :function(obj){
			if(obj == "success"){
// 				alert("업주 메뉴 선택에 성공했습니다.");
				swal({
					title: "메뉴 선택 성공", 
					text: "업주 메뉴 선택에 성공했습니다", 
				},
				function(){ 
					location.reload();
				});
			}
		},error : function(obj){
			if(obj == "fail"){
// 				alert("업주 메뉴 선택에 실패했습니다. 관리자에게 문의해주세요.");
				swal({
					title: "메뉴 선택 실패", 
					text: "업주 메뉴 선택에 실패했습니다", 
				},
				function(){ 
					location.reload();
				});
			}
		}
	});
	
}
function mainMenu(){
	var menu_category = "주메뉴";
	var menuList = document.getElementById("menuList");
	$.ajax({
		url : "./OselAllMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						htmlText += "<div class=\"menu\"><label><input id='checkbox' name=\"menu_seq\" type=\"checkbox\" value=\""+menu.menu_seq+"\"/><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</label></div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
// 			alert("관리자에게 문의해주세요"); 
		}
	})
}
function sideMenu(){
	var menu_category = "사이드메뉴";
	$.ajax({
		url : "./OselAllMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						htmlText += "<div class=\"menu\"><label><input id='checkbox' name=\"menu_seq\" type=\"checkbox\" value=\""+menu.menu_seq+"\"/><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</label></div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
// 			alert("관리자에게 문의해주세요");
			swal("메뉴 선택 실패", "업주 메뉴 선택에 실패했습니다","error");
		}
	})
}
function drinkMenu(){
	var menu_category = "음료";
	$.ajax({
		url : "./OselAllMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						htmlText += "<div class=\"menu\"><label><input id='checkbox' name=\"menu_seq\" type=\"checkbox\" value=\""+menu.menu_seq+"\"/><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</label></div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
// 			alert("관리자에게 문의해주세요");
			swal("메뉴 선택 실패", "업주 메뉴 선택에 실패했습니다","error");
		}
	})
}
function ownerMenuList(){
	location.href="./ownerMenuList.do";
}

</script>
<body>
	<div id="container">
		<%@include file="../header.jsp"%>
		<div class="bodyFrame">
			<div class="bodyfixed">
				<div class="oneDepth"><p>메　뉴</p></div>
				<div class="twoDepth">
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link" data-toggle="tab" onclick="ownerMenuList()">판매 메뉴</a></li>
						<li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#" style="border: 1px solid rgb(21,140,186);"><strong>전체 메뉴</strong></a></li>
					</ul>
					<div class="menutabs">
						<input class="btn btn-outline-primary" id="mainMenu" name="menu_category" type="button" value="주메뉴" onclick="mainMenu()" /> 
						<input class="btn btn-outline-primary" id="sideMenu" name="menu_category" type="button" value="사이드메뉴" onclick="sideMenu()" />
						<input class="btn btn-outline-primary" id="drink" name="menu_category" type="button" value="음료" onclick="drinkMenu()" />
					</div>
					<div id="insert">
						<label><input style="vertical-align:-4px;" type="checkbox" onclick="checkAllDel(this.checked)" /><small>전체선택</small></label>
						<input id="choiceMenu" type="button" class="btn btn-outline-success" value="판매 메뉴 등록" onclick="choiceMenu()" />
					</div>
					<div id="menuList">
						<c:forEach begin="0" end="${fn:length(menuList)}" items="${menuList}" var="menu" varStatus="vs">
							<div class="menu">
							<label>
								<input id="checkbox" name="menu_seq" type="checkbox" value="${menu.menu_seq}" />
								<img class="menuImg" src="${menu.fileDto.file_rurl}" alt="" /><br>
								${menu.menu_name}&nbsp;&nbsp;${menu.menu_price}
							</label>
							</div>
							<c:if test="${vs.count mod 4 eq 0}">
								<br>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<%@include file="../footer.jsp"%>
	</div>
</body>
</html>