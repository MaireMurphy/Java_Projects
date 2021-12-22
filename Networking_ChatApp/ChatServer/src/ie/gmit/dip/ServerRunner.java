package ie.gmit.dip;

/**
 * Launches Chat Server.
 * 
 * @author Máire Murphy
 *
 */

public class ServerRunner {

	public static void main(String[] args) throws Exception {
		// configuration file is located in project folder
		String configFileName = "\\config.txt";

		try {
			Server sr = new Server(configFileName);
			sr.go();
		} catch (Exception e) {
			System.out.println("[ERROR} Unable to start the server. ");
			e.printStackTrace();
			System.exit(1);
		}

	}
}
