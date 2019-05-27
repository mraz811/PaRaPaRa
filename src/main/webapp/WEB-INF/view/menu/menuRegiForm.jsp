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
</style>
</head>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript">
	function regiMenu(){
		var menu_name = document.getElementById("menu_name").value;
		var menu_price = document.getElementById("menu_price").value;
		var select = document.getElementsByTagName("select")[0];
		var index = select.selectedIndex;
		var menu_category = select.options[index].value;
		$.ajax({
			url : "./regiNewMenu.do",
			type : "post",
			async : true,
			data : {"menu_name":menu_name,"menu_price":menu_price,"menu_category":menu_category},
			success : function(obj){
				if(obj == "성공"){
					alert("\""+menu_name+"\""+"이(가)"+"등록 되었습니다.");
					window.close();
				}
			},error : function(obj){
				alert("관리자에게 문의해주세요"); 
			}
		})
	}
</script>
<body>
	<div id="top">
		메뉴등록
	</div>
	<div id="image">
		<img id="menuImg" alt="메뉴사진" src="./masolimg/img.png">
		<input type="file" value=""/>
	</div>
	<div id="selectAndFile">
		<select id="select">
			<option value="">---카테고리 선택---</option>
			<option value="주메뉴">주메뉴</option>
			<option value="사이드메뉴">사이드메뉴</option>
			<option value="음료">음료</option>
		</select>
	</div>
	<div id="menu">
		<input id="menu_name" style="width: 190px; height: 40px;" type="text" name="menu_name" placeholder="메뉴명" required="required"/>
		<input id="menu_price" style="width: 90px; height: 40px;" name="menu_price" type="text" placeholder="가격" required="required"/>
	</div>
	<div id="confirm">
		<input style="width: 150px; height: 30px" type="button" value="등록완료" onclick="regiMenu()"/><input style="width: 150px; height: 30px" type="button" value="취소" onclick="window.close()"/>
	</div>
</body>
</html>