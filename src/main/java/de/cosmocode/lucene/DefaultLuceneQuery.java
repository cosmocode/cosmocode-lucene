package de.cosmocode.lucene;

import java.lang.reflect.Array;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * A default implementation of a LuceneQuery.
 * This implementation is not threadsafe.
 * </p>
 * 
 * @author Oliver Lorenz
 */
public final class DefaultLuceneQuery extends AbstractLuceneQuery implements LuceneQuery {
    
    public static final String ERR_BOOST_OUT_OF_BOUNDS = 
        "boostFactor must be greater than 0 and less than 10.000.000 (10 millions)";

    
    private final StringBuilder queryArguments;
    
    public DefaultLuceneQuery() {
        this.queryArguments = new StringBuilder();
    }

    // TODO is an empty String on getQuery() ok? should it throw an IllegalStateException instead?
    @Override
    public String getQuery() {
        return this.queryArguments.toString();
    }
    

    /* ---------------------------
     *     addArgument-methods
     */
    
    /**
     * search for input wildcarded (wildcard is appended at the end). 
     * the original input is added, too, because e.g. adidas* doesn't match "adidas" on text-fields
     * @param value the String value to add wildcarded
     */
    private void addWildcarded(final String value) {
        queryArguments.
            append("\"").append(LuceneHelper.escapeQuotes(value)).append("\"^2").
            append(" ").append(LuceneHelper.escapeAll(value)).append("*");
    }
    
    private void addFuzzy(final String value, final double fuzzyness) {
        queryArguments.
            append(LuceneHelper.escapeAll(value)).
            append("~").append(fuzzyness);
    }
    
    private void addWildcardedFuzzy(final String value, final double fuzzyness) {
        queryArguments.
            append("\"").append(LuceneHelper.escapeQuotes(value)).append("\"^2").
            append(" ").append(LuceneHelper.escapeAll(value)).append("*").
            append(" ").append(LuceneHelper.escapeAll(value)).
            append("~").append(fuzzyness);
    }
    
    private void addSplitted(final String value, final QueryModifier modifier) {
        queryArguments.append(" (");
        for (final String token : value.split(" ")) {
            this.addArgument(token, modifier.copy().dontSplit().end());
        }
        queryArguments.append(")^0.5 ");
    }
    
    
    @Override
    public DefaultLuceneQuery addFuzzyArgument(final String value, 
            final QueryModifier modifier, final double fuzzyness) {
        return this.addArgument(value, modifier.copy().setFuzzyness(fuzzyness).end());
    }
    
    
    @Override
    public DefaultLuceneQuery addArgument(final String value, final QueryModifier modifier) {
        if (StringUtils.isNotBlank(value) && modifier != null) {
            queryArguments.append(modifier.getTermPrefix());
            
            queryArguments.append("(");
            if (modifier.isWildcarded() && modifier.isFuzzyEnabled()) {
                addWildcardedFuzzy(value, modifier.getFuzzyness());
            } else if (modifier.isWildcarded()) {
                addWildcarded(value);
            } else if (modifier.isFuzzyEnabled()) {
                addFuzzy(value, modifier.getFuzzyness());
            } else {
                queryArguments.append(LuceneHelper.escapeAll(value));
            }
            
            if (modifier.isSplit() && (value.contains(" "))) {
                addSplitted(value, modifier);
            }
            
            queryArguments.append(") ");
        }
        
        return this;
    }
    

    /* ---------------------------
     *     addArgumentAs...-methods
     */
    
    private void beforeIteration(final QueryModifier modifier) {
        queryArguments.append(modifier.getTermPrefix());
        queryArguments.append("(");
    }
    
    private void afterIteration() {
        if (queryArguments.charAt(queryArguments.length() - 1) == '(') {
            // if the just opened bracket is still the last character, then revert it
            queryArguments.setLength(queryArguments.length() - 1);
        } else {
            queryArguments.append(") ");
        }
    }
    
