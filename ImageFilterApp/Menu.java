package ie.gmit.dip;

import java.util.Scanner;

/**
 * Menu handles user interaction. Displays main menu and submenus. The user can
 * respond to menu choices.
 * 
 * @author Máire Murphy G00375722
 *
 */

public class Menu {
	private ImageProcessor ip = new ImageProcessor(); // filters image with convolution kernel
	private MessageDisplay md = new MessageDisplay(); // formatted message display console
	private KernelDataProcessor kdp = new KernelDataProcessor(); // kernel array processor
	private FileIO io = new FileIO(); // file input/out/validation

	private Scanner s;// console input

	private boolean keepRunning = true; // keep main menu running flag
	private boolean keepSubMenuRunning = false; // keep sub menus running flag.
	private boolean inUse = false; // flag to prevent banner for being displayed in current session
	private boolean imageFound = false; // valid image selected flag
	boolean fileFound = false; // valid csv file selected flag

	private String imageName = null; // image name and location
	private String fileName = null; // csv file name and location

	ConsoleDecorColour[] txtCol = ConsoleDecorColour.values();// populate txtCol array with values held in
																// ConsoleDecorColour Enum

	private static final String LARGE_INDENT = "\t\t\t\t\t\t";// control spacing in console display
	private static final String SMALLER_INDENT = "\t\t\t\t";

	/**
	 * constructor creates a scanner object used to capture user input
	 */
	public Menu() {
		s = new Scanner(System.in);
	}

	/**
	 * displays what image is currently selected
	 */
	private void showCurrentSelectedFile() {
		if (imageName != null) {
			System.out
					.println("\n\n" + ConsoleColour.PINK + LARGE_INDENT + "~ ~  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~ ~");
			System.out.println(LARGE_INDENT + " " + imageName.strip().toLowerCase() + " currently selected.   ");
			System.out.println(LARGE_INDENT + "~ ~  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~ ~");
		}
	}

	/**
	 * controls the overall application logic - via the main menu; it keeps running
	 * until the user selects quit in the main menu.
	 * 
	 * @throws Exception user may enter invalid input via the scanner object, which
	 *                   are caught and handled.
	 */
	public void start() throws Exception {
		keepSubMenuRunning = false;
		// Only show banner first time application is started
		if (inUse == false)
			showAppBanner();
		while (keepRunning) {
			inUse = true;
			showCurrentSelectedFile();

			showMainMenu();

			md.showMsg("action", "Select Option [1-6]" + ConsoleColour.WHITE_BRIGHT + "> ");
			try {
				int choice = Integer.parseInt(s.nextLine().strip());

				switch (choice) {

				case 1 -> {
					imageFound = false;
					getImage();// enter an image name & location
				}
				case 2 -> {
					keepSubMenuRunning = true;
					chooseFilter("effects"); // submenu shows filters options for special effects
				}
				case 3 -> {
					keepSubMenuRunning = true;
					chooseFilter("lines"); // submenu shows options for line detection filters
				}
				case 4 -> {

					filterFromFile();// kernel filter data held in a comma delimited file
					fileName = null;
				}
				case 5 -> {

					customFilter(); // filter entered by user
					fileName = null;
				}
				case 6 -> {
					md.showMsg("info", "Shutting down... ");
					keepRunning = false;
				}
				default -> {
					// displays common error message: "Invalid menu choice. Try again."
					md.wrongChoiceMsg();
				}

				}
			} // try
			catch (NumberFormatException nfe) {
				md.wrongChoiceMsg();
			}

			catch (Exception e) { // these message are shown to user if non numeric content is entered
				md.showMsg("error", e.getMessage());

			}

		}
	}

	/**
	 * Displays a line of rainbow astrix's (*) to decorate the application banner
	 */
	private void showColouredDecoration() {
		int x = 0;
		System.out.print(SMALLER_INDENT + "*");
		// length of array is matched to length of banner text
		for (int i = 0; i <= 60; i++) {
			System.out.print(txtCol[x] + "*");
			x++;
			if (x == txtCol.length - 1)
				x = 0;
		}
		System.out.print(ConsoleColour.WHITE + "*");
	}

