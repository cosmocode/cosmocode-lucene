package de.cosmocode.lucene;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link QueryModifier}. 
 * 
 * @author Oliver Lorenz
 */
public final class QueryModifierTest {
    
    /**
     * Tests the default constructor, created with QueryModifier.start().end().
     */
    @Test
    public void empty() {
        final QueryModifier empty = QueryModifier.start().end();
        Assert.assertEquals("getTermModifier()", TermModifier.NONE, empty.getTermModifier());
        Assert.assertEquals("isFuzzyEnabled()", false, empty.isFuzzyEnabled());
        Assert.assertEquals("isDisjunct()", false, empty.isDisjunct());
        Assert.assertEquals("isSplit()", false, empty.isSplit());
        Assert.assertEquals("isWildcarded()", false, empty.isWildcarded());
    }
    
    /**
     * Tests {@link QueryModifier#getFuzzyness()} on an empty/default QueryModifier.
     * Expects an IllegalStateException.
     */
    @Test(expected = IllegalStateException.class)
    public void emptyGetFuzzyness() {
        final QueryModifier empty = QueryModifier.start().end();
        empty.getFuzzyness();
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setFuzzyness(Double)} with a valid fuzzyness of 0.7.
     */
    @Test
    public void setFuzzyness() {
        final QueryModifier newModifier = QueryModifier.start().setFuzzyness(0.7).end();
        final double expected = 0.7;
        final double actual = newModifier.getFuzzyness();
        final double delta = 0.001;
        Assert.assertEquals("getFuzzyness", expected, actual, delta);
        Assert.assertEquals("isFuzzyEnabled()", true, newModifier.isFuzzyEnabled());
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setFuzzyness(Double)} with a valid fuzzyness of 0.0.
     */
    @Test
    public void setFuzzynessZero() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, false, false, false, 0.0);
        final QueryModifier actual = QueryModifier.start().setFuzzyness(0.0).end();
        Assert.assertEquals(expected, actual);
    }

    /**
     * Tests {@link QueryModifier.Builder#setFuzzyness(Double)} with an invalid negative fuzzyness.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setFuzzynessNegative() {
        QueryModifier.start().setFuzzyness(-0.1);
    }

    /**
     * Tests {@link QueryModifier.Builder#setFuzzyness(Double)} with an invalid fuzzyness greater than one.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setFuzzynessGreaterOne() {
        QueryModifier.start().setFuzzyness(1.1);
    }

    /**
     * Tests {@link QueryModifier.Builder#setFuzzyness(Double)} with an invalid fuzzyness of one.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setFuzzynessOne() {
        QueryModifier.start().setFuzzyness(1.0);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setFuzzyness(Double)} with a valid fuzzyness of null.
     * Fuzzyness is then disabled.
     */
    @Test
    public void setFuzzynessNull() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, false, false, false, null);
        final QueryModifier actual = QueryModifier.start().setFuzzyness(null).end();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#noFuzzyness()}.
     */
    @Test
    public void noFuzzyness() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, false, false, false, null);
        final QueryModifier actual = QueryModifier.start().noFuzzyness().end();
        Assert.assertEquals(expected, actual);
    }

    /**
     * Tests {@link QueryModifier#getFuzzyness()} on a QueryModifier set with no fuzzyness.
     * Expects an IllegalStateException.
     */
    @Test(expected = IllegalStateException.class)
    public void noFuzzynessGetFuzzyness() {
        final QueryModifier newModifier = QueryModifier.start().noFuzzyness().end();
        newModifier.getFuzzyness();
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setDisjunct(boolean)} with true.
     */
    @Test
    public void setDisjunctTrue() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, false, true, false, null);
        final QueryModifier actual = QueryModifier.start().setDisjunct(true).end();
        Assert.assertEquals(expected, actual);
    }

    /**
     * Tests {@link QueryModifier.Builder#setDisjunct(boolean)} with false.
     */
    @Test
    public void setDisjunctFalse() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, false, false, false, null);
        final QueryModifier actual = QueryModifier.start().setDisjunct(false).end();
        Assert.assertEquals(expected, actual);
    }

    /**
     * Tests {@link QueryModifier.Builder#disjunct()}.
     */
    @Test
    public void disjunct() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, false, true, false, null);
        final QueryModifier actual = QueryModifier.start().disjunct().end();
        Assert.assertEquals(expected, actual);
    }

    /**
     * Tests {@link QueryModifier.Builder#conjunct()}.
     */
    @Test
    public void conjunct() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, false, false, false, null);
        final QueryModifier actual = QueryModifier.start().conjunct().end();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setSplit(boolean)} with true.
     */
    @Test
    public void setSplitTrue() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, true, false, false, null);
        final QueryModifier actual = QueryModifier.start().setSplit(true).end();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setSplit(boolean)} with false.
     */
    @Test
    public void setSplitFalse() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, false, false, false, null);
        final QueryModifier actual = QueryModifier.start().setSplit(false).end();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#doSplit()}.
     */
    @Test
    public void doSplit() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, true, false, false, null);
        final QueryModifier actual = QueryModifier.start().doSplit().end();
        Assert.assertEquals(expected, actual);
    }

    /**
     * Tests {@link QueryModifier.Builder#dontSplit()}.
     */
    @Test
    public void dontSplit() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, false, false, false, null);
        final QueryModifier actual = QueryModifier.start().dontSplit().end();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setWildcarded(boolean)} with true.
     */
    @Test
    public void setWildcardedTrue() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, false, false, true, null);
        final QueryModifier actual = QueryModifier.start().setWildcarded(true).end();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setWildcarded(boolean)} with false.
     */
    @Test
    public void setWildcardedFalse() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, false, false, false, null);
        final QueryModifier actual = QueryModifier.start().setWildcarded(false).end();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#wildcarded()}.
     */
    @Test
    public void wildcarded() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, false, false, true, null);
        final QueryModifier actual = QueryModifier.start().wildcarded().end();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#notWildcarded()}.
     */
    @Test
    public void notWildcarded() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, false, false, false, null);
        final QueryModifier actual = QueryModifier.start().notWildcarded().end();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setTermModifier(TermModifier)} with null.
     * Expects a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void setTermModifierNull() {
        QueryModifier.start().setTermModifier(null);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setTermModifier(TermModifier)} with TermModifier.NONE.
     */
    @Test
    public void setTermModifierNONE() {
        final QueryModifier expected = new QueryModifier(TermModifier.NONE, false, false, false, null);
        final QueryModifier actual = QueryModifier.start().setTermModifier(TermModifier.NONE).end();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setTermModifier(TermModifier)} with TermModifier.PROHIBITED.
     */
    @Test
    public void setTermModifierPROHIBITED() {
        final QueryModifier expected = new QueryModifier(TermModifier.PROHIBITED, false, false, false, null);
        final QueryModifier actual = QueryModifier.start().setTermModifier(TermModifier.PROHIBITED).end();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setTermModifier(TermModifier)} with TermModifier.REQUIRED.
     */
    @Test
    public void setTermModifierREQUIRED() {
        final QueryModifier expected = new QueryModifier(TermModifier.REQUIRED, false, false, false, null);
        final QueryModifier actual = QueryModifier.start().setTermModifier(TermModifier.REQUIRED).end();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#required()}.
     */
    @Test
    public void required() {
        final QueryModifier expected = new QueryModifier(TermModifier.REQUIRED, false, false, false, null);
        final QueryModifier actual = QueryModifier.start().required().end();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#prohibited()}.
     */
    @Test
    public void prohibited() {
        final QueryModifier expected = new QueryModifier(TermModifier.PROHIBITED, false, false, false, null);
        final QueryModifier actual = QueryModifier.start().prohibited().end();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#excluded()}.
     */
    @Test
    public void excluded() {
        final QueryModifier expected = new QueryModifier(TermModifier.PROHIBITED, false, false, false, null);
        final QueryModifier actual = QueryModifier.start().excluded().end();
        Assert.assertEquals(expected, actual);
    }

}
