<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#container{
		width: 1020px;
		height: 435px;
	}
	#day{
		margin-top: 10px;
		margin-bottom: 10px;
	}
	#startDay{
		width: 180px;
		height: 40px;
		margin-left: 600px;
	}
	#endDay{
		width: 180px;
		height: 40px;
	}
</style>
</head>
<script type="text/javascript" src="./js/requestList.js"></script>
<script type="text/javascript">
	function selRequestStatus(){
		location.href="./selRequestStatus.do";
	}
</script>
<body>
	<div id="container">
	<%@include file="../header.jsp" %>
	<div class="bodyFrame">
	<div class="bodyfixed">
		<div class="oneDepth">
		주문
		</div>
		<div class="twoDepth">
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" onclick="selRequestStatus()">주문현황</a>
  				</li>
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#">주문내역</a>
  				</li>
			</ul>
		<div id="day">
			<input id="startDay" name="start" type="date" value="시작일"/>~<input id="endDay" name="end" type="date" value="종료일"/>
		</div>
		<div class="tab-content">
					<!-- 각자 내용들.. -->
					<form action="#" id="frm" method="post">
		<table class="table">
			<thead>
			<tr>
				<td>번호</td>
				<td>주문메뉴명</td>
				<td>총가격</td>
				<td>주문시간</td>
				<td>은행명</td>
				<td>계좌번호</td>
				<td>환불</td>
			</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${fn:length(requestList) eq 0}">
					<tr>
						<th colspan="7">주문 완료,환불 내역이 없습니다.</th>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach begin="0" end="${fn:length(requestList)}" items="${requestList}" var="request" varStatus="vs">
						<tr>
							<td style="width: 50px;">${fn:length(requestList)-vs.count+1}</td>
							<td style="width: 420px;">${request.menu_name}</td>
							<td style="width: 100px;">${request.request_price}</td>
							<td style="width: 100px;">${fn:substring(request.request_time,0,10)}</td>
							<td style="width: 100px;">${request.request_bank}</td>
							<td style="width: 100px;">${request.request_account}</td>
							<td style="width: 45px;"><input type="button" value="환불"/></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
		<div id="paging">
		<!-- 페이징 처리 -->
			<!-- 출력할 페이지 번호, 출력할 페이지 시작번호, 출력할 게시글 갯수 -->
			<input type="hidden" id="index" name="index" value="${requestRow.index}">
			<input type="hidden" id="pageNum" name="pageNum" value="${requestRow.pageNum}">
			<input type="hidden" id="listNum" name="listNum" value="${requestRow.listNum}">
			
			<div class="center">
				<ul class="pagination">
					<li><a href="#" onclick="pageFirst(${requestRow.pageList},${requestRow.pageList})">&laquo;</a></li>
					<li><a href="#" onclick="pagePre(${requestRow.pageNum},${requestRow.pageList})">&lsaquo;</a></li>
					<c:forEach var="i" begin="${requestRow.pageNum}" end="${requestRow.count}" step="1">
						<li><a href="#" onclick="pageIndex(${i})">${i}</a></li>
					</c:forEach>
					<li><a href="#" onclick="pageNext(${requestRow.pageNum},${requestRow.total},${requestRow.listNum},${requestRow.pageList})">&rsaquo;</a></li>
					<li><a href="#" onclick="pageLast(${requestRow.pageNum},${requestRow.total},${requestRow.listNum},${requestRow.pageList})">&raquo;</a></li>
				</ul>
			</div>
		</div>
			</form>
				</div>
			</div>
	</div>
	</div>
<%@include file="../footer.jsp" %>
	</div>
	
</body>
</html>