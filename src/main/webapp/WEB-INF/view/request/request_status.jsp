<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	table{
		border-collapse: collapse;
	}
	tr,td{
		border: 1px solid black;
	}
	#container{
		width: 1020px;
		height: 435px; 
		position: relative;
	}
	#make{
		width: 510px;
		height: 435px;
		position: absolute;
		left: 20px;
	}
	#wait{
		width: 510px;
		height: 435px;
		position: absolute;
		right: 0px;
	}
	#making{
		width: 140px;
		height: 40px;
		margin: 10px auto 10px 10px;
	}
	#waiting{
		width: 140px;
		height: 40px;
		margin: 10px 360px 10px auto;
	}
	#makingList{
		width: 490px;
		height: 245px;
		overflow: scroll;
	}
	#waitingList{
		width: 490px;
		height: 245px;
		overflow: scroll;
	}
	#makingDetail{
		width:510px;
		height: 130px;
		top: 420px;
		position: absolute;
	}
	#waitingDetail{
		width:510px;
		height: 130px;
		left : 550px;
		top: 420px;
		position: absolute;
	}
</style>
</head>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript">
	function makeMenuDetail(request_seq,rnum){
		$.ajax({
			url : "./selMakeReqDetail.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq},
			dataType : "json",
			success : function(obj){
				var makingDetail = document.getElementById("makingDetail");
				makingDetail.innerHTML = "<div>"+rnum+"</div>"
										  +"<div>"+obj.makeMenu.request_time+"</div>"
										  +"<div>"+obj.makeMenu.menu_name+"</div>";
			},error : function(obj){
				alert("등록에 실패하였습니다."); //성공햇는데 error로 넘어옴 ㅡㅡ; producer 없애고 JSONObject로 던져서 해결함
			}
		})
	}
	function waitMenuDetail(request_seq,rnum){
		$.ajax({
			url : "./selWaitReqDetail.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq},
			dataType : "json",
			success : function(obj){
				var waitingDetail = document.getElementById("waitingDetail");
				waitingDetail.innerHTML = "<div>"+rnum+"</div>"
										  +"<div>"+obj.makeMenu.request_time+"</div>"
										  +"<div>"+obj.makeMenu.menu_name+"</div>";
			},error : function(obj){
				alert("등록에 실패하였습니다."); //성공햇는데 error로 넘어옴 ㅡㅡ; producer 없애고 JSONObject로 던져서 해결함
			}
		})
	}
	function changeStatusCode3(request_seq){
		var os_code = "3";
		$.ajax({
			url : "./updateOrderState.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq,"os_code":os_code},
			success : function(obj){
;				location.reload();
			},error : function(obj){
				alert(obj); 
			}
		})
	}
	function changeStatusCode2(request_seq){
		var os_code = "2";
		$.ajax({
			url : "./updateOrderState.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq,"os_code":os_code},
			success : function(obj){
				location.reload();
			},error : function(obj){
				alert(obj); 
			}
		})
	}
</script>
<body>
	<div id="container">
	<%@include file="../header.jsp" %>
	<div class="bodyFrame">
	<div class="bodyfixed">
		<div class="oneDepth">
		주문
		</div>
		<div class="twoDepth">
			<ul class="nav nav-tabs">
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#">주문현황</a>
  				</li>
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="./selRequestList.do">주문내역</a>
  				</li>
			</ul>
		<div id="make">
			<div id="making">
			제조중
			</div>
			<div id="makingList">
				<table>
					<tr>
						<td style="width: 50px;height: 28px">번호</td>
						<td style="width: 300px;height: 28px">주문메뉴명</td>
						<td style="width: 100px;height: 28px">주문시간</td>
						<td style="width: 35px;height: 28px">완료</td>
					</tr>
					<c:forEach begin="0" end="${fn:length(makeLists)}" items="${makeLists}" var="make" varStatus="vs">
						
						<tr>
							
							<td style="width: 50px;height: 28px" onclick="makeMenuDetail(${make.request_seq},${make.rnum})">${make.rnum}</td>
							<td style="width: 300px;height: 28px" onclick="makeMenuDetail(${make.request_seq},${make.rnum})">
								<c:choose>
									<c:when test="${fn:length(make.menu_name) > 20}">
										${fn:substring(make.menu_name,0,20)}...
									</c:when>
									<c:otherwise>
										${make.menu_name}
									</c:otherwise>
								</c:choose>
							</td>
							<td style="width: 100px;height: 28px" onclick="makeMenuDetail(${make.request_seq},${make.rnum})">${fn:substring(make.request_time,11,19)}</td>
							<td style="width: 35px;height: 28px"><input type="button" value="완료" onclick="changeStatusCode3(${make.request_seq})"/></td>
							
						</tr>
						
					</c:forEach>
				</table>
			</div>
		</div>
		<div id="makingDetail">
		</div>
		<div id="wait">
			<div id="waiting">
			대기중
			</div>
			<div id="waitingList">
				<table>
					<tr>
						<td style="width: 50px;height: 28px">번호</td>
						<td style="width: 260px;height: 28px">주문메뉴명</td>
						<td style="width: 100px;height: 28px">주문시간</td>
						<td style="width: 35px;height: 28px">제조</td>
						<td style="width: 35px;height: 28px">환불</td>
					</tr>
					<c:forEach begin="0" end="${fn:length(waitLists)}" items="${waitLists}" var="wait" varStatus="vs">
						<tr>
							<td style="width: 50px;height: 28px" onclick="waitMenuDetail(${wait.request_seq},${wait.rnum})">${wait.rnum}</td>
							<td style="width: 260px;height: 28px" onclick="waitMenuDetail(${wait.request_seq},${wait.rnum})">
								<c:choose>
									<c:when test="${fn:length(wait.menu_name) > 14}">
										${fn:substring(wait.menu_name,0,14)}...
									</c:when>
									<c:otherwise>
										${wait.menu_name}
									</c:otherwise>
								</c:choose>
							</td>
							<td style="width: 100px;height: 28px" onclick="waitMenuDetail(${wait.request_seq},${wait.rnum})">${fn:substring(wait.request_time,11,19)}</td>
							<td style="width: 35px;height: 28px"><input type="button" value="제조" onclick="changeStatusCode2(${wait.request_seq})"/></td>
							<td style="width: 35px;height: 28px"><input type="button" value="환불"/></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div id="waitingDetail">
		<a href="${loginDto.auth eq 'A'?'./ownerMenuList.do':'./adminAllMenuList.do'}">헤더에서 메뉴 1depth 이동에 쓸꺼임</a>
		</div>
			</div>
	</div>
	</div>
<%@include file="../footer.jsp" %>
	</div>
</body>
</html>