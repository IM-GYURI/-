<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="DAO.HistoryDAO" %>
<!DOCTYPE html>
<html>
	<body>
	    <%
		    HistoryDAO historyDAO = new HistoryDAO();
	    	String id = request.getParameter("id");
	    	historyDAO.deleteHistory(id);
	    %>
	   
	   	<script>
	   		location.href = "locationHistoryList.jsp";
	   	</script>
	</body>
</html>