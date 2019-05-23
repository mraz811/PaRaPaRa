<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>담당자 등록 페이지</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<script type="text/javascript">
//등록 취소 버튼클릭 시 실행할 함수
function adRegiCancel(){
	self.close();
};

//select 박스로 시/도 선택시 시/군/구 바꾸어주기
var chgSigungu = function(sido){
	var seoul = ["강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구",
		"동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구",
		"용산구","은평구","종로구","중구","중랑구"];
	var incheon = ["강화군","계양구","남동구","동구","미추홀구","부평구","서구","연수구","옹진군","중구"];
	
	var target = document.getElementById("sigungu");
// 	alert(sido.value);
	if(sido.value == "SEOUL"){
		var sigungu = seoul;
	} else if(sido.value == "INCHEON"){
		var sigungu = incheon;
	} else{
		swal("등록 오류","지역을 선택해 주세요","error");
		return false;
	}
	
	target.options.length = 0;
	for(x in sigungu){
		var opt = document.createElement("option");
		opt.innerHTML = sigungu[x];
		opt.value = change(new Number(x)+1);
		target.appendChild(opt);
		
	}
};

var change = function(val){
	// 1자리 숫자 앞에 0 붙여주기
	return (val/10<1)? "0"+val : val;
};

</script>
<style type="text/css">

.form-control{
	width: 250px;
}

.fullCtrl{
	width: 280px;
	margin-left: 20%;
	margin-top: 20px;
	margin-bottom: 40px;
}

.custom-select{
	width: 119px;
	margin-right: 9px;
	margin-left: 0px;
}
</style>
</head>
<body>
<div id="container">
	
	<input type="hidden" value="0" id="idchkVal">
	<input type="hidden" value="0" id="pwchkVal">
	<input type="hidden" value="0" id="phnchkVal">
	<input type="hidden" value="0" id="emailchkVal">
	
	<!-- 담당자 등록 form -->
	<div class="fullCtrl">
	<form id="adRegiForm" action="" method="post" onsubmit="regiChk()">
	<fieldset>
		<div class="form-group">
			<label for="id">사번</label>
			<input class="form-control" type="text" id="id" name="admin_id" placeholder="사번(아이디)" required="required" maxlength="8">
			<div class="valid-feedback">사용 가능한 아이디</div>
			<div class="invalid-feedback">사용 불가능한 아이디</div>
		</div>
		<div class="form-group">
			<label for="pw">비밀번호</label>	
			<input class="form-control" type="password" id="pw" name="admin_pw" placeholder="비밀번호" required="required" maxlength="12">
			<div class="valid-feedback">사용 가능한 비밀번호</div>
			<div class="invalid-feedback">사용 불가능한 비밀번호</div>
		</div>
		<div class="form-group">
			<label for="pwChk">비밀번호 확인</label><span id="pwChkRst"></span><br>	
			<input class="form-control" type="password" id="pwChk" placeholder="비밀번호 확인" required="required" maxlength="12">
			<div class="valid-feedback">비밀번호 일치</div>
			<div class="invalid-feedback">비밀번호 불일치</div>
		</div>
		<div class="form-group">
			<label for="name">담당자명</label>
			<input class="form-control" type="text" id="name" name="admin_name" placeholder="이름" required="required" maxlength="20">
		</div>
		<div class="form-group">			
			<label>전화번호</label><span id="phnRst"></span>
			<input class="form-control" type="text" id="phone" name="admin_phone" placeholder="연락처" required="required" maxlength="20">
			<div class="valid-feedback">사용 가능한 전화번호</div>
			<div class="invalid-feedback">-를 포함해서 입력해주세요</div>
		</div>
		<div class="form-group">
			<label>이메일</label><span id="emailRst"></span>
			<input class="form-control" type="text" id="email" name="admin_email" placeholder="이메일" required="required" maxlength="80">
			<div class="valid-feedback">사용 가능한 이메일</div>
			<div class="invalid-feedback">유효한 이메일을 입력해주세요</div>
		</div>
		<div class="form-group">	
			<label>지역코드</label><br>
			<select class="custom-select" name="loc_sido" onchange="chgSigungu(this)" required="required">
				<option>시/도</option>
				<option value="SEOUL">서울시</option>
				<option value="INCHEON">인천시</option>
			</select>	
			
			<select class="custom-select" id="sigungu" name="loc_sigungu" required="required">
				<option>시/군/구</option>
			</select>
		</div>
		<br>
		<div>	
			<input style="width: 123px;" class="btn btn-outline-success" type="submit" value="등　록">
			<input style="width: 123px;" class="btn btn-outline-warning" type="button" value="취　소" onclick="adRegiCancel()">
		</div>
		</fieldset>
	</form>
	</div>

</div>

</body>
<!-- 유효성 검사 -->
<script type="text/javascript" src="js/validationChk.js"></script>
<script type="text/javascript">

//회원 등록 버튼 클릭 시 실행할 함수
var regiChk = function(){
	var idVal = $("#idchkVal").val();
	var pwVal = $("#pwchkVal").val();
	var phnVal = $("#phnchkVal").val();
	var emailVal = $("#emailchkVal").val();
	var sido = $("select[name=loc_sido]")[0].val();
	
	
	alert(idVal +":" + pwVal +":"+phnVal +":" +emailVal);
	alert(sido);
	
	if(idVal=="1" && pwVal=="1" && phnVal=="1" && emailVal=="1"){
		var adminData = $("form").serialize();
 		// 유효값 모두 통과 시
		$.ajax({
			url: "./adminRegi.do",
			type: "post",
			data: adminData,
			async: false,
			success: function(){
				swal({
					title: "담당자 등록 완료", 
					text: "담당자가 등록되었습니다", 
					type: "success"
				},
				function(){ 
					opener.parent.location.reload();
					regiCancel();
				});
			},
			error: function(){
				swal("회원등록 실패", "등록이 실패되었습니다.","error");
			}
 		}); // ajax
		
 	} else {
 		swal("회원등록 실패", "유효값을 확인해주세요", "error");
 	}
};


$(function(){
	// 회원등록 폼 작성시 아이디 유효값 확인 필요(공백포함여부, 중복여부)
	$("#id").keyup(function(){
		var inputLen = $(this).val().length;
		var id = $(this).val();
		// 담당자 사번 숫자만 입력 가능
		var admRegex = /^[0-9]*$/;
		
		// 공백여부 검사
		if(id.indexOf(" ") != -1){
			$("#id").attr("class","form-control is-invalid");
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
						$("#id").attr("class","form-control is-valid");
						$("#idchkVal").val("1");						
					} else{
						$("#idchkVal").val("0");
					}
				}
			});
			
		} else{
			$("#id").attr("class","form-control is-invalid");
			$("#idchkVal").val("0");
		}
	});  // 아이디 유효성 검사 종료
});

</script>
</html>