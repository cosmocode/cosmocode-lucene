package de.cosmocode.lucene.fragments;

import org.junit.Before;
import org.junit.Test;

import de.cosmocode.lucene.LuceneQuery;
import de.cosmocode.lucene.QueryModifier;

/**
 * <p> This is an abstract Test that tests every possible state of a QueryModifier
 * and applies it to a LuceneQuery. The way it is applied must be specified
 * by the implementing class.
 * </p>
 * 
 * @author Oliver Lorenz
 */
public abstract class AbstractQueryModifierFragment extends LuceneQueryTestFragment {
    
    private LuceneQuery unit;
    
    /**
     * Apply a normal value (with QueryModifier.DEFAULT) to the query.
     * @param query the query to apply
     * @param mod the modifier to apply
     */
    protected abstract void applyNormal(final LuceneQuery query, final QueryModifier mod);
    
    /**
     * Apply a wildcarded value (with QueryModifier set to wildcarded) to the query.
     * @param query the query to apply
     * @param mod the modifier to apply
     */
    protected abstract void applyWildcarded(final LuceneQuery query, final QueryModifier mod);
    
    /**
     * Apply a split value (with QueryModifier set to split) to the query.
     * @param query the query to apply
     * @param mod the modifier to apply
     */
    protected abstract void applySplit(final LuceneQuery query, final QueryModifier mod);
    
    /**
     * Apply a fuzzy value (with QueryModifier set to fuzzyness 0.7) to the query.
     * @param query the query to apply
     * @param mod the modifier to apply
     */
    protected abstract void applyFuzzy(final LuceneQuery query, final QueryModifier mod);
    
    /**
     * Apply a wildcarded and fuzzy value
     * (with QueryModifier set to fuzzyness 0.7 and wildcarded) to the query.
     * @param query the query to apply
     * @param mod the modifier to apply
     */
    protected abstract void applyWildcardedFuzzy(final LuceneQuery query, final QueryModifier mod);
    
    /**
     * Apply a wildcarded value splitted (with QueryModifier set to wildcarded and split) to the query.
     * @param query the query to apply
     * @param mod the modifier to apply
     */
    protected abstract void applyWildcardedSplit(final LuceneQuery query, final QueryModifier mod);
    
    /**
     * Apply a fuzzy value splitted
     * (with QueryModifier set to fuzzyness 0.7 and split) to the query.
     * @param query the query to apply
     * @param mod the modifier to apply
     */
    protected abstract void applyFuzzySplit(final LuceneQuery query, final QueryModifier mod);
    
    /**
     * Apply a fuzzy and wildcarded value splitted
     * (with QueryModifier set to fuzzyness 0.7 and wildcarded and split) to the query.
     * @param query the query to apply
     * @param mod the modifier to apply
     */
    protected abstract void applyWildcardedFuzzySplit(final LuceneQuery query, final QueryModifier mod);
    
    
    
    /** The expected query for {@code QueryModifier.start().conjunct().end()}.
     * @return expected query for normal conjunct */
    protected abstract String expectedNormalConjunct();
    
    /** The expected query for {@code QueryModifier.start().disjunct().end()}.
     * @return expected query for normal disjunct */
    protected abstract String expectedNormalDisjunct();

    /** The expected query for {@code QueryModifier.start().wildcarded().conjunct().end()}.
     * @return expected query for wildcarded conjunct */
    protected abstract String expectedWildcardedConjunct();

    /** The expected query for {@code QueryModifier.start().wildcarded().disjunct().end()}.
     * @return expected query for wildcarded disjunct */
    protected abstract String expectedWildcardedDisjunct();

    /** The expected query for {@code QueryModifier.start().doSplit().conjunct().end()}.
     * @return expected query for split conjunct */
    protected abstract String expectedSplitConjunct();

    /** The expected query for {@code QueryModifier.start().doSplit().disjunct().end()}.
     * @return expected query for split disjunct */
    protected abstract String expectedSplitDisjunct();

    /** The expected query for {@code QueryModifier.start().setFuzzyness(0.7).conjunct().end()}.
     * @return expected query for fuzzyness 0.7 conjunct */
    protected abstract String expectedFuzzyConjunct();

    /** The expected query for {@code QueryModifier.start().setFuzzyness(0.7).disjunct().end()}.
     * @return expected query for fuzzyness 0.7 disjunct */
    protected abstract String expectedFuzzyDisjunct();
    
