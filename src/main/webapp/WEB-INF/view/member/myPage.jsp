<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이 페이지</title>
</head>
<body>
<%@include file="../header.jsp" %>
세션 정보: ${loginDto} <br>
담당자 로그인 시 정보: ${aDto} <br>
업주 로그인 시 정보: ${oDto} <br>
<div id="container">

	<c:if test="${loginDto.auth eq 'A'}">
		담당자 정보 가져올 곳
		<div>
			<form action="./adminModi.do" method="post">
				<span>사번</span><br>${loginDto.admin_id}
				<input type="hidden" name="admin_id" placeholder="사번(아이디)" readonly="readonly" value="${loginDto.admin_id}">
				
				<br><span>변경할 새 비밀번호</span><br>	
				<input type="password" id="pw" name="admin_pw" placeholder="변경할 비밀번호"  maxlength="20">
			
				<br><span>비밀번호 확인</span><br>	
				<input type="password" name="pwChk" placeholder="비밀번호 확인"  maxlength="20">
				<input type="password" id="pw" name="admin_pw" placeholder="변경할 비밀번호"  maxlength="12">
			
				<br><span>비밀번호 확인</span><br>	
				<input type="password" name="pwChk" placeholder="비밀번호 확인"  maxlength="12">
			
				<br><span>담당자명</span><br>	
				<input type="text" id="name" name="admin_name" placeholder="이름" required="required" maxlength="20" value="${aDto.admin_name}">
				
				<br><span>전화번호</span><br>	
				<input type="text" id="phone" name="admin_phone" placeholder="연락처" required="required" maxlength="20" value="${aDto.admin_phone}">
			
				<br><span>이메일</span><br>	
				<input type="text" name="admin_email" placeholder="이메일" required="required" maxlength="80" value="${aDto.admin_email}">
				<br>
				<input type="submit" value="수정하기" onsubmit="return confirmModi()">
				<input type="button" value="돌아가기" onclick="backToMain()">
				
			</form>
		</div>		
	</c:if>
	
	<c:if test="${loginDto.auth eq 'U'}">
		업주 정보 가져올 곳
		<div>
			<form action="./ownerModi.do" method="post">
				<span>사업자 번호</span><br>${loginDto.owner_id}
				<input type="hidden" name="owner_seq" value="${loginDto.owner_seq}">
				
				<br><span>변경할 새 비밀번호</span><br>	
				<input type="password" id="pw" name="owner_pw" placeholder="변경할 비밀번호"  maxlength="20">
			
				<br><span>비밀번호 확인</span><br>	
				<input type="password" name="pwChk" placeholder="비밀번호 확인"  maxlength="20">
			
				<br><span>업주명</span><br>	
				<input type="text" id="name" name="owner_name" placeholder="이름" required="required" maxlength="20" value="${oDto.owner_name}">
				
				<br><span>전화번호</span><br>	
				<input type="text" id="phone" name="owner_phone" placeholder="연락처" required="required" maxlength="20" value="${oDto.owner_phone}">
			
				<br><span>이메일</span><br>	
				<input type="text" name="owner_email" placeholder="이메일" required="required" maxlength="80" value="${oDto.owner_email}">
				<br>
				<input type="submit" value="수정하기" onsubmit="return confirmModi()">
				<input type="button" value="돌아가기" onclick="backToMain()">
			</form>
		</div>
	</c:if>

</div>


<%@include file="../footer.jsp" %>
</body>
<script type="text/javascript">

var confirmModi = function(){
	// 수정하기 버튼 클릭시 확인할 내용 추가 할 부분
	// 비밀번호=""이고 세션과 변경값 비교하여 변경한 부분 없을 경우 return false
	// admin / owner 나눠야 할지? id같은거로 주고 할지 밥먹고 생각해 봇ㅂ사.ㄷ
			
	return true;
}

var backToMain = function(){
	location.href="./main.do";
}

</script>



</html>