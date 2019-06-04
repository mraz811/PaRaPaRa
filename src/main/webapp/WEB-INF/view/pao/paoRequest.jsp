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
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./css/header.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<script type="text/javascript" src="./js/bootstrap.js"></script>
<script type="text/javascript">

	// DOM이 생성됐을 때 input의 class가 txt인 요소들을 readonly로 만듦
	$(document).ready(function(){
		$("input[class=txt]").attr("readonly",true);
	});

	// 모든 엘리먼트에 keydown이벤트 추가 후 엔터키 이벤트 제거
	document.addEventListener('keydown', function(event) {
	    if (event.keyCode === 13) {
	        event.preventDefault();
	    }
	}, true);

	var cnt = 1;	// 발주 품목에서 번호를 매겨줄 count

	// 발주 품목의 수량을 빼주는 이벤트
	function minus(el) {
		//var su = document.getElementsByName("pi_qty")[0].value;
		//document.getElementsByName("pi_qty")[0].value = new Number(su) - 1;
		//alert($('.downBtn').index(el));
		var idx = $('.downBtn').index(el);
		var su = $(".pi_qty:eq("+idx+")").val();
		
		// 발주 품목의 수량이 1보다 작을 때
		if(su<=1){
			alert("1개 이상만 발주할 수 있습니다.");
		}else{
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
        if( ((keyValue >= 96) && (keyValue <= 105)) || ((keyValue >= 48) && (keyValue <= 57)) || keyValue==8 || keyValue==46 ){
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
    		
        }if( ((keyValue >= 65) && (keyValue <= 90)) ||  ((keyValue >= 106) && (keyValue <= 111)) || ((keyValue >= 186) && (keyValue <= 222)) || keyValue==32 ){	// 문자 및 특수문자, 스페이스바를 입력했을 때
        	alert("숫자만 입력해주세요!!");
    		$(el).val(piQty.substring(0, piQty.length-1));	// 잘못 입력한 값을 지워줌
        }
        /* 
     	// 숫자, BackSpace(8), delete(46)를 입력했을 때
        if( ((keyValue >= 96) && (keyValue <= 105)) || ((keyValue >= 48) && (keyValue <= 57)) || keyValue==8 || keyValue==46 ){
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
    		
        }
     	if( ((keyValue >= 65) && (keyValue <= 90)) || ((keyValue >= 97) && (keyValue <= 122)) ){	// 문자를 입력했을 때 
     		alert("숫자만 입력해주세요!!");
    		$(el).val(piQty.substring(0, piQty.length-1));	// 잘못 입력한 값을 지워줌
     	}
        if( ((keyValue >= 106) && (keyValue <= 111)) || ((keyValue >= 186) && (keyValue <= 222)) || keyValue==32 ){	// 특수문자, 스페이스바(32)를 입력했을 때
    		alert("숫자만 입력해주세요!!");
			$(el).val(piQty.substring(0, piQty.length-1));	// 잘못 입력한 값을 지워줌
    	}
     	/* 
     	if( ((keyValue >= 33) && (keyValue <= 47)) || ((keyValue >= 106) && (keyValue <= 111)) || ((keyValue >= 58) && (keyValue <= 64)) || ((keyValue >= 91) && (keyValue <= 96)) || ((keyValue >= 123) && (keyValue <= 126)) ){
     		alert("숫자만 입력해주세요!!");
    		$(el).val(piQty.substring(0, piQty.length-1));	// 잘못 입력한 값을 지워줌
     	}
     	 */
     	 
     	
          
	}
	
	// 재고 목록에서 해당 품목 추가를 했을 때 재고 목록에서는 사라지고 발주 품목에 추가되는 이벤트 
	function addStock(info) {
		// 재고 정보에서 해당 <tr>의 id에 대한 정보, 재고명과 가격 정보를 가져와 split으로 자름
		var stockInfo = info.split(",");
		
		var stockTr_id = stockInfo[0];	// 해당 <tr>의 id
		var itemSeq = stockInfo[1];	// 품목번호
		var piSeq = cnt++;	// 발주품목 번호
		var piName = stockInfo[2];;	// 발주품목 품목명
		var piQty = 1;	// 처음 발주품목 수량
		var piPrice = stockInfo[3];	// 발주품목 가격
		var sumPiPrice = stockInfo[3];	// 발주품목 합계금액
		
		var newTr = document.createElement("tr");	// 새로운 <tr> 태그 생성
		newTr.setAttribute("id", stockTr_id);	// 새로 생성된 <tr>에 id를 똑같이 설정 
		
		var pbody = document.getElementById("pbody");
		
		// 발주 품목에 추가한 재고의 <tr>을 hidden
		var currentLine = document.getElementById(stockTr_id);
		currentLine.style.display = "none";
		
		// 발주 품목의 목록이 없을 때 나타나는 <tr>을 hidden
		var noListTr = document.getElementById("noPiList");
		noListTr.style.display = "none";
		
		pbody.appendChild(newTr).innerHTML = "<td width='60px;'>" +
												"<input type='hidden' name='item_seq' value='"+itemSeq+"'>" +
												"<input type='text' class='txt' name='pi_seq' value='"+piSeq+"' style='width:58px;' readonly='readonly'>" +
											  "</td>" +
											  "<td width='300px;'>" +
												"<input type='text' class='txt' name='pi_name' value='"+piName+"' style='width:300px;' readonly='readonly'>" +
											  "</td>" +
											  "<td width='110px;' style='text-algin:center;'>" +
												"<input type='button' class='downBtn' value='-' style='width:25px;' onclick='minus(this)'>" +
												"<input type='text' name='pi_qty' class='pi_qty' value='"+piQty+"' style='width:50px;' onkeyup='changeQty(this)' required='required'>" +
												"<input type='button' class='upBtn' value='+' style='width:25px;' onclick='plus(this)'>" +
											  "</td>" +
											  "<td width='110px;' >" +
												"<input type='text' class='txt' name='pi_price' value='"+piPrice+"' style='width:80px; padding-right:10px; text-align:right;' readonly='readonly'>원" +
											  "</td>" +
											 "<td width='150px;'>" +
												"<input type='text' class='txt' name='sumPi_price' value='"+sumPiPrice+"' style='width:130px; padding-right:10px; text-align:right;' readonly='readonly'>원" +
											  "</td>" +
											  "<td width='50px;'>" +
											  	"<input type='button'class='btn btn-secondary' name='delBtn' value='삭제' style='padding: 2px 5px 2px 5px;' onclick='delStock(this, \""+stockTr_id+"\")'>" +
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

		// 현재 줄의 인덱스 번호
		var idx = $('input[name=delBtn]').index(line);
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
		var piSeq = document.getElementsByName("pi_seq").length;
		
		// 발주 품목의 목록이 없을 때 대한 처리
		if(piSeq == 0){ 
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
	
	// 신청 버튼 클릭 이벤트
	function reqPao() {
		
		var piSeqs = document.getElementsByName("pi_seq").length;	// 발주 품목이 있는지 확인하기 위한 length
		var piNames = document.getElementsByName("pi_name");	// 각각의 발주 품목명
		
		var storeCode = document.getElementById("store_code").value;	// 로그인한 업주의 매장코드

		var itemSeqs = new Array();	// 발주 품목에 추가된 품목 각각의 원래 재고번호를 담을 배열
		var piQtys = new Array();	// 발주 품목에 추가된 품목 각각의 갯수를 담을 배열
		
		for (var i = 0; i < piSeqs; i++) {
			itemSeqs[i] = document.getElementsByName("item_seq")[i].value;	// 재고번호 입력
			piQtys[i] = document.getElementsByName("pi_qty")[i].value;	// 갯수 입력
			
			// 발주 품목 중 수량이 0인 품목이 있을 때
			if(piQtys[i]==0){
				alert((i+1)+"번째 발주 품목 [ "+piNames[i].value+" ]의 수량이 0 입니다.");
				return false;
			}
			// 발주 품목 중 수량이 숫자가 아닌 품목이 있을 때
			if(isNaN(piQtys[i])){
				alert((i+1)+"번째 발주 품목 [ "+piNames[i].value+" ]의 수량을 확인해주세요.");
				return false;
			}
		}
		
		if(piSeqs == 0){	// 발주할 품목이 존재하지 않을 때
			alert("발주하실 품목을 선택해 주세요!");	
			return false;
		}else{
			var isc = confirm("해당 발주를 신청하시겠습니까??");
			
			if(isc){
				
				$.ajax({
					url : "./paoRequest.do",	// 요청 URL
					type : "post",	// 전송 처리 방식
					asyn : false,	// trun 비동기식, false 동기식
					data : "store_code="+storeCode+"&item_seqs="+itemSeqs+"&pi_qtys="+piQtys,	// 서버 전송 파라미터(매장코드, 재고번호, 수량)
					success : function() {
						alert("발주 신청이 완료되었습니다!");
						opener.parent.location.reload();	// 부모 페이지인 paoList.jsp 페이지 새로고침 실행
						window.close();	// 발주 신청 창 닫음
						
					}, error : function() {
						alert("발주 신청이 실패했습니다. 다시 신청해주세요.");
					}
				});
				 
			}
			
		}
		
		return false;
	}
	
	// 닫기 버튼 클릭 이벤트
	function closeWindow() {
		var isc = confirm("해당 발주를 종료하시겠습니까??");
		if(isc){
			window.close();
		}
	}
	
</script>
<style type="text/css">
  	body{
  		margin: 0 auto;
  		padding-top: 20px;
  		padding-left: 20px;
  		width: 840px;
  	}
	thead, tbody, tr, th, td>input{
		border: 1px solid black;
		border-collapse: collapse;
		text-align: center;
	}
	tr{
		height: 26px; 
	}
	hr{
		width: 800px;
		margin: 10px 0px 20px 0px;
	}
	#pageName{
		height: 40px;
	}
	#stockList{
		margin-bottom: 25px;
	}
	
	#resultDiv #totalCal{
		border-style: hidden;
	}
	
	#resultDiv #totalCal th{
		border-style: none;
	}
	 
	#resultDiv #totalCal th .txt{
		background-color: #F6D8CE;
		width: 100px;
		text-align: right;
		padding-right: 10px;
	}
	#btn{
		width: 15px;
		height: 15px;
	}
	.txt{
		border: none;
	}
	input.txt{
		outline: none;
	}
	 
	#stockList thead, #paoList thead{
		position: absolute;
		width: 800px;
	}
	#sbody, #pbody{
		position: relative;
		width: 800px;
		max-height:126px;
		overflow-y:scroll;
		display: block;
		float:left;
		margin-top: 28px;
		height: 126px;
	}
