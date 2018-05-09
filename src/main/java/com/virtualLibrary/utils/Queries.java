package com.virtualLibrary.utils;

public class Queries {
	public static String selectBook = "select * from book where ISBN = ?;";
	public static String insertBook = "INSERT INTO BOOK (title, ISBN, rate, rateNo) VALUES (?, ?, ?, ?);";
	public static String getReviews = "select review,reviewer from review where ISBN = ?;";
	public static String updateRate = "UPDATE BOOK SET rate = ?, rateNo = ? WHERE ISBN = ?;";
	public static String addReview = "INSERT INTO REVIEW (REVIEW, REVIEWER, ISBN) VALUES (?, ?, ?)";
	public static String addBookToUser = "INSERT INTO USER VALUES (?, ?, ?)";
	public static String getBooks = "Select ISBN FROM USER WHERE  id = ? AND type = ?";
	public static String removeBook = "DELETE FROM USER  WHERE id = ? and ISBN = ? and type = ?";
}
