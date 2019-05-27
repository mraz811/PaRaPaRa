<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	response.setContentType("text/html; charset=UTF-8");
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	function modifyBtn() {
		var modiQty = document.getElementById('modifyQty');

		if (modiQty.value == "수정 하기") {
			modiQty.value = "수정 완료";
			var aty = document.getElementsByName("stock_qty");
			var qty = document.querySelectorAll("input[name*=qty]");
			// 		alert(aty.length);

			var a = document.getElementsByName("Slists[" + 1 + "].stock_qty")[0];
			alert(a.value);

			for (var i = 1; i < qty.length + 1; i++) {
				document.getElementsByName("Slists[" + i + "].stock_qty")[0]
						.removeAttribute("readonly");

			}
		} else {
			modiQty.value == "수정 하기"

			var frm = document.forms[0];
			frm.action = "./stockModi.do";
			frm.submit();
		}
	}
</script>

</head>
<body>


	<div id="container">
		<%@include file="../header.jsp"%>
		<div class="bodyFrame">
			<div class="bodyfixed">
				<div class="oneDepth">
					<!-- oneDepth에 적힐 내용이 들어감 ex)매장관리 -->

				</div>
				<!-- div class=oneDepth -->
				<div class="twoDepth">
					<!-- onDepth 안에 있는 twoDepth가 들어감 ex)1depth가 매장관리일 경우 a 태그에 적힐 내용은 일정관리, 재고, 발주 등  -->
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link" data-toggle="tab"
							id="calendar">CALENDAR</a></li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab"
							id="pao">발주</a></li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab"
							href="#home">재고</a></li>

					</ul>
					<div class="tab-content">

						<script type="text/javascript">
							$(function() {
								$("#calendar").click(function() {
									location.href = "./selCal.do";
								});
								$("#pao").click(function() {
									location.href = "./selPaoList.do";
								});
							});
						</script>

						<form action="#" method="post">
							<input type="text" name="store_code" value="${store_code}" />

							<table class="table table-hover">
								<tr class="table-primary">
									<th>재고 번호<th>
									<th>재고명</th>
									<th>재고수량</th>
									<th>삭제</th>
								</tr>
								

								<c:forEach var="dto" items="${lists}" varStatus="vs">
									<%-- 		<c:forEach var="dto" items="${listsOne}" varStatus="vs"> --%>
										
										<tr>
											<td colspan="2">${vs.count}</td>
											<td>
												<input type="hidden" name="Slists[${vs.count}].stock_seq" value="${dto.stock_seq}" />
												<input type="text" name="Slists[${vs.count}].stock_name" value="${dto.stock_name}" readonly="readonly"
													style="border:none; background-color: none;" />
											</td>
											<td>
												<input type="number" name="Slists[${vs.count}].stock_qty" value="${dto.stock_qty}" readonly="readonly" />
											</td>
	
											<c:if test="${empty dto.item_name}">
												<td>
													<input type="button" onclick="location.href='./delStock.do?stock_seq=${dto.stock_seq}'" value="삭제" />
												</td>
											</c:if>
	
										</tr>
										
								</c:forEach>
								
							</table>

							<input type="button" id="modifyQty" value="수정 하기"
								onclick="modifyBtn()">

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