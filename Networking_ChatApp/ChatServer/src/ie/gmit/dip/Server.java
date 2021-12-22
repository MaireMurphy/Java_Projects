package ie.gmit.dip;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Handles Server side functionality to enable clients to chat over a <b>LAN using TCP socket connections<b>
 * 
 * @author Máire Murphy
 */
public class Server {
	private ConcurrentHashMap<String, Socket> m = new ConcurrentHashMap<>(); // thread safe storage
	private int poolSize; // thread pool size
	private static final String SMALLER_INDENT = "\t\t\t\t";// add standard space before message
	private int port = 0;
	// Console colors
	public static final String TEXT_PURPLE = "\u001B[35m";
	public static final String TEXT_RESET = "\u001B[0m";
	public static final String TEXT_WHITE = "\u001B[37m";

	/**
	 * Initialize a new server object use FileIO to extract configuration date to
	 * populate port and pool size
	 * 
	 * @param configPath: configuration path location
	 */
	public Server(String configFileName) {
		FileIO io = new FileIO();
		try {
			
			String configFile = io.getConfigLocation().concat(configFileName);
			List<String> configInfo = io.getConfigInfo(configFile);

			this.port = Integer.parseInt(configInfo.get(1));
			if (this.port == 0) {
				System.out.println("[ERROR] There is an issue with the data in the config file");
				System.exit(1);
			}
			this.poolSize = Integer.parseInt(configInfo.get(2));
		} catch (NumberFormatException e) {
			System.out.println("[ERROR] There is an issue with the data in the config file");
		}
	}

	/**
	 * Calculate number of user connected excluding the client viewing the
	 * information
	 * 
	 * @return number of users stored minus the current user
	 */
	private int otherUsers() {
		return m.size() - 1;
	}

	/**
	 * Show server startup display info.
	 */
	private void showBanner() {
		System.out.print("\n\n\n");
		System.out.println(SMALLER_INDENT + "______________________________________________________________");
		System.out.println(SMALLER_INDENT + "|                                                             |");
		System.out.println(SMALLER_INDENT + "|                    NATTER Server Started                    |");
		System.out.println(SMALLER_INDENT + "|                                                             |");
		System.out.println(SMALLER_INDENT + "|                    (Press Ctrl + C to Stop)                 |");
		System.out.println(SMALLER_INDENT + "|                                                             |");
		System.out.println(SMALLER_INDENT + "|               (Author: Máire Murphy G00375722)              |");
		System.out.println(SMALLER_INDENT + "|_____________________________________________________________|");
		System.out.print("\n\n\n");

	}

	/**
	 * Remove a client from tracking map (ConcurrentHashMap)
	 * @param user: user name is the key to identify which entry to remove
	 */
	private void removeUser(String user) {
		m.remove(user);
	}

	/**
	 * Keep track of clients connected by storing their user name as a key and socket in 
	 * a ConcurrentHashMap 
	 * @param clientUsernam: unique String
	 * @param connection: Socket
	 * @return true if successfully added to the ConcurrentHashMap otherwise false
	 */
	private boolean addUser(String clientUsername, Socket connection) {
		boolean userAdded = false;

		if (!m.containsKey(clientUsername)) {
			m.put(clientUsername, connection);

			userAdded = true;
		}

		return userAdded;
	}

	/**
	 * Routinely check if clients disconnected - if a connection is found that is
	 * closed, then remove it from the hashmap
	 */
	private void housekeepConnections() {

		for (Map.Entry<String, Socket> entry : m.entrySet()) {
			String uName = entry.getKey();
			Socket conn = entry.getValue();

			if (conn.isClosed()) {
				System.out.println("[INFO] removed disconnected user from map: " + uName);
				removeUser(uName);
			}
		}

	}

	/**
	 * User can join chat session if an unique user name was supplied. 
	 * @param username: user name sent from the client
	 * @param conn: socket connection
	 * @return status "ok" if a unique user name was supplied. Other wise "notSet" means the user name is already taken
	 */
	private String join(String username, Socket conn) {
		String status = "notSet";
		boolean userAdded = addUser(username, conn);
		status = userAdded == true ? "ok" : "notSet";
		return status;

	}

