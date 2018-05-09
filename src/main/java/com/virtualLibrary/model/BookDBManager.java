package com.virtualLibrary.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.virtualLibrary.utils.Queries;

public class BookDBManager {
	
	private Connection connection;
	private Statement statement;
	private ResultSet rs;
	private PreparedStatement preparedStatement;
	Book book;
	
	public BookDBManager(Book book) {
        this.book = book;	
	}
	
	private List<List<String>> getReviews() {
		List<List<String>> reviews = new ArrayList<List<String>> ();
		try {
		    preparedStatement = connection.prepareStatement(
					                                  Queries.getReviews);
			preparedStatement.setString(1, book.getISBN());
			rs = preparedStatement.executeQuery();
	        while(rs.next()) {
				List<String> record = new ArrayList<String>();
				record.add(rs.getString("review"));
				record.add(rs.getString("reviewer"));
				reviews.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   return reviews;

	}
	
	public void addReview(String review, String reviewer) {
		List<String> record = new ArrayList<String> ();
		record.add(review);
		record.add(reviewer);
		book.getReviews().add(record);
		try {
		    preparedStatement = connection.prepareStatement(
					                                  Queries.addReview);
			preparedStatement.setString(1, review);
			preparedStatement.setString(2, reviewer);
			preparedStatement.setString(3, book.getISBN());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private double getRate() {
		ResultSet rs = getBook(book.getISBN());
		double rateNo = 0;
		try {
			rs.next();
			rateNo = rs.getDouble("rate");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rateNo;
	}

	private int getRateNo() {
		ResultSet rs = getBook(book.getISBN());
		System.out.println(rs.toString());
		int rateNo = 0;
		try {
			rs.next();
			rateNo = rs.getInt("rateNo");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rateNo;
	}
	
	public void updateRate(double rate) {
		double currRate = getRate();
		int rateNo = getRateNo();
		double newRate = ( currRate * rateNo + rate ) / ++rateNo;
		book.setRateDBNo(rateNo);
		book.setRatingDB(newRate);
		
		try {
		    preparedStatement = connection.prepareStatement(
					                                  Queries.updateRate);
			preparedStatement.setDouble(1, newRate);
			preparedStatement.setInt(2, rateNo);
			preparedStatement.setString(3, book.getISBN());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}

	private boolean checkExists(String ISBN) {
		boolean exits =  false;
	    try {
			exits = getBook(ISBN).next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return exits;
	}
	
	private ResultSet getBook(String ISBN) {
		System.out.println(ISBN);
		try {
			System.out.println(preparedStatement);
		    preparedStatement = connection.prepareStatement(
					                                  Queries.selectBook);
			preparedStatement.setString(1, ISBN);
			rs = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public void insertBook() {
		try {
		    preparedStatement = connection.prepareStatement(
					                                  Queries.insertBook);
			preparedStatement.setString(1, book.getTitle());
			preparedStatement.setString(2, book.getISBN());
			preparedStatement.setDouble(3, 0);
			preparedStatement.setDouble(4, 0);
	        preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void build() {
		this.book = book;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book-library", "root", "");
			statement = connection.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(!checkExists(book.getISBN())){
	        insertBook();
			book.setRateDBNo(0);
			book.setRatingDB(0.0);
			book.setReviews(new ArrayList<List<String>>());
		} else {
			book.setRateDBNo(getRateNo());
			book.setRatingDB(getRate());
			book.setReviews(getReviews());
		}
	}
}
