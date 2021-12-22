package ie.gmit.dip;


import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

/** Handles Client side tasks:
 *  - create TCP socket connection to server
 *  - Listen for incoming chat messages
 *  - send chat message to the server
 *  
 *  
 * @author Máire Murphy
 */
public class ClientApp {
	private User u; //access user's username
	boolean exitApp;  //flag
	String host;
	int port;
	private FileIO fileIO; //access to file input/output functionality

	/**
	 * Setup the Client App: get port and host from the configuration file
	 * @param filePath: configuration file path
	 * @param user: allow access to user's user name
	 */
	ClientApp(String filePath, User user) {
		this.u = user;
		this.exitApp = false;
		this.fileIO = new FileIO();
		String configFile = fileIO.getConfigLocation().concat(filePath);
		List<String> configInfo = fileIO.getConfigInfo(configFile);
		this.port = Integer.parseInt(configInfo.get(1));
		this.host = configInfo.get(0);
	}
	
	

	/**
	 * Request that the user can join the chat - checks if user name is unique or user can enter '\q' to quit
	 * repeats until user picks a unique user name
	 * @param socketConn: socket connection
	 * @return true is user joined the chat with a unique 
	 */
	private boolean addUser(Socket socketConn) {
		boolean userAdded = false;
		String addStatus = "notSet";

		try {
			PrintWriter out = new PrintWriter(socketConn.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socketConn.getInputStream()));

			do {
				u.setUsername();
				if (u.getUsername().equals("\\Q"))
					exitApp();
				out.println(u.getUsername());// send user name to the server
				addStatus = in.readLine();
				if (addStatus.equals("notSet"))
					ClientInterface.displayMsg("INFO", "Username: " + u.getUsername() + " already taken. Try again.");
				else {
					ClientInterface.displayMsg("INFO", "Successfully connected to NATTER. There are " + addStatus
							+ " other user(s) connnected. (Type at prompt and press return)");
					userAdded = true;
				}
			} while (addStatus.equals("notSet"));

		} catch (IOException e) {
			ClientInterface.displayMsg("ERROR", "There is an issue setting up username in Natter");
		}
		return userAdded;
	}

	/**
	 * Exit the chat app normally
	 */
	public void exitApp() {
		System.exit(0);
	}

	/**
	 * Execute the client app. Create a socket connection with the Server on the local area network via TCP
	 * Create a thread to handle a chat task 
	 * Create a thread to handle a listening task 
	 
	 */
	public void go() {
		try {
			Socket socketConn = new Socket(host, port);

			// ensure a unique user name setup
			boolean userSet = addUser(socketConn);
			if (userSet) {
				
				// create a new thread to listen
				Thread t1 = new Thread(new Listener(socketConn), u.getUsername() + "-T1");

				// create a thread to chat
				Thread t2 = new Thread(new Chatter(socketConn), u.getUsername() + "-T2");
				t2.setPriority(Thread.NORM_PRIORITY + 1); // set chatter priority a little higher than normal
				t2.start();
				t1.start();

			}
		} catch (UnknownHostException e) {
			ClientInterface.displayMsg("ERROR", "Unknown host: " + host);
			System.exit(1);
		} catch (IOException e) {
			ClientInterface.displayMsg("ERROR", "Cannot connect to the NATTER Server. Try again later.");
			System.exit(1);

		}
	}

	/**
	 * If socket connection is still open - listen for incoming messages
	 * @author Maire Murphy
	 *
	 */
	private class Listener implements Runnable {

		private Socket socket;
		/**
		 * Initialize thread socket with established socket
		 * @param socket
		 */
		Listener(Socket socket) {
			this.socket = socket;
		}

		/**
		 * Task: listen for incoming messages from the server
		 * 
		 * Check if user is exiting the app and check if the socket is still open - before reading in from the server 
		 */
		public void run() {

			if (!socket.isClosed()) {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
					String incoming;
					while (true) {
						if (!exitApp) { //if user has not quit the app
							if (!socket.isClosed()) {
								incoming = in.readLine();
								if (!incoming.isEmpty())
									System.out.println(incoming);
							}
						} else {
							exitApp();
							break;
						}
					}
				}

				catch (IOException e) {
					// if socket closed then ignore exception as user may have exited
				} catch (NullPointerException npe) {
					// exiting in middle of thread
				}
			}
		}
	}

	/**
	 * Enable the User to send chat messages until quit ('\q') is entered
	 * @author Maire Murphy
	 *
	 */
	private class Chatter implements Runnable {
		private Socket socket;

		/**
		 * Initialize thread socket with already established socket
		 * @param clientSocket
		 */
		Chatter(Socket clientSocket) {
			this.socket = clientSocket;
		}

		/**
		 * Chatting thread - enable the user to send chat messages until the user decides to exit or the socket connection is lost
		 * 
		 * Checks that the socket is still open before sending messages to the server
		 */
		public void run() {

			try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

					BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
				String userInput;
				do {
					//check if socket is still open
					if (!socket.isClosed()) {
						userInput = stdIn.readLine();
						userInput.strip();

						if (!userInput.isEmpty()) {

							out.println(userInput);
							out.flush();
						}
					} else {
						ClientInterface.displayMsg ("ERROR", " Cannot connect to the NATTER Server. Try again later.");
						userInput = "\\q";
					}
				} while (!userInput.equals("\\q"));//quit
				exitApp = true;
				ClientInterface.displayMsg("INFO", "Goodbye " + u.getUsername());

			} catch (IOException e) {
				ClientInterface.displayMsg("ERROR", "Issue sending chat text");
			//	e.printStackTrace();
			}
		}

	}
}
