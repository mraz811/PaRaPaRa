<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>noticeWriteForm</title>
</head>
<script type="text/javascript">
	function listMove() {
		location.href="./selCal.do";
	}
</script>
<body>

<div>
	<div>
		
		<form role="form" action="./regiNotice.do" method="post">
<!-- 			<input type="hidden" name="id" value=""> session 에 아이디 정보 담겨있어서 컨트롤러로 보낼 필요 없고,
										컨트롤러에서 바로 세션에서 가져오면 됨.  -->
			
			<div>
				<label>제목</label>
				<input type="text" placeholder="제목을 입력하세요" name="notice_title" required="required">
			</div>
			
			<div>
				<textarea rows="10" cols="28" name="notice_content" placeholder="내용을 입력하세요"></textarea>
			</div>
			
			<div>
				<input type="submit" value="글 등록">
				<input type="button" value="목록이동" onclick="listMove()">
			</div>			

		</form>
	</div>
	
</div>

</body>
</html>