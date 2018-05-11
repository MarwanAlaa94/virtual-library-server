package com.virtualLibrary.retreive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Authentication.ClientCredentials;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.virtualLibrary.utils.Utils;

public class DatabaseConnection {
	
	private static Connection instance;
	
	private DatabaseConnection(){}
	
	public static Connection getInstance() {
		if(instance == null){
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				instance = DriverManager.getConnection("jdbc:mysql://localhost:3306/book-library", "root", "");
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		return instance;
	}
}