<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#chatList{
		margin-left: 300px;
		border: 2px solid black;
		width: 420px;
		height: 395px;
		overflow: auto;
	}
	.chatTarget{
		margin-left: 12px;
		margin-top: 10px;
		height: 70px;
		width: 380px;		
		border: 1px solid blue;
		text-align: center;
		
	}
	.chatClick{
		margin-top: 18px;
		font-size: x-large;
		
	}
</style>
</head>
<body>
<div id="container">
<%@include file="../header.jsp" %>
	<div class="bodyFrame">
	<div class="bodyfixed">
		<div class="oneDepth">
			<!-- oneDepth에 적힐 내용이 들어감 ex)매장관리 -->
			<p>채팅</p>
			
		</div> <!-- div class=oneDepth -->
		<div class="twoDepth">
			<!-- onDepth 안에 있는 twoDepth가 들어감 ex)1depth가 매장관리일 경우 a 태그에 적힐 내용은 일정관리, 재고, 발주 등  -->
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" style="border: 1px solid rgb(21,140,186);" href="#home"><strong>채팅목록</strong></a>
  				</li>
			</ul>
			<div class="tab-content">
				<div id="chatList">
					<c:if test="${loginDto.auth eq 'A'}">
						<c:forEach items="${lists}" var="list">
							<div class="chatTarget">	
								<div class="chatClick">
									<a href="./socketOpen.do?id=${list.owner_id}&auth=${loginDto.auth}&store_code=${list.store_code}">${list.owner_id}</a>
								</div>								
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${loginDto.auth eq 'U'}">
						<div class="chatTarget">
							<div class="chatClick">
								<a class="chatClick" href="./socketOpen.do?id=${adminDto.admin_id}&auth=${loginDto.auth}">${adminDto.admin_id}</a>
							</div>
						</div>
					</c:if>
				</div>
			</div> <!-- div class=tab-content -->
		</div> <!-- div class twoDepth -->
	</div> <!-- div class=bodyfixed -->
	</div> <!-- div class=bodyFrame -->
<%@include file="../footer.jsp" %>
</div>
</body>
</html>