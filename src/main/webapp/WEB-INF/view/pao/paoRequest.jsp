<%@page import="java.util.List"%>
<%@page import="com.happy.para.dto.PaoDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>업주 발주 신청</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<script type="text/javascript">

	var cnt = 1;	// 발주 품목에서 번호를 매겨줄 count

	// 발주 품목의 수량을 빼주는 이벤트
	function minus(el) {
		//var su = document.getElementsByName("pi_qty")[0].value;
		//document.getElementsByName("pi_qty")[0].value = new Number(su) - 1;
		//alert($('.downBtn').index(el));
		var idx = $('.downBtn').index(el);
		var su = $(".pi_qty:eq("+idx+")").val();
		su = $(".pi_qty:eq("+idx+")").val(su*1-1); 
		
		// 합계금액을 변경
		var price = Number(document.getElementsByName("pi_price")[idx].value);
		var sumPrice = Number(document.getElementsByName("sumPi_price")[idx].value);
		
		sumPrice -= price;
		
		document.getElementsByName("sumPi_price")[idx].value = sumPrice;
		
		
		// 발주 품목에서 - 버튼을 누를 때 총 수량 값 변경
		var totalPiQty = Number($('input[name=totalPiQty]').val());
		totalPiQty -= 1;
		$('input[name=totalPiQty]').val(totalPiQty);
		
		// 발주 품목에서 - 버튼을 누를 때 총 금액 값 변경
		var totalPiPrice = Number($('input[name=totalPiPrice]').val());
		totalPiPrice -= Number(price);
		$('input[name=totalPiPrice]').val(totalPiPrice);
		
	}
	
	
	// 발주 품목의 수량을 늘려주는 이벤트
	function plus(el) {
		//var su = document.getElementsByName("pi_qty")[0].value;
		//document.getElementsByName("pi_qty")[0].value = new Number(su) + 1;
		//alert($('.upBtn').index(el));
		var idx = $('.upBtn').index(el);
		var su = $(".pi_qty:eq("+idx+")").val();
		su = $(".pi_qty:eq("+idx+")").val(su*1+1); 
		
		// 합계금액을 변경
		var price = Number(document.getElementsByName("pi_price")[idx].value);
		var sumPrice = Number(document.getElementsByName("sumPi_price")[idx].value);
		
		sumPrice += price;
		
		document.getElementsByName("sumPi_price")[idx].value = sumPrice;
		
		
		// 발주 품목에서 + 버튼을 누를 때 총 수량 값 변경
		var totalPiQty = Number($('input[name=totalPiQty]').val());
		totalPiQty += 1;
		$('input[name=totalPiQty]').val(totalPiQty);
		
		// 발주 품목에서 + 버튼을 누를 때 총 금액 값 변경
		var totalPiPrice = Number($('input[name=totalPiPrice]').val());
		totalPiPrice += Number(price);
		$('input[name=totalPiPrice]').val(totalPiPrice);
	}

	// 발주 품목에서 수량 text에 값을 입력할 때
	function changeQty(el) {
		var idx = $('.pi_qty').index(el);
        var piQty = $(el).val();
        
        //alert(piQty.length);
        
        // 키를 눌렀을 때 해당 key의 코드를 받아옴 
        var keyValue = event.keyCode;
        
        // 숫자, BackSpace(8), delete(46)를 입력했을 때
        if( ((keyValue >= 96) && (keyValue <= 105)) || ((keyValue >= 48) && (keyValue <= 57)) || (keyValue==8 || keyValue==46) ){
        	// 합계금액을 변경
    		var price = Number(document.getElementsByName("pi_price")[idx].value);
    		var sumPiPrice = Number(document.getElementsByName("sumPi_price")[idx].value);
    		
    		sumPiPrice = price * piQty;	// 가격 * 현재 입력되어 있는 수량으로 합계금액을 계산
    		
    		document.getElementsByName("sumPi_price")[idx].value = sumPiPrice;
    		
    		// 발주 품목에 있는 모든 수량과 금액을 종합 함
    		var piQtyList = document.getElementsByName("pi_qty");
    		var piSumPriceList = document.getElementsByName("sumPi_price");
    		var sumQty = 0;
    		var sumPrice = 0;
    		
    		for (var i = 0; i < piQtyList.length; i++) {
    			sumQty += Number(piQtyList[i].value);
    			sumPrice += Number(piSumPriceList[i].value);
			}
    		//alert(sumQty);
    		 
    		// 발주 품목 수량 text에 직접 입력할 때 총 수량 값 변경
    		var totalPiQty = Number($('input[name=totalPiQty]').val());
    		totalPiQty = Number(sumQty);
    		$('input[name=totalPiQty]').val(totalPiQty);
    		
    		
    		// 발주 품목 수량 text에 직접 입력할 때 총 금액 값 변경
    		var totalPiPrice = Number($('input[name=totalPiPrice]').val());
    		totalPiPrice = Number(sumPrice);
    		$('input[name=totalPiPrice]').val(totalPiPrice);
    		
        }else if(keyValue==13){	// 엔터를 입력했을 때
        	alert("숫자만 입력해주세요!!");
        }else if( keyValue==59 || keyValue==61 || keyValue==110 || keyValue==111 || ((keyValue >= 188) && (keyValue <= 192)) || ((keyValue >= 219) && (keyValue <= 222)) ){	// 문자 및 특수문자, 스페이스바를 입력했을 때
        	alert("숫자만 입력해주세요!!");
    		$(el).val(piQty.substring(0, piQty.length-1));	// 잘못 입력한 값을 지워줌
        }
        
	}
	
	// 재고 목록에서 해당 품목 추가를 했을 때 재고 목록에서는 사라지고 발주 품목에 추가되는 이벤트 
	function addStock(info) {
		// 재고 정보에서 해당 <tr>의 id에 대한 정보, 재고명과 가격 정보를 가져와 split으로 자름
		var stockInfo = info.split(",");
		
		var stockTr_id = stockInfo[0];	// 해당 <tr>의 id
		var piSeq = cnt++;	// 발주품목 번호
		var piName = stockInfo[1];;	// 발주품목 품목명
		var piQty = 1;	// 발주품목 수량
		var piPrice = stockInfo[2];	// 발주품목 가격
		var sumPiPrice = stockInfo[2];	// 발주품목 합계금액
	
		var newTr = document.createElement("tr");	// 새로운 <tr> 태그 생성
		
		newTr.setAttribute("id", stockTr_id);	// 새로 생성된 <tr>에 id를 똑같이 설정 
		
		var pbody = document.getElementById("pbody");
		
		// 발주 품목에 추가한 재고의 <tr>을 hidden
		var currentLine = document.getElementById(stockTr_id);
		currentLine.style.display = "none";
		
		// 발주 품목의 목록이 없을 때 나타나는 <tr>을 hidden
		var noListTr = document.getElementById("noPiList");
		noListTr.style.display = "none";
		
		pbody.appendChild(newTr).innerHTML = "<td>" +
												"<input type='text' class='txt' name='pi_seq' value='"+piSeq+"' readonly='readonly'>" +
											  "</td>" +
											  "<td>" +
												"<input type='text' class='txt' name='pi_name' value='"+piName+"' readonly='readonly'>" +
											  "</td>" +
											  "<td>" +
												"<input type='button' class='downBtn' value='-' onclick='minus(this)'>" +
												"<input type='text' name='pi_qty' class='pi_qty' value='"+piQty+"' onkeyup='changeQty(this)'>" +
												"<input type='button' class='upBtn' value='+' onclick='plus(this)'>" +
											  "</td>" +
											  "<td>" +
												"<input type='text' class='txt' name='pi_price' value='"+piPrice+"' readonly='readonly'>" +
											  "</td>" +
											 "<td>" +
												"<input type='text' class='txt' name='sumPi_price' value='"+sumPiPrice+"' readonly='readonly'>" +
											  "</td>" +
											  "<td>" +
											  	"<input type='button' class='delBtn' value='삭제'  onclick='delStock(this, \""+stockTr_id+"\")'>" +
											  	//"<input type='button' class='delBtn' value='삭제'  onclick='delStock(\""+stockTr_id+"\")'>" +
											  "</td>";
		
		
		// 재고 목록에서 추가 버튼을 누를 때 총 수량 값 변경
		var totalPiQty = Number($('input[name=totalPiQty]').val());
		totalPiQty += 1;
		$('input[name=totalPiQty]').val(totalPiQty);
		
		// 재고 목록에서 추가 버튼을 누를 때 총 금액 값 변경
		var totalPiPrice = Number($('input[name=totalPiPrice]').val());
		totalPiPrice += Number(piPrice);
		$('input[name=totalPiPrice]').val(totalPiPrice);
	}
	
	// 발주 품목에서 해당 품목 삭제를 했을 때 발주 품목에서는 사라지고 재고 목록에 추가되는 이벤트
	function delStock(line, lineId) {
		//alert(lineId);
		
		
		// 현재 줄의 인덱스 번호
		var idx = $('.delBtn').index(line);
		//alert(idx);
		var piQty = document.getElementsByName("pi_qty")[idx].value;
		var sumPiPrice = document.getElementsByName("sumPi_price")[idx].value;
		//alert(piQty);
		
		// 발주 품목에서 삭제 버튼을 누를 때 총 수량 값 변경
		var totalPiQty = Number($('input[name=totalPiQty]').val());
		totalPiQty -= piQty;
		$('input[name=totalPiQty]').val(totalPiQty);
		
		// 발주 품목에서 삭제 버튼을 누를 때 총 금액 값 변경
		var totalPiPrice = Number($('input[name=totalPiPrice]').val());
		totalPiPrice -= Number(sumPiPrice);
		$('input[name=totalPiPrice]').val(totalPiPrice);
		
		
		// 발주 품목에서 해당 품목을 삭제
		var pbody = document.getElementById("pbody");
		var selectTr = line.parentNode.parentNode;
		pbody.removeChild(selectTr);
		
		// 재고 목록에 삭제한 품목을 다시 보이게 함
		var currentLine = document.getElementById(lineId);
		currentLine.style.display = "";
		cnt--;
		
		// 발주 품목의 번호를 다시 계산하기 위해 pi_seq들을 가져옴
		var piSeq = document.getElementsByName("pi_seq");
		
		// 발주 품목의 목록이 없을 때 대한 처리
		if(isNaN(piSeq)){
			// 발주 품목의 목록이 없을 때 나타나는 <tr>을 보이게 함 
			var noListTr = document.getElementById("noPiList");
			noListTr.style.display = "";
		}else{
			// 발주 품목의 번호 다시 계산하여 value에 입력
			for (var i = 0; i < cnt; i++) {
				piSeq[i].value = (i+1);
			}
		}
	}
	
