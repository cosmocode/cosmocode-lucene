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

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import de.cosmocode.lucene.LuceneQuery;
import de.cosmocode.lucene.QueryModifier;

/**
 * <p> Tests all addField-methods that are Collection related for {@link LuceneQuery}. 
 * </p>
 * <p> The test methods are:
 * </p>
 * <ul>
 *   <li> {@link LuceneQuery#addField(String, Collection)} </li>
 *   <li> {@link LuceneQuery#addField(String, boolean, Collection, boolean)} </li>
 *   <li> {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)} </li>
 * </ul>
 * @author Oliver Lorenz
 */
public abstract class AddFieldCollectionTest extends AbstractLuceneQueryTestCase {

    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with a valid key and a null value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullValue() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, (Collection<?>) null);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with a null key and a valid value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullKey() {
        final LuceneQuery query = unit();
        query.addField(null, ImmutableList.of(ARG1));
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with null key and null value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNulls() {
        final LuceneQuery query = unit();
        query.addField(null, (Collection<?>) null);
        query.getQuery();
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with a valid key and an empty collection as value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldEmptyValue() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, Collections.emptySet());
        query.getQuery();
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with a valid key and a collection that contains only null-pointer, empty and blank Strings.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldOnlyInvalid() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, Lists.newArrayList("   ", null, "", null, "   "));
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with a valid key and a list with one element as value.
     */
    @Test
    public void addFieldOneElement() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, ImmutableList.of(ARG1));
        final String expected = "+" + FIELD1 + ":" + ARG1;
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * on another field and a list with one element as value.
     */
    @Test
    public void addFieldField2OneElement() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD2, ImmutableList.of(ARG1));
        final String expected = "+" + FIELD2 + ":" + ARG1;
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with a valid key and a list with two elements as value.
     */
    @Test
    public void addFieldTwoElements() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, ImmutableList.of(ARG1, ARG2));
        final String expected = "+" + FIELD1 + ":" + "(+" + ARG1 + " +" + ARG2 + ")";
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, Collection)}
     * with a valid key and a list that contains nulls and empty Strings.
     */
    @Test
    public void addFieldContainsInvalid() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, Lists.newArrayList(ARG1, "", null, ARG3, null, "   "));
        final String expected = "+" + FIELD1 + ":" + "(" + ARG1 + " AND " + ARG3 + ")";
        assertEquals(expected, query);
    }

    
    /*
     * Null and illegal arguments test for addField(String, boolean, Collection, boolean)
     */
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key and a null value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullValueBoolBool() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, true, (Collection<?>) null, true);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a null key and a valid value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullKeyBoolBool() {
        final LuceneQuery query = unit();
        query.addField(null, true, ImmutableList.of(ARG1), true);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with null key and null value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullsBoolBool() {
        final LuceneQuery query = unit();
        query.addField(null, true, (Collection<?>) null, true);
        query.getQuery();
    }

    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key and an empty collection as value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldEmptyValueBoolBool() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, true, Collections.emptySet(), true);
        query.getQuery();
    }

    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key and a collection with only invalid elements (nulls, empty and blank Strings).
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldOnlyInvalidBoolBool() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, true, Lists.newArrayList("   ", null, "", null, "   "), true);
        query.getQuery();
    }

    
    /*
     * Tests for addField(String, boolean, Collection, boolean)
     */
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key, true, a list with one element as value and true.
     */
    @Test
    public void addFieldOneElementTrueTrue() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, true, ImmutableList.of(ARG1), true);
        final String expected = "+" + FIELD1 + ":" + ARG1;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key, true, a list with one element as value and false.
     */
    @Test
    public void addFieldOneElementTrueFalse() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, true, ImmutableList.of(ARG1), false);
        final String expected = "+" + FIELD1 + ":" + ARG1;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key, false, a list with one element as value and true.
     */
    @Test
    public void addFieldOneElementFalseTrue() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, false, ImmutableList.of(ARG1), true);
        final String expected = FIELD1 + ":" + ARG1;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key, false, a list with one element as value and false.
     */
    @Test
    public void addFieldOneElementFalseFalse() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, false, ImmutableList.of(ARG1), false);
        final String expected = FIELD1 + ":" + ARG1;
        assertEquals(expected, query);
    }

    
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key, true, a list with two elements as value and true.
     */
    @Test
    public void addFieldTwoElementsTrueTrue() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, true, ImmutableList.of(ARG1, ARG2), true);
        final String expected = "+" + FIELD1 + ":" + "(" + ARG1 + " AND " + ARG2 + ")";
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key, true, a list with two elements as value and false.
     */
    @Test
    public void addFieldTwoElementsTrueFalse() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, true, ImmutableList.of(ARG1, ARG2), false);
        final String expected = "+" + FIELD1 + ":" + "(" + ARG1 + " OR " + ARG2 + ")";
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key, false, a list with two elements as value and true.
     */
    @Test
    public void addFieldTwoElementsFalseTrue() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, false, ImmutableList.of(ARG1, ARG2), true);
        final String expected = FIELD1 + ":" + "(" + ARG1 + " AND " + ARG2 + ")";
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key, false, a list with two elements as value and false.
     */
    @Test
    public void addFieldTwoElementsFalseFalse() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, false, ImmutableList.of(ARG1, ARG2), false);
        final String expected = FIELD1 + ":" + "(" + ARG1 + " OR " + ARG2 + ")";
        assertEquals(expected, query);
    }

    
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key, true, a list that contains nulls and empty Strings and true.
     */
    @Test
    public void addFieldContainsInvalidTrueTrue() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, true, Lists.newArrayList(ARG1, "", null, ARG3, null, "   "), true);
        final String expected = "+" + FIELD1 + ":" + "(" + ARG1 + " AND " + ARG3 + ")";
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key, true, a list that contains nulls and empty Strings and false.
     */
    @Test
    public void addFieldContainsInvalidTrueFalse() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, true, Lists.newArrayList(ARG1, "", null, ARG3, null, "   "), false);
        final String expected = "+" + FIELD1 + ":" + "(" + ARG1 + " OR " + ARG3 + ")";
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key, false, a list that contains nulls and empty Strings and true.
     */
    @Test
    public void addFieldContainsInvalidFalseTrue() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, false, Lists.newArrayList(ARG1, "", null, ARG3, null, "   "), true);
        final String expected = FIELD1 + ":" + "(" + ARG1 + " AND " + ARG3 + ")";
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean)}
     * with a valid key, false, a list that contains nulls and empty Strings and false.
     */
    @Test
    public void addFieldContainsInvalidFalseFalse() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, false, Lists.newArrayList(ARG1, "", null, ARG3, null, "   "), false);
        final String expected = FIELD1 + ":" + "(" + ARG1 + " OR " + ARG3 + ")";
        assertEquals(expected, query);
    }

    
    /*
     * Null and illegal arguments test for addField(String, boolean, Collection, boolean, double)
     */
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key and a null value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullValueBoolBoolDouble() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, true, (Collection<?>) null, true, 2.0);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a null key and a valid value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullKeyBoolBoolDouble() {
        final LuceneQuery query = unit();
        query.addField(null, true, ImmutableList.of(ARG1), true, 2.0);
        query.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with null key and null value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldNullsBoolBoolDouble() {
        final LuceneQuery query = unit();
        query.addField(null, true, (Collection<?>) null, true, 2.0);
        query.getQuery();
    }

    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key and an empty collection as value.
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldEmptyValueBoolBoolDouble() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, true, Collections.emptySet(), true, 3.0);
        query.getQuery();
    }

    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key and a collection with only invalid elements (nulls, empty and blank Strings).
     */
    @Test(expected = IllegalStateException.class)
    public void addFieldOnlyInvalidBoolBoolDouble() {
        final LuceneQuery query = unit();
        query.addField(FIELD1, true, Lists.newArrayList("   ", null, "", null, "   "), true, 3.0);
        query.getQuery();
    }
    
    
    /*
     * Tests for addField(String, boolean, Collection, boolean, double)
     */
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key, true, a list with one element as value and true.
     */
    @Test
    public void addFieldOneElementTrueTrueDouble() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, true, ImmutableList.of(ARG1), true, 5.0);
        query.addField(FIELD1, ARG3, false);
        final String expected = "+" + FIELD1 + ":" + ARG1 + "^5 " + FIELD1 + ":" + ARG3;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key, true, a list with one element as value and false.
     */
    @Test
    public void addFieldOneElementTrueFalseDouble() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, true, ImmutableList.of(ARG1), false, 3.5);
        query.addField(FIELD1, ARG3, false);
        final String expected = "+" + FIELD1 + ":" + ARG1 + "^3.5 " + FIELD1 + ":" + ARG3;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key, false, a list with one element as value and true.
     */
    @Test
    public void addFieldOneElementFalseTrueDouble() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, false, ImmutableList.of(ARG1), true, 2.5);
        query.addField(FIELD1, ARG3, false);
        final String expected = FIELD1 + ":" + ARG1 + "^2.5 " + FIELD1 + ":" + ARG3;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key, false, a list with one element as value and false.
     */
    @Test
    public void addFieldOneElementFalseFalseDouble() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, false, ImmutableList.of(ARG1), false, 2.35);
        query.addField(FIELD1, ARG3, false);
        final String expected = FIELD1 + ":" + ARG1 + "^2.35 " + FIELD1 + ":" + ARG3;
        assertEquals(expected, query);
    }

    
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key, true, a list with two elements as value and true.
     */
    @Test
    public void addFieldTwoElementsTrueTrueDouble() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, true, ImmutableList.of(ARG1, ARG2), true, 4.5);
        query.addField(FIELD1, ARG3, false);
        final String expected = "+" + FIELD1 + ":" + "(" + ARG1 + " AND " + ARG2 + ")^4.5 " + FIELD1 + ":" + ARG3;
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key, true, a list with two elements as value and false.
     */
    @Test
    public void addFieldTwoElementsTrueFalseDouble() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, true, ImmutableList.of(ARG1, ARG2), false, 2.15);
        query.addField(FIELD1, ARG3, false);
        final String expected = "+" + FIELD1 + ":" + "(" + ARG1 + " OR " + ARG2 + ")^2.15 " + FIELD1 + ":" + ARG3;
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key, false, a list with two elements as value and true.
     */
    @Test
    public void addFieldTwoElementsFalseTrueDouble() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, false, ImmutableList.of(ARG1, ARG2), true, 3.0);
        query.addField(FIELD1, ARG3, false);
        final String expected = FIELD1 + ":" + "(" + ARG1 + " AND " + ARG2 + ")^3.0 " + FIELD1 + ":" + ARG3;
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key, false, a list with two elements as value and false.
     */
    @Test
    public void addFieldTwoElementsFalseFalseDouble() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, false, ImmutableList.of(ARG1, ARG2), false, 4.0);
        query.addField(FIELD1, ARG3, false);
        final String expected = FIELD1 + ":" + "(" + ARG1 + " OR " + ARG2 + ")^4.0 " + FIELD1 + ":" + ARG3;
        assertEquals(expected, query);
    }

    
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key, true, a list that contains nulls and empty Strings and true.
     */
    @Test
    public void addFieldContainsInvalidTrueTrueDouble() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, true, Lists.newArrayList(ARG1, "", null, ARG3, null, "   "), true, 2.5);
        query.addField(FIELD1, ARG3, false);
        final String expected = "+" + FIELD1 + ":" + "(" + ARG1 + " AND " + ARG3 + ")^2.5 " + FIELD1 + ":" + ARG3;
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key, true, a list that contains nulls and empty Strings and false.
     */
    @Test
    public void addFieldContainsInvalidTrueFalseDouble() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, true, Lists.newArrayList(ARG1, "", null, ARG3, null, "   "), false, 4.2);
        query.addField(FIELD1, ARG3, false);
        final String expected = "+" + FIELD1 + ":" + "(" + ARG1 + " OR " + ARG3 + ")^4.2 " + FIELD1 + ":" + ARG3;
        assertEquals(expected, query);
    }
    
    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key, false, a list that contains nulls and empty Strings and true.
     */
    @Test
    public void addFieldContainsInvalidFalseTrueDouble() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, false, Lists.newArrayList(ARG1, "", null, ARG3, null, "   "), true, 3.8);
        query.addField(FIELD1, ARG3, false);
        final String expected = FIELD1 + ":" + "(" + ARG1 + " AND " + ARG3 + ")^3.8 " + FIELD1 + ":" + ARG3;
        assertEquals(expected, query);
    }

    /**
     * Tests {@link LuceneQuery#addField(String, boolean, Collection, boolean, double)}
     * with a valid key, false, a list that contains nulls and empty Strings and false.
     */
    @Test
    public void addFieldContainsInvalidFalseFalseDouble() {
        final LuceneQuery query = unit();
        query.setModifier(QueryModifier.start().required().end());
        query.addField(FIELD1, false, Lists.newArrayList(ARG1, "", null, ARG3, null, "   "), false, 3.4);
        query.addField(FIELD1, ARG3, false);
        final String expected = FIELD1 + ":" + "(" + ARG1 + " OR " + ARG3 + ")^3.4 " + FIELD1 + ":" + ARG3;
        assertEquals(expected, query);
    }

}
