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

#replyDiv {
	position: relative;
}

#modifyQty {
	position: absolute;
	right: 30px;
	bottom : 15px;
}

#tableHeader{
	margin: 0px;
}

</style>
<script type="text/javascript">
	function modifyBtn() {
		var modiQty = document.getElementById('modifyQty');

		if (modiQty.value == "수정 하기") {
			modiQty.value = "수정 완료";
			var aty = document.getElementsByName("stock_qty");
			var qty = document.querySelectorAll("input[name*=qty]");
			// 		alert(aty.length);

			var a = document.getElementsByName("Slists[" + 1 + "].stock_qty")[0];
			alert(a.value);

			for (var i = 1; i < qty.length + 1; i++) {
				document.getElementsByName("Slists[" + i + "].stock_qty")[0].removeAttribute("readonly");
 				document.getElementsByName("Slists[" + i + "].stock_qty")[0].style.border = "2px solid #ff0000";
			}
		} else {
			modiQty.value == "수정 하기"

			var frm = document.forms[0];
			frm.action = "./stockModi.do";
			frm.submit();
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
        	alert("숫자만 입력해주세요.");
    		$(el).val(stockQty.substring(0, stockQty.length-1));
        }
        if(stockQty > 99999){
        	alert("입력 범위를 넘었습니다.");
    		$(el).val(99999);
        	
        }
	}
</script>

</head>
<body>

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
								<tr class="table-primary">
									<th style="width:100px;">재고 번호</th>
									<th style="width:500px; text-align: center;">재고명</th>
									<th>재고수량</th>
									<th>삭제</th>
								</tr>
							</table>

							<div style="overflow-y: auto; height: 315px;">
							<table class="table table-hover">
								<c:forEach var="dto" items="${lists}" varStatus="vs">
										<tr>
											<td style="width:45px; text-align: center;">${vs.count}</td>
											<td style="width:245px; padding-left:120px;">
												<input type="hidden" name="Slists[${vs.count}].stock_seq" value="${dto.stock_seq}" />
												<input type="hidden" name="Slists[${vs.count}].stock_name" value="${dto.stock_name}" readonly="readonly"
													style="border:none; background-color: none;" />
												${dto.stock_name}
											</td>
											<td style="width:220px; padding-left:140px;">
												<input type="number" min="0" class="stockQty" name="Slists[${vs.count}].stock_qty" value="${dto.stock_qty}" readonly="readonly"  onkeyup="changeQty(this)"/>
											</td>
											<c:if test="${empty dto.item_name}">
												<td style="width:100px; padding-left: 0px;">
													<input type="button" class="btn btn-outline-warning" onclick="location.href='./delStock.do?stock_seq=${dto.stock_seq}&store_code=${store_code}'" value="삭제" />
												</td>
											</c:if>
											<c:if test="${!empty dto.item_name}">
												<td style="width:100px;"></td>
											</c:if>
	
										</tr>										
								</c:forEach>
							</table>
							</div>

							<input type="button" class="btn btn-outline-success" id="modifyQty" value="수정 하기" onclick="modifyBtn()" />

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
</body>
</html>