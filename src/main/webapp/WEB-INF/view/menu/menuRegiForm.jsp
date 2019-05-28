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
	
// 	//폼에 데이터를 추가하는거(파일을 전송하기전 정보 유지)
// 	var fd = new FormData();
	
// 	function regiFile(){
// 		//추가적으로 보낼 파라미터가 있으면 FormData에 넣어줌
// 		var menu_name = document.getElementById("menu_name").value;
// 		var menu_price = document.getElementById("menu_price").value;
// 		var select = document.getElementsByTagName("select")[0];
// 		var index = select.selectedIndex;
// 		var menu_category = select.options[index].value;
		
// 		fd.append("menu_name", menu_name);
// 		fd.append("menu_price", menu_price);
// 		fd.append("menu_category", menu_category);
		
// 		var 
		
// 		var files = e.originalEvent.dataTransfer.files;
// 		handleFileUpload(files);
		
// 		regiMenu();
// 	}
	
// 	function handleFileUpload(files) {
// 		//파일의 길이만큼 반복하며 formData에 셋팅해준다.
// 		alert(files.length);
// 		var megaByte = 1024*1024;
// 		for (var i = 0; i < files.length; i++) {
// 			alert("what??");
// 			alert(files[i].name);
// 			if((files[i].size/megaByte) <= 10 ){
// //			if(map.containsValue(files[i].name) == false && (files[i].size/megaByte) <= 10 ){
// 				alert("for문실행");
// 				fd.append(files[i].name, files[i]);
// 				//파일 중복 업로드를 방지하기 위한 설정
// //				map.put(files[i].name, files[i].name);
// 				// 파일 이름과 정보를 추가해준다.
// //				var tag = createFile(files[i].name, files[i].size);
// //				$('#fileTable').append(tag);
// 			}else{
// 				alert("뭐지");
// 				//중복되는 정보 확인 위해 콘솔에 찍음
// 				if((files[i].size/megaByte) > 10){
// 					alert(files[i].name+"은(는) \n 10메가 보다 커서 업로드가 할 수 없습니다.");
// 				}else{
// 					alert('파일 중복 : ' + files[i].name);
// 				}
// 			}
// 		}
// 	}
	
// 	var regiMenu = function (){
//   		$.ajax({
// 			url : "./regiNewMenu.do",
// 			type : "post",
// 			async : true,
// 			data : fd,
// 			contentType : false,
// 			processData : false,
// 			cache : false,
// 			success : function(obj){
// 				if(obj == "성공"){
// 					alert("\""+menu_name+"\""+"이(가)"+"등록 되었습니다.");
// 					window.close();
// 				}
// 			},error : function(obj){
// 				alert("관리자에게 문의해주세요"); 
// 			}
// 		})
// 	}
	function regiMenu(){
		var frm = document.getElementById("frm");
		var select = document.getElementsByTagName("select")[0];
		var index = select.selectedIndex;
		var menu_category = select.options[index].value;
		document.getElementById("menu_category").value = menu_category;
		frm.action = "./regiNewMenu.do";
		frm.method = "post";
		frm.enctype = "multipart/form-data";
		frm.submit();
	}
</script>
<body>
	<div id="top">
		메뉴등록
	</div>
	<form id="frm" action="./regiNewMenu.do" method="post" enctype="multipart/form-data">
		<div id="image" class="form_group">
			<img id="menuImg" alt="메뉴사진" src="./masolimg/img.png">
			<input type="file" name="file1"/>
		</div>
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
			<input id="menu_price" style="width: 90px; height: 40px;" name="menu_price" type="text" placeholder="가격" required="required"/>
		</div>
		<div id="confirm">
<!-- 			<input style="width: 150px; height: 30px" type="button" value="등록완료" onclick="regiFile()"/><input style="width: 150px; height: 30px" type="button" value="취소" onclick="window.close()"/> -->
			<input style="width: 150px; height: 30px" type="button" value="등록완료" onclick="regiMenu()"/><input style="width: 150px; height: 30px" type="button" value="취소" onclick="window.close()"/>
		</div>
	</form>	
</body>
</html>