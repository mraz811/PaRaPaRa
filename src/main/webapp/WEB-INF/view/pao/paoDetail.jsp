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
<title>업주 발주 리스트</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<script type="text/javascript">

	// 담당자가 발주 상세내역에서 발주 승인 처리 시 발생하는 이벤트
	function approve() {
		var pao_seq = document.getElementById("pao_seq").value;
		var isc = confirm("해당 발주를 승인하시겠습니까??");
		
		if(isc){
			
			$.ajax({
				url : "./approvePao.do",	// 요청 URL
				type : "post",	// 전송 처리 방식
				asyn : false,	// trun 비동기식, false 동기식
				data : "pao_seq="+pao_seq,	// 서버 전송 파라미터(매장코드, 재고번호, 수량)
				success : function() {
					alert("발주 승인이 완료되었습니다!");
					opener.parent.location.reload();	// 부모 페이지인 paoList.jsp 페이지 새로고침 실행
					window.close();	// 발주 상세내역 창 닫음
					
				}, error : function() {
					alert("발주 승인이 실패했습니다. 다시 신청해주세요.");
				}
			});
			 
		}
		
	}
</script>
<style type="text/css">
	/* 
	table{
		border: 1px solid black;
		border-collapse: collapse;
		width: 800px;
	}
	thead, tbody, tr, th, td{
		border: 1px solid black;
		border-collapse: collapse;
	}
	 */
	 body{
	 	width: 800px;
	 }
	 table{
	 	text-align: left;
	 }
	 th,td{
	 	text-align: center;
	 }
	 .rightAlign{
	 	text-align: right;
	 }
	 #pageName{
	 	margin-bottom: 50px;
	 }
	 #stockList{
	 	margin-bottom: 40px;
	 }
	 hr{
	 	height: 20px;
	 }
</style>
</head>
<body>
	<div id="pageName">
		<h1>발주 상세내역</h1>
		<hr>
	</div>
	

	<h3>■ 발주</h3>
	<div id="stockList">
		<div>
			<table class="table table-hover">
				<thead>
					<tr class="table-secondary">
						<th>발주번호</th><th>매장명</th><th>발주상태</th><th>날짜</th>
					</tr>
				</thead>
			
				<tbody id="sbody">		
					<tr>
						<td>
							${paoDto.pao_seq}
							<input type="hidden" id="pao_seq" value="${paoDto.pao_seq}">
						</td>
						<td>
							${paoDto.store_name}
						</td>
						<td>
							${paoDto.ps_name}
						</td>
						<td>
							${paoDto.pao_date}
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>			
				 
				
					
	<h3>■ 발주 품목</h3>
	<div id="piList">
		<div>
			<table class="table table-hover">
				<thead>
					<tr class="table-secondary">
						<th>번호</th><th>품목명</th><th>수량</th><th>가격</th><th>합계금액</th>
					</tr>
				</thead>
				<tbody id="pbody" style="height: 110px; width: 880px; overflow: scroll;">	
					<c:set var="t_qty" value="0"/>
					<c:set var="t_price" value="0"/>	
					<c:forEach var="dto" items="${piLists}" varStatus="status">
						<tr>
							<c:set var="piQty" value = "${dto.pi_qty}" />
							<c:set var="sumPrice" value="${piQty * dto.item_price}"/>
							<c:set var="totalPrice" value="${t_qty = t_qty + piQty}"/>
							<c:set var="totalPrice" value="${t_price = t_price + sumPrice}"/>
							<td>${status.count}</td>
							<td>${dto.item_name}</td>
							<td>${dto.pi_qty}</td>
							<td class="rightAlign">${dto.item_price}원</td>
							<td class="rightAlign"><c:out value="${sumPrice}원"/></td>
						</tr>
					</c:forEach>
					<tr>
						<th>합계</th>
						<th class="rightAlign" colspan="2">
							수량
							<input type="text" class="txt" name="totalPiQty" value="${t_qty}" readonly="readonly" style="text-align: right; width: 80px;">개
						</th>
						<th class="rightAlign" colspan="2">
							총금액
							<input type="text" class="txt" name="totalPiPrice" value="${t_price}" readonly="readonly" style="text-align: right; width: 150px;">원
						</th>
					</tr>
					<tr>
						<td>
							<c:if test="${loginDto.auth eq 'A' and paoDto.ps_name eq '발주 대기'}">
								<input type="button" class="approveBtn" value="발주승인" onclick="approve()">
							</c:if>
								<input type="button" class="closeBtn" value="닫기" onclick="javascript:window.close()">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

</body>
</html>