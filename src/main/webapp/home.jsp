<%@ page import="DAO.WiFiDAO" %>
<%@ page import="DTO.WiFiDTO" %>
<%@ page import="java.util.*" %>
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
		<h1>와이파이 정보 구하기</h1>
		<%@ include file="header.jsp"%>
		<%
	        String lat = request.getParameter("lat") == null ? "0.0" : request.getParameter("lat");
	        String lnt = request.getParameter("lnt") == null ? "0.0" : request.getParameter("lnt");
	    %>
		
		<div class="input">
			<span>LAT:</span>
			<input type="text" id="lat" value = "<%=lat%>">
			
			<span>, LNT:</span>
			<input type="text" id="lnt" value = "<%=lnt%>">
			
			<button id="btn_cur_position"><span>내 위치 가져오기</span></button>
			<button id="btn_nearest_wifi"><span>근처 WiFi 정보 보기</span></button>
		</div>
		
		<div>
			<table>
				<thead>
					<tr>
						<th>거리(Km)</th>
						<th>관리번호</th>
		                <th>자치구</th>
		                <th>와이파이명</th>
		                <th>도로명주소</th>
		                <th>상세주소</th>
		                <th>설치위치(층)</th>
		                <th>설치유형</th>
		                <th>설치기관</th>
		                <th>서비스구분</th>
		                <th>망종류</th>
		                <th>설치년도</th>
		                <th>실내외구분</th>
		                <th>WIFI접속환경</th>
		                <th>X좌표</th>
		                <th>Y좌표</th>
		                <th>작업일자</th>
				</thead>
				<tbody>
					<%
                    	if (!("0.0").equals(lat) && !("0.0").equals(lnt)) {
	                        WiFiDAO wifiDAO = new WiFiDAO();
	                        List<WiFiDTO> list = wifiDAO.getNearestWifiList(lat, lnt);
	
	                        if (list != null) {
	                            for (WiFiDTO wifiDTO : list) {
	                %>
                    <tr>
                        <td><%=wifiDTO.getDistance()%></td>
                        <td><%=wifiDTO.getXSwifiMgrNo()%></td>
                        <td><%=wifiDTO.getXSwifiWrdofc()%></td>
                        <td><a href="http://localhost:5501/Mission1/detail-wifi.jsp?mgrNo=<%= wifiDTO.getXSwifiMgrNo() %>&distance=<%=wifiDTO.getDistance()%>"><%=wifiDTO.getXSwifiMainNm() %></a></td>
                        <td><%=wifiDTO.getXSwifiAdres1()%></td>
                        <td><%=wifiDTO.getXSwifiAdres2()%></td>
                        <td><%=wifiDTO.getXSwifiInstlFloor()%></td>
                        <td><%=wifiDTO.getXSwifiInstlMby()%></td>
                        <td><%=wifiDTO.getXSwifiInstlTy()%></td>
                        <td><%=wifiDTO.getXSwifiSvcSe()%></td>
                        <td><%=wifiDTO.getXSwifiCmcwr()%></td>
                        <td><%=wifiDTO.getXSwifiCnstcYear()%></td>
                        <td><%=wifiDTO.getXSwifiInoutDoor()%></td>
                        <td><%=wifiDTO.getXSwifiRemars3()%></td>
                        <td><%=wifiDTO.getLat()%></td>
                        <td><%=wifiDTO.getLnt()%></td>
                        <td><%=wifiDTO.getWorkDttm()%></td>
                    </tr>
                <% } %>
                <% } %>
                <% } else { %>
                    <tr>
                    	<td colspan='17'> 위치 정보를 입력하신 후에 조회해 주세요. </td>
                	</tr>
                <% } %>
				</tbody>
			</table>
		</div>
		
		<script>
	        let getCurPosition = document.getElementById("btn_cur_position");
	        let getNearestWifi = document.getElementById("btn_nearest_wifi");
	
	        let lat = null;
	        let lnt = null;
	
	        window.onload = () => {
	            lat = document.getElementById("lat").value;
	            lnt = document.getElementById("lnt").value;
	        }
	
	        getCurPosition.addEventListener("click", function () {
	            if ('geolocation' in navigator) {
	                navigator.geolocation.getCurrentPosition(function (position){
	                    let latitude = position.coords.latitude;
	                    let longitude = position.coords.longitude;
	                    document.getElementById("lat").value = latitude;
	                    document.getElementById("lnt").value = longitude;
	                })
	            } else{
	                alert("위치 정보 확인 불가 : 직접 입력")
	            }
	        });
	
	        getNearestWifi.addEventListener("click", function (){
				let latitude = document.getElementById("lat").value;
				let longitude = document.getElementById("lnt").value;
	
				if (latitude !== "" || longitude !== "") {
					window.location.assign("http://localhost:5501/Mission1/home.jsp?lat=" + latitude + "&lnt=" + longitude);
				} else {
					alert("위치 정보를 입력하신 후에 조회해주세요.")
				}
			})
	    </script>
	</body>
</html>