</style>
</head>
<body>
	<div id="pageName">
		<div style="width:200px; height:50px; font-size: 30px; font-weight: bold; float: left;">
			발주 신청
		</div>
	</div>
	<hr>
	
	<h4>▣ 재고</h4>
	<div id="stockList">
		<table>
			<thead>
				<tr class="table-primary">
					<th width="80px;">번호</th><th width="340px;">재고명</th><th width="130px;">수량</th><th colspan="2" width="260px;">가격</th>
				</tr>
			</thead>

			<tbody id="sbody">		
				<c:choose>
					<c:when test="${empty stockLists}">
						<tr>
							<td id="noList" colspan="5" style="text-align: center; color: red; font-weight: bold;"> 재고 목록이 없습니다. </td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="dto" items="${stockLists}" varStatus="status">
							<tr id="stockLine${status.count}">
								<td width="80px;">
									<input type="hidden" class="txt" name="item_seq" value="${dto.itemDto.item_seq}">
									<input type="text" class="txt" name="seq" value="${status.count}" style="width: 80px;">
								</td>
								<td width="340px;"> 
									<input type="text" class="txt" name="stock_name" value="${dto.stock_name}" style="width: 340px;">
								</td>
								<td width="130px;" style="padding-right: 10px;">	
									<input type="text" class="txt" name="stock_seq" value="${dto.stock_qty}" style="width: 50px; text-align: right; margin-right: 10px;">개
								</td>
								<td width="200px;" style="padding-right:20px; text-align: right;">
									<input type="text" class="txt" name="stock_seq" value="${dto.itemDto.item_price}" style="width: 80px; text-align: right; margin-right: 10px;">원
								</td>
								<td width="60px;" style="padding-left: 5px;">
									<input type="button" class='btn btn-secondary' value="추가" style="padding: 2px 5px 2px 5px;" onclick="addStock('stockLine${status.count},${dto.itemDto.item_seq},${dto.stock_name},${dto.itemDto.item_price}')">
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	
	<form action="./paoRequest.do" method="post" onsubmit="return reqPao();">
		<input type='hidden' name='store_code' id='store_code' value='${stockLists[0].store_code}'>
		<h4>▣ 발주 품목</h4>
		<div id="paoList">
			<table>
				<thead>
					<tr class="table-info" height="26px;"> 
						<th width="60px;">번호</th><th width="300px;">품목명</th><th width="110px;">수량</th><th width="130px;">가격</th><th colspan="2" width="200px;">합계금액</th>
					</tr>
				</thead>
				<tbody id="pbody">		
					<tr id="noPiList" style="border-style: none;">
						<td colspan="6" style="width: 800px; text-align: center; color: red; font-weight: bold;">발주 품목이 없습니다.</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div id="resultDiv">
			<table id="totalCal">
				<tr class="table-dark">
					<th width="100px;">합계</th>
					<th width="200px;" style="text-align: right;">수량</th>
					<th width="150px;" style="text-align: right;">
						<input type="text" class="txt" name="totalPiQty" value="0">개
					</th>
					<th colspan="2" width="150px;" style="text-align: right; padding-right:20px;">총금액</th>
					<th width="200px;" style="padding-right: 50px;">
						<input type="text" class="txt" name="totalPiPrice" value="0">원
					</th>
				</tr>
				<tr>
					<th colspan="6" style="text-align: right; padding-top: 20px;">
						<input type="submit" class="btn btn-outline-success" value="신청" style="margin-right: 10px;">
						<input type="button" class="btn btn-secondary" value="닫기" onclick="closeWindow()">
					</th>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>