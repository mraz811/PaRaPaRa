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
		left: 0px;
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
		top: 305px;
		position: absolute;
	}
	#waitingDetail{
		width:510px;
		height: 130px;
		left : 510px;
		top: 305px;
		position: absolute;
	}
</style>
</head>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript">
	function makeMenuDetail(request_seq){
		$.ajax({
			url : "./selMakeReqDetail.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq},
			dataType : "json",
			success : function(obj){
				var makingDetail = document.getElementById("makingDetail");
				makingDetail.innerHTML = "<div>"+obj.makeMenu.rnum+"</div>"
										  +"<div>"+obj.makeMenu.request_time+"</div>"
										  +"<div>"+obj.makeMenu.menu_name+"</div>";
			},error : function(obj){
				alert("등록에 실패하였습니다."); //성공햇는데 error로 넘어옴 ㅡㅡ; producer 없애고 JSONObject로 던져서 해결함
			}
		})
	}
	function waitMenuDetail(request_seq){
		$.ajax({
			url : "./selWaitReqDetail.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq},
			dataType : "json",
			success : function(obj){
				var makingDetail = document.getElementById("waitingDetail");
				makingDetail.innerHTML = "<div>"+obj.makeMenu.rnum+"</div>"
										  +"<div>"+obj.makeMenu.request_time+"</div>"
										  +"<div>"+obj.makeMenu.menu_name+"</div>";
			},error : function(obj){
				alert("등록에 실패하였습니다."); //성공햇는데 error로 넘어옴 ㅡㅡ; producer 없애고 JSONObject로 던져서 해결함
			}
		})
	}
	function changeStatus(){
		
	}
</script>
<body>
	<div id="container">
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
							
							<td style="width: 50px;height: 28px" onclick="makeMenuDetail(${make.request_seq})">${make.rnum}</td>
							<td style="width: 300px;height: 28px" onclick="makeMenuDetail(${make.request_seq})">${make.menu_name}</td>
							<td style="width: 100px;height: 28px" onclick="makeMenuDetail(${make.request_seq})">${fn:substring(make.request_time,11,19)}</td>
							<td style="width: 35px;height: 28px"><input type="button" value="완료" onclick="changeStatus(${make.request_seq})"/></td>
							
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
						<td style="width: 35px;height: 28px">확인</td>
						<td style="width: 35px;height: 28px">환불</td>
					</tr>
					<c:forEach begin="0" end="${fn:length(waitLists)}" items="${waitLists}" var="wait" varStatus="vs">
						<tr>
							<td style="width: 50px;height: 28px" onclick="waitMenuDetail(${wait.request_seq})">${wait.rnum}</td>
							<td style="width: 260px;height: 28px" onclick="waitMenuDetail(${wait.request_seq})">${wait.menu_name}</td>
							<td style="width: 100px;height: 28px" onclick="waitMenuDetail(${wait.request_seq})">${fn:substring(wait.request_time,11,19)}</td>
							<td style="width: 35px;height: 28px"><input type="button" value="확인"/></td>
							<td style="width: 35px;height: 28px"><input type="button" value="환불"/></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div id="waitingDetail">
		</div>
	</div>
</body>
</html>