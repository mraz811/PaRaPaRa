<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<body>
	<div id="container">
<%-- 	<%@include file="../header.jsp" %> --%>
<!-- 		<form action="./regiStore.do" method="post"> -->
		<form action="#" id="frm" method="post">
			<input type="hidden" name="store_code" value="${store_code}">
			<input type="hidden" name="loc_code" value="${loginDto.loc_code}">
			<input type="hidden" name="admin_id" value="${loginDto.admin_id}">
			<table>
				<thead>
					<tr>
						<th colspan="2">매장등록</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>매장코드</th>
						<td>${store_code}</td>
					</tr>
					<tr>
						<th>지역코드</th>
						<td>${loginDto.loc_code}</td>
					</tr>
					<tr>
						<th>매장전화번호</th>
						<td><input type="text" name="store_phone">
					</tr>
					<tr>
						<th>매장명</th>
						<td><input type="text" name="store_name">
					</tr>
					<tr>
						<th>매장주소</th>
						<td><input type="text" name="store_address">
					</tr>
				</tbody>
			</table>
			
			<div>
				<input type="button" value="등록" onclick="regStore()">
				<input type="button" value="취소" onclick="close()">
			</div>
		</form>
		
	</div>
<script type="text/javascript">
	function regStore() {
		$.ajax({
			url :"./regiStore.do",
			type: "post",
			async:true,
			data:$("#frm").serialize(),
			dataType:"json",
			success:function(msg){
				alert(msg.isc);
			},error:function(){
				alert("실패");
			}
		});
	}
	
</script>
</body>
</html>