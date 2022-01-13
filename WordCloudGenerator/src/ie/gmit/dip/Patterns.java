package ie.gmit.dip;

/**
 * Declare's common patterns that may be matched in content
 * 
 * @author Maire Murphy
 *
 */
public enum Patterns {

	STYLE_TAG("<style[^>]*>(.*?)</style>"), // pattern: <style> [and text inside] </style>
	SCRIPT_TAG("<script[^>]*>(.*?)</script>"), // pattern: <script> [and text inside] </script>
	INSIDE_TAGS("<[^>]*>"), // pattern: all html tags <>
	SPACE_HTML("&nbsp;"), // pattern html space &nbsp;
	NON_LETTERS("[^a-zA-Z]"), // pattern: all non letters
	EXTRA_SPACE("\\s+"),
	APOSTROPHY("'"),
	QUOTE_HTML("&ldquo;"),
	RDQUOTE_HTML("&rdquo;"),
	RSQUOTE_HTML("&rsquo;"),
	MDASH_HTML("&mdash;");

	private final String pattern;

	Patterns(final String pattern) {
		this.pattern = pattern;
	}

	public String getValue() {
		return pattern;
	}
}
