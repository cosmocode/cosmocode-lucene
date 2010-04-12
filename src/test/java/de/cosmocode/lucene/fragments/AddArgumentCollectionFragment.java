package de.cosmocode.lucene.fragments;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import de.cosmocode.lucene.LuceneQuery;
import de.cosmocode.lucene.QueryModifier;

/**
 * <p> Tests all addArgument-methods that are Collection related for {@link LuceneQuery}. <br />
 * Tested methods are:
 * </p>
 * <ul>
 *   <li> {@link LuceneQuery#addArgument(Collection)} </li>
 *   <li> {@link LuceneQuery#addArgument(Collection, boolean)} </li>
 * </ul>
 * @author Oliver Lorenz
 */
public final class AddArgumentCollectionFragment extends LuceneQueryTestFragment {
    
    @Override
    public LuceneQuery unit() {
        final LuceneQuery unit = super.unit();
        unit.setDefaultQueryModifier(QueryModifier.start().required().end());
        return unit;
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection)} with null.
     */
    @Test
    public void addArgumentNull() {
        final LuceneQuery query = unit();
        query.addArgument((Collection<?>) null);
        final String expected = "";
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addArgument(Collection)} with an empty collection.
     */
    @Test
    public void addArgumentEmpty() {
        final LuceneQuery query = unit();
        query.addArgument(Collections.emptySet());
        final String expected = "";
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection)} with a list with one element.
     */
    @Test
    public void addArgumentOneElement() {
        final LuceneQuery query = unit();
        query.addArgument(ImmutableList.of(ARG1));
        final String expected = "+" + ARG1;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection)} with a list with 2 elements.
     */
    @Test
    public void addArgumentTwoElements() {
        final LuceneQuery query = unit();
        query.addArgument(ImmutableList.of(ARG1, ARG2));
        final String expected = "+" + ARG1 + " +" + ARG2;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection)} with a list that contains null and empty String.
     */
    @Test
    public void addArgumentContainsInvalid() {
        final LuceneQuery query = unit();
        query.addArgument(Lists.newArrayList(ARG1, "", null, ARG3, ""));
        final String expected = "+" + ARG1 + " +" + ARG3;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, boolean)} with null and true.
     */
    @Test
    public void addArgumentNullTrue() {
        final LuceneQuery query = unit();
        query.addArgument((Collection<?>) null, true);
        final String expected = "";
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addArgument(Collection, boolean)}
     * with an empty collection and true.
     */
    @Test
    public void addArgumentEmptyTrue() {
        final LuceneQuery query = unit();
        query.addArgument(Collections.emptySet(), true);
        final String expected = "";
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, boolean)}
     * with a list with one element and true.
     */
    @Test
    public void addArgumentOneElementTrue() {
        final LuceneQuery query = unit();
        query.addArgument(ImmutableList.of(ARG1), true);
        final String expected = "+" + ARG1;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, boolean)}
     * with a list with 2 elements and true.
     */
    @Test
    public void addArgumentTwoElementsTrue() {
        final LuceneQuery query = unit();
        query.addArgument(ImmutableList.of(ARG1, ARG2), true);
        final String expected = "+" + ARG1 + " +" + ARG2;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, boolean)}
     * with a list that contains nulls and empty Strings and with the parameter true.
     */
    @Test
    public void addArgumentContainsInvalidTrue() {
        final LuceneQuery query = unit();
        query.addArgument(Lists.newArrayList(ARG1, "", null, ARG3, ""), true);
        final String expected = "+" + ARG1 + " +" + ARG3;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, boolean)}
     * with null and false.
     */
    @Test
    public void addArgumentNullFalse() {
        final LuceneQuery query = unit();
        query.addArgument((Collection<?>) null, false);
        final String expected = "";
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addArgument(Collection, boolean)}
     * with an empty collection and with false.
     */
    @Test
    public void addArgumentEmptyFalse() {
        final LuceneQuery query = unit();
        query.addArgument(Collections.emptySet(), false);
        final String expected = "";
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, boolean)}
     * with a list with one element and with false.
     */
    @Test
    public void addArgumentOneElementFalse() {
        final LuceneQuery query = unit();
        query.addArgument(ImmutableList.of(ARG1), false);
        final String expected = ARG1;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, boolean)}
     * with a list with 2 elements and with false.
     */
    @Test
    public void addArgumentTwoElementsFalse() {
        final LuceneQuery query = unit();
        query.addArgument(ImmutableList.of(ARG1, ARG2), false);
        final String expected = "(" + ARG1 + " " + ARG2 + ")";
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, boolean)}
     * with a list that contains null and empty String with the parameter false.
     */
    @Test
    public void addArgumentContainsInvalidFalse() {
        final LuceneQuery query = unit();
        query.addArgument(Lists.newArrayList(ARG1, "", null, ARG3, "   "), false);
        final String expected = "(" + ARG1 + " " + ARG3 + ")";
        assertEquals(expected, query);
    }
    
}
