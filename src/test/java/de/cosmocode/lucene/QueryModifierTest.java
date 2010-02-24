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
        final QueryModifier newModifier = QueryModifier.start().setFuzzyness(0.0).end();
        final double expected = 0.0;
        final double actual = newModifier.getFuzzyness();
        final double delta = 0.0;
        Assert.assertEquals("getFuzzyness", expected, actual, delta);
        Assert.assertEquals("isFuzzyEnabled()", true, newModifier.isFuzzyEnabled());
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
     * Tests {@link QueryModifier.Builder#setFuzzyness(Double)} with a valid fuzzyness of null.
     * Fuzzyness is then disabled.
     */
    @Test
    public void setFuzzynessNull() {
        final QueryModifier newModifier = QueryModifier.start().setFuzzyness(null).end();
        Assert.assertEquals("isFuzzyEnabled()", false, newModifier.isFuzzyEnabled());
    }
    
    /**
     * Tests {@link QueryModifier.Builder#noFuzzyness()}.
     */
    @Test
    public void noFuzzyness() {
        final QueryModifier newModifier = QueryModifier.start().noFuzzyness().end();
        Assert.assertEquals("isFuzzyEnabled()", false, newModifier.isFuzzyEnabled());
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
        final QueryModifier newModifier = QueryModifier.start().setDisjunct(true).end();
        final boolean expected = true;
        final boolean actual = newModifier.isDisjunct();
        Assert.assertEquals(expected, actual);
    }

    /**
     * Tests {@link QueryModifier.Builder#setDisjunct(boolean)} with false.
     */
    @Test
    public void setDisjunctFalse() {
        final QueryModifier newModifier = QueryModifier.start().setDisjunct(false).end();
        final boolean expected = false;
        final boolean actual = newModifier.isDisjunct();
        Assert.assertEquals(expected, actual);
    }

    /**
     * Tests {@link QueryModifier.Builder#disjunct()}.
     */
    @Test
    public void disjunct() {
        final QueryModifier newModifier = QueryModifier.start().disjunct().end();
        final boolean expected = true;
        final boolean actual = newModifier.isDisjunct();
        Assert.assertEquals(expected, actual);
    }

    /**
     * Tests {@link QueryModifier.Builder#conjunct()}.
     */
    @Test
    public void conjunct() {
        final QueryModifier newModifier = QueryModifier.start().conjunct().end();
        final boolean expected = false;
        final boolean actual = newModifier.isDisjunct();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setSplit(boolean)} with true.
     */
    @Test
    public void setSplitTrue() {
        final QueryModifier newModifier = QueryModifier.start().setSplit(true).end();
        final boolean expected = true;
        final boolean actual = newModifier.isSplit();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setSplit(boolean)} with false.
     */
    @Test
    public void setSplitFalse() {
        final QueryModifier newModifier = QueryModifier.start().setSplit(false).end();
        final boolean expected = false;
        final boolean actual = newModifier.isSplit();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#doSplit()}.
     */
    @Test
    public void doSplit() {
        final QueryModifier newModifier = QueryModifier.start().doSplit().end();
        final boolean expected = true;
        final boolean actual = newModifier.isSplit();
        Assert.assertEquals(expected, actual);
    }

    /**
     * Tests {@link QueryModifier.Builder#dontSplit()}.
     */
    @Test
    public void dontSplit() {
        final QueryModifier newModifier = QueryModifier.start().dontSplit().end();
        final boolean expected = false;
        final boolean actual = newModifier.isSplit();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setWildcarded(boolean)} with true.
     */
    @Test
    public void setWildcardedTrue() {
        final QueryModifier newModifier = QueryModifier.start().setWildcarded(true).end();
        final boolean expected = true;
        final boolean actual = newModifier.isWildcarded();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setWildcarded(boolean)} with false.
     */
    @Test
    public void setWildcardedFalse() {
        final QueryModifier newModifier = QueryModifier.start().setWildcarded(false).end();
        final boolean expected = false;
        final boolean actual = newModifier.isWildcarded();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#wildcarded()}.
     */
    @Test
    public void wildcarded() {
        final QueryModifier newModifier = QueryModifier.start().wildcarded().end();
        final boolean expected = true;
        final boolean actual = newModifier.isWildcarded();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#notWildcarded()}.
     */
    @Test
    public void notWildcarded() {
        final QueryModifier newModifier = QueryModifier.start().notWildcarded().end();
        final boolean expected = false;
        final boolean actual = newModifier.isWildcarded();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setTermModifier(TermModifier)} with TermModifier.NONE.
     */
    @Test
    public void setTermModifierNONE() {
        final QueryModifier newModifier = QueryModifier.start().setTermModifier(TermModifier.NONE).end();
        final TermModifier expected = TermModifier.NONE;
        final TermModifier actual = newModifier.getTermModifier();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setTermModifier(TermModifier)} with TermModifier.PROHIBITED.
     */
    @Test
    public void setTermModifierPROHIBITED() {
        final QueryModifier newModifier = QueryModifier.start().setTermModifier(TermModifier.PROHIBITED).end();
        final TermModifier expected = TermModifier.PROHIBITED;
        final TermModifier actual = newModifier.getTermModifier();
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Tests {@link QueryModifier.Builder#setTermModifier(TermModifier)} with TermModifier.REQUIRED.
     */
    @Test
    public void setTermModifierREQUIRED() {
        final QueryModifier newModifier = QueryModifier.start().setTermModifier(TermModifier.REQUIRED).end();
        final TermModifier expected = TermModifier.REQUIRED;
        final TermModifier actual = newModifier.getTermModifier();
        Assert.assertEquals(expected, actual);
    }

}
