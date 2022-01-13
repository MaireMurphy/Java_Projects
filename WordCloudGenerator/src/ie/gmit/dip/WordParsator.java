package ie.gmit.dip;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Role of WordParser - is to ensure text stored in a string is cleaned up
 * of non word text\symbols and the text is split into an array of strings
 *
 * 
 * @author Maire Murphy
 *
 */

public interface WordParsator {

	/**
	 * Remove non ASCII letter symbols from a text String. Replaces any matched enum
	 * patterns with space or nothing.
	 * 
	 * @param text: String of text
	 * @return : text containing letters
	 */
	//O(n) - all string has to be searched for match substring
	public static String cleanText(String text) {

		text = text.replaceAll(Patterns.APOSTROPHY.getValue(), "");
		text = text.replaceAll(Patterns.NON_LETTERS.getValue(), " ");
		text = text.replaceAll(Patterns.EXTRA_SPACE.getValue(), " ");
		text = text.toLowerCase().trim();
		return text;
	}

	/**
	 * Splits a String into an array of Strings using a space as the delimiter
	 * @param text: a String of content
	 * @return: array of Strings
	 */
	//O(n) iterate through whole string to match the delimiter
	public static String[] splitText(String text) {
		String[] content = text.split(" ");
		return content;
	}

	/**
	 * Add array of String to a Set collection - ensure there are no duplicates
	 * 
	 *
	 * Add list to HashSet O(1).
	 * @param content: array of Strings
	 * @return Set of Strings
	 */
	//0(n) - String array has to be converted to a list.
	public static Set<String> removeDuplicates(String[] content) {
		Set<String> noDuplicatesIgWords = new HashSet<String>(Arrays.asList(content)); // Removes duplicates from
																						// duplicates

		return noDuplicatesIgWords;
	}

}
