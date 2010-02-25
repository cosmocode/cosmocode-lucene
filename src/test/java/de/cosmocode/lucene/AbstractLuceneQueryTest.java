package de.cosmocode.lucene;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.cosmocode.junit.UnitProvider;
import de.cosmocode.lucene.fragments.AddCollectionFragment;
import de.cosmocode.lucene.fragments.AddStringFragment;

@RunWith(Suite.class)
@SuiteClasses({
    AddCollectionFragment.class,
    AddStringFragment.class
})
public abstract class AbstractLuceneQueryTest implements UnitProvider<LuceneQuery> {
    
    private static Class<? extends UnitProvider<LuceneQuery>> instance;
    
    protected AbstractLuceneQueryTest() {
        
    }
    
    public static UnitProvider<LuceneQuery> getInstance() {
        try {
            return instance.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
    
    public static void setInstance(Class<? extends UnitProvider<LuceneQuery>> newInstance) {
        instance = newInstance;
    }
    
    @AfterClass
    public static void unsetClass() {
        setInstance(null);
    }

}
