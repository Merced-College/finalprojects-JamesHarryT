/*
 * Name: Harrison Tinley
 * Class CSVLoader.java
 * 
 * Description: This class loads the media.csv file and saves it so the user can open up
 * the program just as they left it. The methods separate values using the "|"
 * character to allow users to type commas and other characters in their notes and titles.
 */

package media_tracker_cpsc_39_final;

import java.io.*;
import java.util.ArrayList;

public class CSVLoader {
	
	private static String filePath = "src/data/media.csv";
	
	//makes an ArrayList that loads media onto it and then this loaded media is transfered to the MediaManager mediaList.
	private static ArrayList<Media> mediaList = new ArrayList<>();
	private static WatchQueue watchQueue = new WatchQueue();
	
	// method for saving media. Used whenever media is added, removed, or edited.
	public static void saveMedia(ArrayList<Media> mediaList) {
		try {
			FileWriter writer = new FileWriter(filePath); 
			//opens new writer and overwrites previous one to refresh it completely whenever media is saved.
			writer.write("Title|Type|Notes|Rating|hasWatched/Played|WatchListIndex"); //writes header for csv for users to read if they open the file.
			
			// separate values with "|" so commas and other characters can be used in strings
			for (Media m : mediaList) {
				int watchListIndex = -1; // default is to not be in the watch list
				
				if (m.getHasWatched() == false) {
					// media is not watched so find it's spot on the watch list
					watchListIndex = watchQueue.getIndex(m);
				}
				
				// writes the values into the file so they can be retrieved on load.
				writer.write(
					"\n" + m.getTitle() + "|" + 
					m.getType().toUpperCase() + "|" +
					m.getNotes() + "|" +
					m.getRating() + "|" +
					m.getHasWatched() + "|" +
					watchListIndex
				);
				//System.out.println("I successfully saved");
			}
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// loads media from save. Loads when program is first opened
	public static void loadMedia() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			
			String line;
			reader.readLine(); // skips first line (header) of the file
			
			// reads a new line and continues looping until no more lines are to be read.
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\|"); //splits the line into parts using '|' as a separator.
				
				// each value is retrieved from each part so that each media object can be created
				String title = parts[0];
				String type = parts[1];
				String notes = parts[2];
				int rating = Integer.parseInt(parts[3]);
				boolean hasWatched = Boolean.parseBoolean(parts[4]);
				int watchListIndex = Integer.parseInt(parts[5]);
				
				Media m = new Media(title, type, notes, rating, hasWatched, watchListIndex);
				mediaList.add(m);
				
			}
			
			//set up the watch list in the correct order
			sortByWatchListHighToLow();
			
			// adds the media in order as long as the index isn't equal to -1 (not in the watchlist)
			for (Media m : mediaList) {
				if (m.getWatchListIndex() != -1) {
					watchQueue.addToWatchList(m, 't'); // since sorted, always add to top (lowest index is highest in the list)
				}
			}
			
			
			
			reader.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// function for loading the csv data and then it returns the ArrayList which is used to set up the ArrayList in MediaManager.
	public static ArrayList<Media> getLoadedMediaList() {
		loadMedia();
		return mediaList;
	}

	// selection sort method for mediaList by watchlist index from high to low so watchQueue can be loaded properly
	public static void sortByWatchListHighToLow() {
		for (int i = 0; i < mediaList.size() - 1; i++) {
			int max_index = i;
			// goes through whole list to find if any index is higher than the current one
			for (int j = i + 1; j < mediaList.size(); j++) {
				if (mediaList.get(j).getWatchListIndex() > mediaList.get(max_index).getWatchListIndex()) {
					max_index = j;
				}
			}
				
			// swaps biggest value found with current value found, if no bigger value found it doesn't do much.
			Media temp = mediaList.get(max_index);
			mediaList.set(max_index, mediaList.get(i));
			mediaList.set(i, temp);
		}
	}
}
