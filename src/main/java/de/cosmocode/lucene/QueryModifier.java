package de.cosmocode.lucene;

import de.cosmocode.patterns.Immutable;
import de.cosmocode.patterns.ThreadSafe;

/**
 * This is an immutable class that affects the addArgument and addField methods of SolrQuery.
 * It is for that reason just a storage class, to keep the signature of the LuceneQuery methods short.
 * Detailed documentation for the input types can be found in the methods of the {@link Builder} of this class.
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

    
    public QueryModifier(TermModifier termModifier, boolean split,
            boolean disjunct, boolean wildcarded, Double fuzzyness) {
        super();
        
        if (termModifier == null) {
            throw new NullPointerException(ERR_TERMMOD_NULL);
        }
        if (fuzzyness != null && (fuzzyness < 0 || fuzzyness >= 1)) {
            throw new IllegalArgumentException(ERR_FUZZYNESS_INVALID);
        }
        
        this.termModifier = termModifier;
        this.split = split;
        this.disjunct = disjunct;
        this.wildcarded = wildcarded;
        this.fuzzyness = fuzzyness;
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
        if (disjunct) {
            return new QueryModifier(TermModifier.NONE, split, disjunct, wildcarded, fuzzyness);
        } else {
            return new QueryModifier(TermModifier.REQUIRED, split, disjunct, wildcarded, fuzzyness);
        }
    }
    
    /**
     * Returns the QueryModifier for the arguments of a field.
     * The TermModifier is set to NONE.
     * @return the QueryModifier for the arguments of a field
     */
    public QueryModifier getArgumentModifier() {
        return new QueryModifier(TermModifier.NONE, split, disjunct, wildcarded, fuzzyness);
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
        if (fuzzyness == null) throw new IllegalStateException(ERR_FUZZYNESS_DISABLED);
        return fuzzyness.doubleValue();
    }
    
    
    
    /* 
     * Builder Pattern
     * The Builder is a static class, and QueryModifier has some helper methods
     */
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (disjunct ? 1231 : 1237);
        result = prime * result
                + ((fuzzyness == null) ? 0 : fuzzyness.hashCode());
        result = prime * result + (split ? 1231 : 1237);
        result = prime * result
                + ((termModifier == null) ? 0 : termModifier.hashCode());
        result = prime * result + (wildcarded ? 1231 : 1237);
        return result;
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


    /**
     * Returns a {@link Builder} which is initialized as a copy of this QueryModifier.
     * @return a copy of this QueryModifier, as a {@link Builder}
     */
    public QueryModifier.Builder copy() {
        return copyOf(this);
    }
    
    /**
     * Returns a copy of the given QueryModifier. 
     * It returns a {@link Builder} which is initialized with the same values as `mod`
     * @param mod the QueryModifier to copy
     * @return a copy of a QueryModifier, as a {@link Builder}
     */
    public static QueryModifier.Builder copyOf(final QueryModifier mod) {
        final QueryModifier.Builder builder = new QueryModifier.Builder();
        builder.setTermModifier(mod.termModifier);
        builder.setSplit(mod.isSplit());
        builder.setDisjunct(mod.isDisjunct());
        builder.setWildcarded(mod.isWildcarded());
        builder.setFuzzyness(mod.fuzzyness);
        return builder;
    }
    
    /**
     * Returns an empty {@link Builder}. 
     * It can be used to create a new {@link QueryModifier}.
     * <br> The TermModifier ({@link #getTermModifier()}) is set to {@link TermModifier#NONE}
     * split is false, disjunct is false and wildcarded is false.
     * @return an empty {@link Builder}
     */
    public static QueryModifier.Builder start() {
        final QueryModifier.Builder builder = new QueryModifier.Builder();
        builder.setTermModifier(TermModifier.NONE);
        return builder;
    }
    
    /**
     * This class is a Builder for a {@link QueryModifier}.
     * An object of this class is mutable, but the resulting {@link QueryModifier} 
     * of the {@link #end()} method is immutable.
     * 
     * @author Oliver Lorenz
     */
    public static final class Builder {
        
        private TermModifier tm;
        private boolean s;
        private boolean d;
        private boolean wc;
        private Double fuzzy;
        
        
        public Builder() {
            this.tm = TermModifier.NONE;
        }
        
        
        /**
         * Set the {@link TermModifier}.
         * @param termModifier the {@link TermModifier} to set.
         * @return this
         * @throws NullPointerException if tm is null
         */
        public Builder setTermModifier(final TermModifier termModifier) {
            if (termModifier == null) throw new NullPointerException(ERR_TERMMOD_NULL);
            this.tm = termModifier;
            return this;
        }
        
        /**
         * Set split.
         * <br> If true, then all added fields and arguments to a {@link LuceneQuery} are
         * - additionally to being added normally - split at blanks and each fragment is added to the query.
         * @param isSplit whether or not 
         * @return this
         */
        public Builder setSplit(final boolean isSplit) {
            this.s = isSplit;
            return this;
        }
        
        /**
         * Set disjunct.
         * <br> If disjunct is true then multiple values added to a field on a {@link LuceneQuery}
         * are added in disjunction (or-clause, one of the values must be found).
         * <br> Otherwise multiple values in a field on a {@link LuceneQuery}
         * are added in conjunction (and-clause, all values must be found).
         * @param disjunct if true then values are disjunct (or-clause), otherwise conjunct (and-clause)
         * @return this
         */
        public Builder setDisjunct(final boolean disjunct) {
            this.d = disjunct;
            return this;
        }
        
        /**
         * Set wildCarded.
         * <br> If true then values added to a {@link LuceneQuery} are searched wildcarded.
         * That means: If the user searches for "abc", then the Query is built so that
         * Lucene searches for everything that starts with "abc", or is "abc" itself.
         * <br> In Lucene's Query Language this is: abc* "abc"
         * <br> Otherwise values are added normally.
         * @param wildCarded whether values should be wildcarded or not
         * @return this
         */
        public Builder setWildcarded(final boolean wildCarded) {
            this.wc = wildCarded;
            return this;
        }
        
        /**
         * Set fuzzyness to given value.
         * @param fuzzyness the fuzzyness to set
         * @return this
         * @throws IllegalArgumentException if fuzzyness < 0 || f >= 1
         */
        public Builder setFuzzyness(final Double fuzzyness) {
            if (fuzzyness != null) {
                if (fuzzyness < 0 || fuzzyness >= 1) throw new IllegalArgumentException(ERR_FUZZYNESS_INVALID);
            }
            this.fuzzy = fuzzyness;
            return this;
        }
        
        
        /**
         * <p> This is a shortcut for {@code setTermModifier(TermModifier.REQUIRED)}.
         * </p> 
         * @return this
         * @see TermModifier#REQUIRED
         * @see #setTermModifier(TermModifier)
         */
        public Builder required() {
            this.setTermModifier(TermModifier.REQUIRED);
            return this;
        }
        
        /**
         * <p> This is a shortcut for {@code setTermModifier(TermModifier.PROHIBITED)}.
         * </p> 
         * @return this
         * @see TermModifier#PROHIBITED
         */
        public Builder excluded() {
            this.setTermModifier(TermModifier.PROHIBITED);
            return this;
        }

        /**
         * <p> This is a shortcut for {@code setTermModifier(TermModifier.PROHIBITED)}.
         * </p> 
         * @return this
         * @see TermModifier#PROHIBITED
         */
        public Builder prohibited() {
            this.setTermModifier(TermModifier.PROHIBITED);
            return this;
        }
        
        /**
         * Set wildCarded to true.
         * <br> Values added to a {@link LuceneQuery} are searched wildcarded.
         * That means: If the user searches for "abc", then the Query is built so that
         * Lucene searches for everything that starts with "abc", or is "abc" itself.
         * <br> In Lucene's Query Language this is: abc* "abc"
         * @return this
         */
        public Builder wildcarded() {
            this.wc = true;
            return this;
        }
        
        /**
         * Set wildCarded to false.
         * <br> Values added to a {@link LuceneQuery} are searched normally, without wildcards.
         * @return this
         */
        public Builder notWildcarded() {
            this.wc = false;
            return this;
        }
        
        /**
         * Set split to true. 
         * <br> That means that all added fields and arguments to the {@link LuceneQuery} are 
         * - additionally to being added normally - split at blanks and each fragment is added to the query.
         * @return this
         */
        public Builder doSplit() {
            this.s = true;
            return this;
        }
        
        /**
         * Set split to false.
         * <br> All added fields and arguments to a {@link LuceneQuery} are added normally.
         * @return this
         */
        public Builder dontSplit() {
            this.s = false;
            return this;
        }
        
        /**
         * Set disjunct to true.
         * <br> This means: multiple values added to a field on a {@link LuceneQuery}
         * are added in disjunction (or-clause, one of the values must be found).
         * @return this
         */
        public Builder disjunct() {
            this.d = true;
            return this;
        }
        
        /**
         * Set disjunct to false.
         * <br> This means: Multiple values in a field on a {@link LuceneQuery}
         * are added in conjunction (and-clause, all values must be found).
         * @return this
         */
        public Builder conjunct() {
            this.d = false;
            return this;
        }
        
        /**
         * Disables fuzzyness (the default state of a freshly initialized {@link Builder}).
         * @return this
         */
        public Builder noFuzzyness() {
            this.fuzzy = null;
            return this;
        }
        
        /**
         * Builds the {@link QueryModifier} that this {@link Builder} represents.
         * @return the finished {@link QueryModifier}
         */
        public QueryModifier end() {
            return new QueryModifier(tm, s, d, wc, fuzzy);
        }
    }
    
}
