package com.virtualLibrary.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.books.Books;
import com.virtualLibrary.retreive.BookHandler;

import Authentication.User;

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
    	Map<String, String> map = getMapFromGoogleTokenString(token);
    	if (map != null) return new User(map, books, bookHandler);
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
			e.printStackTrace();
		}
		return params;
    	
    }
    
    private static Map<String,String> getMapFromGoogleTokenString(final String idTokenString){
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    ((HttpURLConnection) (new URL("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token="
                    	+ idTokenString.trim()))
                    .openConnection()).getInputStream(), Charset.forName("UTF-8")));

            StringBuffer b = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                b.append(inputLine + "\n");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return new ObjectMapper().readValue(b.toString(), objectMapper.getTypeFactory()
        					   .constructMapType(Map.class, String.class, String.class));

        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }   
}
