<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@include file="../header.jsp" %>
	
	<div class="modal fade" id="modify" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">게시글 수정</h4>
					</div>
					<div class="modal-body">
						<form action="#" role="form" method="post" id="frmModify"></form>
					</div>
				</div>
			</div>
		</div><!-- 수정 modal -->

<%@include file="../footer.jsp" %>
</body>
</html>