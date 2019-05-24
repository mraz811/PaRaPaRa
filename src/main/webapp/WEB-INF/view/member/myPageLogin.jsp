<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지 비밀번호 확인</title>
<style type="text/css">
#tab-content{
	width: 255px;
	margin: 100px auto;
	position:relative;
}

</style>
</head>
<body>

<div id="container">
<%@include file="../header.jsp" %>
	<div class="bodyFrame">
	<div class="bodyfixed">
		<div class="oneDepth">
			
		</div>
		<div class="twoDepth">
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#home">비밀번호 확인</a>
  				</li>
			</ul>
			
			<div id="tab-content" class="tab-content">
		
			<!-- 관리자/담당자 -->
			<c:if test="${loginDto.auth eq 'A' || loginDto.auth eq 'S'}">
				<form action="./toMypage.do" method="post">
					<input type="hidden" name="auth" value="${loginDto.auth}">
					<input type="hidden" name="id" value="${loginDto.admin_id}">
				<div class="form-group">
<!-- 					<h2>아이디</h2> -->
<%-- 					${loginDto.admin_id}<br> --%>
					<h2>비밀번호 확인</h2>
					<input class="form-control" style="width: 250px;" type="password" id="inputPw" name="pw" placeholder="비밀번호를 입력하세요" required="required" >
				</div>
				<div>
					<input style="width: 123px;" class="btn btn-outline-success" type="submit" value="확　인">
					<input style="width: 123px;" class="btn btn-outline-warning" type="button" value="취　소" onclick="backToMain()">
				</div>
				</form>
			</c:if>
					
			<c:if test="${loginDto.auth eq 'U'}">
				
				<form action="./toMypage.do" method="post">
					<input type="hidden" name="auth" value="${loginDto.auth}">
					<input type="hidden" name="id" value="${loginDto.owner_id}">
				<div class="form-group">
					<h2>비밀번호 확인</h2>
					<input class="form-control" type="password" id="inputPw" name="pw" placeholder="비밀번호를 입력하세요" required="required" >
				</div>
				<div class="form-group">
					<input style="width: 123px;" class="btn btn-outline-success" type="submit" value="확　인">
					<input style="width: 123px;" class="btn btn-outline-warning" type="button" value="취　소" onclick="backToMain()">
				</div>
				</form>
			
			</c:if>


			</div><!-- tab-content -->
			
		</div> <!-- two depth -->
	</div><!-- bodyfixed -->
	</div><!-- bodyFrame -->

<%@include file="../footer.jsp" %>
</div>


</body>
<script type="text/javascript">
var backToMain = function(){
	location.href="./main.do" ;
};
</script>
</html>