<%@page import="Service.APIService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="EUC-KR">
		<title>와이파이 정보 구하기</title>
		<style>
			div {
				text-align : center;
			}
		</style>
	</head>
	<body>
		<%
			int count = APIService.getPublicWiFiJson();
        	boolean savedSuccessfully = count > 0;
        	boolean alreadySaved = count == -1;
		%>
		
		<div>
			<% if (savedSuccessfully) { %>
				<div>
					<h1><%= count %>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
					<a href="http://localhost:5501/Mission1/home.jsp">홈으로 돌아가기</a>
				</div>
			<% } else if (alreadySaved) { %>
				<div>
					<h1>Open API 와이파이 정보를 전부 가져온 상태입니다.</h1>
					<a href="http://localhost:5501/Mission1/home.jsp">홈으로 돌아가기</a>
				</div>
			<% } else { %>
				<h1>데이터 저장에 실패하였습니다.</h1>
				<a href="http://localhost:5501/Mission1/home.jsp">홈으로 돌아가기</a>
			<% } %>
		</div>
	</body>
</html>