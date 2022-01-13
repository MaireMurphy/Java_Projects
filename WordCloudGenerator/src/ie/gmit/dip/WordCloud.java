package ie.gmit.dip;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.concurrent.ConcurrentHashMap;

import java.util.stream.Collectors;

/**
 * Handles Word Cloud related tasks; such getting and setting data required for the word cloud.
 *  It controls setting up the source content.
 * 
 * @author Maire Murphy
 *
 */
public class WordCloud implements ScriptParsator {
	private int maxWords;
	private String source;// file location
	private String destinationFileName; // name and location to save Word Cloud image
	private Sources type; // type 'FILE' or 'URL' from Sources Enum
	private ConcurrentHashMap<String, Integer> uniqueWordsMap; // Stores each unique Word and frequency
	private Map<String, Integer> sortedContent; // word(key)/frequency(value) in descending order by value
	private AppController app; // handle on AppController
	private FileIO in = new FileIO(); // file input/output

	/**
	 * Initialize
	 * 
	 * @param app: handle on existing AppController object
	 */
	// association: tightly coupled
	WordCloud(AppController app) {
		uniqueWordsMap = new ConcurrentHashMap<String, Integer>();
		this.app = app; // use existing AppController object
	}
	
	/**
	 * Once the image is created; empty the maps
	 */
	//O(n) - need to go through the backing array and delete each entry on a HashMap
	private void emptyCollections() {
		uniqueWordsMap.clear();
		sortedContent.clear();
	}

	/**
	 * Get how many words the user want in the output word cloud
	 * 
	 * @return: number of words to display
	 */
	//O(1) - single operation
	public int getMaxWords() {
		return maxWords;
	}

	/**
	 * Handles getting a valid max words to display (1-30) from the User. Allows
	 * user to cancel and return to main menu.
	 * 
	 * @return a flag String to calling method 'CANCELLED' or 'OK'. 'OK' means user
	 *         set the max value.
	 */
	public String setMaxWords() {
		String userEntry = "";
		int tmpNum = 0;
		String okFlag = "";
		do {
			AppInterface.showActionMsg("Enter number of words to display in word cloud - 30 Max ('c' to Cancel)> ");
			try {
				userEntry = app.enterText("number");
				if (userEntry.equals("CANCELLED")) {
					okFlag = "CANCELLED";
					return okFlag;
				} else {

					// convert user entry to a number
					tmpNum = Integer.parseInt(userEntry);
					if (tmpNum > 30 || tmpNum < 1) {
						AppInterface.showErrMsg("Invalid number of display words entered. Try again");
						setMaxWords();
					} else {
						this.maxWords = tmpNum;
						okFlag = "OK";
					}
				}
			} catch (Exception e) {
				AppInterface.showErrMsg(AppInterface.INVALID_ENTRY);

			}
		} while (userEntry.isBlank()); // while user has not entered a value
		return okFlag;
	}

	/**
	 * Returns the source entered by user (file location or URL)
	 * 
	 * @return Source String
	 */
	//O(1)
	public String getSource() {
		return source;
	}

	/**
	 * * Handles getting a source value from the User. Allows user to cancel and
	 * return to main menu.
	 * 
	 * @return a flag String to calling method 'CANCELLED' or 'OK'. 'OK' means user
	 *         set the max value.
	 */
	//O(1)
	public String setSource() {

		String userEntry = "";
		do {
			AppInterface.showActionMsg("Enter " + getType() + " source ('c' to Cancel)>  ");
			try {
				userEntry = app.enterText("text");
				if (userEntry.equals("CANCELLED"))
					return "CANCELLED";
				this.source = userEntry;
			} catch (Exception e) {
				AppInterface.showErrMsg(AppInterface.INVALID_ENTRY);
			}
		} while (userEntry.isBlank());
		return "OK";
	}

	/**
	 * Sets the source
	 * 
	 * @param str
	 */
	//O(1)
	public void setSource(String str) {
		this.source = str;

	}

	/**
	 * get output image location & name
	 * 
	 * @return String with output location & name
	 */
	//O(1)
	public String getDestination() {
		return destinationFileName;
	}

	/**
	 * Handles getting a the location where to create word cloud image from the
	 * User. Allows user to cancel and return to main menu.
	 * 
	 * @return a flag String to calling method 'CANCELLED' or 'OK'. 'OK' means user
	 *         set the max value.
	 */
	//O(1)
	public String setDestinationPath() {
		String userEntry = "";
		boolean validLocation = false;
		do {

			AppInterface.showActionMsg("Enter location for output file ('c' to Cancel)>  ");
			try {
				userEntry = app.enterText("text");
				if (userEntry.equals("CANCELLED")) {
					return "CANCELLED";
				}
				this.destinationFileName = userEntry;
				validLocation = in.validLocation(this.destinationFileName);
				userEntry = "";
			} catch (Exception e) {
				AppInterface.showErrMsg(AppInterface.INVALID_ENTRY);
				System.out.println(e.getStackTrace());
			}
		} while (userEntry.isBlank() && validLocation == false);
		
	
		return "OK";
	}

