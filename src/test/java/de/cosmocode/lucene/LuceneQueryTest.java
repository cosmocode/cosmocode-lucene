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
import java.util.Arrays;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * <p> Generic Test for {@link LuceneQuery}.
 * This is an abstract class that only sets up the lucene index.
 * Every test has to be declared separately and run in a test suite.
 * </p>
 * <p> This test has a dependency to the maven artifact: org.apache.lucene:lucene-core:jar:2.4.0 
 * </p>
 * @author Oliver Lorenz
 */
public abstract class LuceneQueryTest {
    
    /**
     * Creates a new Lucene Index with some predefined field and arguments.
     * @throws IOException if creating the index failed
     */
    @BeforeClass
    public static void createLuceneIndex() throws IOException {
        final IndexWriter writer = IndexHelper.createIndexWriter();
        
        // iterate through all fields
        for (final String field : IndexHelper.ALL_FIELDS) {
            // name for default field: "arg", every other field: (fieldName)_arg
            final String fieldName = 
                (field.equals(IndexHelper.DEFAULT_FIELD))
                ? "arg"
                : field + "_arg";
            
            // iterate through all ARGS in IndexHelper and add documents with the current field
            int argsCounter = 0;
            for (final Object arg : IndexHelper.ARGS) {
                argsCounter++;
                
                // create document with only two fields: name and field => arg.
                writer.addDocument(
                    IndexHelper.createDocument(
                        "name", fieldName + argsCounter, 
                        field, arg.toString()
                    )
                );
                
                // add all args up to current argument combined for conjunct searches
                final Multimap<String, Object> multiArgs = ArrayListMultimap.create();
                multiArgs.put("name", fieldName + "1-" + argsCounter);
                for (final Object innerArg : Arrays.copyOf(IndexHelper.ARGS, argsCounter)) {
                    multiArgs.put(field, innerArg);
                }
                writer.addDocument(IndexHelper.createDocument(multiArgs));
            }
        }
        
        writer.close();
    }
    
    /**
     * Cleans the index up again, to ensure an empty lucene index in the next test.
     * @throws IOException if cleaning the index failed
     * @throws ParseException should not happen
     */
    @AfterClass
    public static void cleanLuceneIndex() throws IOException, ParseException {
        final IndexWriter writer = IndexHelper.createIndexWriter();
        writer.deleteDocuments(IndexHelper.createQueryParser().parse("+empty:empty"));
        writer.close();
    }

}
