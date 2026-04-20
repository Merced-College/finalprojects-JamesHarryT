/*
 * Name: Harrison Tinley
 * This class loads the CSV files and saves them so the user can pick up where they left off whenever they open the program.
 */

package media_tracker_cpsc_39_final;

import java.io.*;
import java.util.ArrayList;

public class CSVLoader {
	
	private static String filePath = "src/data/media.csv";
	
	//makes an ArrayList that loads media onto it and then this laoded media is transfered to the MediaManager mediaList.
	private static ArrayList<Media> mediaList = new ArrayList<>();
	
	// method for saving media. Used whenever media is added, removed, or edited.
	public static void saveMedia(ArrayList<Media> mediaList) {
		try {
			FileWriter writer = new FileWriter(filePath); 
			//opens new writer and overwrites previous one to refresh it completely whenever media is saved.
			writer.write("Title|Type|Notes|Rating|hasWatched/Played"); //writes header for csv for users to read if they open the file.
			
			// separate values with "|" so commas and other characters can be used in strings
			for (Media m : mediaList) {
				writer.write(
					"\n" + m.getTitle() + "|" + 
					m.getType().toUpperCase() + "|" +
					m.getNotes() + "|" +
					m.getRating() + "|" +
					m.getHasWatched()
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
				String[] parts = line.split("\\|"); //splits the line into parts using commas as a separator.
				
				String title = parts[0];
				String type = parts[1];
				String notes = parts[2];
				int rating = Integer.parseInt(parts[3]);
				boolean hasWatched = Boolean.parseBoolean(parts[4]);
				
				Media m = new Media(title, type, notes, rating, hasWatched);
				mediaList.add(m);
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

}
