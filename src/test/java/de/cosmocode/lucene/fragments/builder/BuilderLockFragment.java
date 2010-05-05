/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.lucene.fragments.builder;

import java.util.Collection;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.cosmocode.junit.UnitProvider;
import de.cosmocode.lucene.LuceneQuery;
import de.cosmocode.lucene.LuceneQueryBuilder;
import de.cosmocode.lucene.QueryModifier;

/**
 * Tests the lock() method for {@link LuceneQueryBuilder}. 
 *
 * @author Oliver Lorenz
 */
public final class BuilderLockFragment implements UnitProvider<LuceneQuery> {
    
    private static final QueryModifier TEST_MOD = QueryModifier.start().required().wildcarded().doSplit().build();
    
    @Override
    public LuceneQueryBuilder unit() {
        final LuceneQueryBuilder builder = new LuceneQueryBuilder();
        builder.addField("dummy", "dummy");
        builder.lock();
        return builder;
    }
    
    /**
     * Tests all read methods after a lock.
     */
    @Test
    public void readAfterLock() {
        final LuceneQueryBuilder builder = unit();
        builder.getModifier();
        builder.getQuery();
        builder.isWildCarded();
        builder.lastSuccessful();
        builder.build();
        builder.lock();
    }
    
    
    /*
     * addArgument methods
     */

    /**
     * Attempts {@link LuceneQuery#addArgument(String)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentString() {
        unit().addArgument("test");
    }

    /**
     * Attempts {@link LuceneQuery#addArgument(String, boolean)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentStringBool() {
        unit().addArgument("test", true);
    }

    /**
     * Attempts {@link LuceneQuery#addArgument(String, QueryModifier)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentStringModifier() {
        unit().addArgument("test", TEST_MOD);
    }

    /**
     * Attempts {@link LuceneQuery#addArgument(Object[])} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentArray() {
        unit().addArgument(new String[] {"test1", "test2"});
    }

    /**
     * Attempts {@link LuceneQuery#addArgument(Object[], boolean)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentArrayBool() {
        unit().addArgument(new String[] {"test1", "test2"}, true);
    }

    /**
     * Attempts {@link LuceneQuery#addArgument(Object[], QueryModifier)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentArrayModifier() {
        unit().addArgument(new String[] {"test1", "test2"}, TEST_MOD);
    }

    /**
     * Attempts {@link LuceneQuery#addArgument(Collection)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentCollection() {
        unit().addArgument(Lists.newArrayList("test1", "test2"));
    }

    /**
     * Attempts {@link LuceneQuery#addArgument(Collection, boolean)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentCollectionBool() {
        unit().addArgument(Lists.newArrayList("test1", "test2"), true);
    }

    /**
     * Attempts {@link LuceneQuery#addArgument(Collection, QueryModifier)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentCollectionModifier() {
        unit().addArgument(Lists.newArrayList("test1", "test2"), TEST_MOD);
    }

    /**
     * Attempts {@link LuceneQuery#addArgument(int[])} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentIntArray() {
        unit().addArgument(new int[] {1, 5, 3});
    }

    /**
     * Attempts {@link LuceneQuery#addArgument(int[], QueryModifier)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentIntArrayModifier() {
        unit().addArgument(new int[] {1, 5, 3}, TEST_MOD);
    }

    /**
     * Attempts {@link LuceneQuery#addArgument(double[])} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentDoubleArray() {
        unit().addArgument(new double[] {1.5, 5.3, 3.2});
    }

    /**
     * Attempts {@link LuceneQuery#addArgument(double[], QueryModifier)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentDoubleArrayModifier() {
        unit().addArgument(new double[] {1.5, 5.3, 3.2}, TEST_MOD);
    }
    
    /**
     * Attempts {@link LuceneQuery#addArgumentAsArray(Object[])} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentAsArray() {
        unit().addArgumentAsArray(new String[] {"test1", "test2"});
    }
    
    /**
     * Attempts {@link LuceneQuery#addArgumentAsArray(Object[], QueryModifier)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentAsArrayModifier() {
        unit().addArgumentAsArray(new String[] {"test1", "test2"}, TEST_MOD);
    }
    
    
    /*
     * addField methods
     */

    /**
     * Attempts {@link LuceneQuery#addField(String, String)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldString() {
        unit().addField("test", "test");
    }

    /**
     * Attempts {@link LuceneQuery#addField(String, String, boolean)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldStringBool() {
        unit().addField("test", "test", true);
    }

    /**
     * Attempts {@link LuceneQuery#addField(String, String, QueryModifier)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldStringModifier() {
        unit().addField("test", "test", TEST_MOD);
    }

    /**
     * Attempts {@link LuceneQuery#addField(String, Object[])} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldArray() {
        unit().addField("test", new String[] {"test1", "test2"});
    }

//    /**
//     * Attempts {@link LuceneQueryBuilder#addField(String, Object[], boolean)} after lock.
//     */
//    @Test(expected = IllegalStateException.class)
//    public void addFieldArrayBool() {
//        unit().addField("test", new String[] {"test1", "test2"}, true);
//    }

    /**
     * Attempts {@link LuceneQuery#addField(String, Object[], QueryModifier)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldArrayModifier() {
        unit().addField("test", new String[] {"test1", "test2"}, TEST_MOD);
    }

    /**
     * Attempts {@link LuceneQuery#addField(String, Collection)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldCollection() {
        unit().addField("test", Lists.newArrayList("test1", "test2"));
    }

//    /**
//     * Attempts {@link LuceneQueryBuilder#addField(String, Collection, boolean)} after lock.
//     */
//    @Test(expected = IllegalStateException.class)
//    public void addFieldCollectionBool() {
//        unit().addField("test", Lists.newArrayList("test1", "test2"), true);
//    }

    /**
     * Attempts {@link LuceneQuery#addField(String, Collection, QueryModifier)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldCollectionModifier() {
        unit().addField("test", Lists.newArrayList("test1", "test2"), TEST_MOD);
    }

//    /**
//     * Attempts {@link LuceneQuery#addField(String, int[])} after lock.
//     */
//    @Test(expected = IllegalStateException.class)
//    public void addFieldIntArray() {
//        unit().addField(String, new int[] {1, 5, 3});
//    }
//
//    /**
//     * Attempts {@link LuceneQuery#addField(String, int[], QueryModifier)} after lock.
//     */
//    @Test(expected = IllegalStateException.class)
//    public void addFieldIntArrayModifier() {
//        unit().addField(String, new int[] {1, 5, 3}, TEST_MOD);
//    }

//    /**
//     * Attempts {@link LuceneQuery#addField(String, double[])} after lock.
//     */
//    @Test(expected = IllegalStateException.class)
//    public void addFieldDoubleArray() {
//        unit().addField(String, new double[] {1.5, 5.3, 3.2});
//    }
//
//    /**
//     * Attempts {@link LuceneQuery#addField(String, double[], QueryModifier)} after lock.
//     */
//    @Test(expected = IllegalStateException.class)
//    public void addFieldDoubleArrayModifier() {
//        unit().addField("test", new double[] {1.5, 5.3, 3.2}, TEST_MOD);
//    }
    
    /**
     * Attempts {@link LuceneQuery#addFieldAsArray(String, Object[])} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldAsArray() {
        unit().addFieldAsArray("test", new String[] {"test1", "test2"});
    }
    
    /**
     * Attempts {@link LuceneQuery#addFieldAsArray(String, Object[], QueryModifier)} after lock.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldAsArrayModifier() {
        unit().addFieldAsArray("test", new String[] {"test1", "test2"}, TEST_MOD);
    }
    

}
