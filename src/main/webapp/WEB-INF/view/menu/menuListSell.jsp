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
<title>판매 메뉴</title>
<link rel="stylesheet" type="text/css" href="./css/menu.css">
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
		var formData = new FormData(document.getElementById("frm"));
		$.ajax({
			url : "./menuCancel.do",
			type : "post",
			async : true,
			data : formData,
			processData: false,    // 반드시 작성
		    contentType: false,    // 반드시 작성
			success : function(obj){
				if(obj == "성공"){
					swal({
						title: "메뉴 삭제 성공", 
						text: "업주 메뉴 삭제에 성공했습니다", 
					},
					function(){ 
						location.reload();
					});
				}
			},error : function(obj){
				if(obj == "실패"){
					swal({
						title: "메뉴 삭제 실패", 
						text: "관리자에게 문의해주세요", 
					},
					function(){ 
						
					});
				}
			}
		})
	}else{
		swal({
			title: "메뉴 삭제 실패", 
			text: "선택된 메뉴가 없습니다", 
		},
		function(){ 
			
		});
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
						htmlText += "<div class=\"menu\" ><label><input id=\"checkbox\" name=\"cancel_menu_seq\" type=\"checkbox\" value=\""+menu.menu_seq+"\"/><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</label></div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
// 			alert("관리자에게 문의해주세요"); 
			swal({
				title: "메뉴 삭제 실패", 
				text: "관리자에게 문의해주세요", 
			},
			function(){ 
				
			});
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
						htmlText += "<div class=\"menu\" ><label><input id=\"checkbox\" name=\"cancel_menu_seq\" type=\"checkbox\" value=\""+menu.menu_seq+"\"/><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</label></div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
// 			alert("관리자에게 문의해주세요");
			swal({
				title: "메뉴 삭제 실패", 
				text: "관리자에게 문의해주세요", 
			},
			function(){ 
				
			});
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
						htmlText += "<div class=\"menu\" ><label><input id=\"checkbox\" name=\"cancel_menu_seq\" type=\"checkbox\" value=\""+menu.menu_seq+"\"/><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</label></div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
// 			alert("관리자에게 문의해주세요");
			swal({
				title: "메뉴 삭제 실패", 
				text: "관리자에게 문의해주세요", 
			},
			function(){ 
				
			});
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
				<div class="oneDepth"><p>메　뉴</p></div>
				<div class="twoDepth">
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link  active" data-toggle="tab" href="#" style="border: 1px solid rgb(21,140,186);"><strong>판매 메뉴</strong></a></li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab" onclick="ownerAllMenuList()">전체 메뉴</a></li>
					</ul>
					<div class="menutabs">
						<input class="btn btn-outline-primary" id="mainMenu" name="menu_category" type="button" value="주메뉴" onclick="mainMenu()" />
						<input class="btn btn-outline-primary" id="sideMenu" name="menu_category" type="button" value="사이드메뉴" onclick="sideMenu()" /> 
						<input class="btn btn-outline-primary" id="drink" name="menu_category" type="button" value="음료" onclick="drinkMenu()" />
					</div>
					<div id="insert">
						<label><input style="vertical-align:-4px;" type="checkbox" onclick="checkAllDel(this.checked)"/><small>전체선택</small></label>
						<input class="btn btn-outline-warning" id="cancelMenu" type="button" value="판매 메뉴 삭제" onclick="cancelMenu()" />
					</div>
					<form id="frm" action="#" method="post" >
						<div id="menuList">
							<c:forEach begin="0" end="${fn:length(menuList)}" items="${menuList}" var="menu" varStatus="vs">
								<div class="menu">
								<label>
									<input id="checkbox" name="cancel_menu_seq" type="checkbox" value="${menu.menu_seq}" />
									<img class="menuImg" src="${menu.fileDto.file_rurl}" alt="" /><br>
									${menu.menu_name}&nbsp;&nbsp;${menu.menu_price}
								</label>
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