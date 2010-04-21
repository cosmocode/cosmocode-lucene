package de.cosmocode.lucene.fragments;

import org.junit.Test;

import de.cosmocode.lucene.LuceneQuery;

/**
 * <p> Tests all addArgument-methods that are String related for {@link LuceneQuery}. <br />
 * Tested methods are:
 * </p>
 * <ul>
 *   <li> {@link LuceneQuery#addArgument(String)}</li>
 *   <li> {@link LuceneQuery#addArgument(String, boolean)}</li>
 * </ul>
 * 
 * @author Oliver Lorenz
 */
public class AddArgumentStringFragment extends LuceneQueryTestFragment {
    
    /**
     * Tests {@link LuceneQuery#addArgument(String)} with null.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentNull() {
        final LuceneQuery query = unit();
        query.addArgument((String) null);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String)} with a blank String value.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentBlank() {
        final LuceneQuery query = unit();
        query.addArgument("  ");
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String)} with a valid String.
     */
    @Test
    public void addArgument() {
        final LuceneQuery query = unit();
        query.addArgument(ARG1);
        final String expected = ARG1;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, boolean)} with a null value and true.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentNullTrue() {
        final LuceneQuery query = unit();
        query.addArgument((String) null, true);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, boolean)} with a blank String value and true.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentBlankTrue() {
        final LuceneQuery query = unit();
        query.addArgument("  ", true);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, boolean)} with a valid String value and true.
     */
    @Test
    public void addArgumentTrue() {
        final LuceneQuery query = unit();
        query.addArgument(ARG1, true);
        final String expected = "+" + ARG1;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, boolean)} with a null value and false.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentNullFalse() {
        final LuceneQuery query = unit();
        query.addArgument((String) null, false);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, boolean)} with a blank String value and false.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentBlankFalse() {
        final LuceneQuery query = unit();
        query.addArgument("  ", false);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, boolean)} with a valid String value and false.
     */
    @Test
    public void addArgumentFalse() {
        final LuceneQuery query = unit();
        query.addArgument(ARG1, false);
        final String expected = ARG1;
        assertEquals(expected, query);
    }

}
