
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


a[id="replyName"] {
	width: 15%;
	height: 100%;
	border: none;
	font-size: 14px;
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
	font-size:  14px;
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
	font-size:  14px;
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
	font-size:  14px;
	color: #042AaC;
	outline: none;
	display: inline;
	margin-left: 3px;
	box-sizing: border-box;
}

input[id=del]:hover {
	background-color: lightgray;
}

.modiBtn{
	position : absolute;
	right : 172px;
	bottom : 333px;
}

.delBtn{
	position : absolute;
	right : 90px;
	bottom : 333px;
}

.backBtn{
	position : absolute;
	right : 10px;
	bottom : 333px;
}

th{
	text-align: center;
}

</style>
<title>noticeDetail</title>


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
		<input type="hidden" name="loginDtoAuth" value="${loginDto.auth}">

		<table>
			<tr>
				<th>작성자</th>
				<td>${dto.notice_id}</td>
				<th>작성일</th>
				<td>${dto.notice_regdate}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${dto.notice_title}</td>
				
				<!-- 세션 id와 작성자의 일치 여부 판단 -->
			<!-- loginDtoAuth 값이 A 면 으로 c:if 로 바깥에 묶어준다.  -->
				
<%-- 			<c:if test="${loginDto.admin_id eq dto.notice_id}"> --%>
			<c:if test="${admin_id eq dto.notice_id}">
				<td class="modiBtn"><input class="btn btn-outline-success" type="submit" id="modify" value="수　정"></td>
			</c:if>

				<!-- 세션 id와 작성자의 일치 여부 판단 -->
			<c:if test="${admin_id eq dto.notice_id}">
				<td class="delBtn"><input class="btn btn-outline-warning" type="button" id="delete" value="삭　제" ></td>
			</c:if>

				<td class="backBtn"><input class="btn btn-outline-warning" type="button" onclick="listMove()" value="목　록"></td>

			</tr>
			<tr>
				<td colspan="4" style="width:780px; height:200px; border:1px solid pink;">
					${dto.notice_content}
				</td>

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
							
									<a id="replyName">${Rlists[i].reply_name}</a>
									<input type="hidden" name="reply_seq" value="${Rlists[i].reply_seq}" />
									<input id="text" name="name" readonly="readonly" value="${Rlists[i].reply_content}"/>
									
									<a id="replyRegdate">${Rlists[i].reply_regdate}</a>
								
								<!-- 세션 id와 작성자의 일치 여부 판단 -->
							<c:if test="${admin_id eq Rlists[i].reply_id}">
									<input type="button" id="del" name="name" value="삭제"
											onclick="location='./replyDel.do?reply_seq=${Rlists[i].reply_seq}&notice_seq=${Rlists[i].notice_seq}&loginDtoAuth=${loginDto.auth}'"/>
							</c:if>
							<c:if test="${owner_id eq Rlists[i].reply_id}">
									<input type="button" id="del" name="name" value="삭제"
											onclick="location='./replyDel.do?reply_seq=${Rlists[i].reply_seq}&notice_seq=${Rlists[i].notice_seq}&loginDtoAuth=${loginDto.auth}'"/>
							</c:if>
							
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
			var loginDtoAuth = document.getElementsByName("loginDtoAuth")[0].value;
			var reply_content = document.getElementsByName("reply_content");
			var notice_seq = document.getElementsByName("notice_seq");
			
			var frm = document.forms[0];
			
			if(reply_content[0].value == ""){
				alert("작성된 댓글이 없습니다.");
			}else{
				var frm = document.forms[0];
				frm.action = "./replyWrite.do";
				frm.submit();			
			}
		});
	});

	function listMove() {
		location.href="./selNoticeList.do";
	}
	
</script>
</html>