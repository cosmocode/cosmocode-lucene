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

import de.cosmocode.lucene.LuceneQuery;
import de.cosmocode.lucene.QueryModifier;

/**
 * Tests {@link LuceneQuery#addRangeField(String, int, int, QueryModifier)}.
 *
 * @author Oliver Lorenz
 */
public class AddRangeFieldIntIntModFragment extends AbstractQueryModifierFragment {

    @Override
    protected void applyNormal(LuceneQuery query, QueryModifier mod) {
        query.addRangeField(FIELD1, 3, 9, mod);
    }

    @Override
    protected String expectedNormalConjunct() {
        return FIELD1 + ":[3 TO 9]";
    }

    @Override
    protected String expectedNormalDisjunct() {
        return FIELD1 + ":[3 TO 9]";
    }

    @Override
    protected void applyWildcarded(LuceneQuery query, QueryModifier mod) {
        query.addRangeField(FIELD1, 1, 2, mod);
    }

    @Override
    protected String expectedWildcardedConjunct() {
        return FIELD1 + ":[1* TO 2*]";
    }

    @Override
    protected String expectedWildcardedDisjunct() {
        return FIELD1 + ":[1* TO 2*]";
    }
    
    // the rest below does not do anything different (yet).

    @Override
    protected void applyFuzzy(LuceneQuery query, QueryModifier mod) {
        applyNormal(query, mod);
    }

    @Override
    protected void applyFuzzySplit(LuceneQuery query, QueryModifier mod) {
        applyNormal(query, mod);
    }

    @Override
    protected void applySplit(LuceneQuery query, QueryModifier mod) {
        applyNormal(query, mod);
    }

    @Override
    protected void applyWildcardedFuzzy(LuceneQuery query, QueryModifier mod) {
        applyWildcarded(query, mod);
    }

    @Override
    protected void applyWildcardedFuzzySplit(LuceneQuery query, QueryModifier mod) {
        applyWildcarded(query, mod);
    }

    @Override
    protected void applyWildcardedSplit(LuceneQuery query, QueryModifier mod) {
        applyWildcarded(query, mod);
    }

    @Override
    protected String expectedFuzzyConjunct() {
        return expectedNormalConjunct();
    }

    @Override
    protected String expectedFuzzyDisjunct() {
        return expectedNormalDisjunct();
    }

    @Override
    protected String expectedFuzzySplitConjunct() {
        return expectedNormalConjunct();
    }

    @Override
    protected String expectedFuzzySplitDisjunct() {
        return expectedNormalConjunct();
    }

    @Override
    protected String expectedSplitConjunct() {
        return expectedNormalConjunct();
    }

    @Override
    protected String expectedSplitDisjunct() {
        return expectedNormalDisjunct();
    }

    @Override
    protected String expectedWildcardedFuzzyConjunct() {
        return expectedWildcardedConjunct();
    }

    @Override
    protected String expectedWildcardedFuzzyDisjunct() {
        return expectedWildcardedDisjunct();
    }

    @Override
    protected String expectedWildcardedFuzzySplitConjunct() {
        return expectedWildcardedConjunct();
    }

    @Override
    protected String expectedWildcardedFuzzySplitDisjunct() {
        return expectedWildcardedDisjunct();
    }

    @Override
    protected String expectedWildcardedSplitConjunct() {
        return expectedWildcardedConjunct();
    }

    @Override
    protected String expectedWildcardedSplitDisjunct() {
        return expectedWildcardedDisjunct();
    }

}
