package de.cosmocode.lucene;

import java.util.Collection;

import com.google.common.collect.ForwardingObject;

/**
 * <p>
 * An abstract implementation of a LuceneQuery that delegates
 * every method to an abstract {@link #delegate()} method.
 * </p>
 * 
 * @author Oliver Lorenz
 */
public abstract class ForwardingLuceneQuery extends ForwardingObject implements LuceneQuery {

    @Override
    protected abstract LuceneQuery delegate();
    

    @Override
    public LuceneQuery addArgument(Collection<?> values, boolean mandatory) {
        delegate().addArgument(values, mandatory);
        return this;
    }

    @Override
    public LuceneQuery addArgument(Collection<?> values, QueryModifier modifier) {
        delegate().addArgument(values, modifier);
        return this;
    }

    @Override
    public LuceneQuery addArgument(Collection<?> values) {
        delegate().addArgument(values);
        return this;
    }

    @Override
    public LuceneQuery addArgument(double[] values, QueryModifier modifier) {
        delegate().addArgument(values, modifier);
        return this;
    }

    @Override
    public LuceneQuery addArgument(double[] values) {
        delegate().addArgument(values);
        return this;
    }

    @Override
    public LuceneQuery addArgument(int[] values, QueryModifier modifier) {
        delegate().addArgument(values, modifier);
        return this;
    }

    @Override
    public LuceneQuery addArgument(int[] values) {
        delegate().addArgument(values);
        return this;
    }

    @Override
    public <K> LuceneQuery addArgument(K[] values, boolean mandatory) {
        delegate().addArgument(values, mandatory);
        return this;
    }

    @Override
    public <K> LuceneQuery addArgument(K[] values, QueryModifier modifier) {
        delegate().addArgument(values, modifier);
        return this;
    }

    @Override
    public <K> LuceneQuery addArgument(K[] values) {
        delegate().addArgument(values);
        return this;
    }

    @Override
    public LuceneQuery addArgument(String value, boolean mandatory) {
        delegate().addArgument(value, mandatory);
        return this;
    }

    @Override
    public LuceneQuery addArgument(String value, QueryModifier modifier) {
        delegate().addArgument(value, modifier);
        return this;
    }

    @Override
    public LuceneQuery addArgument(String value) {
        delegate().addArgument(value);
        return this;
    }

    @Override
    public <K> LuceneQuery addArgumentAsArray(K[] values, QueryModifier modifier) {
        delegate().addArgumentAsArray(values, modifier);
        return this;
    }

    @Override
    public <K> LuceneQuery addArgumentAsArray(K[] values) {
        delegate().addArgumentAsArray(values);
        return this;
    }

    @Override
    public LuceneQuery addArgumentAsCollection(Collection<?> values,
            QueryModifier modifier) {
        delegate().addArgumentAsCollection(values, modifier);
        return this;
    }

    @Override
    public LuceneQuery addArgumentAsCollection(Collection<?> values) {
        delegate().addArgumentAsCollection(values);
        return this;
    }

    @Override
    public LuceneQuery addBoost(double boostFactor) {
        delegate().addBoost(boostFactor);
        return this;
    }

    @Override
    public LuceneQuery addField(String key, boolean mandatoryKey,
            Collection<?> value, boolean mandatoryValue, double boostFactor) {
        delegate().addField(key, mandatoryKey, value, mandatoryValue, boostFactor);
        return this;
    }

    @Override
    public LuceneQuery addField(String key, boolean mandatoryKey,
            Collection<?> value, boolean mandatoryValue) {
        delegate().addField(key, mandatoryKey, value, mandatoryValue);
        return this;
    }

    @Override
    public LuceneQuery addField(String key, Collection<?> value,
            QueryModifier modifier) {
        delegate().addField(key, value, modifier);
        return this;
    }

    @Override
    public LuceneQuery addField(String key, Collection<?> value) {
        delegate().addField(key, value);
        return this;
    }

    @Override
    public <K> LuceneQuery addField(String key, K[] value, QueryModifier modifier) {
        delegate().addField(key, value, modifier);
        return this;
    }

    @Override
    public <K> LuceneQuery addField(String key, K[] value) {
        delegate().addField(key, value);
        return this;
    }

    @Override
    public LuceneQuery addField(String key, String value, boolean mandatoryKey, 
            double boostFactor) {
        delegate().addField(key, value, mandatoryKey, boostFactor);
        return this;
    }

