/*(
 * Author: Harrison Tinley
 * Class WatchQueue.java
 * 
 * Description: this class handles the watchlist part of the program using a Queue data structure.
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
	
	// gets title and loops through watchList until finding the media, then when found it deletes
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
	
	public void printWatchList() {
		for (Media m : watchList) {
			System.out.println(m.toString());
		}
	}
}
