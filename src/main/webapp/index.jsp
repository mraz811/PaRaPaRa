<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>parapara</title>
</head>
<body>
parapara메인<br>
<%-- <jsp:forward page="./test.do"/> --%>
<a href="./choiceMenuList.do?menu_seq=1,3,5">업주 선택 메뉴 조회</a><br>
<a href="./menuChoice.do?owner_menu=1,3,5&owner_seq=41">업주 메뉴 선택</a><br>
<a href="./selAllMenuList.do">전체메뉴조회</a><br>
<a href="./regiNewMenu.do?menu_name=소주&menu_price=4000&menu_category=음료">담당자 메뉴 등록</a><br>
<a href="./regiNewMenu2.do?file_tname=소주임시사진&file_rname=소주진짜사진&file_size=100&menu_seq=9">담당자 메뉴 이미지 등록</a><br>
<a href="./menuModi.do?menu_name=후라이드치킨&menu_price=17000&menu_seq=1">담당자 메뉴 수정</a><br>
<a href="./menuModi2.do?file_tname=후라임시사진&file_rname=후라진짜사진&file_regdate=20190514&file_size=50&menu_seq=1">담당자 메뉴 이미지 수정</a><br>
<a href="./delMenu.do?menu_seq=9">담당자 메뉴 삭제</a><br>
<hr>
<a href="./regiCustomOrder.do?request_menu=2,4&request_price=40000&store_code=스토어코드1&request_bank=우리은행&request_account=1002-651-065588">고객 주문 입력</a><br>
<a href="./selWaitRequest.do?store_code=스토어코드1">주문상태 대기중 조회</a><br>
<a href="./selWaitReqDetail.do?store_code=스토어코드1&request_seq=2">주문상태 대기중 상세 조회</a><br>
<a href="./updateOrderState.do?os_code=3&store_code=스토어코드2&request_seq=101">주문 상태 업데이트</a><br>
<a href="./selMakeRequest.do?store_code=스토어코드1">주문상태 제조중 조회</a><br>
<a href="./selMakeReqDetail.do?store_code=스토어코드1&request_seq=1">주문상태 제조중 상세 조회</a><br>
<a href="./selRequestList.do?store_code=스토어코드2&start=190514&end=190516">주문 완료,환불 내역 조회</a><br>
<hr>
<a href="./ownerStatsIn.do?store_code=스토어코드1&start=20190514&end=20190516">업주 수익 통계</a><br>
<a href="./ownerStatsOut.do?store_code=스토어코드1&start=20190514&end=20190519">업주 지출 통계</a><br>
<a href="./ownerStatsMenu.do?store_code=스토어코드1&start=20190514&end=20190516">업주 상위판매메뉴 통계</a><br>
<a href="./adminStatsIn.do?store_code=스토어코드1,스토어코드2&start=20190514&end=20190516">담당자,관리자 수익 통계</a><br>
<a href="./adminStatsMenu.do?store_code=스토어코드1,스토어코드2&start=20190514&end=20190516">담당자,관리자 상위판매메뉴 통계</a><br>
</body>
</html>