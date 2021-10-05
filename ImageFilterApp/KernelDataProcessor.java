package ie.gmit.dip;

import java.util.Scanner;

/**
 * KernelDataProcessor performs convolution kernel related processing such as
 * validation checks, displays a kernel and converting data to a double[][]
 * array.
 * 
 * @author Máire Murphy
 *
 */
public class KernelDataProcessor {
	private MessageDisplay md = new MessageDisplay();
	private static final String SMALLER_INDENT = "\t\t\t\t";

	/**
	 * ensures the size is an odd number that can be a perfect square
	 * 
	 * @param filterSize size of a potential convolution kernel
	 * @return true if filterSize can make a perfect square and the number is odd;
	 *         otherwise false
	 */
	public boolean validateSize(int filterSize) {

		boolean valid = false;
		boolean perfectSquare = false;
		//filter should be greater than 1x1
		if (filterSize > 1) {
			//should be an odd number
			if (filterSize % 2 != 0) {

				perfectSquare = isPerfectSquare(filterSize);

				if (perfectSquare == true) {

					valid = true;
				}
			}
		}
		return valid;
	}

	/**
	 * Validation check to ensure the data array contain numeric content that can be
	 * converted to double.
	 * 
	 * @param data
	 * @return true if all the content of the data array is numeric; false if any
	 *         data can not be parsed to numeric.
	 */
	public boolean checkContentsNumeric(String[] data) {
		boolean valid = false;
		try {
			for (int i = 0; i < data.length; i++) {
				Double.parseDouble(data[i]);
			}
			valid = true;
		} catch (Exception e) {
			md.showMsg("error", "File contains invalid data. " + e.getMessage());
		}

		return valid;
	}

	/**
	 * https://www.geeksforgeeks.org/check-if-given-number-is-perfect-square-in-cpp/
	 * Validation check to ensure that x when converted to a square root has no
	 * decimal places
	 * 
	 * @param x
	 * @return true if the x is a perfect square; otherwise false.
	 */
	private boolean isPerfectSquare(int x) {
		if (x >= 0) {

			// Find floating point value of
			// square root of x.
			int sr = (int) Math.sqrt(x);

			// if product of square root
			// is equal, then
			// return T/F

			return ((sr * sr) == x);
		}
		return false;
	}

	/**
	 * Iterates through the string array and inserts into the double[][] array as
	 * type double
	 * 
	 * @param data an array of string data (that is numeric)
	 * @return a 2d double array
	 */
	public double[][] convertToKernel(String[] data) {
		// size of 2d array is the square root of the incoming array
		int kernelSize = (int) Math.sqrt(data.length);

		double kernel[][] = new double[kernelSize][kernelSize];

		int index = 0;
		for (int x = 0; x < kernelSize; x++) {
			for (int y = 0; y < kernelSize; y++) {
				// convert data to type double
				kernel[x][y] = Double.parseDouble(data[index]);
				index++;
			}
		}

		return kernel;
	}

	/**
	 * Get values for the kernel from the user. User can exit by entering 99. Allows
	 * the user to re-enter a value if an invalid char was entered.
	 * 
	 * @param kernel - will be populated with values from user
	 * @return the populated kernel or a null kernel if the user decides to quit.
	 */

	public double[][] getFilterValues(double[][] kernel, Scanner s) {
		// loop for each value in the 2d array

		for (int i = 0; i < kernel.length; i++) {
			for (int x = 0; x < kernel[i].length; x++) {
				try {
					md.showMsg("action", "Enter (" + i + "," + x + ")  (enter 99 to quit) > ");
					kernel[i][x] = Double.parseDouble(s.nextLine().strip());
					if (kernel[i][x] == 99) {
						// exit
						kernel = null;
						return kernel;
					}
				} catch (Exception e) {
					md.showMsg("error", "Invalid entry. Re-enter.");
					// change index to enable re-entry of value
					x--;
				}

			}
		}

		return kernel;
	}

	/**
	 * loops through the 2d kernel array and displays to the console in a tabular
	 * format
	 * 
	 * @param kernel populated with values from file
	 */
	public void displayKernel(double[][] kernel) {
		md.showMsg("info", "Convolution Kernel:   \n");

		for (int x = 0; x < kernel.length; x++) {

			System.out.print(SMALLER_INDENT);
			for (int y = 0; y < kernel[x].length; y++) {
				// format data with 8 spaces and 3 decimal places
				System.out.printf("%8.3f", kernel[x][y]);

			}
			System.out.println("");
		}
		System.out.println("\n\n");
	}
	
	

	/**
	 * prompts user to enter a size for the convolution kernel. The size is
	 * validated. The user prompted to enter a size again if the previous entry was
	 * invalid.
	 * 
	 * @return size entered is returned to if valid, -1 is returned if the user
	 *         cancels or enters an invalid size
	 */
	public int getKernelSize(Scanner s) {
		int size = -1;
		boolean validData = false;
		String answer = "";

		while (validData == false || answer.equals("c")) {
			md.showMsg("action", "Enter filter size (Enter 'c' to Cancel) > ");// "c" is the escape clause
			try {
				answer = s.nextLine().strip().toLowerCase();
				// allow user to enter a string and then convert to an integer if "c" not
				// pressed
				if (answer.equals("c"))
					return -1;
				else
					size = Integer.parseInt(answer); // size of array must be an integer
				// square the entered size for validation
				validData = validateSize((size * size));
				if (validData == false)
					md.showMsg("error", "Size entered is not valid for a convolution kernel.");

			} catch (Exception e) {
				md.showMsg("error", "Invalid entry.");
			}
		}
		return size;
	}

}