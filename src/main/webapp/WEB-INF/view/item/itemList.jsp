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
						<table class="table">
							<thead>
								<tr>
									<th>품목번호</th>
									<th>품목명</th>
									<th>가격</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(itemLists) eq 0}">
										<tr>
											<th colspan="3">등록된 매장이 없습니다.</th>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${itemLists}" var="itemDto">
											<tr>
												<td>${itemDto.item_seq}</td>
												<td>${itemDto.item_name}</td>
												<td>${itemDto.item_price}</td>
											</tr>
										</c:forEach>	
									</c:otherwise>
								</c:choose>
							
							</tbody>
						</table>
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