	/**
	 * Handles getting a file name for word cloud image from the User. It checks if
	 * the User added a 'png' file extension. It combines output filename with the
	 * location Allows user to cancel and return to main menu.
	 * 
	 * @return a flag String to calling method 'CANCELLED' or 'OK'. 'OK' means user
	 *         set the max value.
	 * @return
	 */
	//O(1)
	public String setDestinationFileName() {
		String userEntry = "";
		boolean validFile = false;
		do {

			AppInterface.showActionMsg("Enter name for PNG image ('c' to Cancel)>  ");
			try {
				userEntry = app.enterText("text");
				if (userEntry.equals("CANCELLED")) {
					return "CANCELLED";
				}
				String imageName = this.destinationFileName + "\\" + userEntry;
				validFile = in.validExt(imageName, "png");
				if (validFile)
					this.destinationFileName = imageName;
				else
					userEntry = "";
			} catch (Exception e) {
				AppInterface.showErrMsg(AppInterface.INVALID_ENTRY);

			}
		} while (userEntry.isBlank() && validFile == false);

		return "OK";
	}

	/**
	 * A type is stored depending on type of source.
	 * 
	 * @return 'FILE' or 'URL'
	 */
	//O(1)
	public Sources getType() {
		return type;
	}

	/**
	 * Depending on user choice - the source type is set
	 * 
	 * @param type: 'FILE' or 'URL
	 */
	//O(1)
	public void setType(Sources type) {
		this.type = type;

	}

	/**
	 * Add content from String array into ConcurrentHashMap with a word (key),
	 * frequency of word (value) pair.
	 * 
	 * @param parsed content in a String array
	 */
	// O(n) - loop through all values in array. Insertion into hash map is O(1)
	private void setUniqueWords(String[] content) {

		for (String key : content) {
			// get the value of the specified key
			Integer count = uniqueWordsMap.get(key);

			// initial insertion of a word has frequency value of 1
			if (count == null) {
				// O(1)
				uniqueWordsMap.put(key, 1);
			}
			// increment the frequency of duplicate words
			else {
				// O(1)
				uniqueWordsMap.put(key, count + 1);
			}
		}
		
}

	


	/**
	 * Handles each ignore word (if existing in ConcurrentHashMap) being removed -
	 * using a Thread for each ignore word.
	 * 
	 * @param igWords - read only Collection of ignore words
	 */
	// 0(n) - loop through each value in IgnoreW collection
	public void removeIgnoreWords(Collection<String> igWords) {

		for (String ignoreW : igWords) {

			Runnable task = new RemoveIgnoreWordsTask(uniqueWordsMap, ignoreW);
			Thread worker = new Thread(task);
			worker.setPriority(10);
			worker.setName(ignoreW + "-Thread");
			worker.start();

		}

	}

	/**
	 * Create a sorted map in descending order based on frequency value from the
	 * ConcurrentHashMap uniqueWordsMap using a lambda expression
	 * 
	 * Code integrated from
	 * https://www.tutorialspoint.com/how-can-we-sort-a-map-by-both-key-and-value-using-lambda-in-java
	 */
	// O(n*log(n))
	public void sortContent() {
		sortedContent = uniqueWordsMap.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

	}

	/**
	 * Source text for the word cloud is parsed using help methods in the WordParser
	 * Interface. The array of strings is then stored in a ConcurrentHashMap.
	 * 
	 * @param text: from the source
	 */
	//O(n) - iterate through String to split it
	public void parseText(String text) {

		String content = text;

		if (this.type == Sources.URL)
			content = ScriptParsator.cleanScript(text);
		content = WordParsator.cleanText(content);
		String[] words = WordParsator.splitText(content);
		// add content to a ConcurrentHashMap
		setUniqueWords(words);

	}

	/**
	 * Copies the requested number of top words from the ordered Map into a List
	 * Iterator code amalgamated from
	 * https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
	 * 
	 * @return a List of top words with a specified size
	 */
	// O(log n) - iterate the list up until the max words or O(n) if sorted list is
	// less than or equal max words.
	private List<String> getTopWords() {
		List<String> outputWords = new ArrayList<String>(maxWords);

		Iterator<Entry<String, Integer>> hmIterator = sortedContent.entrySet().iterator();

		// If the source content is small; the maxWords may be greater than the parsed
		// sorted content collection
		if (maxWords > sortedContent.size()) {
			AppInterface
					.showInfoMsg("Max words is greater than the source words available. All words will be displayed.");
			maxWords = sortedContent.size();
		}

		for (int i = 0; i < maxWords; i++) {
			Map.Entry<String, Integer> mapElement = (Map.Entry<String, Integer>) hmIterator.next();
			outputWords.add(mapElement.getKey());
		}
		return outputWords;
	}

	/**
	 * Get top words and trigger word cloud image creation passing in list of top
	 * words and save location\png name
	 * 
	 * @return true if the image created successfully
	 */
	// 0(1) - function calls
	public boolean createImage() {
		List<String> words = getTopWords();
		boolean result = WCImageMaker.createImage(words, getDestination());
		emptyCollections();
		return result;
	}

	/**
	 * Thread - remove the ignore word from the ConcurrentHashMap if it exists.
	 * 
	 * @author Maire Murphy
	 *
	 */
	//O(1) - a removal is index based in hash map
	private class RemoveIgnoreWordsTask implements Runnable {

		private String word;
		private ConcurrentHashMap<String, Integer> contentMap;

		RemoveIgnoreWordsTask(ConcurrentHashMap<String, Integer> map, String word) {
			this.contentMap = map;
			this.word = word;
		}

		@Override
		public void run() {
			// O(1)
			contentMap.remove(word);
			

		}
	}
}
