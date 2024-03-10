<%@page import="DTO.WiFiDTO"%>
<%@page import="DAO.WiFiDAO"%>
<%@page import="DTO.BookmarkGroupDTO"%>
<%@page import="DAO.BookmarkGroupDAO"%>
<%@page import="DTO.BookmarkDTO"%>
<%@page import="DAO.BookmarkDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>와이파이 정보 구하기</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	<body>
		<h1>북마크 삭제</h1>
		<%@ include file="header.jsp"%>
		<h4>북마크를 삭제하시겠습니까?</h4>
		<%
		    String id = request.getParameter("id");
		
		    BookmarkDAO bookmarkDAO = new BookmarkDAO();
		    BookmarkDTO bookmarkDTO = bookmarkDAO.selectBookmark(Integer.parseInt(id));
		
		    BookmarkGroupDAO bookmarkGroupDAO = new BookmarkGroupDAO();
		    BookmarkGroupDTO bookmarkGroupDTO = bookmarkGroupDAO.selectBookmarkGroup(bookmarkDTO.getGroup_no());
		
		    WiFiDAO wifiDAO = new WiFiDAO();
		    WiFiDTO wifiDTO = wifiDAO.selectWifi(bookmarkDTO.getMgr_no());
	    %>
	</body>
	<form method="post" action="bookmark-delete-submit.jsp">
	    <table>
	        <tr>
	            <th>북마크 이름</th>
	            <td><%=bookmarkGroupDTO.getName()%></td>
	        </tr>
	        <tr>
	            <th>와이파이명</th>
	            <td><%=wifiDTO.getXSwifiMainNm()%></td>
	        </tr>
	        <tr>
	            <th>등록일자</th>
	            <td><%=bookmarkDTO.getRegister_dttm()%></td>
	        </tr>
	        <tr>
	            <td colspan="2">
	                <a href="http://localhost:5501/Mission1/bookmark.jsp">돌아가기</a>
	                <input type="submit" name="delete" value="삭제">
	                <input type="hidden" name="id" value="<%=bookmarkDTO.getId()%>">
	            </td>
	        </tr>
	    </table>
	</form>
</html>