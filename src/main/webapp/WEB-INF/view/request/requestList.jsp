<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	response.setContentType("text/html; charset=UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 내역</title>
<link rel="stylesheet" href="./css/requestList.css">
</head>
<script type="text/javascript" src="./js/requestList.js"></script>
<script type="text/javascript">
	function selRequestStatus(){
		location.href="./selRequestStatus.do";
	}
	//주문 완료, 환분, 전체 조회 셀렉트 박스 선택시 작동
	function changeStatus(){
		   var select = document.getElementById("selectBox");
		   var selectBoxFrm = document.getElementById("selectBoxFrm");
		   selectBoxFrm.method = "post";
		   selectBoxFrm.action = "./selRequestList.do";
		   selectBoxFrm.submit();
	}
	//환불버튼 클릭 시 작동
	function changeStatusCode0(request_seq){
		var os_code = "0";
		swal({
			  title: "주문을 환불하시겠습니까?",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonClass: "btn-danger",
			  cancelButtonClass: "btn-warning",
			  confirmButtonText: "취소",
			  cancelButtonText: "환불",
			  closeOnConfirm: true,
			  closeOnCancel: true
			},
			function(isConfirm) {
				$.ajax({
					url : "./updateOrderState.do",
					type : "post",
					async : true,
					data : {"request_seq":request_seq,"os_code":os_code},
					success : function(obj){
						location.reload();
					},error : function(obj){
						alert(obj); 
					}
				})
			});
	}
	//주문 메뉴명 클릭시 작동
	function menuDetail(request_seq,os_code){
		var detailMenu = document.getElementById("detailMenu");
		$.ajax({
			url : "./selRequestDetail.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq,"os_code":os_code},
			dataType : "json",
			success : function(obj){
				swal(obj.menu.menu_name,"가격 : "+obj.menu.request_price+"원");
			},error : function(obj){
				alert("관리자에게 문의해 주세요"); 
			}
		})
	}
</script>
<body>
	<div id="container">
		<%@include file="../header.jsp"%>
		<div class="bodyFrame">
			<div class="bodyfixed">
				<div class="oneDepth">
					<p>주문</p>
				</div>
				<div class="twoDepth">
					<ul class="nav nav-tabs">
						<li class="nav-item"><a id="requestStatus" class="nav-link"
							data-toggle="tab" onclick="selRequestStatus()">주문</a></li>
						<li class="nav-item"><a class="nav-link active"
							data-toggle="tab" href="#"
							style="border: 1px solid rgb(21, 140, 186);"><strong>주문내역</strong></a></li>
					</ul>
					<form id="selectBoxFrm" action="#" method="post">
						<div id="selectStatus" class="form-group">
							<select class="form-control" id="selectBox" name="os_code" onchange="changeStatus()">
								<option>완료,환불 선택</option>
								<option value="4">완료,환불 전체 보기</option>
								<option value="3">완료</option>
								<option value="0">환불</option>
							</select>
						</div>
					</form>
					<div class="tab-content">
						<form action="#" id="frm" method="post">
							<table class="table table-hover">
								<thead>
									<tr class="table-primary">
										<td>번호</td>
										<td>주문메뉴명</td>
										<td>총가격</td>
										<td>주문시간</td>
										<td>은행명</td>
										<td>계좌번호</td>
										<td>상태</td>
										<td></td>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(requestList) eq 0}">
											<c:choose>
												<c:when test="${os_code eq '0'}">
													<tr>
														<th colspan="7">환불 내역이 없습니다.</th>
													</tr>
												</c:when>
												<c:when test="${os_code eq '3'}">
													<tr>
														<th colspan="7">주문 완료 내역이 없습니다.</th>
													</tr>
												</c:when>
												<c:otherwise>
													<tr>
														<th colspan="7">주문 완료,환불 내역이 없습니다.</th>
													</tr>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:forEach begin="0" end="${fn:length(requestList)}"
												items="${requestList}" var="request" varStatus="vs">
												<c:choose>
													<c:when test="${fn:length(request.menu_name) > 27}">
														<tr>
															<td style="width: 50px;">${request.rnum}</td>
															<td id="detailMenu" style="width: 300px;"
																onclick="menuDetail(${request.request_seq},${request.os_code})">${fn:substring(request.menu_name,0,27)}...</td>
															<td style="width: 100px;">${request.request_price}원</td>
															<td style="width: 150px;">${request.request_time}</td>
															<td style="width: 100px;">${request.request_bank}</td>
															<td style="width: 130px;">${request.request_account}</td>
															<td style="width: 50px;">${request.os_code eq 3?'완료':'환불'}</td>
															<c:if test="${request.os_code eq 3}">
																<td style="width: 45px; padding: 5px 0px;"><input
																	style="width: 45px; height: 30px;"
																	class="btn btn-outline-warning" type="button"
																	value="환불"
																	onclick="changeStatusCode0(${request.request_seq})" /></td>
															</c:if>
															<c:if test="${request.os_code eq 0}">
																<td style="width: 45px;"></td>
															</c:if>
														</tr>
													</c:when>
													<c:otherwise>
														<tr>
															<td style="width: 50px;">${request.rnum}</td>
															<td id="detailMenu" style="width: 300px;"
																onclick="menuDetail(${request.request_seq},${request.os_code})">${request.menu_name}</td>
															<td style="width: 100px;">${request.request_price}원</td>
															<td style="width: 150px;">${request.request_time}</td>
															<td style="width: 100px;">${request.request_bank}</td>
															<td style="width: 130px;">${request.request_account}</td>
															<td style="width: 50px;">${request.os_code eq 3?'완료':'환불'}</td>
															<c:if test="${request.os_code eq 3}">
																<td style="width: 45px; padding: 5px 0px;"><input
																	style="width: 45px; height: 30px;"
																	class="btn btn-outline-warning" type="button"
																	value="환불"
																	onclick="changeStatusCode0(${request.request_seq})" /></td>
															</c:if>
															<c:if test="${request.os_code eq 0}">
																<td style="width: 45px;"></td>
															</c:if>
														</tr>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
							<div id="pagingForm">
								<!-- 페이징 처리 -->
								<!-- 출력할 페이지 번호, 출력할 페이지 시작번호, 출력할 게시글 갯수 -->
								<input type="hidden" id="index" name="index"
									value="${requestRow.index}"> <input type="hidden"
									id="pageNum" name="pageNum" value="${requestRow.pageNum}">
								<input type="hidden" id="listNum" name="listNum"
									value="${requestRow.listNum}">

								<div class="center" align="center">
									<ul class="pagination pagination-lg">
										<li class="page-item"><a class="page-link" href="#"
											onclick="pageFirst(${requestRow.pageList},${requestRow.pageList})">&laquo;</a></li>
										<li class="page-item"><a class="page-link" href="#"
											onclick="pagePre(${requestRow.pageNum},${requestRow.pageList})">&lsaquo;</a></li>
										<c:forEach var="i" begin="${requestRow.pageNum}"
											end="${requestRow.count}" step="1">
											<li><a class="page-link" href="#"
												onclick="pageIndex(${i})">${i}</a></li>
										</c:forEach>
										<li class="page-item"><a class="page-link" href="#"
											onclick="pageNext(${requestRow.pageNum},${requestRow.total},${requestRow.listNum},${requestRow.pageList})">&rsaquo;</a></li>
										<li class="page-item"><a class="page-link" href="#"
											onclick="pageLast(${requestRow.pageNum},${requestRow.total},${requestRow.listNum},${requestRow.pageList})">&raquo;</a></li>
									</ul>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<%@include file="../footer.jsp"%>
	</div>
</body>
</html>