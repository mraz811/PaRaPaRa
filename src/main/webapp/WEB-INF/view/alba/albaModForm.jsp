<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아르바이트 정보 수정</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<style type="text/css">
.form-control{
	width: 300px;
}
.fullCtrl{
	width: 320px;
	margin: 10px auto;
}
.albawriteform{
	width: 300px;
	font-size: 30px; 
	background-color: RGB(21,140,186); 
	color:white; 
	font-weight: bold; 
	padding: 0px 10px; 
	text-align: center;
	border-radius: 0.2em;
}
</style>
</head>
<body>
<div id="container">
	<!-- 아르바이트 수정 form -->
	<div class="fullCtrl">
	<form name="albaFrm" action="#" method="post">
	<input type="hidden" name="alba_seq" value="${alba.alba_seq}">
	  <fieldset>
	  	<p class="albawriteform">아르바이트 수정</p>
		<div class="form-group">
      		<label for="alba_name">이름</label>
			<input type="text" class="form-control" id="alba_name" name="alba_name" placeholder="이름" required="required" maxlength="20" value="${alba.alba_name}">
		</div>
		<div class="form-group">		
			<label for="alba_phone">전화번호</label>	
			<input type="text" class="form-control" name="alba_phone" placeholder="연락처" required="required" maxlength="20" value="${alba.alba_phone}">
		</div>
		<div class="form-group">
			<label for="alba_address">주소</label>
			<input type="text" class="form-control" name="alba_address" placeholder="주소" required="required" maxlength="100" value="${alba.alba_address}">
		</div>
		<div class="form-group">	
			<label>시급</label>	
			<input type="text" class="form-control" name="alba_timesal" placeholder="시급" required="required" maxlength="10" value="${alba.alba_timesal}">
			<div class="valid-feedback"></div>
			<div class="invalid-feedback">숫자만 입력해주세요</div>
		</div>
		<div class="form-group">
			<label>은행명</label>
			<input type="text" class="form-control" name="alba_bank" placeholder="은행명" required="required" maxlength="10" value="${alba.alba_bank}">
		</div>
		<div class="form-group">
			<label>계좌번호</label>	
			<input type="text" class="form-control" name="alba_account" placeholder="계좌번호" required="required" maxlength="20" value="${alba.alba_account}">
		</div>
		<div class="form-group">
			<label>근무시작일</label>	
			<input type="date" class="form-control" name="alba_regdate" placeholder="근무시작일" readonly="readonly" value="${fn:substring(alba.alba_regdate,0,10)}">
		</div>
		<div>	
			<input style="width: 123px; margin-left: 17px; " class="btn btn-outline-success" type="button" value="수　정" onclick="modiChk()">
			<input style="width: 123px; margin-left: 17px; " class="btn btn-outline-warning" type="button" value="취　소" onclick="modiCancel()">
		</div>
		</fieldset>
	</form>
	</div>

</div>
</body>
<script type="text/javascript">
var modiCancel = function(){
	self.close();
}

var modiChk = function(){
	var albadata = $("form").serialize();
	
	$.ajax({
		url : "./albaModi.do",
		type: "post",
		data: albadata,
		async: false,
		success: function(){
			swal({
				title: "수정 완료",
				text: "아르바이트 수정이 완료되었습니다",
				type: "success"
			},
			function(){
				opener.parent.location.reload();
				modiCancel();
			});
		}, error: function(){
			swal("수정 실패", "아르바이트 정보 수정이 실패되었습니다.","error");
		}
	});
	
}

</script>
</html>