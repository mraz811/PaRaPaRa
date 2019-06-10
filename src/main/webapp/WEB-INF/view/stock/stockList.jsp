<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>재고 목록</title>
<style type="text/css">

.table {
	margin: 0px;
}

#tableHeader{
	margin: 0px;
}

#stock_bottom{
	position: relative;
	margin: 10px;
}

#searchBtn{
	width: 300px;
	left: 400px;
	position: absolute;
}

#regBtn{
	right: 10px;
	position: absolute;
}

</style>


</head>

<body>
<%-- ${lists} --%>
	<div id="container">
		<%@include file="../header.jsp"%>
		<div class="bodyFrame">
			<div class="bodyfixed">
				<div class="oneDepth">
					<!-- oneDepth에 적힐 내용이 들어감 ex)매장관리 -->
				<p>매장관리</p>
				</div>
				<!-- div class=oneDepth -->
				<div class="twoDepth">
					<!-- onDepth 안에 있는 twoDepth가 들어감 ex)1depth가 매장관리일 경우 a 태그에 적힐 내용은 일정관리, 재고, 발주 등  -->
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link" data-toggle="tab" id="calendar" href="#" >Calendar</a></li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab" id="pao" href="#">발주</a></li>
						<li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#home" style="border: 1px solid rgb(21,140,186);"><strong>재고</strong></a></li>
					</ul>
					<div class="tab-content">

						<script type="text/javascript">
							$(function() {
								$("#calendar").click(function() {
									location.href = "./selCal.do";
								});
								$("#pao").click(function() {
									location.href = "./selPaoList.do";
								});
							});
						</script>

						<form action="#" method="post">
						<input type="hidden" name="store_code" value="${store_code}" />

							<table id="tableHeader" class="table">
								<thead>
									<tr class="table-primary">
										<th style="width:100px; text-align: center;">재고 번호</th>
										<th style="width:500px; padding-left:85px;">재고명</th>
										<th style="padding-left:60px;">재고수량</th>
										<th style="padding-left:25px; width:150px;">삭제</th>
									</tr>
								</thead>
							</table>

							<div style="overflow-y: auto; height: 315px;">
							<table class="table table-hover">
								<tbody>
									<c:forEach var="dto" items="${lists}" varStatus="vs">
											<tr>
												<td style="width:75px; text-align: center;">
												${vs.count}</td>
												<td style="width:400px; padding-left:85px;">
													<input type="hidden" name="Slists[${vs.count}].stock_seq" value="${dto.stock_seq}" />
													<input type="hidden" name="Slists[${vs.count}].stock_name" value="${dto.stock_name}" readonly="readonly"
														style="border:none; background-color: none;" />
													${dto.stock_name}
													<c:if test="${dto.store_code eq NULL}">
														<a style="color:red;">NEW</a>
													</c:if>
												</td>
												<td style="width:220px;">
													<input type="number" min="0" class="stockQty" name="Slists[${vs.count}].stock_qty" value="${dto.stock_qty}" readonly="readonly"  onkeyup="changeQty(this)"/>
												</td>
												<c:if test="${dto.item_delflag eq NULL}">
													<td style="width:100px; padding: 7px 0px 0px 12px;">
														<input type="button" class="btn btn-outline-warning" onclick="location.href='./delStock.do?stock_seq=${dto.stock_seq}&store_code=${store_code}'" value="삭제" />
													</td>
												</c:if>
												<c:if test="${dto.item_delflag ne NULL}">
													<td style="width:100px;"></td>
												</c:if>
											</tr>										
									</c:forEach>
								</tbody>
							</table>
							</div>

							<div id="stock_bottom">
								<div id="searchBtn" class="form-group row">
									<input style="width: 200px; margin-right: 5px;" class="form-control" type="text" id="searchStock" onkeypress="if(event.keyCode==13) $('#searchStockBtn').click()" width="400px;">
									<input type="button" id="searchStockBtn" class="btn btn-outline-primary" value="검색" onclick="searchStockList()">
								</div>
								<div id="regBtn">
									<input type="button" class="btn btn-outline-primary" id="btn" value="전체품목" onclick="selStockList()">
									<input type="button" class="btn btn-outline-success" id="modifyQty" value="수정 하기" onclick="modifyBtn()" />
								</div>
							</div>

						</form>

					</div>
					<!-- div class=tab-content -->
				</div>
				<!-- div class twoDepth -->
			</div>
			<!-- div class=bodyfixed -->
		</div>
		<!-- div class=bodyFrame -->
		<%@include file="../footer.jsp"%>
	</div>

