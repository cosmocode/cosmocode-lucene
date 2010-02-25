package de.cosmocode.lucene.fragments;

import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import de.cosmocode.lucene.LuceneQuery;
import de.cosmocode.lucene.QueryModifier;

/**
 * <p> Tests all Collection related methods for {@link LuceneQuery}. 
 * </p>
 * <p> The test methods are:
 * </p>
 * <ul>
 *   <li> {@link LuceneQuery#addArgument(Collection)} </li>
 *   <li> {@link LuceneQuery#addArgument(Collection, boolean)} </li>
 *   <li> {@link LuceneQuery#addArgument(Collection, QueryModifier)} </li>
 *   <li> {@link LuceneQuery#addField(String, Collection)} </li>
 *   <li> {@link LuceneQuery#addField(String, boolean, Collection, boolean)} </li>
 *   <li> {@link LuceneQuery#addField(String, Collection, QueryModifier)} </li>
 * </ul>
 * @author Oliver Lorenz
 */
public final class AddCollectionFragment extends LuceneQueryTestFragment {
    
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
    
    @Test
    public void addArgumentModifier() {
        Assert.fail("not yet implemented");
    }
    
    // TODO tests for addArgument(Collection, QueryModifier)

    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with a valid key and a null value.
     */
    @Test
    public void addFieldNullValue() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, (Collection<?>) null);
        final String expected = "";
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with a null key and a valid value.
     */
    @Test
    public void addFieldNullKey() {
        final LuceneQuery query = unit();
        query.addField(null, ImmutableList.of(ARG1));
        final String expected = "";
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with null key and null value.
     */
    @Test
    public void addFieldNulls() {
        final LuceneQuery query = unit();
        query.addField(null, (Collection<?>) null);
        final String expected = "";
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with a valid key and an empty collection as value.
     */
    @Test
    public void addFieldEmptyValue() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, Collections.emptySet());
        final String expected = "";
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with a valid key and a list with one element as value.
     */
    @Test
    public void addFieldOneElement() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, ImmutableList.of(ARG1));
        final String expected = "+" + FIELD1 + ":" + ARG1;
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * on another field and a list with one element as value.
     */
    @Test
    public void addFieldField2OneElement() {
        final LuceneQuery query = unit();
        query.addField(FIELD2, ImmutableList.of(ARG1));
        final String expected = "+" + FIELD2 + ":" + ARG1;
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with a valid key and a list with two elements as value.
     */
    @Test
    public void addFieldTwoElements() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, ImmutableList.of(ARG1, ARG2));
        final String expected = "+" + FIELD1 + ":" + "(+" + ARG1 + " +" + ARG2 + ")";
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with a valid key and a list that contains nulls and empty Strings.
     */
    @Test
    public void addFieldContainsInvalid() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, Lists.newArrayList(ARG1, "", null, ARG3, null, ""));
        final String expected = "+" + FIELD1 + ":" + "(+" + ARG1 + " +" + ARG3 + ")";
        assertEquals(expected, query);
    }

    @Test
    public void addFieldModifier() {
        Assert.fail("not yet implemented");
    }
    
    // TODO add other test methods
    
}
