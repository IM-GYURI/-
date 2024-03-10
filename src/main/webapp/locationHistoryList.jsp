<%@page import="DAO.HistoryDAO"%>
<%@ page import="java.util.List" %>
<%@ page import="DTO.HistoryDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<script src="/js/jquery-3.7.1.min.js"></script>
		<title>와이파이 정보 구하기</title>
		<style>
			table, td, th {
				border : 1px solid #dadad8;
				padding : 15px 1px;
			}
			table {
				width : 100%;
				border-collapse : collapse;
				border-spacint : 0 1rem;
			}
			th {
				font-size : 12px;
				color : white;
				background-color : #029d5f;
			}
			td {
				height : 100%;
				text-align : center;
				color : black;
				font-size : 12px;
			}
		</style>
	</head>
	<body>
		<h1>위치 히스토리 목록</h1>
		<%@ include file="header.jsp"%>
		
		<div>
			<%
				HistoryDAO historyDAO = new HistoryDAO();
				List<HistoryDTO> list = historyDAO.searchHistoryList();
				
				String strID = request.getParameter("id");
		        if (strID != null) {
		        	historyDAO.deleteHistory(strID);
		        }
			%>

			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>X좌표</th>
						<th>Y좌표</th>
						<th>조회일자</th>
						<th>비고</th>
					</tr>
				</thead>
				<tbody>
					<% if (list.isEmpty()) { %>
						<tr>
							<td colspan = '5'> 위치 히스토리 목록이 비어있습니다.</td>
						</tr>
					<%} else { %>
					<% for (HistoryDTO historyDTO : list) {%>
						<tr>
							<td><%=historyDTO.getId() %></td>
							<td><%=historyDTO.getLat() %></td>
							<td><%=historyDTO.getLnt() %></td>
							<td><%=historyDTO.getSearchDttm() %></td>
							<td><button onclick="confirmDelete(<%= historyDTO.getId() %>)">삭제</button>
						</tr>
					<%}} %>
				</tbody>
			</table>
		</div>
		
		<script>
		    function confirmDelete(id) {
		        if (confirm("데이터를 삭제하시겠습니까?")) {
		            location.href = "delete-history.jsp?id=" + id;
		        }
		    }
		</script>
	</body>
</html>