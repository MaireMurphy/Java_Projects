package ie.gmit.dip;

import java.util.Scanner;

/**
 * Displays output to the console. It uses values in the ConsoleColour enum to display text 
 * in different colours. 
 * 
 * @author Maire Murphy
 *
 */

public class AppInterface {
	private static final String SMALLER_INDENT = "\t\t\t";
	private static final String LARGE_INDENT = "\t\t\t\t\t\t";// control spacing in console display
	private static Scanner s = new Scanner(System.in);
	public static final String INVALID_ENTRY = "Invalid entry ('c' to Cancel). Try again.";
	public static final String INVALID_MENU_CHOICE = "Invalid Menu Choice ('c' to Cancel). Try again.";
	
	/**
	 * Informs users the application is shutting down. The Consol colour is reset and the 
	 * system closes normally.
	 */
	public static void exitApp() {
		showInfoMsg("Shutting down...\n\n");
		System.out.println(ConsoleColour.RESET);
		s.close();
		System.exit(1);
	}
	
	/**
	 * Display a formatted Information message to the user
	 * @param msg
	 */
	public static void showInfoMsg(String msg) {
		System.out.println(ConsoleColour.CYAN + "[INFO]   " + msg);
	}
	
	/**
	 * Display a formatted Action message to the user
	 * @param msg
	 */
	public static void showActionMsg(String msg) {
		System.out.print(ConsoleColour.WHITE + "[ACTION] " + msg);
	}
	
	/**
	 * Display a formatted Error message to the user
	 * @param msg
	 */
	public static void showErrMsg(String msg) {
		System.out.println(ConsoleColour.ORANGE + "[ERROR]  " + msg);
	}


	/**
	 * Wrong choice menu message is common across various function
	 */
	public static void wrongChoiceMsg() {

		System.out.println(ConsoleColour.ORANGE + "[ERROR]  " + "Invalid menu choice. Try again.");
	}
	/**
	 * Display the main banner when application starts
	 * Word art taken from https://toools.cloud/text-tools/ascii-art
	 */
	public static void bannerDisplay() {
		System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n");

		System.out.println(ConsoleColour.WHITE_BRIGHT);
		System.out.println(LARGE_INDENT + "GMIT - Dept. Computer Science & Applied Physics       \n");
        System.out.println(LARGE_INDENT + "  H.Dip in Science (Software Development)      \n"); 
        System.out.println(LARGE_INDENT + "      Author: Maire Murphy (G00375722)      \n\n\n\n\n"); 
		System.out.println(SMALLER_INDENT + " dP   dP   dP                         dP     a88888b. dP                         dP ");
		System.out.println(SMALLER_INDENT + " 88   88   88                         88    d8'   `88 88                         88 ");
		System.out.println(SMALLER_INDENT + " 88  .8P  .8P .d8888b. 88d888b. .d888b88    88        88 .d8888b. dP    dP .d888b88 ");
		System.out.println(SMALLER_INDENT + " 88  d8'  d8' 88'  `88 88'  `88 88'  `88    88        88 88'  `88 88    88 88'  `88 ");
		System.out.println(SMALLER_INDENT + " 88.d8P8.d8P  88.  .88 88       88.  .88    Y8.   .88 88 88.  .88 88.  .88 88.  .88 ");
		System.out.println(SMALLER_INDENT + " 8888' Y88'   `88888P' dP       `88888P8     Y88888P' dP `88888P' `88888P' `88888P8 ");
		System.out.println("\n " + LARGE_INDENT + ConsoleColour.GREEN_BOLD_BRIGHT+ "         G E N E R A T O R                                    ");

		System.out.print("\n\n\n");
	}

	/**
	 * displays main menu
	 */
	public static void showMainMenu() {
		System.out.print("\n" + ConsoleColour.WHITE_BRIGHT);
		//System.out.println(LARGE_INDENT + ConsoleColour.GREEN+ "\t  W O R D    C L O U D" + ConsoleColour.RESET);
		System.out.println(LARGE_INDENT + "   ______________________________");
		
		System.out.println(LARGE_INDENT + ConsoleColour.WHITE_BOLD_BRIGHT + "\t  " );
		System.out.println(LARGE_INDENT + ConsoleColour.GREEN + "       Word Cloud Main Menu\n" + ConsoleColour.RESET);

		System.out.println("\n" + LARGE_INDENT + "    1) Generate from File    ");
		System.out.println(LARGE_INDENT + "    2) Generate from URL");
		System.out.println(LARGE_INDENT + "    3) Quit                ");
		System.out.println(LARGE_INDENT + "   ______________________________ \n\n\n\n");
		System.out.print(ConsoleColour.WHITE);
	}
	

	
	
}
