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
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./css/header.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<script type="text/javascript" src="./js/bootstrap.js"></script>
<script type="text/javascript">
	
	// 발주 상세내역 excel 다운로드
	function paoExcelDownload(){
		location.href = "./poiPao.do?store_code=${paoDto.store_code}&pao_seq=${paoDto.pao_seq}";
	}
	

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
					alert("발주 승인 처리를 실패했습니다. 다시 처리해주세요.");
				}
			});
			 
		}
		
	}
	
	// 담당자가 승인 처리를 한 발주를 업주가 발주 상세내역에서 발주 완료 처리 시 발생하는 이벤트
	function complete() {
		var pao_seq = document.getElementById("pao_seq").value;
		var isc = confirm("해당 발주를 완료하시겠습니까??");
		
		if(isc){
			
			$.ajax({
				url : "./completePao.do",	// 요청 URL
				type : "post",	// 전송 처리 방식
				asyn : false,	// trun 비동기식, false 동기식
				data : "pao_seq="+pao_seq,	// 서버 전송 파라미터(매장코드, 재고번호, 수량)
				success : function() {
					alert("발주가 최종적으로 완료되었습니다!");
					opener.parent.location.reload();	// 부모 페이지인 paoList.jsp 페이지 새로고침 실행
					window.close();	// 발주 상세내역 창 닫음
					
				}, error : function() {
					alert("발주 완료 처리를 실패했습니다. 다시 처리해주세요.");
				}
			});
			 
		}
	}
	
	// 대기중인 발주를 업주가 발주 상세내역에서 발주 취소 처리 시 발생하는 이벤트
	function cancle() {
		var pao_seq = document.getElementById("pao_seq").value;
		var isc = confirm("해당 발주를 취소하시겠습니까??");
		
		if(isc){
			
			$.ajax({
				url : "./canclePao.do",	// 요청 URL
				type : "post",	// 전송 처리 방식
				asyn : false,	// trun 비동기식, false 동기식
				data : "pao_seq="+pao_seq,	// 서버 전송 파라미터(매장코드, 재고번호, 수량)
				success : function() {
					alert("발주가 취소되었습니다!");
					opener.parent.location.reload();	// 부모 페이지인 paoList.jsp 페이지 새로고침 실행
					window.close();	// 발주 상세내역 창 닫음
					
				}, error : function() {
					alert("발주 취소 처리를 실패했습니다. 다시 처리해주세요.");
				}
			});
			 
		}
	}
</script>
<style type="text/css">
	 body{
	 	
	 	margin: 0 auto;
  		padding-top: 20px;
  		padding-left: 20px;
	 }
	 table{
	 	text-align: left;
	 }
	 th,td{
	 	text-align: center;
	 }
	 hr{
	 	height: 20px;
	 }
	
	 .rightAlign{
	 	text-align: right;
	 }
	 #pageName{
		height: 60px;
		margin-bottom: 25px; 
	}
	 #stockList{
	 	width: 800px;
	 	margin-bottom: 25px;
	 }
	 
	 #piList thead{
		position: absolute;
		width: 800px;
	}
	#pbody{
		position: relative;
		width: 800px;
		max-height:210px;
		overflow-y:scroll;
		display: block;
		float:left;
		margin-top: 47px; 
		height: 210px;
	}
	.txt{
		width: 100px;
		text-align: right;
		background-color: #F6D8CE;
	}
</style>
</head>
<body>
	<div id="pageName">
		<h1>발주 상세내역</h1>
		<input type="button" value="엑셀로 다운로드" onclick="paoExcelDownload()">
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
						<th style="width: 100px;">번호</th><th style="width: 300px;">품목명</th><th style="width: 110px;">수량</th><th style="width: 130px;">가격</th><th style="width: 200px;">합계금액</th>
					</tr>
				</thead>
				<tbody id="pbody">	
					<c:set var="t_qty" value="0"/>
					<c:set var="t_price" value="0"/>	
					<c:forEach var="dto" items="${piLists}" varStatus="status">
						<tr>
							<c:set var="piQty" value = "${dto.pi_qty}" />
							<c:set var="sumPrice" value="${piQty * dto.item_price}"/>
							<c:set var="totalPrice" value="${t_qty = t_qty + piQty}"/>
							<c:set var="totalPrice" value="${t_price = t_price + sumPrice}"/>
							<td style="width: 100px;">${status.count}</td>
							<td style="padding-left: 25px; width: 300px;">${dto.item_name}</td>
							<td style="padding-left: 34px; width: 110px;">${dto.pi_qty}개</td>
							<td class="rightAlign" style="padding-right: 20px; width: 130px;">${dto.item_price}원</td>
							<td class="rightAlign" style="padding-right: 50px; width: 200px;"><c:out value="${sumPrice}원"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table>
				<tr class="table-dark">
					<th style="width: 100px; text-align: center;">합계</th>
					<th style="width: 200px; text-align: right;">수량</th>
					<th class="rightAlign" style="width: 150px;">
						<input type="text" class="txt" name="totalPiQty" value="${t_qty}" readonly="readonly">개
					</th>
					<th style="width: 150px; text-align: right;">총금액</th>
					<th style="width: 200px;">
						<input type="text" class="txt" name="totalPiPrice" value="${t_price}" readonly="readonly">원
					</th>
				</tr>
				<tr>
					<td style="padding-top: 20px; text-align: left;">
						<c:if test="${loginDto.auth eq 'A' and paoDto.ps_name eq '발주 대기'}">
							<input type="button" class="btn btn-outline-primary" value="발주승인" onclick="approve()">
						</c:if>
						<c:if test="${loginDto.auth eq 'U' and paoDto.ps_name eq '발주 승인'}">
							<input type="button" class="btn btn-outline-success" value="발주완료" onclick="complete()">
						</c:if>
						<c:if test="${loginDto.auth eq 'U' and paoDto.ps_name eq '발주 대기'}">
							<input type="button" class="btn btn-outline-danger" value="발주취소" onclick="cancle()">
						</c:if>
					</td>
					<td colspan="4" style="padding-top: 20px; text-align: right;">
						<input type="button" class="btn btn-secondary" value="닫기" onclick="javascript:window.close()">
					</td>
				</tr>
			</table>
		</div>
	</div>

</body>
</html>