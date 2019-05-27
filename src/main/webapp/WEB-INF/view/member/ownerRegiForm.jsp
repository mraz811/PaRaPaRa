<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업주 등록 페이지</title>
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<script type="text/javascript" src="./js/member/validationChk.js"></script>
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

</style>
</head>
<body>
<div id="container">
<%-- 세션 : ${loginDto} --%>
<%-- ${store_code} --%>

	<input type="hidden" value="0" id="idchkVal">
	<input type="hidden" value="0" id="pwchkVal">
	<input type="hidden" value="0" id="phnchkVal">
	<input type="hidden" value="0" id="emailchkVal">
	
	<!-- 업주 등록 form -->
	<div class="fullCtrl">
	
	<form id="owRegiForm" action="" method="post"  onsubmit="return regiChk()">
	<fieldset>	
		<div class="form-group">
			<label for="id">사업자 등록번호</label>
			<input class="form-control" type="text" id="id" name="owner_id" placeholder="ex)111-11-11111" required="required" maxlength="12">
			<div class="valid-feedback">사용 가능한 아이디</div>
			<div class="invalid-feedback">사용 불가능한 아이디</div>
		</div>
		<div class="form-group">	
			<label for="pw">비밀번호</label>	
			<input class="form-control" type="password" id="pw" name="owner_pw" placeholder="비밀번호" required="required" maxlength="12">
			<div class="valid-feedback">사용 가능한 비밀번호</div>
			<div class="invalid-feedback">사용 불가능한 비밀번호</div>
		</div>
		<div class="form-group">
			<label for="pwChk">비밀번호 확인</label>	
			<input class="form-control" type="password" id="pwChk" name="pwChk" placeholder="비밀번호 확인" required="required" maxlength="12">
			<div class="valid-feedback">비밀번호 일치</div>
			<div class="invalid-feedback">비밀번호 불일치</div>
		</div>
		<div class="form-group">
			<label for="name">업주명</label>	
			<input class="form-control" type="text" id="name" name="owner_name" placeholder="이름" required="required" maxlength="20">
		</div>
		<div class="form-group">	
			<label>전화번호</label>	
			<input class="form-control" type="text" id="phone" name="owner_phone" placeholder="연락처" required="required" maxlength="20">
			<div class="valid-feedback">사용 가능한 전화번호</div>
			<div class="invalid-feedback">-를 포함해서 입력해주세요</div>
		</div>
		<div class="form-group">
			<label>이메일</label>	
			<input class="form-control" type="text" id="email" name="owner_email" placeholder="이메일" required="required" maxlength="80">
			<div class="valid-feedback">사용 가능한 이메일</div>
			<div class="invalid-feedback">유효한 이메일을 입력해주세요</div>
		</div>
		<div class="form-group">	
			<label>매장코드</label><br>
			<select class="form-control" name="store_code" required="required">
				<option value="">선택하기</option>
				
				<c:forEach var="i" begin="0" end="${fn:length(store_code)}" step="1">
				<c:if test="${store_code[i] ne null}">
					<option value="${store_code[i]}">${store_code[i]}</option>
				</c:if>
				<c:if test="${store_code[0] eq null}">
					<option value="">선택가능한 매장이 없습니다.</option>
				</c:if>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<label>계약시작일</label>
			<input class="form-control" type="date" name="owner_start" required="required">
		</div>
			
		<input type="hidden" name="admin_id" value="${loginDto.admin_id}">
		<input type="hidden" name="loc_code" value="${loginDto.loc_code}">
			
		
		<div>	
			<input style="width: 123px;" class="btn btn-outline-success" type="submit" value="등록 완료" >
			<input style="width: 123px;" class="btn btn-outline-warning" type="button" value="취소" onclick="regiCancel()">
		</div>
	</fieldset>
	</form>

	</div>
</div>

</body>
<!-- 유효성 검사: 비밀번호, 확인, 전화번호, 이메일 -->
<script type="text/javascript" src="js/validationChk.js"></script>
<script type="text/javascript">

$(function(){
	
	// 회원등록 폼 작성시 아이디 유효값 확인 필요(공백포함여부, 중복여부)
	$("#id").keyup(function(){
		var inputLen = $(this).val().length;
		var id = $(this).val();
		// 업주 로그인 시 사업자 번호 숫자 3자리-2자리-5자리
		var ownRegex = /^\d{3}-\d{2}-\d{5}$/;
		
		// 공백여부 검사
		if(id.indexOf(" ") != -1){
			$("#id").attr("class","form-control is-invalid");
			$("#idchkVal").val("0");
		} else if(inputLen>11 && id.match(ownRegex)!=null){
			// ajax를 통한 아이디 중복검사 
			$.ajax({
				url : "./ownIdChk.do",
				type : "post",
				data : "owner_id="+id,
				async : true,
				success : function(msg){
// 					alert(msg.substr(0,5)); // 사용 가능 / 사용 불가
					if(msg.substr(0,5)=="사용 가능"){
						$("#id").attr("class","form-control is-valid");
						$("#idchkVal").val("1");						
					} else{
						$("#id").attr("class","form-control is-invalid");
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


//등록 취소 버튼클릭 시 실행할 함수
var regiCancel = function(){
	self.close();
};
	
	
//회원 등록 버튼 클릭 시 실행할 함수
var regiChk = function(){
	var idVal = document.getElementById("idchkVal").value;
	var pwVal = document.getElementById("pwchkVal").value;
	var phnVal = document.getElementById("phnchkVal").value;
	var emailVal = document.getElementById("emailchkVal").value;
	
	
// 	alert(idVal +":" + pwVal +":"+phnVal +":" +emailVal);
	if(idVal=='1' && pwVal=='1' && phnVal=='1' && emailVal=='1'){
		var ownerData = $("#owRegiForm").serialize();
		
		$.ajax({
			url: "./ownerRegi.do",
			type: "post",
			data: ownerData,
			async: false,
			success: function(rst){
				opener.parent.location.reload();
				regiCancel()
			},
			error: function(rst){
				alert("등록이 실패되었습니다.");
				return false;
			}
		});
		
		
	} else {
		swal("업주 등록 실패", "유효값을 확인해주세요", "error");
		return false;
	}
	return false;
};



</script>
</html>