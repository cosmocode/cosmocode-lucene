package de.cosmocode.lucene;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.cosmocode.junit.UnitProvider;

/**
 * Tests {@link LuceneQueryBuilder}.
 *
 * @author Oliver Lorenz
 */
@RunWith(Suite.class)
@SuiteClasses(LuceneQueryTest.class)
public final class LuceneQueryBuilderTest implements UnitProvider<LuceneQuery> {
    
    @Override
    public LuceneQuery unit() {
        return new LuceneQueryBuilder();
    }
    
    /**
     * Sets up this class as the current class to test.
     * Unset happens automatically.
     */
    @BeforeClass
    public static void setupClass() {
        LuceneQueryTest.setUnitProvider(LuceneQueryBuilderTest.class);
    }

}
