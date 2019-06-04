<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>품목 수정</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<script type="text/javascript" src="./js/bootstrap.js"></script>
<style type="text/css">
.form-control{
	width: 300px;
}

.fullCtrl{
	width: 320px;
	margin: 10px auto;
}
.writeform{
	width: 300px;
	font-size: 30px; 
	background-color: RGB(21,140,186); 
	color:white; 
	font-weight: bold; 
	padding: 0px 10px; 
	text-align: center;
	border-radius: 0.2em;
}
</style>
</head>
<body>
	<div id="container">
	<div class="fullCtrl">
		<form action="#" id="frm" method="post">
			<fieldset>
			<p class="writeform">품목 수정</p>
				<input type="hidden" name="item_seq" value="${dto.item_seq}">
<%-- 			<input type="hidden" name="loc_code" value="${loginDto.loc_code}"> --%>
<%-- 				<input type="hidden" naSme="admin_id" value="${loginDto.admin_id}"> --%>
				
				<div class="form-group">
					<label>품목명</label>
					<input class="form-control" type="text" value="${dto.item_name}" required="required" name="item_name">
				</div>
				<div class="form-group">
					<label>가격</label>
					<input class="form-control" type="text" name="item_price" value="${dto.item_price}" required="required">
				</div>
				<div align="left" style="margin-top: 30px;">
					<input type="button" style="width: 148px;" class="btn btn-outline-success" value="수정" onclick="modiItem()">
					<input type="button" style="width: 148px;" class="btn btn-outline-warning" value="취소" onclick="modiCancel()">
				</div>
			</fieldset>
		</form>
	</div>
	</div>
<script type="text/javascript">
	function modiItem() {
		$.ajax({
			url :"./itemModi.do",
			type: "post",
			async:true,
			data:$("#frm").serialize(),
// 			dataType:"json",
			success:function(msg){
// 				alert(msg.isc);
				swal({
					title: "수정 완료", 
					text: "품목 수정이 완료되었습니다", 
					type: "success"
				},
				function(){ 
					opener.parent.location.reload();
					modiCancel();
				});
			},error:function(){
// 				alert("실패");
				swal("수정 에러", "수정 중 문제가 발생하였습니다.", "error");
			}
		});
	}
	var modiCancel = function(){
		self.close();
	}
	
</script>
</body>
</html>