/*
 * Name: Harrison Tinley
 * Media class that defines what a media item is, has constructors and getters to help other classes.
 */

package media_tracker_cpsc_39_final;

public class Media {

	private String title;
	private String type;
	private String notes;
	private int rating;
	private boolean hasWatched;
	
	
	//constructor method that sets all the variables.
	public Media(String title, String type, String notes, int rating, boolean hasWatched) {
		this.title = title;
		this.type = type.toUpperCase();
		this.notes = notes;
		this.rating = rating;
		this.hasWatched = hasWatched;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type.toUpperCase();
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public boolean getHasWatched() {
		return hasWatched;
	}
	
	public void setHasWatched(boolean hasWatched) {
		this.hasWatched = hasWatched;
	}

	@Override
	public String toString() {
		return title + " | " + type + " | Rating: " + rating + " | Watched: " + hasWatched;
	}
	
}