    /** The expected query for {@code QueryModifier.start().setFuzzyness(0.7).wildcarded().conjunct().end()}.
     * @return expected query for fuzzyness 0.7, wildcarded, conjunct */
    protected abstract String expectedWildcardedFuzzyConjunct();
    
    /** The expected query for {@code QueryModifier.start().setFuzzyness(0.7).wildcarded().disjunct().end()}.
     * @return expected query for fuzzyness 0.7, wildcarded, disjunct */
    protected abstract String expectedWildcardedFuzzyDisjunct();
    
    /** The expected query for {@code QueryModifier.start().wildcarded().split().conjunct().end()}.
     * @return expected query for wildcarded, split, conjunct */
    protected abstract String expectedWildcardedSplitConjunct();

    /** The expected query for {@code QueryModifier.start().wildcarded().split().disjunct().end()}.
     * @return expected query for wildcarded, split, disjunct */
    protected abstract String expectedWildcardedSplitDisjunct();
    
    /** The expected query for {@code QueryModifier.start().setFuzzyness(0.7).doSplit().conjunct().end()}.
     * @return expected query for fuzzyness 0.7, split, conjunct */
    protected abstract String expectedFuzzySplitConjunct();
    
    /** The expected query for {@code QueryModifier.start().setFuzzyness(0.7).doSplit().disjunct().end()}.
     * @return expected query for fuzzyness 0.7, split, disjunct */
    protected abstract String expectedFuzzySplitDisjunct();
    
    /** The expected query for
     * {@code QueryModifier.start().setFuzzyness(0.7).doSplit().wildcarded().conjunct().end()}.
     * @return expected query for fuzzyness 0.7, wildcarded, split, conjunct */
    protected abstract String expectedWildcardedFuzzySplitConjunct();
    
    /** The expected query for
     * {@code QueryModifier.start().setFuzzyness(0.7).doSplit().wildcarded().disjunct().end()}.
     * @return expected query for fuzzyness 0.7, wildcarded, split, disjunct */
    protected abstract String expectedWildcardedFuzzySplitDisjunct();
    
    
    /**
     * Creates a unit.
     */
    @Before
    public void setupUnit() {
        this.unit = unit();
    }
    
    
    /*
     * no split, fuzzy or wildcarded
     */
    
