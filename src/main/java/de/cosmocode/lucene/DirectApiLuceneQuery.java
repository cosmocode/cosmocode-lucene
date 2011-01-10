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

import java.lang.reflect.Array;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeQuery;
import org.apache.lucene.search.TermQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * {@link LuceneQuery} implementation that uses the Lucene API directly.
 * 
 * @since 1.3
 * @author Oliver Lorenz
 */
final class DirectApiLuceneQuery extends AbstractLuceneQuery {
    
    private static final Logger LOG = LoggerFactory.getLogger(DirectApiLuceneQuery.class);
    
    private final String defaultField;
    private final Analyzer analyzer;
    private final BooleanQuery topQuery;
    
    private Query lastQuery;
    private String currentField;
    
    public DirectApiLuceneQuery(String defaultField, Analyzer analyzer) {
        this.defaultField = defaultField;
        this.analyzer = analyzer;
        this.topQuery = new BooleanQuery();
        this.currentField = defaultField;
    }
    
    /* private methods that do the query conversion */

    private void addQueryToTopQuery(final Query query, final Occur occur) {
        this.topQuery.add(query, occur);
        setLastSuccessful(true);
        lastQuery = query;
    }
    
    private Query createSingleQuery(String value, QueryModifier modifier) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        
        // TODO handle QueryModifier completely
        // TODO use analyzer to construct tokens and add them to the query
        return new TermQuery(new Term(currentField, value));
    }
    
    private Query createMultiQuery(Iterable<?> values, QueryModifier modifier) {
        if (values == null) {
            return null;
        }
        
        final BooleanQuery multiQuery = new BooleanQuery();
        final QueryModifier valueModifier = modifier.getMultiValueModifier();
        final Occur occur = TermModifierToOccur.INSTANCE.apply(valueModifier.getTermModifier());
        
        for (Object value : values) {
            final Query subQuery = createQuery(value, valueModifier);
            if (subQuery == null) {
                continue;
            }
            multiQuery.add(subQuery, occur);
        }
        
        if (multiQuery.clauses().size() == 0) {
            return null;
        } else {
            return multiQuery;
        }
    }
    
    /**
     * Returns a {@link Query} from a typed array.
     * If no valid query could be constructed from the given array then this method returns null.
     * 
     * @param values the primitive array, as an object
     * @param modifier the QueryModifier to apply to the query
     * @return a {@link Query} that searches for the given values or null if no valid query could be constructed
     */
    private <K> Query createMultiQuery(K[] values, QueryModifier modifier) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        final BooleanQuery multiQuery = new BooleanQuery();
        final QueryModifier valueModifier = modifier.getMultiValueModifier();
        final Occur occur = TermModifierToOccur.INSTANCE.apply(valueModifier.getTermModifier());
        
        for (Object value : values) {
            final Query subQuery = createQuery(value, valueModifier);
            if (subQuery == null) {
                continue;
            }
            multiQuery.add(subQuery, occur);
        }
        
        return multiQuery;
    }
    
    /**
     * Returns a {@link Query} from a primitive array, which is given as an Object.
     * The values in the primitive array are accessed by reflection.
     * If the given array is empty or the object is null then this method returns null.
     * 
     * @param values the primitive array, as an object
     * @param modifier the QueryModifier to apply to the query
     * @return a {@link Query} that searches for the given values or null if values are empty
     */
    private Query createMultiQueryFromPrimitiveArray(Object values, QueryModifier modifier) {
        if (values == null || Array.getLength(values) == 0) {
            return null;
        }
        
        final BooleanQuery multiQuery = new BooleanQuery();
        final QueryModifier valueModifier = modifier.getMultiValueModifier();
        final Occur occur = TermModifierToOccur.INSTANCE.apply(valueModifier.getTermModifier());
        final int arrayLength = Array.getLength(values);
        
        // add all items
        for (int i = 0; i < arrayLength; i++) {
            // we can construct a single query without null check,
            // because the type is primitive (long, byte, ...)
            final Object value = Array.get(values, i);
            final Query subQuery = createSingleQuery(value.toString(), valueModifier);
            multiQuery.add(subQuery, occur);
        }

        return multiQuery;
    }
    
    private Query createQuery(Object value, QueryModifier modifier) {
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return createSingleQuery(value.toString(), modifier);
        } else if (value instanceof Iterable<?>) {
            return createMultiQuery(Iterable.class.cast(value), modifier);
        } else if (value.getClass().isArray()) {
            if (Array.getLength(value) == 0) {
                return null;
            } else if (Array.get(value, 0).getClass().isPrimitive()) {
                return createMultiQueryFromPrimitiveArray(value, modifier);
            } else {
                return createMultiQuery((Object[]) value, modifier);
            }
        } else {
            return createSingleQuery(value.toString(), modifier);
        }
    }

    @Override
    public LuceneQuery addArgument(String value, QueryModifier modifier) {
        Preconditions.checkState(value != null, "Value must not be null");
        
        final Occur occur = TermModifierToOccur.INSTANCE.apply(modifier.getTermModifier());
        final Query query = createSingleQuery(value, modifier);
        Preconditions.checkState(query != null, "Constructed an empty query from %s", value);
        addQueryToTopQuery(query, occur);
        
        return this;
    }

    @Override
    public <K> LuceneQuery addArgumentAsArray(K[] values, QueryModifier modifier) {
        Preconditions.checkState(values != null, "Values must not be null");
        
        final Occur occur = TermModifierToOccur.INSTANCE.apply(modifier.getTermModifier());
        final Query query = createMultiQuery(values, modifier);
        Preconditions.checkState(query != null, "Constructed an empty query from %s", values);
        addQueryToTopQuery(query, occur);
        
        return this;
    }

    @Override
    protected LuceneQuery addArgumentAsArray(Object values, QueryModifier modifier) {
        Preconditions.checkState(values != null, "Values must not be null");
        
        final Occur occur = TermModifierToOccur.INSTANCE.apply(modifier.getTermModifier());
        final Query query = createMultiQueryFromPrimitiveArray(values, modifier);
        Preconditions.checkState(query != null, "Constructed an empty query from %s", values);
        addQueryToTopQuery(query, occur);
        
        return this;
    }

    @Override
    public LuceneQuery addArgumentAsCollection(Collection<?> values, QueryModifier modifier) {
        Preconditions.checkState(values != null, "Values must not be null");
        
        final Occur occur = TermModifierToOccur.INSTANCE.apply(modifier.getTermModifier());
        final Query query = createMultiQuery(values, modifier);
        Preconditions.checkState(query != null, "Constructed an empty query from %s", values);
        addQueryToTopQuery(query, occur);
        
        return this;
    }

    @Override
    public LuceneQuery addBoost(double boostFactor) {
        if (boostFactor <= 0.0 || boostFactor >= 10000000.0)
            throw new IllegalArgumentException(ERR_BOOST_OUT_OF_BOUNDS);
        
        // only add boost factor if != 1 (optimization) and last action was successful
        if (boostFactor != 1.0 && lastSuccessful()) {
            lastQuery.setBoost((float) boostFactor);
        }
        
        return this;
    }

    @Override
    public LuceneQuery addRange(double from, double to, QueryModifier modifier) {
        return addRange(Double.toString(from), Double.toString(to), modifier);
    }

    @Override
    public LuceneQuery addRange(int from, int to, QueryModifier modifier) {
        return addRange(Integer.toString(from), Integer.toString(to), modifier);
    }

    @Override
    public LuceneQuery addRange(String from, String to, QueryModifier modifier) {
        final Occur occur = TermModifierToOccur.INSTANCE.apply(modifier.getTermModifier());
        final Term lowerTerm = new Term(currentField, from);
        final Term upperTerm = new Term(currentField, to);
        final RangeQuery query = new RangeQuery(lowerTerm, upperTerm, true);
        addQueryToTopQuery(query, occur);
        
        return this;
    }

    @Override
    public LuceneQuery addSubquery(LuceneQuery value, QueryModifier modifiers) {
        final Occur occurance = TermModifierToOccur.INSTANCE.apply(modifiers.getTermModifier());
        final QueryParser parser = new QueryParser(defaultField, analyzer);
        
        try {
            this.topQuery.add(parser.parse(value.getQuery()), occurance);
        } catch (ParseException e) {
            setLastSuccessful(false);
            LOG.error("Could not parse {}", value);
            throw new IllegalArgumentException("Could not parse " + value, e);
        }
        
        setLastSuccessful(true);
        return this;
    }

    @Override
    public LuceneQuery addUnescaped(CharSequence value, boolean mandatory) {
        if (value == null || value.length() == 0) {
            setLastSuccessful(false);
            return this;
        }
        
        final Occur occurance = mandatory ? Occur.MUST : Occur.SHOULD;
        final QueryParser parser = new QueryParser(defaultField, analyzer);
        
        try {
            this.topQuery.add(parser.parse(value.toString()), occurance);
        } catch (ParseException e) {
            setLastSuccessful(false);
            LOG.error("Could not parse {}", value);
            throw new IllegalArgumentException("Could not parse " + value, e);
        }
        
        setLastSuccessful(true);
        return this;
    }

    @Override
    public String getQuery() {
        Preconditions.checkState(this.topQuery.clauses().size() > 0, ERR_EMPTY_QUERY);
        return this.topQuery.toString();
    }

    @Override
    public LuceneQuery startField(String fieldName, QueryModifier modifier) {
        if (StringUtils.isBlank(fieldName)) {
            setLastSuccessful(false);
            return this;
        }
        
        this.currentField = fieldName;
        setLastSuccessful(true);
        return this;
    }

    @Override
    public LuceneQuery endField() {
        this.currentField = defaultField;
        return this;
    }

}
