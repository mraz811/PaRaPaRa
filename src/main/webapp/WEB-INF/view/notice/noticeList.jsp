<%@page import="com.happy.para.dto.NoticeDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	response.setContentType("text/html; charset=UTF-8");
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>noticeList</title>
<link rel="stylesheet" href="./css/NoticeList.css">
<script type="text/javascript" src="./js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="./js/NoticeList.js"></script>

</head>
<body>
<%-- ${lists.get(0)} --%>
	<div id="container">
		<%@include file="../header.jsp"%>
		<div class="bodyFrame">
			<div class="bodyfixed">
				<div class="oneDepth">
					<!-- oneDepth에 적힐 내용이 들어감 ex)매장관리 -->

				</div>
				<!-- div class=oneDepth -->
				<div class="twoDepth">
					<!-- onDepth 안에 있는 twoDepth가 들어감 ex)1depth가 매장관리일 경우 a 태그에 적힐 내용은 일정관리, 재고, 발주 등  -->
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link" data-toggle="tab"
							href="#home">공지사항</a></li>
					</ul>
					<div class="tab-content">

						<form action="#" id="frm" method="get">
						
						<input type="hidden" name="loginDtoAuth" value="${loginDto.auth}">

							<div>
								<div style="height:350px;">
								<table id="noticeTable" class="table table-hover">
								
									<tr class="table-primary">
										<th>NO.</th>
										<th>제목</th>
										<th>작성자</th>
										<th>등록일</th>
									</tr>

								<%-- 
									<jsp:useBean id="format"
										class="com.happy.para.common.NoticeInputList" scope="page" />
									<jsp:setProperty property="lists" name="format"
										value="${lists}" />
									<jsp:getProperty property="listformat" name="format" />
								 --%>
								
								<c:forEach var="dto" items="${lists}" varStatus="vs">
									<tr>
										<td>${vs.count}</td>
										
<%-- 									<td><a href="./selNoticeDetail.do?notice_seq="+${dto.notice_seq}+"&loginDtoAuth="+${loginDto.auth}>${dto.notice_title}</a></td> --%>
										<td><a href="./selNoticeDetail.do?notice_seq=${dto.notice_seq}&loginDtoAuth=${loginDto.auth}">${dto.notice_title}</a></td>
										<td>${dto.notice_name}</td>
										<td>${dto.notice_regdate}</td>
									</tr>
								</c:forEach>
								
								</table>
								</div>

								<!-- 현재 페이지, 인덱스, 출력 갯수 -->
<%-- 								${noticRow.index} ${noticRow.pageNum} ${noticRow.listNum} ${noticRow.count} --%>
								<input type="hidden" id="index" name="index" value="${noticRow.index}">
								<input type="hidden" id="pageNum" name="pageNum" value="${noticRow.pageNum}">
								<input type="hidden" id="listNum" name="listNum" value="${noticRow.listNum}">

								<div class="center">
									<ul class="pagination">
										<li><a href="#" onclick="pageFirst(${noticRow.pageList},${noticRow.pageList})">&laquo;</a></li>
										<li><a href="#" onclick="pagePre(${noticRow.pageNum},${noticRow.pageList})">&lsaquo;</a></li>
										
										<c:forEach var="i" begin="${noticRow.pageNum}" end="${noticRow.count}" step="1">
											<li><a href="#" onclick="pageIndex(${i})">${i}</a></li>
										</c:forEach>
										
										<li><a href="#" onclick="pageNext(${noticRow.pageNum},${noticRow.total},${noticRow.listNum},${noticRow.pageList})">&rsaquo;</a></li>
										<li><a href="#" onclick="pageLast(${noticRow.pageNum},${noticRow.total},${noticRow.listNum},${noticRow.pageList})">&raquo;</a></li>
									</ul>
								</div>
								
							</div>

								<c:if test="${loginDto.auth eq 'A'}">
									<div>
										<input type="button" value="글쓰기" onclick="location.href='./regiNoticeForm.do'">
									</div>
								</c:if>
						</form>
						
						
					</div><!-- div class=tab-content -->
				</div><!-- div class twoDepth -->
			</div><!-- div class=bodyfixed -->
		</div><!-- div class=bodyFrame -->
		<%@include file="../footer.jsp"%>
	</div>
</body>

</html>