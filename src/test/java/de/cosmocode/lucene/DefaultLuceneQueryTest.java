package de.cosmocode.lucene;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.cosmocode.junit.UnitProvider;

/**
 * Tests {@link DefaultLuceneQuery}.
 * 
 * @author Oliver Lorenz
 */
@RunWith(Suite.class)
@SuiteClasses(LuceneQueryTest.class)
public final class DefaultLuceneQueryTest implements UnitProvider<LuceneQuery> {
    
    @Override
    public LuceneQuery unit() {
        return new DefaultLuceneQuery();
    }
    
    /**
     * Sets up this class as the current class to test.
     * Unset happens automatically.
     */
    @BeforeClass
    public static void setupClass() {
        LuceneQueryTest.setUnitProvider(DefaultLuceneQueryTest.class);
    }

}
