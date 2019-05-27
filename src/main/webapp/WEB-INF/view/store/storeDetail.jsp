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
			<fieldset id="detailStore">
				<div class="form-group">
					<label>매장명</label>
					<input class="form-control" type="text" readonly="readonly" id="store_name" name="store_name" value="${dto.store_name}">
				</div>
				<div class="form-group">
					<label>매장코드</label>
					<input class="form-control" type="text" readonly="readonly" id="store_code" name="store_code" value="${dto.store_code}">
				</div>
				<div class="form-group">
					<label>지역코드</label>
					<input class="form-control" type="text" readonly="readonly" id="loc_code" name="loc_code" value="${dto.loc_code}">
				</div>
				<div class="form-group">
					<label>매장전화번호</label>
					<input class="form-control" type="text" readonly="readonly" id="store_phone" name="store_phone" value="${dto.store_phone}">
				</div>
				<div class="form-group">
					<label>매장주소</label>
					<input class="form-control" type="text" readonly="readonly" id="store_address" name="store_address" value="${dto.store_address}" >
				</div>
				<div class="form-group">
					<label>사번</label>
					<input class="form-control" type="text" readonly="readonly" id="admin_id" name="admin_id" value="${dto.admin_id}">
				</div>
				<div id="btnDiv" align="right">
					<input type="button" class="btn btn-outline-success" id="modiForm" value="수정" onclick="modiStoreForm()">
					<input type="button" class="btn btn-outline-success" id="deleteStore" value="삭제" onclick="delStore('${dto.store_code}')">
					<input type="button" class="btn btn-outline-success" id="close" value="닫기" onclick="modiCancel()">
				</div>
			</fieldset>
		</form>
	</div>
	<script type="text/javascript">
		function modiStoreForm() {
			$("#store_name").removeAttr("readonly");
			$("#store_phone").removeAttr("readonly");
			$("#store_address").removeAttr("readonly");
			$("#admin_id").removeAttr("readonly");
			var htmlBtn = "";
			htmlBtn += "<input type='button' class='btn btn-outline-success' id='modi' value='수정완료' onclick='modiStore()'>"
					+ "<input type='button' class='btn btn-outline-success' id='close' value='닫기' onclick='close()'>";
// 		 	alert(htmlBtn);
			$("#btnDiv").html(htmlBtn);
		}
		function modiStore(){
			$.ajax({
				url : "./storeModi.do",
				type : "post",
				async : true,
				data : $("#frm").serialize(),
				dataType:"json",
				success : function(msg){
// 					alert("왜안되야");
					swal({
						title: "수정 완료", 
						text: "매장 수정이 완료되었습니다", 
						type: "success"
					},
					function(){ 
// 						opener.parent.location.reload();
// 						modiCancel();
						var htmlDetail = "";
						htmlDetail += 	"<div class='form-group'>"
											+"<label>매장명</label>"
											+"<input class='form-control' type='text' readonly='readonly' id='store_name' name='store_name' value='"+msg.store_name+"'>"
										+"</div>"
										+"<div class='form-group'>"
											+"<label>매장코드</label>"
											+"<input class='form-control' type='text' readonly='readonly' id='store_code' name='store_code' value='"+msg.store_code+"'>"
										+"</div>"
										+"<div class='form-group'>"
											+"<label>지역코드</label>"
											+"<input class='form-control' type='text' readonly='readonly' id='loc_code' name='loc_code' value='"+msg.loc_code+"'>"
										+"</div>"
										+"<div class='form-group'>"
											+"<label>매장전화번호</label>"
											+"<input class='form-control' type='text' readonly='readonly' id='store_phone' name='store_phone' value='"+msg.store_phone+"'>"
										+"</div>"
										+"<div class='form-group'>"
											+"<label>매장주소</label>"
											+"<input class='form-control' type='text' readonly='readonly' id='store_address' name='store_address' value='"+msg.store_address+"'>"
										+"</div>"
										+"<div class='form-group'>"
											+"<label>사번</label>"
											+"<input class='form-control' type='text' readonly='readonly' id='admin_id' name='admin_id' value='"+msg.admin_id+"'>"
										+"</div>"
										+"<div id='btnDiv' align='right'>"
											+"<input type='button' class='btn btn-outline-success' id='modiForm' value='수정' onclick='modiStoreForm()'>"
											+"<input type='button' class='btn btn-outline-success' id='deleteStore' value='삭제' onclick='delStore(\""+msg.store_code+"\")'>"
											+"<input type='button' class='btn btn-outline-success' id='close' value='닫기' onclick='modiCancel()'>"
										+"</div>";
						$("#detailStore").html(htmlDetail);
						
					});
				},error:function(){
//	 				alert("실패");
					swal("수정 에러", "수정 중 문제가 발생하였습니다.", "error");
				}
			});
		}
		var modiCancel = function(){
			self.close();
		}
		
		var delStore = function(storeCode){
			swal({
				title: "삭제 확인",
				text: "정말 삭제하시겠습니까?",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "lightgray",
				confirmButtonText: "취 소",
				cancelButtonText: "확 인",
				closeOnConfirm: false,
				closeOnCancel: false
			},
			function(isConfirm){
				if(isConfirm){ // confirmButtonText
					swal("취소", "매장 정보 삭제가 취소 되었습니다.", "error");
//		 			return false;
				} else{ // cancelButtonText
					// 확인 했을 때
					$.ajax({
						type: "post",
						url: "./delStore.do",
						data: "store_code="+storeCode,
						async : false,
						success: function (data) {
				        	swal({
								title: "삭제 완료", 
								text: "매장 정보 삭제가 완료되었습니다", 
								type: "success"
							},
							function(){ 
								opener.parent.location.reload();
								modiCancel();
							});
						},
				        error: function (data) {
				        	swal("삭제 에러", "삭제 중 문제가 발생하였습니다.", "error");
				        }
					});
				}
			});
		}
	</script>
</body>
</html>