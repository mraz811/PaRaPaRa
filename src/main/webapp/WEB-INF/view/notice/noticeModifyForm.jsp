<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	response.setContentType("text/html; charset=UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 수정</title>
<style type="text/css">
#backBtn {
	position: absolute;
	right: 30px;
	bottom: 383px;
}

#subBtn {
	position: absolute;
	width: 62px;
	right: 110px;
	bottom: 383px;
}

#titleWrite {
	position: absolute;
	left: 145px;
	bottom: 340px;
	top: 166px;
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
</head>
<script type="text/javascript">
	function listMove() {
		location.href = "./selNoticeList.do";
	}
	
	function modi() {
		
		var WriteTitle = document.getElementById("writeTitle").value;
		var writeContent = document.getElementById("writeContent").value;
		
		title = WriteTitle.replace(/ /g, "");
		content = writeContent.replace(/ /g, "");
		
		if(title == "" || content == ""){
			swal("등록 에러", "제목 및 내용을 입력하세요.", "warning");
		}else{
			var frm = document.forms[0];
			frm.action = "./noticeModify.do";
			frm.submit();			
		}
	}
</script>
<body>
	<div id="container">
		<%@include file="../header.jsp"%>
		<div class="bodyFrame">
			<div class="bodyfixed">
				<div class="oneDepth">
					<!-- oneDepth에 적힐 내용이 들어감 ex)매장관리 -->
					<p>공지사항</p>
				</div>
				<!-- div class=oneDepth -->
				<div class="twoDepth">
					<!-- onDepth 안에 있는 twoDepth가 들어감 ex)1depth가 매장관리일 경우 a 태그에 적힐 내용은 일정관리, 재고, 발주 등  -->
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#home" style="border: 1px solid rgb(21,140,186);"><strong>공지사항</strong></a></li>
					</ul>
					<div class="tab-content">

						<form action="#" method="post">
							<input type="hidden" name="notice_seq" value="${dto.notice_seq}">

							<table>
								<tr style="border-bottom: 1px solid lightgray;">
									<th class="table-primary">작 성 자</th>
									<td style="text-align: left; width: 265px;">${dto.notice_name}</td>
									<th class="table-primary">작 성 일</th>
									<td style="text-align: left;">${dto.notice_regdate}</td>
								</tr>
								<tr>
									<th class="table-primary">제 목</th>
									<td style="text-align: left;" colspan="4" width="895px;"></td>
								</tr>
							</table>
							
							<div id="titleWrite" style="width: 893px;">
								<input class="form-control form-control" id="writeTitle" type="text" name="notice_title" value="${dto.notice_title}" />
							</div>
							
							<input id="subBtn" class="btn btn-outline-success" value="수　정" onclick="modi()">
							<input id="backBtn" class="btn btn-outline-primary" type="button" onclick="listMove()" value="목　록">
							
							<table>
								<tr>
									<td style="padding: 2px; width: 1100px;">
										<div class="form-group">
											<textarea class="form-control" id="writeContent" style="text-align: left;" rows="15" id="comment" name="notice_content"
												>${dto.notice_content}</textarea>
										</div>
									</td>
								</tr>
							</table>

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
</html>