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

import org.apache.lucene.search.BooleanClause.Occur;

import de.cosmocode.commons.Bijection;

/**
 * {@link Bijection} that converts an {@link Occur} (from the Lucene API)  
 * to a {@link TermModifier}.
 *
 * @since 1.3
 * @author olorenz
 */
enum OccurToTermModifier implements Bijection<Occur, TermModifier> {
    
    INSTANCE;
    
    @Override
    public TermModifier apply(Occur input) {
        if (input == null) {
            // input == null is here to satisfy function contract
            return TermModifier.NONE;
        } else if (input == Occur.MUST) {
            return TermModifier.REQUIRED;
        } else if (input == Occur.MUST_NOT) {
            return TermModifier.PROHIBITED;
        } else if (input == Occur.SHOULD) {
            return TermModifier.NONE;
        } else {
            throw new IllegalArgumentException("Unknown Occur type " + input);
        }
    }

    @Override
    public Bijection<TermModifier, Occur> inverse() {
        return TermModifierToOccur.INSTANCE;
    }
}
