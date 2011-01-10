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

package de.cosmocode.lucene.fragments.query;

import org.junit.Test;

import de.cosmocode.lucene.LuceneQuery;

/**
 * <p> Tests all addRange and addRangeField methods without QueryModifiers from LuceneQuery.
 * These are:
 * </p>
 * <ul>
 *   <li> {@link LuceneQuery#addRange(String, String)} </li>
 *   <li> {@link LuceneQuery#addRange(int, int)} </li>
 *   <li> {@link LuceneQuery#addRange(double, double)} </li>
 *   <li> {@link LuceneQuery#addRangeField(String, String, String)} </li>
 *   <li> {@link LuceneQuery#addRangeField(String, int, int)} </li>
 *   <li> {@link LuceneQuery#addRangeField(String, double, double)} </li>
 * </ul>
 *
 * @author Oliver Lorenz
 */
public abstract class AddRangeTest extends AbstractLuceneQueryTestCase {

    /**
     * Tests {@link LuceneQuery#addRangeField(String, String, String)} with null values.
     */
    @Test(expected = IllegalStateException.class)
    public void fieldNulls() {
        final LuceneQuery unit = unit().addRangeField(FIELD1, null, null);
        assertEquals("", unit);
    }
    
    /**
     * Tests {@link LuceneQuery#addRangeField(String, String, String)}.
     */
    @Test
    public void fieldStrings() {
        final LuceneQuery unit = unit().addRangeField(FIELD1, "a", "b");
        assertEquals(FIELD1 + ":[a TO b]", unit);
    }

    /**
     * Tests {@link LuceneQuery#addRangeField(String, int, int)}.
     */
    @Test
    public void fieldInts() {
        final LuceneQuery unit = unit().addRangeField(FIELD1, 0, 4);
        assertEquals(FIELD1 + ":[0 TO 4]", unit);
    }

    /**
     * Tests {@link LuceneQuery#addRangeField(String, double, double)}.
     */
    @Test
    public void fieldDoubles() {
        final LuceneQuery unit = unit().addRangeField(FIELD1, 1.2, 1.7);
        assertEquals(FIELD1 + ":[1.2 TO 1.7]", unit);
    }

    /**
     * Tests {@link LuceneQuery#addRange(String, String)}.
     */
    @Test
    public void strings() {
        final LuceneQuery unit = unit().addRange("a", "d");
        assertEquals("[a TO d]", unit);
    }

    /**
     * Tests {@link LuceneQuery#addRange(String, String)} with null values.
     */
    @Test(expected = IllegalStateException.class)
    public void nulls() {
        final LuceneQuery unit = unit().addRange(null, null);
        assertEquals("", unit);
    }
    
    /**
     * Tests {@link LuceneQuery#addRange(int, int)}.
     */
    @Test
    public void ints() {
        final LuceneQuery unit = unit().addRange(0, 8);
        assertEquals("[0 TO 8]", unit);
    }

    /**
     * Tests {@link LuceneQuery#addRange(double, double)}.
     */
    @Test
    public void doubles() {
        final LuceneQuery unit = unit().addRange(1.2, 1.7);
        assertEquals("[1.2 TO 1.7]", unit);
    }

}
