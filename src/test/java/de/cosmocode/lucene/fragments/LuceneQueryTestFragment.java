package de.cosmocode.lucene.fragments;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import de.cosmocode.junit.UnitProvider;
import de.cosmocode.lucene.AbstractLuceneQueryTest;
import de.cosmocode.lucene.LuceneQuery;
import de.cosmocode.lucene.QueryModifier;

public abstract class LuceneQueryTestFragment implements UnitProvider<LuceneQuery> {
    
    public static final Directory DIRECTORY = new RAMDirectory();
    public static final Analyzer ANALYZER = new KeywordAnalyzer();
    
    protected static final Version USED_VERSION = Version.LUCENE_CURRENT;
   
    /** A helper for wildcard queries, different then WILDCARD2. */
    protected static final String WILDCARD1 = "arg";
    /** A helper for wildcard queries, different then WILDCARD1. */
    protected static final String WILDCARD2 = "hex";
    /** A helper for wildcard queries. yields different results then WILDCARD1 and WILDCARD2. */
    protected static final String WILDCARD3 = "foo";
    
    /** A helper for fuzzy queries. */
    protected static final String FUZZY1 = "truf";
    /** A helper for fuzzy queries. yields different results then FUZZY1 and FUZZY3. */
    protected static final String FUZZY2 = "arf1";
    /** A helper for fuzzy queries. yields different results then FUZZY1 AND FUZZY2. */
    protected static final String FUZZY3 = "fooba";
    
    protected static final String ARG1 = "arg1";
    protected static final boolean ARG2 = true;
    protected static final String ARG3 = "arg3";
    protected static final String ARG4 = "hexff00aa";
    protected static final String ARG5 = "hex559911";
    protected static final String ARG6 = "foobar";
    protected static final int ARG7 = 20;
    
    protected static final String DEFAULT_FIELD = "default_field";
    protected static final String FIELD1 = "field1";
    protected static final String FIELD2 = "field2";
    
    @Override
    public LuceneQuery unit() {
        final LuceneQuery unit = AbstractLuceneQueryTest.getInstance().unit();
        unit.addField("empty", "empty", QueryModifier.start().required().end());
        return unit;
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
        final String expectedString = unit().getQuery() + expected;
        final String actualString = actual.getQuery();
        
        final QueryParser parser = new QueryParser(USED_VERSION, DEFAULT_FIELD, ANALYZER);
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
            docExpected = search(queryExpected, 100);
            docActual = search(queryActual, 100);
        } catch (IOException e) {
            throw new IllegalStateException("low level IOException", e);
        }
        
        if (!docExpected.equals(docActual)) {
            System.out.println("Expected result: " + docExpected + ", actual result: " + docActual);
        }
        final String errorMsg = "Expected query: " + expectedString + ", but is: " + actualString;
        Assert.assertTrue(errorMsg, docExpected.equals(docActual));
    }
    
    /**
     * Searches for the given query and returns a list that has at most "max" items.
     * @param query the query to search for
     * @param max the maximum number of items in the returned list
     * @return a list with the found Documents
     * @throws IOException if lucene throws an exception 
     */
    protected List<String> search(final Query query, final int max) throws IOException {
        final List<String> docList = new LinkedList<String>();
        final IndexSearcher searcher = new IndexSearcher(DIRECTORY);
        final TopDocs docs = searcher.search(query, max);
        if (docs.totalHits > 0) {
            for (final ScoreDoc doc : docs.scoreDocs) {
                docList.add(searcher.doc(doc.doc).get("name"));
            }
        }
        return docList;
    }
    
    /**
     * <p> Creates a lucene document that store the given value
     * in a field named "key".
     * </p>
     * <p> This ensures that a search for "key:value"
     * returns the generated document.
     * If the key is DEFAULT_FIELD then a search for "value"
     * returns the generated document.
     * </p>
     * 
     * @param keyValues name and value of the fields, alternating (key, value, key, value, ...)
     * @return a new document with a field set to the given values
     */
    protected Document createDocument(final String... keyValues) {
        final Document doc = new Document();
        String key = null;
        for (String keyOrValue : keyValues) {
            if (key == null) {
                key = keyOrValue;
                continue;
            }
            
            final Field field = new Field(key, keyOrValue, Store.YES, Index.ANALYZED);
            doc.add(field);
            key = null;
        }
        doc.add(new Field("empty", "empty", Store.NO, Index.ANALYZED));
        return doc;
    }
    
    /**
     * Creates a new Lucene Index with some predefined field and arguments.
     * @throws IOException if creating the index failed
     */
    @Before
    public void createLuceneIndex() throws IOException {
        final IndexWriter writer = new IndexWriter(DIRECTORY, ANALYZER, MaxFieldLength.UNLIMITED);
        
        final String[] fields = new String[] {FIELD1, FIELD2, DEFAULT_FIELD};
        
        for (final String field : fields) {
            final String fieldName = field == DEFAULT_FIELD ? "" : field + "_";
            writer.addDocument(createDocument("name", fieldName + "arg1", field, ARG1));
            writer.addDocument(createDocument("name", fieldName + "arg2", field, Boolean.toString(ARG2)));
            writer.addDocument(createDocument("name", fieldName + "arg3", field, ARG3));
            writer.addDocument(createDocument("name", fieldName + "arg4", field, ARG4));
            writer.addDocument(createDocument("name", fieldName + "arg5", field, ARG5));
            writer.addDocument(createDocument("name", fieldName + "arg6", field, ARG6));
            writer.addDocument(createDocument("name", fieldName + "arg7", field, Integer.toString(ARG7)));
            writer.addDocument(createDocument(
                "name", fieldName + "arg1_arg2", field, ARG1, field, Boolean.toString(ARG2)));
            writer.addDocument(createDocument(
                "name", fieldName + "arg1_arg2_arg3", field, ARG1, field, Boolean.toString(ARG2), field, ARG3));
            writer.addDocument(createDocument(
                "name", fieldName + "arg4_arg5_arg6", field, ARG4, field, ARG5, field, ARG6));
            writer.addDocument(createDocument(
                    "name", fieldName + "all_args",
                    field, ARG1, field, Boolean.toString(ARG2), field, ARG3,
                    field, ARG4, field, ARG5, field, ARG6, field, Integer.toString(ARG7)));
        }
        
        writer.close();
    }
    
    /**
     * Cleans the index up again, to ensure an empty lucene index in the next test.
     * @throws IOException if cleaning the index failed
     */
    @After
    public void cleanLuceneIndex() throws IOException {
        final IndexWriter writer = new IndexWriter(DIRECTORY, ANALYZER, MaxFieldLength.UNLIMITED);
        writer.deleteAll();
        writer.close();
    }

}
