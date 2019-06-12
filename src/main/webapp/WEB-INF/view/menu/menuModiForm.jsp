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
//임시 저장한 파일의 정보를 저장하는 변수
var fDto = null;
//파일 임시 저장 후 미리보기
function regiTempFile(targetObj){ 
	var form = new FormData(document.getElementById("tempFrm"));
	form.append("file_seq", document.getElementById("file_seq").value);
	
	//확장자를 확인해 이미지 파일만 업로드 가능하게함
	var ext = targetObj.value.split(".").pop().toLowerCase();
	if ($.inArray(ext, ["gif", "jpg", "jpeg", "png"]) == -1) {
	 	alert("이미지 파일(gif, jpg, jpeg, png)만 업로드 가능합니다.");
	 	
	 	//input에 저장된 파일 지워줌
	 	 $("#selectedFile").val("");
	 	
		return false;
	} 
	
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
			alert("관리자에게 문의해주세요.");
		}
	});
}

//메뉴 수정
function modiMenu(){
	var menu_seq = document.getElementById("menu_seq").value;
	var menu_price = document.getElementById("menu_price").value.trim();
	var menu_name = document.getElementById("menu_name").value.trim();
	
	//메뉴의 이미지 파일을 수정하였는지 변수를 통해 판단
	if(fDto == null){
		var file_seq = document.getElementById("file_seq").value;
		var file_tname = document.getElementById("file_tname").value;
		var file_rname = document.getElementById("file_rname").value;
		var file_size = document.getElementById("file_size").value;
		var file_aurl = document.getElementById("file_aurl").value;
		var file_rurl = document.getElementById("file_rurl").value;
	}else{
		var file_seq = fDto.file_seq;
		var file_tname = fDto.file_tname;
		var file_rname = fDto.file_rname;
		var file_size = fDto.file_size;
		var file_aurl = fDto.file_aurl;
		var file_rurl = fDto.file_rurl;
	}
	
	if(menu_name == ""){
		alert("메뉴명을 입력해주세요.");
		return false;
	}
	if(menu_price == ""){
		alert("메뉴가격을 입력해주세요.");
		return false;
	}
	
	var isc = confirm("수정하시겠습니까?");
	if(isc){
		$.ajax({
			url : "./menuModi.do",
			type : "post",
			async : true,
			data : {"file_seq":file_seq,"file_tname":file_tname,"file_rname":file_rname,"file_size":file_size,"file_aurl":file_aurl,"file_rurl":file_rurl,"menu_seq":menu_seq,"menu_name":menu_name,"menu_price":menu_price},
			dataType : "json",
			success : function(obj){
				alert("수정이 완료되었습니다.");
				window.opener.location.reload();
				window.close();
			},error : function(obj){
				alert("관리자에게 문의해주세요.");
			}
		})
	}
	
}
//메뉴 금액 유효값
function regiPrice(val){ 
	 // 키를 눌렀을 때 해당 key의 코드를 받아옴 
   var keyValue = event.keyCode;
	if(((keyValue >= 65) && (keyValue <= 90)) ||  ((keyValue >= 106) && (keyValue <= 111)) || ((keyValue >= 186) && (keyValue <= 222)) || keyValue==32 ){	// 문자 및 특수문자, 스페이스바를 입력했을 때
   	alert("메뉴 금액란에 숫자만 입력해주세요.");
   	document.getElementById("menu_price").value = "";
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
		<input type="file" class="form-control-file" id="selectedFile" name="file1" onchange="regiTempFile(this)"/>
	</div>
	</form>	
	
	<input type="hidden" id="file_seq" value="${menuDto.fileDto.file_seq}"/>
	<input type="hidden" id="file_tname" value="${menuDto.fileDto.file_tname}"/>
	<input type="hidden" id="file_rname" value="${menuDto.fileDto.file_rname}"/>
	<input type="hidden" id="file_size" value="${menuDto.fileDto.file_size}"/>
	<input type="hidden" id="file_aurl" value="${menuDto.fileDto.file_aurl}"/>
	<input type="hidden" id="file_rurl" value="${menuDto.fileDto.file_rurl}"/>
	
	<div id="selectAndFile" align="left">
		<span>카테고리 : ${menuDto.menu_category}</span>
	</div>
	<div id="menu" class="form-group row" align="left" style="margin:10px 0px;">
		<input class="form-control" id="menu_name" style="width: 200px;" type="text" name="menu_name" required="required" value="${menuDto.menu_name}"/>
		<input class="form-control" id="menu_price" style="width: 95px;" name="menu_price" type="text" required="required" value="${menuDto.menu_price}" onkeyup="regiPrice(this)"/>
		<input id="menu_seq" type="hidden" name="menu_seq" value="${menuDto.menu_seq}"/>
	</div>
	<div id="confirm" align="left">
		<input style="width: 146px; margin-left: 0px;" class="btn btn-outline-success" type="button" value="수정 완료" onclick="modiMenu()"/>
		<input style="width: 146px;" class="btn btn-outline-warning" type="button" value="취소" onclick="window.close()"/>
	</div>
</div>
	
</body>
</html>