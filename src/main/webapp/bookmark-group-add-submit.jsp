<%@page import="DAO.BookmarkGroupDAO"%>
<%@page import="DTO.BookmarkGroupDTO"%>
<%@ page import="java.sql.Timestamp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>와이파이 정보 구하기</title>
	</head>
	<body>
		<h1>북마크 그룹 추가</h1>
		<%@ include file="header.jsp"%>
		<%
	    	request.setCharacterEncoding("UTF-8");
			
			String name = request.getParameter("name");
			String order_num = request.getParameter("order_num");
			
			BookmarkGroupDTO bookmarkGroupDTO = new BookmarkGroupDTO();
			bookmarkGroupDTO.setName(name);
			bookmarkGroupDTO.setOrder_num(Integer.parseInt(order_num));
			bookmarkGroupDTO.setRegister_dttm(String.valueOf(new Timestamp(System.currentTimeMillis())));
			
			BookmarkGroupDAO bookmarkGroupDAO = new BookmarkGroupDAO();
			int affected = bookmarkGroupDAO.saveBookmarkGroup(bookmarkGroupDTO);
		%>
	</body>
	<script>
		<%
			String text = affected > 0 ? "북마크 그룹 데이터를 추가하였습니다." : "북마크 그룹 데이터 추가에 실패하였습니다";
		%>
		alert("<%=text%>");
		location.href = "http://localhost:5501/Mission1/bookmark-group.jsp";
	</script>
</html>