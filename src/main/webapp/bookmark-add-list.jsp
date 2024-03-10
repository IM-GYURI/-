<%@page import="DAO.BookmarkDAO"%>
<%@page import="DTO.BookmarkDTO"%>
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
		<%
		    request.setCharacterEncoding("UTF-8");
	
		    String mgrNo = request.getParameter("mgrNo");
		    String gId = request.getParameter("gId");
		    
		    if (gId.equals("none")) {
		        response.sendRedirect(request.getHeader("Referer"));
		        return;
		    }
			
		    BookmarkDTO bookmarkDTO = new BookmarkDTO();
		    bookmarkDTO.setMgr_no(mgrNo);
		    bookmarkDTO.setGroup_no(Integer.parseInt(gId));
		    
		    BookmarkDAO bookmarkDAO = new BookmarkDAO();
		    int affected = bookmarkDAO.insertBookmark(bookmarkDTO);
		%>
	</body>
	<script>
	    <%
	        String text = affected > 0 ? "북마크 데이터를 추가하였습니다." : "북마크 데이터 추가에 실패하였습니다.";
	    %>
	    alert("<%= text %>");
	    location.href = "http://localhost:5501/Mission1/bookmark.jsp";
	</script>
</html>