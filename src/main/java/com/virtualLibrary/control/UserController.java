package com.virtualLibrary.control;

import java.util.ArrayList;
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

import Authentication.User;


@RestController
public class UserController {
	
	private User user;
	private Books books;
	private BookHandler bookHandler;
	
	public UserController() {
		bookHandler = BookHandler.getInstance();
		books = LibraryBooks.getInstance();
	}
    
    @RequestMapping(value = "/library/userInfo", method = RequestMethod.GET)
   	public User getUserInfo(ModelMap model){
   		return user;
   	}
    
    @RequestMapping(value = "/library/user", method = RequestMethod.POST)
   	public User getUser(ModelMap model, @RequestBody String token){
    	ArrayList<String> keys = new ArrayList<String> ();
    	keys.add("token");
    	user = Utils.getUserInfo(Utils.extractFromJSON(token, keys).get(0),
    			                 bookHandler,
    			                 books);
   		return user;
   	}
	
    @RequestMapping(value = "/library/addFav", method = RequestMethod.POST)
   	public void addFav(ModelMap model, @RequestBody String ISBN){
    	ArrayList<String> keys = new ArrayList<String> ();
    	keys.add("ISBN");
    	user.addToFavorites(Utils.extractFromJSON(ISBN,keys).get(0));
   	}
    
    @RequestMapping(value = "/library/remFav", method = RequestMethod.POST)
   	public void remFav(ModelMap model, @RequestBody String ISBN){
    	ArrayList<String> keys = new ArrayList<String> ();
    	keys.add("ISBN");
    	user.removeFromFavorites(Utils.extractFromJSON(ISBN,keys).get(0));
   	}
    
    @RequestMapping(value = "/library/checkFav", method = RequestMethod.POST)
   	public boolean checkFav(ModelMap model, @RequestBody String ISBN){
    	ArrayList<String> keys = new ArrayList<String> ();
    	keys.add("ISBN");
    	return user.checkFav(Utils.extractFromJSON(ISBN,keys).get(0));
   	}
      
    @RequestMapping(value = "/library/addToRead", method = RequestMethod.POST)
   	public void addToRead(ModelMap model, @RequestBody String ISBN){
    	ArrayList<String> keys = new ArrayList<String> ();
    	keys.add("ISBN");
    	user.addToBeRead(Utils.extractFromJSON(ISBN, keys).get(0));
   	}
    
    @RequestMapping(value = "/library/remToRead", method = RequestMethod.POST)
   	public void remToRead(ModelMap model, @RequestBody String ISBN){
    	ArrayList<String> keys = new ArrayList<String> ();
    	keys.add("ISBN");
    	user.removeFromToBeRead(Utils.extractFromJSON(ISBN,keys).get(0));
   	}
    
    @RequestMapping(value = "/library/checkToRead", method = RequestMethod.POST)
   	public boolean checkToRead(ModelMap model, @RequestBody String ISBN){
    	ArrayList<String> keys = new ArrayList<String> ();
    	keys.add("ISBN");
    	return user.checkToRead(Utils.extractFromJSON(ISBN,keys).get(0));
   	}
    
    @RequestMapping(value = "/library/done", method = RequestMethod.POST)
   	public void addToDone(ModelMap model, @RequestBody String ISBN){
    	ArrayList<String> keys = new ArrayList<String> ();
    	keys.add("ISBN");
    	user.addRead(Utils.extractFromJSON(ISBN, keys).get(0));
   	}
    
    @RequestMapping(value = "/library/remDone", method = RequestMethod.POST)
   	public void remDone(ModelMap model, @RequestBody String ISBN){
    	ArrayList<String> keys = new ArrayList<String> ();
    	keys.add("ISBN");
    	user.removeFromRead(Utils.extractFromJSON(ISBN,keys).get(0));
   	}
    
    @RequestMapping(value = "/library/checkDone", method = RequestMethod.POST)
   	public boolean checkRead(ModelMap model, @RequestBody String ISBN){
    	ArrayList<String> keys = new ArrayList<String> ();
    	keys.add("ISBN");
    	return user.checkRead(Utils.extractFromJSON(ISBN,keys).get(0));
   	}
    
    @RequestMapping(value = "/library/rate", method = RequestMethod.POST)
   	public String rateBook(ModelMap model, @RequestBody String jsonStr){
    	JSONObject jObject;
		try {
			jObject = new JSONObject(jsonStr);
		    Book book = bookHandler.getBook(books, jObject.getString("ISBN"));
	        return book.updateRate(jObject.getDouble("rate"));
	        
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return "not found";	
   	}
    
    @RequestMapping(value = "/library/addReview", method = RequestMethod.POST)
    public List<List<String>> addReview(ModelMap model,@RequestBody String jsonStr){
    	 ArrayList<String> keys = new ArrayList<String> ();
    	 keys.add("ISBN");
    	 keys.add("title");
    	 keys.add("review");
    	 List<String> params = Utils.extractFromJSON(jsonStr, keys);
         Book book = bookHandler.getBook(books, params.get(0));
         book.addReview(params.get(2), user.getName() + " " + user.getFamilyName());
         return book.getReviews();
    }
 
}
