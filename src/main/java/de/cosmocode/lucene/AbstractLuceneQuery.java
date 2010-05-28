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

import java.util.Collection;

import com.google.common.base.Preconditions;


/**
 * <p>An abstract implemententation of the LuceneQueryBuilder,
 * that takes care of the redirects to methods with
 * default values and those with old style "boolean mandatory" signature. 
 * </p>
 * <p>It implements most methods, with these exceptions:
 * </p>
 * <ul>
 *   <li>{@link #addArgument(String, QueryModifier)}</li>
 *   <li>{@link #addArgumentAsCollection(Collection, QueryModifier)}</li>
 *   <li>{@link #addArgumentAsArray(Object[], QueryModifier)}</li>
 *   <li>{@link #addArgumentAsArray(Object, QueryModifier)}</li>
 *   <li>{@link #addSubquery(LuceneQuery, QueryModifier)}</li>
 *   <li>{@link #addUnescaped(CharSequence, boolean)}</li>
 *   <li>{@link #addUnescapedField(String, CharSequence, boolean)}</li>
 *   <li>{@link #startField(String, QueryModifier)}</li>
 *   <li>{@link #endField()}</li>
 *   <li>{@link #addBoost(double)}</li>
 *   <li>{@link #getQuery()}</li>
 * </ul>
 * 
 * @since 1.0
 * @author Oliver Lorenz
 * 
 * @see LuceneQuery
 * @see ForwardingLuceneQuery
 */
public abstract class AbstractLuceneQuery implements LuceneQuery {
    
    
    private QueryModifier defaultModifier;
    
    private boolean wasLastSuccessful;
    
    
    /**
     * Initializes this {@link AbstractLuceneQuery} with the QueryModifier {@link QueryModifier#DEFAULT}.
     */
    public AbstractLuceneQuery() {
        this.defaultModifier = QueryModifier.DEFAULT;
        this.wasLastSuccessful = false;
    }
    
    /**
     * Initializes this {@link AbstractLuceneQuery} with the given default QueryModifier.
     * @param defaultModifier the default QueryModifier for the default calls and {@link #getModifier()}.
     */
    public AbstractLuceneQuery(final QueryModifier defaultModifier) {
        this.defaultModifier = defaultModifier;
        this.wasLastSuccessful = false;
    }
    

    
    //---------------------------
    //   default getter and setter
    //---------------------------
    
    
    @Override
    public boolean isWildCarded() {
        return defaultModifier.isWildcarded();
    }
    
    @Override
    public void setWildCarded(boolean wildCarded) {
        this.defaultModifier = defaultModifier.copy().setWildcarded(wildCarded).end();
    }
    
    
    @Override
    public final QueryModifier getModifier() {
        return defaultModifier;
    }
    
    @Override
    public final void setModifier(QueryModifier mod) {
        this.defaultModifier = Preconditions.checkNotNull(mod, ERR_MODIFIER_NULL);
    }
    
    @Override
    public boolean lastSuccessful() {
        return wasLastSuccessful;
    }
    
    protected void setLastSuccessful(final boolean lastSuccessful) {
        this.wasLastSuccessful = lastSuccessful;
    }
    
    @Override
    public abstract String getQuery();
    
    
    
    //---------------------------
    //     addFuzzyArgument
    //---------------------------
    
    
    @Override
    public final LuceneQuery addFuzzyArgument(final String value) {
        return addArgument(value, defaultModifier.copy().setFuzzyness(DEFAULT_FUZZYNESS).end());
    }
    
    
    @Override
    public final LuceneQuery addFuzzyArgument(final String value, final boolean mandatory) {
        return addFuzzyArgument(value, mandatory, DEFAULT_FUZZYNESS);
    }
    
    
    @Override
    public LuceneQuery addFuzzyArgument(final String value, final boolean mandatory, final double fuzzyness) {
        final TermModifier tm = mandatory ? TermModifier.REQUIRED : TermModifier.NONE;
        final QueryModifier mod = defaultModifier.copy().setTermModifier(tm).setFuzzyness(fuzzyness).end();
        return this.addArgument(value, mod);
    }
    
    
    
