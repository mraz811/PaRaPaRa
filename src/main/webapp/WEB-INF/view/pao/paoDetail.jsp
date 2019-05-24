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

<style type="text/css">
	table{
		border: 1px solid black;
		border-collapse: collapse;
	}
	thead, tbody, tr, th, td{
		border: 1px solid black;
		border-collapse: collapse;
	}
</style>
</head>
<body>
<%-- 
	<h3>■ 발주</h3>
	<div id="stockList">
		<div>
			<table>
				<thead>
					<tr>
						<th>발주번호</th><th>매장명</th><th>발주상태</th><th>날짜</th>
					</tr>
				</thead>
			
				<tbody id="sbody">		
					<tr>
						<td>
							${paoDto.pao_seq}
						</td>
						<td>
							${paoDto.store_namee}
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
				 --%>
				
					
	<h3>■ 발주 품목</h3>
	<div id="piList">
		<div>
			<table>
				<thead>
					<tr>
						<th>번호</th><th>품목명</th><th>수량</th><th>가격</th><th>합계금액</th>
					</tr>
				</thead>
				<tbody id="pbody" style="height: 110px; width: 880px; overflow: scroll;">		
					<c:forEach var="dto" items="${piLists}" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td>${dto.item_name}</td>
							<td>${dto.pi_qty}</td>
							<td>${dto.item_price}</td>
							<td>합계금액</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
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
			<tr>
				<th colspan="3">
					<input type="button" class="closeBtn" value="닫기" onclick="closeWindow()">
				</th>
			</tr>
		</table>
	</div>	
</body>
</html>