	/**
	 * Description information about the project decorated with rainbow *
	 */
	private void showAppBanner() {
		System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n");
		showColouredDecoration();
		System.out.println(ConsoleColour.WHITE_BRIGHT);
		System.out.println(SMALLER_INDENT + "*       GMIT - Dept. Computer Science & Applied Physics       *");
		System.out.println(SMALLER_INDENT + "*                                                             *");
		System.out.println(SMALLER_INDENT + "*                 Image Filtering System V1.0                 *");
		System.out.println(SMALLER_INDENT + "*           H.Dip in Science (Software Development)           *");
		System.out.println(SMALLER_INDENT + "*                                                             *");
		showColouredDecoration();
		System.out.print("\n\n\n");
	}

	/**
	 * displays main menu
	 */
	private void showMainMenu() {
		System.out.print("\n" + ConsoleColour.WHITE_BRIGHT);
		System.out.println(LARGE_INDENT + "______________________________\n");
		System.out.println(LARGE_INDENT + ConsoleColour.WHITE_BOLD_BRIGHT + "\t    Main Menu" + ConsoleColour.RESET);

		System.out.println("\n" + LARGE_INDENT + " 1) Enter Image Name    ");
		System.out.println(LARGE_INDENT + " 2) Effects Filter Menu");
		System.out.println(LARGE_INDENT + " 3) Line Detection Filter Menu");
		System.out.println(LARGE_INDENT + " 4) Apply Filter from File");
		System.out.println(LARGE_INDENT + " 5) Enter a custom filter");
		System.out.println(LARGE_INDENT + " 6) Quit                ");
		System.out.println(LARGE_INDENT + "______________________________ \n\n\n\n");
		System.out.print(ConsoleColour.WHITE);
	}

	/**
	 * prompts user to enter an image to be filtered. The image is then validated to
	 * check if it exists. If user presses c to cancel the program will return to
	 * main menu.
	 * 
	 * @throws Exception: input issues are handled in calling method.
	 */
	private void getImage() throws Exception {

		while (imageFound == false || imageName == "c") {
			md.showMsg("action", "Enter " + ConsoleColour.WHITE_UNDERLINED + "image name" + ConsoleColour.WHITE_BRIGHT
					+ " including path (press 'c' to Cancel)>  ");

			// need to account for spaces in directory and filenames
			imageName = s.nextLine().toLowerCase().strip();

			if (imageName.equals("c")) {
				imageName = null;
				keepSubMenuRunning = false;
				// return to calling method
				return;
			} else {

				imageFound = io.checkImageValid(imageName);
				if (imageFound != true) {
					md.showMsg("error", "Invalid image file. Try again.");
					imageName = null;
				}

			}
		} // while
	}

	/**
	 * 
	 * prompts user to enter an file. The file is then validated to check if it
	 * exists. If user presses c to cancel the program will return to main menu.
	 * 
	 * @throws Exception: input issues are handled by displaying the error to the
	 *                    user.
	 */
	private void getFile() throws Exception {

		try {
			while (fileFound == false || fileName == "c") {

				md.showMsg("action", "Enter " + ConsoleColour.WHITE_UNDERLINED + "CSV file" + ConsoleColour.WHITE_BRIGHT
						+ " name including path (press 'c' to Cancel)>  ");

				// need to account for spaces in directory and filenames
				fileName = s.nextLine().toLowerCase().strip();
				if (fileName.equals("c"))
					// return to calling method as user has canceled operation
					return;
				else
					fileFound = io.checkFileExists(fileName);

				if (fileFound == false)
					md.showMsg("error", "File does not exist. Try again.  ");
			}

		}

		catch (Exception e) {
			md.showMsg("error", e.getMessage());
		}

	}

