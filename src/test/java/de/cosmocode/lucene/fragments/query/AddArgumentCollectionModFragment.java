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

import org.junit.Test;

import com.google.common.collect.Lists;

import de.cosmocode.lucene.LuceneQuery;
import de.cosmocode.lucene.QueryModifier;

/**
 * <p> Tests the addArgument-method
 * {@link LuceneQuery#addArgument(Collection, QueryModifier)}
 * for {@link LuceneQuery}.
 * </p>
 * 
 * @author Oliver Lorenz
 */
public final class AddArgumentCollectionModFragment extends AbstractQueryModifierFragment {
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with null and a dummy QueryModifier.
     */
    @Test(expected = IllegalStateException.class)
    public void collectionNull() {
        final LuceneQuery unit = unit().addArgument((Collection<?>) null, QueryModifier.DEFAULT);
        unit.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with an empty Collection and a dummy QueryModifier.
     */
    @Test(expected = IllegalStateException.class)
    public void collectionEmpty() {
        final LuceneQuery unit = unit().addArgument(Lists.newArrayList(), QueryModifier.DEFAULT);
        unit.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a blank Collection (with blank and empty Strings and nulls) and a dummy QueryModifier.
     */
    @Test(expected = IllegalStateException.class)
    public void collectionEmptyValues() {
        final Collection<?> toAdd = Lists.newArrayList("   ", null, "", null, "     ");
        final LuceneQuery unit = unit().addArgument(toAdd, QueryModifier.DEFAULT);
        unit.getQuery();
    }
    
    /**
     * Tests {@link LuceneQuery#addArgument(Collection, QueryModifier)}
     * with a normal value and the QueryModifier null.
     * Expects a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void modifierNull() {
        unit().addArgument(Lists.newArrayList(ARG1), null);
    }
    
    
    
    @Override
    protected void applyNormal(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.<Object>newArrayList(ARG1, "  ", ARG2, null, ARG3, "");
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedNormalConjunct() {
        return "(" + ARG1 + " AND " + ARG2 + " AND " + ARG3 + ")";
    }
    
    @Override
    protected String expectedNormalDisjunct() {
        return "(" + ARG1 + " OR " + ARG2 + " OR " + ARG3 + ")";
    }
    
    
    
    @Override
    protected void applyWildcarded(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.newArrayList(WILDCARD1, "  ", null, WILDCARD2);
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedWildcardedConjunct() {
        return "(" + WILDCARD1 + "* " + WILDCARD1 + ") AND (" + WILDCARD2 + "* " + WILDCARD2 + ")";
    }
    
    @Override
    protected String expectedWildcardedDisjunct() {
        return "(" + WILDCARD1 + "* " + WILDCARD1 + ") OR (" + WILDCARD2 + "* " + WILDCARD2 + ")";
    }
    
    
    
    @Override
    protected void applySplit(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.<Object>newArrayList(ARG1 + "  " + ARG3, "", ARG2, null, "");
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedSplitConjunct() {
        return "((" + ARG1 + "\\ " + ARG3 + " OR (" + ARG1 + " AND " + ARG3 + "))^0.5 AND " + ARG2 + ")";
    }
    
    @Override
    protected String expectedSplitDisjunct() {
        return "((" + ARG1 + "\\ " + ARG3 + " OR (" + ARG1 + " OR " + ARG3 + "))^0.5 OR " + ARG2 + ")";
    }
    
    
    
    @Override
    protected void applyFuzzy(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.<Object>newArrayList(FUZZY1, "", null, "   ", FUZZY2);
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedFuzzyConjunct() {
        return "(" + FUZZY1 + "~0.7 AND " + FUZZY2 + "~0.7)";
    }
    
    @Override
    protected String expectedFuzzyDisjunct() {
        return "(" + FUZZY1 + "~0.7 OR " + FUZZY2 + "~0.7)";
    }
    
    
    
    @Override
    protected void applyWildcardedFuzzy(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.<Object>newArrayList(FUZZY1, WILDCARD1, null, "  ", FUZZY2, WILDCARD2);
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedWildcardedFuzzyConjunct() {
        return
            "(" + FUZZY1 + " " + FUZZY1 + "* " + FUZZY1 + "~0.7) " +
            "AND (" + WILDCARD1 + " " + WILDCARD1 + "* " + WILDCARD1 + "~0.7) " +
            "AND (" + FUZZY2 + " " + FUZZY2 + "* " + FUZZY2 + "~0.7) " +
            "AND (" + WILDCARD2 + " " + WILDCARD2 + "* " + WILDCARD2 + "~0.7)";
    }
    
    @Override
    protected String expectedWildcardedFuzzyDisjunct() {
        return
            "(" + FUZZY1 + " " + FUZZY1 + "* " + FUZZY1 + "~0.7) " +
            "OR (" + WILDCARD1 + " " + WILDCARD1 + "* " + WILDCARD1 + "~0.7) " +
            "OR (" + FUZZY2 + " " + FUZZY2 + "* " + FUZZY2 + "~0.7) " +
            "OR (" + WILDCARD2 + " " + WILDCARD2 + "* " + WILDCARD2 + "~0.7)";
    }
    
    
    
    @Override
    protected void applyFuzzySplit(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.newArrayList(FUZZY1 + "  " + FUZZY2, "", null, FUZZY3, null, "   ");
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedFuzzySplitConjunct() {
        return
            "(" + FUZZY1 + "\\ " + FUZZY2 + "~0.7 OR " +
            "(" + FUZZY1 + "~0.7 AND " + FUZZY2 + "~0.7)" +
            ")^0.5 AND " + FUZZY3 + "~0.7";
    }
    
    @Override
    protected String expectedFuzzySplitDisjunct() {
        return
            "(" + FUZZY1 + "\\ " + FUZZY2 + "~0.7 OR " +
            "(" + FUZZY1 + "~0.7 OR " + FUZZY2 + "~0.7)" +
            ")^0.5 OR " + FUZZY3 + "~0.7";
    }
    
    
    
    @Override
    protected void applyWildcardedSplit(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.newArrayList(WILDCARD1 + "  " + WILDCARD2, "", null, WILDCARD3);
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedWildcardedSplitConjunct() {
        return
            "(" +
                "(" +
                    "\"" + WILDCARD1 + "  " + WILDCARD2 + "\" " +
                    WILDCARD1 + "\\ \\ " + WILDCARD2 + "* " +
                ") OR (" +
                    "(" + WILDCARD1 + " " + WILDCARD1 + "*)" +
                    " AND (" + WILDCARD2 + " " + WILDCARD2 + "*)" +
                ")^0.5" +
            ") AND (" + WILDCARD3 + " " + WILDCARD3 + "*)";
    }
    
    @Override
    protected String expectedWildcardedSplitDisjunct() {
        return
            "(" +
                "(" +
                    "\"" + WILDCARD1 + "  " + WILDCARD2 + "\" " +
                    WILDCARD1 + "\\ \\ " + WILDCARD2 + "* " +
                ") OR (" +
                    "(" + WILDCARD1 + " " + WILDCARD1 + "*)" +
                    " OR (" + WILDCARD2 + " " + WILDCARD2 + "*)" +
                ")^0.5" +
            ") OR (" + WILDCARD3 + " " + WILDCARD3 + "*)";
    }
    
    
    
    @Override
    protected void applyWildcardedFuzzySplit(LuceneQuery query, QueryModifier mod) {
        final Collection<?> toAdd = Lists.newArrayList(
            WILDCARD1 + "  " + FUZZY2, "  ", null, "  ", WILDCARD2, FUZZY3);
        query.addArgument(toAdd, mod);
    }
    
    @Override
    protected String expectedWildcardedFuzzySplitConjunct() {
        return
            "(" +
                "(\"" + WILDCARD1 + "  " + FUZZY2 + "\" " +
                    WILDCARD1 + "\\ \\ " + FUZZY2 + "* " + 
                    WILDCARD1 + "\\ \\ " + FUZZY2 + "~0.7" +
                ") OR (" +
                    "(" + WILDCARD1 + " " + WILDCARD1 + "* " + WILDCARD1 + "~0.7) " +
                    "AND (" + FUZZY2 + " " + FUZZY2 + "* " + FUZZY2 + "~0.7)" +
                ")^0.5" +
            ") AND (" + WILDCARD2 + " " + WILDCARD2 + "* " + WILDCARD2 + "~0.7) " +
            "AND (" + FUZZY3 + " " + FUZZY3 + "* " + FUZZY3 + "~0.7)";
    }
    
    @Override
    protected String expectedWildcardedFuzzySplitDisjunct() {
        return
            "(" +
                "(\"" + WILDCARD1 + "  " + FUZZY2 + "\" " +
                    WILDCARD1 + "\\ \\ " + FUZZY2 + "* " + 
                    WILDCARD1 + "\\ \\ " + FUZZY2 + "~0.7" +
                ") OR (" +
                    "(" + WILDCARD1 + " " + WILDCARD1 + "* " + WILDCARD1 + "~0.7) " +
                    "OR (" + FUZZY2 + " " + FUZZY2 + "* " + FUZZY2 + "~0.7)" +
                ")^0.5" +
            ") OR (" + WILDCARD2 + " " + WILDCARD2 + "* " + WILDCARD2 + "~0.7) " +
            "OR (" + FUZZY3 + " " + FUZZY3 + "* " + FUZZY3 + "~0.7)";
    }

}
