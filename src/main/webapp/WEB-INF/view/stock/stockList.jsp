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

	${lists}
	<hr>
	<%-- ${itemList} --%>
	<hr>
	${lists[0].stock_name} ${lists[0].stock_qty}
	<hr>
	${listsOne}
	<hr>
	${itemList} ${fn:length(itemList)}

	<form action="#" method="post">
		<input type="text" name="store_code" value="${store_code}" />

		<table>
			<c:forEach var="dto" items="${lists}" varStatus="vs">
				<%-- 		<c:forEach var="dto" items="${listsOne}" varStatus="vs"> --%>
				<tr>
					<td>${vs.count}</td>
					<td>
						<input type="hidden" name="Slists[${vs.count}].stock_seq" value="${dto.stock_seq}" />
						<input name="Slists[${vs.count}].stock_name" value="${dto.stock_name}" />
					</td>
					<td>
					<input type="number" name="Slists[${vs.count}].stock_qty" value="${dto.stock_qty}" readonly="readonly" /></td>

						<c:if test="${empty dto.item_name}">
							<td>
								<input type="button" onclick="location.href='./delStock.do?stock_seq=${dto.stock_seq}'" value="삭제" />
							</td>
						</c:if>
					
				</tr>
			</c:forEach>
		</table>

		<input type="button" id="modifyQty" value="수정 하기" onclick="modifyBtn()">

	</form>
</body>
</html>