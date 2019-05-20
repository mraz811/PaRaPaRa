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
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript">
function modiMenu(){
	var menu_seq = document.getElementById("menu_seq").value;
	var menu_name = document.getElementById("menu_name").value;
	var menu_price = document.getElementById("menu_price").value;
	$.ajax({
		url : "./menuModi.do",
		type : "post",
		async : true,
		data : {"menu_seq":menu_seq,"menu_name":menu_name,"menu_price":menu_price},
		dataType : "json",
		success : function(){
			alert("수정이 완료되었습니다.");
		},error : function(){
			alert("수정에 실패하였습니다."); //성공햇는데 error로 넘어옴 ㅡㅡ;
		}
	})
}
</script>
<body>

	<div id="top">
		메뉴수정
	</div>
	<div id="image">
		<img id="menuImg" alt="메뉴사진" src="./masolimg/img.png">
	</div>
	<div id="selectAndFile">
		<p>${menuDto.menu_category}</p><input type="file" value=""/>
	</div>
	<div id="menu">
		<input id="menu_name" style="width: 100px;" type="text" name="menu_name" required="required" value="${menuDto.menu_name}"/>
		<input id="menu_price" style="width: 100px;" name="menu_price" type="text" required="required" value="${menuDto.menu_price}"/>
		<input id="menu_seq" type="hidden" name="menu_seq" value="${menuDto.menu_seq}"/>
	</div>
	<div id="confirm">
		<input type="button" value="수정완료" onclick="modiMenu()"/><input type="button" value="취소" onclick="window.close()"/>
	</div>

</body>
</html>