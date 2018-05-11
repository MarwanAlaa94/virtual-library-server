package Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.api.services.books.Books;
import com.virtualLibrary.model.Book;
import com.virtualLibrary.model.UserDBM;
import com.virtualLibrary.retreive.BookHandler;

public class User {
	private String name;
	private String familyName;
	private String givenName;
	private String email;
	private String token;
	private String locale;
	private String pictureUrl;
	private String userId;
	private boolean emailVerified;
	private UserDBM userDBM;
	private Books books;
	private BookHandler bookHandler;
	public User(Books books, BookHandler bookHandler) {
		userId = "1234596";
		email = "marwannazeih@gmail.com";
		emailVerified = true;
		name = "Marwan";
		pictureUrl = "assets/default.jpg";
		locale = "smouha";
		familyName = "Nazeih";
		givenName = "Marwan";
		userDBM = new UserDBM(this);
		this.books = books;
		this.bookHandler = bookHandler;
	}
	
	public User(Map<String, String> payload, Books books, BookHandler bookHandler) {
		userId = payload.get("sub");
		System.out.println("User ID: " + userId);
		email = payload.get("email");
		emailVerified = Boolean.parseBoolean(payload.get("email_verified"));
		name = payload.get("name");
		pictureUrl = payload.get("picture");
		locale = payload.get("locale");
		familyName = payload.get("family_name");
		givenName = payload.get("given_name");
		userDBM = new UserDBM(this);
		this.books = books;
		this.bookHandler = bookHandler;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
	
	public void removeFromFavorites(String isbn) {
		userDBM.removeFromFavorites(isbn);;
	}
	
	public void addToFavorites(String isbn) {
		userDBM.addToFavorites(isbn);
	}
	
	public void addToRated(String isbn) {
		userDBM.addToRated(isbn);
	}
	
	public void removeFromRead(String isbn) {
		userDBM.removeFromRead(isbn);;
	}
	
	public void addRead(String isbn) {
		userDBM.addRead(isbn);
	}
	
	public void removeFromToBeRead(String isbn) {
		userDBM.removeFromToBeRead(isbn);
	}
	
	public void addToBeRead(String isbn) {
		userDBM.addToBeRead(isbn);
	}
	
	public boolean checkFav(String isbn) {
		return userDBM.checkFav(isbn);
	}
	
	public boolean checkRated(String isbn) {
		return userDBM.checkRated(isbn);
	}
	
	public boolean checkRead(String isbn) {
		return userDBM.checkRead(isbn);
	}
	
	public boolean checkToRead(String isbn) {
		return userDBM.checkToRead(isbn);
	}
	
	public List<Book> getFavorites() {
		return createBooksList(userDBM.getFavorites());
	}
	
	public List<Book> getRead() {
		return createBooksList(userDBM.getRead());
	}
	public List<Book> getToRead() {
		return createBooksList(userDBM.getToRead());
	}
	
    private List<Book> createBooksList(List<String> isbns) {
    	List<Book> ret = new ArrayList<Book>();
    	isbns.forEach(isbn -> {
    		Book book = bookHandler.getBook(books, isbn);
    		if(book != null && !book.getTitle().equals("hamada"))
    		    ret.add(book);
    	});
    	return ret;
    }
}
