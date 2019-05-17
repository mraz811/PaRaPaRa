<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">

<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
</head>
<body>


<div id="container">

	<input type="hidden" id="emailchkVal" value="0">
	<input type="hidden" id="idchkVal" value="0">
	<form id="findPwForm" action="./findPw.do" method="post" onsubmit="return chkVal()">
		<label><input type="radio" name="auth" value="U" checked="checked"> 업주 </label>		
		<label><input type="radio" name="auth" value="A"> 담당자 </label>	
		<br>
		<input type="text" id="inputId" name="id" placeholder="아이디를 입력하세요" required="required" >
		<span id="idRst"></span><br>
		<input type="text" id="inputEmail" name="email" placeholder="이메일을 입력하세요" required="required" >
		<span id="emailRst"></span><br>

		<div>
			<input type="submit" value="임시 비밀번호 받기" >
			<input type="button" value="취소" onclick="javascript:history.back(-1)">
		</div>
	</form>
</div>

<%@include file="../footer.jsp" %>

</body>
<script type="text/javascript">

$(function(){

	//이메일 유효성 검사
	$("#inputEmail").keyup(function(){
		var email = $(this).val();
		
		var emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

		if(email.indexOf(" ") != -1){
			$("#emailRst").css({'color':'red', 'font-size':'10px'});
			$("#emailRst").html("  공백 사용 불가");
			$("#emailchkVal").val("0");
		}else if(email.match(emailRegExp)!=null){
			$("#emailRst").css({'color':'forestgreen', 'font-size':'10px'});
			$("#emailRst").html("  사용 가능한 이메일");
			$("#emailchkVal").val("1");
		}else{
			$("#emailRst").css({'color':'red', 'font-size':'10px'});
			$("#emailRst").html("  유효한 이메일을 입력해 주세요");
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
			$("#idRst").css({'color':'red', 'font-size':'10px'});
			$("#idRst").html("  공백 사용 불가");
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
						$("#idRst").css({'color':'forestgreen', 'font-size':'10px'});
						$("#idRst").html("  유효한 아이디");
						$("#idchkVal").val("1");
					} else{
						$("#idRst").css({'color':'red', 'font-size':'10px'});
						$("#idRst").html("  유효하지 않은 아이디");
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
						$("#idRst").css({'color':'forestgreen', 'font-size':'10px'});
						$("#idRst").html("  유효한 아이디");
						$("#idchkVal").val("1");					
					} else{
						$("#idRst").css({'color':'red', 'font-size':'10px'});
						$("#idRst").html("  유효하지 않은 아이디");
						$("#idchkVal").val("0");
					}
				}
			});
			
		} else{
			$("#idRst").css({'color':'red', 'font-size':'10px'});
			$("#idRst").html("  유효하지 않은 아이디");
			$("#idchkVal").val("0");
		}
	});
	
});

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