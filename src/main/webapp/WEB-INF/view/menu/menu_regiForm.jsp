<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#top{
		width: 300px;
		height: 80px;
	}
	#image{
		width: 300px;
		height: 120px;
	}
	#selectAndFile{
		width: 300px;
		height: 50px;
	}
	#menu{
		width: 300px;
		height: 70px;
	}
	#confirm{
		width: 300px;
		height: 50px;
	}
	#menuImg{
		width: 100px;
		height: 100px;
	}
</style>
</head>
<body>

	<div id="top">
		메뉴등록
	</div>
	<div id="image">
		<img id="menuImg" alt="메뉴사진" src="./masolimg/img.png">
	</div>
	<div id="selectAndFile">
	<select>
		<option>주메뉴</option>
		<option>사이드메뉴</option>
		<option>음료</option>
	</select>
	</div>
	<form action="./regiNewMenu.do" method="post">
		<div id="menu">
			<input style="width: 100px;" type="text" name="menu_name" placeholder="메뉴명"/><input style="width: 100px;" name="menu_price" type="text" placeholder="가격"/>
		</div>
		<div id="confirm">
			<input type="submit" value="등록완료"/><input type="button" value="취소"/>
		</div>
	</form>

</body>
</html>