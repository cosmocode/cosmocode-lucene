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

import com.google.common.base.Preconditions;

import de.cosmocode.patterns.Immutable;
import de.cosmocode.patterns.ThreadSafe;

/**
 * <p> This is an immutable class that affects the addArgument and addField methods of SolrQuery.
 * It is for that reason just a storage class, to keep the signature of the LuceneQuery methods short.
 * Detailed documentation for the input types can be found in the methods of the {@link ModifierBuilder}.
 * </p>
 * <p> Some predefined QueryModifiers can be found at LuceneHelper:
 * </p>
 * <ul>
 *   <li> {@link LuceneHelper#MOD_ID} - for ids </li>
 *   <li> {@link LuceneHelper#MOD_TEXT} - for texts </li>
 *   <li> {@link LuceneHelper#MOD_NOT_ID} - to exclude ids </li>
 *   <li> {@link LuceneHelper#MOD_AUTOCOMPLETE} - a sample for autocompletion, with fuzzyness at 0.7 </li>
 * </ul>
 * 
 * @since 1.0
 * @author Oliver Lorenz
 */
@ThreadSafe
@Immutable
public final class QueryModifier {
    
    public static final String ERR_TERMMOD_NULL = 
        "the given TermModifier must not be null";
    
    public static final String ERR_FUZZYNESS_INVALID = 
        "the given fuzzyness must be between 0 (inclusive) and 1 (exclusive)";
    
    public static final String ERR_FUZZYNESS_DISABLED = 
        "fuzzyness is not enabled";
    
    /**
     * The default QueryModifier.
     * It has termModifier set to NONE,
     * split, disjunct and wildcarded are false
     * and fuzzyness is disabled.
     */
    public static final QueryModifier DEFAULT = start().end();
    
    
    private final TermModifier termModifier;
    private final boolean split;
    private final boolean disjunct;
    private final boolean wildcarded;
    private final Double fuzzyness;
    
