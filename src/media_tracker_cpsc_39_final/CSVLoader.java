/*
 * Name: Harrison Tinley
 * This class loads the CSV files and saves them so the user can pick up where they left off whenever they open the program.
 */

package media_tracker_cpsc_39_final;

import java.io.*;
import java.util.ArrayList;

public class CSVLoader {
	
	private static String filePath = "src/data/media.csv";
	
	public static void saveMedia(ArrayList<Media> mediaList) {
		try {
			FileWriter writer = new FileWriter(filePath); 
			//opens new writer and overwrites previous one to refresh it completely whenever media is saved.
			writer.write("Title,Type,Notes,Rating,hasWatched/Played"); //writes header for csv for users to read if they open the file.
			
			
			for (Media m : mediaList) {
				writer.write(
					"\n" + m.getTitle() + "," + 
					m.getType().toUpperCase() + "," +
					m.getNotes() + "," +
					m.getRating() + "," +
					m.getHasWatched()
				);
				System.out.println("I successfully saved");
			}
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
