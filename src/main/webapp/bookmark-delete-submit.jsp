<%@page import="DAO.BookmarkDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>와이파이 정보 구하기</title>
	</head>
	<body>
		<%
		    request.setCharacterEncoding("UTF-8");
		
		    String id = request.getParameter("id");
		
		    BookmarkDAO bookmarkDAO = new BookmarkDAO();
		    int affected = bookmarkDAO.deleteBookmark(Integer.parseInt(id));
		%>
	</body>
	<script>
	    <%
	        String text = affected > 0 ? "북마크 데이터를 삭제하였습니다." : "북마크 데이터 삭제에 실패하였습니다.";
	    %>
	    alert("<%=text%>");
	    location.href = "http://localhost:5501/Mission1/bookmark.jsp";
	</script>
</html>