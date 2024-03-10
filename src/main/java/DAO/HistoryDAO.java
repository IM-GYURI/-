package DAO;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

import DB.DBConnection;
import DTO.HistoryDTO;

public class HistoryDAO {
	public static Connection connection;
	public static PreparedStatement preparedStatement;
	public static ResultSet resultSet;
	
	public static void searchHistory(String lat, String lnt) {
		connection = null;
		preparedStatement = null;
		resultSet = null;
		
		try {
			connection = DBConnection.connectToDB();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String strDate = sdf.format(new Date());

			String sql = " insert into wifi_history "
					+ " ( lat, lnt, search_dttm ) "
					+ " values ( ?, ?, ? ); ";
					
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, lat);
			preparedStatement.setString(2, lnt);
			preparedStatement.setString(3, strDate);
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(connection, preparedStatement, resultSet);
		}
	}
	
	public List<HistoryDTO> searchHistoryList() {
		List<HistoryDTO> list = new ArrayList<>();
		
		connection = null;
		preparedStatement = null;
		resultSet = null;
		
		try {
			connection = DBConnection.connectToDB();
			String sql = " select * from wifi_history order by id desc ";
			
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				HistoryDTO historyDTO = new HistoryDTO(
						resultSet.getInt("id"), resultSet.getString("lat"), 
						resultSet.getString("lnt"), resultSet.getString("search_dttm"));
				list.add(historyDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(connection, preparedStatement, resultSet);
		}
		
		return list;
	}
	
	public void deleteHistory(String id) {
		connection = null;
		preparedStatement = null;
		resultSet = null;
		
		try {
			connection = DBConnection.connectToDB();
			String sql = " delete from wifi_history where id = ? ";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1,  Integer.parseInt(id));
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(connection, preparedStatement, resultSet);
		}
	}
}