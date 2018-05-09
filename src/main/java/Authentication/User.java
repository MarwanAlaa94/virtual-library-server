package Authentication;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.services.books.Books;
import com.virtualLibrary.model.Book;
import com.virtualLibrary.model.UserDBM;
import com.virtualLibrary.retreive.BookHandler;
import com.virtualLibrary.utils.Utils;

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
	
	public User(Payload payload, Books books, BookHandler bookHandler) {
		userId = payload.getSubject();
		//System.out.println("User ID: " + userId);
		email = payload.getEmail();
		emailVerified = Boolean.valueOf(payload.getEmailVerified());
		name = (String) payload.get("name");
		pictureUrl = (String) payload.get("picture");
		locale = (String) payload.get("locale");
		familyName = (String) payload.get("family_name");
		givenName = (String) payload.get("given_name");
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
	
	public void addToFavorites(String isbn) {
		userDBM.addToFavorites(isbn);
	}
	
	public void addRead(String isbn) {
		userDBM.addRead(isbn);
	}
	
	public void addToBeRead(String isbn) {
		userDBM.addToBeRead(isbn);
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
    		Book book = bookHandler.getBook(books, isbn, "hamada");
    		if(book != null && !book.getTitle().equals("hamada"))
    		    ret.add(book);
    	});
    	return ret;
    }
}
