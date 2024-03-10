package DAO;

import java.sql.*;
import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import DB.DBConnection;
import DTO.WiFiDTO;
import static DAO.HistoryDAO.searchHistory;

public class WiFiDAO
{
    public static Connection connection;
    public static PreparedStatement preparedStatement;
    public static ResultSet resultSet;
    
	public WiFiDAO() {}
	
	public static int insertWiFi(JsonArray jsonArray) {
		connection = null;
		preparedStatement = null;
		resultSet = null;
		
		int count = 0;
		
		try {
            connection = DBConnection.connectToDB();
            connection.setAutoCommit(false);

            String sql = " insert into public_wifi "
                    + " ( x_swifi_mgr_no, x_swifi_wrdofc, x_swifi_main_nm, x_swifi_adres1, x_swifi_adres2, "
                    + " x_swifi_instl_floor, x_swifi_instl_ty, x_swifi_instl_mby, x_swifi_svc_se, x_swifi_cmcwr, "
                    + " x_swifi_cnstc_year, x_swifi_inout_door, x_swifi_remars3, lat, lnt, work_dttm) "
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";

            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < jsonArray.size(); i++) {

                JsonObject data = (JsonObject) jsonArray.get(i).getAsJsonObject();

                preparedStatement.setString(1, data.get("X_SWIFI_MGR_NO").getAsString());
                preparedStatement.setString(2, data.get("X_SWIFI_WRDOFC").getAsString());
                preparedStatement.setString(3, data.get("X_SWIFI_MAIN_NM").getAsString());
                preparedStatement.setString(4, data.get("X_SWIFI_ADRES1").getAsString());
                preparedStatement.setString(5, data.get("X_SWIFI_ADRES2").getAsString());
                preparedStatement.setString(6, data.get("X_SWIFI_INSTL_FLOOR").getAsString());
                preparedStatement.setString(7, data.get("X_SWIFI_INSTL_TY").getAsString());
                preparedStatement.setString(8, data.get("X_SWIFI_INSTL_MBY").getAsString());
                preparedStatement.setString(9, data.get("X_SWIFI_SVC_SE").getAsString());
                preparedStatement.setString(10, data.get("X_SWIFI_CMCWR").getAsString());
                preparedStatement.setString(11, data.get("X_SWIFI_CNSTC_YEAR").getAsString());
                preparedStatement.setString(12, data.get("X_SWIFI_INOUT_DOOR").getAsString());
                preparedStatement.setString(13, data.get("X_SWIFI_REMARS3").getAsString());
                preparedStatement.setString(14, data.get("LAT").getAsString());
                preparedStatement.setString(15, data.get("LNT").getAsString());
                preparedStatement.setString(16, data.get("WORK_DTTM").getAsString());

                preparedStatement.addBatch();
                preparedStatement.clearParameters();

                if ((i + 1) % 1000 == 0) {
                    int[] result = preparedStatement.executeBatch();
                    count += result.length;
                    connection.commit();
                }
            }

            int[] result = preparedStatement.executeBatch();
            count += result.length;
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

        } finally {
            DBConnection.close(connection, preparedStatement, resultSet);
        }