<script type="text/javascript">
	function modifyBtn() {
		var modiQty = document.getElementById('modifyQty');

		if (modiQty.value == "수정 하기") {
			modiQty.value = "수정 완료";
			var aty = document.getElementsByName("stock_qty");
			var qty = document.querySelectorAll("input[name*=qty]");
			// 		alert(aty.length);

			var a = document.getElementsByName("Slists[" + 1 + "].stock_qty")[0];
			
			for (var i = 1; i < qty.length + 1; i++) {
				document.getElementsByName("Slists[" + i + "].stock_qty")[0].removeAttribute("readonly");
 				document.getElementsByName("Slists[" + i + "].stock_qty")[0].style.border = "2px solid #ff0000";
			}
		} else {
			if(confirm("입력한 수량으로 수정하시겠습니까?")){
				modiQty.value == "수정 하기"
				var frm = document.forms[0];
				frm.action = "./stockModi.do";
				frm.submit();
				swal("수정이 완료되었습니다.");
			}
		}
	}
	
	function changeQty(el) {
		var idx = $('.stockQty').index(el);
        var stockQty = $(el).val();
        
        //alert(stockQty.length);
        
        // 키를 눌렀을 때 해당 key의 코드를 받아옴 
        var keyValue = event.keyCode;
        
        // 숫자, BackSpace(8), delete(46)를 입력했을 때
       	if( ((keyValue >= 65) && (keyValue <= 90)) ||  ((keyValue >= 106) && (keyValue <= 111)) || ((keyValue >= 186) && (keyValue <= 222)) || keyValue==32 ){	// 문자 및 특수문자, 스페이스바를 입력했을 때
        	swal("", "숫자만 입력해주세요.");
    		$(el).val(stockQty.substring(0, stockQty.length-1));
        }
        if(stockQty > 99999){
        	swal("", "입력 범위를 넘었습니다.");
    		$(el).val(99999);
        }
	}
	
	var enterSearch = function(){
		if (window.event.keyCode == 13) {
			searchStockList();
		}
	}
	
	var searchStockList = function(){
		var searchVal = document.getElementById("searchStock").value.trim();
		var store_code = document.getElementsByName("store_code")[0].value;
// 		alert(store_code);
// 		alert("trim 한 searchVal : " + searchVal);
		$.ajax({
			post : "get",
			url : "./searchStock.do",
			data : {"stock_name": searchVal, "store_code":store_code},
			dataType : "json",
			async : false,
			success: function (data) {
// 				alert(typeof data.count);
				if(data.count == 0){
					swal({
						title: "", 
						text: "검색된 항목이 존재하지 않습니다.", 
						type: "warning"
					},function(){
						selStockList();
					});
				}else{
					var htmlTable = "";
					var cnt = 1;
					$.each(data, function(key,value){
						if(key=="lists"){
							$.each(value,function(key, val){
// 								alert("val.item_delflag > "+val.item_delflag);
// 								alert("val.stock_delflag > "+val.stock_delflag);
								if(val.item_delflag == 'Y'){
								htmlTable += 	"<tr>"+
												"<td style='width:75px; text-align: center;'>"+cnt+"</td>"+
												"<td style='width:400px; padding-left:85px;'>"+
												"<input type='hidden' name='Slists["+cnt+"].stock_seq' value='"+val.stock_seq+"' />"+
												"<input type='hidden' name='Slists["+cnt+"].stock_name' value='"+val.stock_name+"' readonly='readonly'"+
												"		style='border:none; background-color: none;' />"+val.stock_name+"</td>"+
												"<td style='width:220px;'>"+
												"	<input type='number' min='0' class='stockQty' name='Slists["+cnt+"].stock_qty' value='"+val.stock_qty+"' readonly='readonly'  onkeyup='changeQty(this)'/>"+
												"</td>"+
												"<td style='width:100px; padding: 7px 0px 0px 12px;'>"+
												"<input type='button' class='btn btn-outline-warning' onclick='location.href=\"./delStock.do?stock_seq=${dto.stock_seq}&store_code=${store_code}\"' value='삭제' />"+
												"</td></tr>";
							}else{
								htmlTable += 	"<tr>"+
												"<td style='width:75px; text-align: center;'>"+cnt+"</td>"+
												"<td style='width:400px; padding-left:85px;'>"+
												"<input type='hidden' name='Slists["+cnt+"].stock_seq' value='"+val.stock_seq+"' />"+
												"<input type='hidden' name='Slists["+cnt+"].stock_name' value='"+val.stock_name+"' readonly='readonly'"+
												"		style='border:none; background-color: none;' />"+val.stock_name+"</td>"+
												"<td style='width:220px;'>"+
												"	<input type='number' min='0' class='stockQty' name='Slists["+cnt+"].stock_qty' value='"+val.stock_qty+"' readonly='readonly'  onkeyup='changeQty(this)'/>"+
												"</td>"+
												"<td style='width:100px;'></td></tr>";
							}
								cnt = cnt+1;
							}); 
						} 
					}); 
					$(".table > tbody").html(htmlTable);
				}
			},
	        error: function (data) {
	        	swal("조회 에러", "조회 중 문제가 발생하였습니다.", "error");
	        }
		});
	}
	
	function selStockList(){
		var store_code = document.getElementsByName("store_code")[0].value;
		location.href="./selStockOne.do?store_code="+store_code;
	}
	
</script>
	
</body>
</html>