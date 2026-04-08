/*
 * Name: Harrison Tinley
 * This class manages the media and helps with sorting them and everything.
 */

package media_tracker_cpsc_39_final;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MediaManager {
	private ArrayList<Media> mediaList; // useful for sorting most media
	private HashMap<String, Media> mediaMap; // useful for searching most media
	
	// constructor to initialize the data structures.
	public MediaManager() {
		mediaList = new ArrayList<>();
		mediaMap = new HashMap<>();
	}
	
	// method for setting data structures. Used to load the csv data onto these structures.
	public void setMediaList(ArrayList<Media> list) {
		for (Media m : list) {
			mediaList.add(m);
			mediaMap.put(m.getTitle(), m);
		}
	}
	
	// method for adding media, adds it to both the ArrayList and the HashMap.
	public void addMedia(Scanner scnr) {
		System.out.print("Title: ");
		String title = scnr.nextLine();
		
		System.out.print("Type: ");
		String type = scnr.nextLine();
		
		System.out.print("Notes: ");
		String notes = scnr.nextLine();
		
		System.out.print("Rating: ");
		int rating = scnr.nextInt();
		
		System.out.print("Has watched or played (true/false): ");
		boolean hasWatched = scnr.nextBoolean();
		scnr.nextLine();
		
		Media m = new Media(title, type.toUpperCase(), notes, rating, hasWatched);
		mediaList.add(m);
		mediaMap.put(m.getTitle(), m);
		
		CSVLoader.saveMedia(mediaList);
	}
	
	// method for removing media, removes it from both the ArrayList and the HashMap.
	public void removeMedia(Scanner scnr) {
		System.out.print("Enter title to remove: ");
		String title = scnr.nextLine();
		Media m = mediaMap.get(title);
		if (m != null) { // if hashmap is storing the media, then remove it.
			mediaList.remove(m);
			mediaMap.remove(title);
		}
		
		CSVLoader.saveMedia(mediaList);
	}
	
	//method that gets the media by title using the HashMap.
	public Media getMedia(String title) {
		return mediaMap.get(title);
	}
	
	
	public void editMedia(Scanner scnr) {
		System.out.println("Enter title to edit: ");
		String title = scnr.nextLine();
		
		Media m = getMedia(title);
		
		if (m == null) {
			System.out.println("Media not found.");
			return;
		}
		
		System.out.print("New type: ");
		m.setType(scnr.nextLine());
		
		System.out.print("New notes: ");
		m.setNotes(scnr.nextLine());
		
		System.out.print("New rating: ");
		m.setRating(scnr.nextInt());
		
		System.out.print("Have Watched or Played (true/false): ");
		m.setHasWatched(scnr.nextBoolean());
		scnr.nextLine();
		
		CSVLoader.saveMedia(mediaList);
	}
	
	
	public void viewMedia(Scanner scnr) {
		System.out.println("Enter title: ");
		String title = scnr.nextLine();
		
		Media m = mediaMap.get(title);
		
		if (m != null) {
			System.out.println(m.toString());
		}
		else {
			System.out.println("Media not found.");
		}
	}
	
	
	//prints all the media, mainly will be used for testing to make sure everything is working right.
	public void printAllMedia() {
		for (Media m : mediaList) {
			System.out.println(m.toString());
		}
	}
	
}