    private final int myHashCode;
    private QueryModifier multiModifier;
    private QueryModifier argumentModifier;

    
    public QueryModifier(TermModifier termModifier, boolean split,
            boolean disjunct, boolean wildcarded, Double fuzzyness) {
        super();
        
        Preconditions.checkNotNull(termModifier, ERR_TERMMOD_NULL);
        Preconditions.checkArgument(
            fuzzyness == null || (fuzzyness >= 0 && fuzzyness < 1),
            ERR_FUZZYNESS_INVALID
        );
        
        this.termModifier = termModifier;
        this.split = split;
        this.disjunct = disjunct;
        this.wildcarded = wildcarded;
        this.fuzzyness = fuzzyness;

        this.myHashCode = generateHashCode();
    }
    
    
    private int generateHashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (disjunct ? 1231 : 1237);
        result = prime * result + ((fuzzyness == null) ? 0 : fuzzyness.hashCode());
        result = prime * result + (split ? 1231 : 1237);
        result = prime * result + ((termModifier == null) ? 0 : termModifier.hashCode());
        result = prime * result + (wildcarded ? 1231 : 1237);
        return result;
    }
    
    
    private QueryModifier createMultiModifier() {
        if (disjunct && termModifier == TermModifier.NONE) {
            return this;
        } else if (disjunct) {
            return this.copy().setTermModifier(TermModifier.NONE).end();
        } else if (termModifier == TermModifier.REQUIRED) {
            return this;
        } else {
            return this.copy().required().end();
        }
    }
    
    
    private QueryModifier createArgumentModifier() {
        if (termModifier == TermModifier.NONE) {
            return this;
        } else {
            return new QueryModifier(TermModifier.NONE, split, disjunct, wildcarded, fuzzyness);
        }
    }
    
    
    /**
     * This method returns the term prefix.
     * The term prefix is written in front of the term or field
     * and affects the number and content of documents returned.
     * 
     * @see TermModifier
     * @return the term prefix for the Term
     */
    public String getTermPrefix() {
        return termModifier.getModifier();
    }
    
    public TermModifier getTermModifier() {
        return termModifier;
    }
    
    /**
     * Returns the QueryModifier for the values of a collection or an array.
     * @return the QueryModifier for the values of a collection or an array
     */
    public QueryModifier getMultiValueModifier() {
        if (this.multiModifier == null) {
            this.multiModifier = createMultiModifier();
        }
        return multiModifier;
    }
    
    /**
     * Returns the QueryModifier for the arguments of a field.
     * The TermModifier is set to NONE.
     * @return the QueryModifier for the arguments of a field
     */
    public QueryModifier getArgumentModifier() {
        if (this.argumentModifier == null) {
            this.argumentModifier = createArgumentModifier();
        }
        return argumentModifier;
    }
    
    public boolean isDisjunct() {
        return disjunct;
    }
    
    public boolean isSplit() {
        return split;
    }
    
    public boolean isWildcarded() {
        return wildcarded;
    }
    
    public boolean isFuzzyEnabled() {
        return fuzzyness != null;
    }
    
    /**
     * Returns the fuzzyness for a fuzzy search.
     * Throws an IllegalStateException if fuzzyness is not enabled.
     * @return the fuzzyness of the search
     * @throws IllegalStateException if fuzzyness is disabled. Check with {@link #isFuzzyEnabled()}
     * 
     * @see #isFuzzyEnabled()
     */
    public double getFuzzyness() {
        Preconditions.checkState(fuzzyness != null, ERR_FUZZYNESS_DISABLED);
        return fuzzyness.doubleValue();
    }
    
    
    @Override
    public int hashCode() {
        return myHashCode;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof QueryModifier) {
            final QueryModifier other = (QueryModifier) obj;
            return
                disjunct == other.disjunct
                && split == other.split
                && wildcarded == other.wildcarded
                && termModifier.equals(other.termModifier)
                && (isFuzzyEnabled() 
                    ? fuzzyness.equals(other.fuzzyness) 
                    : !other.isFuzzyEnabled());
        } else {
            return false;
        }
    }
    

    @Override
    public String toString() {
        return "QueryModifier [disjunct=" + disjunct + ", fuzzyness="
                + fuzzyness + ", split=" + split + ", termModifier="
                + termModifier + ", wildcarded=" + wildcarded + "]";
    }
    
    
    
    /* 
     * Builder Pattern
     * The Builder is an external class, and following are some helper methods
     */


    /**
     * Returns a {@link ModifierBuilder} which is initialized as a copy of this QueryModifier.
     * @return a copy of this QueryModifier, as a {@link ModifierBuilder}
     */
    public ModifierBuilder copy() {
        return copyOf(this);
    }
    
    /**
     * Returns a copy of the given QueryModifier. 
     * It returns a {@link ModifierBuilder} which is initialized with the same values as `mod`
     * @param mod the QueryModifier to copy
     * @return a copy of a QueryModifier, as a {@link ModifierBuilder}
     */
    public static ModifierBuilder copyOf(final QueryModifier mod) {
        final ModifierBuilder builder = new ModifierBuilder();
        builder.setTermModifier(mod.termModifier);
        builder.setSplit(mod.isSplit());
        builder.setDisjunct(mod.isDisjunct());
        builder.setWildcarded(mod.isWildcarded());
        builder.setFuzzyness(mod.fuzzyness);
        return builder;
    }
    
    /**
     * Returns an empty {@link ModifierBuilder}. 
     * It can be used to create a new {@link QueryModifier}.
     * <br> The TermModifier ({@link #getTermModifier()}) is set to {@link TermModifier#NONE}
     * split is false, disjunct is false and wildcarded is false.
     * @return an empty {@link ModifierBuilder}
     */
    public static ModifierBuilder start() {
        final ModifierBuilder builder = new ModifierBuilder();
        builder.setTermModifier(TermModifier.NONE);
        return builder;
    }
    
}
