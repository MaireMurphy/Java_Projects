package ie.gmit.dip;

/**
 * Role of ScriptParsator is to remove  HTML and Script tags including metadata 
 * from a String plus the functionality it inherits from WordParsator.
 * 
 * @author Maire Murphy
 *
 */
public interface ScriptParsator extends WordParsator {
	/**
	 * Remove script and HTML tags from String using the Patterns from the Patterns enum.
	 * @param text: String of content
	 * @return a String without Script\HTML
	 */
	//O(n) - iterate through string to  match pattern
  public static String cleanScript(String text) {
		text = text.replaceAll(Patterns.SCRIPT_TAG.getValue(), "");
		text = text.replaceAll(Patterns.STYLE_TAG.getValue(), "");
		text = text.replaceAll(Patterns.INSIDE_TAGS.getValue(), " ");
		text = text.replaceAll(Patterns.SPACE_HTML.getValue(), " ");
		text = text.replaceAll(Patterns.QUOTE_HTML.getValue(), "");
		text = text.replaceAll(Patterns.RDQUOTE_HTML.getValue(), "");
		text = text.replaceAll(Patterns.RSQUOTE_HTML.getValue(), "");
		text = text.replaceAll(Patterns.MDASH_HTML.getValue(), "");
		
	  return text;
  }
}