</script>
<style type="text/css">
	table{
		border: 1px solid black;
		border-collapse: collapse;
		width: 800px;
		
	}
	thead, tbody, tr, th, td>input{
		border: 1px solid black;
		border-collapse: collapse;
		text-align: center;
	}
	tbody{
		height: 110px;
	}
	tr{
		height: 26px;
	}
	th{
		background-color: skyblue;
	}
	#resultDiv #totalCal th{
		background-color: orange;
	}
	#btn{
		width: 15px;
		height: 15px;
	}
	.txt{
		border: none;
	}
	/* 
	thead{
		position: absolute;
		display: block; 
	} */
	/* tbody{display: block; overflow-y:scroll; float:left; width:880px; max-height:110px;} */

</style>
</head>
<body>
	${stockLists}
	
	<div id="stockList">
		<input type='hidden' id='store_code' value='${stockLists[0].store_code}'>
		<h3>■ 재고</h3>
		<table>
			<thead>
				<tr>
					<th>재고번호</th><th>재고명</th><th>수량</th><th>가격</th><th>추가</th>
				</tr>
			</thead>
			<tbody id="sbody">		
				<c:choose>
					<c:when test="${empty stockLists}">
						<tr>
							<td id="noList" colspan="5">-- 작성된 글이 없습니다 --</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="dto" items="${stockLists}" varStatus="status">
							<tr id="stockLine${status.count}">
								<td>
									<input type="text" class="txt" name="stock_seq" value="${dto.stock_seq}" readonly="readonly">
								</td>
								<td>
									<input type="text" class="txt" name="stock_name" value="${dto.stock_name}" readonly="readonly">
								</td>
								<td>
									<input type="text" class="txt" name="stock_seq" value="${dto.stock_qty}" readonly="readonly">
								</td>
								<td>
									<input type="text" class="txt" name="stock_seq" value="${dto.itemDto.item_price}" readonly="readonly">
								</td>
								<td>
									<input type="button" class="addBtn" value="추가" onclick="addStock('stockLine${status.count},${dto.stock_name},${dto.itemDto.item_price}')">
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	
	<div id="paoList">
		<h3>■ 발주 품목</h3>
		<table>
			<thead>
				<tr>
					<th>번호</th><th>품목명</th><th>수량</th><th>가격</th><th>합계금액</th><th>삭제</th>
				</tr>
			</thead>
			<tbody id="pbody" style="height: 110px; width: 880px; overflow: scroll;">		
				<tr id="noPiList">
					<td colspan='6'>발주 품목이 없습니다.</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div id="resultDiv">
		<table id="totalCal">
			<tr>
				<th colspan="2">합계</th>
				<th>수량</th>
				<th>
					<input type="text" class="txt" name="totalPiQty" value="0" readonly="readonly" style="text-align: right;">개
				</th>
				<th>총금액</th>
				<th>
					<input type="text" class="txt" name="totalPiPrice" value="0" readonly="readonly" style="text-align: right;">원
				</th>
			</tr>
		</table>
	</div>
</body>
</html>