	/**
	 * Displays menu option for edge detection filters
	 */
	public void lineDectionMenu() {

		System.out.println(
				"\n\n" + LARGE_INDENT + ConsoleColour.WHITE_BOLD + "  Line Detection Filters " + ConsoleColour.WHITE);
		System.out.println(
				LARGE_INDENT + ConsoleColour.WHITE_BOLD + " -------------------------          " + ConsoleColour.WHITE);

		System.out.println(LARGE_INDENT + "1) Outline Edge ");
		System.out.println(LARGE_INDENT + "2) Inside Edge ");
		System.out.println(LARGE_INDENT + "3) Laplacian");
		System.out.println(LARGE_INDENT + "4) Vertical Lines");
		System.out.println(LARGE_INDENT + "5) Horizontal Lines");
		System.out.println(LARGE_INDENT + "6) Diagonal Lines 45 Degrees");
		System.out.println(LARGE_INDENT + "7) Horizonatal Sobel Filter");
		System.out.println(LARGE_INDENT + "8) Vertical Sobel Filter");

		System.out.println(LARGE_INDENT + ConsoleColour.YELLOW + "0) Main Menu \n");
		md.showMsg("action", "Select Option [0-8]" + ConsoleColour.WHITE + "> ");

	}

	/**
	 * Display menu options for other effects filters such as blurring and embossing
	 */

	public void effectsMenu() {

		System.out.println(
				"\n\n" + LARGE_INDENT + ConsoleColour.WHITE_BOLD + "      Effects Filters" + ConsoleColour.WHITE);
		System.out.println(
				LARGE_INDENT + ConsoleColour.WHITE_BOLD + "  ----------------------          " + ConsoleColour.WHITE);

		System.out.println(LARGE_INDENT + "1) Identity (Original)");
		System.out.println(LARGE_INDENT + "2) Box Blur");
		System.out.println(LARGE_INDENT + "3) Gaussian Blur");
		System.out.println(LARGE_INDENT + "4) Emboss");
		System.out.println(LARGE_INDENT + "5) Sharpen Filter");
		System.out.println(LARGE_INDENT + "6) Motion Filter");

		System.out.println(LARGE_INDENT + ConsoleColour.YELLOW + "0) Main Menu \n");
		md.showMsg("action", "Select Option [0-6]" + ConsoleColour.WHITE + "> ");

	}

	/**
	 * Gets user submenu choice for a special effect filters or line detection
	 * filters. Calls method to effectsHandler() or linesHandler based on current
	 * submenu type
	 * 
	 * @throws Exception: if user enters invalid chars, displays error message and
	 *                    allows user to retry
	 */
	private void chooseFilter(String submenuType) throws Exception {
		// keep displaying submenu until user chooses 0 to exit
		while (keepSubMenuRunning) {
			// ensure that there is an image for image filtering
			if (imageFound == false) {
				getImage();

			}
			if (imageFound) {
				// remind user which image is currently selected
				showCurrentSelectedFile();
				// based on submenu type passed from main menu show correct sub menu
				if (submenuType == "effects")
					effectsMenu();
				else
					lineDectionMenu();
				try {
					// make sure answer is an integer if not catch the exception
					int choice = Integer.parseInt(s.nextLine().strip());

					if (submenuType == "effects")
						effectsHandler(choice);
					else
						linesHandler(choice);
				} catch (Exception e) {
					md.wrongChoiceMsg();

					chooseFilter(submenuType);
				}
			}
		}
	}

	/**
	 * based on user choice; pass image name and location, filter identification,
	 * bias (image brightness) and a kernel 2d array if applicable otherwise pass in
	 * null to Image Processor
	 */
	private void effectsHandler(int choice) throws Exception {
		switch (choice) {

		case 1 -> ip.filterImage(imageName, "IDENTITY", 0.0, null);
		case 2 -> ip.filterImage(imageName, "BOX_BLUR", 0.0, null);
		case 3 -> ip.filterImage(imageName, "GAUSSIAN_BLUR", 0.0, null);
		// set bias (image brightness) to 128 for Emboss which makes the image shades of
		// grey
		case 4 -> ip.filterImage(imageName, "EMBOSS", 128.0, null);
		case 5 -> ip.filterImage(imageName, "SHARPEN", 0.0, null);
		case 6 -> ip.filterImage(imageName, "MOTION", 0.0, null);
		case 0 -> {
			keepSubMenuRunning = false; // stop submenu from repeating
			start(); // show main menu
		}
		default -> md.wrongChoiceMsg();
		}
	}

