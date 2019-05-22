<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아르바이트 등록</title>
</head>
<body>

<div id="container">
	
	<!-- 아르바이트 등록 form -->
	<form action="./albaRegi.do" method="post" onsubmit="return regiChk()">
		<div>
			<span>이름</span><br>
			<input type="text" name="alba_name" placeholder="이름" required="required" maxlength="20">
			
			<br><span>전화번호</span><br>	
			<input type="text" name="alba_phone" placeholder="연락처" required="required" maxlength="20">

			<br><span>주소</span><br>	
			<input type="text" name="alba_address" placeholder="주소" required="required" maxlength="100">
		
			<br><span>시급</span><br>	
			<input type="text" name="alba_timesal" placeholder="시급" required="required" maxlength="10">
		
			<br><span>은행</span><br>	
			<input type="text" name="alba_bank" placeholder="은행명" required="required" maxlength="10">
			
			<br><span>계좌번호</span><br>	
			<input type="text" name="alba_account" placeholder="계좌번호" required="required" maxlength="20">
		
			<br><span>근무시작일</span><br>	
			<input type="date" name="alba_regdate" placeholder="근무시작일" required="required">
			
		</div>
		<div>	
			<input type="submit" value="등록 완료">
			<input type="button" value="취소" onclick="regiCancel()">
		</div>
		
	</form>


</div>

</body>
<script type="text/javascript">

//등록 취소 버튼클릭 시 실행할 함수
var regiCancel = function(){
	location.href="./selAlbaList.do";
};

var regiChk = function(){
	
}

</script>
</html>