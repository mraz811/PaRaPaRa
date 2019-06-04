<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PaRaPaRa</title>
<style type="text/css">
.card{
	margin-top: 10px;
}
</style>
</head>
<body>
<div id="container">
<%@include file="./header.jsp" %>
	<div class="bodyFrame">
	<div class="bodyfixed">
		<div class="oneDepth">
			<!-- oneDepth에 적힐 내용이 들어감 ex)매장관리 -->
			
			
		</div> <!-- div class=oneDepth -->
		<div class="twoDepth">
			<!-- onDepth 안에 있는 twoDepth가 들어감 ex)1depth가 매장관리일 경우 a 태그에 적힐 내용은 일정관리, 재고, 발주 등  -->
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link active" data-toggle="tab" style="border: 1px solid rgb(21,140,186);" ><strong>PaRaPaRa</strong></a>
  				</li>
			</ul>
			<div class="tab-content">
				<!-- 각자 내용들.. -->
			
			<c:if test="${loginDto.auth eq 'S'}">
			<div class="card">
				<div class="card-body">
    			<h4 class="card-title">공지사항</h4>
    			<h6 class="card-subtitle mb-2 text-muted">담당자 공지사항</h6>
    			<p class="card-text">새로운 공지사항이 있는지 확인하러 가기!</p>
    			<a href="./selNoticeList.do" class="card-link">여기를 클릭하세요</a>
    			<a href="#" class="card-link">Another link</a>
				</div>
			</div>

			<div class="card">
				<div class="card-body">
    			<h4 class="card-title">담당자 등록 바로가기</h4>
    			<h6 class="card-subtitle mb-2 text-muted">메인에 뭐넣지</h6>
    			<p class="card-text">메인에 뭐넣을지 다같이 고민좀 해봅시다. 일단 담당자 등록~ 바로가기~ &lt;'-'&gt;</p>
    			<a href="./adminRegiForm.do" target="_blank" class="card-link">담당자 등록</a>
				</div>
			</div>
			</c:if>
			
			<c:if test="${loginDto.auth eq 'A'}">
			<div class="card">
				<div class="card-body">
    			<h4 class="card-title">공지사항</h4>
    			<h6 class="card-subtitle mb-2 text-muted">담당자 공지사항</h6>
    			<p class="card-text">새로운 공지사항이 있는지 확인하러 가기!</p>
    			<a href="./selNoticeList.do" class="card-link">여기를 클릭하세요</a>
    			<a href="#" class="card-link">Another link</a>
				</div>
			</div>

			<div class="card">
				<div class="card-body">
    			<h4 class="card-title">매장/업주 등록 바로가기</h4>
    			<h6 class="card-subtitle mb-2 text-muted">메인에 뭐넣지</h6>
    			<p class="card-text">메인에 뭐넣을지 다같이 고민좀 해봅시다. 일단 매장 등록~ 회원 등록~ 바로가기~ &lt;'-'&gt;</p>
    			<a href="./regiStoreForm.do" target="_blank" class="card-link">매장 등록</a>
    			<a href="./ownerRegiForm.do" target="_blank" class="card-link">업주 등록</a>
				</div>
			</div>
			</c:if>
			
			<c:if test="${loginDto.auth eq 'U' }">
				<jsp:forward page="/selNoticeList.do"/>
			</c:if>
			
			
			
			
			
			
			</div> <!-- div class=tab-content -->
			
		</div> <!-- div class twoDepth -->
	</div> <!-- div class=bodyfixed -->
	</div> <!-- div class=bodyFrame -->
<%@include file="./footer.jsp" %>
</div> <!-- div id=container -->
</body>
</html>