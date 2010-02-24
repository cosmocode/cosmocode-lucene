package de.cosmocode.lucene.fragments;

import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import de.cosmocode.lucene.LuceneQuery;

public final class AddCollectionFragment extends LuceneQueryTestFragment {
    
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
    
    @Test
    public void addArgumentList() {
        Assert.fail("not yet implemented");
    }

    @Test
    public void addFieldValueNull() {
        final LuceneQuery query = unit();
        query.addField("field", (Collection<?>) null);
        final String expected = "";
        assertEquals(expected, query);
    }

    @Test
    public void addFieldValueEmpty() {
        Assert.fail("not yet implemented");
    }
    
    // TODO add other test methods
    
}
