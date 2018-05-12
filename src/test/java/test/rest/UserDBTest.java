package test.rest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.virtualLibrary.model.UserDBM;

import Authentication.User;

public class UserDBTest {
	private static User user;
	private static UserDBM manager;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		user = new User(null, null);
		manager = new UserDBM(user);
	}
	
	private boolean check(List<String> last, String isbn, List<String> favs) {
		last.forEach(book -> favs.remove(last));
		favs.remove(isbn);
		return favs.isEmpty();
	}

	@Test
	public void addToFavoritesShouldThrowException() {
		try {
			manager.addToFavorites(null);
			fail("should throw IAE");
		} catch(IllegalArgumentException e) {}
	}

	@Test
	public void addToFavoritesEmptyString() {
		try {
			manager.addToFavorites("");
			fail("should throw IAE");
		} catch(IllegalArgumentException e) {}
	}
	
	@Test
	public void addToFavorites() {
		String isbn = "1";
		manager.removeFromFavorites(isbn);
		List<String> favs = manager.getFavorites();
		manager.addToFavorites(isbn);
		assertTrue(check(favs, isbn, manager.getFavorites()));
		manager.removeFromFavorites("1");
	}
	
	@Test
	public void remFromFavorites() {
		try {
			manager.removeFromFavorites(null);
			fail("should throw IAE");
		} catch(IllegalArgumentException e) {}
	}
	
	@Test
	public void addToBeRead() {
		String isbn = "1";
		List<String> favs = manager.getToRead();
		manager.addToBeRead(isbn);
		assertTrue(check(favs, isbn, manager.getToRead()));
		manager.removeFromToBeRead("1");
	}
	
	@Test
	public void remFromToBeRead() {
		try {
			manager.removeFromToBeRead(null);
			fail("should throw IAE");
		} catch(IllegalArgumentException e) {}
	}
	
	@Test
	public void addRead() {
		String isbn = "1";
		List<String> favs = manager.getRead();
		manager.addRead(isbn);
		assertTrue(check(favs, isbn, manager.getRead()));
		manager.removeFromRead("1");
	}
	
	@Test
	public void remFromRead() {
		try {
			manager.removeFromRead(null);
			fail("should throw IAE");
		} catch(IllegalArgumentException e) {}
	}
	
	@Test
	public void doubleRemoveShouldDoNothing() {
		manager.removeFromFavorites("NonExisting");
		manager.removeFromFavorites("NonExisting");
	}
	
	@Test
	public void testExistingFav() {
		manager.removeFromFavorites("hamada");
		manager.addToFavorites("hamada");
		assertTrue(manager.checkFav("hamada"));
		manager.removeFromFavorites("hamada");
	}
}
