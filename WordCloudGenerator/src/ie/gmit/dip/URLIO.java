package ie.gmit.dip;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Validates and Scans input a URL source
 * 
 * @author Maire Murphy
 *
 */
public class URLIO extends InputProcesser {

	private URL url;

	URLIO() {

	}

	/**
	 * Checks if the URL exists. Then checks the site can be connected to.
	 * Response code 200 means that the HTML source content can be accessed.
	 * @param source: URL inputed by User
	 * @return true if the site content can be accessed.
	 */
	//O(1) - single operations
	@Override
	public boolean validInputSource(String source) {
		boolean sourceOK = false;
		try {

			this.url = new URL(source);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			int code = connection.getResponseCode();
			switch (code) {
			case 200 -> {
				sourceOK = true; //page can be connected to
			}
			case 404 -> {
				AppInterface.showErrMsg("Error 404. Page not found");
				sourceOK = false;
			}

			case 403 -> {
				AppInterface.showErrMsg("Error 403. Forbidden. ");
				sourceOK = false;
			}
			}

		}

		catch (MalformedURLException e) {
			AppInterface.showErrMsg("There is a problem with the URL. " + e.getMessage());
			return sourceOK;
		}

		catch (IOException ex) {
			AppInterface.showErrMsg("Problem encounterd. " + ex.getMessage());
			return sourceOK;
		}

		return sourceOK;
	}

	/**
	 * Input the source content into a Scanner object
	 */
	//O(1) - single operation
	@Override
	public Scanner scanSource(String source) {

		Scanner sc;
		try {
			sc = new Scanner(url.openStream());

			return sc;
		} catch (IOException e) {

			AppInterface.showErrMsg("Problem encounterd. " + e.getMessage());
			return null;
		}

	}
}
