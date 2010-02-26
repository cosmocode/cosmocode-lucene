package de.cosmocode.lucene;

import org.junit.BeforeClass;

/**
 * Tests {@link DefaultLuceneQuery} as a {@link LuceneQuery}.
 * 
 * @author Oliver Lorenz
 */
public final class DefaultLuceneQueryTest extends AbstractLuceneQueryTest {
    
    @Override
    public LuceneQuery unit() {
        return new DefaultLuceneQuery();
    }
    
    /**
     * Sets up the instance.
     * Unset happens automatically.
     */
    @BeforeClass
    public static void setInstance() {
        setUnitProvider(DefaultLuceneQueryTest.class);
    }

}
