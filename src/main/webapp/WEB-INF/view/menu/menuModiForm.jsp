<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메뉴 수정</title>
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<style type="text/css">
.writeform{
	width: 300px;
	font-size: 30px; 
	background-color: RGB(21,140,186); 
	color:white; 
	font-weight: bold; 
	padding: 0px 10px; 
	text-align: center;
	border-radius: 0.2em;
}
#container{
	width: 310px;
	margin: 10px auto;
}
#image{
	width: 300px;
	height: 150px;
	text-align: center;
}
#menu{
	width: 300px;
	position: relative;
}

#confirm{
	margin: 20px 0px;
	padding-left: 0px;
	width: 300px;
	height: 50px;
}
#menuImg{
	width: 100px;
	height: 100px;
}

</style>
</head>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript">
var fDto = null;
function regiTempFile(){ //파일 임시 저장 후 미리보기
	var form = new FormData(document.getElementById("tempFrm"));
	form.append("file_seq", document.getElementById("file_seq").value);

	$.ajax({
		url : "./modifyMenuTempFile.do",
		type : "post",
		data : form,
		dataType : "json",
		processData: false, 
		contentType: false,
		cache : false,
		success : function(obj){
			fDto = obj.fDtoSel;
			document.getElementById("image").innerHTML = "<img id=\"menuImg\" alt=\"메뉴사진\" src=\""+obj.fDtoSel.file_rurl+"\">";
		},error : function(obj){
			alert("안돼안돼");
		}
	});
}

function modiMenu(){
	var menu_seq = document.getElementById("menu_seq").value;
	var menu_price = document.getElementById("menu_price").value;
	var menu_name = document.getElementById("menu_name").value;
	
	var file_seq = fDto.file_seq;
	var file_tname = fDto.file_tname;
	var file_rname = fDto.file_rname;
	var file_size = fDto.file_size;
	var file_aurl = fDto.file_aurl;
	var file_rurl = fDto.file_rurl;
	
	var isc = confirm("수정하시겠습니까?");
	if(isc){
		$.ajax({
			url : "./menuModi.do",
			type : "post",
			async : true,
			data : {"file_seq":file_seq,"file_tname":file_tname,"file_rname":file_rname,"file_size":file_size,"file_aurl":file_aurl,"file_rurl":file_rurl,"menu_seq":menu_seq,"menu_name":menu_name,"menu_price":menu_price},
			dataType : "json",
			success : function(obj){
				alert("메뉴명이 "+"\""+obj.detailMenu.menu_name+"\""+"(으)로 수정되었습니다");
				window.opener.location.reload();
				window.close();
			},error : function(obj){
				alert("관리자에게 문의해주세요.");
			}
		})
	}
	
}
</script>
<body>
<div id="container">
		<p class="writeform">메뉴 수정</p>
	<div id="image">
		<img id="menuImg" alt="메뉴사진" src="${menuDto.fileDto.file_rurl}">
	</div>
	
	<form id="tempFrm" action="#" method="post" enctype="multipart/form-data">
	<div class="form-group">
		<input type="file" class="form-control-file" id="selectedFile" name="file1" onchange="regiTempFile()"/>
	</div>
	</form>	
	
	<input type="hidden" id="file_seq" value="${menuDto.fileDto.file_seq}"/>
	<div id="selectAndFile" align="left">
		<span>카테고리 : ${menuDto.menu_category}</span>
	</div>
	<div id="menu" class="form-group row" align="left" style="margin:10px 0px;">
		<input class="form-control" id="menu_name" style="width: 200px;" type="text" name="menu_name" required="required" value="${menuDto.menu_name}"/>
		<input class="form-control" id="menu_price" style="width: 95px;" name="menu_price" type="text" required="required" value="${menuDto.menu_price}"/>
		<input id="menu_seq" type="hidden" name="menu_seq" value="${menuDto.menu_seq}"/>
	</div>
	<div id="confirm" align="left">
		<input style="width: 146px; margin-left: 0px;" class="btn btn-outline-success" type="button" value="수정 완료" onclick="modiMenu()"/>
		<input style="width: 146px;" class="btn btn-outline-warning" type="button" value="취소" onclick="window.close()"/>
	</div>
</div>
	
</body>
</html>