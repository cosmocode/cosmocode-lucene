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
 * NOT FINISHED.
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
    
    private Query createSingleQuery(String value) {
        return new TermQuery(new Term(currentField, value));
    }
    
    private Query createMultiQuery(Iterable<?> values, QueryModifier modifier) {
        final BooleanQuery multiQuery = new BooleanQuery();
        final QueryModifier valueModifier = modifier.getMultiValueModifier();
        final Occur occur = TermModifierToOccur.INSTANCE.apply(valueModifier.getTermModifier());
        
        for (Object value : values) {
            if (value != null) {
                multiQuery.add(createQuery(value, valueModifier), occur);
            }
        }
        
        return multiQuery;
    }
    
    private Query createMultiQueryFromArray(Object values, QueryModifier modifier) {
        final BooleanQuery multiQuery = new BooleanQuery();
        final QueryModifier valueModifier = modifier.getMultiValueModifier();
        final Occur occur = TermModifierToOccur.INSTANCE.apply(valueModifier.getTermModifier());
        final int arrayLength = Array.getLength(values);
        
        // add all items
        for (int i = 0; i < arrayLength; i++) {
            final Object value = Array.get(values, i);
            if (value != null) {
                multiQuery.add(createQuery(value, valueModifier), occur);
            }
        }

        return multiQuery;
    }
    
    private Query createQuery(Object value, QueryModifier modifier) {
        Preconditions.checkNotNull(value, "Value");
        if (value instanceof String) {
            return createSingleQuery(value.toString());
        } else if (value instanceof Iterable<?>) {
            return createMultiQuery(Iterable.class.cast(value), modifier);
        } else if (value.getClass().isArray()) { 
            return createMultiQueryFromArray(value, modifier);
        } else {
            return createSingleQuery(value.toString());
        }
    }

    @Override
    public LuceneQuery addArgument(String value, QueryModifier modifier) {
        Preconditions.checkState(value != null, "Value must not be null");
        
        final Occur occur = TermModifierToOccur.INSTANCE.apply(modifier.getTermModifier());
        final Query query = createSingleQuery(value);
        addQueryToTopQuery(query, occur);
        
        return this;
    }

    @Override
    public <K> LuceneQuery addArgumentAsArray(K[] values, QueryModifier modifier) {
        Preconditions.checkState(values != null, "Values must not be null");
        
        final Occur occur = TermModifierToOccur.INSTANCE.apply(modifier.getTermModifier());
        final Query query = createMultiQueryFromArray(values, modifier);
        addQueryToTopQuery(query, occur);
        
        return this;
    }

    @Override
    protected LuceneQuery addArgumentAsArray(Object values, QueryModifier modifier) {
        Preconditions.checkState(values != null, "Values must not be null");
        
        final Occur occur = TermModifierToOccur.INSTANCE.apply(modifier.getTermModifier());
        final Query query = createMultiQueryFromArray(values, modifier);
        addQueryToTopQuery(query, occur);
        
        return this;
    }

    @Override
    public LuceneQuery addArgumentAsCollection(Collection<?> values, QueryModifier modifier) {
        Preconditions.checkState(values != null, "Values must not be null");
        
        final Occur occur = TermModifierToOccur.INSTANCE.apply(modifier.getTermModifier());
        final Query query = createMultiQuery(values, modifier);
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
        Preconditions.checkState(this.topQuery.getClauses().length > 0, ERR_EMPTY_QUERY);
        return this.topQuery.toString();
    }

    @Override
    public LuceneQuery startField(String fieldName, QueryModifier modifier) {
        this.currentField = fieldName;
        return this;
    }

    @Override
    public LuceneQuery endField() {
        this.currentField = defaultField;
        return this;
    }

}
