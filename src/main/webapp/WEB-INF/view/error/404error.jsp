<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>404 Error</title>
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<style type="text/css">
body {
	height: 610px;
}
img{
	width: 808px;
	height: 540px;
}
</style>
</head>
<body>
<div style="width:870px; margin: 10px auto;">
	<input style="width: 808px;" type="button" class="btn btn-outline-primary" value="메인 페이지로" onclick="toLoginForm()">
	<a href="./loginForm.do">
		<img alt="404error" src="./imgs/404_page_cover.jpg">
	</a>
</div>
</body>
<script type="text/javascript">
var toLoginForm = function(){
	location.href ="./loginForm.do";
}

</script>
</html>