package DAO;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import DB.DBConnection;
import DTO.BookmarkDTO;

public class BookmarkDAO {
    public static Connection connection;
    public static ResultSet resultSet;
    public static PreparedStatement preparedStatement;
    
    public int countBookmark() {
    	int count = 0;
    	
    	connection = null;
        preparedStatement = null;
        resultSet = null;
        
        try {
        	connection = DBConnection.connectToDB();
        	String sql = " select count(*) from bookmark ";
        	
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
    
    public int insertBookmark(BookmarkDTO bookmarkDTO) {
    	int affected = 0;
    	
    	connection = null;
        preparedStatement = null;
        resultSet = null;
        
        try {
        	connection = DBConnection.connectToDB();
        	String sql = " insert or replace into bookmark "
        			+ " (group_no, mgr_no, register_dttm) "
        			+ " values (?, ?, ?) ";
        	
        	preparedStatement = connection.prepareStatement(sql);
        	
        	preparedStatement.setInt(1,  bookmarkDTO.getGroup_no());
        	preparedStatement.setString(2,  bookmarkDTO.getMgr_no());
        	preparedStatement.setString(3,  String.valueOf(new Timestamp(System.currentTimeMillis())));
        	
        	affected = preparedStatement.executeUpdate();
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(connection, preparedStatement, resultSet);
		}
        
    	return affected;
    }
    
    public int deleteBookmark(int id) {
    	int affected = 0;
    	
    	connection = null;
        preparedStatement = null;
        resultSet = null;
        
        try {
        	connection = DBConnection.connectToDB();
        	String sql = " delete from bookmark where id = ? ";
        	
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
    
    public BookmarkDTO selectBookmark(int id) {
    	BookmarkDTO bookmarkDTO = new BookmarkDTO();
    	
    	connection = null;
        preparedStatement = null;
        resultSet = null;
        
        try {
        	connection = DBConnection.connectToDB();
        	String sql = " select * from bookmark where id = ? ";
        	
        	preparedStatement = connection.prepareStatement(sql);
        	
        	preparedStatement.setInt(1,  id);
        	resultSet = preparedStatement.executeQuery();
        	
        	while (resultSet.next()) {
        		bookmarkDTO.setId(resultSet.getInt("id"));
        		bookmarkDTO.setGroup_no(resultSet.getInt("group_no"));
        		bookmarkDTO.setMgr_no(resultSet.getString("mgr_no"));
        		bookmarkDTO.setRegister_dttm(resultSet.getString("register_dttm"));
        	}
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(connection, preparedStatement, resultSet);
		}
        
        return bookmarkDTO;
    }
    
    public List<BookmarkDTO> showBookmarkList() {
    	connection = null;
        preparedStatement = null;
        resultSet = null;
        
        List<BookmarkDTO> list = new ArrayList<>();
        
        try {
        	connection = DBConnection.connectToDB();
        	String sql = " select bookmark.* from bookmark "
        			+ " inner join bookmark_group "
        			+ " on bookmark.group_no = bookmark_group.id "
        			+ " order by bookmark_group.order_num ";
        	
        	preparedStatement = connection.prepareStatement(sql);
        	resultSet = preparedStatement.executeQuery();
        	
        	while (resultSet.next()) {
        		BookmarkDTO bookmarkDTO = BookmarkDTO.builder()
        				.id(resultSet.getInt("id"))
        				.group_no(resultSet.getInt("group_no"))
        				.mgr_no(resultSet.getString("mgr_no"))
        				.register_dttm(resultSet.getString("register_dttm"))
        				.build();
        		
        		list.add(bookmarkDTO);
        	}
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(connection, preparedStatement, resultSet);
		}
        
        return list;
    }
}