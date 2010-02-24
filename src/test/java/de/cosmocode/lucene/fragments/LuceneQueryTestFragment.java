package de.cosmocode.lucene.fragments;

import junit.framework.Assert;
import de.cosmocode.junit.UnitProvider;
import de.cosmocode.lucene.AbstractLuceneQueryTest;
import de.cosmocode.lucene.LuceneQuery;

abstract class LuceneQueryTestFragment implements UnitProvider<LuceneQuery> {
    
    @Override
    public LuceneQuery unit() {
        return AbstractLuceneQueryTest.getInstance().unit().addArgument("empty");
    }
    
    // TODO sanitize expected, or send actual to a lucene index through the lucene API
    protected void assertEquals(final String expected, final LuceneQuery actual) {
        final String expectedString = unit().getQuery() + expected;
        final String actualString = actual.getQuery();
        Assert.assertEquals(expectedString, actualString);
    }

}
