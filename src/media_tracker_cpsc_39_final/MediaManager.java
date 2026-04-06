/*
 * Name: Harrison Tinley
 * This class manages the media and helps with sorting them and everything.
 */

package media_tracker_cpsc_39_final;

import java.util.ArrayList;
import java.util.HashMap;

public class MediaManager {
	private ArrayList<Media> mediaList; // useful for sorting most media
	private HashMap<String, Media> mediaMap; // useful for searching most media
	
	// constructor to initialize the data structures.
	public MediaManager() {
		mediaList = new ArrayList<>();
		mediaMap = new HashMap<>();
	}
	
	// method for adding media, adds it to both the ArrayList and the HashMap.
	public void addMedia(Media m) {
		mediaList.add(m);
		mediaMap.put(m.getTitle(), m);
	}
	
	// method for removing media, removes it from both the ArrayList and the HashMap.
	public void removeMedia(String title) {
		Media m = mediaMap.get(title);
		if (m != null) { // if hashmap is storing the media, then remove it.
			mediaList.remove(m);
			mediaMap.remove(title);
		}
	}
	
	//method that gets the media by title using the HashMap.
	public Media getMedia(String title) {
		return mediaMap.get(title);
	}
	
	//prints all the media, mainly will be used for testing to make sure everything is working right.
	public void printAllMedia() {
		for (Media m : mediaList) {
			System.out.println(m.toString());
		}
	}
	
}
