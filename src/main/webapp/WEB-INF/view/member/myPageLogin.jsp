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
<!-- 				<h4>비밀번호 확인</h4> -->
				<hr>
				<div class="form-group">
					<input class="form-control" style="width: 250px;" readonly="readonly" id="inputId" name="id" value="${loginDto.admin_id}"> 
				</div>
				<div class="form-group">
					<input onkeyup="enterkey();" class="form-control" style="width: 250px;" type="password" id="inputPw" name="pw" placeholder="비밀번호를 입력하세요" required="required" >
				</div>
				<div style="margin-top: 20px;">
					<input style="width: 123px;" class="btn btn-outline-success" type="button" value="확　인" onclick="pwCheck()">
					<input style="width: 123px;" class="btn btn-outline-warning" type="button" value="취　소" onclick="backToMain()">
				</div>
				<hr>
				</form>
			</c:if>
					
			<c:if test="${loginDto.auth eq 'U'}">
				
				<form action="./toMypage.do" method="post">
				<input type="hidden" name="auth" value="${loginDto.auth}">
				<hr>
				<div class="form-group">
					<input class="form-control" style="width: 250px;" readonly="readonly" id="inputId" name="id" value="${loginDto.owner_id}"> 
				</div>
				<div class="form-group">
					<input onkeyup="enterkey();" class="form-control" style="width: 250px;" type="password" id="inputPw" name="pw" placeholder="비밀번호를 입력하세요" required="required" >
				</div>
				<div style="margin-top: 20px;">
					<input style="width: 123px;" class="btn btn-outline-success" type="button" value="확　인" onclick="pwCheck()">
					<input style="width: 123px;" class="btn btn-outline-warning" type="button" value="취　소" onclick="backToMain()">
				</div>
				<hr>
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

function enterkey() {
    if (window.event.keyCode == 13) {
         // 엔터키가 눌렸을 때 실행할 내용
         pwCheck();
    }
}

var pwCheck = function(){
	var id = $("#inputId").val();
	var pw = $("#inputPw").val();
	
	var loginForm = $("form");
	var logAjax = $("form").serialize();
// 	alert(logAjax);
	
	if(pw==""){
		swal("비밀번호 불일치", "비밀번호를 확인해 주세요");
	} else{
		$.ajax({
			url: "./loginChk.do",
			type: "post",
			data: logAjax,
			success: function(msg){
				if(msg=="성공"){
					loginForm.submit();
				}else{
					swal("비밀번호 불일치", "비밀번호를 확인해 주세요");
				}
				
			}, error: function(msg){
				swal("비밀번호 불일치", "비밀번호를 확인해 주세요");
			}
		});
		
		
	}
	
	
}

</script>
</html>