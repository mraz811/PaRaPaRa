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
function regiMenu(){
	window.open('./menuList.do', 'window팝업', 'width=310, height=380, scrollbars=no');
// }

</script>
<body>
<div id="container">
	<form action="./selAllMenuList.do" method="post">
		<div id="category">
			<input id="mainMenu" name="menu_category" type="submit" value="주메뉴"/>
			<input id="sideMenu" name="menu_category" type="submit" value="사이드메뉴"/>
			<input id="drink" name="menu_category" type="submit" value="음료"/>
		</div>
	</form>
	<div id="insert">
			<input id="regiMenu" type="button" value="메뉴 등록" onclick="regiMenu()"/>
	</div>
	<div id="menuList">
		<c:forEach begin="0" end="${fn:length(menuList)}" items="${menuList}" var="menu" varStatus="vs">
				<div class="menu"><img class="menuImg" src="./masolimg/img.png" alt=""/><br>${menu.menu_name}&nbsp;&nbsp;${menu.menu_price}</div>
				<c:if test="${vs.count mod 4 eq 0}">
					<br>
				</c:if>
		</c:forEach>
	</div>
	<div id="paging">
	
	</div>
</div>
</body>
</html>