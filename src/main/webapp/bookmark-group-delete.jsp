<%@page import="DTO.BookmarkGroupDTO"%>
<%@page import="DAO.BookmarkGroupDAO"%>
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
		<h1>북마크 그룹 삭제</h1>
		<%@ include file="header.jsp"%>
		<div>
			<h4>북마크 그룹을 삭제하시겠습니까?</h4>
		</div>
		
		<%
			String id = request.getParameter("id");
	    	BookmarkGroupDAO bookmarkGroupDAO = new BookmarkGroupDAO();
	    	BookmarkGroupDTO bookmarkGroupDTO = bookmarkGroupDAO.selectBookmarkGroup(Integer.parseInt(id));
		%>
		
		<form method="post" action="http://localhost:5501/Mission1/bookmark-group-delete-submit.jsp">
			<table>
				<tr>
					<th>북마크 이름</th>
					<td><%=bookmarkGroupDTO.getName()%></td>
				</tr>
				<tr>
					<th>순서</th>
					<td><%=bookmarkGroupDTO.getOrder_num() %></td>
				</tr>
				<tr>
					<td colspan='2'>
						<a href="http://localhost:5501/Mission1/bookmark-group.jsp">돌아가기</a>
						<input type="submit" name="delete" value="삭제">
						<input type="hidden" name="id" value="<%=bookmarkGroupDTO.getId() %>">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>