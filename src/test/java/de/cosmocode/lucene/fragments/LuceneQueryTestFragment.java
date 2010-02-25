package de.cosmocode.lucene.fragments;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
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
import de.cosmocode.lucene.TermModifier;

public abstract class LuceneQueryTestFragment implements UnitProvider<LuceneQuery> {
    
    public static final Directory DIRECTORY = new RAMDirectory();
    public static final Analyzer ANALYZER = new SimpleAnalyzer();
    
    protected static final Version USED_VERSION = Version.LUCENE_CURRENT;
    
    protected static final String ARG1 = "arg1";
    protected static final boolean ARG2 = true;
    protected static final String ARG3 = "arg3";
    
    protected static final String DEFAULT_FIELD = "default_field";
    protected static final String FIELD1 = "field1";
    protected static final String FIELD2 = "field2";
    
    @Override
    public LuceneQuery unit() {
        final LuceneQuery unit = AbstractLuceneQueryTest.getInstance().unit();
        unit.addField("empty", "empty", QueryModifier.start().setTermModifier(TermModifier.REQUIRED).end());
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
            docExpected = search(queryExpected, 15);
            docActual = search(queryActual, 15);
        } catch (IOException e) {
            throw new IllegalStateException("low level IOException", e);
        }
        
        System.out.println("Expected: " + docExpected + ", actual: " + docActual);
        Assert.assertEquals(docExpected, docActual);
    }
    
    /**
     * Searches for the given query and returns a list that has at most "max" items.
     * @param query the query to search for
     * @param max the maximum number of items in the returned list
     * @return a list with the found Documents
     * @throws IOException if lucene throws an exception 
     */
    protected List<Integer> search(final Query query, final int max) throws IOException {
        final List<Integer> docList = new LinkedList<Integer>();
        final IndexSearcher searcher = new IndexSearcher(DIRECTORY);
        final TopDocs docs = searcher.search(query, max);
        if (docs.totalHits > 0) {
            for (final ScoreDoc doc : docs.scoreDocs) {
                docList.add(doc.doc);
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
        
        // 0-4
        writer.addDocument(createDocument(FIELD1, ARG1));
        writer.addDocument(createDocument(FIELD1, Boolean.toString(ARG2)));
        writer.addDocument(createDocument(FIELD1, ARG3));
        writer.addDocument(createDocument(FIELD1, ARG1, FIELD1, Boolean.toString(ARG2)));
        writer.addDocument(createDocument(FIELD1, ARG1, FIELD1, Boolean.toString(ARG2), FIELD1, ARG3));
        
        // 5-9
        writer.addDocument(createDocument(FIELD2, ARG1));
        writer.addDocument(createDocument(FIELD2, Boolean.toString(ARG2)));
        writer.addDocument(createDocument(FIELD2, ARG3));
        writer.addDocument(createDocument(FIELD2, ARG1, FIELD2, Boolean.toString(ARG2)));
        writer.addDocument(createDocument(FIELD2, ARG1, FIELD2, Boolean.toString(ARG2), FIELD2, ARG3));
        
        // 10-14
        writer.addDocument(createDocument(DEFAULT_FIELD, ARG1));
        writer.addDocument(createDocument(DEFAULT_FIELD, Boolean.toString(ARG2)));
        writer.addDocument(createDocument(DEFAULT_FIELD, ARG3));
        writer.addDocument(createDocument(DEFAULT_FIELD, ARG1, DEFAULT_FIELD, Boolean.toString(ARG2)));
        writer.addDocument(createDocument(
            DEFAULT_FIELD, ARG1, DEFAULT_FIELD, Boolean.toString(ARG2), DEFAULT_FIELD, ARG3));
        
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
