package ie.gmit.dip;

import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;

/**
 * Handles reading, parsing and loading the ignorewords.txt file into a Set.
 * 
 * @author Maire Murphy
 *
 */
public class IgnoreWords implements WordParsator {

	private InputProcesser in; // handled file input processing
	private static final String IGNORE_WORDS_FILE = "ignorewords.txt"; 
	private Set<String> ignoreWords; //holder for unique collection of ignore words
	private Scanner sc; //Source reader 

	/**
	 * Checks that the ignorewords.txt file (stored with project\jar) is a valid input
	 * source. The contents of the file are read into a String and stored in the IgnoreWords object
	 * using the InputProcessor.
	 */
	//O(1) - single operations
	public void loadIgnoreWords() {

		in = new FileIO();
		// get ignore words file relative to running code location
		String filePath = in.getPath();
		filePath = filePath.concat("\\" + IGNORE_WORDS_FILE);
		boolean fileOK = in.validInputSource(filePath);
		
		if (fileOK) {
			sc = in.scanSource(filePath);
			String ignoreText = in.readScanner(sc);
			parseText(ignoreText);
		//if ignorewords.text is not found then exit the application.
		}else {
			AppInterface.showErrMsg("ignorewords.txt was not located.");
			System.exit(0);
		}

		// make the Set ReadOnly
		ignoreWords = Collections.unmodifiableSet(ignoreWords);
	}

	/**
	 * Get the Collection of ignore words
	 * @return Collection of Strings
	 */
	//O(1) - return reference to collection
	public Collection<String> getIgnoreWords() {
		return ignoreWords;
	}

	/**
	 * Source text for the word cloud is parsed using help methods in the WordParser Interface. 
	 *  The array of strings is then stored in a ConcurrentHashMap.
	 * 
	 * @param text: String of content
	 */
	//O(1) - calls to other functions
	public void parseText(String text) {

		String content = WordParsator.cleanText(text);
		String[] words = WordParsator.splitText(content);

		// returns a set of ignore words
		this.ignoreWords = WordParsator.removeDuplicates(words);

	}

}
