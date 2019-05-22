<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	function modiStore() {
		var code = document.getElementById("storeCode").value;
		location.href = "./storeModiForm.do?store_code="+code;
	}
	
	function delStore(){
		var code = document.getElementById("storeCode").value;
		location.href = "./delStore.do?store_code="+code;
	}
</script>
<body>
	<div id="container">
		<input type="hidden" id="storeCode" value="${dto.store_code}">
		<table>
			<thead>
				<tr>
					<th colspan="2">${dto.store_name}</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th>매장코드</th>
					<td>${dto.store_code}</td>
				</tr>
				<tr>
					<th>지역코드</th>
					<td>${dto.loc_code}</td>
				</tr>
				<tr>
					<th>매장전화번호</th>
					<td>${dto.store_phone}</td>
				</tr>
				<tr>
					<th>매장주소</th>
					<td>${dto.store_address}</td>
				</tr>
				<tr>
					<th>사번</th>
					<td>${dto.admin_id}</td>
				</tr>
			</tbody>
		</table>
		<div>
			<input type="button" value="수정" onclick="modiStore()">
			<input type="button" value="삭제" onclick="delStore()">
			<input type="button" value="닫기" onclick="close()">
		</div>
	</div>
</body>
</html>