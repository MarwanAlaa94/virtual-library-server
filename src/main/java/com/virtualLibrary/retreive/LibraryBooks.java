package com.virtualLibrary.retreive;

import Authentication.ClientCredentials;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.virtualLibrary.utils.Utils;

public class LibraryBooks {
	
	private static Books instance;
	
	private LibraryBooks(){}
	
	public static Books getInstance() {
		if(instance == null){
			ClientCredentials clientCredentials = new ClientCredentials();
			JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
			clientCredentials.errorIfNotSpecified();
			try {
				instance = new Books.Builder(
						GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
						.setApplicationName(Utils.APPLICATION_NAME)
						.setGoogleClientRequestInitializer(
							new BooksRequestInitializer(
								ClientCredentials.API_KEY
							)
						).build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
}
