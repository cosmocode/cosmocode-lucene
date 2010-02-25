package de.cosmocode.lucene.fragments;

import java.util.Collection;

import org.junit.Test;

import de.cosmocode.lucene.LuceneQuery;

/**
 * Tests 
 * 
 * @author Oliver Lorenz
 */
public class AddStringFragment extends LuceneQueryTestFragment {
    
    /**
     * Tests {@link LuceneQuery#addArgument(String)} with null.
     */
    @Test
    public void addArgumentNull() {
        final LuceneQuery query = unit();
        query.addArgument((String) null);
        final String expected = "";
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String)} with an empty String.
     */
    @Test
    public void addArgumentEmpty() {
        final LuceneQuery query = unit();
        query.addArgument("");
        final String expected = "";
        assertEquals(expected, query);
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

}
