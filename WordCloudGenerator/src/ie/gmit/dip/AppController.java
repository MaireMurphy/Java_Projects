package ie.gmit.dip;

import java.util.Collection;

import java.util.Scanner;

/**
 * Handles control of the main menu flow: create from file/URL or exit. It handles other
 * required data: max words to display/where output is to be saved. Once all necessary
 * data is stored, the word cloud creation is triggered.
 * 
 * @author Maire Murphy
 *
 */
public class AppController {

	private Scanner s;// console input
	private WordCloud wc; // word cloud
	private IgnoreWords ig; // words to ignore in the content for word cloud
	private boolean keepRunning = true; // keep main menu running flag
	private boolean sourceOK = false; // valid input source selected flag
	private InputProcesser ip; // source input - validation etc
	

	/**
	 * Create a new Scanner object for user input and create a new Word Cloud
	 * object. Use previously setup IgnoreWords object in AppController.
	 * 
	 * @param ig: Ignore Words
	 */
	AppController(IgnoreWords ig) {
		s = new Scanner(System.in);
		wc = new WordCloud(this);
		this.ig = ig;
		
	}

	

	/**
	 * Ensure's that the user select's a valid input source. Once done the user is
	 * prompted to enter how many words to display in the WordCloud (max is 30).
	 * Then the user is prompted to enter the destination name and location for the
	 * png output file.
	 */
	private void wordCloudSetup() {
		String userStatus = "OK"; // control flag if user cancels during input

		// user can cancel by typing 'C' and return to the main menu
		while (sourceOK == false) {
		
			userStatus = wc.setSource();// enter a url web address
			if (userStatus.equals("CANCELLED")) {
				sourceOK = false;
				wc.setSource(null);
				return;

			} else {// check source is valid

				if (wc.getType().equals(Sources.FILE))
					this.ip = new FileIO();
				else
					this.ip = new URLIO();
				// example of polymorphism ...depending on user choice, the input processor
				// implementation will be different
				sourceOK = ip.validInputSource(wc.getSource());
				if (sourceOK == false) {
					AppInterface.showErrMsg("Invalid Source.");
					sourceOK = false;
				} else if (sourceOK) {
					userStatus = wc.setMaxWords(); // how many words to display in word cloud
					if (userStatus.equals("CANCELLED")) {
						sourceOK = false;
						wc.setSource(null);
						return;
					} else {
						userStatus = wc.setDestinationPath();// name and location of output png
						if (userStatus.equals("CANCELLED"))
							return;
					    userStatus = wc.setDestinationFileName();
					    if (userStatus.equals("CANCELLED")) 
					    	return;
						// once all necessary data collected then trigger the Word Cloud creation
						if (!userStatus.equals("CANCELLED")) {
							AppInterface.showInfoMsg("Processing ...");
							generateWordCloud();
						}
					}
				}

			}
		}
	}

	/**
	 * Main menu to select the User's source for Word Cloud content. It keeps
	 * running until the user selects 'quit' in the main menu.
	 * 
	 * @throws Exception user may enter invalid input via the scanner object, which
	 *                   are caught and handled.
	 */
	public void go() throws Exception {

		// Only show banner first time application is started

		while (keepRunning) {
			// static function
			AppInterface.showMainMenu();

			AppInterface.showActionMsg("Select Option [1-3]" + ConsoleColour.WHITE_BRIGHT + "> ");
			try {
				int choice = Integer.parseInt(s.nextLine().strip());
				sourceOK = false;

				switch (choice) {

				case 1 -> {// file source
					wc.setType(Sources.FILE);
					wordCloudSetup();

				}
				case 2 -> {// url source
					wc.setType(Sources.URL);
					wordCloudSetup();

				}
				case 3 -> {// exit application
					AppInterface.showInfoMsg("Shutting down... ");
					keepRunning = false;
				}
				default -> {
					// displays common error message: "Invalid menu choice. Try again."
					AppInterface.wrongChoiceMsg();
				}

				}
			} // not a valid number
			catch (NumberFormatException nfe) {
				AppInterface.wrongChoiceMsg();
			}

			catch (Exception e) { // these message are shown to user if non numeric content is entered
				AppInterface.showErrMsg(e.getMessage());

			}

		}
	}

	/**
	 * Controls the logic for creating the word cloud 
	 * 
	 * The source (file or url) is read by Scanner object into a
	 * String. The String is parsed to remove all unnecessary content and split into a String[].
	 * The array of content is loaded into a ConcurrentHashMap with the unique word as the key
	 * and the times it occurs as the value. Words to ignore are removed from the ConcurrentHashMap
	 * using threads. The map is sorted descending order by frequency.
	 * 
	 * The image with the word cloud is created displaying the number of words
	 * specified by the user in the location previously specified. The image is displayed
	 * to the user on screen.
	 *  
	 */
	private void generateWordCloud() {
		boolean imgCreated = false;

		Scanner sc = ip.scanSource(wc.getSource());
		String sourceText = ip.readScanner(sc);
		wc.parseText(sourceText);
		Collection<String> ignoreWords = ig.getIgnoreWords();
		if (ignoreWords == null)
			AppInterface.showErrMsg("the ignore word collection is null");
		
		wc.removeIgnoreWords(ignoreWords);
		try {
			//Make main thread sleep for 3 seconds- to let remove ignore words threads finish
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// sort the ConcurrentHashMap by it's frequency value
		wc.sortContent();
		imgCreated = wc.createImage();
		if (imgCreated) {
			AppInterface.showInfoMsg(
					"Congratulations! Word Cloud image successfully created at: " + wc.getDestination() +  "\n\n\n");
			try {
				new ImageDisplay(wc.getDestination());
			} catch (Exception e) {
				AppInterface.showErrMsg("The word cloud image can not be displayed due to an issue" + e.getMessage());
			}
		}

	}

	/**
	 * Accepts User input. Input is formatted. Then checked if user cancelled; if
	 * not the input is sent for basic validation.
	 * 
	 * @param typeEntry number or a String
	 * @return true if the input is OK otherwise false
	 */
	public String enterText(String typeEntry) {
		String userEntry = "";
		boolean status = false;
		try {
			userEntry = s.nextLine().toLowerCase().strip().trim();
			if (userEntry.equals("c")) {
				return "CANCELLED";
			}
			status = validateEntry(userEntry, typeEntry);

		} catch (Exception e) {
			AppInterface.showErrMsg(AppInterface.INVALID_ENTRY);
		}
		if (status == true)
			return userEntry;
		else
			return userEntry = "";

	}

	/**
	 * Performs basic validation on User input depending if the input is a number or
	 * a String
	 * 
	 * @param entry: User input
	 * @param type:  number or a String
	 * @return true is not blank; if its a number then it must be greater than 0
	 */
	private boolean validateEntry(String entry, String type) {
		boolean entryOK = false;
		entryOK = entry.isBlank() ? false : true;

		if (type.equals("number")) {
			int number = Integer.parseInt(entry);
			entryOK = number > 0 ? true : false;
		}
		
		return entryOK;
	}

}
