<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PaRaPaRa</title>
</head>
<body>
<div id="container">
<%@include file="./header.jsp" %>
	<div class="bodyFrame">
	<div class="bodyfixed">
		<div class="oneDepth">
			<!-- oneDepth에 적힐 내용이 들어감 ex)매장관리 -->
			
			
		</div> <!-- div class=oneDepth -->
		<div class="twoDepth">
			<!-- onDepth 안에 있는 twoDepth가 들어감 ex)1depth가 매장관리일 경우 a 태그에 적힐 내용은 일정관리, 재고, 발주 등  -->
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" >PaRaPaRa</a>
  				</li>
			</ul>
			<div class="tab-content">
				<!-- 각자 내용들.. -->
			
			</div> <!-- div class=tab-content -->
			
		</div> <!-- div class twoDepth -->
	</div> <!-- div class=bodyfixed -->
	</div> <!-- div class=bodyFrame -->
<%@include file="./footer.jsp" %>
</div> <!-- div id=container -->
</body>
</html>