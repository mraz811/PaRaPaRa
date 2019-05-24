
<%@page import="java.util.List"%>
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
<style type="text/css">
-

 .in-line {
	width: 700px;
	height: 40px;
/* 	text-align: left; */
}

input {
	margin: 0;
}

a[id="replyId"] {
	width: 15%;
	height: 100%;
	border: none;
	font-size: 1em;
 	padding-left: 5px; 
	padding: 0px;
 	margin-top: 5px;
	display: inline;
	outline: none;
	box-sizing: border-box;
	color: black;
/* 	text-decoration: none; */
}

input[id="text"] {
	width: 60%;
	height: 100%;
	border: none;
	font-size: 1em;
 	padding-left: 5px; 
	padding: 0px;
 	margin-top: 5px;
	display: inline;
	outline: none;
	box-sizing: border-box;
	color: black;
}

a[id="replyRegdate"] {
	width: 15%;
	height: 100%;
	border: none;
	font-size: 1em;
 	padding-left: 5px; 
	padding: 0px;
 	margin-top: 5px;
	display: inline;
	outline: none;
	box-sizing: border-box;
	color: black;
}

input[id=del] {
	width: 10%;
	height: 105%;
	background-color: lightgray;
	border: none;
	background-color: white;
	font-size: 1em;
	color: #042AaC;
	outline: none;
	display: inline;
	margin-left: 3px;
	box-sizing: border-box;
}

input[id=del]:hover {
	background-color: lightgray;
}


</style>
<title>noticeDetail</title>

<script type="text/javascript" src="./js/jquery-3.2.1.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script type="text/javascript" src="./js/NoticeList.js"></script>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

<script type="text/javascript">

	$(function() {
		$("#modify").click(function() {
			var notice_seq = document.getElementsByName("notice_seq");
			var frm = document.forms[0];
			frm.action = "./noticeModifyForm.do";
			frm.submit();
		});
	});
	
	$(function() {
		$("#delete").click(function() {
			var notice_seq = document.getElementsByName("notice_seq");
			var frm = document.forms[0];
			if(confirm("선택한 게시글을 삭제하시겠습니까?")==true){
				frm.action = "./noticeDelete.do";
				frm.submit();				
			}else{
				return;
			}
		});
	});

	$(function() {
		$("#replyBtn").click(function() {
			
			var reply_content = document.getElementsByName("reply_content");
			var notice_seq = document.getElementsByName("notice_seq");
			var frm = document.forms[0];
			
			if(reply_content[0].value == ""){
				alert("작성된 댓글이 없습니다.");
			}else{
				var frm = document.forms[0];
				frm.action = "./replyWrite.do?";
				frm.submit();			
			}
		});
	});

	
	
</script>
</head>
<body>

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

	<form action="#" method="post">
		<input type="hidden" id="notice_seq" name="notice_seq" value="${dto.notice_seq}">
		
		<table>
			<tr>
				<td>작성자</td>
				<td>${dto.notice_id}</td>
				<td>작성일</td>
				<td>${dto.notice_regdate}</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>${dto.notice_title}</td>
				
				<!-- 세션 id와 작성자의 일치 여부 판단 -->
<%--			<c:if test="${fn:trim(mem.auth) eq dto.notice_id}"> --%>
				<td><input type="submit" id="modify" value="수정"></td>
<%--			</c:if> --%>
				
				<!-- 세션 id와 작성자의 일치 여부 판단 -->
<%--			<c:if test="${fn:trim(mem.auth) eq dto.notice_id}"> --%>
					<td><input type="button" id="delete" value="삭제" ></td>
<%--			</c:if> --%>
				
			</tr>
			<tr>
				<td colspan="4">
				<textarea rows="10" cols="50" style="overflow-y: scroll">${dto.notice_content}</textarea></td>
			</tr>
		</table>


		<div>
			<input type="text" name="reply_content" id="reply_content" />
			<input type="button" value="댓글 입력" id="replyBtn" onclick="replyBtn()" />
		</div>

<!-- 		<a id="view">댓글 펼치기</a> -->

		<div class="allReply">
			<c:choose>
				<c:when test="${empty Rlists}">

				</c:when>
				<c:otherwise>
					<c:forEach var="i" begin="0" end="${fn:length(Rlists)}" step="1">

						<c:if test="${null ne Rlists[i].reply_content}">

							<div class="in-line">
							
									<a id="replyId">${Rlists[i].reply_id}</a>
<%-- 									<input type="hidden" name="reply_seq">${Rlists[i].reply_seq}</input> --%>
<%-- 									<input id="text" name="name" readonly="readonly">${Rlists[i].reply_content}</input> --%>
									<input type="hidden" name="reply_seq" value="${Rlists[i].reply_seq}" />
									<input id="text" name="name" readonly="readonly" value="${Rlists[i].reply_content}"/>
									
									
									<a id="replyRegdate">${Rlists[i].reply_regdate}</a>
								
								<!-- 세션 id와 작성자의 일치 여부 판단 -->
<%-- 							<c:if test="${fn:trim(mem.auth) eq Rlists.reply_id}"> --%>
									<input type="button" id="del" name="name" value="삭제"
											onclick="location='./replyDel.do?reply_seq=${Rlists[i].reply_seq}&notice_seq=${Rlists[i].notice_seq}'"/>
<%-- 							</c:if> --%>
							</div>

						</c:if>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>


	</form>
	
						</div><!-- div class=tab-content -->
				</div><!-- div class twoDepth -->
			</div><!-- div class=bodyfixed -->
		</div><!-- div class=bodyFrame -->
		<%@include file="../footer.jsp"%>
	</div>
</body>
</html>