<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	function listMove() {
		location.href="./selNoticeList.do";
	}
</script>
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

	<form action="./noticeModify.do" method="post">
	<input type="hidden" name="notice_seq" value="${dto.notice_seq}">
		<table>
			<tr>
				<td>제목</td>
				<td><input name="notice_title" type="text" placeholder="${dto.notice_title}"></td>
			</tr>
			<tr>
				<td colspan="2">
					<textarea name="notice_content" rows="10" cols="30" style="overflow-y:scroll" placeholder="${dto.notice_content}"></textarea>
				</td>
			</tr>
		</table>

			<input type="button" value="글 목록" onclick="listMove()">
			<input type="submit" value="글 수정">

	</form>	
	
						</div><!-- div class=tab-content -->
				</div><!-- div class twoDepth -->
			</div><!-- div class=bodyfixed -->
		</div><!-- div class=bodyFrame -->
		<%@include file="../footer.jsp"%>
	</div>
	
</body>
</html>