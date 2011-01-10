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
 * {@link Bijection} that converts a {@link TermModifier} to an {@link Occur}
 * from the Lucene API.
 *
 * @since 1.3
 * @author olorenz
 */
enum TermModifierToOccur implements Bijection<TermModifier, Occur> {
    
    INSTANCE;

    @Override
    public Occur apply(TermModifier input) {
        if (input == null) {
            // null check here to satisfy function apply contract
            return Occur.SHOULD;
        } else {
            switch (input) {
                case NONE: {
                    return Occur.SHOULD;
                }
                case PROHIBITED: {
                    return Occur.MUST_NOT;
                }
                case REQUIRED: {
                    return Occur.MUST;
                }
                default: {
                    throw new IllegalStateException("Unknown term modifier " + input);
                }
            }
        }
    }

    @Override
    public Bijection<Occur, TermModifier> inverse() {
        return OccurToTermModifier.INSTANCE;
    }
}
