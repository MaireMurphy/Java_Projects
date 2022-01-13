package ie.gmit.dip;

/**
 * Launches the Word Cloud Generator. Displays the welcome banner. Starts a
 * thread to perform Ignore words setup. It calls AppController's go() function
 * to kick start the main menu (passing in the existing IgnoreWord object).
 * Finally it ensures all process are ended when User exits.
 * 
 * @author Maire Murphy
 *
 */
public class Runner {

	//O(1) - other function calls
	public static void main(String[] args) {
		AppInterface.bannerDisplay(); //show welcome banner from static helper function

		IgnoreWords ig = new IgnoreWords();

		// start a separate thread to perform ignore words setup
		Runnable task = new IgnoreWordsSetup(ig);
		Thread worker = new Thread(task);
		worker.setName("LoadIgnoreWords-Task");
		worker.start();

		try {
			new AppController(ig).go(); //call main menu
			System.out.println(ConsoleColour.RESET);
			System.exit(1);// close any open processes

		} catch (Exception e) {
			AppInterface.showErrMsg("A problem occured on start up. " + e.getMessage());
		}
	}

	/**
	 * The thread handles the setup of the ignore words from a file into a read only
	 * Set collection.
	 * 
	 * @author Maire Murphy
	 *
	 */
	//O(1) - function call
	private static class IgnoreWordsSetup implements Runnable {

		private IgnoreWords ig;

		IgnoreWordsSetup(IgnoreWords ig) {
			this.ig = ig;
		}

		@Override
		public void run() {
			ig.loadIgnoreWords();

		}
	}

}
