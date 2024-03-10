package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import DB.DBConnection;
import DTO.BookmarkGroupDTO;

public class BookmarkGroupDAO {
	public static Connection connection;
	public static PreparedStatement preparedStatement;
	public static ResultSet resultSet;
	
	public int countBookmarkGroup() {
		int count = 0;
		
		connection = null;
		preparedStatement = null;
		resultSet = null;
		
		try {
			connection = DBConnection.connectToDB();
			String sql = " select count(*) from bookmark_group ";
			
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(connection, preparedStatement, resultSet);
		}
		
		return count; 
	}
	
	public int saveBookmarkGroup(BookmarkGroupDTO bookmarkGroupDTO) {
		int affected = 0;
		
		connection = null;
		preparedStatement = null;
		resultSet = null;
		
		try {
			connection = DBConnection.connectToDB();
			String sql = " insert into bookmark_group (name, order_num, register_dttm) "
					+ " values (?, ?, ? ) ";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bookmarkGroupDTO.getName());
			preparedStatement.setInt(2, bookmarkGroupDTO.getOrder_num());
			preparedStatement.setString(3,bookmarkGroupDTO.getRegister_dttm());
			
			affected = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(connection, preparedStatement, resultSet);
		}
		
		return affected;
	}
	
	public BookmarkGroupDTO selectBookmarkGroup(int id) {
		BookmarkGroupDTO bookmarkGroupDTO = new BookmarkGroupDTO();
		
		connection = null;
		preparedStatement = null;
		resultSet = null;
		
		try {
			connection = DBConnection.connectToDB();
			String sql = " select * from bookmark_group where id = ? ";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				bookmarkGroupDTO.setId(resultSet.getInt("id"));
				bookmarkGroupDTO.setName(resultSet.getString("name"));
				bookmarkGroupDTO.setOrder_num(resultSet.getInt("order_num"));
				bookmarkGroupDTO.setRegister_dttm(resultSet.getString("register_dttm"));
				bookmarkGroupDTO.setUpdate_dttm(resultSet.getString("update_dttm"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(connection, preparedStatement, resultSet);
		}
	
		return bookmarkGroupDTO;
	}
	
	public List<BookmarkGroupDTO> showBookmarkGroupList() {
		List<BookmarkGroupDTO> list = new ArrayList<>();
		
		connection = null;
		preparedStatement = null;
		resultSet = null;
		
		try {
			connection = DBConnection.connectToDB();
			String sql = " select * from bookmark_group order by order_num ";
			
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				BookmarkGroupDTO bookmarkGroupDTO = BookmarkGroupDTO.builder()
						.id(resultSet.getInt("id"))
						.name(resultSet.getString("name"))
						.order_num(resultSet.getInt("order_num"))
						.register_dttm(resultSet.getString("register_dttm"))
						.update_dttm(resultSet.getString("update_dttm"))
						.build();
				
				list.add(bookmarkGroupDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(connection, preparedStatement, resultSet);
		}
		
		return list;
	}
	
	public int updateBookmarkGroup(int id, String name, int order_num) {
		int result = 0;
		
		connection = null;
		preparedStatement = null;
		resultSet = null;
		
		try {
			connection = DBConnection.connectToDB();
			String sql = " update bookmark_group "
					+ " set name = ?, order_num = ?, update_dttm = ? where id = ? ";
			
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1,  name);
			preparedStatement.setInt(2,  order_num);
			preparedStatement.setString(3,  String.valueOf(Timestamp.valueOf(LocalDateTime.now())));
			preparedStatement.setInt(4,  id);
			
			result = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(connection, preparedStatement, resultSet);
		}
		
		return result;
	}
	
	public int deleteBookmarkGroup(int id) {
		int affected = 0;
		
		connection = null;
		preparedStatement = null;
		resultSet = null;
		
		try {
			connection = DBConnection.connectToDB();
			
			preparedStatement = connection.prepareStatement(" pragma foreign_keys = on ");
			preparedStatement.executeUpdate();
			
			String sql = " delete from bookmark_group where id = ? ";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1,  id);
			affected = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(connection, preparedStatement, resultSet);
		}
		
		return affected;
	}
}