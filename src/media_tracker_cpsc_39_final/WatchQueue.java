/*(
 * Author: Harrison Tinley
 * Class WatchQueue.java
 * 
 * Description: this class handles the watchlist part of the program using a Queue data structure.
 * It gives the option to add media items to the top or bottom of the watchlist based on input.
 */

package media_tracker_cpsc_39_final;

import java.util.LinkedList;

public class WatchQueue {

	private static LinkedList<Media> watchList = new LinkedList<>();
	
	// adds media to watchList and places it according to where the user wants it (top or bottom)
	public void addToWatchList(Media m, char priority) {
		if (priority == 't') {
			watchList.addFirst(m);
		}
		else {
			watchList.addLast(m);
		}
	}
	
	/*
	 * Algorithm: Linear Search
	 * Time Complexity: O(n)
	 * 
	 * This algorithm searches for a media item in watchList with the title given
	 * by going through the list one by one until the correct title is found. Once
	 * found the list removes that media item.
	 */
	public void removeFromWatchlist(String title) {
		for (int i = 0; i < watchList.size(); i++) {
			if (watchList.get(i).getTitle().equalsIgnoreCase(title)) {
				watchList.get(i).setHasWatched(true);
				watchList.remove(i);
				System.out.printf("Removed %s from watchlist.\n", title);
				return;
			}
		}
		
		System.out.println("Media not found in watchlist.");
	}
	
	// goes through the watch list and prints each media one by one. Works because everything is sorted in the right way already.
	public void printWatchList() {
		for (Media m : watchList) {
			System.out.println(m.toString());
		}
	}
	
	// used when saving to find index of each item in the watch list
	public int getIndex(Media m) {
		return watchList.indexOf(m);
	}
}