        return count;
	}
	
	public static int getCountFromDatabase() {
        int count = 0;
        connection = null;
        preparedStatement = null;
        resultSet = null;

        try {
            connection = DBConnection.connectToDB();
            String sql = "SELECT COUNT(*) AS count FROM public_wifi";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection, preparedStatement, resultSet);
        }

        return count;
    }
	
    public List<WiFiDTO> getNearestWifiList(String lat, String lnt) {
        connection = null;
        preparedStatement = null;
        resultSet = null;

        List<WiFiDTO> list = new ArrayList<>();

        try {

            connection = DBConnection.connectToDB();

            String sql = " SELECT *, " +
                    " round(6371*acos(cos(radians(?))*cos(radians(LAT))*cos(radians(LNT) " +
                    " -radians(?))+sin(radians(?))*sin(radians(LAT))), 4) " +
                    " AS distance " +
                    " FROM public_wifi " +
                    " ORDER BY distance " +
                    " LIMIT 20;";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, Double.parseDouble(lat));
            preparedStatement.setDouble(2, Double.parseDouble(lnt));
            preparedStatement.setDouble(3, Double.parseDouble(lat));

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                WiFiDTO wifiDTO = WiFiDTO.builder()
                        .distance(resultSet.getDouble("distance"))
                        .xSwifiMgrNo(resultSet.getString("x_swifi_mgr_no"))
                        .xSwifiWrdofc(resultSet.getString("x_swifi_wrdofc"))
                        .xSwifiMainNm(resultSet.getString("x_swifi_main_nm"))
                        .xSwifiAdres1(resultSet.getString("x_swifi_adres1"))
                        .xSwifiAdres2(resultSet.getString("x_swifi_adres2"))
                        .xSwifiInstlFloor(resultSet.getString("x_swifi_instl_floor"))
                        .xSwifiInstlTy(resultSet.getString("x_swifi_instl_ty"))
                        .xSwifiInstlMby(resultSet.getString("x_swifi_instl_mby"))
                        .xSwifiSvcSe(resultSet.getString("x_swifi_svc_se"))
                        .xSwifiCmcwr(resultSet.getString("x_swifi_cmcwr"))
                        .xSwifiCnstcYear(resultSet.getString("x_swifi_cnstc_year"))
                        .xSwifiInoutDoor(resultSet.getString("x_swifi_inout_door"))
                        .xSwifiRemars3(resultSet.getString("x_swifi_remars3"))
                        .lat(resultSet.getString("lat"))
                        .lnt(resultSet.getString("lnt"))
                        .workDttm(String.valueOf(resultSet.getTimestamp("work_dttm").toLocalDateTime()))
                        .build();

                list.add(wifiDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection, preparedStatement, resultSet);
        }
        
        searchHistory(lat, lnt);

        return list;
    }
    
    public List<WiFiDTO> selectWifiList(String mgrNo, double distance) {
        connection = null;
        preparedStatement = null;
        resultSet = null;

        List<WiFiDTO> list = new ArrayList<>();

        try {
            connection = DBConnection.connectToDB();
            String sql = " select * from public_wifi where x_swifi_mgr_no = ? ";
            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mgrNo);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            	WiFiDTO wifiDTO = WiFiDTO.builder()
                       .distance(distance)
                        .xSwifiMgrNo(resultSet.getString("X_SWIFI_MGR_NO"))
                        .xSwifiWrdofc(resultSet.getString("X_SWIFI_WRDOFC"))
                        .xSwifiMainNm(resultSet.getString("X_SWIFI_MAIN_NM"))
                        .xSwifiAdres1(resultSet.getString("X_SWIFI_ADRES1"))
                        .xSwifiAdres2(resultSet.getString("X_SWIFI_ADRES2"))
                        .xSwifiInstlFloor(resultSet.getString("X_SWIFI_INSTL_FLOOR"))
                        .xSwifiInstlTy(resultSet.getString("X_SWIFI_INSTL_TY"))
                        .xSwifiInstlMby(resultSet.getString("X_SWIFI_INSTL_MBY"))
                        .xSwifiSvcSe(resultSet.getString("X_SWIFI_SVC_SE"))
                        .xSwifiCmcwr(resultSet.getString("X_SWIFI_CMCWR"))
                        .xSwifiCnstcYear(resultSet.getString("X_SWIFI_CNSTC_YEAR"))
                        .xSwifiInoutDoor(resultSet.getString("X_SWIFI_INOUT_DOOR"))
                        .xSwifiRemars3(resultSet.getString("X_SWIFI_REMARS3"))
                        .lat(resultSet.getString("LAT"))
                        .lnt(resultSet.getString("LNT"))
                        .workDttm(String.valueOf(resultSet.getTimestamp("work_dttm").toLocalDateTime()))
                        .build();
                list.add(wifiDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection, preparedStatement, resultSet);
        }

       return list;
    }

    public WiFiDTO selectWifi(String mgrNo) {
    	WiFiDTO wifiDTO = new WiFiDTO();

        connection = null;
        preparedStatement = null;
        resultSet = null;

        try {
            connection = DBConnection.connectToDB();
            
            String sql = " select * from public_wifi where x_swifi_mgr_no = ? ";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, mgrNo);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                wifiDTO.setXSwifiMgrNo(resultSet.getString("X_SWIFI_MGR_NO"));
                wifiDTO.setXSwifiWrdofc(resultSet.getString("X_SWIFI_WRDOFC"));
                wifiDTO.setXSwifiMainNm(resultSet.getString("X_SWIFI_MAIN_NM"));
                wifiDTO.setXSwifiAdres1(resultSet.getString("X_SWIFI_ADRES1"));
                wifiDTO.setXSwifiAdres2(resultSet.getString("X_SWIFI_ADRES2"));
                wifiDTO.setXSwifiInstlFloor(resultSet.getString("X_SWIFI_INSTL_FLOOR"));
                wifiDTO.setXSwifiInstlTy(resultSet.getString("X_SWIFI_INSTL_TY"));
                wifiDTO.setXSwifiInstlMby(resultSet.getString("X_SWIFI_INSTL_MBY"));
                wifiDTO.setXSwifiSvcSe(resultSet.getString("X_SWIFI_SVC_SE"));
                wifiDTO.setXSwifiCmcwr(resultSet.getString("X_SWIFI_CMCWR"));
                wifiDTO.setXSwifiCnstcYear(resultSet.getString("X_SWIFI_CNSTC_YEAR"));
                wifiDTO.setXSwifiInoutDoor(resultSet.getString("X_SWIFI_INOUT_DOOR"));
                wifiDTO.setXSwifiRemars3(resultSet.getString("X_SWIFI_REMARS3"));
                wifiDTO.setLat(resultSet.getString("LAT"));
                wifiDTO.setLnt(resultSet.getString("LNT"));
                wifiDTO.setWorkDttm(String.valueOf(resultSet.getTimestamp("work_dttm").toLocalDateTime()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection, preparedStatement, resultSet);
        }

        return wifiDTO;
    }
    
    public double calculateDistance(String lat, String lnt, String mgrNo) {
    	double distance = 0;
    	
    	connection = null;
        preparedStatement = null;
        resultSet = null;
        
        try {
        	connection = DBConnection.connectToDB();
        	String sql = " SELECT " +
                    " round(6371*acos(cos(radians(?))*cos(radians(LAT))*cos(radians(LNT) " +
                    " -radians(?))+sin(radians(?))*sin(radians(LAT))), 4) " +
                    " AS distance " +
                    " FROM public_wifi " +
                    " where x_swifi_mgr_no = ? ";
        	
        	preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, Double.parseDouble(lat));
            preparedStatement.setDouble(2, Double.parseDouble(lnt));
            preparedStatement.setDouble(3, Double.parseDouble(lat));
            preparedStatement.setString(4, mgrNo);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                distance = resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection, preparedStatement, resultSet);
        }
        
        return distance;
    }
}
