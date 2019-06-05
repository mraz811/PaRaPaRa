<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>비밀번호 찾기</title>
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<style type="text/css">
#findPwForm{
	margin: 60px auto;
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

	<input type="hidden" id="emailchkVal" value="0">
	<input type="hidden" id="idchkVal" value="0">
	<form id="findPwForm" action="./findPw.do" method="post" onsubmit="return chkVal()">
		<h3 align="center">비밀번호 찾기</h3>
	<div id="paraPhoto">
			<a href="loginForm.do">
				<img alt="parapara_logo" src="./imgs/logo2.jpg" class="logoImg" >
			</a>
		</div>
		<hr>	
		<label style="width: 148px;"><input type="radio" name="auth" value="U" checked="checked"> 업주 </label>		
		<label><input type="radio" name="auth" value="A"> 담당자 </label>	
	<div class="form-group">
		<input class="form-control" type="text" id="inputId" name="id" placeholder="아이디를 입력하세요" required="required">
		<div class="valid-feedback">유효한 아이디</div>
		<div class="invalid-feedback">유효하지 않은 아이디</div>
	</div>
	<div class="form-group">
		<input onkeyup="enterkey();" class="form-control" type="text" id="inputEmail" name="email" placeholder="이메일을 입력하세요" required="required" >
	</div>
	<div>
		<input style="width: 198px; height: 40px;" class="btn btn-outline-success" type="button" value="email로 임시 비밀번호 받기"  onclick="findpwchk()">
		<input style="width: 98px; height: 40px;" class="btn btn-outline-warning" type="button" value="취　소" onclick="self.close()">
	</div>
	<hr>
	</form>
</div>

<%@include file="../footer.jsp" %>

</body>
<script type="text/javascript">

function enterkey() {
    if (window.event.keyCode == 13) {
         // 엔터키가 눌렸을 때 실행할 내용
         findpwchk();
    }
}

$(function(){

	//이메일 유효성 검사
	$("#inputEmail").keyup(function(){
		var email = $(this).val();
		
		var emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

		if(email.indexOf(" ") != -1){
			$("#inputEmail").attr("class","form-control is-invalid");
			$("#emailchkVal").val("0");
		}else if(email.match(emailRegExp)!=null){
			$("#inputEmail").attr("class","form-control is-valid");
			$("#emailchkVal").val("1");
		}else{
			$("#inputEmail").attr("class","form-control is-invalid");
			$("#emailchkVal").val("0");
		}
	}); // 이메일 유효성 검사 완료

	$("#inputId").keyup(function(){
		var id = $(this).val();
		var auth = $("input[name=auth]");
		
		// 담당자 정규화 표현식. id는 숫자만 입력가능
		var admRegex = /^[0-9]*$/;
		// 업주  사업자 번호 숫자 3자리-2자리-5자리
		var ownRegex = /^\d{3}-\d{2}-\d{5}$/;
		
		if(id.indexOf(" ") != -1){
			$("#inputId").attr("class","form-control is-invalid");
			$("#idchkVal").val("0");
		} else if(auth[0].checked && id.match(ownRegex)!=null){ // 업주일 때
			// 아이디 존재여부 검사
			$.ajax({
				url : "./ownIdChk.do",
				type : "post",
				data : "owner_id="+id,
				async : true,
				success : function(msg){
// 					alert(msg.substr(0,5)); // 사용 가능 / 사용 불가
					if(msg.substr(0,5)=="사용 불가"){
						$("#inputId").attr("class","form-control is-valid");
						$("#idchkVal").val("1");
					} else{ // 존재하지 않을 시
						$("#inputId").attr("class","form-control is-invalid");
						$("#idchkVal").val("0");
					}
				}
			});
			
		} else if(auth[1].checked && id.match(admRegex)!=null){ // 담당자일 때
			// ajax를 통한 아이디 존재여부 검사
			$.ajax({
				url : "./admIdChk.do",
				type : "post",
				data : "admin_id="+id,
				async : true,
				success : function(msg){
// 					alert(msg.substr(0,5)); // 사용 가능 / 사용 불가
					if(msg.substr(0,5)=="사용 불가"){
						$("#inputId").attr("class","form-control is-valid");
						$("#idchkVal").val("1");					
					} else{
						$("#inputId").attr("class","form-control is-invalid");
						$("#idchkVal").val("0");
					}
				}
			});
			
		} else{
			$("#inputId").attr("class","form-control is-invalid");
			$("#idchkVal").val("0");
		}
	});
	
	// 담당자/업주 선택 변경 시 값 초기화
	$("input:radio[name=auth]").click(function(){
		$("#inputId").val("");
		$("#inputId").attr("class","form-control");
		$("#inputEmail").val("");
		$("#inputEmail").attr("class","form-control");

	});
	
});


var findpwchk = function(){
	var id = $("#inputId").val();
	var email = $("#inputEmail").val();
	var findPwForm = $("#findPwForm");
	
// 	alert(id+":"+email);
	
	if(id=="" || email==""){
		swal("비밀번호 찾기", "입력값을 확인해 주세요");
	} else {
		$.ajax({
			url : "./findPwChk.do",
			type: "post",
			data: findPwForm.serialize(),
			success: function(msg){
				if(msg=="성공"){
					findPwForm.submit();
					swal("이메일 확인", "임시 비밀번호로 로그인 해주세요","success");
				}else{
					swal("비밀번호 찾기","아이디와 이메일을 확인해 주세요", "error");
				}
				
			}, error: function(msg){
				swal("비밀번호 찾기","아이디와 이메일을 확인해 주세요", "error");
			}
		});
	}
	
	
	
};

// 폼 제출 전 확인
var chkVal = function(){
	var emchk = $("#emailchkVal").val();
	var idchk = $("#idchkVal").val();
// 	alert(emchk+" : "+idchk);
	
	if(emchk =="1" && idchk =='1'){
		return true;
	}else{
		swal("입력값 확인","아이디와 비밀번호를 확인해주세요");
		return false;
	}
	return false;
}

</script>
</html>