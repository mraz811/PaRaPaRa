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
	body{
		margin-left: 100px;
	}
	#top{
		width: 300px;
		height: 40px;
		text-align: center;
	}
	#image{
		width: 300px;
		height: 150px;
		text-align: center;
	}
	#selectAndFile{
		width: 300px;
		height: 40px;
	}
	#menu{
		width: 300px;
		height: 50px;
	}
	#confirm{
		width: 300px;
		height: 50px;
	}
	#menuImg{
		width: 100px;
		height: 100px;
	}
	hr{
		width: 290px;
	}
</style>
</head>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript">
function modiMenu(){
	var menu_seq = document.getElementById("menu_seq").value;
	var menu_name = document.getElementById("menu_name").value;
	var menu_price = document.getElementById("menu_price").value;
	var isc = confirm("수정하시겠습니까?");
	if(isc){
		$.ajax({
			url : "./menuModi.do",
			type : "post",
			async : true,
			data : {"menu_seq":menu_seq,"menu_name":menu_name,"menu_price":menu_price},
			dataType : "json",
			success : function(obj){
				alert("\""+menu_name+"\""+"에서 "+"\""+obj.detailMenu.menu_name+"\""+"(으)로 수정되었습니다");
				window.close();
			},error : function(obj){
				alert("관리자에게 문의해주세요.");
			}
		})
	}
	
}
</script>
<body>
	<div id="top">
		메뉴수정
	</div>
	<div id="image">
		<img id="menuImg" alt="메뉴사진" src="./masolimg/img.png">
		<input type="file" value=""/><br>
	</div>
	<div id="selectAndFile">
		<span>카테고리 : ${menuDto.menu_category}</span>
	</div>
	<div id="menu">
		<input id="menu_name" style="width: 190px; height: 40px;" type="text" name="menu_name" required="required" value="${menuDto.menu_name}"/>
		<input id="menu_price" style="width: 90px; height: 40px;" name="menu_price" type="text" required="required" value="${menuDto.menu_price}"/>
		<input id="menu_seq" type="hidden" name="menu_seq" value="${menuDto.menu_seq}"/>
	</div>
	<div id="confirm">
		<input style="width: 150px; height: 30px" type="button" value="수정완료" onclick="modiMenu()"/><input style="width: 150px; height: 30px" type="button" value="취소" onclick="window.close()"/>
	</div>
</body>
</html>