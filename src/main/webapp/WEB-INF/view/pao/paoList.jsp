<%@page import="java.util.List"%>
<%@page import="com.happy.para.dto.PaoDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>발주 리스트</title>
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
			asyn : false,	// true 비동기식, false 동기식
			data : "store_code="+store_code+"&status="+paoStatus+"&startDate="+startDate+"&endDate="+endDate+"&loginDtoAuth="+loginDtoAuth,	// 서버 전송 파라미터
			dataType : "json",	// 서버에서 받는 데이터 타입
			success: function(msg){
	
				$.each(msg,function(key,value){
						var htmlTable = "";
						
						if(key=="paoLists"){ // table을 만들어 줌
							htmlTable += "<thead>"+
											"<tr class='table-primary' style='text-align: center;'>"+
												"<th>발주번호</th>"+
												"<th>매장명</th>"+
												"<th>발주상태</th>"+
												"<th>날짜</th>"+
											"</tr>"+
										 "</thead>";
							
							// 내용을 출력해 준다(paoLists:[{key,value},{},{}])
							// key에 대응하는 value 값이 없다면
							if(value.length==0){
								htmlTable += "<tbody>" 
												+ "<tr>"
													+ "<th colspan='4' style='text-align:center; color: red; font-weight: bold;'>발주 내역이 없습니다.</th>"
										   		+ "</tr>"
										   	 "</tbody>";
							}else{
								$.each(value,function(key, dto){
									
									htmlTable += "<tbody>"
													+ "<tr style='text-align: center;' onclick='paoDetail(this)'>"  
										  				+ "<td>"
								  								+dto.pao_seq
								  								+"<input type='hidden' class='pao_seq' name='pao_seq' value='"+dto.pao_seq+"'>"
								  						+ "</td>"
								  						+ "<td>"
								  								+dto.store_name
								  								+"<input type='hidden' name='pao_store_code' value='"+dto.store_code+"'>"
								  						+"</td>";
								  						if(dto.ps_code=="1"){
								  							htmlTable += "<td style='color:orange; font-weight:bold;'>"+dto.ps_name+"</td>";	
								  						}else if(dto.ps_code=="2"){
								  							htmlTable += "<td style='color:blue; font-weight:bold;'>"+dto.ps_name+"</td>";
								  						}else if(dto.ps_code=="3"){
								  							htmlTable += "<td style='color:green; font-weight:bold;'>"+dto.ps_name+"</td>";
								  						}else{
								  							htmlTable += "<td style='color:red; font-weight:bold;'>"+dto.ps_name+"</td>";
								  						}
											htmlTable += "<td>"+dto.pao_date+"</td>"
													+ "</tr>"
												+"</tbody>";
								});
							}
							  
						}else{ // key=paoRow는 paging를 만들어 줌
				
							htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageFirst("+value.pageList+","+value.pageList+")'>&laquo;</a></li>";
							htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pagePre("+value.pageNum+","+value.pageList+")'>&lsaquo;</a></li>";
								
							for (var i =value.pageNum ; i <= value.count; i++) {
								htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageIndex("+i+")'>"+i+"</a></li>";
							}
													
							htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageNext("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&rsaquo;</a></li>";
							htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageLast("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&raquo;</a></li>";
						}
				
								
						if(key=="paoLists"){
							$("#paoTable").html(htmlTable);
							var noTable = document.getElementById("paoTable").innerHTML;
						}else{
							$(".pagination").html(htmlTable);
						}
					});
				}, error : function() {
					alert("에러");
				}
		});
		
	}

	//발주 상세보기 이벤트
	 
	function paoDetail(el) {
		var idx = $('tbody').children('tr').index(el);
		var store_code = document.getElementsByName("pao_store_code")[idx].value;
		var pao_seq = document.getElementsByName("pao_seq")[idx].value;
		
		window.open("./paoDetailOpen.do?store_code="+store_code+"&pao_seq="+pao_seq, "발주 상세조회", "width=860, height=600, toolbar=no, menubar=no, scrollbars=no, resizable=yes");
	}
	
	// 발주 신청 버튼 이벤트
	function paoRequest() {
		var store_code = document.getElementById("store_code").value;
		
		window.open("./paoRequestOpen.do?store_code="+store_code, "발주신청", "width=860, height=600, toolbar=no, menubar=no, scrollbars=no, resizable=yes");
	}
	
	
	////////////////////////////////////////////////
	//******************* 페이징 *******************//
	///////////////////////////////////////////////
	
	// 페이지 리스트
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
	
	// pageFrist(${paoRow.pageList},${paoRow.pageList})
	function pageFirst(num, pageList){
		var pageNum = document.getElementById("pageNum");
		var	index = document.getElementById("index");
			
		pageNum.value = 1;
		index.value = 0;
			
		pageAjax();
			
	}
	
	// pagePre(${paoRow.pageNum}, ${paoRow.pageList})
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
	
	// pageNext(${paoRow.pageNum},${paoRow.total},${paoRow.listNum},${paoRow.pageList})
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
	
	// pageLast(${paoRow.pageNum},${paoRow.total},${paoRow.listNum},${paoRow.pageList})
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
	var count = 1;
	
	// 페이지 Ajax
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
						htmlTable += "<thead>"+
										"<tr class='table-primary' style='text-align: center;'>"+
											"<th>발주번호</th>"+
											"<th>매장명</th>"+
											"<th>발주상태</th>"+
											"<th>날짜</th>"+
										"</tr>"+
									 "</thead>";
		
						// 내용을 출력해 준다(paoLists:[{key,value},{},{}])
						// key에 대응하는 value 값이 없다면
						if(value.length==0){
							htmlTable += "<tbody>" 
											+ "<tr>"
												+ "<th colspan='4' style='text-align:center; color: red; font-weight: bold;'>발주 내역이 없습니다.</th>"
									   		+ "</tr>"
									   	 "</tbody>";
						}else{
							$.each(value,function(key, dto){
								
								htmlTable += "<tbody>"
												+ "<tr style='text-align: center;' onclick='paoDetail(this)'>"  
									  				+ "<td>"
							  							+ dto.pao_seq 	
							  							+ "<input type='hidden' class='pao_seq' name='pao_seq' value='"+dto.pao_seq+"'>"
							  						+ "</td>"
							  						+ "<td>"
							  								+dto.store_name
							  								+"<input type='hidden' name='pao_store_code' value='"+dto.store_code+"'>"
							  						+"</td>";
							  						if(dto.ps_code=="1"){	// 발주 대기 상태일 시
							  							htmlTable += "<td style='color:orange; font-weight:bold;'>"+dto.ps_name+"</td>";	
							  						}else if(dto.ps_code=="2"){	// 발주 승인 상태일 시
							  							htmlTable += "<td style='color:blue; font-weight:bold;'>"+dto.ps_name+"</td>";
							  						}else if(dto.ps_code=="3"){	// 발주 완료 상태일 시
							  							htmlTable += "<td style='color:green; font-weight:bold;'>"+dto.ps_name+"</td>";
							  						}else{	// 발주 취소 상태일 시
							  							htmlTable += "<td style='color:red; font-weight:bold;'>"+dto.ps_name+"</td>";
							  						}
										htmlTable += "<td>"+dto.pao_date+"</td>"
												+ "</tr>"
											+"</tbody>";
							});
						}
					}else{ // key=paoRow는 paging를 만들어 줌
	
						htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageFirst("+value.pageList+","+value.pageList+")'>&laquo;</a></li>";
						htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pagePre("+value.pageNum+","+value.pageList+")'>&lsaquo;</a></li>";
						
						for (var i =value.pageNum ; i <= value.count; i++) {
							htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageIndex("+i+")'>"+i+"</a></li>";
						}
										
						htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageNext("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&rsaquo;</a></li>";
						htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageLast("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&raquo;</a></li>";
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
	#selectStatus{
		float: left;
	}
	#selectDate{
		float: right;
	}
	#paoList{
		height: 337px;
	}
	#btnDiv{
		margin-top: 10px;
		margin-left: 945px; 
		position: absolute; 
	}
	.psCode1{
		color: orange;
		font-weight: bold;
	}
	.psCode2{
		color: blue;
		font-weight: bold;
	}
	.psCode3{
		color: green;
		font-weight: bold;
	}
	.psCode0{
		color: red;
		font-weight: bold;
	}
	tr{
		cursor: pointer;
	}
