package com.virtualLibrary.model;

import java.util.List;

import com.google.api.services.books.model.Volume;

public class Book {
	private String title;
	private String author;
	private String description;
	private String imageLink;
	private String ISBN;
	private double ratingAPI;
	private double ratingDB;
	private int rateAPINo;
	private int rateDBNo;
	private List<List<String>> reviews ;
	private BookDBManager bookDBManager;
	
	public Book(Volume volume) {
	    BookAPIBuilder.build(this, volume);
        this.bookDBManager = new BookDBManager(this);
        bookDBManager.build();
	}
	
	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public void setReviews(List<List<String>> reviews) {
		this.reviews = reviews;
	}
	
	public Book(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public List<List<String>> getReviews() {
		return reviews;
	}
	public String getGoogleMoreInfo() {
		return googleMoreInfo;
	}
	public void setGoogleMoreInfo(String googleMoreInfo) {
		this.googleMoreInfo = googleMoreInfo;
	}

	public double getRatingAPI() {
		return ratingAPI;
	}

	public void setRatingAPI(double ratingAPI) {
		this.ratingAPI = ratingAPI;
	}

	public double getRatingDB() {
		return ratingDB;
	}

	public void setRatingDB(double ratingDB) {
		this.ratingDB = ratingDB;
	}

	public int getRateAPINo() {
		return rateAPINo;
	}

	public void setRateAPINo(int rateAPINo) {
		this.rateAPINo = rateAPINo;
	}

	public int getRateDBNo() {
		return rateDBNo;
	}

	public void setRateDBNo(int rateDBNo) {
		this.rateDBNo = rateDBNo;
	}
	
	public void addReview(String review, String reviewer) {
		bookDBManager.addReview(review, reviewer);
	}
	public String updateRate(double rate) {
		bookDBManager.updateRate(rate);
		return getAverageRating().toString();
	}
	
	public Double getAverageRating () {
		if(rateAPINo + rateDBNo == 0) return 0.0;
		return ( rateAPINo * ratingAPI + rateDBNo * ratingDB ) / (rateAPINo + rateDBNo);
	}

	private String googleMoreInfo;
}