    //---------------------------
    //     addArgument
    //---------------------------
    
    
    @Override
    public final LuceneQuery addArgument(final String value) {
        return addArgument(value, defaultModifier);
    }
    
    
    @Override
    public LuceneQuery addArgument(final String value, final boolean mandatory) {
        final TermModifier tm = mandatory ? TermModifier.REQUIRED : TermModifier.NONE;
        final QueryModifier mod = defaultModifier.copy().setTermModifier(tm).end();
        return this.addArgument(value, mod);
    }
    
    
    @Override
    public abstract LuceneQuery addArgument(final String value, final QueryModifier modifiers);
    
    
    @Override
    public final LuceneQuery addArgument(Collection<?> values) {
        return addArgument(values, defaultModifier);
    }
    
    
    @Override
    public LuceneQuery addArgument(final Collection<?> value, final boolean mandatory) {
        // if mandatory is true, then all arguments must be found (conjunction, required).
        // otherwise if mandatory is false, then no argument must be found (disjunction, not required).
        final TermModifier tm = mandatory ? TermModifier.REQUIRED : TermModifier.NONE;
        final boolean disjunct = !mandatory;
        final QueryModifier mod = defaultModifier.copy().setTermModifier(tm).setDisjunct(disjunct).end();
        return this.addArgument(value, mod);
    }
    
    
    @Override
    public final LuceneQuery addArgument(Collection<?> values, QueryModifier modifier) {
        return addArgumentAsCollection(values, modifier);
    }
    
    
    @Override
    public final <K> LuceneQuery addArgument(K[] values) {
        return addArgument(values, defaultModifier);
    }
    
    @Override
    public <K> LuceneQuery addArgument(K[] values, boolean mandatory) {
        // if mandatory is true, then all arguments must be found (conjunction, required).
        // otherwise if mandatory is false, then no argument must be found (disjunction, not required).
        final TermModifier tm = mandatory ? TermModifier.REQUIRED : TermModifier.NONE;
        final boolean disjunct = !mandatory;
        final QueryModifier mod = defaultModifier.copy().setTermModifier(tm).setDisjunct(disjunct).end();
        return addArgument(values, mod);
    }
    
    @Override
    public final <K> LuceneQuery addArgument(K[] values, QueryModifier modifier) {
        return addArgumentAsArray(values, modifier);
    }
    
    @Override
    public final LuceneQuery addArgument(int[] values) {
        return addArgument(values, defaultModifier);
    }
    
    @Override
    public LuceneQuery addArgument(int[] values, QueryModifier modifier) {
        return addArgumentAsArray(values, modifier);
    }
    
    @Override
    public final LuceneQuery addArgument(double[] values) {
        return addArgument(values, defaultModifier);
    }
    
    @Override
    public LuceneQuery addArgument(double[] values, QueryModifier modifier) {
        return addArgumentAsArray(values, modifier);
    }
    
    
    /**
     * This is a utility method, that determines what to call based on "instanceof".
     * 
     * @param value the query argument to add; null is omitted
     * @param modifiers the modifiers for the addArgumentAs...-methods
     * @return this
     */
    protected LuceneQuery addArgument(final Object value, final QueryModifier modifiers) {
        if (value == null || modifiers == null) return this;
        
        if (value instanceof String) {
            return this.addArgument(String.class.cast(value), modifiers);
        } else if (value instanceof Collection<?>) {
            return this.addArgumentAsCollection(Collection.class.cast(value), modifiers);
        } else if (value.getClass().isArray()) { 
            return this.addArgumentAsArray(value, modifiers);
        } else if (value instanceof LuceneQuery) {
            return this.addSubquery(LuceneQuery.class.cast(value), modifiers);
        } else {
            return this.addArgument(value.toString(), modifiers);
        }
    }
    
    
    
    //---------------------------
    //     addArgumentAs...
    //---------------------------
    
    
    @Override
    public final <K> LuceneQuery addArgumentAsArray(K[] values) {
        return addArgumentAsArray(values, defaultModifier);
    };
    
    
    @Override
    public abstract <K> LuceneQuery addArgumentAsArray(K[] values, QueryModifier modifier);
    
    
    /**
     * Add an array of Terms to this QueryBuilder.
     * 
     * @param values the array of terms to search for
     * @param modifier the modifier for the search of this term.
     * @return this
     */
    protected abstract LuceneQuery addArgumentAsArray(Object values, QueryModifier modifier);
    
    
    @Override
    public final LuceneQuery addArgumentAsCollection(Collection<?> values) {
        return addArgumentAsCollection(values, defaultModifier);
    }
    
    @Override
    public abstract LuceneQuery addArgumentAsCollection(Collection<?> values, QueryModifier modifier);
    
    
    /*
     * addRange
     */

