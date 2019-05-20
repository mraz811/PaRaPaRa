<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/file.js"></script>
<script type="text/javascript" src="./js/socket.js"></script>
<body>
	<div class="dragDropDiv">
		<c:if test="${loginDto.auth eq 'A'}">
			<input type="text" id="nickName" value="${loginDto.admin_id}">
			<input type="text" id="sessionId" value="${loginDto.admin_id}">
		</c:if>
		<c:if test="${loginDto.auth eq 'U'}">
			<input type="text" id="nickName" value="${loginDto.owner_id}">
			<input type="text" id="sessionId" value="${loginDto.owner_id}">
		</c:if>
		<input type="text" value="hello">
		<input type="text" id="targetId" value="${target}"><br>
		<input type="text" id="auth" value="${loginDto.auth}"><br>
		<input type="text" id="storeCode" value="${store_code}"><br>
		${chatDto}
		<div id="chatMsgBox" style="border: 1px solid black; width: 490px; height: 490px;" >
			${chatDto.chat_content}
		</div>
		<input type="text" id="chat" style="width: 440px; display: inline;" onKeypress="if(event.keyCode==13) $('#chat_btn').click();" />
		<input type="button" id="chat_btn" value="입력" />
		<input type="button" value="나가기" onclick="disconnect()">
	</div>
</body>
</html>