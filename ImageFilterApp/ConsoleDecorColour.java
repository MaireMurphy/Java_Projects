package ie.gmit.dip;

/**
 * Ascci codes for text colour.
 * 
 * @author Máire Murphy
 *
 */
public enum ConsoleDecorColour {
	//Reset
    RESET("\033[0m"),  							//Text Reset
	
	//Regular Colors

    RED("\033[0;31m"),     						//RED
    GREEN("\033[0;32m"),   						//GREEN
    YELLOW("\033[0;33m"),  						//YELLOW
    PURPLE("\033[0;35m"),  						//PURPLE
    CYAN("\033[0;36m"),    						//CYAN
    WHITE("\033[0;37m"),   						//WHITE
    PINK("\033[38;5;206m"),						//pink
    ORANGE("\033[38;5;208m");					//ORANGE
    
    
	private final String colour;
	
	ConsoleDecorColour(String colour) {
		this.colour = colour;
	}
	
	public String colour() { 
		return this.colour; 
	}
	
	@Override
    public String toString() {
        return colour;
    }
}
