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
<script type="text/javascript" src="./js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/storeList.js"></script>
	<div class="bodyFrame">
		<div class="bodyfixed">
			<div class="oneDepth">
			<p class="text-primary" style="font-size: 40px;">매장</p>
			</div>
			<div class="twoDepth">
				<ul class="nav nav-tabs">
	  				<li class="nav-item">
	    			 <a class="nav-link" data-toggle="tab" href="./selStoreList.do">매장 정보</a>
	  				</li>
	  				<li class="nav-item">
	    			 <a class="nav-link" data-toggle="tab" href="#profile">발주</a>
	  				</li>
				</ul>
				<div class="tab-content">
					<!-- 각자 내용들.. -->
					<form action="#" id="frm" method="post">
						<table class="table">
							<thead>
								<tr>
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
												<td><a href="./selStoreDetail.do?store_code=${storeDto.store_code}">${storeDto.store_name}</a></td>
												<td>${storeDto.store_address}</td>
												<td>${storeDto.store_phone}</td>
											</tr>
										</c:forEach>	
									</c:otherwise>
								</c:choose>
							
							</tbody>
						</table>
						<input type="hidden" id="index" name="index" value="${storeRow.index}">
						<input type="hidden" id="pageNum" name="pageNum" value="${storeRow.pageNum}">
						<input type="hidden" id="listNum" name="listNum" value="${storeRow.listNum}">
						
						<div class="center">
							<ul class="pagination">
								<li><a href="#" onclick="pageFirst(${storeRow.pageList},${storeRow.pageList})">&laquo;</a></li>
								<li><a href="#" onclick="pagePre(${storeRow.pageNum},${storeRow.pageList})">&lsaquo;</a></li>
								<c:forEach var="i" begin="${storeRow.pageNum}" end="${storeRow.count}" step="1">
									<li><a href="#" onclick="pageIndex(${i})">${i}</a></li>
								</c:forEach>
								<li><a href="#" onclick="pageNext(${storeRow.pageNum},${storeRow.total},${storeRow.listNum},${storeRow.pageList})">&rsaquo;</a></li>
								<li><a href="#" onclick="pageLast(${storeRow.pageNum},${storeRow.total},${storeRow.listNum},${storeRow.pageList})">&raquo;</a></li>
							</ul>
							<input type="button" id="addStoreBtn" value="매장등록" class="btn btn-outline-primary" onclick="insertStore()">
						</div>
					</form>
				</div>
					
			</div>
		</div>
	</div>
					<!-- 매장 등록  modal -->
<!-- 	<div class="modal fade" id="insert" role="dialog"> -->
<!-- 		<div class="modal-dialog"> -->
<!-- 			<!-- Modal content--> -->
<!-- 			<div class="modal-content"> -->
<!-- 				<div class="modal-header"> -->
<!-- 					<button type="button" class="close" data-dismiss="modal">&times;</button> -->
<!-- 					<h4 class="modal-title">매장 등록</h4> -->
<!-- 				</div> -->
<!-- 				<div class="modal-body"> -->
<!-- 					<form action="#" role="form" method="post" id="frmInsert"></form> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> 매장 등록 modal 끝 -->
<!-- 	<div> -->
	
<%@include file="../footer.jsp" %>
</div>
<%-- 	${lists} --%>
<%-- 	${storeRow} --%>
		

</body>
</html>