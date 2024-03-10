<%@page import="DTO.BookmarkGroupDTO"%>
<%@page import="DAO.BookmarkGroupDAO"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>와이파이 정보 구하기</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	<body>
		<h1>북마크 그룹 관리</h1>
		<%@ include file="header.jsp"%>
		<button onclick="location.href='http://localhost:5501/Mission1/bookmark-group-add.jsp'">북마크 그룹 이름 추가</button>
		<br><br>
		<table>
			<thead>
				<tr>
					<th>ID</th>
					<th>북마크 이름</th>
					<th>순서</th>
					<th>등록일자</th>
					<th>수정일자</th>
					<th>비고</th>
				</tr>
			</thead>
			<tbody>
				<%
					BookmarkGroupDAO bookmarkGroupDAO = new BookmarkGroupDAO();
					if (bookmarkGroupDAO.countBookmarkGroup() == 0) {
				%>
				<tr>
					<td colspan='6'>북마크 그룹이 존재하지 않습니다.</td>
				</tr>
				<%} else {
					List<BookmarkGroupDTO> list = bookmarkGroupDAO.showBookmarkGroupList();
					for (BookmarkGroupDTO bookmarkGroupDTO : list) {
						String update = bookmarkGroupDTO.getUpdate_dttm() == null ? "" : bookmarkGroupDTO.getUpdate_dttm();	
				%>
				<tr>
					<td><%=bookmarkGroupDTO.getId() %></td>
					<td><%=bookmarkGroupDTO.getName() %></td>
					<td><%=bookmarkGroupDTO.getOrder_num() %></td>
					<td><%=bookmarkGroupDTO.getRegister_dttm() %></td>
					<td><%=update %></td>
					<td>
						<a href="http://localhost:5501/Mission1/bookmark-group-update.jsp?id=<%=bookmarkGroupDTO.getId() %>">수정</a>
						<a href="http://localhost:5501/Mission1/bookmark-group-delete.jsp?id=<%=bookmarkGroupDTO.getId() %>">삭제</a>
				</tr>
				<%}} %>
			</tbody>
		</table>
	</body>
</html>