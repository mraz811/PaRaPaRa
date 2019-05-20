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
	<input type="hidden" value="0" id="phnchkVal">
	<input type="hidden" value="0" id="emailchkVal">
	
	<!-- 담당자 등록 form -->
	<form id="adRegiForm" action="./adminRegi.do" method="post" onsubmit="return RegiChk()">
		<div>
			<span>사번</span><span id="idRst"></span><br>
			<input type="text" id="id" name="admin_id" placeholder="사번(아이디)" required="required" maxlength="8">
			
			<br><span>비밀번호</span><span id="pwRst"></span><br>	
			<input type="password" id="pw" name="admin_pw" placeholder="비밀번호" required="required" maxlength="12">
		
			<br><span>비밀번호 확인</span><span id="pwChkRst"></span><br>	
			<input type="password" id="pwChk" name="pwChk" placeholder="비밀번호 확인" required="required" maxlength="12">
		
			<br><span>담당자명</span><br>	
			<input type="text" id="name" name="admin_name" placeholder="이름" required="required" maxlength="20">
			
			<br><span>전화번호</span><span id="phnRst"></span><br>	
			<input type="text" id="phone" name="admin_phone" placeholder="연락처" required="required" maxlength="20">
		
			<br><span>이메일</span><span id="emailRst"></span><br>	
			<input type="text" id="email" name="admin_email" placeholder="이메일" required="required" maxlength="80">
			
			<br/><span>지역코드</span><br/>
			<select name="loc_sido" onchange="chgSigungu(this)">
				<option>시/도</option>
				<option value="SEOUL">서울시</option>
				<option value="INCHEON">인천시</option>
			</select>	
			
			<select id="sigungu" name="loc_sigungu">
				<option>시/군/구</option>
<!-- 				<option value="01">강남구</option> -->
<!-- 				<option value="02">강동구</option> -->
			</select>
		</div>
		<div>	
			<input type="submit" value="등록 완료">
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
// 					alert(msg.substr(0,5)); // 사용 가능 / 사용 불가
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
		
		if(pw!=pwChk){ // pwChk 사용 후 다시 변경 시
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
			$("#pwChkRst").html("  비밀번호 불일치");
			$("#pwchkVal").val("0");
		}
	}); // 비밀번호 확인 유효성 검사 종료
	
	
	// 전화번호 정규화 표현식
	$("#phone").keyup(function(){
		var phn = $(this).val();
		
		var phnRegExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
		
		if(phn.indexOf(" ") != -1){
			$("#phnRst").css({'color':'red', 'font-size':'10px'});
			$("#phnRst").html("  공백 사용 불가");
			$("#phnchkVal").val("0");
		} else if(phn.match(phnRegExp) != null){
			$("#phnRst").css({'color':'forestgreen', 'font-size':'10px'});
			$("#phnRst").html("  사용 가능한 전화번호");
			$("#phnchkVal").val("1");
		} else {
			$("#phnRst").css({'color':'red', 'font-size':'10px'});
			$("#phnRst").html(" -포함해서 입력해주세요");
			$("#phnchkVal").val("0");
		}
	}); // 전화번호 유효성 검사 종료
	
	// 이메일 유효성 검사
	$("#email").keyup(function(){
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
	
	
});

// select 박스로 시/도 선택시 시/군/구 바꾸어주기
var chgSigungu = function(sido){
	var seoul = ["강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구",
		"동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구",
		"용산구","은평구","종로구","중구","중랑구"];
	var incheon = ["강화군","계양구","남동구","동구","미추홀구","부평구","서구","연수구","옹진군","중구"];
	
	var target = document.getElementById("sigungu");
	alert(sido.value);
	if(sido.value == "SEOUL"){
		var sigungu = seoul;
	} else if(sido.value == "INCHEON"){
		var sigungu = incheon;
	}
	
	target.options.length = 0;
	for(x in sigungu){
		var opt = document.createElement("option");
		opt.innerHTML = sigungu[x];
		opt.value = change(new Number(x)+1);
		target.appendChild(opt);
		
	}
}

var change = function(val){
	// 1자리 숫자 앞에 0 붙여주기
	return (val/10<1)? "0"+val : val;
}

// 등록 취소 버튼클릭 시 실행할 함수
var regiCancel = function(){
	
	
}


// 회원 등록 버튼 클릭 시 실행할 함수
var RegiChk = function(){
	var idVal = document.getElementById("idchkVal").value;
	var pwVal = document.getElementById("pwchkVal").value;
	var phnVal = document.getElementById("phnchkVal").value;
	var emailVal = document.getElementById("emailchkVal").value;
	
// 	alert(idVal +":" + pwVal +":"+phnVal +":" +emailVal);
	if(idVal=='1' && pwVal=='1' && phnVal=='1' && emailVal=='1'){
		return true;
	} else {
		swal("회원등록 실패", "유효값을 확인해주세요");
		return false;
	}
	return false;
}

</script>
</html>