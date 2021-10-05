
package ie.gmit.dip;

import java.awt.image.BufferedImage;

/**
 * The FilterImage class contains functionality for image filtering using a
 * Convolution Kernel.
 * 
 * @author Máire Murphy G00375722
 *
 */
public class ImageProcessor {
	private BufferedImage image = null; // image
	private MessageDisplay md = new MessageDisplay(); // display uniform formatded messages
	private FileIO io = new FileIO(); // file handling

	/**
	 * filterImage - manages the processing of an image to filter is using the
	 * required convolution kernel. The required image is opened and a new filtered
	 * version is saved using the FileIO class. The filtered output image is
	 * displayed to the user via ImageDisplay class.
	 * 
	 * @param imageName:    input image file to be processed
	 * @param filterChoice: User's filter choice i.e. identity, outline, custom
	 *                      (from file or user input) ...
	 * @param bias:         brightness setting.
	 * @param customKernel: a double[][] array populated with data from either file
	 *                      or user input; Otherwise it is null.
	 */
	public void filterImage(String imageName, String filterChoice, double bias, double[][] customKernel) {
		double[][] filter = null; // holds filter from Enum

		// image is a buffered image which gives access to pixel manipulation
		image = io.readInput(imageName);

		md.showMsg("info", "Processing image...\n");

		int w = image.getWidth();
		int h = image.getHeight();

		/*
		 * catch any exceptions with the image processing for example if the image is
		 * corrupt
		 */
		try {

			switch (filterChoice) {
			// CUSTOM handles kernel from user console input or from file
			case "CUSTOM" -> filter = customKernel;
			// all other choices will use filters from the Kernel Enum
			default ->
				// get the matching filter from the Kernel enum and assign to a 2D array
				filter = Kernel.valueOf(filterChoice).getValues();
			}
			// Multiplication factor is used in filtering when the sum of the kernel is >1.0
			double multFactor = getMultiplicationFactor(filter);
			// applies the filter to a pixel
			applyKernel(w, h, filter, bias, multFactor);

			// creates the filtered output image in the folder above src folder
			boolean outputStatus = io.createOutput(image);

			if (outputStatus == true) {
				/* calculate the output location to display the filtered image
				 * one level above where source code is running
				 */
				String fileName = io.getOutFileName();
				String outputLoc = io.getOutputLocation();
				String imageLoc = outputLoc + "\\" + fileName;

				/*
				 * if the file was successfully created show information to the user such as
				 * output filename, location and success status
				 */
				md.showMsg("info", "Filtered image " + fileName + " successfully created. Located in: " + outputLoc);
				// displays the filtered output image
				new ImageDisplay(imageLoc);
				// if there is a problem displaying image, wait a little time to show error
				// message before moving on
				Thread.sleep(1000);

			}
		} catch (Exception e) {
			md.showMsg("error", "Unable to process the image");
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Loops through the image one pixel at a time. The convolution filter is
	 * applied to the current pixel and its neighbouring pixels (to each RGB stream
	 * of the pixel). If the current pixel is on an edge, then then the opposite
	 * edge pixel is used (wrapped). Bias and multiplication factor are applied and
	 * the new values are applied to the image pixel.
	 * 
	 * @param imageWidth
	 * @param imageHeight
	 * @param filter:              kernel
	 * @param bias:                default is 1.0 or 128 for emboss filter
	 * @param multiplicationFactor
	 */
	public void applyKernel(int imageWidth, int imageHeight, double[][] filter, double bias,
			double multiplicationFactor) {

		double red = 0.0, green = 0.0, blue = 0.0;
		int r = 0, g = 0, b = 0, a = 0, p = 0;

		// Image pixel location is read as Column (y coordinate), Row (x coordinate)
		for (int y = 0; y < imageHeight; y++) {
			for (int x = 0; x < imageWidth; x++) {

				red = 0.0;
				green = 0.0;
				blue = 0.0;
				try {

					// loop for kernel
					for (int row = 0; row < filter.length; row++) {
						for (int col = 0; col < filter[row].length; col++) {
							// current index
							int imageX = x + col;
							int imageY = y + row;

							// check if current image coordinate is outside the image boundaries
							if (imageX < 0 || imageX >= imageWidth) {
								// x is out of bounds! wrap opposite edge
								imageX = (imageX - filter.length / 2 + imageWidth) % imageWidth;
							}

							if (imageY < 0 || imageY >= imageHeight) {
								// y is out of bounds! wrap to opposite edge
								imageY = (imageY - filter[row].length / 2 + imageHeight) % imageHeight;
							}
							// current pixel (integer)
							p = image.getRGB(imageX, imageY);

							// extract red, green, blue and transparency integer values
							a = (p >> 24) & 0xff; // Shift bits and unsign
							r = (p >> 16) & 0xff;
							g = (p >> 8) & 0xff;
							b = p & 0xff; // first 8 bits

							// apply kernel value to each color stream
							red += r * filter[row][col];
							green += g * filter[row][col];
							blue += b * filter[row][col];
						}
					}
					// apply bias and multiplication factor. If value is negative set to 0 if value
					// greater than 255 set to 255
					int outRed = Math.min(Math.max((int) (multiplicationFactor * red + bias), 0), 255);
					int outGreen = Math.min(Math.max((int) (multiplicationFactor * green + bias), 0), 255);
					int outBlue = Math.min(Math.max((int) (multiplicationFactor * blue + bias), 0), 255);
					// assign the pixels red, green, blue values (transparency is unchanged)
					p = (a << 24) | (outRed << 16) | (outGreen << 8) | outBlue;
					// apply new value p to pixel at coordinate x, y in the image
					image.setRGB(x, y, p);

				} catch (Exception e) {
					// System.out.println("There was a problem with the bounds");
					continue;
				}
			}

		}
	}

	/**
	 * Calculates the multiplication factor used for image filtering using
	 * convolution kernel
	 * 
	 * @param kernel: filter kernel
	 * @return 1.0 divided by the sum if sume is greater than 1.0. Otherwise 1.0 is
	 *         returned.
	 */
	private double getMultiplicationFactor(double[][] kernel) {
		double multFactor = 0.0;
		double sum = 0.0;

		// loop for kernel
		for (int i = 0; i < kernel.length; i++) {
			for (int j = 0; j < kernel[i].length; j++) {
				sum += kernel[i][j];
			}
		}

		if (sum > 1.0)
			multFactor = 1.0 / sum;
		else
			multFactor = 1.0;

		return multFactor;
	}
}
