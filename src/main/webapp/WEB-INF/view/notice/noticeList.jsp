<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
공지사항 페이지
${lists}

<!-- <form action="./noticeWriteForm.do" method="get"> -->
<form action="#" id="frm" method="post">

<div>
	<table class="table">
		<tr>
			<th>NO.</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
		</tr>
	<!-- NoticeInputList 이거 추가 해야함! -->	
<%-- 		<jsp:useBean id="format" class="happy.lee.seul.beans.NoticeInputList" scope="page" /> --%>
		<jsp:setProperty property="lists" name="format" value="${lists}" />
		<jsp:getProperty property="listformat" name="format" />
	
	</table>
	
	<!-- 현재 페이지, 인덱스, 출력 갯수 -->
	${row.index} ${row.pageNum} ${row.listNum} ${row.count}
	<input type="hidden" id="index" name="index" value="${row.index}">
	<input type="hidden" id="pageNum" name="pageNum" value="${row.pageNum}">
	<input type="hidden" id="listNum" name="listNum" value="${row.listNum}">
	
	<div class="center">
		<ul class="pagination">
			<li><a href="#"
				onclick="pageFirst(${row.pageList},${row.pageList})">&laquo;</a></li>
			<li><a href="#"
				onclick="pagePre(${row.pageNum},${row.pageList})">&lsaquo;</a></li>
			<c:forEach var="i" begin="${row.pageNum}" end="${row.count}" step="1">
				<li><a href="#" onclick="pageIndex(${i})">${i}</a></li>
			</c:forEach>
			<li><a href="#"
				onclick="pageNext(${row.pageNum},${row.total},${row.listNum},${row.pageList})">&rsaquo;</a></li>
			<li><a href="#"
				onclick="pageLast(${row.pageNum},${row.total},${row.listNum},${row.pageList})">&raquo;</a></li>
		</ul>
	</div>
	
</div>
	
		<!-- 세션 auth 가 담당자일 경우에만 글쓰기 버튼이 보이도록 or 버튼은 보이지만 권한이 없다는 팝업창 뜨도록 -->
<%-- 	<c:if test="${fn:trim(mem.auth) eq 'A'}"> --%>
			<div>
				<input type="button" value="글쓰기" onclick="location.href='./noticeWriteForm.do'">
			</div>
<%-- 	</c:if> --%>
</form>

</body>
</html>