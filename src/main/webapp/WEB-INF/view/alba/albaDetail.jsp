<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아르바이트 상세 조회</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<style type="text/css">
th{
	width: 100px;
	text-align: center;
}
table {
	margin: 50px auto; 
}
</style>
</head>
<body>
<div id="container">
	<div class="fullCtrl">
	<fieldset>
		<table class="table table-hover" >
			<tr>
				<th class="table-primary">이름</th>
				<td>${alba.alba_name}</td>
				<th class="table-primary">시급</th>
				<td>${alba.alba_timesal}원</td>
			</tr>
			<tr>
				<th class="table-primary">전화</th>
				<td>${alba.alba_phone}</td>
				<th class="table-primary">근무시작일</th>
				<td>${fn:substring(alba.alba_regdate,0,10)}</td>
			</tr>
			<tr>
				<th class="table-primary">주소</th>
				<td colspan="3">${alba.alba_address}</td>
			</tr>
			<tr>
				<th class="table-primary">은행</th>
				<td>${alba.alba_bank}</td>
				<th class="table-primary">계좌번호</th>
				<td>${alba.alba_account}</td>
			</tr>
		</table>
		<div align="center" style="margin-top: 50px;">
			<input style="width: 75px;" type="button" class="btn btn-outline-warning" value="닫기" onclick="self.close()">
		</div>
	</fieldset>
	</div>

</div>
</body>
</html>