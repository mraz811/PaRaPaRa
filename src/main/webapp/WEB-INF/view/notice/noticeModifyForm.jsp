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
		location.href="./noticeList.do";
	}
</script>
<body>
${dto}

	<form action="./noticeModify.do" method="post">
	<input type="hidden" name="notice_seq" value="${dto.notice_seq}">
		<table>
			<tr>
				<td>제목</td>
				<td><input name="notice_title" type="text"></td>
			</tr>
			<tr>
				<td colspan="2">
					<textarea name="notice_content" rows="10" cols="30" style="overflow-y:scroll"></textarea>
				</td>
			</tr>	
		</table>
	
			<input type="button" value="글 목록" onclick="listMove()">
			<input type="submit" value="글 수정">

	</form>	
</body>
</html>