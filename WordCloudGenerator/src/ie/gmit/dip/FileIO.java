package ie.gmit.dip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Handles all file related input/output and validation tasks.
 * 
 * Source for the Word Cloud may come from a file. Also the output is a png
 * file.
 * 
 * @author Maire Murphy
 *
 */
public class FileIO extends InputProcesser {

	private FileInputStream f;

	/**
	 * check that the source inputed by User is a valid file
	 * 
	 * @param source: location of the source file (relative or absolute)
	 * @return true if the file source is OK
	 * 
	 */
	//O(1) - no loops; single operations
	public boolean validInputSource(String source) {
		try {
			File file = new File(source);
			if (file.exists()) {
				this.f = new FileInputStream(source);
				return true;
			} else {
				return false;
			}
		} catch (FileNotFoundException e) {
			// invalid file message handled in calling function
			AppInterface.showErrMsg("Problem encounterd. " + e.getMessage());
			return false;
		}
	}

	/**
	 * The contents of the file are inputed into a Scanner object.
	 * 
	 * @param source: User inputed file source
	 * @return the scanner object containing the source content
	 * @throws an exception back to the calling class that there is an issue with
	 *            the source
	 */
	//O(1) - single operation
	public Scanner scanSource(String source) {

		try {
			this.f = new FileInputStream(source);
		} catch (FileNotFoundException e) {

			AppInterface.showErrMsg("Problem inputting from File. " + e.getMessage());
		}
		Scanner sc = new Scanner(f);
		return sc;
	}

	/**
	 * Used to locate file (ignorewords.txt) saved in project folder\jar
	 * @return the path for file top level folder (Jar\project)
	 */
	//O(1) - single operations
	public String getPath() {

		String loc = null;
		try {
			// path is the location of the jar\source code
			Path path = Paths.get(FileIO.class.getProtectionDomain().getCodeSource().getLocation().toURI());

			loc = path.getParent().toString();

		} catch (URISyntaxException e) {
			System.out.println("[ERROR] " + e.getMessage());
		}
		return loc;
	}
	

	/**
	 * Get the filename extension
	 * 
	 * @param fName - file name
	 * @return the file extension
	 */
	//O(n) - loop the fName String to get last instance of '.'
	public String getFileExtension(String fName) {

		String fileExtension = "";

		// get the extension (any chars after the .)
		fileExtension = fName.substring(fName.lastIndexOf(".") + 1);
		return fileExtension;

	}

	/**
	 * Ensure that the directory location exists
	 * 
	 * @param fPath: absolute or relative path inputed by user
	 * @return true if directory exists otherwise false
	 */
	//O(1) - single if statement
	public boolean validLocation(String fPath) {
		boolean destinationOK = false;
		File file = new File(fPath);
		if (file.isDirectory())
			destinationOK = true;
		else {
			AppInterface.showErrMsg("The directory does not exist");
			destinationOK = false;
		}
		return destinationOK;
	}

	/**
	 * Check if user specified the 'png' file extension.
	 * 
	 * @param inputFile: name of file specified by the user
	 * @param ext: value is "png"
	 * @return true if the file extension specified by the user is png 
	 */
	//O(1) - single operation
	public boolean validExt(String inputFile, String ext) {
		boolean validExt = false;

		if (!getFileExtension(inputFile).toLowerCase().equals(ext)) {
			AppInterface.showErrMsg("Image file requires a 'png' extension");
			validExt = false;
		} else
			validExt = true;
		return validExt;
	}

}
