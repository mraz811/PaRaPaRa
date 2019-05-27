<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아르바이트 등록</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>

<style type="text/css">
.form-control{
	width: 250px;
}
.fullCtrl{
	width: 420px;
	margin-left: 20%;
	margin-top: 20px;
	margin-bottom: 40px;
}
</style>
</head>
<body>
<div id="container">
	<!-- 아르바이트 등록 form -->
	<div class="fullCtrl">
	<form name="albaFrm" action="#" method="post">
	  <fieldset>
		<div class="form-group">
      		<label for="alba_name">이름</label>
			<input type="text" class="form-control" id="alba_name" name="alba_name" placeholder="이름" required="required" maxlength="20">
		</div>
		<div class="form-group">		
			<label for="alba_phone">전화번호</label>	
			<input type="text" class="form-control" name="alba_phone" placeholder="연락처" required="required" maxlength="20">
		</div>
		<div class="form-group">
			<label for="alba_address">주소</label>
			<input type="text" class="form-control" name="alba_address" placeholder="주소" required="required" maxlength="100">
		</div>
		<div class="form-group">	
			<label>시급</label>	
			<input type="text" class="form-control" name="alba_timesal" placeholder="시급" required="required" maxlength="10">
		</div>
		<div class="form-group">
			<label>은행명</label>
			<input type="text" class="form-control" name="alba_bank" placeholder="은행명" required="required" maxlength="10">
		</div>
		<div class="form-group">
			<label>계좌번호</label>	
			<input type="text" class="form-control" name="alba_account" placeholder="계좌번호" required="required" maxlength="20">
		</div>
		<div class="form-group">
			<label>근무시작일</label>	
			<input type="date" class="form-control" name="alba_regdate" placeholder="근무시작일" required="required">
		</div>
		<div>	
			<input style="width: 123px;" class="btn btn-outline-success" type="button" value="등　록" onclick="regiChk()">
			<input style="width: 123px;" class="btn btn-outline-warning" type="button" value="취　소" onclick="regiCancel()">
		</div>
		</fieldset>
	</form>
	</div>

</div>
</body>
<script type="text/javascript">

// 제출 시
function regiChk(){
	var albadata = $("form").serialize();
// 	alert(albadata);
	
	$.ajax({
		url : "./albaRegi.do",
		type: "post",
		data: albadata,
		async: false,
		success: function(){
			swal({
				title: "등록 완료",
				text: "아르바이트 등록이 완료되었습니다",
				type: "success"
			},
			function(){
				opener.parent.location.reload();
				regiCancel();
			});
		}, error: function(){
			swal("등록 실패", "정보를 모두 입력해주세요","error");
		}
	});
}

//등록 취소 버튼클릭 시 실행할 함수
var regiCancel = function(){
	self.close();
};
</script>
</html>