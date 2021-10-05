
package ie.gmit.dip;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * Contains functions specific to files input, output and file validation
 * 
 * @author Máire Murphy
 *
 */
public class FileIO {

	MessageDisplay md = new MessageDisplay();
	private static final String OUT_FILE_NAME = "Out.png";

	public String getOutFileName() {
		return OUT_FILE_NAME;
	}

	/**
	 * File name validation: check that the image file had an image file extension
	 * check that the file exists and check if the file has contents
	 */
	public boolean checkImageValid(String filePath) {

		boolean validFile = false;
		// create a new file
		File imageFile = new File(filePath);
		// get the file extension
		String ext = getFileExtension(filePath);
		// check the file extension is a valid extension
		boolean extValid = checkIsValidExt(ext);

		if (extValid) {// if the file extension is valid then
			// Check if the specified file Exists or not
			if (imageFile.exists() && !imageFile.isDirectory()) {
				validFile = true;
			}
		}

		// check if the file is empty
		if (imageFile.length() == 0)
			validFile = false;

		return validFile;
	}

	/**
	 * get the filename extension
	 * 
	 * @param fName - file name
	 * @return the file extension
	 */
	public String getFileExtension(String fName) {

		String fileExtension = "";
		// Get file Name first
		// String fileName = f.getName();
		// get the extension (any chars after the .)
		fileExtension = fName.substring(fName.lastIndexOf(".") + 1);
		return fileExtension;

	}

	/**
	 * code sourced from:
	 * https://mkyong.com/java/how-to-write-an-image-to-file-imageio/
	 * 
	 * Check if the image extension matches a supported format.
	 * 
	 * @param ext image file name extension
	 * @return true if valid otherwise false
	 */
	private boolean checkIsValidExt(String ext) {
		// create an array of supported formats
		String writerNames[] = ImageIO.getWriterFormatNames();
		
		// check if input file extension is in array of supported formats

		// if not match found then return false
		return Arrays.stream(writerNames).anyMatch(ext::equals);
	}

	/**
	 * Check does the file exist
	 * 
	 * @param pathToFile - location and name of file
	 * @return false if not file otherwise true
	 */
	public boolean checkFileExists(String pathToFile) throws FileNotFoundException {

		boolean fileExists = false;
		File file = new File(pathToFile);
		if (file.isFile()) {
			fileExists = true;
		}

		return fileExists;
	}

	/**
	 * Read the comma delimited file into a BufferedReader, then read each line and
	 * assign data into a String array using the comma delimiter to divide the data.
	 * If there are any issues with this process then display the error.
	 * 
	 * @param pathToCsv
	 * @return a string array contain the data from the csv file.
	 */
	public String[] readCSV(String pathToCsv) {
		String[] data = null;
		String row = null;

		try (BufferedReader csvReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(pathToCsv))))) {

			// create BufferedReader and read data from csv
			// csvReader = new BufferedReader(new FileReader(pathToCsv));
			while ((row = csvReader.readLine()) != null) {

				data = row.split(",");
				//9 represents a 3x3 valid convolution kernel (smallest possible)
				if (data.length < 9) {
					return data = null;
				}
			}
		} catch (Exception e) {
			md.showMsg("error", "The comma delimited file is not valid. Details: " + e.getMessage());
		}
		return data;
	}

	
	/**
	 * Read image into a bufferedImage in order to operate directly with image data
	 * @param imageName - image name and location
	 * @return buffered image
	 */
	public BufferedImage readInput(String imageName) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(imageName));

		} catch (IOException e) {
			md.showMsg("error", "Unable to read image: " + imageName);
		}
		return image;
	}


	/**
	 * Save the filtered image as the name declared in the constant OUT_FILE_NAME. The image will be created in folder containing jar\source code
	 * @param image: buffered image
	 * @return true is successfully created otherwise false
	 */
	public boolean createOutput(BufferedImage image) {

		boolean createStatus = false;
		try {
			String saveLocation = getOutputLocation();
			ImageIO.write(image, "png", new File(saveLocation + "\\"+ OUT_FILE_NAME));
		
			createStatus = true;
		} catch (IOException e) {
			createStatus = false;
			md.showMsg("error", "Unable to create output file");
		}
		return createStatus;
	}

	/**
	 * 
	 * @return the path where the output file is saved
	 */
	public String getOutputLocation() {

		String loc = null;

		try {
			// path is the location of the jar\source code
			Path path = Paths.get(FileIO.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		//	System.out.println("Path of source code" + path);
			// loc is in folder that contains src and README.txt
			loc = path.getParent().toString();
			

		} catch (URISyntaxException e) {
			md.showMsg("error", e.getMessage());
		}
		return loc;
	}

}
