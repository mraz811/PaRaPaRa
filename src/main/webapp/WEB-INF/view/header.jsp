<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Header</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./css/header.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<script type="text/javascript" src="./js/bootstrap.js"></script>
</head>

<%-- 로그인 세션 : ${loginDto} --%>

<div id="header" class="container-fluid">
  <div class="row content">
	<div class="headConts" align="center">
<!-- 	<div id="paraPhoto"><a href="main.do"><img alt="parapara_logo" src="./imgs/11111.JPG" class="logoImg" ></a></div> -->
	<div id="paraPhoto"><a href="main.do"><img alt="parapara_logo" src="./imgs/logo2.jpg" class="logoImg" ></a></div>
	<c:if test="${loginDto.auth eq 'S'}">
		<div class="welcomeMsg"><p><small>관리자님 환영합니다!</small></p>
	      <input type="button" class="btn btn-outline-success" value="마이페이지" onclick="chkMyPage()">
	      <input type="button" class="btn btn-outline-success" value="로그아웃" onclick="logout('${loginDto.auth}')">
		</div>
	    <hr>
	    <ul class="nav nav-pills flex-column">
	      <li class="nav-item"><a class="nav-link" href="./selNoticeList.do">
	      	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="공지사항"></a>
	      </li>
	      <li class="nav-item"><a class="nav-link" href="./selAdminList.do">
	      	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="회원관리"></a>
	      </li>
	      <li class="nav-item"><a class="nav-link" href="./adminStatsIn.do">
	      	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="통　　계"></a>
	      </li>
	    </ul><br>
	</c:if>
	  
	
	<c:if test="${loginDto.auth eq 'A'}">
		<div class="welcomeMsg"><p><small>${loginDto.admin_name}님 (${loginDto.loc_name})</small></p>
	      <input type="button" class="btn btn-outline-success" value="마이페이지" onclick="chkMyPage()">
	      <input type="button" class="btn btn-outline-success" value="로그아웃" onclick="logout('${loginDto.auth}')">
		</div>
		<hr>
		<ul class="nav nav-pills flex-column">
	        <li class="nav-item"><a class="nav-link" href="./selNoticeList.do">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="공지사항"></a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="./adminAllMenuList.do">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="메　　뉴"></a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="./selOwnerList.do?loc_code=${loginDto.loc_code}">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="회원관리"></a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="./selStoreList.do">
		        <input style="width: 160px;" type="button" class="btn btn-outline-primary" value="매장관리"></a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="./chatList.do?auth=A">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="채　　팅"></a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="./adminStats.do">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="통　　계"></a>
	        </li>
	 	</ul><br>
	</c:if>
	<c:if test="${loginDto.auth eq 'U'}">
		<div class="welcomeMsg"><p><small>${loginDto.owner_name}님 (${loginDto.store_name})</small></p>
	      <input type="button" class="btn btn-outline-success" value="마이페이지" onclick="chkMyPage()">
	      <input type="button" class="btn btn-outline-success" value="로그아웃" onclick="logout('${loginDto.auth}')">
		</div>
		<hr>
		<ul class="nav nav-pills flex-column">
	        <li class="nav-item"><a class="nav-link" href="./selNoticeList.do">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="공지사항"></a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="./ownerMenuList.do">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="메　　뉴"></a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="./selRequestStatus.do">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="주　　문"></a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="./selCal.do">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="매장관리"></a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="./selTimeSheet.do">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="아르바이트"></a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="./chatList.do?auth=U">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="채　　팅"></a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="./ownerStats.do">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="통　　계"></a>
	        </li>
		</ul><br>
	</c:if>

    </div> <!-- headConts -->
  </div>
</div> <!-- id:header -->



<script type="text/javascript">

function logout(auth){
	// 권한에 따른 로그아웃 실행
	location.href="./logout.do?auth="+auth;
};

var chkMyPage = function(){
	// MyPage 가기 위한 비밀번호 입력 페이지로
	location.href="./pwCheckForm.do";
};

</script>
</html>