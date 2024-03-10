<%@page import="DAO.BookmarkDAO"%>
<%@page import="DTO.BookmarkDTO"%>
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
		<h1>북마크 그룹 추가</h1>
		<%@ include file="header.jsp"%>
		<form action="http://localhost:5501/Mission1/bookmark-group-add-submit.jsp">
			<table>
				<tr>
					<th>북마크 이름</th>
					<td><input type="text" name="name"></td>
				</tr>
				<tr>
            		<th>순서</th>
            		<td><input type="text" name="order_num"></td>
        		</tr>
        		<tr>
            		<td style="text-align: center;" colspan="2">
                		<input type="submit" value="추가">
            		</td>
        		</tr>
			</table>
		</form>
	</body>
</html>