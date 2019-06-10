<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 확인</title>
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>

<style type="text/css">
#bodyform{
	padding-top: 10%;
	width: 300px;
	margin: 0px auto;
}
.form-control{
	width: 300px;
}
</style>
</head>
<body>
<form action="#" method="get" id="chkForm">
	<div class="form-group" id="bodyform">
		<input type="hidden" name="id" value="${loginDto. owner_id}">
		<input type="hidden" name="auth" value="${loginDto.auth}">
		<input onkeyup="enterkey();" class="form-control" type="password" id="inputPw" name="pw" placeholder="비밀번호를 입력하세요" required="required" >
	</div>
</form>
	<div class="form-group" style="width: 180px; margin: 5px auto;">
		<input class="btn btn-outline-primary" type="button" value="메인으로" onclick="toChkPw()">
		<input class="btn btn-secondary" type="button" value="　취소　" onclick="self.close()">
	</div>
</body>
<script type="text/javascript">
var toChkPw = function(){
	var chkForm = $("#chkForm");
	var logAjax = $("#chkForm").serialize();
	
	$.ajax({
		url : "./loginChk.do",
		type: "post",
		data: logAjax,
		success: function(msg){
			if(msg=="성공"){
// 				chkForm.submit();
				opener.parent.location.href="./main.do";
				self.close();
			}else{
				swal("", "비밀번호를 확인해 주세요");
			}
				
		}, error: function(msg){
			swal("", "비밀번호를 확인해 주세요");
		}
	});
		
	
}
</script>
</html>