    @Override
    public final LuceneQuery addRange(double from, double to) {
        return addRange(from, to, defaultModifier);
    };
    
    @Override
    public abstract LuceneQuery addRange(double from, double to, QueryModifier mod);
    
    @Override
    public final LuceneQuery addRange(int from, int to) {
        return addRange(from, to, defaultModifier);
    }
    
    @Override
    public abstract LuceneQuery addRange(int from, int to, QueryModifier mod);
    
    @Override
    public final LuceneQuery addRange(String from, String to) {
        return addRange(from, to, defaultModifier);
    }
    
    @Override
    public abstract LuceneQuery addRange(String from, String to, QueryModifier mod);
    
    
    
    //---------------------------
    //     addSubquery
    //---------------------------
    
    
    @Override
    public final LuceneQuery addSubquery(LuceneQuery value) {
        return addSubquery(value, defaultModifier);
    }
    
    @Override
    public LuceneQuery addSubquery(LuceneQuery value, boolean mandatory) {
        final TermModifier tm = mandatory ? TermModifier.REQUIRED : TermModifier.NONE;
        final QueryModifier mod = defaultModifier.copy().setTermModifier(tm).end();
        return addSubquery(value, mod);
    }
    
    @Override
    public abstract LuceneQuery addSubquery(LuceneQuery value, QueryModifier modifiers);
    
    
    
    //---------------------------
    //     addField(String, String, ...)
    //---------------------------
    
    
    @Override
    public final LuceneQuery addField(String key, String value) {
        return addField(key, value, defaultModifier);
    }
    
    @Override
    public LuceneQuery addField(String key, String value, boolean mandatoryKey) {
        final TermModifier tm = mandatoryKey ? TermModifier.REQUIRED : TermModifier.NONE;
        final QueryModifier mod = defaultModifier.copy().setTermModifier(tm).end();
        return addField(key, value, mod);
    }
    
    @Override
    public LuceneQuery addField(String key, String value,
            boolean mandatoryKey, double boostFactor) {
        final TermModifier tm = mandatoryKey ? TermModifier.REQUIRED : TermModifier.NONE;
        final QueryModifier mod = defaultModifier.copy().setTermModifier(tm).end();
        return addField(key, value, mod).addBoost(boostFactor);
    }
    
    @Override
    public LuceneQuery addField(final String key, final String value, final QueryModifier modifier) {
        this.startField(key, modifier);
        if (lastSuccessful()) {
            this.addArgument(value, modifier.getArgumentModifier());
            this.endField();
        }
        return this;
    }
    
    
    
    //---------------------------
    //     addField(String, Collection, ...)
    //---------------------------
    
    
    @Override
    public final LuceneQuery addField(String key, Collection<?> value) {
        return addFieldAsCollection(key, value, defaultModifier);
    }
    
    @Override
    public LuceneQuery addField(String key, boolean mandatoryKey,
            Collection<?> value, boolean mandatoryValue) {
        final ModifierBuilder builder = defaultModifier.copy();
        final QueryModifier mod;
        
        if (mandatoryKey && mandatoryValue) {
            // field is required; all values must occur: conjunction (and)
            mod = builder.setTermModifier(TermModifier.REQUIRED).setDisjunct(false).end();
        } else if (mandatoryKey && !mandatoryValue) {
            // field is required; no value is mandatory: disjunction (or)
            // that means that one of the values must occur
            mod = builder.setTermModifier(TermModifier.REQUIRED).setDisjunct(true).end();
        } else if (!mandatoryKey && mandatoryValue) {
            // field is not required (but boosted in results);
            // all values must occur: conjunction (and)
            mod = builder.setTermModifier(TermModifier.NONE).setDisjunct(false).end();
        } else {
            // field is not required (but boosted in results);
            // no value is mandatory: disjunction (or)
            // This means: Each document that has one of the given values for the field is boosted
            mod = builder.setTermModifier(TermModifier.NONE).setDisjunct(true).end();
        }
        
        return addFieldAsCollection(key, value, mod);
    }
    
    @Override
    public LuceneQuery addField(String key, boolean mandatoryKey,
            Collection<?> value, boolean mandatoryValue, double boostFactor) {
        return
            addField(key, mandatoryKey, value, mandatoryValue).
            addBoost(boostFactor);
    }
    
    @Override
    public final LuceneQuery addField(String key, Collection<?> value, QueryModifier modifier) {
        return addFieldAsCollection(key, value, modifier);
    }
    
    
    
