package de.cosmocode.lucene.fragments;

import java.util.Collection;

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
public final class AddArgumentCollectionQueryModifierFragment extends AbstractQueryModifierFragment {
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with null and a dummy QueryModifier.
     */
    @Test
    public void collectionNull() {
        final LuceneQuery unit = unit().addArgument((Collection<?>) null, QueryModifier.DEFAULT);
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
    
    
    
    @Override
    protected void applyNormal(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.<Object>newArrayList(ARG1, "  ", ARG2, null, ARG3, "");
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedNormalConjunct() {
        return "(" + ARG1 + " AND " + ARG2 + " AND " + ARG3 + ")";
    }
    
    @Override
    protected String expectedNormalDisjunct() {
        return "(" + ARG1 + " OR " + ARG2 + " OR " + ARG3 + ")";
    }
    
    
    
    @Override
    protected void applyWildcarded(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.newArrayList(WILDCARD1, "  ", null, WILDCARD2);
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedWildcardedConjunct() {
        return "(" + WILDCARD1 + "*  AND " + WILDCARD2 + "*)";
    }
    
    @Override
    protected String expectedWildcardedDisjunct() {
        return "(" + WILDCARD1 + "*  OR " + WILDCARD2 + "*)";
    }
    
    
    
    @Override
    protected void applySplit(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.<Object>newArrayList(ARG1 + "  " + ARG3, "", ARG2, null, "");
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedSplitConjunct() {
        return "((" + ARG1 + "\\ " + ARG3 + " OR (" + ARG1 + " AND " + ARG3 + ")) AND " + ARG2 + ")";
    }
    
    @Override
    protected String expectedSplitDisjunct() {
        return "((" + ARG1 + "\\ " + ARG3 + " OR (" + ARG1 + " OR " + ARG3 + ")) OR " + ARG2 + ")";
    }
    
    
    
    @Override
    protected void applyFuzzy(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.<Object>newArrayList(FUZZY1, "", null, "   ", FUZZY2);
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedFuzzyConjunct() {
        return "(" + FUZZY1 + "~0.7 AND " + FUZZY2 + "~0.7)";
    }
    
    @Override
    protected String expectedFuzzyDisjunct() {
        return "(" + FUZZY1 + "~0.7 OR " + FUZZY2 + "~0.7)";
    }
    
    
    
    @Override
    protected void applyWildcardedFuzzy(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.<Object>newArrayList(FUZZY1, WILDCARD1, null, " ", FUZZY2, WILDCARD2);
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedWildcardedFuzzyConjunct() {
        return
            "(" + FUZZY1 + "~0.7 " + FUZZY1 + "*) AND (" + WILDCARD1 + "~0.7 " + WILDCARD1 + "*) " +
            "AND (" + FUZZY2 + "~0.7 " + FUZZY2 + "*) AND (" + WILDCARD2 + "~0.7 " + WILDCARD2 + "*)";
    }
    
    @Override
    protected String expectedWildcardedFuzzyDisjunct() {
        return
            "(" + FUZZY1 + "~0.7 " + FUZZY1 + "*) OR (" + WILDCARD1 + "~0.7 " + WILDCARD1 + "*) " +
            "OR (" + FUZZY2 + "~0.7 " + FUZZY2 + "*) OR (" + WILDCARD2 + "~0.7 " + WILDCARD2 + "*)";
    }
    
    
    
    @Override
    protected void applyFuzzySplit(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.<Object>newArrayList(FUZZY1 + "  " + FUZZY2, "", null, FUZZY3, null, " ");
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedFuzzySplitConjunct() {
        return
            "(" + FUZZY1 + "\\ " + FUZZY2 + "~0.7 OR " +
            "(" + FUZZY1 + "~0.7 AND " + FUZZY2 + "~0.7)" +
            ")^0.5 AND " + FUZZY3 + "~0.7";
    }
    
    @Override
    protected String expectedFuzzySplitDisjunct() {
        return
            "(" + FUZZY1 + "\\ " + FUZZY2 + "~0.7 OR " +
            "(" + FUZZY1 + "~0.7 OR " + FUZZY2 + "~0.7)" +
            ")^0.5 OR " + FUZZY3 + "~0.7";
    }
    
    
    
    @Override
    protected void applyWildcardedSplit(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.newArrayList(WILDCARD1 + "  " + WILDCARD2, "", null, WILDCARD3);
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedWildcardedSplitConjunct() {
        return
            "(" + WILDCARD1 + "\\ " + WILDCARD2 + "* OR " +
            "(" + WILDCARD1 + "* AND " + WILDCARD2 + "*))^0.5 " +
            "AND " + WILDCARD3 + "*";
    }
    
    @Override
    protected String expectedWildcardedSplitDisjunct() {
        return
            "(" + WILDCARD1 + "\\ " + WILDCARD2 + "* OR " +
            "(" + WILDCARD1 + "* OR " + WILDCARD2 + "*))^0.5 " +
            "OR " + WILDCARD3 + "*";
    }
    
    
    
    @Override
    protected void applyWildcardedFuzzySplit(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.newArrayList(
            WILDCARD1 + "  " + FUZZY2, "  ", null, "  ", WILDCARD2, FUZZY3);
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedWildcardedFuzzySplitConjunct() {
        return
            "((" + WILDCARD1 + "\\ " + FUZZY2 + "* " + WILDCARD1 + "\\ " + FUZZY2 + "~0.7) OR " +
            "((" + WILDCARD1 + "* " + WILDCARD1 + "~0.7) AND (" + FUZZY2 + "* " + FUZZY2 + "~0.7)))^0.5 " +
            "AND (" + WILDCARD2 + "~0.7 " + WILDCARD2 + "*) " +
            "AND (" + FUZZY3 + "~0.7 " + FUZZY3 + "*)";
    }
    
    @Override
    protected String expectedWildcardedFuzzySplitDisjunct() {
        return
            "((" + WILDCARD1 + "\\ " + FUZZY2 + "* " + WILDCARD1 + "\\ " + FUZZY2 + "~0.7) OR " +
            "((" + WILDCARD1 + "* " + WILDCARD1 + "~0.7) OR (" + FUZZY2 + "* " + FUZZY2 + "~0.7)))^0.5 " +
            "OR (" + WILDCARD2 + "~0.7 " + WILDCARD2 + "*) " +
            "OR (" + FUZZY3 + "~0.7 " + FUZZY3 + "*)";
    }

}
