<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>테스트 헤더</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<style type="text/css">
*{
	margin: 0px;
	padding: 0px;
}

.bodyFrame{
/* 	border: 1px solid red; */
	width: 1060px;
	height: 540px;
	right: 0px;
	position: absolute;
	margin: 0px;
	padding: 0px;
}

.table-size{
	border: 1px solid blue;
	width: 800px;
	height: 300px;
}
#container{
	width: 1260px;
	height: 610px;
	position: relative;
	
}
#header{
	border: 1px solid black;
	width: 200px;
	height: 610px;
	position: absolute;
}

#footer{
	border: 1px solid green;
	width: 1060px;
	height: 70px;
	position: absolute;
	bottom: 0px;
	right: 0px;
}

.bodyfixed{
	border: 1px dotted red;
	width: 1060px;
	height: 540px;
	right: 0px;
	position: relative;
	margin: 0px;
	padding: 0px;
}

.oneDepth{
	border: 1px dotted gray;
	width: 1020px;
	height: 50px; 
	margin: 10px 20px;
}

.twoDepth{
	border: 1px dotted red;
	width: 1020px;
	height: 460px;
	margin: 10px 20px;
}

.headConts{
	margin: 0px;
	padding: 0px;
}

#paraPhoto{
	border: 1px dotted green;
	margin: 0px;
	width: 200px;
	height: 100px; 
}

.welcomeMsg{
	border: 1px dotted red;
	width: 200px;
	height: 75px;
}

</style>
</head>

<%-- 로그인 세션 : ${loginDto} --%>

<div id="header" class="container-fluid">
  <div class="row content">
	<div class="headConts" align="center">
	<div id="paraPhoto"><a href="main.do">사진 넣을 공간</a></div>
	<c:if test="${loginDto.auth eq 'S'}">
		<div class="welcomeMsg"><p>관리자님 환영합니다!</p>
	      <input type="button" class="btn btn-outline-success" value="마이페이지" onclick="chkMyPage()">
	      <input type="button" class="btn btn-outline-success" value="로그아웃" onclick="logout('${loginDto.auth}')">
		</div>
	    <hr>
	    <ul class="nav nav-pills flex-column">
	      <li class="nav-item"><a class="nav-link" href="#">
	      	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="공지사항"></a>
	      </li>
	      <li class="nav-item"><a class="nav-link" href="./selAdminList.do">
	      	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="회원관리"></a>
	      </li>
	      <li class="nav-item"><a class="nav-link" href="#">
	      	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="통　　계"></a>
	      </li>
	    </ul><br>
	</c:if>
	  
	
	<c:if test="${loginDto.auth eq 'A'}">
		<div class="welcomeMsg"><p>${loginDto.admin_name}님 환영합니다!</p>
	      <input type="button" class="btn btn-outline-success" value="마이페이지" onclick="chkMyPage()">
	      <input type="button" class="btn btn-outline-success" value="로그아웃" onclick="logout('${loginDto.auth}')">
		</div>
		<hr>
		<ul class="nav nav-pills flex-column">
	        <li class="nav-item"><a class="nav-link" href="#">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="공지사항"></a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="#">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="메　　뉴"></a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="./selOwnerList.do?loc_code=${loginDto.loc_code}">
	        	<input style="width: 160px;" type="button" class="btn btn-outline-primary" value="회원관리"></a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="#">매장관리</a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="#">채　　팅</a>
	        </li>
	        <li class="nav-item"><a class="nav-link" href="#">통　　계</a>
	        </li>
	 	</ul><br>
	</c:if>
	<c:if test="${loginDto.auth eq 'U'}">
		<div><p>파라파라 역삼역점</p>
	      <input type="button" class="btn btn-outline-success" value="마이페이지" onclick="chkMyPage()">
	      <input type="button" class="btn btn-outline-success" value="로그아웃" onclick="logout('${loginDto.auth}')">
		</div>
		<hr>
		<ul class="nav nav-pills flex-column">
	        <li class="nav-item"><a class="nav-link" href="#">공지사항　</a></li>
	        <li class="nav-item"><a class="nav-link" href="#">메　　뉴　</a></li>
	        <li class="nav-item"><a class="nav-link" href="#">주　　문　</a></li>
	        <li class="nav-item"><a class="nav-link" href="#">매장관리　</a></li>
	        <li class="nav-item"><a class="nav-link" href="#">아르바이트</a></li>
	        <li class="nav-item"><a class="nav-link" href="#">채　　팅　</a></li>
	        <li class="nav-item"><a class="nav-link" href="#">통　　계　</a></li>
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