package de.cosmocode.lucene;

import org.junit.BeforeClass;

public final class DefaultLuceneQueryTest extends AbstractLuceneQueryTest {
    
    @Override
    public LuceneQuery unit() {
        return new DefaultLuceneQuery();
    }
    
    @BeforeClass
    public static void setClass() {
        setInstance(DefaultLuceneQueryTest.class);
    }

}
