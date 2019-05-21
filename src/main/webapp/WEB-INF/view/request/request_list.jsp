<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="container">
		<table>
			<c:forEach begin="0" end="${fn:length(requestList)}" items="${requestList}" var="request" varStatus="vs">
				<tr>
					<td>${fn:length(requestList)-vs.count+1}</td>
					<td>${request.menu_name}</td>
					<td>${request.request_menu}</td>
					<td>${request.request_price}</td>
					<td>${request.request_time}</td>
					<td>${request.request_bank}</td>
					<td>${request.request_account}</td>
					<td><input type="button" value="환불"/></td>
				</tr>
			</c:forEach>
		</table>	
	</div>
</body>
</html>