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
 * <p> Tests all addField-methods that are String related for {@link LuceneQuery}. <br />
 * Tested methods are:
 * </p>
 * <ul>
 *   <li> {@link LuceneQuery#addField(String, String)} </li>
 *   <li> {@link LuceneQuery#addField(String, String, boolean)} </li>
 *   <li> {@link LuceneQuery#addField(String, String, boolean, double)} </li>
 * </ul>
 * 
 * @author Oliver Lorenz
 */
public class AddFieldStringFragment extends AbstractLuceneQueryTestFragment {
    
    
    /*
     * Tests for LuceneQuery#addField(String, String)
     */

    /**
     * Tests {@link LuceneQuery#addField(String, String)}
     * with a valid key and a null value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullValue() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, (String) null);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String)}
     * with a null key and a valid value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullKey() {
        final LuceneQuery query = unit();
        query.addField(null, ARG1);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String)}
     * with null key and null value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNulls() {
        final LuceneQuery query = unit();
        query.addField(null, (String) null);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String)}
     * with a valid key and a blank String value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldBlankValue() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, "  ");
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String)}
     * with a blank key and a valid String value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldBlankKey() {
        final LuceneQuery query = unit();
        query.addField("  ", ARG1);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String)}
     * with a blank key and a blank String value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldBlanks() {
        final LuceneQuery query = unit();
        query.addField("  ", "   ");
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String)}
     * with a valid key and a valid String.
     */
    @Test
    public void addField() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, ARG1);
        final String expected = FIELD1 + ":" + ARG1;
        assertEquals(expected, query);
    }
    
    
    /*
     * Tests for LuceneQuery#addField(String, String, boolean)
     */

    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean)}
     * with a valid key and a null value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullValueBool() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, (String) null, true);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean)}
     * with a null key and a valid value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullKeyBool() {
        final LuceneQuery query = unit();
        query.addField(null, ARG1, true);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean)}
     * with null key and null value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullsBool() {
        final LuceneQuery query = unit();
        query.addField(null, (String) null, true);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean)}
     * with a valid key and a blank String value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldBlankValueBool() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, "  ", true);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean)}
     * with a blank key and a valid String value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldBlankKeyBool() {
        final LuceneQuery query = unit();
        query.addField("  ", ARG1, true);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean)}
     * with a blank key and a blank String value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldBlanksBool() {
        final LuceneQuery query = unit();
        query.addField("  ", "   ", true);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean)}
     * with a valid key and a valid String and true.
     */
    @Test
    public void addFieldTrue() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, ARG1, true);
        final String expected = "+" + FIELD1 + ":" + ARG1;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean)}
     * with a valid key and a valid String and false.
     */
    @Test
    public void addFieldFalse() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, ARG1, false);
        final String expected = FIELD1 + ":" + ARG1;
        assertEquals(expected, query);
    }
    
    
    /*
     * Tests for LuceneQuery#addField(String, String, boolean, double)
     */

    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean, double)}
     * with a valid key and a null value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullValueBoolDouble() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, (String) null, true, 2.5);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean, double)}
     * with a null key and a valid value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullKeyBoolDouble() {
        final LuceneQuery query = unit();
        query.addField(null, ARG1, true, 2.5);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean, double)}
     * with null key and null value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullsBoolDouble() {
        final LuceneQuery query = unit();
        query.addField(null, (String) null, true, 2.5);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean, double)}
     * with a valid key and a blank String value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldBlankValueBoolDouble() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, "  ", true, 2.5);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean, double)}
     * with a blank key and a valid String value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldBlankKeyBoolDouble() {
        final LuceneQuery query = unit();
        query.addField("  ", ARG1, true, 2.5);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean, double)}
     * with a blank key and a blank String value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldBlanksBoolDouble() {
        final LuceneQuery query = unit();
        query.addField("  ", "   ", true, 2.5);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean, double)}
     * with a valid key and a valid String and true.
     */
    @Test
    public void addFieldTrueDouble() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, ARG1, true, 2.5);
        query.addField(FIELD1, ARG3, true, 0.5);
        final String expected = "+" + FIELD1 + ":" + ARG1 + "^2.5" + " +" + FIELD1 + ":" + ARG3 + "^0.5";
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, String, boolean, double)}
     * with a valid key and a valid String and false.
     */
    @Test
    public void addFieldFalseDouble() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, ARG1, false, 2.5);
        query.addField(FIELD2, ARG1, false, 1);
        final String expected = FIELD1 + ":" + ARG1 + "^2.5" + " " + FIELD2 + ":" + ARG1;
        assertEquals(expected, query);
    }

}