    @Override
    public LuceneQuery addField(String key, String value, boolean mandatoryKey) {
        delegate().addField(key, value, mandatoryKey);
        return this;
    }

    @Override
    public LuceneQuery addField(String key, String value, QueryModifier modifier) {
        delegate().addField(key, value, modifier);
        return this;
    }

    @Override
    public LuceneQuery addField(String key, String value) {
        delegate().addField(key, value);
        return this;
    }

    @Override
    public <K> LuceneQuery addFieldAsArray(String key, K[] value, QueryModifier modifier) {
        delegate().addFieldAsArray(key, value, modifier);
        return this;
    }

    @Override
    public <K> LuceneQuery addFieldAsArray(String key, K[] value) {
        delegate().addFieldAsArray(key, value);
        return this;
    }

    @Override
    public LuceneQuery addFieldAsCollection(String key, Collection<?> value,
            QueryModifier modifier, double boost) {
        delegate().addFieldAsCollection(key, value, modifier, boost);
        return this;
    }

    @Override
    public LuceneQuery addFieldAsCollection(String key, Collection<?> value,
            QueryModifier modifier) {
        delegate().addFieldAsCollection(key, value, modifier);
        return this;
    }

    @Override
    public LuceneQuery addFieldAsCollection(String key, Collection<?> value) {
        delegate().addFieldAsCollection(key, value);
        return this;
    }

    @Override
    public LuceneQuery addFuzzyArgument(String value, boolean mandatory, double fuzzyness) {
        delegate().addFuzzyArgument(value, mandatory, fuzzyness);
        return this;
    }

    @Override
    public LuceneQuery addFuzzyArgument(String value, boolean mandatory) {
        delegate().addFuzzyArgument(value, mandatory);
        return this;
    }

    @Override
    public LuceneQuery addFuzzyArgument(String value) {
        delegate().addFuzzyArgument(value);
        return this;
    }

    @Override
    public LuceneQuery addFuzzyField(String key, String value, boolean mandatoryKey, double fuzzyness) {
        delegate().addFuzzyField(key, value, mandatoryKey, fuzzyness);
        return this;
    }

    @Override
    public LuceneQuery addFuzzyField(String key, String value, boolean mandatoryKey) {
        delegate().addFuzzyField(key, value, mandatoryKey);
        return this;
    }

    @Override
    public LuceneQuery addFuzzyField(String key, String value) {
        delegate().addFuzzyField(key, value);
        return this;
    }

    @Override
    public LuceneQuery addSubquery(LuceneQuery value, boolean mandatory) {
        delegate().addSubquery(value, mandatory);
        return this;
    }

    @Override
    public LuceneQuery addSubquery(LuceneQuery value, QueryModifier modifiers) {
        delegate().addSubquery(value, modifiers);
        return this;
    }

    @Override
    public LuceneQuery addSubquery(LuceneQuery value) {
        delegate().addSubquery(value);
        return this;
    }

    @Override
    public LuceneQuery addUnescaped(CharSequence value, boolean mandatory) {
        delegate().addUnescaped(value, mandatory);
        return this;
    }

    @Override
    public LuceneQuery addUnescapedField(String key, CharSequence value,
            boolean mandatory) {
        delegate().addUnescapedField(key, value, mandatory);
        return this;
    }

    @Override
    public LuceneQuery endField() {
        delegate().endField();
        return this;
    }

    @Override
    public LuceneQuery startField(String fieldName, boolean mandatory) {
        delegate().startField(fieldName, mandatory);
        return this;
    }

    @Override
    public LuceneQuery startField(String fieldName, QueryModifier modifier) {
        delegate().startField(fieldName, modifier);
        return this;
    }
    

    @Override
    public QueryModifier getModifier() {
        return delegate().getModifier();
    }

    @Override
    public String getQuery() {
        return delegate().getQuery();
    }

    @Override
    public boolean isWildCarded() {
        return delegate().isWildCarded();
    }

    @Override
    public void setModifier(QueryModifier mod) {
        delegate().setModifier(mod);
    }

    @Override
    public void setWildCarded(boolean wildCarded) {
        final QueryModifier newDefaultMod = getModifier().copy().setWildcarded(wildCarded).end();
        delegate().setModifier(newDefaultMod);
    }
    
    @Override
    public boolean lastSuccessful() {
        return delegate().lastSuccessful();
    }

}
