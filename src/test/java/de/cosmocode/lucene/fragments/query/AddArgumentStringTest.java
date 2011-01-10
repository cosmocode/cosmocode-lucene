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
 * <p> Tests all addArgument-methods that are String related for {@link LuceneQuery}. <br />
 * Tested methods are:
 * </p>
 * <ul>
 *   <li> {@link LuceneQuery#addArgument(String)}</li>
 *   <li> {@link LuceneQuery#addArgument(String, boolean)}</li>
 * </ul>
 * 
 * @author Oliver Lorenz
 */
public abstract class AddArgumentStringTest extends AbstractLuceneQueryTestCase {
    
    /**
     * Tests {@link LuceneQuery#addArgument(String)} with null.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentNull() {
        final LuceneQuery query = unit();
        query.addArgument((String) null);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String)} with a blank String value.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentBlank() {
        final LuceneQuery query = unit();
        query.addArgument("  ");
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String)} with a valid String.
     */
    @Test
    public void addArgument() {
        final LuceneQuery query = unit();
        query.addArgument(ARG1);
        final String expected = ARG1;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, boolean)} with a null value and true.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentNullTrue() {
        final LuceneQuery query = unit();
        query.addArgument((String) null, true);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, boolean)} with a blank String value and true.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentBlankTrue() {
        final LuceneQuery query = unit();
        query.addArgument("  ", true);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, boolean)} with a valid String value and true.
     */
    @Test
    public void addArgumentTrue() {
        final LuceneQuery query = unit();
        query.addArgument(ARG1, true);
        final String expected = "+" + ARG1;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, boolean)} with a null value and false.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentNullFalse() {
        final LuceneQuery query = unit();
        query.addArgument((String) null, false);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, boolean)} with a blank String value and false.
     */
    @Test(expected = IllegalStateException.class)
    public void addArgumentBlankFalse() {
        final LuceneQuery query = unit();
        query.addArgument("  ", false);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, boolean)} with a valid String value and false.
     */
    @Test
    public void addArgumentFalse() {
        final LuceneQuery query = unit();
        query.addArgument(ARG1, false);
        final String expected = ARG1;
        assertEquals(expected, query);
    }

}
