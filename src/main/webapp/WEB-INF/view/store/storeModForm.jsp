<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매장 수정</title>
</head>
<body>
<%@include file="../header.jsp" %>
<div id="container">
	<div class="fullCtrl">
		<form action="./storeModi.do" method="post">
			<input type="hidden" name="store_code" value="${dto.store_code}">
			${nameListJson}
			<p class="writeform">매장 수정</p>
			<table>
				<thead>
					<tr>
						<th colspan="2">${dto.store_name}</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>매장명</th>
						<td><input type="text" name="store_name" value="${dto.store_name}"/></td>
					</tr>
					<tr>
						<th>지역코드</th>
						<td>${dto.loc_code}</td>
					</tr>
					<tr>
						<th>매장전화번호</th>
						<td><input type="text" name="store_phone" value="${dto.store_phone}"/></td>
					</tr>
					<tr>
						<th>매장주소</th>
						<td><input type="text" name="store_address" value="${dto.store_address}" /></td>
					</tr>
					<tr>
						<th>사번</th>
						<td><input type="text" name="admin_id" value="${dto.admin_id}"/></td>
					</tr>
				</tbody>
			</table>
			<div>
				<input type="submit" value="수정완료">
				<input type="button" value="닫기" onclick="close()">
			</div>
		</form>
	</div>
</div>
<%@include file="../footer.jsp" %>
</body>
</html>