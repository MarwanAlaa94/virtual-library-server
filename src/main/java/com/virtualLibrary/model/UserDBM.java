package com.virtualLibrary.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Authentication.User;

import com.virtualLibrary.utils.Queries;

public class UserDBM {
	private Connection connection;
	private ResultSet rs;
	private User user;
	private static final int FAVORITE_ID = 1;
	private static int READ_ID = 2;
	private static int TO_READ_ID = 3;
	public UserDBM(User user) {
        this.user = user;
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book-library", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getFavorites() {
		return getBooks(FAVORITE_ID);
	}
	
	public List<String> getRead() {
		return getBooks(READ_ID);
	}
	
	public List<String> getToRead() {
		return getBooks(TO_READ_ID);
	}
	
	public void removeFromFavorites(String isbn) {
		if (isbn == null || isbn.length() == 0)throw new IllegalArgumentException();
		removeRecord(isbn, FAVORITE_ID);
	}
	
	public void addToFavorites(String isbn) {
		if (isbn == null || isbn.length() == 0)throw new IllegalArgumentException();
		addRecord(isbn, FAVORITE_ID);
	}
	
	public void removeFromRead(String isbn) {
		if (isbn == null || isbn.length() == 0)throw new IllegalArgumentException();
		removeRecord(isbn, READ_ID);
	}
	
	public void addRead(String isbn) {
		if (isbn == null || isbn.length() == 0)throw new IllegalArgumentException();
		removeRecord(isbn, TO_READ_ID);
		addRecord(isbn, READ_ID);
	}
	
	public void removeFromToBeRead(String isbn) {
		if (isbn == null || isbn.length() == 0)throw new IllegalArgumentException();
		removeRecord(isbn, TO_READ_ID);
	}
	
	public void addToBeRead(String isbn) {
		if (isbn == null || isbn.length() == 0)throw new IllegalArgumentException();
		removeRecord(isbn, READ_ID);
		addRecord(isbn, TO_READ_ID);
	}

	public boolean checkFav(String isbn) {
		if (isbn == null || isbn.length() == 0)throw new IllegalArgumentException();
		return checkBook(isbn, FAVORITE_ID);
	}
	
	public boolean checkRead(String isbn) {
		if (isbn == null || isbn.length() == 0)throw new IllegalArgumentException();
		return checkBook(isbn, READ_ID);
	}
	
	public boolean checkToRead(String isbn) {
		if (isbn == null || isbn.length() == 0)throw new IllegalArgumentException();
		return checkBook(isbn, TO_READ_ID);
	}
	
	private List<String> getBooks(int type) {
		List<String> books = new ArrayList<String> ();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					                                  Queries.getBooks);
			preparedStatement.setString(1, user.getUserId());
			preparedStatement.setInt(2, type);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				books.add(rs.getString("ISBN"));
				System.out.println(rs.getString("ISBN"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   return books;
	}
	
	private void addRecord(String isbn, int type) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
			        Queries.addBookToUser);
			preparedStatement.setString(1, user.getUserId());
			preparedStatement.setString(2, isbn);
			preparedStatement.setInt(3, type);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void removeRecord(String isbn, int type) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
			        Queries.removeBook);
			preparedStatement.setString(1, user.getUserId());
			preparedStatement.setString(2, isbn);
			preparedStatement.setInt(3, type);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean checkBook(String isbn, int type) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
			        Queries.checkBook);
			preparedStatement.setString(1, user.getUserId());
			preparedStatement.setString(2, isbn);
			preparedStatement.setInt(3, type);
			rs = preparedStatement.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
