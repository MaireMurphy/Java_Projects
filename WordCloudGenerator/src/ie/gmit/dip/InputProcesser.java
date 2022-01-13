package ie.gmit.dip;

import java.util.Scanner;

/**
 * Handles input from the source. (The validate source and scan source are
 * deferred to its inherited classes.) The scanned source is read in a
 * StringBuffer word by word. The StringBuffer is converted to a String.
 * 
 * @author Maire Murphy
 *
 */
public abstract class InputProcesser implements IOator {

	@Override
	public abstract boolean validInputSource(String source);

	@Override
	public abstract Scanner scanSource(String source);

	//implemented in derived class FiloIO
	public String getPath() {
		return null;
	}

	/**
	 * The scanned source is added to a StringBuffer while there is another token
	 * (word) - with a space. The StringBuffer is converted to a String.
	 * 
	 * @param sc: Scanner with source input
	 * @return: String of source content
	 */
	//O(n) - go through each token in the Scanner object
	public String readScanner(Scanner sc) {
		// Instantiating the StringBuffer class to hold the result
		StringBuffer sb = new StringBuffer();
		while (sc.hasNext()) {
			sb.append(sc.next() + " ");
		}

		// Retrieving the String from the String Buffer object
		String result = sb.toString();

		sc.close();
		return result;
	}
}
