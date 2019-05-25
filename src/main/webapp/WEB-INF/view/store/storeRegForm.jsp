<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매장등록</title>
</head>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<script type="text/javascript" src="./js/bootstrap.js"></script>
<body>
	<div id="container">
<%-- 	<%@include file="../header.jsp" %> --%>
<!-- 		<form action="./regiStore.do" method="post"> -->
		<form action="#" id="frm" method="post">
			<fieldset>
			
<%-- 			<input type="hidden" name="loc_code" value="${loginDto.loc_code}"> --%>
			<input type="hidden" name="admin_id" value="${loginDto.admin_id}">
				
				<div class="form-group">
					<label>매장코드</label>
					<input class="form-control" type="text" readonly="readonly" name="store_code" value="${store_code}">
				</div>
				<div class="form-group">
					<label>지역코드</label>
					<input class="form-control" type="text" readonly="readonly" name="loc_code" value="${loginDto.loc_code}">
				</div>
				<div class="form-group">
					<label>매장전화번호</label>
					<input class="form-control" type="text" id="phone" name="store_phone" placeholder="매장전화번호" required="required" maxlength="20">
				</div>
				<div class="form-group">
					<label>매장명</label>
					<input class="form-control" type="text" id="name" name="store_name" placeholder="매장명" required="required" maxlength="20">
				</div>
				<div class="form-group">
					<label>매장주소</label>
					<input class="form-control" type="text" id="address" name="store_address" placeholder="매장주소" required="required" maxlength="20">
				</div>
				<div align="right">
					<input type="button" style="width: 123px;" class="btn btn-outline-success" value="등록" onclick="regStore()">
					<input type="button" style="width: 123px;" class="btn btn-outline-warning" value="취소" onclick="regiCancel()">
				</div>
			</fieldset>
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
// 				alert(msg.isc);
				swal({
					title: "등록 완료", 
					text: "매장 등록이 완료되었습니다", 
					type: "success"
				},
				function(){ 
					opener.parent.location.reload();
					regiCancel();
				});
			},error:function(){
// 				alert("실패");
				swal("등록 에러", "등록 중 문제가 발생하였습니다.", "error");
			}
		});
	}
	var regiCancel = function(){
		self.close();
	}
	
</script>
</body>
</html>