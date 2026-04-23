/*
 * Name: Harrison Tinley
 * Class MediaManager.java
 * 
 * Description: This class manages all the user's media with an ArrayList<Media> and
 * a HashMap<String, Media> where the media title is the key. It's used to perform most operations
 * with changing media such as adding, removing, editing, printing, and sorting.
 */

package media_tracker_cpsc_39_final;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MediaManager {
	private ArrayList<Media> mediaList; // useful for sorting most media
	private HashMap<String, Media> mediaMap; // useful for searching most media
	
	private static WatchQueue watchQueue = new WatchQueue();
	
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
		String title = scnr.nextLine().toUpperCase();
		
		// if mediaMap has a media with that title don't add it (duplicates ruin program logic)
		if (mediaMap.containsKey(title) == true) {
			System.out.println("Media with this title already exists. Try again.");
			return;
		}
		
		System.out.print("Type: ");
		String type = scnr.nextLine();
		
		System.out.print("Notes: ");
		String notes = scnr.nextLine();
		
		System.out.print("Rating: ");
		int rating = getValidInt(scnr);
		
		System.out.print("Has watched or played (true/false): ");
		boolean hasWatched = getValidBoolean(scnr);
		
		
		
		
		Media m = new Media(title, type.toUpperCase(), notes, rating, hasWatched, -1);
		
		// media isn't a duplicate so add it correctly
		mediaList.add(m);
		mediaMap.put(m.getTitle(), m);
		// if added media hasn't been watched -> add it to watchlist
		if (hasWatched == false) {
			watchQueue.addToWatchList(m, getValidChoice(scnr));
		}
			
		CSVLoader.saveMedia(mediaList);
	}
	
	// method for removing media, removes it from both the ArrayList and the HashMap.
	public void removeMedia(Scanner scnr) {
		System.out.print("Enter title to remove: ");
		String title = scnr.nextLine().toUpperCase();
		Media m = mediaMap.get(title);
		if (m != null) { // if hashmap is storing the media, then remove it.
			mediaList.remove(m);
			mediaMap.remove(title);
			watchQueue.removeFromWatchlist(m.getTitle());
		}
		
		CSVLoader.saveMedia(mediaList);
	}
	
	/*
	 * Algorithm: HashMap Search
	 * Time Complexity: O(1)
	 * 
	 * This algorithm finds a specific key in the HashMap by calculating the key's hash code
	 * and using buckets where this key's corresponding values are located. It returns media
	 * very efficiently and is great for my search method.
	 */
	public Media getMedia(String title) {
		return mediaMap.get(title);
	}
	
	
	public void editMedia(Scanner scnr) {
		System.out.println("Enter title to edit: ");
		String title = scnr.nextLine().toUpperCase();
		
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
		m.setRating(getValidInt(scnr));
		
		System.out.print("Have Watched or Played (true/false): ");
		boolean hasWatched = getValidBoolean(scnr);
		boolean previousWatchState = m.getHasWatched();
		m.setHasWatched(hasWatched);
		
		// if edited media has now been watched, remove it from watch list
		if (hasWatched == true && previousWatchState == false) {
			watchQueue.removeFromWatchlist(m.getTitle());
		}
		// adds to watch list if user edits hasWatched to false and gives them choice where to place media in watch list
		else if (hasWatched == false) {
			watchQueue.addToWatchList(m, getValidChoice(scnr));
		}
		
		CSVLoader.saveMedia(mediaList);
	}
	
	//method for getting valid integer so user can keep typing until correct value is entered
	private int getValidInt(Scanner scnr) {
		while (true) {
			//if scnr has an int it can read then read it and stop the loop
			if (scnr.hasNextInt()) {
				int value = scnr.nextInt();
				scnr.nextLine(); // clears newline
				return value;
			}
			else {
				System.out.print("Invalid number. Try again: ");
				scnr.nextLine(); //gets rid of bad input.
			}
		}
	}
	
	
	// instead of using nextBoolean() this method allows multiple ways for user to express true or false
	private boolean getValidBoolean(Scanner scnr) {
		// loops through getting input until user types a correct input.
		while (true) {
			String input = scnr.nextLine().toLowerCase();
			
			// returns true if user enters true, yes, or y
			if (input.equals("true") || input.equals("yes") || input.equals("y")) {
				return true;
			}
			// returns false if user types false, no, or n
			else if (input.equals("false") || input.equals("no") || input.equals("n")) {
				return false;
			}
			else {
				System.out.print("Enter (yes/no) (y/n) (true/false): ");
			}
		}
	}
	
	private char getValidChoice(Scanner scnr) {
		char choice = 'b';
		while (true) {
			System.out.print("Add to top (type 't') or bottom (type 'b') of watchlist: ");
			choice = scnr.nextLine().charAt(0);
			if (choice == 't' || choice == 'b') {
				return choice;
			}
			else {
				System.out.println("Invalid choice. Type (t/b)");
			}
		}
	}
	
	public void viewMedia(Scanner scnr) {
		System.out.println("Enter title: ");
		String title = scnr.nextLine().toUpperCase();
		
		Media m = mediaMap.get(title);
		
		if (m != null) {
			System.out.println(m.toString());
			
			System.out.println("View notes? (y/n): ");
			char choice = scnr.nextLine().charAt(0); //just gets first character to make it a char
			if (choice == 'y' || choice == 'Y') {
				System.out.println("Notes: " + m.getNotes());
			}
			
		}
		else {
			System.out.println("Media not found.");
		}
	}
	
	
	//prints all the media, mainly will be used for testing to make sure everything is working right.
	public void printAllMedia(Scanner scnr) {
		//prints and gets choice from user to determine how media will all be printed
		System.out.printf("Sort by: \n1. Title (A-Z) \n2. Title (Z-A) \n3. Rating (High - Low) \n4. Rating (Low - High) \n5. Type \n");
		int choice = scnr.nextInt();
		scnr.nextLine();
		
		switch (choice) {
			
			case 1:
				sortByTitleAToZ();
				break;
			
			case 2:
				sortByTitleZToA();
				break;
				
			case 3:
				sortByRatingHighToLow();
				break;
				
			case 4:
				sortByRatingLowToHigh();
				break;
				
			case 5:
				sortByType();
				break;
				
			default:
				System.out.println("Invalid option.");
		}
		
		
		// since media list is sorted accordingly, now each media will be printed
		
		for (Media m : mediaList) {
			System.out.println(m.toString());
		}
	}
	
	// Algorithms for sorting media in the printAllMedia() function
	// All use some variation of selection sort
	
	/*
	 * Algorithm: Selection Sort (Title A-Z)
	 * Time Complexity: O(n^2)
	 * 
	 * This algorithm sorts the media list in alphabetical order by title
	 * by repeatedly finding the smallest element (letter closest to 'a' in the alphabet)
	 * and swapping it.
	 */
	public void sortByTitleAToZ() {
		for (int i = 0; i < mediaList.size() - 1; i++) {
			int min_index = i;
			// goes through whole list comparing strings swapping them to become arranged alphabetically
			for (int j = i + 1; j < mediaList.size(); j++) {
				// if current title (j) is closer to 'a' than title at max_index, max_index will be set to j
				if (mediaList.get(j).getTitle().compareToIgnoreCase(mediaList.get(min_index).getTitle()) < 0) {
					min_index = j;
				}
			}
			
			Media temp = mediaList.get(min_index);
			mediaList.set(min_index, mediaList.get(i));
			mediaList.set(i, temp);
		}
	}
	
	/*
	 * Algorithm: Selection Sort (Title Z-A)
	 * Time Complexity: O(n^2)
	 * 
	 * This algorithm sorts the media list in reverse alphabetically order by title
	 * by repeatedly finding the biggest element (letter closest to 'z' in the alphabet)
	 * and swapping it.
	 */
	public void sortByTitleZToA() {
		for (int i = 0; i < mediaList.size() - 1; i++) {
			int max_index = i;
			// goes through whole list comparing strings swapping them to become arranged alphabetically
			for (int j = i + 1; j < mediaList.size(); j++) {
				// if current title (j) is closer to 'z' than title at max_index, max_index will be set to j
				if (mediaList.get(j).getTitle().compareToIgnoreCase(mediaList.get(max_index).getTitle()) > 0) {
					max_index = j;
				}
			}
			
			Media temp = mediaList.get(max_index);
			mediaList.set(max_index, mediaList.get(i));
			mediaList.set(i, temp);
		}
	}
	
	/*
	 * Algorithm: Selection Sort (Rating High-Low)
	 * Time Complexity: O(n^2)
	 * 
	 * This algorithm sorts the media list by rating from highest to lowest
	 * by repeatedly finding the element with the highest rating
	 * and swapping it.
	 */
	public void sortByRatingHighToLow() {
		for (int i = 0; i < mediaList.size() - 1; i++) {
			int max_index = i;
			// goes through whole list to find if any rating is higher than the current one
			for (int j = i + 1; j < mediaList.size(); j++) {
				if (mediaList.get(j).getRating() > mediaList.get(max_index).getRating()) {
					max_index = j;
				}
			}
			
			// swaps biggest value found with current value found, if no bigger value found it doesn't do much.
			Media temp = mediaList.get(max_index);
			mediaList.set(max_index, mediaList.get(i));
			mediaList.set(i, temp);
		}
	}
	
	/*
	 * Algorithm: Selection Sort (Rating Low-High)
	 * Time Complexity: O(n^2)
	 * 
	 * This algorithm sorts the media list by rating from lowest to highest
	 * by repeatedly finding the element with the lowest rating
	 * and swapping it.
	 */
	public void sortByRatingLowToHigh() {
		for (int i = 0; i < mediaList.size() - 1; i++) {
			int min_index = i;
			// goes through whole list to find if any rating is lower than the current one
			for (int j = i + 1; j < mediaList.size(); j++) {
				if (mediaList.get(j).getRating() < mediaList.get(min_index).getRating()) {
					min_index = j;
				}
			}
			
			// swaps smallest value found with current value found, if no smaller value found it doesn't do much.
			Media temp = mediaList.get(min_index);
			mediaList.set(min_index, mediaList.get(i));
			mediaList.set(i, temp);
		}
	}
	
	/*
	 * Algorithm: Selection Sort (Type A-Z)
	 * Time Complexity: O(n^2)
	 * 
	 * This algorithm sorts the media list in alphabetical order by media type
	 * by repeatedly finding the smallest element (letter closest to 'a' in the alphabet)
	 * and swapping it.
	 */
	public void sortByType() {
		for (int i = 0; i < mediaList.size() - 1; i++) {
			int min_index = i;
			
			for (int j = i + 1; j < mediaList.size(); j++) {
				if (mediaList.get(j).getType().compareToIgnoreCase(mediaList.get(min_index).getType()) < 0) {
					min_index = j;
				}
			}
			
			Media temp = mediaList.get(min_index);
			mediaList.set(min_index, mediaList.get(i));
			mediaList.set(i, temp);
		}
	}
}
