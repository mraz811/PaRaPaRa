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

<!-- 업주 등록 form -->
	<form id="owRegiForm" action="./ownerRegi.do" method="post">
		<div>
			<span>사업자 등록번호</span><br>
			<input type="text" name="owner_id" placeholder="ex)111-11-11111" required="required" maxlength="12">
			
			<br><span>비밀번호</span><br>	
			<input type="password" name="owner_pw" placeholder="비밀번호" required="required" maxlength="12">
		
			<br><span>비밀번호 확인</span><br>	
			<input type="password" name="pwChk" placeholder="비밀번호 확인" required="required" maxlength="12">
		
			<br><span>업주명</span><br>	
			<input type="text" name="owner_name" placeholder="이름" required="required" maxlength="20">
			
			<br><span>전화번호</span><br>	
			<input type="text" name="owner_phone" placeholder="연락처" required="required" maxlength="20">
		
			<br><span>이메일</span><br>	
			<input type="text" name="owner_email" placeholder="이메일" required="required" maxlength="80">
			
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
			
		</div>
		
		<div>	
			<input type="submit" value="등록 완료" onsubmit="return adRegiChk()">
			<input type="button" value="취소" onclick="regiCancel()">
		</div>
	</form>


</div>

<%@include file="../footer.jsp" %>

</body>
<script type="text/javascript">

// 업주 등록 완료 시 유효값 검사
var adRegiChk = function(){
	
	
	
	return true;
}

// 등록 취소 시
var regiCancel = function(){
	
	
}





</script>
</html>