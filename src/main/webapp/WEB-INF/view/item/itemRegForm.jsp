<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>품목 등록</title>
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
	
		<form action="#" id="frm" method="post" onsubmit="return regItem()">
			<fieldset>
			<p class="writeform">품목 등록</p>
			
<%-- 			<input type="hidden" name="loc_code" value="${loginDto.loc_code}"> --%>
<%-- 				<input type="hidden" naSme="admin_id" value="${loginDto.admin_id}"> --%>
				<input type="hidden" value="0" id="nameChkVal">
				<div class="form-group">
					<label>품목명</label>
					<input class="form-control" type="text" id="item_name" placeholder="ex)양념소스" required="required" name="item_name">
					<div class="valid-feedback">사용 가능한 품목명</div>
					<div class="invalid-feedback">사용 불가능한 품목명</div>
				</div>
				<div class="form-group">
					<label>가격</label>
					<input class="form-control" type="text" id="item_price" name="item_price" onkeyup="numberOnly()" placeholder="ex)10000, 숫자만 입력하세요" required="required">
				</div>
				<div align="left" style="margin-top: 30px;">
					<input type="submit" style="width: 148px;" class="btn btn-outline-success" value="등록" >
					<input type="button" style="width: 148px;" class="btn btn-outline-warning" value="취소" onclick="regiCancel()">
				</div>
			</fieldset>
		</form>
	</div>
	</div>
<script type="text/javascript">

	// 품목 등록 시 중복되는 품목명을 판단해주기 위하여
	$(function() {
		$("#item_name").keyup(function() {
			var iName = $(this).val(); // 품목명 등록 input tag의 값
// 			alert(iName);
			$.ajax({
				url : "./itemValid.do",
				type : "post",
				data : "item_name="+iName,
				async : true,
				success:function(msg){
					if(msg.substr(0,5)=="사용 가능"){
						$("#item_name").attr("class","form-control is-valid");
						$("#nameChkVal").val("1");						
					} else{
						$("#item_name").attr("class","form-control is-invalid");
						$("#nameChkVal").val("0");
					}
				},error : function() {
					alert("what?");
				}
			});
			
		});
		
		
	});
		
	function numberOnly(){
		var iPrice = $("#item_price").val();
		var keyValue = event.keyCode;
		if( ((keyValue >= 65) && (keyValue <= 90)) ||  ((keyValue >= 106) && (keyValue <= 111)) || ((keyValue >= 186) && (keyValue <= 222)) || keyValue==32 ){
			swal("등록 에러", "숫자만 입력해주세요.", "error");
			$("#item_price").val(iPrice.substring(0,iPrice.length-1));
		}
	}
	var regItem = function () {
		var chkVal = $("#nameChkVal").val();
		if(chkVal == '1'){
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
					alert("실패");
					return false;
				}
			});
			
		}else{
			swal("등록 에러", "등록 중 문제가 발생하였습니다.", "error");
			return false;
		}
		return false;
		
	}
	var regiCancel = function(){
		self.close();
	}
	

	
</script>
</body>
</html>