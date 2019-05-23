/**
 *	작성자 - 조민지 - 문의는 제게 해주세요. 
 *  공통되는 유효값 처리
 *  담당자/업주 등록 시 비밀번호, 비밀번호 확인, 전화번호, 이메일 유효값 확인
 *  담당자/업주 정보 수정 시 비밀번호, 비밀번호 확인, 전화번호, 이메일 유효값 확인
 *  
 *  아이디 유효값은 포함하지 않음
 *   
 */

$(function(){
	
	//비밀번호 유효성 검사(공백검사, 4자리 이상)
	$("#pw").keyup(function(){
		var inputLen = $(this).val().length;
		var pw = $(this).val();
		var pwChk = $("#pwChk").val();

		if(pw!=pwChk){ // pwChk 사용 후 다시 변경 시
//			$("#pwChkRst").css({'color':'red', 'font-size':'10px'});
//			$("#pwChkRst").html(" ");
			$("#pw").attr("class","form-control is-invalid");
			$("#pwchkVal").val("0");
		}
		
		if(pw.indexOf(" ") != -1){
//			$("#pwRst").css({'color':'red', 'font-size':'10px'});
//			$("#pwRst").html("  공백 사용 불가");
			$("#pw").attr("class","form-control is-invalid");
			$("#pwchkVal").val("0");
		} else if(inputLen>=4 && inputLen <=12){
//			$("#pwRst").css({'color':'forestgreen', 'font-size':'10px'});
//			$("#pwRst").html("  사용 가능한 비밀번호");
			$("#pw").attr("class","form-control is-valid");
			$("#pwchkVal").val("1");
		} else {
//			$("#pwRst").css({'color':'red', 'font-size':'10px'});
//			$("#pwRst").html(" ");
			$("#pw").attr("class","form-control is-invalid");
			$("#pwchkVal").val("0");
		}
	}); // 비밀번호 유효성 검사 종료
	
	// 비밀번호 확인 유효성 검사
	$("#pwChk").keyup(function(){
		var pwChk = $(this).val();
		var pw = $("#pw").val();
		
		if(pwChk==pw){
//			$("#pwChkRst").css({'color':'forestgreen', 'font-size':'10px'});
//			$("#pwChkRst").html("  비밀번호 일치");
			$("#pwChk").attr("class","form-control is-valid");
			$("#pwchkVal").val("1");
		} else {
//			$("#pwChkRst").css({'color':'red', 'font-size':'10px'});
//			$("#pwChkRst").html("  비밀번호 불일치");
			$("#pwChk").attr("class","form-control is-invalid");
			$("#pwchkVal").val("0");
		}
	}); // 비밀번호 확인 유효성 검사 종료

	// 전화번호 정규화 표현식
	$("#phone").keyup(function(){
		var phn = $(this).val();
		
		var phnRegExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
		
		if(phn.indexOf(" ") != -1){
//			$("#phnRst").css({'color':'red', 'font-size':'10px'});
//			$("#phnRst").html("  공백 사용 불가");
			$("#phone").attr("class","form-control is-invalid");
			$("#phnchkVal").val("0");
		} else if(phn.match(phnRegExp) != null){
//			$("#phnRst").css({'color':'forestgreen', 'font-size':'10px'});
//			$("#phnRst").html("  사용 가능한 전화번호");
			$("#phone").attr("class","form-control is-valid");
			$("#phnchkVal").val("1");
		} else {
//			$("#phnRst").css({'color':'red', 'font-size':'10px'});
//			$("#phnRst").html(" -포함해서 입력해주세요");
			$("#phone").attr("class","form-control is-invalid");
			$("#phnchkVal").val("0");
		}
	}); // 전화번호 유효성 검사 종료
	
	// 이메일 유효성 검사
	$("#email").keyup(function(){
		var email = $(this).val();
		
		var emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	
		if(email.indexOf(" ") != -1){
//			$("#emailRst").css({'color':'red', 'font-size':'10px'});
//			$("#emailRst").html("  공백 사용 불가");
			$("#email").attr("class","form-control is-invalid");
			$("#emailchkVal").val("0");
		}else if(email.match(emailRegExp)!=null){
//			$("#emailRst").css({'color':'forestgreen', 'font-size':'10px'});
//			$("#emailRst").html("  사용 가능한 이메일");
			$("#email").attr("class","form-control is-valid");
			$("#emailchkVal").val("1");
		}else{
//			$("#emailRst").css({'color':'red', 'font-size':'10px'});
//			$("#emailRst").html("  유효한 이메일을 입력해 주세요");
			$("#email").attr("class","form-control is-invalid");
			$("#emailchkVal").val("0");
		}
	}); // 이메일 유효성 검사 완료
	
});