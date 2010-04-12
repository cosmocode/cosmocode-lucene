package de.cosmocode.lucene.fragments;

import java.util.Collection;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.cosmocode.lucene.LuceneQuery;
import de.cosmocode.lucene.QueryModifier;

/**
 * <p> Tests the addArgument-method
 * {@link LuceneQuery#addField(String, Collection, QueryModifier)}
 * for {@link LuceneQuery}.
 * </p>
 * 
 * @author Oliver Lorenz
 */
public final class AddFieldCollectionModFragment extends AbstractQueryModifierFragment {
    
    /**
     * Tests {@link LuceneQuery#addField(String, Collection, QueryModifier)}
     * with null and a dummy QueryModifier.
     */
    @Test
    public void collectionNull() {
        final LuceneQuery unit = unit().addField(FIELD1, (Collection<?>) null, QueryModifier.DEFAULT);
        assertEquals("", unit);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, Collection, QueryModifier)}
     * with an empty Collection and a dummy QueryModifier.
     */
    @Test
    public void collectionEmpty() {
        final LuceneQuery unit = unit().addField(FIELD1, Lists.newArrayList(), QueryModifier.DEFAULT);
        assertEquals("", unit);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, Collection, QueryModifier)}
     * with a blank Collection (with blank and empty Strings and null) and a dummy QueryModifier.
     */
    @Test
    public void collectionEmptyValues() {
        final Collection<?> toAdd = Lists.newArrayList("   ", null, "", null, "     ");
        final LuceneQuery unit = unit().addField(FIELD1, toAdd, QueryModifier.DEFAULT);
        assertEquals("", unit);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, Collection, QueryModifier)}
     * with a normal value and the QueryModifier null.
     * Expects a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void modifierNull() {
        unit().addField(FIELD1, Lists.newArrayList(ARG1), null);
    }
    
    
    
    @Override
    protected void applyNormal(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.<Object>newArrayList(ARG1, "  ", ARG2, null, ARG3, "");
        query.addField(FIELD1, toAdd, mod);
    }
    
    @Override
    protected String expectedNormalConjunct() {
        return FIELD1 + ":(" + ARG1 + " AND " + ARG2 + " AND " + ARG3 + ")";
    }
    
    @Override
    protected String expectedNormalDisjunct() {
        return FIELD1 + ":(" + ARG1 + " OR " + ARG2 + " OR " + ARG3 + ")";
    }
    
    
    
    @Override
    protected void applyWildcarded(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.newArrayList(WILDCARD1, "  ", null, "", WILDCARD2, null);
        query.addField(FIELD1, toAdd, mod);
    }
    
    @Override
    protected String expectedWildcardedConjunct() {
        return FIELD1 + ":((" + WILDCARD1 + " " + WILDCARD1 + "*) AND (" + WILDCARD2 + " " + WILDCARD2 + "*))";
    }
    
    @Override
    protected String expectedWildcardedDisjunct() {
        return FIELD1 + ":((" + WILDCARD1 + " " + WILDCARD1 + "*) OR (" + WILDCARD2 + " " + WILDCARD2 + "*))";
    }
    
    
    
    @Override
    protected void applySplit(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.<Object>newArrayList(ARG1 + "  " + ARG3, "", ARG2, null, "  ");
        query.addField(FIELD1, toAdd, mod);
    }
    
    @Override
    protected String expectedSplitConjunct() {
        return FIELD1 + ":((" + ARG1 + "\\ " + ARG3 + " OR (" + ARG1 + " AND " + ARG3 + "))^0.5 AND " + ARG2 + ")";
    }
    
    @Override
    protected String expectedSplitDisjunct() {
        return FIELD1 + ":((" + ARG1 + "\\ " + ARG3 + " OR (" + ARG1 + " OR " + ARG3 + "))^0.5 OR " + ARG2 + ")";
    }
    
    
    
    @Override
    protected void applyFuzzy(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.<Object>newArrayList(FUZZY1, "", null, "   ", FUZZY2);
        query.addField(FIELD1, toAdd, mod);
    }
    
    @Override
    protected String expectedFuzzyConjunct() {
        return FIELD1 + ":(" + FUZZY1 + "~0.7 AND " + FUZZY2 + "~0.7)";
    }
    
    @Override
    protected String expectedFuzzyDisjunct() {
        return FIELD1 + ":(" + FUZZY1 + "~0.7 OR " + FUZZY2 + "~0.7)";
    }
    
    
    
    @Override
    protected void applyWildcardedFuzzy(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.<Object>newArrayList(FUZZY1, WILDCARD1, null, "  ", FUZZY2, WILDCARD2);
        query.addField(FIELD1, toAdd, mod);
    }
    
    @Override
    protected String expectedWildcardedFuzzyConjunct() {
        return 
            FIELD1 + ":(" +
                "(" + FUZZY1 + " " + FUZZY1 + "* " + FUZZY1 + "~0.7) " +
                "AND (" + WILDCARD1 + " " + WILDCARD1 + "* " + WILDCARD1 + "~0.7) " +
                "AND (" + FUZZY2 + " " + FUZZY2 + "* " + FUZZY2 + "~0.7) " +
                "AND (" + WILDCARD2 + " " + WILDCARD2 + "* " + WILDCARD2 + "~0.7)" +
            ")";
    }
    
    @Override
    protected String expectedWildcardedFuzzyDisjunct() {
        return 
            FIELD1 + ":(" +
                "(" + FUZZY1 + " " + FUZZY1 + "* " + FUZZY1 + "~0.7) " +
                "OR (" + WILDCARD1 + " " + WILDCARD1 + "* " + WILDCARD1 + "~0.7) " +
                "OR (" + FUZZY2 + " " + FUZZY2 + "* " + FUZZY2 + "~0.7) " +
                "OR (" + WILDCARD2 + " " + WILDCARD2 + "* " + WILDCARD2 + "~0.7)" +
            ")";
    }
    
    
    
    @Override
    protected void applyFuzzySplit(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.newArrayList(FUZZY1 + "  " + FUZZY2, "", null, FUZZY3, null, "   ");
        query.addField(FIELD1, toAdd, mod);
    }
    
    @Override
    protected String expectedFuzzySplitConjunct() {
        return 
            FIELD1 + ":(" +
                "(" + FUZZY1 + "\\ " + FUZZY2 + "~0.7 OR " +
                "(" + FUZZY1 + "~0.7 AND " + FUZZY2 + "~0.7)" +
                ")^0.5 AND " + FUZZY3 + "~0.7"
            + ")";
    }
    
    @Override
    protected String expectedFuzzySplitDisjunct() {
        return 
            FIELD1 + ":(" +
                "(" + FUZZY1 + "\\ " + FUZZY2 + "~0.7 OR " +
                "(" + FUZZY1 + "~0.7 OR " + FUZZY2 + "~0.7)" +
                ")^0.5 OR " + FUZZY3 + "~0.7"
            + ")";
    }
    
    
    
    @Override
    protected void applyWildcardedSplit(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.newArrayList(WILDCARD1 + "  " + WILDCARD2, "", null, WILDCARD3);
        query.addField(FIELD1, toAdd, mod);
    }
    
    @Override
    protected String expectedWildcardedSplitConjunct() {
        return 
            FIELD1 + ":(" +
                "(" +
                    "(" +
                        "\"" + WILDCARD1 + "  " + WILDCARD2 + "\" " +
                        WILDCARD1 + "\\ \\ " + WILDCARD2 + "* " +
                    ") OR (" +
                        "(" + WILDCARD1 + " " + WILDCARD1 + "*)" +
                        " AND (" + WILDCARD2 + " " + WILDCARD2 + "*)" +
                    ")^0.5" +
                ") AND (" + WILDCARD3 + " " + WILDCARD3 + "*)" +
            ")";
    }
    
    @Override
    protected String expectedWildcardedSplitDisjunct() {
        return
            FIELD1 + ":(" +
                "(" +
                    "(" +
                        "\"" + WILDCARD1 + "  " + WILDCARD2 + "\" " +
                        WILDCARD1 + "\\ \\ " + WILDCARD2 + "* " +
                    ") OR (" +
                        "(" + WILDCARD1 + " " + WILDCARD1 + "*)" +
                        " OR (" + WILDCARD2 + " " + WILDCARD2 + "*)" +
                    ")^0.5" +
                ") OR (" + WILDCARD3 + " " + WILDCARD3 + "*)" +
            ")";
    }
    
    
    
    @Override
    protected void applyWildcardedFuzzySplit(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.newArrayList(
            WILDCARD1 + "  " + FUZZY2, "  ", null, "  ", WILDCARD2, FUZZY3);
        query.addField(FIELD1, toAdd, mod);
    }
    
    @Override
    protected String expectedWildcardedFuzzySplitConjunct() {
        return 
            FIELD1 + ":(" +
                "(" +
                    "(\"" + WILDCARD1 + "  " + FUZZY2 + "\" " +
                        WILDCARD1 + "\\ \\ " + FUZZY2 + "* " + 
                        WILDCARD1 + "\\ \\ " + FUZZY2 + "~0.7" +
                    ") OR (" +
                        "(" + WILDCARD1 + " " + WILDCARD1 + "* " + WILDCARD1 + "~0.7) " +
                        "AND (" + FUZZY2 + " " + FUZZY2 + "* " + FUZZY2 + "~0.7)" +
                    ")^0.5" +
                ") AND (" + WILDCARD2 + " " + WILDCARD2 + "* " + WILDCARD2 + "~0.7) " +
                "AND (" + FUZZY3 + " " + FUZZY3 + "* " + FUZZY3 + "~0.7)"
            + ")";
    }
    
    @Override
    protected String expectedWildcardedFuzzySplitDisjunct() {
        return
            FIELD1 + ":(" +
                "(" +
                    "(\"" + WILDCARD1 + "  " + FUZZY2 + "\" " +
                        WILDCARD1 + "\\ \\ " + FUZZY2 + "* " + 
                        WILDCARD1 + "\\ \\ " + FUZZY2 + "~0.7" +
                    ") OR (" +
                        "(" + WILDCARD1 + " " + WILDCARD1 + "* " + WILDCARD1 + "~0.7) " +
                        "OR (" + FUZZY2 + " " + FUZZY2 + "* " + FUZZY2 + "~0.7)" +
                    ")^0.5" +
                ") OR (" + WILDCARD2 + " " + WILDCARD2 + "* " + WILDCARD2 + "~0.7) " +
                "OR (" + FUZZY3 + " " + FUZZY3 + "* " + FUZZY3 + "~0.7)"
            + ")";
    }

}