</style>
</head>
<body>
	<div id="container">
	
	<%@include file="../header.jsp" %>
	
		<div class="bodyFrame">
			<div class="bodyfixed">
				<div class="oneDepth">
					<p>매장관리</p>
				</div>
				<div class="twoDepth">
					<ul class="nav nav-tabs">
						<c:if test="${loginDto.auth eq 'U'}">
			  				<li class="nav-item">
				    			 <a class="nav-link" data-toggle="tab" id="calendar">Calendar</a>
				  				</li>
				  				<li class="nav-item">
				    			 <a class="nav-link active" data-toggle="tab" href="./selPaoList.do" style="border: 1px solid rgb(21,140,186);"><strong>발주</strong></a>
				  				</li>
				  				<li class="nav-item">
				    			 <a class="nav-link" data-toggle="tab" id="stock">재고</a>
				  			</li>
			  			</c:if>
			  			<c:if test="${loginDto.auth eq 'A'}">
			  				<li class="nav-item">
		    					<a class="nav-link" data-toggle="tab" id="storeList" href="#">매장 정보</a>
			  				</li>
			  				<li class="nav-item">
			    				<a class="nav-link active" data-toggle="tab" href="./selAdminPaoList.do" href="#" style="border: 1px solid rgb(21,140,186);"><strong>발주</strong></a>
			  				</li>
			  				<li class="nav-item">
			    				<a class="nav-link" data-toggle="tab" id="itemList" href="#">품목</a>
			  				</li>
			  			</c:if>
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
						
						$(function() {
							$("#storeList").click(function() {
								location.href="./selStoreList.do";
							});
						})
						
						$(function() {
							$("#itemList").click(function() {
								location.href="./selItemList.do";
							});
						})
					</script>
					<div class="tab-content" align="center">
						<form action="#" id="frm" method="get">
						<!-- 각자 내용들.. -->
							<div id="selectStatus" class="form-group">
								<select name="paoStatus" class="form-control" style="width: 150px; margin: 5px 1px 0px;" onchange="selectStatusDate()">
									<option value="1,2,3,0">발주전체조회</option>
									<option value="1">발주대기</option>
									<option value="2">발주승인</option>
									<option value="3">발주완료</option>			
									<option value="0">발주취소</option>			
								</select>
							</div>
							<div id="selectDate">	
								<div class="input-group date" style="margin-top: 5px;">
									<input type="date" class="form-control" name="startDate">
									&nbsp;&nbsp; ~ &nbsp;&nbsp;
									<input type="date" class="form-control" name="endDate">
									&nbsp;&nbsp;
									<input type="button" class="btn btn-secondary" value="검색" onclick="selectStatusDate()">
								</div>
							</div>
							
							<div id="paoList">
								<input type="hidden" name="loginDtoAuth" value="${loginDto.auth}">
								<input type='hidden' id='store_code' value='${store_code}'>
								
								<table class="table table-hover" id="paoTable">
									<thead>
										<tr class="table-primary" style="text-align: center;">
											<th>발주번호</th><th>매장명</th><th>발주상태</th><th>날짜</th>
										</tr>
									</thead>
									<tbody>		
										<c:choose>
											<c:when test="${empty paoLists}">
												<tr>
													<td id="noList" colspan="4" style="text-align: center; color: red; font-weight: bold;">발주 내역이 없습니다.</td>
												</tr>
											</c:when>
											<c:otherwise>
												<c:forEach var="dto" items="${paoLists}" varStatus="status">
													<tr style="text-align: center;" onclick="paoDetail(this)">
														<td>
															${dto.pao_seq}
															<input type="hidden" class="pao_seq" name="pao_seq" value="${dto.pao_seq}">
														</td>
														<td>
															${dto.store_name}
															<input type="hidden" name="pao_store_code" value="${dto.store_code}">
														</td>
														<td class="psCode${dto.ps_code}">${dto.ps_name}</td>
														<td>${dto.pao_date}</td>
													</tr>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
							
							<div id="pagingForm">
								<!-- 현재 페이지, 인덱스, 출력 갯수 -->
								<%-- ${paoRow.index} ${paoRow.pageNum} ${paoRow.listNum} ${paoRow.count} --%>
								<input type="hidden" id="index" name="index" value="${paoRow.index}">
								<input type="hidden" id="pageNum" name="pageNum" value="${paoRow.pageNum}">
								<input type="hidden" id="listNum" name="listNum" value="${paoRow.listNum}">
		
								<div class="center">
									<ul class="pagination pagination-lg">
										<li class="page-item"><a class="page-link" href="#" onclick="pageFirst(${paoRow.pageList},${paoRow.pageList})">&laquo;</a></li>
										<li class="page-item"><a class="page-link" href="#" onclick="pagePre(${paoRow.pageNum},${paoRow.pageList})">&lsaquo;</a></li>
											
										<c:forEach var="i" begin="${paoRow.pageNum}" end="${paoRow.count}" step="1">
											<li class="page-item"><a class="page-link" href="#" onclick="pageIndex(${i})">${i}</a></li>
										</c:forEach>
												
										<li class="page-item"><a class="page-link" href="#" onclick="pageNext(${paoRow.pageNum},${paoRow.total},${paoRow.listNum},${paoRow.pageList})">&rsaquo;</a></li>
										<li class="page-item"><a class="page-link" href="#" onclick="pageLast(${paoRow.pageNum},${paoRow.total},${paoRow.listNum},${paoRow.pageList})">&raquo;</a></li>
									</ul>
								</div>
							</div>
							<div id="btnDiv">
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