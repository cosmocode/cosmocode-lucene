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

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import com.google.common.collect.Multimap;

/**
 * A helper class that holds some test constants and helper methods.
 * 
 * @author Oliver Lorenz
 */
public final class IndexHelper {
    
    public static final Directory DIRECTORY = new RAMDirectory();
    public static final Analyzer ANALYZER = new KeywordAnalyzer();
    
    /**
     * Standard INDEX method.<br />
     * This is Index.TOKENIZED, which is deprecated, but some solr libraries use an old lucene library.
     * so this is used for backwards compatibility.
     */
    @SuppressWarnings("deprecation")
    public static final Index INDEX_METHOD = Index.TOKENIZED;
    
    /** 
     * A helper for wildcard queries, different than WILDCARD2. 
     */
    public static final String WILDCARD1 = "arg";
    
    /** 
     * A helper for wildcard queries, different than WILDCARD1. 
     */
    public static final String WILDCARD2 = "hex";
    
    /** 
     * A helper for wildcard queries. 
     * yields different results than WILDCARD1 and WILDCARD2. 
     */
    public static final String WILDCARD3 = "foo";
    
    /**
     * A helper for fuzzy queries.
     * Also yields a result for wildcard queries, 
     * so it can be used for wildcard + fuzzy. */
    public static final String FUZZY1 = "truf";
    
    /** 
     * A helper for fuzzy queries. 
     * yields different results than FUZZY1 and FUZZY3. 
     */
    public static final String FUZZY2 = "arf1";
    
    /** 
     * A helper for fuzzy queries. 
     * yields different results than FUZZY1 AND FUZZY2. */
    public static final String FUZZY3 = "fooba";
    
    public static final String ARG1 = "arg1";
    public static final boolean ARG2 = true;
    public static final String ARG3 = "arg3";
    
    public static final String DEFAULT_FIELD = "default_field";
    public static final String FIELD1 = "field1";
    public static final String FIELD2 = "field2";
    public static final String[] ALL_FIELDS = {DEFAULT_FIELD, FIELD1, FIELD2};
    
    public static final Object[] ARGS = {
        ARG1, ARG2, ARG3, 
        WILDCARD1, WILDCARD2, WILDCARD3, 
        FUZZY1, FUZZY2, FUZZY3,
        "hexff00aa", "hex559911", "foobar", "truffel",
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
        1.1, 1.2, 1.3, 1.4, 1.5, 1.63, 1.7, 1.8, 1.88, 1.9, 2.0,
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
        "s", "t", "u", "v", "w", "x", "y", "z"
    };
    
    private IndexHelper() {
        // private constructor to prevent instantiation
    }
    
    /**
     * Creates a new IndexWriter with the directory and analyzer defined as constants
     * in this class.
     * 
     * @return a new IndexWriter
     * @throws IOException if opening the index writer failed for any reason
     */
    public static IndexWriter createIndexWriter() throws IOException {
        return new IndexWriter(DIRECTORY, ANALYZER, MaxFieldLength.UNLIMITED);
    }
    
    /**
     * Creates a new QueryParser with the default field and analyzer defined in this class.
     * @return a new QueryParser
     */
    public static QueryParser createQueryParser() {
        return new QueryParser(DEFAULT_FIELD, ANALYZER);
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
    public static Document createDocument(final String... keyValues) {
        final Document doc = new Document();
        String key = null;
        for (String keyOrValue : keyValues) {
            if (key == null) {
                key = keyOrValue;
                continue;
            }
            
            final Field field = new Field(key, keyOrValue, Store.YES, INDEX_METHOD);
            doc.add(field);
            key = null;
        }

        // add empty field, so that every search returns at least one document
        doc.add(new Field("empty", "empty", Store.NO, INDEX_METHOD));
        return doc;
    }
    
    /**
     * <p> Creates a lucene document with the given mapping as fields.
     * </p>
     * 
     * @param keyValues the mapping of fieldname to value
     * @return a new document with a field set to the given values
     */
    public static Document createDocument(final Map<String, ?> keyValues) {
        final Document doc = new Document();
        for (final Map.Entry<String, ?> mapping : keyValues.entrySet()) {
            if (mapping.getKey() == null || mapping.getValue() == null) {
                // ignore null key or value
                continue;
            }
            
            final String key = mapping.getKey();
            final String value = mapping.getValue().toString();
            final Field field = new Field(key, value, Store.YES, INDEX_METHOD);
            doc.add(field);
        }
        
        // add empty field, so that every search returns at least one document
        final Field emptyField = new Field("empty", "empty", Store.NO, INDEX_METHOD);
        doc.add(emptyField);
        
        return doc;
    }
    
    /**
     * <p> Creates a lucene document with the given mapping as fields.
     * </p>
     * 
     * @param fieldNameToValues the mapping of fieldname to values
     * @return a new document with a field set to the given values
     */
    public static Document createDocument(final Multimap<String, ?> fieldNameToValues) {
        final Document doc = new Document();
        for (final Map.Entry<String, ?> mapping : fieldNameToValues.entries()) {
            if (mapping.getKey() == null || mapping.getValue() == null) {
                // ignore null key or value
                continue;
            }
            
            final String key = mapping.getKey();
            final String value = mapping.getValue().toString();
            final Field field = new Field(key, value, Store.YES, INDEX_METHOD);
            doc.add(field);
        }
        
        // add empty field, so that every search returns at least one document
        final Field emptyField = new Field("empty", "empty", Store.NO, INDEX_METHOD);
        doc.add(emptyField);
        
        return doc;
    }

}
