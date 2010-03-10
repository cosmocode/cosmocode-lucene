package de.cosmocode.lucene.fragments;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.cosmocode.lucene.LuceneQuery;
import de.cosmocode.lucene.QueryModifier;

/**
 * <p> Tests the addArgument-method
 * {@link LuceneQuery#addArgument(Collection, QueryModifier)}
 * for {@link LuceneQuery}.
 * </p>
 * 
 * @author Oliver Lorenz
 */
public final class AddArgumentCollectionQueryModifierFragment extends LuceneQueryTestFragment {
    
    private LuceneQuery unit;
    
    private QueryModifier mod;
    

    private void assertEqualsNormal(final String expected) {
        @SuppressWarnings("unchecked")
        final Collection<?> toAdd = Lists.newArrayList(ARG1, "", ARG2, null, ARG3, "");
        unit.addArgument(toAdd, mod);
        assertEquals(expected, unit);
    }
    
    private void assertEqualsWildcarded(final String expected) {
        final Collection<?> toAdd = Lists.newArrayList(WILDCARD1, "", null, WILDCARD2);
        unit.addArgument(toAdd, mod);
        assertEquals(expected, unit);
    }
    
    private void assertEqualsSplit(final String expected) {
        @SuppressWarnings("unchecked")
        final Collection<?> toAdd = Lists.newArrayList(ARG1 + " " + ARG3, "", ARG2, null, "");
        unit.addArgument(toAdd, mod);
        assertEquals(expected, unit);
    }
    
    
    /**
     * Creates a unit.
     */
    @Before
    public void setupUnit() {
        this.unit = unit();
    }
    
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with null and a dummy QueryModifier.
     */
    @Test
    public void collectionNull() {
        mod = QueryModifier.start().doSplit().end();
        unit.addArgument((Collection<?>) null, mod);
        assertEquals("", unit);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a normal value and the QueryModifier null.
     * Expects a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void modifierNull() {
        unit().addArgument(Lists.newArrayList(ARG1), null);
    }
    
    
    /*
     * no split, fuzzy or wildcarded
     */
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().conjunct().end()}.
     */
    @Test
    public void noneConjunct() {
        mod = QueryModifier.start().conjunct().end();
        assertEqualsNormal("(" + ARG1 + " AND " + ARG2 + " AND " + ARG3 + ")");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().disjunct().end()}.
     */
    @Test
    public void noneDisjunct() {
        mod = QueryModifier.start().disjunct().end();
        assertEqualsNormal("(" + ARG1 + " OR " + ARG2 + " OR " + ARG3 + ")");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().required().conjunct().end()}.
     */
    @Test
    public void requiredConjunct() {
        mod = QueryModifier.start().required().conjunct().end();
        assertEqualsNormal("+(" + ARG1 + " AND " + ARG2 + " AND " + ARG3 + ")");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().required().disjunct().end()}.
     */
    @Test
    public void requiredDisjunct() {
        mod = QueryModifier.start().required().disjunct().end();
        assertEqualsNormal("+(" + ARG1 + " OR " + ARG2 + " OR " + ARG3 + ")");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().prohibited().conjunct().end()}.
     */
    @Test
    public void prohibitedConjunct() {
        mod = QueryModifier.start().prohibited().conjunct().end();
        assertEqualsNormal("-(" + ARG1 + " AND " + ARG2 + " AND " + ARG3 + ")");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().prohibited().disjunct().end()}.
     */
    @Test
    public void prohibitedDisjunct() {
        mod = QueryModifier.start().prohibited().disjunct().end();
        assertEqualsNormal("-(" + ARG1 + " OR " + ARG2 + " OR " + ARG3 + ")");
    }
    
    
    /*
     * wildcarded
     */
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().wildcarded().conjunct().end()}.
     */
    @Test
    public void wildcardedNoneConjunct() {
        mod = QueryModifier.start().wildcarded().conjunct().end();
        assertEqualsWildcarded("(" + WILDCARD1 + "*  AND " + WILDCARD2 + "*)");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().wildcarded().disjunct().end()}.
     */
    @Test
    public void wildcardedNoneDisjunct() {
        mod = QueryModifier.start().wildcarded().disjunct().end();
        assertEqualsWildcarded("(" + WILDCARD1 + "*  OR " + WILDCARD2 + "*)");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().wildcarded().required().conjunct().end()}.
     */
    @Test
    public void wildcardedRequiredConjunct() {
        mod = QueryModifier.start().wildcarded().required().conjunct().end();
        assertEqualsWildcarded("+(" + WILDCARD1 + "*  AND " + WILDCARD2 + "*)");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().wildcarded().required().disjunct().end()}.
     */
    @Test
    public void wildcardedRequiredDisjunct() {
        mod = QueryModifier.start().wildcarded().required().disjunct().end();
        assertEqualsWildcarded("+(" + WILDCARD1 + "*  OR " + WILDCARD2 + "*)");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().wildcarded().prohibited().conjunct().end()}.
     */
    @Test
    public void wildcardedProhibitedConjunct() {
        mod = QueryModifier.start().wildcarded().prohibited().conjunct().end();
        assertEqualsWildcarded("-(" + WILDCARD1 + "*  AND " + WILDCARD2 + "*)");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().wildcarded().prohibited().disjunct().end()}.
     */
    @Test
    public void wildcardedProhibitedDisjunct() {
        mod = QueryModifier.start().wildcarded().prohibited().disjunct().end();
        assertEqualsWildcarded("-(" + WILDCARD1 + "*  OR " + WILDCARD2 + "*)");
    }
    
    
    /*
     * split
     */
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().doSplit().conjunct().end()}.
     */
    @Test
    public void splitNoneConjunct() {
        mod = QueryModifier.start().doSplit().conjunct().end();
        assertEqualsSplit("((" + ARG1 + "\\ " + ARG3 + " OR (" + ARG1 + " AND " + ARG3 + ")) AND " + ARG2 + ")");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().doSplit().disjunct().end()}.
     */
    @Test
    public void splitNoneDisjunct() {
        mod = QueryModifier.start().doSplit().disjunct().end();
        assertEqualsSplit("((" + ARG1 + "\\ " + ARG3 + " OR (" + ARG1 + " OR " + ARG3 + ")) OR " + ARG2 + ")");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().doSplit().required().conjunct().end()}.
     */
    @Test
    public void splitRequiredConjunct() {
        mod = QueryModifier.start().doSplit().required().conjunct().end();
        assertEqualsSplit("+((" + ARG1 + "\\ " + ARG3 + " OR (" + ARG1 + " AND " + ARG3 + ")) AND " + ARG2 + ")");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().doSplit().required().disjunct().end()}.
     */
    @Test
    public void splitRequiredDisjunct() {
        mod = QueryModifier.start().doSplit().required().disjunct().end();
        assertEqualsSplit("+((" + ARG1 + "\\ " + ARG3 + " OR (" + ARG1 + " OR " + ARG3 + ")) OR " + ARG2 + ")");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().doSplit().prohibited().conjunct().end()}.
     */
    @Test
    public void splitProhibitedConjunct() {
        mod = QueryModifier.start().doSplit().prohibited().conjunct().end();
        assertEqualsSplit("-((" + ARG1 + "\\ " + ARG3 + " OR (" + ARG1 + " AND " + ARG3 + ")) AND " + ARG2 + ")");
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a list and {@code QueryModifier.start().doSplit().prohibited().disjunct().end()}.
     */
    @Test
    public void splitProhibitedDisjunct() {
        mod = QueryModifier.start().doSplit().prohibited().disjunct().end();
        assertEqualsSplit("-((" + ARG1 + "\\ " + ARG3 + " OR (" + ARG1 + " OR " + ARG3 + ")) OR " + ARG2 + ")");
    }
    
    
    
    // TODO fuzzy
    // TODO fuzzy + wildcarded
    
    // TODO split + fuzzy
    // TODO split + wildcarded
    // TODO split + fuzzy + wildcarded
    
    /**
     * Place-holder test for {@link LuceneQuery#addArgument(Collection, QueryModifier)}.
     */
    @Test
    public void addArgumentModifier() {
        Assert.fail("not yet implemented");
    }

}
