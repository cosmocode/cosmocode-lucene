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
 * <p> Tests the addArgument-method
 * {@link LuceneQuery#addArgument(String, QueryModifier)}.
 * 
 * @author Oliver Lorenz
 */
public class AddArgumentStringModFragment extends AbstractQueryModifierFragment {
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, QueryModifier)}
     * with a null String and a dummy QueryModifier.
     */
    @Test(expected = IllegalStateException.class)
    public void stringNull() {
        final LuceneQuery unit = unit().addArgument((String) null, QueryModifier.DEFAULT);
        unit.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, QueryModifier)}
     * with an empty String ("") and a dummy QueryModifier.
     */
    @Test(expected = IllegalStateException.class)
    public void stringEmpty() {
        final LuceneQuery unit = unit().addArgument("", QueryModifier.DEFAULT);
        unit.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, QueryModifier)}
     * with a blank String ("   ") and a dummy QueryModifier.
     */
    @Test(expected = IllegalStateException.class)
    public void stringBlank() {
        final LuceneQuery unit = unit().addArgument("   ", QueryModifier.DEFAULT);
        unit.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(String, QueryModifier)}
     * with a valid String and the QueryModifier null.
     * Expects a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void modifierNull() {
        unit().addArgument("test", null);
    }
    

    @Override
    protected void applyNormal(LuceneQuery query, QueryModifier mod) {
        query.addArgument(ARG3, mod);
    }

    @Override
    protected String expectedNormalConjunct() {
        return ARG3;
    }

    @Override
    protected String expectedNormalDisjunct() {
        return ARG3;
    }
    

    @Override
    protected void applyFuzzy(LuceneQuery query, QueryModifier mod) {
        query.addArgument(FUZZY1, mod);
    }

    @Override
    protected String expectedFuzzyConjunct() {
        return FUZZY1 + "~0.7";
    }

    @Override
    protected String expectedFuzzyDisjunct() {
        return FUZZY1 + "~0.7";
    }
    

    @Override
    protected void applySplit(LuceneQuery query, QueryModifier mod) {
        query.addArgument(ARG1 + "   " + ARG3, mod);
    }

    @Override
    protected String expectedSplitConjunct() {
        return ARG1 + " AND " + ARG3;
    }

    @Override
    protected String expectedSplitDisjunct() {
        return ARG1 + " OR " + ARG3;
    }
    

    @Override
    protected void applyWildcarded(LuceneQuery query, QueryModifier mod) {
        query.addArgument(WILDCARD1, mod);
    }

    @Override
    protected String expectedWildcardedConjunct() {
        return WILDCARD1 + " " + WILDCARD1 + "*";
    }

    @Override
    protected String expectedWildcardedDisjunct() {
        return WILDCARD1 + " " + WILDCARD1 + "*";
    }
    

    @Override
    protected void applyFuzzySplit(LuceneQuery query, QueryModifier mod) {
        query.addArgument(FUZZY1 + "   " + FUZZY2, mod);
    }

    @Override
    protected String expectedFuzzySplitConjunct() {
        return "(" + FUZZY1 + "~0.7) AND (" + FUZZY2 + "~0.7)";
    }

    @Override
    protected String expectedFuzzySplitDisjunct() {
        return "(" + FUZZY1 + "~0.7) OR (" + FUZZY2 + "~0.7)";
    }
    

    @Override
    protected void applyWildcardedFuzzy(LuceneQuery query, QueryModifier mod) {
        query.addArgument(FUZZY1, mod);
    }

    @Override
    protected String expectedWildcardedFuzzyConjunct() {
        return FUZZY1 + " " + FUZZY1 + "* " + FUZZY1 + "~0.7";
    }

    @Override
    protected String expectedWildcardedFuzzyDisjunct() {
        return FUZZY1 + " " + FUZZY1 + "* " + FUZZY1 + "~0.7";
    }
    

    @Override
    protected void applyWildcardedSplit(LuceneQuery query, QueryModifier mod) {
        query.addArgument(WILDCARD1 + "   " + WILDCARD2, mod);
    }

    @Override
    protected String expectedWildcardedSplitConjunct() {
        return
            "(\"" + WILDCARD1 + "   " + WILDCARD2 + "\" " + WILDCARD1 + "\\ \\ \\ " + WILDCARD2 + "*)" +
            "((" + WILDCARD1 + " " + WILDCARD1 + "*) AND (" + WILDCARD2 + " " + WILDCARD2 + "*))^0.5";
    }

    @Override
    protected String expectedWildcardedSplitDisjunct() {
        return
            "(\"" + WILDCARD1 + "   " + WILDCARD2 + "\" " + WILDCARD1 + "\\ \\ \\ " + WILDCARD2 + "*)" +
            "((" + WILDCARD1 + " " + WILDCARD1 + "*) OR (" + WILDCARD2 + " " + WILDCARD2 + "*))^0.5";
    }
    

    @Override
    protected void applyWildcardedFuzzySplit(LuceneQuery query, QueryModifier mod) {
        query.addArgument(FUZZY1 + "   " + WILDCARD2, mod);
    }

    @Override
    protected String expectedWildcardedFuzzySplitConjunct() {
        return
            "(" + FUZZY1 + "\\ \\ \\ " + WILDCARD2 + " " +
                FUZZY1 + "\\ \\ \\ " + WILDCARD2 + "* " +
                FUZZY1 + "\\ \\ \\ " + WILDCARD2 + "~0.7"
            + ") OR (" +
                "(" + FUZZY1 + " " + FUZZY1 + "* " + FUZZY1 + "~0.7) " +
                "AND (" + WILDCARD2 + " " + WILDCARD2 + "* " + WILDCARD2 + "~0.7)" +
            ")^0.5";
    }

    @Override
    protected String expectedWildcardedFuzzySplitDisjunct() {
        return
            "(" + FUZZY1 + "\\ \\ \\ " + WILDCARD2 + " " +
                FUZZY1 + "\\ \\ \\ " + WILDCARD2 + "* " +
                FUZZY1 + "\\ \\ \\ " + WILDCARD2 + "~0.7"
            + ") OR (" +
                "(" + FUZZY1 + " " + FUZZY1 + "* " + FUZZY1 + "~0.7) " +
                "OR (" + WILDCARD2 + " " + WILDCARD2 + "* " + WILDCARD2 + "~0.7)" +
            ")^0.5";
    }

}
