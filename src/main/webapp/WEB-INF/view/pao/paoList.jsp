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

	//발주 상태 조회 셀렉트 박스 이벤트
	function selectStatus() {
		var store_code = document.getElementById("store_code").value;
		alert("스토어 코드 = "+store_code);
		var doc = document.getElementsByName("paoStatus")[0];
		var idx = doc.selectedIndex;	// 선택한 옵션 태그를 인덱스로 변환
		var paoStatus = doc.options[idx].value;	// 선택한 옵션 태그 인덱스의 value 값 가져옴
		alert(store_code);
		alert(paoStatus);
		
		$.ajax({
			url : "./paoStatusAjax.do",	// 요청 URL
			type : "post",	// 전송 처리 방식
			asyn : false,	// trun 비동기식, false 동기식
			data : "store_code="+store_code+"&status="+paoStatus,	// 서버 전송 파라미터
			dataType : "json",	// 서버에서 받는 데이터 타입
			success : function(obj) {
				alert("성공");
				//alert(obj.store_code);
				//alert(obj.status_list);
				$("tbody").children().remove();
				$.each(obj, function(key, val){
					//alert(key);
					var paoLists = val;
					//alert("리스트 길이 : "+paoLists.length);
					
					if(paoLists.length==0){
						$("tbody").append("<tr><td id='noList' colspan='4'>글이없습니다.</td></tr>");
					}else{
						for(var i = 0; i < paoLists.length; i++){
							var dto = paoLists[i];
							var content = "";
							content = "<tr>"  
							  			+ "<td>"+dto.pao_seq+"</td>"
							  			+ "<td>"+dto.store_name+"</td>"
							  			+ "<td>"+dto.ps_name+"</td>"
							  			+ "<td>"+dto.pao_date+"</td>"
							  		+ "</tr>";
							$("tbody").append(content);
						}
						
					}
					
				});
			}, error : function() {
				alert("서버통신 실패");
			}
		});
		
	}
	
	function search() {
		var store_code = document.getElementById("store_code").value;
		var startDate = document.getElementById("startDate").value;
		var endDate = document.getElementById("endDate").value;
		//alert(startDate);
		//alert(endDate);
		$.ajax({
			url : "./paoDateAjax.do",	// 요청 URL
			type : "post",	// 전송 처리 방식
			asyn : false,	// trun 비동기식, false 동기식
			data : "store_code="+store_code+"&startDate="+startDate+"&endDate="+endDate,	// 서버 전송 파라미터
			dataType : "json",	// 서버에서 받는 데이터 타입
			success : function(obj) {
				alert("성공");
				//alert(obj.store_code);
				$("tbody").children().remove();
				$.each(obj, function(key, val){
					//alert(key);
					var paoLists = val;
					//alert("리스트 길이 : "+paoLists.length);
					
					if(paoLists.length==0){
						$("tbody").append("<tr><td id='noList' colspan='4'>글이 없습니다.</td></tr>");
					}else{
						for(var i = 0; i < paoLists.length; i++){
							var dto = paoLists[i];
							var content = "";
							content = "<tr>"  
							  			+ "<td>"+dto.pao_seq+"</td>"
							  			+ "<td>"+dto.store_name+"</td>"
							  			+ "<td>"+dto.ps_name+"</td>"
							  			+ "<td>"+dto.pao_date+"</td>"
							  		+ "</tr>";
							$("tbody").append(content);
						}
						
					}
					
				});
			}, error : function() {
				alert("서버통신 실패");
			}
		});
	}
	
	// 발주 신청 버튼 이벤트
	function paoRequest() {
		//alert("dd");
		var store_code = document.getElementById("store_code").value;
		window.open("./paoRequest.do?store_code="+store_code, "발주신청", "width=880, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes");
		//location.href = "./paoRequest.do?store_code="+store_code;
	}
	
	

</script>
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
	<div id="selectDate">
		<select name="paoStatus" onchange="selectStatus()">
			<option value="1,2,3,0">발주전체조회</option>
			<option value="1">발주대기</option>
			<option value="2">발주승인</option>
			<option value="3">발주완료</option>			
			<option value="0">발주취소</option>			
		</select>
		
		날짜 선택 <input type="date" id="startDate"> ~ <input type="date" id="endDate"> <input type="button" value="검색" onclick="search()">
	</div>
	
	<div id="paoList">
		<input type='hidden' id='store_code' value='${paoLists[0].store_code}'>
		<table>
			<thead>
				<tr>
					<th>발주번호</th><th>매장명</th><th>발주상태</th><th>날짜</th>
				</tr>
			</thead>
			<tbody>		
				<c:choose>
					<c:when test="${empty paoLists}">
						<tr>
							<td id="noList" colspan="4">-- 작성된 글이 없습니다 --</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="dto" items="${paoLists}">
							<tr>
								<td>${dto.pao_seq}</td>
								<td>${dto.store_name}</td>
								<td>${dto.ps_name}</td>
								<td>${dto.pao_date}</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	
	<div id="paoButton">
		페이징
		<input type="button" value="발주신청" onclick="paoRequest()">
			
	</div>
	
	
</body>
</html>