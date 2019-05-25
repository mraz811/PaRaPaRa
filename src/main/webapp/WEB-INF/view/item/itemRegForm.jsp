<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<script type="text/javascript" src="./js/bootstrap.js"></script>
<body>
	<div id="container">
		<form action="#" id="frm" method="post">
			<fieldset>
			
<%-- 			<input type="hidden" name="loc_code" value="${loginDto.loc_code}"> --%>
<%-- 				<input type="hidden" naSme="admin_id" value="${loginDto.admin_id}"> --%>
				
				<div class="form-group">
					<label>품목명</label>
					<input class="form-control" type="text" placeholder="ex)양념소스" required="required" name="item_name">
				</div>
				<div class="form-group">
					<label>가격</label>
					<input class="form-control" type="text" name="item_price" placeholder="ex)10000" required="required">
				</div>
				<div align="right">
					<input type="button" style="width: 123px;" class="btn btn-outline-success" value="등록" onclick="regItem()">
					<input type="button" style="width: 123px;" class="btn btn-outline-warning" value="취소" onclick="regiCasncel()">
				</div>
			</fieldset>
		</form>
	</div>
<script type="text/javascript">
	function regItem() {
		$.ajax({
			url :"./addItem.do",
			type: "post",
			async:true,
			data:$("#frm").serialize(),
			dataType:"json",
			success:function(msg){
// 				alert(msg.isc);
				swal({
					title: "등록 완료", 
					text: "품목 등록이 완료되었습니다", 
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