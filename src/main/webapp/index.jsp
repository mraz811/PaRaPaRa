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
<a href="./menuChoice.do?owner_menu=1,3,5&owner_seq=7">업주 메뉴 선택</a><br>
<a href="./selAllMenuList.do">전체메뉴조회</a><br>
<a href="./regiNewMenu.do?menu_name=소주&menu_price=4000&menu_category=음료">담당자 메뉴 등록</a><br>
<a href="./regiNewMenu2.do?file_tname=소주임시사진&file_rname=소주진짜사진&file_size=100&menu_seq=9">담당자 메뉴 이미지 등록</a><br>
<a href="./menuModi.do?menu_name=후라이드치킨&menu_price=17000&menu_seq=1">담당자 메뉴 수정</a><br>
<a href="./menuModi2.do?file_tname=후라임시사진&file_rname=후라진짜사진&file_regdate=20190514&file_size=50&menu_seq=1">담당자 메뉴 이미지 수정</a><br>
<a href="./delMenu.do?menu_seq=9">담당자 메뉴 삭제</a><br>
</body>
</html>