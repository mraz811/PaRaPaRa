
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

input {
	outline: none;
}

#text {
	border: none;
	width: 700px;
	height: 25px;
	font-size: 14px;
	padding-left: 5px;
	margin-top: 5px;
	color: black;
}

#replyName {
	border: none;
	width: 100px;
	height: 25px;
	font-size: 14px;
	padding-left: 5px;
	margin-top: 5px;
	color: black;
	outline: none;
}

#replyRegdate {
 	border: none;
 	font-size: 14px;
 	padding-left: 5px;
 	padding: 0px;
 	margin-top: 5px;
 	outline: none;
 	color: black;
}

#del { 
	width:105px;
 	background-color: lightgray;
 	border: none;
 	background-color: white;
 	font-size: 14px;
 	color: red;
 	outline: none;
 	display: inline;
 	margin-left: 3px;
}

#del:hover {
	background-color: lightgray;
}

#modify {
	position: absolute;
	right: 192px;
	bottom: 383px;
}

#delete {
	position: absolute;
	right: 110px;
	bottom: 383px;
}

#backBtn {
	position: absolute;
	right: 30px;
	bottom: 383px;
}

#replyBtn {
	position: absolute;
	right: 25px;
	bottom: 94px;
}

.allReply {
	height: 86px;
	overflow-y: scroll;
}

table {
	text-align: center;
}

th {
	width: 120px;
	height: 45px;
	padding: 0px;
}

td {
	height: 45px;
	padding: 5px;
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
					<p>공지사항</p>
				</div>
				<!-- div class=oneDepth -->
				<div class="twoDepth">
					<!-- onDepth 안에 있는 twoDepth가 들어감 ex)1depth가 매장관리일 경우 a 태그에 적힐 내용은 일정관리, 재고, 발주 등  -->
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#home" style="border: 1px solid rgb(21,140,186);"><strong>공지사항</strong></a></li>
					</ul>
					<div class="tab-content">

						<form action="#" method="post" id="frm">

							<input type="hidden" id="notice_seq" name="notice_seq" value="${dto.notice_seq}">
							<input type="hidden" name="loginDtoAuth" value="${loginDto.auth}">

							<table>
								<tr style="border: 1px solid lightgray;">
									<th class="table-primary">작 성 자</th>
									<td style="text-align: left; width: 265px;">${dto.notice_name}</td>
									<th class="table-primary">작 성 일</th>
									<td style="text-align: left;">${dto.notice_regdate}</td>
								</tr>
								<tr>
									<th class="table-primary">제 목</th>
									<td style="text-align: left;" colspan="4">${dto.notice_title}</td>
								</tr>

								<tr>
									<td colspan="4" style="width: 1017px; height: 200px; border: 1px solid pink; text-align: left; overflow-y: scroll;">
										${dto.notice_content}</td>
								</tr>

							</table>

							<!-- 세션 id와 작성자의 일치 여부 판단 -->
							<c:if test="${admin_id eq dto.notice_id}">
								<input class="btn btn-outline-success" type="submit" id="modify" value="수　정" />
							</c:if>

							<c:if test="${admin_id eq dto.notice_id}">
								<input class="btn btn-outline-warning" type="button" id="delete" value="삭　제" />
							</c:if>
							<input class="btn btn-outline-primary" type="button" onclick="listMove()" id="backBtn" value="목　록" />

							<div>
								<input type="text" name="reply_content" id="reply_content" class="form-control form-control" maxlength="65" />
								<input type="button" class="btn btn-link" value="댓글 입력" id="replyBtn" />
							</div>

							<div class="allReply">
								<c:choose>
									<c:when test="${empty Rlists}">
										<span style="color: gray;"> 작성된 댓글이 없습니다. 첫 번째 댓글을 달아주세요! :)</span>
									</c:when>
									<c:otherwise>
										<c:forEach var="i" begin="0" end="${fn:length(Rlists)}" step="1">

											<c:if test="${null ne Rlists[i].reply_content}">
												<div class="in-line">
													<input id="replyName" style="width:100px;" value="${Rlists[i].reply_name}" />
													<input id="text" name="name" readonly="readonly" value="${Rlists[i].reply_content}" />
													<a id="replyRegdate">${Rlists[i].reply_regdate}</a>

													<!-- 세션 id와 작성자의 일치 여부 판단 -->
													<c:if test="${admin_id eq Rlists[i].reply_id}">
														<input type="button" id="del" name="reply_seq" value="삭제" onclick="delReply('${Rlists[i].reply_seq}')" />
													</c:if>
													<c:if test="${owner_id eq Rlists[i].reply_id}">
														<input type="button" id="del" name="reply_seq" value="삭제" onclick="delReply('${Rlists[i].reply_seq}')" />
													</c:if>
												</div>
											</c:if>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</div>
						</form>

					</div>
					<!-- div class=tab-content -->
				</div>
				<!-- div class twoDepth -->
			</div>
			<!-- div class=bodyfixed -->
		</div>
		<!-- div class=bodyFrame -->
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
// 			var notice_seq = document.getElementsByName("notice_seq");
			var frm = document.forms[0];
			if (confirm("선택한 게시글을 삭제하시겠습니까?") == true) {
				frm.action = "./noticeDelete.do";
				frm.submit();
			} else {
				return;
			}
		});
	});

	$(function() {
		$("#replyBtn").click(
				function() {
					var loginDtoAuth = document.getElementsByName("loginDtoAuth")[0].value;
					var reply_content = document.getElementsByName("reply_content");
					var notice_seq = document.getElementsByName("notice_seq");

					if (reply_content[0].value == "") {
						alert("작성된 댓글이 없습니다.");
					} else {
						var frm = document.forms[0];
						frm.action = "./replyWrite.do";
						frm.submit();
					}
				});
	});

	function delReply(seq) {

		var reply_seq = new String(seq);

		var frm = document.forms[0];
		frm.action = "./replyDel.do?reply_seq=" + reply_seq;
		frm.submit();

	}

	function listMove() {
		location.href = "./selNoticeList.do";
	}

</script>
</html>