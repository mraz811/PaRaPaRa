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
<title>담당자:전체메뉴</title>
<style type="text/css">
	.sell{
		width: 200px;
		height: 130px;
		float: left;
		margin-right: 30px;
		margin-bottom: 20px;
		text-align: center;
	}
	.notSell{
		width: 200px;
		height: 130px;
		float: left;
		margin-right: 30px;
		margin-bottom: 20px;
		text-align: center;
	}
	.notSell > img{
		opacity: 0.5;
	}
	#menuList{
		width: 940px;
		height : 350px;
		margin-top : 40px;
		margin-left: 65px;
		margin-right: 35px;
		overflow-x:hidden;
		overflow-y:scroll;
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
</style>
</head>
<script type="text/javascript">
function regiMenu(){
	window.open('./menuRegiForm.do', 'window팝업', 'width=500, height=370, scrollbars=no');
}
function menuModiForm(menu_seq){
	window.open('./modifyMenuForm.do?menu_seq='+menu_seq,'window팝업','width=500, height=370, scrollbars=no');
}
function menuDel(menu_seq){
	swal({
		  title: "메뉴를 삭제하시겠습니까?",
		  type: "warning",
		  showCancelButton: true,
		  confirmButtonClass: "btn-danger",
		  cancelButtonClass: "btn-warning",
		  confirmButtonText: "취소",
		  cancelButtonText: "삭제",
		  closeOnConfirm: true,
		  closeOnCancel: false
		},
		function(isConfirm) {
		  if (!isConfirm) {
		    location.href = "./delMenu.do?menu_seq="+menu_seq;
		  } 
		});
}

	function mainMenu(){
	var menu_category = "주메뉴";
	var menuList = document.getElementById("menuList");
	$.ajax({
		url : "./AselAllMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						if(menu.menu_delflag == 'N'){
							htmlText += "<div class=\"sell\"><input id=\"modiMenu\" type=\"button\" value=\"수정\" onclick=\"menuModiForm("+menu.menu_seq+")\"/><img class=\"menuImg\" src=\"./masolimg/img.png\" alt=\"\"/><input id=\"delMenu\" type=\"button\" value=\"삭제\" onclick=\"menuDel("+menu.menu_seq+")\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
						}else{
							htmlText += "<div class=\"notSell\">판매 중지<img class=\"menuImg\" src=\"./masolimg/img.png\" alt=\"\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
						}
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
		url : "./AselAllMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						if(menu.menu_delflag == 'N'){
							htmlText += "<div class=\"sell\"><input id=\"modiMenu\" type=\"button\" value=\"수정\" onclick=\"menuModiForm("+menu.menu_seq+")\"/><img class=\"menuImg\" src=\"./masolimg/img.png\" alt=\"\"/><input id=\"delMenu\" type=\"button\" value=\"삭제\" onclick=\"menuDel("+menu.menu_seq+")\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
						}else{
							htmlText += "<div class=\"notSell\">판매 중지<img class=\"menuImg\" src=\"./masolimg/img.png\" alt=\"\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
						}
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
		url : "./AselAllMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						if(menu.menu_delflag == 'N'){
							htmlText += "<div class=\"sell\"><input id=\"modiMenu\" type=\"button\" value=\"수정\" onclick=\"menuModiForm("+menu.menu_seq+")\"/><img class=\"menuImg\" src=\"./masolimg/img.png\" alt=\"\"/><input id=\"delMenu\" type=\"button\" value=\"삭제\" onclick=\"menuDel("+menu.menu_seq+")\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
						}else{
							htmlText += "<div class=\"notSell\"><span>판매 중지</span><img class=\"menuImg\" src=\"./masolimg/img.png\" alt=\"\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
						}
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
			alert("관리자에게 문의해주세요"); 
		}
	})
}
</script>
<body>
<div id="container">
<%@include file="../header.jsp" %>
	<div class="bodyFrame">
	<div class="bodyfixed">
		<div class="oneDepth">
		메뉴
		</div>
		<div class="twoDepth">
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#home">전체메뉴</a>
  				</li>
			</ul>
			<input id="mainMenu" name="menu_category" type="button" value="주메뉴" onclick="mainMenu()"/>
			<input id="sideMenu" name="menu_category" type="button" value="사이드메뉴" onclick="sideMenu()"/>
			<input id="drink" name="menu_category" type="button" value="음료" onclick="drinkMenu()"/>
	<div id="insert">
			<input id="regiMenu" type="button" value="메뉴 등록" onclick="regiMenu()"/>
	</div>
		<div id="menuList" >
			<c:forEach begin="0" end="${fn:length(menuList)}" items="${menuList}" var="menu" varStatus="vs">
				<c:choose>
					<c:when test="${menu.menu_delflag eq 'N'}">
						<div class="sell"><input id="modiMenu" type="button" value="수정" onclick="menuModiForm(${vs.current.menu_seq})"/><img class="menuImg" src="./masolimg/img.png" alt=""/><input id="delMenu" type="button" value="삭제" onclick="menuDel(${vs.current.menu_seq})"/><br>${menu.menu_name}&nbsp;&nbsp;${menu.menu_price}</div>
					</c:when>
					<c:otherwise>
						<div class="notSell">판매 중지<img class="menuImg" src="./masolimg/img.png" alt=""/><br>${menu.menu_name}&nbsp;&nbsp;${menu.menu_price}</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
	<div id="paging">
	
	</div>
	</div>
	</div>
	</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>