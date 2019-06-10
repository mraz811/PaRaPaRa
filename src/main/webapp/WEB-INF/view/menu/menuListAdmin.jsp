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
<link rel="stylesheet" type="text/css" href="./css/menu.css">
</head>
<script type="text/javascript">
function regiMenu(){
	window.open('./menuRegiForm.do', '메뉴 등록', 'width=700, height=470, scrollbars=no, left=200px, top=150px;');
}
function menuModiForm(menu_seq){
	window.open('./modifyMenuForm.do?menu_seq='+menu_seq,'메뉴 수정','width=700, height=470, scrollbars=no, left=200px, top=150px;');
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
function reSell(menu_seq){
	swal({
		  title: "메뉴를 재판매 하시겠습니까?",
		  type: "warning",
		  showCancelButton: true,
		  confirmButtonClass: "btn-danger",
		  cancelButtonClass: "btn-warning",
		  confirmButtonText: "취소",
		  cancelButtonText: "재판매",
		  closeOnConfirm: true,
		  closeOnCancel: false
		},
		function(isConfirm) {
		  if (!isConfirm) {
		    location.href = "./reSellMenu.do?menu_seq="+menu_seq;
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
							htmlText += "<div class=\"sell\" align='center'>"+
							"<img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/>"+
							"<br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"<br>"+
							"<input class='btn btn-outline-success' id=\"modiMenu\" type=\"button\" value=\"수정\" onclick=\"menuModiForm("+menu.menu_seq+")\"/>"+
							" <input class='btn btn-outline-warning' id=\"delMenu\" type=\"button\" value=\"삭제\" onclick=\"menuDel("+menu.menu_seq+")\"/>"+
							"</div>";
						}else{
							htmlText += "<div class='notSell'>"+
							"<img class='menuImg' src="+menu.file_rurl+" alt=''/>"+
							"<br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"<br>"+
							"<small>판매 중지 </small>"+
							"<input style='padding-left: 9px;' type='button' id='resellMenu' class='btn btn-outline-primary' value='재판매' onclick=\"reSell("+menu.menu_seq+")\">"+
						"</div>";
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
							htmlText += "<div class=\"sell\" align='center'>"+
							"<img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/>"+
							"<br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"<br>"+
							"<input class='btn btn-outline-success' id=\"modiMenu\" type=\"button\" value=\"수정\" onclick=\"menuModiForm("+menu.menu_seq+")\"/>"+
							" <input class='btn btn-outline-warning' id=\"delMenu\" type=\"button\" value=\"삭제\" onclick=\"menuDel("+menu.menu_seq+")\"/>"+
							"</div>";
						}else{
							htmlText += "<div class='notSell'>"+
							"<img class='menuImg' src="+menu.file_rurl+" alt=''/>"+
							"<br><small>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</small><br>"+
							"<small>판매 중지 </small>"+
							"<input style='padding-left: 9px;' type='button' id='resellMenu' class='btn btn-outline-primary' value='재판매' onclick=\"reSell("+menu.menu_seq+")\">"+
						"</div>";
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
							htmlText += "<div class='sell' align='center'>"+
							"<img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/>"+
							"<br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"<br>"+
							"<input class='btn btn-outline-success' id=\"modiMenu\" type=\"button\" value=\"수정\" onclick=\"menuModiForm("+menu.menu_seq+")\"/>"+
							" <input class='btn btn-outline-warning' id=\"delMenu\" type=\"button\" value=\"삭제\" onclick=\"menuDel("+menu.menu_seq+")\"/>"+
							"</div>";
						}else{
							htmlText += "<div class='notSell'>"+
								"<img class='menuImg' src="+menu.file_rurl+" alt=''/>"+
								"<br><small>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</small><br>"+
								"<small>판매 중지 </small>"+
								"<input style='padding-left: 9px;' type='button' id='resellMenu' class='btn btn-outline-primary' value='재판매' onclick=\"reSell("+menu.menu_seq+")\">"+
							"</div>";
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
		<p>메　뉴</p>
		</div>
		<div class="twoDepth">
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link active" data-toggle="tab" href="#home" style="border: 1px solid rgb(21,140,186);"><strong>전체메뉴</strong></a>
  				</li>
			</ul>
			<div class="menutabs">
			<input class="btn btn-outline-primary" id="mainMenu" name="menu_category" type="button" value="주메뉴" onclick="mainMenu()"/>
			<input class="btn btn-outline-primary" id="sideMenu" name="menu_category" type="button" value="사이드메뉴" onclick="sideMenu()"/>
			<input class="btn btn-outline-primary" id="drink" name="menu_category" type="button" value="음료" onclick="drinkMenu()"/>
			</div>
		<div id="insert">
			<input class="btn btn-outline-success" id="regiMenu" type="button" value="메뉴 등록" onclick="regiMenu()"/>
		</div>
		<div id="menuList" >
			<c:forEach begin="0" end="${fn:length(menuList)}" items="${menuList}" var="menu" varStatus="vs">
				<c:choose>
					<c:when test="${menu.menu_delflag eq 'N'}">
						<div class="sell" align="center">
							<img class="menuImg" src="${menu.fileDto.file_rurl}" alt=""/>
							<br>${menu.menu_name}&nbsp;&nbsp;${menu.menu_price}<br>
							<input class="btn btn-outline-success" id="modiMenu" type="button" value="수정" onclick="menuModiForm(${vs.current.menu_seq})"/>
							<input class="btn btn-outline-warning" id="delMenu" type="button" value="삭제" onclick="menuDel(${vs.current.menu_seq})"/>
						</div>
					</c:when>
					<c:otherwise>
						<div class="notSell">
						<img class="menuImg" src="${menu.fileDto.file_rurl}" alt=""/>
						<br><small>${menu.menu_name}&nbsp;&nbsp;${menu.menu_price}</small><br>
						<small>판매 중지 </small>
						<input style="padding-left: 9px;" type="button" id="resellMenu" class="btn btn-outline-primary" value="재판매" onclick="reSell(${menu.menu_seq})">
						</div>
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