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
	#container{
		width: 1020px;
		height: 435px;
	}
	table{
		border-collapse: collapse;
		margin: 10px 100px auto 50px;
	}
	tr,td{
		border: 1px solid black;
	}
	#day{
		margin-top: 10px;
		margin-bottom: 10px;
	}
	#startDay{
		width: 180px;
		height: 40px;
		margin-left: 600px;
	}
	#endDay{
		width: 180px;
		height: 40px;
	}
</style>
</head>
<script type="text/javascript">
	function selRequestStatus(){
		location.href="./selRequestStatus.do";
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
    			 <a class="nav-link" data-toggle="tab" onclick="selRequestStatus()">주문현황</a>
  				</li>
  				<li class="nav-item">
    			 <a class="nav-link" data-toggle="tab" href="#">주문내역</a>
  				</li>
			</ul>
		<div id="day">
			<input id="startDay" name="start" type="date" value="시작일"/>~<input id="endDay" name="end" type="date" value="종료일"/>
		</div>
		<table>
			<tr>
				<td>번호</td>
				<td>주문메뉴명</td>
				<td>총가격</td>
				<td>주문시간</td>
				<td>은행명</td>
				<td>계좌번호</td>
				<td>환불</td>
			</tr>
			<c:forEach begin="0" end="${fn:length(requestList)}" items="${requestList}" var="request" varStatus="vs">
				<tr>
					<td style="width: 50px;height: 31px">${fn:length(requestList)-vs.count+1}</td>
					<td style="width: 420px;height: 31px">${request.menu_name}</td>
					<td style="width: 100px;height: 31px">${request.request_price}</td>
					<td style="width: 100px;height: 31px">${fn:substring(request.request_time,0,10)}</td>
					<td style="width: 100px;height: 31px">${request.request_bank}</td>
					<td style="width: 100px;height: 31px">${request.request_account}</td>
					<td style="width: 45px;height: 31px"><input type="button" value="환불"/></td>
				</tr>
			</c:forEach>
		</table>
		<div id="paging">
		
		</div>	
			</div>
	</div>
	</div>
<%@include file="../footer.jsp" %>
	</div>
	
</body>
</html>