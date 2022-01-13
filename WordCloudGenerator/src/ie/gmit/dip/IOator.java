 package ie.gmit.dip;

import java.util.Scanner;

/**
 * Ensures that all implementing classes check that the source is 
 * validated and use a Scanner to get input from the source
 *  
 * @author Maire Murphy
 *
 */
public interface IOator {
	/**
	 * Ensure source exists and is valid.
	 * @param source: source inputed by User
	 * @return: true if source exists
	 */
	public boolean validInputSource(String source); //check source exists
	/**
	 * Input the source content into a Scanner
	 * @param source: file location or url
	 * @return: Scanner object
	 */
	public Scanner scanSource(String source); //open the input stream into a Scanner object
}
