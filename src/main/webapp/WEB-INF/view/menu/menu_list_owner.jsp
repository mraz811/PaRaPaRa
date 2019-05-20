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
		height: 160px;
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
		width: 100px;
		height: 100px;
	}
	#insert{
		float: right;
	}
</style>
</head>
<script type="text/javascript">
function checkAllDel(bool){
	var checks = document.getElementsByName("checkVal");
//	alert(checks.length);
	for (var i = 0; i < checks.length; i++) {
		checks[i].checked = bool;
	}
}
function choiceMenu(){
//	alert("작동");
	var checks = document.getElementsByName("menu_seq");
	var c = 0;
	for (var i = 0; i < checks.length; i++) {
		if(checks[i].checked){
			c++;
		}
	}
	if(c > 0){
		var frm = document.getElementById("frm");
		frm.action = "./menuChoice.do";
		frm.method = "post";
		frm.submit();
		return true;
	}else{
		alert("선택된 메뉴가 없습니다.");
		return false;
	}
}

</script>
<body>
<div id="container">
	<form action="./OselAllMenuList.do" method="post">
		<div id="category">
			<input id="mainMenu" name="menu_category" type="submit" value="주메뉴"/>
			<input id="sideMenu" name="menu_category" type="submit" value="사이드메뉴"/>
			<input id="drink" name="menu_category" type="submit" value="음료"/>
		</div>
	</form>
		<div id="insert">
				<input type="checkbox" onclick="checkAllDel(this.checked)" />전체선택
				<input id="choiceMenu" type="button" value="판매 메뉴 등록" onclick="choiceMenu()"/>
		</div>
	<form id="frm" action="./menuChoice.do" method="post" onsubmit="return choiceMenu()">
		<div id="menuList">
			<c:forEach begin="0" end="${fn:length(menuList)}" items="${menuList}" var="menu" varStatus="vs">
					
					<div class="menu"><input name="menu_seq" type="checkbox" value="${menu.menu_seq}"/><img class="menuImg" src="./masolimg/img.png" alt=""/><br>${menu.menu_name}&nbsp;&nbsp;${menu.menu_price}</div>
					<c:if test="${vs.count mod 4 eq 0}">
						<br>
					</c:if>
			</c:forEach>
		</div>
	</form>
	<div id="paging">
	
	</div>
</div>
</body>
</html>