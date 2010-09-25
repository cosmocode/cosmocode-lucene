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

package de.cosmocode.lucene.fragments.query;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cosmocode.junit.UnitProvider;
import de.cosmocode.lucene.IndexHelper;
import de.cosmocode.lucene.LuceneQuery;
import de.cosmocode.lucene.LuceneQueryTest;

/**
 * <p> This is an abstract Test class that implements no test itself.
 * It can only be executed by an {@link LuceneQueryTest}.
 * It sets up a dummy Lucene search directory in which the resulting queries
 * can be tested with the method {@link #assertEquals(String, LuceneQuery)}.
 * </p>
 * 
 * @author Oliver Lorenz
 */
abstract class AbstractLuceneQueryTestFragment implements UnitProvider<LuceneQuery> {
    
    /** A helper for wildcard queries, different than WILDCARD2. */
    public static final String WILDCARD1 = IndexHelper.WILDCARD1;
    /** A helper for wildcard queries, different than WILDCARD1. */
    public static final String WILDCARD2 = IndexHelper.WILDCARD2;
    /** A helper for wildcard queries. yields different results than WILDCARD1 and WILDCARD2. */
    public static final String WILDCARD3 = IndexHelper.WILDCARD3;
    
    /** A helper for fuzzy queries.
     * Also yields a result for wildcard queries, so it can be used for wildcard + fuzzy. */
    public static final String FUZZY1 = IndexHelper.FUZZY1;
    /** A helper for fuzzy queries. yields different results than FUZZY1 and FUZZY3. */
    public static final String FUZZY2 = IndexHelper.FUZZY2;
    /** A helper for fuzzy queries. yields different results than FUZZY1 AND FUZZY2. */
    public static final String FUZZY3 = IndexHelper.FUZZY3;
    
    public static final String ARG1 = IndexHelper.ARG1;
    public static final boolean ARG2 = IndexHelper.ARG2;
    public static final String ARG3 = IndexHelper.ARG3;
    
    public static final String FIELD1 = IndexHelper.FIELD1;
    public static final String FIELD2 = IndexHelper.FIELD2;
    
    
    private static final Logger LOG = LoggerFactory.getLogger(AbstractLuceneQueryTestFragment.class);
    
    
    @Override
    public LuceneQuery unit() {
        return LuceneQueryTest.unitProvider().unit();
    }
    
    /**
     * <p> Asserts that two queries return the same result from lucene.
     * The first query is a hand-made control query, the second is the 
     * LuceneQuery that should be tested.
     * </p>
     * 
     * @param expected the control Query
     * @param actual the generated LuceneQuery
     */
    protected void assertEquals(final String expected, final LuceneQuery actual) {
        // +empty:empty catches every document in the lucene index
        final String expectedString = "+empty:empty " + expected;
        final String actualString = "+empty:empty " + actual.getQuery();
        
        final QueryParser parser = IndexHelper.createQueryParser();
        final Query queryExpected;
        final Query queryActual;
        
        try {
            queryExpected = parser.parse(expectedString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Expected query is illegal", e);
        }
        try {
            queryActual = parser.parse(actualString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Actual query is illegal", e);
        }
        
        final List<?> docExpected;
        final List<?> docActual;
        try {
            docExpected = search(queryExpected, 2000);
            docActual = search(queryActual, 2000);
        } catch (IOException e) {
            throw new IllegalStateException("low level IOException", e);
        }
        
        if (docExpected.equals(docActual)) {
            if (docExpected.size() == 0) {
                LOG.warn("Expected 0 results for {} !!", queryExpected);
                LOG.info("Original query was {}", expectedString);
            }
        } else {
            LOG.debug("Original queries:");
            LOG.debug("\texpected={}", expectedString);
            LOG.debug("\t  actual={}", actualString);
            LOG.debug("Parsed queries:");
            LOG.debug("\texpected={}", queryExpected);
            LOG.debug("\t  actual={}", queryActual);
            LOG.debug("Expected result: {}", docExpected);
            LOG.debug("  Actual result: {}", docActual);
            LOG.debug("End");
        }
        final String errorMsg = "Expected query: " + expectedString + ", but is: " + actualString;
        Assert.assertTrue(errorMsg, docExpected.equals(docActual));
    }
    
    /**
     * Searches for the given query and returns a list that has at most "max" items.
     * @param query the query to search for
     * @param max the maximum number of items in the returned list
     * @return a list with the names of the found documents
     * @throws IOException if lucene throws an exception 
     */
    protected List<String> search(final Query query, final int max) throws IOException {
        final List<String> docList = new LinkedList<String>();
        final IndexSearcher searcher = new IndexSearcher(IndexHelper.DIRECTORY);
        final TopDocs docs = searcher.search(query, max);
        if (docs.totalHits > 0) {
            for (final ScoreDoc doc : docs.scoreDocs) {
                docList.add(searcher.doc(doc.doc).get("name"));
            }
        }
        return docList;
    }

}
