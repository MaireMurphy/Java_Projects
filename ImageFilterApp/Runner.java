package ie.gmit.dip;

/**
 * Starts the image processor application. Ensure the console color is reset to
 * defaults and ends any on-going processes
 * 
 * @author Máire Murphy
 *
 */
public class Runner {

	public static void main(String[] args) throws Exception {

		Menu m = new Menu();
		m.start();
		System.out.println(ConsoleColour.RESET);
		// kill any open frames that are displaying output images
		System.exit(0);

	}

}