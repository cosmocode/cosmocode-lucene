package de.cosmocode.lucene;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.google.common.base.Preconditions;

import de.cosmocode.junit.UnitProvider;
import de.cosmocode.lucene.fragments.AddArgumentArrayFragment;
import de.cosmocode.lucene.fragments.AddArgumentArrayModFragment;
import de.cosmocode.lucene.fragments.AddArgumentCollectionFragment;
import de.cosmocode.lucene.fragments.AddArgumentCollectionModFragment;
import de.cosmocode.lucene.fragments.AddArgumentStringFragment;
import de.cosmocode.lucene.fragments.AddArgumentStringModFragment;
import de.cosmocode.lucene.fragments.AddFieldArrayFragment;
import de.cosmocode.lucene.fragments.AddFieldArrayModFragment;
import de.cosmocode.lucene.fragments.AddFieldCollectionFragment;
import de.cosmocode.lucene.fragments.AddFieldCollectionModFragment;
import de.cosmocode.lucene.fragments.AddFieldStringFragment;
import de.cosmocode.lucene.fragments.AddFieldStringModFragment;

/**
 * <p> Generic Test for {@link LuceneQuery}.
 * This is a final class that executes a test suite and provides only static methods.
 * </p>
 * <p> This test has a dependency to the maven artifact: org.apache.lucene:lucene-core:jar:3.0.0 
 * </p>
 * <p> A test for LuceneQuery must have this class in a TestSuite
 * and add a static method with {@code @}BeforeClass to set the UnitProvider of this class.
 * </p>
 * <p> Example:
 * </p>
 * <pre>
 * {@code @}RunWith(Suite.class)
 * {@code @}SuiteClasses(LuceneQueryTest.class)
 *  public final class MyLuceneQueryTest implements {@code UnitProvider<LuceneQuery>} {
 *     {@code @}Override
 *      public LuceneQuery unit() {
 *          return new MyLuceneQuery();
 *      }
 *  
 *     {@code @}BeforeClass
 *      public static void setupClass() {
 *          LuceneQueryTest.setUnitProvider(MyLuceneQueryTest.class);
 *      }
 *  }
 * </pre>
 * @author Oliver Lorenz
 */
@RunWith(Suite.class)
@SuiteClasses({
    AddArgumentCollectionFragment.class,
    AddArgumentCollectionModFragment.class,
    AddFieldCollectionFragment.class,
    AddFieldCollectionModFragment.class,
    AddArgumentArrayFragment.class,
    AddArgumentArrayModFragment.class,
    AddFieldArrayFragment.class,
    AddFieldArrayModFragment.class,
    AddArgumentStringFragment.class,
    AddArgumentStringModFragment.class,
    AddFieldStringFragment.class,
    AddFieldStringModFragment.class
})
public abstract class LuceneQueryTest {
    
    private static final String ERR_NO_PROVIDER = 
        "UnitProvider class not yet set, " +
        "set it with LuceneQueryTest.setUnitProvider(MyProvider.class) " +
        "in an @BeforeClass annotated static method";
    
    private static UnitProvider<? extends LuceneQuery> unitProvider;
    
    private LuceneQueryTest() {
        
    }
    
    /**
     * Returns a new {@code UnitProvider<LuceneQuery>}.
     * @return a new {@code UnitProvider<LuceneQuery>}
     */
    public static UnitProvider<? extends LuceneQuery> unitProvider() {
        Preconditions.checkNotNull(unitProvider, ERR_NO_PROVIDER);
        return unitProvider;
    }
    
    public static void setUnitProvider(Class<? extends UnitProvider<? extends LuceneQuery>> providerClass) {
        Preconditions.checkNotNull(providerClass, "The given Provider class must not be null");
        try {
            unitProvider = providerClass.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
    
    /**
     * Cleans the AbstractLuceneQueryTest for the next test.
     */
    @AfterClass
    public static void unsetUnitProvider() {
        unitProvider = null;
    }

}
