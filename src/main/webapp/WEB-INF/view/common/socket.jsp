<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.dragDropDiv{
		width: 1020px;
		height: 435px;
		position: relative;
	}
	#chatMsgBox{
		border: 1px solid black;
		width: 350px;
		height: 365px;
		overflow-y: auto;
		left: 0px;
		margin: 0px;
		position: relative;
	}
	#sendBox{
		width: 350px;
		height: 35px;
		margin-top: 10px;
	}
	.clear {
		clear: both;
	}
	#chat{
		width: 210px;
		height: 25px;
		margin-left: 10px;
		margin-right: 10px;
	}
	.form-me{
		position: relative;
		padding: 10px 20px;
		color: white;
		background: #0B93F6;
		border-radius: 25px;
		float: right;
	}
	.from-me:before {
		content: "";
		position: absolute;
		z-index: -1;
		bottom: -2px;
		right: -7px;
		height: 20px;
		border-right: 20px solid #0B93F6;
		border-bottom-left-radius: 16px 14px;
		-webkit-transform: translate(0, -2px);
	}
	.form-other{
		position: relative;
		padding: 10px 20px;
		background: #E5E5EA;
		border-radius: 25px;
		color: black;
		float: left;
	}
	.enter{
		
	}
/* 	#chat_btn{ */
/* 		width: 50px; */
/* 		height: 25px; */
/* 	} */
</style>
</head>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<!-- <script type="text/javascript" src="./js/file.js"></script> -->
<script type="text/javascript" src="./js/socket.js"></script>
<body>
<div id="container">
<%@include file="../header.jsp" %>
	<div class="bodyFrame">
		<div class="bodyfixed">
			<div class="oneDepth">
				<!-- oneDepth에 적힐 내용이 들어감 ex)매장관리 -->
				<p class="text-primary" style="font-size: 40px;">채팅</p>
			</div> <!-- div class=oneDepth -->
			<div class="twoDepth">
				<ul class="nav nav-tabs">
	  				<li class="nav-item">
	    				<a class="nav-link" data-toggle="tab" href="#home">채팅목록</a>
	  				</li>
				</ul>
				<div class="tab-content">
					<div class="dragDropDiv">
						<c:if test="${loginDto.auth eq 'A'}">
							<input type="hidden" id="nickName" value="${loginDto.admin_id}" style="display: none;">
							<input type="hidden" id="sessionId" value="${loginDto.admin_id}" style="display: none;">
						</c:if>
						<c:if test="${loginDto.auth eq 'U'}">
							<input type="hidden" id="nickName" value="${loginDto.owner_id}" style="display: none;">
							<input type="hidden" id="sessionId" value="${loginDto.owner_id}" style="display: none;">
						</c:if>
<!-- 						<input type="text" value="hello"> -->
						<input type="hidden" id="targetId" value="${target}">
						<input type="hidden" id="auth" value="${loginDto.auth}">
						<input type="hidden" id="storeCode" value="${store_code}">
						<div id="receive_msg" class='chattingDiv' style="border: 1px">
							<section id="chatMsgBox">
								${chatDto.chat_content}
							</section>
						</div>
						<div id="sendBox">
							<input type="text" id="chat" onKeypress="if(event.keyCode==13) $('#chat_btn').click();" />
							<input type="button" id="chat_btn" class="btn btn-outline-success" value="입력" />
							<input type="button"  class="btn btn-outline-warning" value="나가기" onclick="disconnect()">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>