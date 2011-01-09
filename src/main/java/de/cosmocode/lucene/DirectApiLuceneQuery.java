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

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;

final class DirectApiLuceneQuery extends AbstractLuceneQuery {
    
    private final String defaultField;
    private final Analyzer analyzer;
    private final BooleanQuery topQuery;
    
    public DirectApiLuceneQuery(String defaultField, Analyzer analyzer) {
        this.defaultField = defaultField;
        this.analyzer = analyzer;
        this.topQuery = new BooleanQuery();
    }
    
    private static Occur fromTermModifierToOccur(TermModifier termModifier) {
        switch (termModifier) {
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
                throw new IllegalStateException("Unknown term modifier " + termModifier);
            }
        }
    }

    @Override
    public LuceneQuery addArgument(String value, QueryModifier modifier) {
        // TODO handle QueryModifier completely
        // TODO use analyzer to construct tokens and add them to the query
        final Query valueQuery = new TermQuery(new Term(defaultField, value));
        final Occur occur = fromTermModifierToOccur(modifier.getTermModifier());
        this.topQuery.add(valueQuery, occur);
        return this;
    }

    @Override
    public <K> LuceneQuery addArgumentAsArray(K[] values, QueryModifier modifier) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected LuceneQuery addArgumentAsArray(Object values,
            QueryModifier modifier) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LuceneQuery addArgumentAsCollection(Collection<?> values,
            QueryModifier modifier) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LuceneQuery addBoost(double boostFactor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LuceneQuery addRange(double from, double to, QueryModifier mod) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LuceneQuery addRange(int from, int to, QueryModifier mod) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LuceneQuery addRange(String from, String to, QueryModifier mod) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LuceneQuery addSubquery(LuceneQuery value, QueryModifier modifiers) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LuceneQuery addUnescaped(CharSequence value, boolean mandatory) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LuceneQuery addUnescapedField(String key, CharSequence value,
            boolean mandatory) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LuceneQuery endField() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getQuery() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LuceneQuery startField(String fieldName, QueryModifier modifier) {
        // TODO Auto-generated method stub
        return null;
    }

}
