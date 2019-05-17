<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">

<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
</head>
<body>
<%-- <%@include file="../header.jsp" %> --%>


<div id="container">
	<div id="title">파라파라 로그인</div>

	<input type="hidden" value="0" id="chkVal">
		
	<form id="loginform" method="post">
		<label><input type="radio" name="auth" value="U" checked="checked"> 업주 </label>		
		<label><input type="radio" name="auth" value="A"> 담당자 </label>	
		<br>
		<input type="text" id="inputId" name="id" placeholder="아이디를 입력하세요" required="required" >
		<span id="idresult"></span><br>
		<input type="password" id="inputPw" name="pw" placeholder="비밀번호를 입력하세요" required="required" >
		<br>
		<input type="button" value="로그인" onclick="loginCheck()">
		
		<!-- 비밀번호 찾기 버튼 추가 예정 --> 
		<a href="./findPwForm.do">비밀번호 찾기</a>
	</form>


</div>

<%@include file="../footer.jsp" %>
</body>

<script type="text/javascript">

// 로그인 버튼 클릭 시 작동되는 함수
	var loginCheck = function(){
// 		var auth = document.getElementsByName("auth");
		var id = document.getElementById("inputId").value;
		var pw = document.getElementById("inputPw").value;
// 		alert(id+":"+pw);
// 		alert(auth[0].checked); // true/false 반환
// 		alert(auth[1].checked); // 담당자
		
		var chkVal = document.getElementById("chkVal").value;
		var loginForm = document.forms[0];
		
		if(id==null || id=="" || pw==null || pw==""){
			swal("로그인","아이디와 비밀번호를 확인해 주세요");
		} else if(chkVal==1){
			loginForm.action = "./login.do";
			loginForm.submit();
			return true;
		} else{
			swal("로그인", "아이디와 비밀번호를 확인해 주세요");
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
			$("#idresult").html("아이디를 확인해 주세요");
			$("#chkVal").val("0");
		} else if(auth[0].checked && id.match(ownRegex)!=null){
			$("#idresult").html("업주 유효값 확인");
			$("#chkVal").val("1");
		} else if(auth[1].checked && id.match(admRegex)!=null){
			$("#idresult").html("담당자 유효값 확인");
			$("#chkVal").val("1");
		} else {
			$("#idresult").html("아이디를 확인해 주세요");
			$("#chkVal").val("0");
		}
	});
	
	// 담당자/업주 선택 변경 시 값 초기화
	$("input:radio[name=auth]").click(function(){
		$("#inputId").val("");
		$("#inputPw").val("");
	});
		
});

</script>
</html>