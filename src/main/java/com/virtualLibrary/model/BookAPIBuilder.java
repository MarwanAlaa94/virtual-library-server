package com.virtualLibrary.model;

import java.util.List;

import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo;
import com.virtualLibrary.utils.Utils;

public class BookAPIBuilder {
	public static void build(Book book, Volume volume) {
	    VolumeInfo volumeInfo = volume.getVolumeInfo();
		book.setTitle(volumeInfo.getTitle());
		book.setGoogleMoreInfo(volumeInfo.getInfoLink());
		book.setDescription(volumeInfo.getDescription());
		book.setISBN(volume.getId());
		List<String> authors = volumeInfo.getAuthors();
		try {
			book.setRateAPINo(volumeInfo.getRatingsCount());
		}
		catch(Exception e) {
			book.setRateAPINo(0);
		}
		
		try {
			book.setRatingAPI(volumeInfo.getAverageRating());
		}
		catch(Exception e) {
			book.setRatingAPI(0);
		}
		
		if (authors != null && !authors.isEmpty())
			book.setAuthor(authors.get(0));
		String imageLink = null;
        try {
        	imageLink = volumeInfo.getImageLinks().get("thumbnail").toString();
        	book.setImageLink(imageLink);
        } catch (Exception e) {
        }finally {
        	if(imageLink == null){
        		book.setImageLink(Utils.standardImageLink);
        	}
        }
	}
}
