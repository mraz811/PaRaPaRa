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
		
		</div>
		<div class="twoDepth">
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#home">메인테스트</a>
  				</li>
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#profile">두번째탭</a>
  				</li>
			</ul>
			<div class="tab-content">
				<!-- 각자 내용들.. -->
			
			</div>
			
		</div>
	</div>
	</div>
<%@include file="./footer.jsp" %>
</div>
</body>
</html>