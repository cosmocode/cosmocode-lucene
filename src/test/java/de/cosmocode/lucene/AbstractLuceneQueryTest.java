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
import de.cosmocode.lucene.fragments.AddArgumentStringModFragment;
import de.cosmocode.lucene.fragments.AddFieldArrayFragment;
import de.cosmocode.lucene.fragments.AddFieldArrayModFragment;
import de.cosmocode.lucene.fragments.AddFieldCollectionFragment;
import de.cosmocode.lucene.fragments.AddArgumentStringFragment;
import de.cosmocode.lucene.fragments.AddFieldCollectionModFragment;
import de.cosmocode.lucene.fragments.AddFieldStringFragment;
import de.cosmocode.lucene.fragments.AddFieldStringModFragment;

/**
 * <p> Generic Test for {@link LuceneQuery}.
 * It provides only static methods, but implements {@code UnitProvider<LuceneQuery>}.
 * The unit() method is not implemented, but left for implementation.
 * A test for LuceneQuery must extend this class, implement the unit()-method
 * and add a static method to set the UnitProvider of this class.
 * </p>
 * <p> Example:
 * </p>
 * <pre>
 * {@code @}Override
 *  public LuceneQuery unit() {
 *      return new DefaultLuceneQuery();
 *  }
 * 
 * {@code @}BeforeClass
 *  public static void setInstance() {
 *      setUnitProvider(DefaultLuceneQueryTest.class);
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
public abstract class AbstractLuceneQueryTest implements UnitProvider<LuceneQuery> {
    
    private static Class<? extends UnitProvider<LuceneQuery>> unitProvider;
    
    protected AbstractLuceneQueryTest() {
        
    }
    
    /**
     * Returns a new {@code UnitProvider<LuceneQuery>}.
     * @return a new {@code UnitProvider<LuceneQuery>}
     */
    public static UnitProvider<LuceneQuery> unitProvider() {
        Preconditions.checkNotNull(unitProvider, 
            "UnitProvider class not yet set, set it with AbstractLuceneQueryTest.setUnitProvider(...)");
        try {
            return unitProvider.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
    
    public static void setUnitProvider(Class<? extends UnitProvider<LuceneQuery>> newInstance) {
        unitProvider = newInstance;
    }
    
    /**
     * Unsets the unit provider class, so that is null afterwards.
     * This method is called after class, this means after the test suite was run.
     */
    @AfterClass
    public static void unsetUnitProvider() {
        setUnitProvider(null);
    }

}
