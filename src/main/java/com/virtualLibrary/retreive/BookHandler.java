package com.virtualLibrary.retreive;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import com.virtualLibrary.model.Book;

@Service
public class BookHandler {
	
	
	private static BookHandler instance;
    
	private BookHandler() {
		
	}
	
	public static BookHandler getInstance() {
		if(instance == null){
			instance = new BookHandler();
		}
		return instance;
	}
	
	public ArrayList<Book> browseBooks(Books books, String category ) {
		return search(books, "category", category, 7);
	}
	
	public ArrayList<Book> search(Books books,
			String searchKey, String searchVal, long limit ) {
		
		ArrayList<Book> result = new ArrayList<Book>();
		String query = searchKey + ":" + searchVal;
		System.out.println(query);
		List volumesList = null;
		try {
			volumesList = books.volumes().list(query).setPrintType("books").
					setOrderBy("relevance").setMaxResults(limit);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Volumes volumes = null;
		try {
			volumes = volumesList.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
			System.out.println("No matches found.");
			return result;
		}
		volumes.getItems().forEach(item -> result.add(new Book(item)));
		System.out.println(searchVal);
		return result;
	}
	
	public Book getBook(Books books, String ISBN) {
		Volume volume = null;
		try {
			volume = books.volumes().get(ISBN).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Book(volume);
	}
}