	/**
	 * Show message to all connected clients
	 * @param msg: String message sent from one client
	 */
	private void broadcastMsg(String msg) {
		housekeepConnections();
		for (Socket conn : m.values()) {
			PrintWriter out;
			try {
				out = new PrintWriter(conn.getOutputStream(), true);
				out.println(TEXT_PURPLE + msg + TEXT_WHITE);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * Show message to a specific client
	 * @param msg: Information\Error message from Server
	 * @param conn: socket connection
	 */
	private void privateMsg(String msg, Socket conn) {

		PrintWriter out;
		try {
			out = new PrintWriter(conn.getOutputStream(), true);
			out.println(msg);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Execute the server. Create server socket. Limit number of threads. Accept incoming connection requests. Create a task 
	 * to handle clients. 
	 */
	public void go() {
		//limit threads to a certain thread pool number specified in the configuration file
		ExecutorService pool = Executors.newFixedThreadPool(this.poolSize);
		try (ServerSocket server = new ServerSocket(this.port)) {

			showBanner();
			System.out.println("\n [INFO] Listening for connection on port " + this.port + " ...\n");
			while (true) {
				try {
					Socket connection = server.accept();
					System.out.println(
							"[INFO] Client connected from " + connection.getInetAddress() + ":" + connection.getPort());
					//Create a thread to handle client tasks
					Callable<Void> task = new ClientHandler(connection, this);

					pool.submit(task);
					housekeepConnections();
				} catch (IOException ex) {
				}
			}
		} catch (IOException ex) {
			System.err.println("[ERROR] Couldn not start server");
		}
	}

	/**
	 * Handles client tasks: only allows clients with a unique user name to join. Listens for incoming messages
	 *  and broadcasts messages to all connected clients
	 * @author Máire Murphy
	 *
	 */
	private class ClientHandler implements Callable<Void> {

		private Socket connection;
		private Server svr;

		/**
		 * Initialize thread socket with the established socket connection. Pass in server object for access to server methods.
		 * @param connection
		 * @param sv
		 */
		ClientHandler(Socket connection, Server sv) {
			this.connection = connection;
			this.svr = sv;
		}

		/**
		 * The call task enables a user with a unique user name to join. Once joined the thread listens for 
		 * incoming messages which are broadcast. If a user quits the app, the user is removed from the stored map of connnections.
		 */
		@Override
		public Void call() {
			String clientUsername;
			String joinStatus = "notSet";

			try {

				System.out.println("Thread: " + Thread.currentThread().getId());
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				// repeat until a unique user name is successfully added to the server
				// concurrent hashmap
				do {
					clientUsername = in.readLine();
					//check if user can join
					joinStatus = join(clientUsername, connection);
					if (joinStatus.equals("notSet"))
						privateMsg("notSet", connection);
					else
						privateMsg((String.valueOf(otherUsers())), connection); //let user know how many others are logged in
					broadcastMsg("[" + clientUsername + " joined]"); //let others know new user joined

				} while (joinStatus.equals("notSet"));

				/*once the client has a unique user name; then read for incoming messages
				 * and forward to other connected clients
				 */
				if (joinStatus.equals("ok")) {
					String inputLine;
					do {

						inputLine = in.readLine();
						//if user had not entered \q (quit chat app)
						if (!inputLine.equals("\\q")) {
							svr.broadcastMsg(clientUsername + "> " + inputLine);

						} else {
							removeUser(clientUsername);
							svr.broadcastMsg("[INFO] " + clientUsername + " has left the chat room. There are "
									+ otherUsers() + " other user(s) connected.");
						}
					} while (!inputLine.equals("\\q")); //loop until the user quits

				}

			} catch (IOException ex) {
				System.err.println(ex);
			} finally {
				try {

					connection.close();
				} catch (IOException e) {
					// ignore;
				}
			}
			return null;
		}
	}

}