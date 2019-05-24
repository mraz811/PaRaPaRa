<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이 페이지</title>
<style type="text/css">
#tab-content{
	width: 1000px;
	height: 400px;
	margin: 10px auto;
	position:relative;
	overflow: auto;
}
.form-group{
	width: 260px;
}
</style>
</head>
<body>
<%-- 세션 정보: ${loginDto} <br> --%>
<%-- 담당자 로그인 시 정보: ${aDto} <br> --%>
<%-- 업주 로그인 시 정보: ${oDto} <br> --%>
<div id="container">
<%@include file="../header.jsp" %>
	<div class="bodyFrame">
	<div class="bodyfixed">
		<div class="oneDepth">
			
		</div>
		<div class="twoDepth">
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#home">마이페이지</a>
  				</li>
			</ul>
			
			<div id="tab-content" class="tab-content">
			
				<input type="hidden" id="pwchkVal" value="1">
				<input type="hidden" id="phnchkVal" value="1">
				<input type="hidden" id="emailchkVal" value="1">
				<c:if test="${loginDto.auth eq 'A' || loginDto.auth eq 'S'}">
<!-- 					담당자 정보 가져올 곳 -->
					<div>
						<form action="./adminModi.do" method="post" onsubmit="return confirmModi()">
						<input type="hidden" name="admin_id" placeholder="사번(아이디)" readonly="readonly" value="${loginDto.admin_id}">
						
						<div class="form-group">
							<label>변경할 새 비밀번호</label>	
							<input class="form-control" type="password" id="pw" name="admin_pw" placeholder="변경할 비밀번호"  maxlength="12">
							<div class="valid-feedback">사용 가능한 비밀번호</div>
							<div class="invalid-feedback">사용 불가능한 비밀번호</div>
						</div>
						<div class="form-group">
							<label>새 비밀번호 확인</label>	
							<input class="form-control" type="password" id="pwChk" name="pwChk" placeholder="비밀번호 확인"  maxlength="12">
							<div class="valid-feedback">비밀번호 일치</div>
							<div class="invalid-feedback">비밀번호 불일치</div>
						</div>
						<div class="form-group">
							<label>담당자명</label>	
							<input class="form-control" type="text" id="name" name="admin_name" placeholder="이름" required="required" maxlength="20" value="${aDto.admin_name}">
						</div>
						<div class="form-group">
							<label>전화번호</label>	
							<input class="form-control" type="text" id="phone" name="admin_phone" placeholder="연락처" required="required" maxlength="20" value="${aDto.admin_phone}">
							<div class="valid-feedback">사용 가능한 전화번호</div>
							<div class="invalid-feedback">-를 포함해서 입력해주세요</div>
						</div>
						<div class="form-group">
							<label>이메일</label>	
							<input class="form-control" type="text" id="email" name="admin_email" placeholder="이메일" required="required" maxlength="80" value="${aDto.admin_email}">
							<div class="valid-feedback">사용 가능한 이메일</div>
							<div class="invalid-feedback">유효한 이메일을 입력해주세요</div>
							<br>
						</div>
						<div>
							<input style="width: 128px;" class="btn btn-outline-success" type="submit" value="수　정" >
							<input style="width: 128px;" class="btn btn-outline-warning" type="button" value="취　소" onclick="backToMain()">
						</div>
						</form>
					</div>		
				</c:if>
				
				<c:if test="${loginDto.auth eq 'U'}">
<!-- 					업주 정보 가져올 곳 -->
					<div>
						<form action="./ownerModi.do" method="post" onsubmit="return confirmModi()">
						<input type="hidden" name="owner_seq" value="${loginDto.owner_seq}">
						<div class="form-group">
							<label>변경할 새 비밀번호</label>	
							<input class="form-control" type="password" id="pw" name="owner_pw" placeholder="변경하지 않을 시 빈칸"  maxlength="12">
							<div class="valid-feedback">사용 가능한 비밀번호</div>
							<div class="invalid-feedback">사용 불가능한 비밀번호</div>
						</div>
						<div class="form-group">
							<label>새 비밀번호 확인</label>
							<input class="form-control" type="password" id="pwChk" name="pwChk" placeholder="비밀번호 확인"  maxlength="12">
							<div class="valid-feedback">비밀번호 일치</div>
							<div class="invalid-feedback">비밀번호 불일치</div>
						</div>
						<div class="form-group">
							<label>업주명</label>
							<input class="form-control" type="text" id="name" name="owner_name" placeholder="이름" required="required" maxlength="20" value="${oDto.owner_name}">
						</div>
						<div class="form-group">	
							<label>전화번호</label>
							<input class="form-control" type="text" id="phone" name="owner_phone" placeholder="연락처" required="required" maxlength="20" value="${oDto.owner_phone}">
							<div class="valid-feedback">사용 가능한 전화번호</div>
							<div class="invalid-feedback">-를 포함해서 입력해주세요</div>
						</div>
						<div class="form-group">
							<label>이메일</label>	
							<input class="form-control" type="text" id="email" name="owner_email" placeholder="이메일" required="required" maxlength="80" value="${oDto.owner_email}">
							<div class="valid-feedback">사용 가능한 이메일</div>
							<div class="invalid-feedback">유효한 이메일을 입력해주세요</div>
						</div>
						<div>
							<input style="width: 128px;" class="btn btn-outline-success" type="submit" value="수　정">
							<input style="width: 128px;" class="btn btn-outline-warning" type="button" value="취　소" onclick="backToMain()">
						</div>
						</form>
					</div>
				</c:if>


			</div><!-- tab-content -->
		</div> <!-- twoDepth -->
	</div><!-- bodyfixed -->
	</div><!-- bodyFrame -->
<%@include file="../footer.jsp" %>
</div>

</body>
<!-- 유효성 검사 -->
<script type="text/javascript" src="js/validationChk.js"></script>
<script type="text/javascript">


var confirmModi = function(){
	
	var pwchkVal = document.getElementById("pwchkVal").value;
	var phnchkVal = document.getElementById("phnchkVal").value;
	var emailchkVal = document.getElementById("emailchkVal").value;
	
	if(pwchkVal=='1' && phnchkVal=='1' && emailchkVal=='1'){
// 		swal({
// 			title: "수정 확인",
// 			text: "정말 수정하시겠습니까?",
// 			showCancelButton: true,
// 			confirmButtonColor: "lightgray",
// 			confirmButtonText: "취 소",
// 			cancelButtonText: "확 인",
// 			closeOnConfirm: false,
// 			closeOnCancel: false
// 		},
// 		function(isConfirm){
// 			if(isConfirm){
// 				swal("취소", "수정이 취소 되었습니다.");
// 				return false;
// 			} else{
// 				return true;
// 			}
// 		});
		return true;
	}
	return false;
};


var backToMain = function(){
	location.href="./main.do" ;
};

</script>

</html>