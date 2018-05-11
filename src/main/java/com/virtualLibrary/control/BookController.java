package com.virtualLibrary.control;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.books.Books;
import com.virtualLibrary.model.Book;
import com.virtualLibrary.retreive.BookHandler;
import com.virtualLibrary.retreive.LibraryBooks;
import com.virtualLibrary.utils.Utils;

import Authentication.ClientCredentials;


@RestController
public class BookController {
	
	private static final NumberFormat CURRENCY_FORMATTER = NumberFormat
			.getCurrencyInstance();
	private static final NumberFormat PERCENT_FORMATTER = NumberFormat
			.getPercentInstance();
	
	private BookHandler bookHandler;
	private ClientCredentials clientCredentials;
	private Books books;
	public BookController() {
		bookHandler = BookHandler.getInstance();
		books = LibraryBooks.getInstance();
	}
	
	
	@RequestMapping(value = "/library/home", method = RequestMethod.GET)
	public HashMap<String, ArrayList<Book>> browseHome() {
		List<String> categories = Utils.getSupportedCategories();
		System.out.println(categories);
		System.out.println(bookHandler);
		HashMap<String, ArrayList<Book>> map = new HashMap<String, ArrayList<Book>> ();
		for(String category : categories){
			map.put(category.replaceAll("\"", ""), bookHandler.browseBooks(books, category.replaceAll("\"", "")));
		}
		return map;
	}
    
	@RequestMapping(value = "/library/categories", method = RequestMethod.GET)
	public List<String> getcategories() {
		return Utils.getSupportedCategories();
	}
	
    @RequestMapping(value = "/library/search", method = RequestMethod.POST)
	public ArrayList<Book> search(ModelMap model, @RequestBody String jsonStr){
    	ArrayList<Book> res = null;
		try {
			JSONObject jObject = new JSONObject(jsonStr);
			res = bookHandler.search(books, jObject.getString("key"), jObject.getString("value"), 40);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res;
    }
    
    @RequestMapping(value = "/library/getRate", method = RequestMethod.POST)
   	public String getRate(ModelMap model, @RequestBody String jsonStr){
    	JSONObject jObject;
		try {
			jObject = new JSONObject(jsonStr);
		    Book book = bookHandler.getBook(books, jObject.getString("ISBN"));
	        return book.getAverageRating().toString();
	        
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return "not found";	
   	}
    
    @RequestMapping(value = "/library/getReviews", method = RequestMethod.POST)
    public List<List<String>> addReview(ModelMap model,@RequestBody String jsonStr){
    	 ArrayList<String> keys = new ArrayList<String> ();
    	 keys.add("ISBN");
    	 keys.add("title");
    	 List<String> params = Utils.extractFromJSON(jsonStr, keys);
         Book book = bookHandler.getBook(books, params.get(0));
         return book.getReviews();
    }
    
  
   
}