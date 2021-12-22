package ie.gmit.dip;



import java.util.Scanner;

/**
 * User has a unique user name - which is used to join the chat 
 * @author Máire Murphy
 */
public class User {
	private Scanner s;
	private String username;
	
	/**
	 * Ensure empty user name are not allowed
	 * @param username
	 */
	private void validateName(String username) {
		if (username.isBlank()) {
			System.out.println("[ERROR] Empty username not allowed.");
			setUsername();
		}
	
	}
	
	/**
	 * enable user to enter a user name via the console
	 */
	public void setUsername() {
		s = new Scanner(System.in);
		System.out.print("Enter username> ");
		username = s.nextLine().strip().toUpperCase();
		validateName(username);
		
		
	}
	
	
/**
 * Get the user's user name
 * @return user's chosen user name
 */
	public String getUsername() {
		return username;
	}
	
	

}
