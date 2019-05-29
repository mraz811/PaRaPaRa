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
	var fDto = null;
	function regiTempFile(){ //파일 임시 저장 후 미리보기
		var form = new FormData(document.getElementById("tempFrm"));
	
		$.ajax({
			url : "./insertMenuTempFile.do",
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
				alert("안되안되");
			}
		});
	}

	function regiMenu(){ //메뉴 등록
		var price = document.getElementById("menu_price").value;
		if(isNaN(price)){
			alert("메뉴 금액란에 숫자만 입력해주세요.");
			return false;
		}
		var fileCheck = document.getElementById("selectedFile").value;
	    if(!fileCheck){
	        alert("파일을 첨부해 주세요");
	        return false;
	    }
		
		var menu_name = document.getElementById("menu_name").value;
		var menu_price = document.getElementById("menu_price").value;
		var select = document.getElementsByTagName("select")[0];
		var index = select.selectedIndex;
		var menu_category = select.options[index].value;
		
		var file_seq = fDto.file_seq;
		var file_tname = fDto.file_tname;
		var file_rname = fDto.file_rname;
		var file_size = fDto.file_size;
		var file_aurl = fDto.file_aurl;
		var file_rurl = fDto.file_rurl;
// 		FILE_SEQ, FILE_TNAME, FILE_RNAME, 
// 		FILE_SIZE, MENU_SEQ, FILE_AURL, FILE_RURL
		
		$.ajax({
			url : "./regiNewMenu.do",
			type : "post",
			data : {"file_seq":file_seq,"file_tname":file_tname,"file_rname":file_rname,"file_size":file_size,"file_aurl":file_aurl,"file_rurl":file_rurl,"menu_name":menu_name,"menu_price":menu_price,"menu_category":menu_category},
			dataType : "json",
			success : function(obj){
				alert("\""+obj.menu_name+"\""+"메뉴가 등록되었습니다.");
				window.opener.location.reload();
				window.close();
			},error : function(obj){
				alert("메뉴 등록 실패, 관리자에게 문의해주세요.");
			}
		});
	}
	function regiPrice(val){ //메뉴 금액 유효값
		 // 키를 눌렀을 때 해당 key의 코드를 받아옴 
        var keyValue = event.keyCode;
		if(((keyValue >= 65) && (keyValue <= 90)) ||  ((keyValue >= 106) && (keyValue <= 111)) || ((keyValue >= 186) && (keyValue <= 222)) || keyValue==32 ){	// 문자 및 특수문자, 스페이스바를 입력했을 때
        	alert("메뉴 금액란에 숫자만 입력해주세요.");
        	document.getElementById("menu_price").value = "";
        }
	}
</script>
<body>
	<div id="top">
		메뉴등록
	</div>
		<div id="image" class="form_group">
			<img id="menuImg" alt="메뉴사진" src="">
		</div>
	<form id="tempFrm" action="#" method="post" enctype="multipart/form-data">
		<input type="file" id="selectedFile" name="file1" onchange="regiTempFile()"/>
	</form>	
	<div id="selectAndFile">
		<select id="select">
			<option value="">---카테고리 선택---</option>
			<option value="주메뉴">주메뉴</option>
			<option value="사이드메뉴">사이드메뉴</option>
			<option value="음료">음료</option>
		</select>
		<input type="hidden" id="menu_category" name="menu_category" value=""/>
	</div>
	<div id="menu">
		<input id="menu_name" style="width: 190px; height: 40px;" type="text" name="menu_name" placeholder="메뉴명" required="required"/>
		<input id="menu_price" style="width: 90px; height: 40px;" name="menu_price" type="text" placeholder="가격" required="required" onkeyup="regiPrice(this)"/>
	</div>
	<div id="confirm">
		<input style="width: 150px; height: 30px" type="button" value="등록완료" onclick="regiMenu()"/><input style="width: 150px; height: 30px" type="button" value="취소" onclick="window.close()"/>
	</div>
</body>
</html>