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

	// 발주 품목의 수량을 빼주는 이벤트
	function minus() {
		var su = document.getElementsByName("pi_qty")[0].value;
		document.getElementsByName("pi_qty")[0].value = new Number(su) - 1;
	}
	
	// 발주 품목의 수량을 늘려주는 이벤트
	function plus() {
		var su = document.getElementsByName("paoSu")[0].value;
		document.getElementsByName("paoSu")[0].value = new Number(su) + 1;
	}

	var cnt = 1;
	
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
		var newTd = document.createElement("td");	// 새로운 <td> 태그 생성
		
		newTr.setAttribute("id", stockTr_id);	// 새로 생성된 <tr>에 id를 똑같이 설정 
		
		var pbody = document.getElementById("pbody");
		
		var currentLine = document.getElementById(stockTr_id);
		currentLine.style.display = "none";
		
		var test = "delStock('"+stockTr_id+"')";
		
		pbody.appendChild(newTr).innerHTML = "<td>" +
												"<input type='text' class='txt' name='pi_seq' value='"+piSeq+"' readonly='readonly'>" +
											  "</td>" +
											  "<td>" +
												"<input type='text' class='txt' name='pi_name' value='"+piName+"' readonly='readonly'>" +
											  "</td>" +
											  "<td>" +
												"<input type='button' class='btn' value='-' onclick='minus()'>" +
												"<input type='text' name='pi_qty' value='"+piQty+"'>" +
												"<input type='button' class='btn' value='+' onclick='plus()'>" +
											  "</td>" +
											  "<td>" +
												"<input type='text' class='txt' name='pi_price' value='"+piPrice+"' readonly='readonly'>" +
											  "</td>" +
											 "<td>" +
												"<input type='text' class='txt' name='sumPi_Price' value='"+sumPiPrice+"' readonly='readonly'>" +
											  "</td>" +
											  "<td>" +
											  	"<input type='button' class='delBtn' value='삭제'  onclick='delStock(\""+stockTr_id+"\")'>" +
											  "</td>";
		

	}
	
	// 발주 품목에서 해당 품목 삭제를 했을 때 발주 품목에서는 사라지고 재고 목록에 추가되는 이벤트
	function delStock(lineId) {
	/* 
		alert(lineId);
		var piTr_id = document.getElementById("lineId");
		//pbody.removeChild(piTr_id);
		
		var currentLine = document.getElementById(lineId);
		currentLine.style.display = "";
		cnt--;
		 */
	}
	
</script>
<style type="text/css">
	table{
		border: 1px solid black;
		border-collapse: collapse;
		width: 800px;
		
	}
	thead, tbody, tr, th, td{
		border: 1px solid black;
		border-collapse: collapse;
	}
	tbody{
		height: 110px;
	}
	tr{
		height: 26px;
	}
	th{
		background-color: orange;
	}
	#btn{
		width: 15px;
		height: 15px;
	}
	.txt{
		border: none;
	}
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
			<tbody id="pbody">		
				<tr>
					<td>
						<input type="text" class="txt" name="pi_seq" value="1" readonly="readonly">
					</td>
					<td>
						<input type="text" class="txt" name="pi_name" value="골뱅이" readonly="readonly">
					</td>
					<td>
						<input type="button" class="btn" value="-" onclick="minus()">
						<input type="text" name="pi_qty" value="20">
						<input type="button" class="btn" value="+" onclick="plus()">
					</td>
					<td>
						<input type="text" class="txt" name="pi_price" value="500" readonly="readonly">
					</td>
					<td>
						<input type="text" class="txt" name="sumPi_Price" value="500" readonly="readonly">
					</td>
					<td><input type="button" class="delBtn" value="삭제" onclick="delStock()"></td>
				</tr>	
			</tbody>
		</table>
	</div>
</body>
</html>