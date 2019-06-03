<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<style type="text/css">

#loginform{
	margin: 100px auto;
	width: 300px;
}
#paraPhoto img{
	width: 300px;
	height: 150px;
}
</style>
</head>
<body>


<div id="container">
	<form id="loginform" method="post" action="./login.do">
		<input type="hidden" value="0" id="chkVal">
<!-- 		<h2 align="center">파라파라 로그인</h2> -->
		<div id="paraPhoto">
			<a href="loginForm.do">
				<img alt="parapara_logo" src="./imgs/logo2.jpg" class="logoImg" >
			</a>
		</div>
		<hr>
		<label style="width: 148px;"><input type="radio" name="auth" value="U" checked="checked"> 업주 </label>		
		<label><input type="radio" name="auth" value="A"> 담당자 </label>	
		<div class="form-group">
			<input class="form-control" type="text" id="inputId" name="id" placeholder="아이디를 입력하세요" required="required" >
			<div class="valid-feedback">	유효한 아이디</div>
			<div class="invalid-feedback">	유효하지 않은 아이디</div>
		</div>
		<div class="form-group">
			<input onkeyup="enterkey();" class="form-control" type="password" id="inputPw" name="pw" placeholder="비밀번호를 입력하세요" required="required" >
		</div>
		<div>
			<input style="width: 300px; height: 40px;" class="btn btn-outline-success" type="button" value="로그인" onclick="loginCheck()">
		</div>
		<hr>
		<div align="right">
			<a href="./findPwForm.do">비밀번호 찾기</a>
		</div>
	</form>


</div>

<%@include file="../footer.jsp" %>
</body>

<script type="text/javascript">

function enterkey() {
    if (window.event.keyCode == 13) {
         // 엔터키가 눌렸을 때 실행할 내용
         loginCheck();
    }
}


// 로그인 버튼 클릭 시 작동되는 함수
	var loginCheck = function(){
// 		var auth = document.getElementsByName("auth");
		var id = $("#inputId").val();
		var pw = $("#inputPw").val();
// 		alert(id+":"+pw);
// 		alert(auth[0].checked); // true/false 반환
// 		alert(auth[1].checked); // 담당자
		
		var chkVal = $("#chkVal").val();
		var logAjax = $("#loginform").serialize();
// 		alert(logAjax);
		
		var loginForm = $("#loginform");

		if(id==null || id=="" || pw==null || pw==""){
			swal("로그인 실패","아이디와 비밀번호를 확인해 주세요");
		} else if(chkVal==1){
			$.ajax({
				url : "./loginChk.do",
				type: "post",
				data: logAjax,
				success: function(msg){
					if(msg=="성공"){
						loginForm.submit();
					}else{
						swal("로그인 실패", "아이디와 비밀번호를 확인해 주세요");
					}
					
				}, error: function(msg){
					swal("로그인 실패", "아이디와 비밀번호를 확인해 주세요");
				}
				
			});
			
		} else{
			// 유효값 
			swal("로그인 실패", "아이디와 비밀번호를 확인해 주세요");
			return false;
		}
		
		return false;	
	}
	
$(function(){
		
	// 업주 및 담당자 선택 후 아이디 입력창 작성 시 유효값 확인하기 위함
	$("#inputId").keyup(function(){
		var inputLen = $(this).val().length;
// 		alert(inputLen);
			
		var id = $(this).val();
		var auth = $("input[name=auth]");
// 		alert(auth[0].checked); //업주 선택
// 		alert(auth[1].checked); //담당자 선택
			
		// 담당자 로그인 시 정규화 표현식. id는 숫자만 입력가능
		var admRegex = /^[0-9]*$/;
		// 업주 로그인 시 사업자 번호 숫자 3자리-2자리-5자리
		var ownRegex = /^\d{3}-\d{2}-\d{5}$/;

		// 공백여부 검사 및 업주(정규표현식), 담당자(정규표현식) 검사
		if(id.indexOf(" ") != -1){
			$("#inputId").attr("class","form-control is-invalid");
			$("#chkVal").val("0");
		} else if(auth[0].checked && id.match(ownRegex)!=null){
			$("#inputId").attr("class","form-control is-valid");
			$("#chkVal").val("1");
		} else if(auth[1].checked && id.match(admRegex)!=null){
			$("#inputId").attr("class","form-control is-valid");
			$("#chkVal").val("1");
		} else {
			$("#inputId").attr("class","form-control is-invalid");
			$("#chkVal").val("0");
		}
	});
	
	// 담당자/업주 선택 변경 시 값 초기화
	$("input:radio[name=auth]").click(function(){
		$("#inputId").val("");
		$("#inputId").attr("class","form-control");
		$("#inputPw").val("");
		$("#inputPw").attr("class","form-control");
	});
		
});

</script>
</html>