<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
로그인 세션: ${loginDto}

<c:if test="${loginDto.auth eq 'A'}">
<input type="button" value="담당자마페" onclick="chkMyPage(${loginDto.admin_id})">
</c:if>
<c:if test="${loginDto.auth eq 'U'}">
<input type="button" value="업주마페" onclick="chkMyPage(${loginDto.admin_id})">
</c:if>






</body>
<script type="text/javascript">

var chkMyPage = function(){
		
	location.href="./pwCheckForm.do";
	
}

</script>
</html>