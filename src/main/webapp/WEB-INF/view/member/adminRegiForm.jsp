<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>담당자 등록 페이지</title>

</head>
<body>
<%@include file="../header.jsp" %>
<div id="container">
	
	<input type="hidden" value="0" id="idchkVal">
	<input type="hidden" value="0" id="pwchkVal">
	
	<!-- 담당자 등록 form -->
	<form id="adRegiForm" action="./adminRegi.do" method="post">
		<div>
			<span>사번</span><span id="idRst"></span><br>
			<input type="text" id="id" name="admin_id" placeholder="사번(아이디)" required="required" maxlength="8">
			
			<br><span>비밀번호</span><span id="pwRst"></span><br>	
			<input type="password" id="pw" name="admin_pw" placeholder="비밀번호" required="required" maxlength="12">
		
			<br><span>비밀번호 확인</span><span id="pwChkRst"></span><br>	
			<input type="password" id="pwChk" name="pwChk" placeholder="비밀번호 확인" required="required" maxlength="12">
		
			<br><span>담당자명</span><br>	
			<input type="text" id="name" name="admin_name" placeholder="이름" required="required" maxlength="20">
			
			<br><span>전화번호</span><br>	
			<input type="text" id="phone" name="admin_phone" placeholder="연락처" required="required" maxlength="20">
		
			<br><span>이메일</span><br>	
			<input type="text" id="email" name="admin_email" placeholder="이메일" required="required" maxlength="80">
			
			<br/><span>지역코드</span><br/>
			<select name="loc_sido" onchange="selSigungu()">
				<option value="SEOUL">서울시</option>
				<option value="INCHEON">인천시</option>
			</select>	
			
			<select name="loc_sigungu">
				<option value="01">강남구</option>
				<option value="02">강동구</option>
				<option value="03">강북구</option>
				<option value="04">강서구</option>
				<option value="05">관악구</option>
				<option value="06">광진구</option>
				<!-- ...위 옵션에 따라 선택해야함 25개... -->
			</select>
		</div>
		<div>	
			<input type="submit" value="등록 완료" onsubmit="return adRegiChk()">
			<input type="button" value="취소" onclick="regiCancel()">
		</div>
	</form>


</div>

<%@include file="../footer.jsp" %>
</body>
<script type="text/javascript">

$(function(){
	
	// 회원등록 폼 작성시 아이디 유효값 확인 필요(공백포함여부, 중복여부)
	$("#id").keyup(function(){
		var inputLen = $(this).val().length;
		var id = $(this).val();
		// 담당자 사번 숫자만 입력 가능
		var admRegex = /^[0-9]*$/;
		
		// 공백여부 검사
		if(id.indexOf(" ") != -1){
			$("#idRst").css({'color':'red', 'font-size':'10px'});
			$("#idRst").html("  사용 불가능한 아이디");
			$("#idchkVal").val("0");
		} else if(inputLen>3 && id.match(admRegex)!=null){
			// ajax를 통한 아이디 중복검사 
			$.ajax({
				url : "./admIdChk.do",
				type : "post",
				data : "admin_id="+id,
				async : true,
				success : function(msg){
					alert(msg.substr(0,5)); // 사용 가능 / 사용 불가
					if(msg.substr(0,5)=="사용 가능"){
						$("#idRst").css({'color':'forestgreen', 'font-size':'10px'});
						$("#idRst").html("  사용 가능한 아이디");
						$("#idchkVal").val("1");						
					} else{
						$("#idRst").css({'color':'red', 'font-size':'10px'});
						$("#idchkVal").val("0");
					}
				}
			});
		} else{
			$("#idRst").css({'color':'red', 'font-size':'10px'});
			$("#idRst").html("  사용 불가능한 아이디");
			$("#idchkVal").val("0");
		}
	});  // 아이디 유효성 검사 종료
	
	
	//비밀번호 유효성 검사(공백검사, 4자리 이상)
	$("#pw").keyup(function(){
		var inputLen = $(this).val().length;
		var pw = $(this).val();
		var pwChk = $("#pwChk").val();
		
		if(pw!=pwChk){
			$("#pwChkRst").css({'color':'red', 'font-size':'10px'});
			$("#pwChkRst").html(" ");
			$("#pwchkVal").val("0");
		}
		
		if(pw.indexOf(" ") != -1){
			$("#pwRst").css({'color':'red', 'font-size':'10px'});
			$("#pwRst").html("  공백 사용 불가");
			$("#pwchkVal").val("0");
		} else if(inputLen>=4 && inputLen <=12){
			$("#pwRst").css({'color':'forestgreen', 'font-size':'10px'});
			$("#pwRst").html("  사용 가능한 비밀번호");
			$("#pwchkVal").val("1");
		} else {
			$("#pwRst").css({'color':'red', 'font-size':'10px'});
			$("#pwRst").html(" ");
			$("#pwchkVal").val("0");
		}
	}); // 비밀번호 유효성 검사 종료
	
	// 비밀번호 확인 유효성 검사
	$("#pwChk").keyup(function(){
		var pwChk = $(this).val();
		var pw = $("#pw").val();
		
		if(pwChk==pw){
			$("#pwChkRst").css({'color':'forestgreen', 'font-size':'10px'});
			$("#pwChkRst").html("  비밀번호 일치");
			$("#pwchkVal").val("1");
		} else {
			$("#pwChkRst").css({'color':'red', 'font-size':'10px'});
			$("#pwChkRst").html(" ");
			$("#pwchkVal").val("0");
		}
	}); // 비밀번호 확인 유효성 검사 종료
	
	// 이메일 유효성 검사
	
	
	
});

// select 박스로 시/도 선택시 시/군/구 바꾸어주어야 함.
var selSigungu = function(){
	
}

// 등록 취소 버튼클릭 시 실행할 함수
var regiCancel = function(){
	
	
}

// 담당자 회원 등록 버튼 클릭 시 실행할 함수
var adRegiChk = function(){
	
}

</script>
</html>