    //---------------------------
    //     addField(String, Array, ...)
    //---------------------------
    
    
    @Override
    public final <K> LuceneQuery addField(String key, K[] value) {
        return addFieldAsArray(key, value, defaultModifier);
    };
    
    
    @Override
    public final <K> LuceneQuery addField(String key, K[] value, QueryModifier modifier) {
        return addFieldAsArray(key, value, modifier);
    };
    
    
    //---------------------------
    //     addFuzzyField
    //---------------------------
    
    
    @Override
    public final LuceneQuery addFuzzyField(String key, String value) {
        return addField(key, value, defaultModifier.copy().setFuzzyness(DEFAULT_FUZZYNESS).end());
    }
    
    @Override
    public LuceneQuery addFuzzyField(String key, String value, boolean mandatoryKey) {
        final TermModifier tm = mandatoryKey ? TermModifier.REQUIRED : TermModifier.NONE;
        final QueryModifier mod = defaultModifier.copy().
            setTermModifier(tm).setFuzzyness(DEFAULT_FUZZYNESS).end();
        return addField(key, value, mod);
    }
    
    @Override
    public LuceneQuery addFuzzyField(String key, String value, boolean mandatoryKey, double fuzzyness) {
        final TermModifier tm = mandatoryKey ? TermModifier.REQUIRED : TermModifier.NONE;
        final QueryModifier mod = defaultModifier.copy().setTermModifier(tm).setFuzzyness(fuzzyness).end();
        return addField(key, value, mod);
    }
    
    
    //---------------------------
    //     addFieldAs...
    //---------------------------
    
    
    @Override
    public final LuceneQuery addFieldAsCollection(String key, Collection<?> value) {
        return addFieldAsCollection(key, value, defaultModifier);
    }
    
    @Override
    public LuceneQuery addFieldAsCollection(String key,
            Collection<?> value, QueryModifier modifier, double boost) {
        return 
            addFieldAsCollection(key, value, modifier).
            addBoost(boost);
    }
    
    @Override
    public LuceneQuery addFieldAsCollection(
        final String key, final Collection<?> value, final QueryModifier modifier) {

        this.startField(key, modifier);
        if (lastSuccessful()) {
            this.addArgumentAsCollection(value, modifier.getArgumentModifier());
            this.endField();
        }
        return this;
    }

    
    @Override
    public final <K> LuceneQuery addFieldAsArray(String key, K[] value) {
        return addFieldAsArray(key, value, defaultModifier);
    };
    
    @Override
    public <K> LuceneQuery addFieldAsArray(
        final String key, final K[] value, final QueryModifier modifier) {
        
        this.startField(key, modifier);
        if (lastSuccessful()) {
            this.addArgumentAsArray(value, modifier.getArgumentModifier());
            this.endField();
        }
        return this;
    }
    
    /**
     * <p> Add a field with an array of Terms to this LuceneQuery.
     * </p>
     * <p> This method can be used for the system type arrays:
     * int[], long[], boolean[], ...
     * </p>
     * 
     * @param key the name of the field
     * @param values the array of terms to search for
     * @param modifier the modifier for the search of this term.
     * @return this
     */
    protected LuceneQuery addFieldAsArray(
        final String key, final Object values, final QueryModifier modifier) {

        startField(key, modifier);
        if (lastSuccessful()) {
            addArgumentAsArray(values, modifier.getArgumentModifier());
            endField();
        }
        return this;
    }
    
    
    //-----------------------------------
    //     startField/endField/addBoost
    //-----------------------------------
    
    @Override
    public LuceneQuery startField(String fieldName, boolean mandatory) {
        final TermModifier tm = mandatory ? TermModifier.REQUIRED : TermModifier.NONE;
        final QueryModifier mod = defaultModifier.copy().setTermModifier(tm).end();
        return startField(fieldName, mod);
    }
    
    @Override
    public abstract LuceneQuery startField(String fieldName, QueryModifier modifier);
    
    @Override
    public abstract LuceneQuery endField();
    
    @Override
    public abstract LuceneQuery addBoost(double boostFactor);
    
    
    //-----------------------------------
    //     addUnescaped...
    //-----------------------------------
    
    @Override
    public abstract LuceneQuery addUnescaped(CharSequence value, boolean mandatory);
    
    @Override
    public abstract LuceneQuery addUnescapedField(String key, CharSequence value, boolean mandatory);
    

}
