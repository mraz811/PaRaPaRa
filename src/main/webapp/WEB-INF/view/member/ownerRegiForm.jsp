<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업주 등록 페이지</title>
</head>
<body>
<%@include file="../header.jsp" %>
<div id="container">
세션 : ${loginDto}
${store_code}

	<input type="hidden" value="0" id="idchkVal">
	<input type="hidden" value="0" id="pwchkVal">
	<input type="hidden" value="0" id="phnchkVal">
	<input type="hidden" value="0" id="emailchkVal">
	
	<!-- 업주 등록 form -->
	<form id="owRegiForm" action="./ownerRegi.do" method="post"  onsubmit="return RegiChk()">
		<div>
			<span>사업자 등록번호</span><span id="idRst"></span><br>
			<input type="text" id="id" name="owner_id" placeholder="ex)111-11-11111" required="required" maxlength="12">
			
			<br><span>비밀번호</span><span id="pwRst"></span><br>	
			<input type="password" id="pw" name="owner_pw" placeholder="비밀번호" required="required" maxlength="12">
		
			<br><span>비밀번호 확인</span><span id="pwChkRst"></span><br>	
			<input type="password" id="pwChk" name="pwChk" placeholder="비밀번호 확인" required="required" maxlength="12">
		
			<br><span>업주명</span><br>	
			<input type="text" id="name" name="owner_name" placeholder="이름" required="required" maxlength="20">
			
			<br><span>전화번호</span><span id="phnRst"></span><br>	
			<input type="text" id="phone" name="owner_phone" placeholder="연락처" required="required" maxlength="20">
		
			<br><span>이메일</span><span id="emailRst"></span><br>	
			<input type="text" id="email" name="owner_email" placeholder="이메일" required="required" maxlength="80">
			
			<br><span>매장코드</span><br>
			<select name="store_code" required="required">
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
	
			<br><span>계약시작일</span><br>
			<input type="date" name="owner_start" required="required">
			
			<input type="hidden" name="admin_id" value="${loginDto.admin_id}">
			<input type="hidden" name="loc_code" value="${loginDto.loc_code}">
			
		</div>
		
		<div>	
			<input type="submit" value="등록 완료" >
			<input type="button" value="취소" onclick="regiCancel('${loginDto.loc_code}')">
		</div>
	</form>


</div>

<%@include file="../footer.jsp" %>

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
			$("#idRst").css({'color':'red', 'font-size':'10px'});
			$("#idRst").html("  사용 불가능한 아이디");
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
	
});


//등록 취소 버튼클릭 시 실행할 함수
var regiCancel = function(loc_code){
	location.href="./selOwnerList.do?loc_code="+loc_code;
};
	
	
//회원 등록 버튼 클릭 시 실행할 함수
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
};



</script>
</html>