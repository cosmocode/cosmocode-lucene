package de.cosmocode.lucene;

import java.util.regex.Pattern;

import de.cosmocode.patterns.Factory;

/**
 * This class provides some static helper methods to format or escape input for Lucene queries.
 * 
 * @author olorenz
 */
public final class LuceneHelper {
    
    
    public static final Factory<LuceneQuery> DEFAULT_FACTORY = new DefaultLuceneQueryFactory();
    
    
    //---------------------------
    //   public helper methods
    //---------------------------
    
    // escape +,\,&,|,!,(,),{,},[,],^,~,?,*,: and blanks  with "\"
    public static final Pattern ESCAPE_PATTERN           = Pattern.compile("[\\Q+-\\&|!(){}[]^~?*:; \\E]");
    // escape +,\,&,|,!,(,),{,},[,],^,~,?,*,: with "\"
    public static final Pattern ESCAPE_NO_BLANKS_PATTERN = Pattern.compile("[\\Q+-\\&|!(){}[]^~?*:;\\E]");
    
    public static final Pattern QUOTES_PATTERN           = Pattern.compile("\"");
    
    
    private LuceneHelper() {
    }
    
    
    
    /**
     * Escapes quotes (") in a given input (" => \").
     * <br>Example:
     * <pre>
     *   final String escaped = LuceneHelper.escapeQuotes("test \"wichtig\" blubb");
     *   System.out.println(escaped);  // test \"wichtig\" blubb
     * </pre>
     * @param input the input to escape
     * @return the input with quotes escaped ("\"" => "\\\"")
     */
    public static String escapeQuotes(final String input) {
        if (input == null) return "";
        return QUOTES_PATTERN.matcher(input).replaceAll("\\\"");
    }
    
    
    /**
     * Removes quotes (") from a given input.
     * <br>Example:
     * <pre>
     *   final String escaped = LuceneHelper.removeQuotes("test \"wichtig\" blubb");
     *   System.out.println(escaped);  // test wichtig blubb
     * </pre>
     * @param input the input to escape
     * @return the input with quotes removed
     */
    public static String removeQuotes(final String input) {
        if (input == null) return "";
        return QUOTES_PATTERN.matcher(input).replaceAll("");
    }
    
    
    public static String removeSpecialCharacters(final String input) {
        if (input == null) return "";
        final Pattern pattern = ESCAPE_PATTERN;
        return escapeQuotes(pattern.matcher(input).replaceAll(""));
    }
    
    
    /**
     * Escapes special chars for solr.<br>
     * Special chars are: +,\,&,|,!,(,),{,},[,],^,~,?,*,:,;<br>
     * If `escapeBlanks` is true, then blanks are escaped, otherwise they are removed.
     * They are escaped with "\".<br>
     * <br>
     * This function was taken (with heavy modifications) from
     * http://www.javalobby.org/java/forums/t86124.html
     * @param input the input to escape
     * @param escapeBlanks if true, then blanks are escaped, otherwise they are removed
     * @return the input, escaped for solr
     */
    public static String escapeInput(final String input, final boolean escapeBlanks) {
        if (input == null) return "";
        final Pattern pattern = escapeBlanks ? ESCAPE_PATTERN : ESCAPE_NO_BLANKS_PATTERN;
        return pattern.matcher(input).replaceAll("\\\\$0");
    }
    
    
    /**
     * Escapes all special chars, blanks and quotes for solr.
     * <br>Escaped chars are: +,\,&,|,!,(,),{,},[,],^,~,?,*,:
     * @param input the input to escape
     * @return the input with escaped special chars
     */
    public static String escapeAll(final String input) {
        return escapeQuotes(escapeInput(input, true));
    }
    
    
    /**
     * Creates a new default LuceneQuery.
     * The returned LuceneQuery is not threadsafe.
     * @return a new default LuceneQuery
     */
    public static LuceneQuery newQuery() {
        return DEFAULT_FACTORY.create();
    }

}
