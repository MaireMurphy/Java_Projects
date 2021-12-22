package ie.gmit.dip;


import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * FileIO class handles file input\output related tasks:
 * Read the configuration file and extract the file information
 * 
 * @author Maire Murphy
 */
public class FileIO {

	/**
	 * Opens file into a Buffered Reader. One line is read into a String
	 * @param filePath: location of configuration file
	 * @return 1 line from file as a String
	 */
	private String readConfigFile(String filePath) {
		String ConfigInfo = null;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
			ConfigInfo = br.readLine();

		} catch (FileNotFoundException ex) {
			System.out.println("[ERROR] Cannot find Config.txt. It must be located in project folder.");
		} catch (IOException e) {
			System.out.println("[ERROR] Unable to get configuration information.");
		}

		return ConfigInfo;
	}

	/**
	 * line from configuration file is parsed into fields held in an
	 * an ArrayList
	 * 
	 * @param line: String from configuration file
	 * @return configList: an ArrayList with the configuration fields
	 * @throws Exception if any problems encountered handle in calling method
	 */
	private List<String> parseLine(String line) throws Exception {
		String[] parsedInfo;
		parsedInfo = line.split(" ");

		// Now convert string into ArrayList
		List<String> configList = new ArrayList<String>(Arrays.asList(parsedInfo));

		return configList;
	}

	/**
	 * Handles flow of configuration file tasks. Call read file function. If there is data then call parse data.
	 * 
	 * @param filePath: location config file
	 * @return configInfo: ArrayList with configuration fields.
	 */
	public List<String> getConfigInfo(String filePath) {
		List<String> configInfo = new ArrayList<String>();

		String line = readConfigFile(filePath);
		if (line.length() > 0) {
			try {
				configInfo = parseLine(line);
			} catch (Exception e) {
				System.out.println("Problem encountered reading data from config file.");
			}
		}
		return configInfo;
	}

	/**
	 * 
	 * @return the path where the configuration file is located
	 * top level folder (Jar\project)
	 */
	public String getConfigLocation() {

		String loc = null;

		try {
			// path is the location of the jar\source code
			Path path = Paths.get(FileIO.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		//	System.out.println("Path of source code" + path);
			// loc is in folder that contains src and README.txt
			loc = path.getParent().toString();
			

		} catch (URISyntaxException e) {
			System.out.println("[ERROR] " + e.getMessage());
		}
		return loc;
	}
}