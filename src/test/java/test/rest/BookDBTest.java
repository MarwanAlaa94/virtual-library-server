package test.rest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.virtualLibrary.model.Book;
import com.virtualLibrary.model.BookDBManager;

public class BookDBTest {
	private static Book book;
	private static BookDBManager manager;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		book = new Book();
		book.setISBN("isbn");
		book.setTitle("title");
		book.setReviews(new ArrayList<List<String>>());
		manager = new BookDBManager(book);
		manager.build();
		book.setBookDBManager(manager);
	}
	
	private boolean check(List<List<String>> revs, String rev, String revr) {
		for(List<String> r: revs) {
			if (r.get(0).equals(rev) && r.get(1).equals(revr)) return true;
		}
		return false;
	}
	
	
	@Test
	public void testAddReview() {
		book.getReviews();
		book.addReview("review", "reviewer");
		assertTrue(check(book.getReviews(), "review", "reviewer"));
	}

	@Test
	public void testInsertBook() {
		String newIsbn = "nonExisting";
		book.setISBN(newIsbn);
		manager.insertBook();
		assertTrue(manager.checkExists(newIsbn));
	}
	
	@Test
	public void testCheckExists() {
		String newIsbn = "nonExisting" + System.currentTimeMillis();
		book.setISBN(newIsbn);
		assertFalse(manager.checkExists(newIsbn));
	}
	
	@Test
	public void testRate() {
		String newIsbn = "nonExistingbook" + System.currentTimeMillis();
		book.setISBN(newIsbn);
		manager.updateRate(5);
		System.out.println(book.getAverageRating());
		assertTrue(Math.abs(book.getAverageRating()-5) < 1e-4);
		assertEquals(book.getRateDBNo(), 1);
		manager.updateRate(1);
		assertTrue(Math.abs(book.getAverageRating()-1) < 1e-4);
	}
}
