package ie.gmit.dip;

/**
 * Displays messages to user's in a uniform way - utilized across all classes in
 * the application. Error's messages are orange, information messages are cyan and action
 * messages are bright white.
 * 
 * @author Máire Murphy
 *
 */

public class MessageDisplay {

	private static final String SMALLER_INDENT = "\t\t\t";//add standard space before message

	public void showMsg(String type, String msg) {
		
		switch (type) {
		case "error" -> System.out.println(ConsoleColour.ORANGE + SMALLER_INDENT + "[ERROR]  " + msg); 
		case "info" -> System.out.println(ConsoleColour.CYAN + SMALLER_INDENT + "[INFO]   " + msg); 
		case "action" -> System.out.print(ConsoleColour.WHITE_BRIGHT + SMALLER_INDENT + "[ACTION] " + msg); 
		}
	}

	/**
	 * Wrong choice menu message is common across various function
	 */
	public void wrongChoiceMsg() {

		System.out.println(ConsoleColour.ORANGE + SMALLER_INDENT + "[ERROR]  " + "Invalid menu choice. Try again.");
	}
}