	/**
	 * based on user choice; pass image name and location, filter identification,
	 * bias and a kernel 2d array if applicable otherwise pass in null to Image
	 * Processor
	 */
	private void linesHandler(int choice) throws Exception {
		switch (choice) {

		case 1 -> ip.filterImage(imageName, "EDGE_DETECTION_1", 0.0, null);
		case 2 -> ip.filterImage(imageName, "EDGE_DETECTION_2", 0.0, null);
		case 3 -> ip.filterImage(imageName, "LAPLACIAN", 0.0, null);
		case 4 -> ip.filterImage(imageName, "VERTICAL_LINES", 0.0, null);
		case 5 -> ip.filterImage(imageName, "HORIZONTAL_LINES", 0.0, null);
		case 6 -> ip.filterImage(imageName, "DIAGONAL_45_LINES", 0.0, null);
		case 7 -> ip.filterImage(imageName, "SOBEL_HORIZONTAL", 0.0, null);
		case 8 -> ip.filterImage(imageName, "SOBEL_VERTICAL", 0.0, null);
		case 0 -> {
			keepSubMenuRunning = false;
			start(); // show main menu
		}
		default -> md.wrongChoiceMsg();
		}
	}

	/**
	 * User can select a comma delimited file that contains data for a convolution
	 * kernel. The csv file is validated, converted to a double[][] array and the
	 * image is processed.
	 * 
	 * @throws Exception: throws invalid input error back to calling method.
	 */
	private void filterFromFile() throws Exception {
		String[] data = null; // holds data from csv file
		boolean validData = false; // data from csv file is valid
		double[][] kernel = null; // holds data from data array (csv file)

		fileFound = false;
		// ensure image is selected
		if (imageFound == false)
			getImage();
		if (imageFound == true) {
			// get file name and location and check file exists
			getFile();
			if (fileFound == true) {
				// read file and return valid contents in a String array otherwise return null
				data = io.readCSV(fileName);
				if (data != null)
					// check number of elements in data array.
					validData = kdp.validateSize(data.length);
				if (validData == true)
					// check that the data is numeric
					validData = kdp.checkContentsNumeric(data);
				if (validData == true) {
					// convert the data into a double[][] array
					kernel = kdp.convertToKernel(data);
					// show the convolution kernel on screen
					kdp.displayKernel(kernel);
					// filter the image passing in kernel from file
					ip.filterImage(imageName, "CUSTOM", 0.0, kernel);

				} else
					md.showMsg("error", "The file does not contain data for a valid convolution kernel");
			} else if (fileFound == false) {
				// return to the main menu
				return;
			}
		}
	}

	/**
	 * User can enter their own convolution kernel via the console. Firstly, user
	 * must enter the kernel size. For example if 3 is entered this would result in
	 * a 3x3 array. The user is then prompted to enter a value for each array
	 * element. The user can re-enter a value if an invalid char was pressed by
	 * mistake. User can enter '99' to exit.
	 * 
	 * @throws Exception for invalid data entered
	 */
	private void customFilter() throws Exception {

		// ensure image is selected
		if (imageFound == false)
			getImage();
		if (imageFound == true) {
			int validSize = kdp.getKernelSize(s);
			if (validSize > 1) {
				// create array as size is valid
				double kernel[][] = new double[validSize][validSize];
				// get values from user
				kernel = kdp.getFilterValues(kernel, s);
				if (kernel != null) {
					// show the convolution kernel on screen
					kdp.displayKernel(kernel);
					// pass valid kernel to Image Processor
					ip.filterImage(imageName, "CUSTOM", 0.0, kernel);
				} else
					return;// return to the main menu
			} else if (validSize == -1) {
				return;
			}

		}
	}

}// class
