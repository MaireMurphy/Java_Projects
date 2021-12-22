package ie.gmit.dip;

/**
 * Launch the client chat App (to allow a TCP socket connection to be made with the chat server).
 * 
 * Shows a banner when the client starts. The location of the
 * configuration file is specified.
 * @author Maire Murphy
 *
 */
public class ClientRunner {
	public static void main(String[] args) throws Exception {
		
		ClientInterface.showBanner();
		User u = new User();

		//configuration file stored with jar file or above src folder
		String configFileName = "\\config.txt";
		try {
			ClientApp c = new ClientApp(configFileName, u);

			c.go();
		} catch (Exception e) {
			ClientInterface.displayMsg("ERROR", "Could not start chat app. Try again later.");
			System.exit(1);
		}

	}
}