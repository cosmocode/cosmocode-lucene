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
import de.cosmocode.lucene.QueryModifier;

/**
 * <p> Tests all addField-methods that are Collection related for {@link LuceneQuery}. 
 * </p>
 * <p> The test methods are:
 * </p>
 * <ul>
 *   <li> {@link LuceneQuery#addField(String, Object[])} </li>
 * </ul>
 * @author Oliver Lorenz
 */
public class AddFieldArrayFragment extends AbstractLuceneQueryTestFragment {
    
    @Override
    public LuceneQuery unit() {
        final LuceneQuery unit = super.unit();
        unit.setModifier(QueryModifier.start().required().end());
        return unit;
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Object[])}
     * with a valid key and a null value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullValue() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, (Object[]) null);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, Object[])}
     * with a null key and a valid value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullKey() {
        final LuceneQuery query = unit();
        query.addField(null, new String[] {ARG1});
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, Object[])}
     * with null key and null value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNulls() {
        final LuceneQuery query = unit();
        query.addField(null, (String[]) null);
        query.getQuery();
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Object[])}
     * with a valid key and an empty collection as value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldEmptyValue() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, new Object[] {});
        query.getQuery();
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Object[])}
     * with a valid key and a collection that contains only null-pointer, empty and blank Strings.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldOnlyInvalid() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, new String[] {"   ", null, "", null, "   "});
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, Object[])}
     * with a valid key and a list with one element as value.
     */
    @Test
    public void addFieldOneElement() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, new String[] {ARG1});
        final String expected = "+" + FIELD1 + ":" + ARG1;
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Object[])}
     * on another field and a list with one element as value.
     */
    @Test
    public void addFieldField2OneElement() {
        final LuceneQuery query = unit();
        query.addField(FIELD2, new String[] {ARG1});
        final String expected = "+" + FIELD2 + ":" + ARG1;
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Object[])}
     * with a valid key and a list with two elements as value.
     */
    @Test
    public void addFieldTwoElements() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, new Object[] {ARG1, ARG2});
        final String expected = "+" + FIELD1 + ":" + "(+" + ARG1 + " +" + ARG2 + ")";
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Object[])}
     * with a valid key and a list that contains nulls and empty Strings.
     */
    @Test
    public void addFieldContainsInvalid() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, new Object[] {ARG1, "", null, ARG3, null, "   "});
        final String expected = "+" + FIELD1 + ":" + "(" + ARG1 + " AND " + ARG3 + ")";
        assertEquals(expected, query);
    }

}
