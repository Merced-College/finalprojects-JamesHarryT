/*
 * Author: Harrison Tinley
 * Date: 4/22/2026
 * CPSC-39
 * 
 * Class: Main.java
 * Description: Main class to start program with a terminal-based interface. It connects the whole
 * project together using the MediaManager and WatchQueue classes and gets input from the user
 * to perform whatever action is asked.  
 */

package media_tracker_cpsc_39_final;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	private static MediaManager mediaManager = new MediaManager();
	private static WatchQueue watchQueue = new WatchQueue();
	
	
	// called when running program
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		boolean running = true;
		
		
		ArrayList<Media> list = CSVLoader.getLoadedMediaList();
		mediaManager.setMediaList(list);
		
		// cycles through menu options getting user input until the user types '8' and the program ends
		while (running == true) {
			System.out.println("\n--- Media Tracker ---");
			System.out.println("1. Add Media");
			System.out.println("2. Remove Media");
			System.out.println("3. Edit Media");
			System.out.println("4. View One Media");
			System.out.println("5. View All Media");
			System.out.println("6. View Watchlist");
			System.out.println("7. Mark as Watched");
			System.out.println("8. Exit");
			System.out.println("Choose an option (type a number): ");
			
			String input = scnr.nextLine();
			int choice;
			
			// makes sure user enters an integer so program doesn't crash
			try {
				choice = Integer.parseInt(input);
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a number (1-8)");
				continue; //restarts loop 
			}
			
			switch (choice) {

				case 1:
					mediaManager.addMedia(scnr);
					break;
	
				case 2:
					mediaManager.removeMedia(scnr);
					break;
	
				case 3:
					mediaManager.editMedia(scnr);
					break;
	
				case 4:
					mediaManager.viewMedia(scnr);
					break;
	
				case 5:
					mediaManager.printAllMedia(scnr);
					break;
					
				case 6:
					watchQueue.printWatchList();
					break;

				case 7:
					System.out.println("Enter title: ");
					String title = scnr.nextLine();
					watchQueue.removeFromWatchlist(title);
					
					break;
	
				case 8:
					running = false;
					System.out.println("Goodbye!");
					break;
	
				default:
					System.out.println("Invalid option.");
			}
			
		}
		
		scnr.close();
		
		
	}

}
