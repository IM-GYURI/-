<%@page import="DTO.WiFiDTO"%>
<%@page import="DAO.WiFiDAO"%>
<%@page import="DTO.BookmarkGroupDTO"%>
<%@page import="DAO.BookmarkGroupDAO"%>
<%@page import="DTO.BookmarkDTO"%>
<%@page import="DAO.BookmarkDAO"%>
<%@ page import="java.util.List" %>
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
		<h1>북마크 목록</h1>
		<%@ include file="header.jsp"%>
		<table>
			<thead>
				<tr>
					<th>ID</th>
					<th>북마크 이름</th>
					<th>와이파이명</th>
					<th>등록일자</th>
					<th>비고</th>
				</tr>
			</thead>
			<tbody>
				<%
					BookmarkDAO bookmarkDAO = new BookmarkDAO();
					if (bookmarkDAO.countBookmark() == 0) {
				%>
				<tr>
					<td colspan='5'>북마크 목록이 비어있습니다.</td>
				</tr>
				<%} else {
					List<BookmarkDTO> list = bookmarkDAO.showBookmarkList();
					for (BookmarkDTO bookmarkDTO : list) {
						BookmarkGroupDAO bookmarkGroupDAO = new BookmarkGroupDAO();
						BookmarkGroupDTO bookmarkGroupDTO = bookmarkGroupDAO.selectBookmarkGroup(bookmarkDTO.getGroup_no());
						
						WiFiDAO wifiDAO = new WiFiDAO();
						WiFiDTO wifiDTO = wifiDAO.selectWifi(bookmarkDTO.getMgr_no());
						double distance = wifiDAO.calculateDistance(wifiDTO.getLat(), wifiDTO.getLnt(), wifiDTO.getXSwifiMgrNo());
						System.out.println(distance);
				%>
				<tr>
					<td><%=bookmarkDTO.getGroup_no() %></td>
					<td><%=bookmarkGroupDTO.getName() %></td>
					<td>
						<a href="http://localhost:5501/Mission1/detail-wifi.jsp?mgrNo=<%= wifiDTO.getXSwifiMgrNo() %>&distance=<%=distance%>">
							<%=wifiDTO.getXSwifiMainNm() %>
						</a>
					</td>
					<td><%=bookmarkDTO.getRegister_dttm() %></td>
					<td><a href="http://localhost:5501/Mission1/bookmark-delete.jsp?id=<%=bookmarkDTO.getId() %>">삭제</a></td>
				</tr>
				<%}} %>
			</tbody>
		</table>
	</body>
</html>