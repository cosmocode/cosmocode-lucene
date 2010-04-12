package de.cosmocode.lucene.fragments;

import org.junit.Test;

import de.cosmocode.lucene.LuceneQuery;
import de.cosmocode.lucene.QueryModifier;

/**
 * <p> Tests the addArgument-method
 * {@link LuceneQuery#addArgument(String, QueryModifier)}.
 * 
 * @author Oliver Lorenz
 */
public class AddArgumentStringModFragment extends AbstractQueryModifierFragment {
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, QueryModifier)}
     * with a null String and a dummy QueryModifier.
     */
    @Test
    public void stringNull() {
        final LuceneQuery unit = unit().addArgument((String) null, QueryModifier.DEFAULT);
        assertEquals("", unit);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, QueryModifier)}
     * with an empty String ("") and a dummy QueryModifier.
     */
    @Test
    public void stringEmpty() {
        final LuceneQuery unit = unit().addArgument("", QueryModifier.DEFAULT);
        assertEquals("", unit);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, QueryModifier)}
     * with a blank String ("   ") and a dummy QueryModifier.
     */
    @Test
    public void stringBlank() {
        final LuceneQuery unit = unit().addArgument("   ", QueryModifier.DEFAULT);
        assertEquals("", unit);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, QueryModifier)}
     * with a valid String and the QueryModifier null.
     * Expects a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void modifierNull() {
        unit().addArgument("test", null);
    }
    

    @Override
    protected void applyNormal(LuceneQuery query, QueryModifier mod) {
        query.addArgument(ARG3, mod);
    }

    @Override
    protected String expectedNormalConjunct() {
        return ARG3;
    }

    @Override
    protected String expectedNormalDisjunct() {
        return ARG3;
    }
    

    @Override
    protected void applyFuzzy(LuceneQuery query, QueryModifier mod) {
        query.addArgument(FUZZY1, mod);
    }

    @Override
    protected String expectedFuzzyConjunct() {
        return FUZZY1 + "~0.7";
    }

    @Override
    protected String expectedFuzzyDisjunct() {
        return FUZZY1 + "~0.7";
    }
    

    @Override
    protected void applySplit(LuceneQuery query, QueryModifier mod) {
        query.addArgument(ARG1 + "   " + ARG3, mod);
    }

    @Override
    protected String expectedSplitConjunct() {
        return ARG1 + " AND " + ARG3;
    }

    @Override
    protected String expectedSplitDisjunct() {
        return ARG1 + " OR " + ARG3;
    }
    

    @Override
    protected void applyWildcarded(LuceneQuery query, QueryModifier mod) {
        query.addArgument(WILDCARD1, mod);
    }

    @Override
    protected String expectedWildcardedConjunct() {
        return WILDCARD1 + " " + WILDCARD1 + "*";
    }

    @Override
    protected String expectedWildcardedDisjunct() {
        return WILDCARD1 + " " + WILDCARD1 + "*";
    }
    

    @Override
    protected void applyFuzzySplit(LuceneQuery query, QueryModifier mod) {
        query.addArgument(FUZZY1 + "   " + FUZZY2, mod);
    }

    @Override
    protected String expectedFuzzySplitConjunct() {
        return "(" + FUZZY1 + "~0.7) AND (" + FUZZY2 + "~0.7)";
    }

    @Override
    protected String expectedFuzzySplitDisjunct() {
        return "(" + FUZZY1 + "~0.7) OR (" + FUZZY2 + "~0.7)";
    }
    

    @Override
    protected void applyWildcardedFuzzy(LuceneQuery query, QueryModifier mod) {
        query.addArgument(FUZZY1, mod);
    }

    @Override
    protected String expectedWildcardedFuzzyConjunct() {
        return FUZZY1 + " " + FUZZY1 + "* " + FUZZY1 + "~0.7";
    }

    @Override
    protected String expectedWildcardedFuzzyDisjunct() {
        return FUZZY1 + " " + FUZZY1 + "* " + FUZZY1 + "~0.7";
    }
    

    @Override
    protected void applyWildcardedSplit(LuceneQuery query, QueryModifier mod) {
        query.addArgument(WILDCARD1 + "   " + WILDCARD2, mod);
    }

    @Override
    protected String expectedWildcardedSplitConjunct() {
        return "(" + WILDCARD1 + "* " + WILDCARD1 + ") AND (" + WILDCARD2 + "* " + WILDCARD2 + ")";
    }

    @Override
    protected String expectedWildcardedSplitDisjunct() {
        return "(" + WILDCARD1 + "* " + WILDCARD1 + ") OR (" + WILDCARD2 + "* " + WILDCARD2 + ")";
    }
    

    @Override
    protected void applyWildcardedFuzzySplit(LuceneQuery query, QueryModifier mod) {
        query.addArgument(FUZZY1 + "   " + WILDCARD2, mod);
    }

    @Override
    protected String expectedWildcardedFuzzySplitConjunct() {
        return
            "(" + FUZZY1 + "\\ \\ \\ " + WILDCARD2 + " " +
                FUZZY1 + "\\ \\ \\ " + WILDCARD2 + "* " +
                FUZZY1 + "\\ \\ \\ " + WILDCARD2 + "~0.7"
            + ") OR (" +
                "(" + FUZZY1 + " " + FUZZY1 + "* " + FUZZY1 + "~0.7) " +
                "AND (" + WILDCARD2 + " " + WILDCARD2 + "* " + WILDCARD2 + "~0.7)" +
            ")^0.5";
    }

    @Override
    protected String expectedWildcardedFuzzySplitDisjunct() {
        return
            "(" + FUZZY1 + "\\ \\ \\ " + WILDCARD2 + " " +
                FUZZY1 + "\\ \\ \\ " + WILDCARD2 + "* " +
                FUZZY1 + "\\ \\ \\ " + WILDCARD2 + "~0.7"
            + ") OR (" +
                "(" + FUZZY1 + " " + FUZZY1 + "* " + FUZZY1 + "~0.7) " +
                "OR (" + WILDCARD2 + " " + WILDCARD2 + "* " + WILDCARD2 + "~0.7)" +
            ")^0.5";
    }

}
