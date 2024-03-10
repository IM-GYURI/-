<%@page import="DAO.BookmarkGroupDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>와이파이 정보 구하기</title>
	</head>
	<body>
		<h1>북마크 그룹 삭제</h1>
		<%@ include file="header.jsp"%>
		<%
		    request.setCharacterEncoding("UTF-8");
	
		    String id = request.getParameter("id");
	
		    BookmarkGroupDAO bookmarkGroupDAO = new BookmarkGroupDAO();
		    int affected = bookmarkGroupDAO.deleteBookmarkGroup(Integer.parseInt(id));
		%>
	</body>
	<script>
		<%
			String text = affected > 0 ? "북마크 그룹 데이터를 삭제하였습니다." : "북마크 그룹 데이터 삭제에 실패하였습니다.";
		%>
		alert("<%= text %>");
		location.href = "http://localhost:5501/Mission1/bookmark-group.jsp";
	</script>
</html>