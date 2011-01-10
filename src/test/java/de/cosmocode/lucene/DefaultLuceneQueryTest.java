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

package de.cosmocode.lucene;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import de.cosmocode.lucene.fragments.query.AddArgumentArrayModifierTest;
import de.cosmocode.lucene.fragments.query.AddArgumentArrayTest;
import de.cosmocode.lucene.fragments.query.AddArgumentCollectionModifierTest;
import de.cosmocode.lucene.fragments.query.AddArgumentCollectionTest;
import de.cosmocode.lucene.fragments.query.AddArgumentStringModifierTest;
import de.cosmocode.lucene.fragments.query.AddFieldArrayModifierTest;
import de.cosmocode.lucene.fragments.query.AddFieldArrayTest;
import de.cosmocode.lucene.fragments.query.AddFieldCollectionModifierTest;
import de.cosmocode.lucene.fragments.query.AddFieldCollectionTest;
import de.cosmocode.lucene.fragments.query.AddFieldStringModifierTest;
import de.cosmocode.lucene.fragments.query.AddFieldStringTest;
import de.cosmocode.lucene.fragments.query.AddRangeDoublesModifierTest;
import de.cosmocode.lucene.fragments.query.AddRangeFieldDoublesModifierTest;
import de.cosmocode.lucene.fragments.query.AddRangeFieldIntegersModifierTest;
import de.cosmocode.lucene.fragments.query.AddRangeFieldStringsModifierTest;
import de.cosmocode.lucene.fragments.query.AddRangeIntsModifierTest;
import de.cosmocode.lucene.fragments.query.AddRangeStringsModifierTest;
import de.cosmocode.lucene.fragments.query.AddRangeTest;

/**
 * Tests {@link DefaultLuceneQuery}.
 * 
 * @author Oliver Lorenz
 */
@RunWith(Enclosed.class)
public final class DefaultLuceneQueryTest extends LuceneQueryTest {
    
    /**
     * Helper class that returns our current unit under test.
     * This is out of convenience and to satisfy the DRY principle.
     * 
     * @return a new LuceneQuery
     */
    public static LuceneQuery unit() {
        return new DefaultLuceneQuery();
    }
    
    /**
     * Implementation of {@link AddArgumentArrayModifierTest}.
     */
    public static class DefaultAddArgumentArrayModifierTest extends AddArgumentArrayModifierTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddArgumentCollectionTest}.
     */
    public static class DefaultAddArgumentCollectionTest extends AddArgumentCollectionTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddArgumentCollectionModifierTest}.
     */
    public static class DefaultAddArgumentCollectionModifierTest extends AddArgumentCollectionModifierTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddArgumentArrayTest}.
     */
    public static class DefaultAddArgumentArrayTest extends AddArgumentArrayTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddArgumentStringModifierTest}.
     */
    public static class DefaultAddArgumentStringModifierTest extends AddArgumentStringModifierTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddFieldArrayModifierTest}.
     */
    public static class DefaultAddFieldArrayModifierTest extends AddFieldArrayModifierTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddFieldArrayTest}.
     */
    public static class DefaultAddFieldArrayTest extends AddFieldArrayTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddFieldCollectionModifierTest}.
     */
    public static class DefaultAddFieldCollectionModifierTest extends AddFieldCollectionModifierTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddFieldCollectionTest}.
     */
    public static class DefaultAddFieldCollectionTest extends AddFieldCollectionTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddFieldStringModifierTest}.
     */
    public static class DefaultAddFieldStringModifierTest extends AddFieldStringModifierTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddFieldStringTest}.
     */
    public static class DefaultAddFieldStringTest extends AddFieldStringTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddRangeTest}.
     */
    public static class DefaultAddRangeTest extends AddRangeTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddRangeDoublesModifierTest}.
     */
    public static class DefaultAddRangeDoublesModifierTest extends AddRangeDoublesModifierTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddRangeFieldDoublesModifierTest}.
     */
    public static class DefaultAddRangeFieldDoublesModifierTest extends AddRangeFieldDoublesModifierTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddRangeFieldIntegersModifierTest}.
     */
    public static class DefaultAddRangeFieldIntegersModifierTest extends AddRangeFieldIntegersModifierTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddRangeFieldStringsModifierTest}.
     */
    public static class DefaultAddRangeFieldStringsModifierTest extends AddRangeFieldStringsModifierTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddRangeIntsModifierTest}.
     */
    public static class DefaultAddRangeIntsModifierTest extends AddRangeIntsModifierTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }
    
    /**
     * Implementation of {@link AddRangeStringsModifierTest}.
     */
    public static class DefaultAddRangeStringsModifierTest extends AddRangeStringsModifierTest {

        @Override
        public LuceneQuery unit() {
            return DefaultLuceneQueryTest.unit();
        }
        
    }

}