    @Override
    public DefaultLuceneQuery addArgumentAsCollection(final Collection<?> values, final QueryModifier modifier) {
        if (values == null || values.size() == 0) return this;
        
        beforeIteration(modifier);

        // add items
        final QueryModifier valueModifier = modifier.getFieldValueModifier();
        for (Object val : values) {
            addArgument(val, valueModifier);
        }
        
        afterIteration();
        
        return this;
    }
    
    
    @Override
    public <K> DefaultLuceneQuery addArgumentAsArray(final K[] values, final QueryModifier modifier) {
        // quick return
        if (values == null || values.length == 0) return this;

        beforeIteration(modifier);
        
        // add items
        final QueryModifier valueModifier = modifier.getFieldValueModifier();
        for (K val : values) {
            addArgument(val, valueModifier);
        }
        
        afterIteration();
        
        return this;
    }
    
    @Override
    protected DefaultLuceneQuery addArgumentAsArray(Object values, final QueryModifier modifier) {
        if (values == null) return this;
        
        if (values.getClass().isArray() && Array.getLength(values) > 0) {
            final int arrayLength = Array.getLength(values);
            
            beforeIteration(modifier);
            
            // add all items
            final QueryModifier valueModifier = modifier.getFieldValueModifier();
            for (int i = 0; i < arrayLength; i++) {
                addArgument(Array.get(values, i), valueModifier);
            }
            
            afterIteration();
        }
        
        return this;
    }
    
    
    @Override
    public DefaultLuceneQuery addSubquery(final LuceneQuery value, final QueryModifier modifier) {
        if (value == null) return this;

        final CharSequence subQuery = value.getQuery();
        if (subQuery.length() == 0) return this;
        
        queryArguments.append(modifier.getTermPrefix());
        queryArguments.append("(").append(subQuery).append(") ");
        
        return this;
    }
    
    
    
    /*---------------------------
     *     addUnescaped-methods
     */
    
    
    @Override
    public DefaultLuceneQuery addUnescapedField(final String key, final CharSequence value, final boolean mandatory) {
        if (key == null || value == null || value.length() == 0) return this;
        
        if (mandatory) queryArguments.append("+");
        queryArguments.append(key).append(":").append(value).append(" ");
        
        return this;
    }
    
    
    @Override
    public DefaultLuceneQuery addUnescaped(final CharSequence value, final boolean mandatory) {
        if (value == null || value.length() == 0) return this;
        
        if (mandatory) queryArguments.append("+");
        queryArguments.append(value).append(" ");
        
        return this;
    }
    

    /*---------------------------
     *  helper methods
     */
    
    @Override
    public DefaultLuceneQuery startField(final String fieldName, final boolean mandatory) {
        if (StringUtils.isBlank(fieldName)) return this;
        
        if (mandatory) queryArguments.append("+");
        queryArguments.append(fieldName).append(":(");
        
        return this;
    }
    
    
    @Override
    public DefaultLuceneQuery startField(final String fieldName, final QueryModifier modifier) {
        if (StringUtils.isBlank(fieldName)) return this;
        
        queryArguments.append(modifier.getTermPrefix());
        queryArguments.append(fieldName).append(":(");
        
        return this;
    }
    
    
    @Override
    public DefaultLuceneQuery endField() {
        if (queryArguments.charAt(queryArguments.length() - 1) == '(') {
            // add an empty string, if the field was ended right after it was started
            queryArguments.append("\"\"");
        }
        queryArguments.append(") ");
        
        return this;
    }
    
    
    @Override
    public DefaultLuceneQuery addBoost(final double boostFactor) {
        if (boostFactor <= 0.0 || boostFactor >= 10000000.0)
            throw new IllegalArgumentException(ERR_BOOST_OUT_OF_BOUNDS);
        
        // optimization: only add boost factor if != 1
        if (boostFactor != 1.0) {
            final double rounded = ((int) (boostFactor * 100.0)) / 100.0;
            this.queryArguments.append("^").append(rounded).append(" ");
        }

        return this;
    }

}