    /**
     * Tests {@code QueryModifier.start().conjunct().end()}.
     */
    @Test
    public void noneConjunct() {
        applyNormal(unit, QueryModifier.start().conjunct().end());
        assertEquals("(" + expectedNormalConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().disjunct().end()}.
     */
    @Test
    public void noneDisjunct() {
        applyNormal(unit, QueryModifier.start().disjunct().end());
        assertEquals("(" + expectedNormalDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().required().conjunct().end()}.
     */
    @Test
    public void requiredConjunct() {
        applyNormal(unit, QueryModifier.start().required().conjunct().end());
        assertEquals("+(" + expectedNormalConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().required().disjunct().end()}.
     */
    @Test
    public void requiredDisjunct() {
        applyNormal(unit, QueryModifier.start().required().disjunct().end());
        assertEquals("+(" + expectedNormalDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().prohibited().conjunct().end()}.
     */
    @Test
    public void prohibitedConjunct() {
        applyNormal(unit, QueryModifier.start().prohibited().conjunct().end());
        assertEquals("-(" + expectedNormalConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().prohibited().disjunct().end()}.
     */
    @Test
    public void prohibitedDisjunct() {
        applyNormal(unit, QueryModifier.start().prohibited().disjunct().end());
        assertEquals("-(" + expectedNormalDisjunct() + ")", unit);
    }
    
    
    /*
     * wildcarded
     */
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().conjunct().end()}.
     */
    @Test
    public void wildcardedNoneConjunct() {
        applyWildcarded(unit, QueryModifier.start().wildcarded().conjunct().end());
        assertEquals("(" + expectedWildcardedConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().disjunct().end()}.
     */
    @Test
    public void wildcardedNoneDisjunct() {
        applyWildcarded(unit, QueryModifier.start().wildcarded().disjunct().end());
        assertEquals("(" + expectedWildcardedDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().required().conjunct().end()}.
     */
    @Test
    public void wildcardedRequiredConjunct() {
        applyWildcarded(unit, QueryModifier.start().wildcarded().required().conjunct().end());
        assertEquals("+(" + expectedWildcardedConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().required().disjunct().end()}.
     */
    @Test
    public void wildcardedRequiredDisjunct() {
        applyWildcarded(unit, QueryModifier.start().wildcarded().required().disjunct().end());
        assertEquals("+(" + expectedWildcardedDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().prohibited().conjunct().end()}.
     */
    @Test
    public void wildcardedProhibitedConjunct() {
        applyWildcarded(unit, QueryModifier.start().wildcarded().prohibited().conjunct().end());
        assertEquals("-(" + expectedWildcardedConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().prohibited().disjunct().end()}.
     */
    @Test
    public void wildcardedProhibitedDisjunct() {
        applyWildcarded(unit, QueryModifier.start().wildcarded().prohibited().disjunct().end());
        assertEquals("-(" + expectedWildcardedDisjunct() + ")", unit);
    }
    
    
    /*
     * split
     */
    
    /**
     * Tests {@code QueryModifier.start().doSplit().conjunct().end()}.
     */
    @Test
    public void splitNoneConjunct() {
        applySplit(unit, QueryModifier.start().doSplit().conjunct().end());
        assertEquals("(" + expectedSplitConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().doSplit().disjunct().end()}.
     */
    @Test
    public void splitNoneDisjunct() {
        applySplit(unit, QueryModifier.start().doSplit().disjunct().end());
        assertEquals("(" + expectedSplitDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().doSplit().required().conjunct().end()}.
     */
    @Test
    public void splitRequiredConjunct() {
        applySplit(unit, QueryModifier.start().doSplit().required().conjunct().end());
        assertEquals("+(" + expectedSplitConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().doSplit().required().disjunct().end()}.
     */
    @Test
    public void splitRequiredDisjunct() {
        applySplit(unit, QueryModifier.start().doSplit().required().disjunct().end());
        assertEquals("+(" + expectedSplitDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().doSplit().prohibited().conjunct().end()}.
     */
    @Test
    public void splitProhibitedConjunct() {
        applySplit(unit, QueryModifier.start().doSplit().prohibited().conjunct().end());
        assertEquals("-(" + expectedSplitConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().doSplit().prohibited().disjunct().end()}.
     */
    @Test
    public void splitProhibitedDisjunct() {
        applySplit(unit, QueryModifier.start().doSplit().prohibited().disjunct().end());
        assertEquals("-(" + expectedSplitDisjunct() + ")", unit);
    }
    
    
    /*
     * fuzzyness (0.7)
     */
    
    /**
     * Tests {@code QueryModifier.start().setFuzzyness(0.7).conjunct().end()}.
     */
    @Test
    public void fuzzyNoneConjunct() {
        applyFuzzy(unit, QueryModifier.start().setFuzzyness(0.7).conjunct().end());
        assertEquals("(" + expectedFuzzyConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().setFuzzyness(0.7).disjunct().end()}.
     */
    @Test
    public void fuzzyNoneDisjunct() {
        applyFuzzy(unit, QueryModifier.start().setFuzzyness(0.7).disjunct().end());
        assertEquals("(" + expectedFuzzyDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().setFuzzyness(0.7).required().conjunct().end()}.
     */
    @Test
    public void fuzzyRequiredConjunct() {
        applyFuzzy(unit, QueryModifier.start().setFuzzyness(0.7).required().conjunct().end());
        assertEquals("+(" + expectedFuzzyConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().setFuzzyness(0.7).required().disjunct().end()}.
     */
    @Test
    public void fuzzyRequiredDisjunct() {
        applyFuzzy(unit, QueryModifier.start().setFuzzyness(0.7).required().disjunct().end());
        assertEquals("+(" + expectedFuzzyDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().setFuzzyness(0.7).prohibited().conjunct().end()}.
     */
    @Test
    public void fuzzyProhibitedConjunct() {
        applyFuzzy(unit, QueryModifier.start().setFuzzyness(0.7).prohibited().conjunct().end());
        assertEquals("-(" + expectedFuzzyConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().setFuzzyness(0.7).prohibited().disjunct().end()}.
     */
    @Test
    public void fuzzyProhibitedDisjunct() {
        applyFuzzy(unit, QueryModifier.start().setFuzzyness(0.7).prohibited().disjunct().end());
        assertEquals("-(" + expectedFuzzyDisjunct() + ")", unit);
    }
    
    
    /*
     * fuzzyness (0.7) and wildcarded
     */
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().setFuzzyness(0.7).conjunct().end()}.
     */
    @Test
    public void wildcardedFuzzyNoneConjunct() {
        applyWildcardedFuzzy(unit,
            QueryModifier.start().wildcarded().setFuzzyness(0.7).conjunct().end());
        assertEquals("(" + expectedWildcardedFuzzyConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().setFuzzyness(0.7).disjunct().end()}.
     */
    @Test
    public void wildcardedFuzzyNoneDisjunct() {
        applyWildcardedFuzzy(unit,
            QueryModifier.start().wildcarded().setFuzzyness(0.7).disjunct().end());
        assertEquals("(" + expectedWildcardedFuzzyDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().setFuzzyness(0.7).required().conjunct().end()}.
     */
    @Test
    public void wildcardedFuzzyRequiredConjunct() {
        applyWildcardedFuzzy(unit,
            QueryModifier.start().wildcarded().setFuzzyness(0.7).required().conjunct().end());
        assertEquals("+(" + expectedWildcardedFuzzyConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().setFuzzyness(0.7).required().disjunct().end()}.
     */
    @Test
    public void wildcardedFuzzyRequiredDisjunct() {
        applyWildcardedFuzzy(unit,
            QueryModifier.start().wildcarded().setFuzzyness(0.7).required().disjunct().end());
        assertEquals("+(" + expectedWildcardedFuzzyDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().setFuzzyness(0.7).prohibited().conjunct().end()}.
     */
    @Test
    public void wildcardedFuzzyProhibitedConjunct() {
        applyWildcardedFuzzy(unit,
            QueryModifier.start().wildcarded().setFuzzyness(0.7).prohibited().conjunct().end());
        assertEquals("-(" + expectedWildcardedFuzzyConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().setFuzzyness(0.7).prohibited().disjunct().end()}.
     */
    @Test
    public void wildcardedFuzzyProhibitedDisjunct() {
        applyWildcardedFuzzy(unit,
            QueryModifier.start().wildcarded().setFuzzyness(0.7).prohibited().disjunct().end());
        assertEquals("-(" + expectedWildcardedFuzzyDisjunct() + ")", unit);
    }
    
    
    /*
     * fuzzyness (0.7) and split
     */
    
    /**
     * Tests {@code QueryModifier.start().doSplit().setFuzzyness(0.7).conjunct().end()}.
     */
    @Test
    public void fuzzySplitNoneConjunct() {
        applyFuzzySplit(unit, QueryModifier.start().doSplit().setFuzzyness(0.7).conjunct().end());
        assertEquals("(" + expectedFuzzySplitConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().doSplit().setFuzzyness(0.7).disjunct().end()}.
     */
    @Test
    public void fuzzySplitNoneDisjunct() {
        applyFuzzySplit(unit, QueryModifier.start().doSplit().setFuzzyness(0.7).disjunct().end());
        assertEquals("(" + expectedFuzzySplitDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().doSplit().setFuzzyness(0.7).required().conjunct().end()}.
     */
    @Test
    public void fuzzySplitRequiredConjunct() {
        applyFuzzySplit(unit, QueryModifier.start().doSplit().setFuzzyness(0.7).required().conjunct().end());
        assertEquals("+(" + expectedFuzzySplitConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().doSplit().setFuzzyness(0.7).required().disjunct().end()}.
     */
    @Test
    public void fuzzySplitRequiredDisjunct() {
        applyFuzzySplit(unit, QueryModifier.start().doSplit().setFuzzyness(0.7).required().disjunct().end());
        assertEquals("+(" + expectedFuzzySplitDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().doSplit().setFuzzyness(0.7).prohibited().conjunct().end()}.
     */
    @Test
    public void fuzzySplitProhibitedConjunct() {
        applyFuzzySplit(unit, QueryModifier.start().doSplit().setFuzzyness(0.7).prohibited().conjunct().end());
        assertEquals("-(" + expectedFuzzySplitConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().doSplit().setFuzzyness(0.7).prohibited().disjunct().end()}.
     */
    @Test
    public void fuzzySplitProhibitedDisjunct() {
        applyFuzzySplit(unit, QueryModifier.start().doSplit().setFuzzyness(0.7).prohibited().disjunct().end());
        assertEquals("-(" + expectedFuzzySplitDisjunct() + ")", unit);
    }
    
    
    /*
     * split and wildcarded
     */
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().doSplit().conjunct().end()}.
     */
    @Test
    public void wildcardedSplitNoneConjunct() {
        applyWildcardedSplit(unit,
            QueryModifier.start().wildcarded().doSplit().conjunct().end());
        assertEquals("(" + expectedWildcardedSplitConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().doSplit().disjunct().end()}.
     */
    @Test
    public void wildcardedSplitNoneDisjunct() {
        applyWildcardedSplit(unit,
            QueryModifier.start().wildcarded().doSplit().disjunct().end());
        assertEquals("(" + expectedWildcardedSplitDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().doSplit().required().conjunct().end()}.
     */
    @Test
    public void wildcardedSplitRequiredConjunct() {
        applyWildcardedSplit(unit,
            QueryModifier.start().wildcarded().doSplit().required().conjunct().end());
        assertEquals("+(" + expectedWildcardedSplitConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().doSplit().required().disjunct().end()}.
     */
    @Test
    public void wildcardedSplitRequiredDisjunct() {
        applyWildcardedSplit(unit,
            QueryModifier.start().wildcarded().doSplit().required().disjunct().end());
        assertEquals("+(" + expectedWildcardedSplitDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().doSplit().prohibited().conjunct().end()}.
     */
    @Test
    public void wildcardedSplitProhibitedConjunct() {
        applyWildcardedSplit(unit,
            QueryModifier.start().wildcarded().doSplit().prohibited().conjunct().end());
        assertEquals("-(" + expectedWildcardedSplitConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().doSplit().prohibited().disjunct().end()}.
     */
    @Test
    public void wildcardedSplitProhibitedDisjunct() {
        applyWildcardedSplit(unit,
            QueryModifier.start().wildcarded().doSplit().prohibited().disjunct().end());
        assertEquals("-(" + expectedWildcardedSplitDisjunct() + ")", unit);
    }
    
    
    /*
     * fuzzyness (0.7) and split and wildcarded
     */
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().setFuzzyness(0.7).doSplit().conjunct().end()}.
     */
    @Test
    public void wildcardedFuzzySplitNoneConjunct() {
        applyWildcardedFuzzySplit(unit,
            QueryModifier.start().wildcarded().setFuzzyness(0.7).doSplit().conjunct().end());
        assertEquals("(" + expectedWildcardedFuzzySplitConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().setFuzzyness(0.7).doSplit().disjunct().end()}.
     */
    @Test
    public void wildcardedFuzzySplitNoneDisjunct() {
        applyWildcardedFuzzySplit(unit,
            QueryModifier.start().wildcarded().setFuzzyness(0.7).doSplit().disjunct().end());
        assertEquals("(" + expectedWildcardedFuzzySplitDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().setFuzzyness(0.7).doSplit().required().conjunct().end()}.
     */
    @Test
    public void wildcardedFuzzySplitRequiredConjunct() {
        applyWildcardedFuzzySplit(unit,
            QueryModifier.start().wildcarded().setFuzzyness(0.7).doSplit().required().conjunct().end());
        assertEquals("+(" + expectedWildcardedFuzzySplitConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().setFuzzyness(0.7).doSplit().required().disjunct().end()}.
     */
    @Test
    public void wildcardedFuzzySplitRequiredDisjunct() {
        applyWildcardedFuzzySplit(unit,
            QueryModifier.start().wildcarded().setFuzzyness(0.7).doSplit().required().disjunct().end());
        assertEquals("+(" + expectedWildcardedFuzzySplitDisjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().setFuzzyness(0.7).doSplit().prohibited().conjunct().end()}.
     */
    @Test
    public void wildcardedFuzzySplitProhibitedConjunct() {
        applyWildcardedFuzzySplit(unit,
            QueryModifier.start().wildcarded().setFuzzyness(0.7).doSplit().prohibited().conjunct().end());
        assertEquals("-(" + expectedWildcardedFuzzySplitConjunct() + ")", unit);
    }
    
    /**
     * Tests {@code QueryModifier.start().wildcarded().setFuzzyness(0.7).doSplit().prohibited().disjunct().end()}.
     */
    @Test
    public void wildcardedFuzzySplitProhibitedDisjunct() {
        applyWildcardedFuzzySplit(unit,
            QueryModifier.start().wildcarded().setFuzzyness(0.7).doSplit().prohibited().disjunct().end());
        assertEquals("-(" + expectedWildcardedFuzzySplitDisjunct() + ")", unit);
    }

}
