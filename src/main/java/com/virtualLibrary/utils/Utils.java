package com.virtualLibrary.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import Authentication.ClientCredentials;
import Authentication.User;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.virtualLibrary.retreive.BookHandler;

public class Utils {
	
	public static final String APPLICATION_NAME = "VirtualLibrary";
	public static String standardImageLink = "/resources/img/book1.png";
	public static String standardUserLink = "/resources/img/user.png";
    public static List<String> getSupportedCategories() {
    	Properties prop = new Properties();
    	InputStream input = null;
    	
    	List<String> categories = new ArrayList<String>();
    	try {

    		input = Utils.class.getResourceAsStream("homeCategories.properties");
    		prop.load(input);
    		
    		int probNo = Integer.parseInt(prop.getProperty("categoriesNo"));
    		
    		for(int i = 1; i <= probNo; i++){
    			categories.add(prop.getProperty("category" + i));
    		}

    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} finally {
    		if (input != null) {
    			try {
    				input.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
		return categories;
    	
    }
    
    public static User getUserInfo(String token, BookHandler bookHandler, Books books) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
			  .Builder(new ApacheHttpTransport(), JacksonFactory.getDefaultInstance())
			  .setAudience(Collections.singletonList(ClientCredentials.CLIENT_ID))
			  .build();
		GoogleIdToken idToken;
		try {
			System.out.println(token);
			idToken = verifier.verify(token);
			System.out.println(idToken);
		  	if (idToken != null) {
		  	  return new User(idToken.getPayload(), books, bookHandler);
		  	} else {
		  		System.out.println("Invalid ID token.");
		  	}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		System.out.println("problem in getting user :)");
		return new User(books, bookHandler);
	}
    
    public static List<String> extractFromJSON (String JSON, List<String> keys) {
    	JSONObject jObject;
    	List<String> params = new ArrayList<String>(); 
		try {
			jObject = new JSONObject(JSON);
			for(String key : keys) {
				params.add(jObject.getString(key));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return params;
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
}
