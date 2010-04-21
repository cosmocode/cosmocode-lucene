package de.cosmocode.lucene.fragments;

import org.junit.Test;

import de.cosmocode.lucene.LuceneQuery;
import de.cosmocode.lucene.QueryModifier;

/**
 * <p> Tests all addArgument-methods that take 
 * a generic Array as first argument for {@link LuceneQuery}.
 * </p>
 * Tested methods are:
 * <ul>
 *   <li> {@link LuceneQuery#addArgument(Object[])} </li>
 *   <li> {@link LuceneQuery#addArgument(Object[], boolean)} </li>
 * </ul>
 * @author Oliver Lorenz
 */
public final class AddArgumentArrayFragment extends LuceneQueryTestFragment {
    
    @Override
    public LuceneQuery unit() {
        final LuceneQuery unit = super.unit();
        unit.setModifier(QueryModifier.start().required().end());
        return unit;
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Object[])} with null.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentNull() {
        final LuceneQuery query = unit();
        query.addArgument((Object[]) null);
        query.getQuery();
    }

    /**
     * Tests {@link LuceneQuery#addArgument(Object[])} with an empty array.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentEmpty() {
        final LuceneQuery query = unit();
        query.addArgument(new String[] {});
        query.getQuery();
    }

    /**
     * Tests {@link LuceneQuery#addArgument(Object[])}
     * with an array that contains only null or empty or blank Strings.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentOnlyInvalid() {
        final LuceneQuery query = unit();
        query.addArgument(new String[] {"   ", null, "", null, "   "});
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Object[])} with an array with one element.
     */
    @Test
    public void addArgumentOneElement() {
        final LuceneQuery query = unit();
        query.addArgument(new String[] {ARG1});
        final String expected = "+" + ARG1;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Object[])} with an array with 2 elements.
     */
    @Test
    public void addArgumentTwoElements() {
        final LuceneQuery query = unit();
        query.addArgument(new Object[] {ARG1, ARG2});
        final String expected = "+" + ARG1 + " +" + ARG2;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Object[])} with an array that contains null and blank String.
     */
    @Test
    public void addArgumentContainsInvalid() {
        final LuceneQuery query = unit();
        query.addArgument(new String[] {ARG1, "", null, ARG3, "   "});
        final String expected = "+" + ARG1 + " +" + ARG3;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Object[], boolean)} with null and true.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentNullTrue() {
        final LuceneQuery query = unit();
        query.addArgument((Object[]) null, true);
        query.getQuery();
    }

    /**
     * Tests {@link LuceneQuery#addArgument(Object[], boolean)}
     * with an empty array and true.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentEmptyTrue() {
        final LuceneQuery query = unit();
        query.addArgument(new Object[] {}, true);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Object[], boolean)}
     * with a list with one element and true.
     */
    @Test
    public void addArgumentOneElementTrue() {
        final LuceneQuery query = unit();
        query.addArgument(new String[] {ARG1}, true);
        final String expected = "+" + ARG1;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Object[], boolean)}
     * with a list with 2 elements and true.
     */
    @Test
    public void addArgumentTwoElementsTrue() {
        final LuceneQuery query = unit();
        query.addArgument(new Object[] {ARG1, ARG2}, true);
        final String expected = "+" + ARG1 + " +" + ARG2;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Object[], boolean)}
     * with a list that contains nulls and empty Strings and with the parameter true.
     */
    @Test
    public void addArgumentContainsInvalidTrue() {
        final LuceneQuery query = unit();
        query.addArgument(new String[] {ARG1, "", null, ARG3, "   "}, true);
        final String expected = "+" + ARG1 + " +" + ARG3;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Object[], boolean)}
     * with null and false.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentNullFalse() {
        final LuceneQuery query = unit();
        query.addArgument((Object[]) null, false);
        query.getQuery();
    }

    /**
     * Tests {@link LuceneQuery#addArgument(Object[], boolean)}
     * with an empty collection and with false.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentEmptyFalse() {
        final LuceneQuery query = unit();
        query.addArgument(new String[] {}, false);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Object[], boolean)}
     * with a list with one element and with false.
     */
    @Test
    public void addArgumentOneElementFalse() {
        final LuceneQuery query = unit();
        query.addArgument(new String[] {ARG1}, false);
        final String expected = ARG1;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Object[], boolean)}
     * with a list with 2 elements and with false.
     */
    @Test
    public void addArgumentTwoElementsFalse() {
        final LuceneQuery query = unit();
        query.addArgument(new Object[] {ARG1, ARG2}, false);
        final String expected = "(" + ARG1 + " " + ARG2 + ")";
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Object[], boolean)}
     * with a list that contains null and empty String with the parameter false.
     */
    @Test
    public void addArgumentContainsInvalidFalse() {
        final LuceneQuery query = unit();
        query.addArgument(new String[] {ARG1, "", null, ARG3, "   "}, false);
        final String expected = "(" + ARG1 + " " + ARG3 + ")";
        assertEquals(expected, query);
    }
    
}
