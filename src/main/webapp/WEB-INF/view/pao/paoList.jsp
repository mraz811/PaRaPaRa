<%@page import="java.util.List"%>
<%@page import="com.happy.para.dto.PaoDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>업주 발주 리스트</title>
<link rel="stylesheet" href="./css/NoticeList.css">
<script type="text/javascript">

	//발주 상태 조회 셀렉트 박스 선택 및 날짜별 검색에 대한 이벤트
	function selectStatusDate() {
		var store_code = document.getElementById("store_code").value;
		var loginDtoAuth = document.getElementsByName("loginDtoAuth")[0].value;
		
		var doc = document.getElementsByName("paoStatus")[0];
		var startDate = document.getElementsByName("startDate")[0].value;
		var endDate = document.getElementsByName("endDate")[0].value;
		var idx = doc.selectedIndex;	// 선택한 옵션 태그를 인덱스로 변환
		var paoStatus = doc.options[idx].value;	// 선택한 옵션 태그 인덱스의 value 값 가져옴

		$.ajax({
			url : "./paoStatusAjax.do",	// 요청 URL
			type : "post",	// 전송 처리 방식
			asyn : false,	// trun 비동기식, false 동기식
			data : "store_code="+store_code+"&status="+paoStatus+"&startDate="+startDate+"&endDate="+endDate+"&loginDtoAuth="+loginDtoAuth,	// 서버 전송 파라미터
			dataType : "json",	// 서버에서 받는 데이터 타입
			success: function(msg){
	
				var store_code = document.getElementById("store_code").value;

				$.each(msg,function(key,value){
						var htmlTable = "";
						var n = $(".table tr:eq(1) td").length;
						// alert(n);
						if(key=="paoLists"){ // table을 만들어 줌
							htmlTable += "<tr class='table-primary'>"+
							"<th>발주번호</th>"+
							"<th>매장명</th>"+
							"<th>발주상태</th>"+
							"<th>날짜</th></tr>";
						
							if(n==0){
								htmlTable += "<tr onclick='paoDetail(this)'>"
												+ "<th>발주 내역이 없습니다.</th>"
										   + "</tr>";
							}else{
								// 내용을 출력해 준다(paoLists:[{key,value},{},{}])
								$.each(value,function(key, dto){
					
									htmlTable += "<tr onclick='paoDetail(this)'>"  
									  				+ "<td>"
							  								+dto.pao_seq
							  								+"<input type='hidden' class='pao_seq' name='pao_seq' value='"+dto.pao_seq+"'>"
							  						+ "</td>"
							  						+ "<td>"+dto.store_name+"</td>"
													+ "<td>"+dto.ps_name+"</td>"
											 		+ "<td>"+dto.pao_date+"</td>"
												+ "</tr>";
								});
							}
								
						}else{ // key=paoRow는 paging를 만들어 줌
				
							htmlTable +="<li><a href='#' onclick='pageFirst("+value.pageList+","+value.pageList+")'>&laquo;</a></li>";
							htmlTable +="<li><a href='#' onclick='pagePre("+value.pageNum+","+value.pageList+")'>&lsaquo;</a></li>";
								
							for (var i =value.pageNum ; i <= value.count; i++) {
								htmlTable +="<li><a href='#' onclick='pageIndex("+i+")'>"+i+"</a></li>";
							}
													
							htmlTable +="<li><a href='#' onclick='pageNext("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&rsaquo;</a></li>";
							htmlTable +="<li><a href='#' onclick='pageLast("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&raquo;</a></li>";
						}
				
								
						if(key=="paoLists"){
							$("#paoTable").html(htmlTable);
							var noTable = document.getElementById("paoTable").innerHTML;
						}else{
							$(".pagination").html(htmlTable);
						}
					});
				}, error : function() {
					alert("페이징 실패");
				}
		});
		
	}

	// 발주 상세보기 이벤트
	function paoDetail(el) {
		var store_code = document.getElementById("store_code").value;
		
		var idx = $('tbody').children('tr').index(el);
		var pao_seq = document.getElementsByName("pao_seq")[idx].value;

		window.open("./paoDetailOpen.do?store_code="+store_code+"&pao_seq="+pao_seq, "발주 상세조회", "width=880, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes");
	}
	
	// 발주 신청 버튼 이벤트
	function paoRequest() {
		var store_code = document.getElementById("store_code").value;
		
		window.open("./paoRequestOpen.do?store_code="+store_code, "발주신청", "width=880, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes");
	}
	
	///////////////////////////////////////////////
	// paging
	///////////////////////////////////////////////
	
	function pageList(){
		var index = document.getElementById("index");
		var pageNum = document.getElementById("pageNum");
		var listNum = document.getElementById("listNum");
		
		index.value = 0;
		pageNum.value = 1;
		listNum.value = document.getElementById("list").value;
		
		pageAjax();
	}
	
	// 페이지 숫자 눌렀을때
	function pageIndex(pageNum){
		var index = document.getElementById("index");
		index.value = pageNum-1;

		pageAjax();
	}
	
	//pageFrist(${paoRow.pageList},${paoRow.pageList})
	function pageFirst(num, pageList){
		var pageNum = document.getElementById("pageNum");
		var	index = document.getElementById("index");
			
		pageNum.value = 1;
		index.value = 0;
			
		pageAjax();
			
	}
	
	
	//pagePre(${paoRow.pageNum}, ${paoRow.pageList})
	function pagePre(num, pageList){
		if(0<num-pageList){
			num -= pageList;
			var pageNum = document.getElementById("pageNum");
			var index = document.getElementById("index");
			
			pageNum.value = num;
			index.value = num-1;
		}
		
		pageAjax();
	}
	
	
	//pageNext(${paoRow.pageNum},${paoRow.total},${paoRow.listNum},${paoRow.pageList})
	function pageNext(num, total, listNum, pageList){
		var index = Math.ceil(total/listNum); //묶음 40/5 => 8
		var max = Math.ceil(index/pageList); // 글의 갯수 8/5 => 2
		
		if(max*pageList > num+pageList){
			num += pageList;
			
			var pageNum = document.getElementById("pageNum");
			var index = document.getElementById("index");
			
			pageNum.value = num;
			index.value = num-1;
		}
		pageAjax();
	} 
	
	//pageLast(${paoRow.pageNum},${paoRow.total},${paoRow.listNum},${paoRow.pageList})
	function pageLast(num, total, listNum, pageList){

		var idx = Math.ceil(total/listNum);
		var max = Math.ceil(idx/pageList);
		
		while(max*pageList > num+pageList){
			num += pageList
		}
		
		var pageNum = document.getElementById("pageNum");
		var index = document.getElementById("index");
		
		pageNum.value= num;
		index.value = idx-1;

		pageAjax();
	
	}

	
	var pageAjax = function(){

		$.ajax({
			url : "./paoPaging.do",
			type : "post",
			async : true,
			data :  $("#frm").serialize(),    //"index="+ obj  // JSON.stringify
			dataType : "json",
			success: function(msg){
	
				$.each(msg,function(key,value){
					var htmlTable = "";
					var n = $(".table tr:eq(0) th").length;
					
					if(key=="paoLists"){ // table을 만들어 줌
						htmlTable += "<tr class='table-primary'>"+
						"<th>발주번호</th>"+
						"<th>매장명</th>"+
						"<th>발주상태</th>"+
						"<th>날짜</th></tr>";
						
						if(n==0){
							htmlTable += "<tr onclick='paoDetail(this)'>"
											+ "<th>발주 내역이 없습니다.</th>"
									   + "</tr>";
						}else{
							// 내용을 출력해 준다(paoLists:[{key,value},{},{}])
							$.each(value,function(key, dto){

								
								htmlTable += "<tr onclick='paoDetail(this)'>"  
						  						+ "<td>"
				  										+dto.pao_seq
				  										+"<input type='hidden' class='pao_seq' name='pao_seq' value='"+dto.pao_seq+"'>"
				  								+ "</td>"
				  								+ "<td>"+dto.store_name+"</td>"
									  			+ "<td>"+dto.ps_name+"</td>"
									  			+ "<td>"+dto.pao_date+"</td>"
									  		+ "</tr>";
							});	
						}
			
					}else{ // key=paoRow는 paging를 만들어 줌
	
						htmlTable +="<li><a href='#' onclick='pageFirst("+value.pageList+","+value.pageList+")'>&laquo;</a></li>";
						htmlTable +="<li><a href='#' onclick='pagePre("+value.pageNum+","+value.pageList+")'>&lsaquo;</a></li>";
						
						for (var i =value.pageNum ; i <= value.count; i++) {
							htmlTable +="<li><a href='#' onclick='pageIndex("+i+")'>"+i+"</a></li>";
						}
										
						htmlTable +="<li><a href='#' onclick='pageNext("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&rsaquo;</a></li>";
						htmlTable +="<li><a href='#' onclick='pageLast("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&raquo;</a></li>";
					}
		
					if(key=="paoLists"){
						$("#paoTable").html(htmlTable);
						var noTable = document.getElementById("paoTable").innerHTML;
					}else{
						$(".pagination").html(htmlTable);
					}
				});
			}
		});
		
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
<div id="container">

<%@include file="../header.jsp" %>
	<div class="bodyFrame">
		<div class="bodyfixed">
			<div class="oneDepth">
				<p>아르바이트</p>
			</div>
			<div class="twoDepth">
				<ul class="nav nav-tabs">
	  				<li class="nav-item">
		    			 <a class="nav-link" data-toggle="tab" id="calendar">Calendar</a>
		  				</li>
		  				<li class="nav-item">
		    			 <a class="nav-link" data-toggle="tab" href="./selPaoList.do">발주</a>
		  				</li>
		  				<li class="nav-item">
		    			 <a class="nav-link" data-toggle="tab" id="stock">재고</a>
		  				</li>
				</ul>
				<script type="text/javascript">
					$(function() {
						$("#calendar").click(function() {
							location.href="./selCal.do";
						});
					})
					
					$(function() {
						$("#stock").click(function() {
							location.href="./selStock.do";
						});
					})
				</script>
				<div class="tab-content" align="center">
					<form action="#" id="frm" method="get">
					<!-- 각자 내용들.. -->
						<div id="selectDate">
							<select name="paoStatus" onchange="selectStatusDate()">
								<option value="1,2,3,0">발주전체조회</option>
								<option value="1">발주대기</option>
								<option value="2">발주승인</option>
								<option value="3">발주완료</option>			
								<option value="0">발주취소</option>			
							</select>
							
							날짜 선택 <input type="date" name="startDate"> ~ <input type="date" name="endDate"> <input type="button" value="검색" onclick="selectStatusDate()">
						</div>
						
						<div id="paoList">
							<input type="text" name="loginDtoAuth" value="${loginDto.auth}">
							<input type='text' id='store_code' value='${store_code}'>
							<table class="table table-hover" id="paoTable">
								<thead>
									<tr class="table-primary">
										<th>발주번호</th><th>매장명</th><th>발주상태</th><th>날짜</th>
									</tr>
								</thead>
								<tbody>		
									<c:choose>
										<c:when test="${empty paoLists}">
											<tr>
												<td id="noList" colspan="4">-- 발주 내역이 없습니다 --</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach var="dto" items="${paoLists}" varStatus="status">
												<tr onclick="paoDetail(this)">
													<td>
														${dto.pao_seq}
														<input type="hidden" class="pao_seq" name="pao_seq" value="${dto.pao_seq}">
													</td>
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
						
						<div id="paoBottom">
							<!-- 현재 페이지, 인덱스, 출력 갯수 -->
							<%-- ${paoRow.index} ${paoRow.pageNum} ${paoRow.listNum} ${paoRow.count} --%>
							<input type="hidden" id="index" name="index" value="${paoRow.index}">
							<input type="hidden" id="pageNum" name="pageNum" value="${paoRow.pageNum}">
							<input type="hidden" id="listNum" name="listNum" value="${paoRow.listNum}">
	
							<div class="center">
								<ul class="pagination">
									<li class="page-item disabled"><a href="#" onclick="pageFirst(${paoRow.pageList},${paoRow.pageList})">&laquo;</a></li>
									<li class="page-item"><a href="#" onclick="pagePre(${paoRow.pageNum},${paoRow.pageList})">&lsaquo;</a></li>
										
									<c:forEach var="i" begin="${paoRow.pageNum}" end="${paoRow.count}" step="1">
										<li class="page-item"><a href="#" onclick="pageIndex(${i})">${i}</a></li>
									</c:forEach>
											
									<li><a href="#" onclick="pageNext(${paoRow.pageNum},${paoRow.total},${paoRow.listNum},${paoRow.pageList})">&rsaquo;</a></li>
									<li><a href="#" onclick="pageLast(${paoRow.pageNum},${paoRow.total},${paoRow.listNum},${paoRow.pageList})">&raquo;</a></li>
								</ul>
							</div>
							
							<c:if test="${loginDto.auth eq 'U'}">
								<input type="button" class="btn btn-outline-success" value="발주신청" onclick="paoRequest()">
							</c:if>
						</div>
					</form>
				</div> <!-- tab-content -->
			</div>
		</div>
	</div>
<%@include file="../footer.jsp" %>
	
</div>
</body>
</html>