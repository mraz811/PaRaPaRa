<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/storeList.css">
</head>
<body>
<div id="container">
<%@include file="../header.jsp" %>
<!-- <script type="text/javascript" src="./js/bootstrap.min.js"></script> -->
<script type="text/javascript" src="./js/storeList.js"></script>
<script type="text/javascript" src="./js/2depthLink.js"></script>
	<div class="bodyFrame">
		<div class="bodyfixed">
			<div class="oneDepth">
				<p>매장</p>
			</div>
			<div class="twoDepth">
				<ul class="nav nav-tabs">
	  				<li class="nav-item">
	    				<a class="nav-link active" data-toggle="tab" style="border: 1px solid rgb(21,140,186);" onclick="selStoreList()" href="#"><strong>매장 정보</strong></a>
	  				</li>
	  				<li class="nav-item">
	    				<a class="nav-link" data-toggle="tab" onclick="selPaoList()" href="#">발주</a>
	  				</li>
	  				<li class="nav-item">
	    				<a class="nav-link" data-toggle="tab" onclick="selItemList()" href="#">품목</a>
	  				</li>
				</ul>
				<div class="tab-content">
					<!-- 각자 내용들.. -->
					<form action="#" id="frm" method="post">
						<div>
							<div style="height: 300px;">
								<table class="table table-hover">
									<thead>
										<tr class="table-primary">
											<th>매장코드</th>
											<th>매장명</th>
											<th>주소</th>
											<th>전화번호</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${fn:length(lists) eq 0}">
												<tr>
													<th colspan="4">등록된 매장이 없습니다.</th>
												</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${lists}" var="storeDto">
													<tr>
														<td>${storeDto.store_code}</td>
														<td><a href="#" onclick="storeDetail('${storeDto.store_code}')">${storeDto.store_name}</a></td>
														<td>${storeDto.store_address}</td>
														<td>${storeDto.store_phone}</td>
													</tr>
												</c:forEach>	
											</c:otherwise>
										</c:choose>
									
									</tbody>
								</table>
							</div>
							<input type="hidden" id="index" name="index" value="${storeRow.index}">
							<input type="hidden" id="pageNum" name="pageNum" value="${storeRow.pageNum}">
							<input type="hidden" id="listNum" name="listNum" value="${storeRow.listNum}">
							
							<div id="pagingForm" >
								<div class="center">
									<ul class="pagination pagination-lg">
										<li class="page-item"><a class="page-link" href="#" onclick="pageFirst(${storeRow.pageList},${storeRow.pageList})">&laquo;</a></li>
										<li class="page-item"><a class="page-link" href="#" onclick="pagePre(${storeRow.pageNum},${storeRow.pageList})">&lsaquo;</a></li>
										<c:forEach var="i" begin="${storeRow.pageNum}" end="${storeRow.count}" step="1">
											<li class="page-item"><a class="page-link" href="#" onclick="pageIndex(${i})">${i}</a></li>
										</c:forEach>
										<li class="page-item"><a class="page-link" href="#" onclick="pageNext(${storeRow.pageNum},${storeRow.total},${storeRow.listNum},${storeRow.pageList})">&rsaquo;</a></li>
										<li class="page-item"><a class="page-link" href="#" onclick="pageLast(${storeRow.pageNum},${storeRow.total},${storeRow.listNum},${storeRow.pageList})">&raquo;</a></li>
									</ul>
								</div>
							</div>
						</div>
						<input type="button" id="addStoreBtn" value="매장등록" class="btn btn-outline-success" onclick="insertStore()">
					</form>
				</div>
					
			</div>
		</div>
	</div>
<%@include file="../footer.jsp" %>
</div>
<%-- 	${lists} --%>
<%-- 	${storeRow} --%>
		

</body>
</html>