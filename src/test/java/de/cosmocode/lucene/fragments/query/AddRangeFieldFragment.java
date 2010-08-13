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
 * <p> Tests all addRangeField methods without QueryModifiers from LuceneQuery.
 * These are:
 * </p>
 * <ul>
 *   <li> {@link LuceneQuery#addRangeField(String, String, String)} </li>
 *   <li> {@link LuceneQuery#addRangeField(String, int, int)} </li>
 *   <li> {@link LuceneQuery#addRangeField(String, double, double)} </li>
 * </ul>
 *
 * @author Oliver Lorenz
 */
public class AddRangeFieldFragment extends AbstractLuceneQueryTestFragment {

    /**
     * Tests {@link LuceneQuery#addRangeField(String, String, String)} with null values.
     */
    @Test(expected = IllegalStateException.class)
    public void nulls() {
        final LuceneQuery unit = unit().addRangeField(FIELD1, null, null);
        assertEquals("", unit);
    }
    
    /**
     * Tests {@link LuceneQuery#addRangeField(String, String, String)}.
     */
    @Test
    public void strings() {
        final LuceneQuery unit = unit().addRangeField(FIELD1, "a", "b");
        assertEquals(FIELD1 + ":[a TO b]", unit);
    }

    /**
     * Tests {@link LuceneQuery#addRangeField(String, int, int)}.
     */
    @Test
    public void ints() {
        final LuceneQuery unit = unit().addRangeField(FIELD1, 0, 4);
        assertEquals(FIELD1 + ":[0 TO 4]", unit);
    }

    /**
     * Tests {@link LuceneQuery#addRangeField(String, double, double)}.
     */
    @Test
    public void doubles() {
        final LuceneQuery unit = unit().addRangeField(FIELD1, 1.2, 1.7);
        assertEquals(FIELD1 + ":[1.2 TO 1.7]", unit);
    }

}
