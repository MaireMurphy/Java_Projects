package ie.gmit.dip;

/**
 * Helper class: display the client banner and any client messages
 * @author Maire Murphy
 *
 */

public class ClientInterface {
	private static final String SMALLER_INDENT = "\t\t\t\t";// add standard space before message
	// Console colors
	public static final String TEXT_YELLOW = "\033[0;33m";
	public static final String TEXT_RESET = "\u001B[0m";
	public static final String TEXT_WHITE = "\u001B[37m";

/**
 * Show a welcome banner to client
 * // ASCII word art is by http://patorjk.com/
 * Set the colour of text to Yellow in the console
 */
	public static void showBanner() {
		    System.out.print("\n\n\n\n\n\n\n\n");
			System.out.println("                                                    Welcome to:");
			System.out.print("\n");

			System.out.println(TEXT_YELLOW);
			// ASCII word art is by http://patorjk.com/
			System.out.println( "NNNNNNNN        NNNNNNNN                          tttt               tttt                                                  ");
			System.out.println( "N:::::::N       N::::::N                       ttt:::t            ttt:::t                                                  ");
			System.out.println( "N::::::::N      N::::::N                       t:::::t            t:::::t                                                  ");
			System.out.println( "N:::::::::N     N::::::N                       t:::::t            t:::::t                                                  ");
			System.out.println( "N::::::::::N    N::::::N  aaaaaaaaaaaaa  ttttttt:::::tttttttttttttt:::::ttttttt        eeeeeeeeeeee    rrrrr   rrrrrrrrr   ");
			System.out.println( "N:::::::::::N   N::::::N  a::::::::::::a t:::::::::::::::::tt:::::::::::::::::t      ee::::::::::::ee  r::::rrr:::::::::r  ");
			System.out.println( "N:::::::N::::N  N::::::N  aaaaaaaaa:::::at:::::::::::::::::tt:::::::::::::::::t     e::::::eeeee:::::eer:::::::::::::::::r ");
			System.out.println( "N::::::N N::::N N::::::N           a::::atttttt:::::::tttttttttttt:::::::tttttt    e::::::e     e:::::err::::::rrrrr::::::r");
			System.out.println( "N::::::N  N::::N:::::::N    aaaaaaa:::::a      t:::::t            t:::::t          e:::::::eeeee::::::e r:::::r     r:::::r");
			System.out.println( "N::::::N   N:::::::::::N  aa::::::::::::a      t:::::t            t:::::t          e:::::::::::::::::e  r:::::r     rrrrrrr");
			System.out.println( "N::::::N    N::::::::::N a::::aaaa::::::a      t:::::t            t:::::t          e::::::eeeeeeeeeee   r:::::r            ");
			System.out.println( "N::::::N     N:::::::::Na::::a    a:::::a      t:::::t    tttttt  t:::::t    tttttte:::::::e            r:::::r            ");
			System.out.println( "N::::::N      N::::::::Na::::a    a:::::a      t::::::tttt:::::t  t::::::tttt:::::te::::::::e           r:::::r            ");
			System.out.println( "N::::::N       N:::::::Na:::::aaaa::::::a      tt::::::::::::::t  tt::::::::::::::t e::::::::eeeeeeee   r:::::r            ");
			System.out.println( "N::::::N        N::::::N a::::::::::aa:::a       tt:::::::::::tt    tt:::::::::::tt  ee:::::::::::::e   r:::::r            ");
			System.out.println( "NNNNNNNN         NNNNNNN  aaaaaaaaaa  aaaa         ttttttttttt        ttttttttttt      eeeeeeeeeeeeee   rrrrrrr            ");
					                                                                                                                        
			System.out.print("\n");
			System.out.println(TEXT_WHITE);
			System.out.println(SMALLER_INDENT + "______________________________________________________________");
			System.out.println(SMALLER_INDENT + "|                                                             |");
			System.out.println(SMALLER_INDENT + "|                   Type '\\q' to leave.                       |");
			System.out.println(SMALLER_INDENT + "|                                                             |");
			System.out.println(SMALLER_INDENT + "|                                                             |");
			System.out.println(SMALLER_INDENT + "|            (Author: Máire Murphy G00375722)                 |");
			System.out.println(SMALLER_INDENT + "|_____________________________________________________________|");
			System.out.print("\n\n\n");
	}

	/**
	 * Show message to user in a standard format
	 * 
	 * @param msgType: Info or Error type messages
	 * @param msg: message body
	 */
	public static void displayMsg(String msgType, String msg) {
		System.out.println("[" + msgType + "]  " + msg);
	}
	

}
