<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아르바이트 등록</title>
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<style type="text/css">
.form-control{
	width: 200px;
}
</style>
</head>
<body>
<div id="container">
	<!-- 아르바이트 등록 form -->
	<form action="./albaRegi.do" method="post" onsubmit="return regiChk()">
	  <fieldset>
    	<legend>아르바이트 정보 등록</legend>
		<div class="form-group">
		
      		<label for="alba_name">이름</label>
			<input type="text" class="form-control" id="alba_name" name="alba_name" placeholder="이름" required="required" maxlength="20">
			<br>		
			<label for="alba_phone">전화번호</label>	
			<input type="text" class="form-control" name="alba_phone" placeholder="연락처" required="required" maxlength="20">
			<br>
			<label for="alba_address">주소</label>
			<input type="text" class="form-control" name="alba_address" placeholder="주소" required="required" maxlength="100">
			<br>	
			<label>시급</label>	
			<input type="text" class="form-control" name="alba_timesal" placeholder="시급" required="required" maxlength="10">
			<br>
			<label>은행명</label>
			<input type="text" class="form-control" name="alba_bank" placeholder="은행명" required="required" maxlength="10">
			<br>
			<label>계좌번호</label>	
			<input type="text" class="form-control" name="alba_account" placeholder="계좌번호" required="required" maxlength="20">
			<br>
			<label>근무시작일</label>	
			<input type="date" class="form-control" name="alba_regdate" placeholder="근무시작일" required="required">
			
		</div>
		<div>	
			<input class="btn btn-outline-success" type="submit" value="등록 완료">
			<input class="btn btn-outline-warning" type="button" value="취소" onclick="regiCancel()">
		</div>
		</fieldset>
	</form>


</div>
</body>
<script type="text/javascript">

//등록 취소 버튼클릭 시 실행할 함수
var regiCancel = function(){
	location.href="./selAlbaList.do";
};

var regiChk = function(){
	return true;
}

</script>
</html>