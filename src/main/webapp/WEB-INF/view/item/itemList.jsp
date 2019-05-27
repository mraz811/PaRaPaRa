<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/storeList.css">
</head>
<body>
<div id="container">
<%@include file="../header.jsp" %>
<script type="text/javascript" src="./js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/2depthLink.js"></script>
<!-- <script type="text/javascript" src="./js/itemList.js"></script> -->
	<div class="bodyFrame">
		<div class="bodyfixed">
			<div class="oneDepth">
			<p class="text-primary" style="font-size: 40px;">매장</p>
			</div>
			<div class="twoDepth">
				<ul class="nav nav-tabs">
	  				<li class="nav-item">
	    				<a class="nav-link" data-toggle="tab" onclick="selStoreList()" href="#">매장 정보</a>
	  				</li>
	  				<li class="nav-item">
	    				<a class="nav-link" data-toggle="tab" href="#profile">발주</a>
	  				</li>
	  				<li class="nav-item">
	    				<a class="nav-link active" data-toggle="tab" onclick="selItemList()" href="#">품목</a>
	  				</li>
				</ul>
				<div class="tab-content" style="overflow: auto; height: 300px;">
					<!-- 각자 내용들.. -->
					<table class="table table-hover" >
						<thead>
							<tr class="table-primary">
								<th>품목번호</th>
								<th>품목명</th>
								<th>가격</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${fn:length(itemLists) eq 0}">
									<tr>
										<th colspan="5">등록된 품목이 없습니다.</th>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${itemLists}" var="itemDto">
										<tr>
											<td>${itemDto.item_seq}</td>
											<td>${itemDto.item_name}</td>
											<td>${itemDto.item_price}</td>
											<td><input type="button" class="btn btn-secondary" value="품목수정" onclick="modItem('${itemDto.item_seq}')"></td>
											<td><input type="button" class="btn btn-warning" value="품목삭제" onclick="delItem('${itemDto.item_seq}')"></td>
										</tr>
									</c:forEach>	
								</c:otherwise>
							</c:choose>
						
						</tbody>
					</table>
				</div>
				<div align="right">
					<input type="button" class="btn btn-primary" id="btn" value="품목등록" onclick="regItem()">
				</div>
					
			</div>
		</div>
	</div>
	
<%@include file="../footer.jsp" %>
</div>
<%-- 	${lists} --%>
<%-- 	${storeRow} --%>
<script type="text/javascript">
	var regItem = function() {
		window.open("./regItem.do","_blank","width=400, height=500, left=500, top=200");
// 		location.href = "./regItem.do";
	}
	
	var modItem = function(itemSeq){
		window.open("./itemModiForm.do?item_seq="+itemSeq, "_blank","width=400, height=500, left=500, top=200");
	}
	
	var delItem = function(itemSeq) {
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
				swal("취소", "품목 정보 삭제가 취소 되었습니다.", "error");
//	 			return false;
			} else{ // cancelButtonText
				// 확인 했을 때
				$.ajax({
					type: "post",
					url: "./delItem.do",
					data: "item_seq="+itemSeq,
					dataType : "json",
					async : false,
					success: function (data) {
			        	swal({
							title: "삭제 완료", 
							text: "품목 정보 삭제가 완료되었습니다", 
							type: "success"
						},
						function(){ 
							$.each(data, function(key,value){
								var htmlTable = "";
								
								if(key=="lists"){
									$.each(value,function(key, val){
										htmlTable += 	"<tr>"
															+"<td>"+val.item_seq+"</td>"
															+"<td>"+val.item_name+"</td>"
															+"<td>"+val.item_price+"</td>"
															+"<td><input type='button' class='btn btn-secondary' value='품목등록' onclick='modItem(\""+val.item_seq+"\")'></td>"
															+"<td><input type='button' class='btn btn-warning' value='품목삭제' onclick='delItem(\""+val.item_seq+"\")'></td>"
													+	"</tr>";
										
										
									}); // itemList를 뿌려주기 위한 value의 key val function
								} // key == lists
// 								alert(htmlTable);
								$(".table > tbody").html(htmlTable);
							}); // data의 key value로 나눈 each문
						}); //swal 뒤 function
					},
			        error: function (data) {
			        	swal("삭제 에러", "삭제 중 문제가 발생하였습니다.", "error");
			        }
				});
			}
		});
	}
// 	function selStoreList(){
// 		location.href="./selStoreList.do";
// 	}
// 	function selItemList(){
// 		location.href="./selItemList.do";
// 	}
</script>

</body>
</html>