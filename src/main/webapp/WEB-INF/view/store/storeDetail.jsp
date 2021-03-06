<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매장 상세보기</title>
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
		<form action="#" id="frm" method="post" onsubmit="return modiStore()">
			<input type="hidden" id="nameChkVal" value="0">
			<input type="hidden" id="nameList" value='${nameListJson}'>
			<fieldset id="detailStore">
			<p class="writeform">매장 상세보기</p>
			
				<div class="form-group">
					<label>매장명</label>
					<input class="form-control" type="text" readonly="readonly" onkeyup="nameChk()" id="store_name" name="store_name" value="${dto.store_name}">
					<div class="valid-feedback">사용 가능한 매장명</div>
					<div class="invalid-feedback">사용 불가능한 매장명</div>
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
				<div id="btnDiv" style=" width: 300px; text-align: center; margin-top: 20px;">
					<input style="width:97px;" type="button" class="btn btn-outline-success" id="modiForm" value="수　정" onclick="modiStoreForm()">
					<input style="width:97px;" type="button" class="btn btn-outline-warning" id="deleteStore" value="삭　제" onclick="delStore('${dto.store_code}')">
					<input style="width:97px;" type="button" class="btn btn-secondary" id="close" value="닫　기" onclick="modiCancel()">
				</div>
			</fieldset>
		</form>
		</div>
	</div>
	<script type="text/javascript">
		
		// 매장명 중복 확인
		function nameChk() {
			var orginalName = '${dto.store_name}';
			
			var nameVal = document.getElementById("store_name").value;
	// 		alert("작성한 매장명 : " + )
			var nameList = document.getElementById("nameList").value;
	// 		alert("매장 이름 리스트 : " +  nameList);
			
			if(orginalName == nameVal){
				$("#store_name").attr("class","form-control is-valid");
				$("#nameChkVal").val("1");
			}
			
			if(nameList.indexOf(nameVal)>-1){
				$("#store_name").attr("class","form-control is-invalid");
				$("#nameChkVal").val("0");
			}else{
				$("#store_name").attr("class","form-control is-valid");
				$("#nameChkVal").val("1");
			}
		}
	
		
		// 수정 버튼 클릭 시 수정할 수 있는 부분 readonly 제거
		function modiStoreForm() {
			$("#store_name").removeAttr("readonly");
			$("#store_phone").removeAttr("readonly");
			$("#store_address").removeAttr("readonly");
			$("#admin_id").removeAttr("readonly");
			var orginalName = '${dto.store_name}';
			var nameVal = document.getElementById("store_name").value;
			// 		alert("작성한 매장명 : " + )
			var nameList = document.getElementById("nameList").value;
			// 		alert("매장 이름 리스트 : " +  nameList);
					
			if(orginalName == nameVal){
				$("#store_name").attr("class","form-control is-valid");
				$("#nameChkVal").val("1");
			}
			var htmlBtn = "";
			// 상세조회 버튼 부분 수정으로 변경
			htmlBtn += "<input style='width:97px; margin: 0px 10px;' type='button' class='btn btn-outline-success' id='modi' value='수정완료' onclick='modiStore()'>"
					+ "<input style='width:97px; margin: 0px 10px;' type='button' class='btn btn-secondary' id='close' value='닫　기' onclick='modiCancel()'>";
// 		 	alert(htmlBtn);
			$("#btnDiv").html(htmlBtn);
		}
		
		// 수정 controller로 이동
		function modiStore(){
			var nameChkVal = $("#nameChkVal").val();
//	 		alert("매장명 중복 확인 : " +nameChkVal );
			if(nameChkVal == '1'){
				$.ajax({
					url : "./storeModi.do",
					type : "post",
					async : true,
					data : $("#frm").serialize(),
					dataType:"json",
					success : function(msg){
						swal({
							title: "수정 완료", 
							text: "매장 수정이 완료되었습니다", 
							type: "success"
						},
						function(){ 
							// 수정된 매장 정보를 받아와 바로 확인 가능하게 함
							var htmlDetail = "<p class='writeform'>매장 상세보기</p>";
							htmlDetail += 	"<div class='form-group'>"
												+"<label>매장명</label>"
												+"<input class='form-control' type='text' readonly='readonly' id='store_name' name='store_name' value='"+msg.store_name+"'>"
												+"<div class='valid-feedback'>사용 가능한 매장명</div>"
												+"<div class='invalid-feedback'>사용 불가능한 매장명</div>"
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
											+"<div id='btnDiv' style='width: 300px; text-align: center; margin-top: 20px;'>"
												+"<input style=width:97px;' type='button' class='btn btn-outline-success' id='modiForm' value='수　정' onclick='modiStoreForm()'>"
												+"<input style=width:97px;' type='button' class='btn btn-outline-warning' id='deleteStore' value='삭　제' onclick='delStore(\""+msg.store_code+"\")'>"
												+"<input style=width:97px;' type='button' class='btn btn-secondary' id='close' value='닫　기' onclick='modiCancel()'>"
											+"</div>";
							$("#detailStore").html(htmlDetail);
							
						});
					},error:function(){
	//	 				alert("실패");
						swal("수정 에러", "수정 중 문제가 발생하였습니다.", "error");
						return false;
					}
				});
			}else{
				swal("수정 에러", "수정 중 문제가 발생하였습니다.", "error");
				return false;
				
			}
			return false;
		}
		var modiCancel = function(){
			self.close();
		}
		
		// 삭제 버튼 클릭 시
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