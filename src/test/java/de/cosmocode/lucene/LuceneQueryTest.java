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
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import de.cosmocode.junit.UnitProvider;
import de.cosmocode.lucene.fragments.query.AddArgumentArrayFragment;
import de.cosmocode.lucene.fragments.query.AddArgumentArrayModFragment;
import de.cosmocode.lucene.fragments.query.AddArgumentCollectionFragment;
import de.cosmocode.lucene.fragments.query.AddArgumentCollectionModFragment;
import de.cosmocode.lucene.fragments.query.AddArgumentStringFragment;
import de.cosmocode.lucene.fragments.query.AddArgumentStringModFragment;
import de.cosmocode.lucene.fragments.query.AddFieldArrayFragment;
import de.cosmocode.lucene.fragments.query.AddFieldArrayModFragment;
import de.cosmocode.lucene.fragments.query.AddFieldCollectionFragment;
import de.cosmocode.lucene.fragments.query.AddFieldCollectionModFragment;
import de.cosmocode.lucene.fragments.query.AddFieldStringFragment;
import de.cosmocode.lucene.fragments.query.AddFieldStringModFragment;
import de.cosmocode.lucene.fragments.query.AddRangeDoubleDoubleModFragment;
import de.cosmocode.lucene.fragments.query.AddRangeFieldDoubleDoubleModFragment;
import de.cosmocode.lucene.fragments.query.AddRangeFieldFragment;
import de.cosmocode.lucene.fragments.query.AddRangeFieldIntIntModFragment;
import de.cosmocode.lucene.fragments.query.AddRangeFieldStringStringModFragment;
import de.cosmocode.lucene.fragments.query.AddRangeFragment;
import de.cosmocode.lucene.fragments.query.AddRangeIntIntModFragment;
import de.cosmocode.lucene.fragments.query.AddRangeStringStringModFragment;

/**
 * <p> Generic Test for {@link LuceneQuery}.
 * This is a final class that executes a test suite and provides only static methods.
 * </p>
 * <p> This test has a dependency to the maven artifact: org.apache.lucene:lucene-core:jar:2.4.0 
 * </p>
 * <p> A test for LuceneQuery must have this class in a TestSuite
 * and add a static method with {@code @}BeforeClass to set the UnitProvider of this class.
 * </p>
 * <p> Example:
 * </p>
 * <pre>
 * {@code @}RunWith(Suite.class)
 * {@code @}SuiteClasses(LuceneQueryTest.class)
 *  public final class MyLuceneQueryTest implements {@code UnitProvider<LuceneQuery>} {
 *     {@code @}Override
 *      public LuceneQuery unit() {
 *          return new MyLuceneQuery();
 *      }
 *  
 *     {@code @}BeforeClass
 *      public static void setupClass() {
 *          LuceneQueryTest.setUnitProvider(MyLuceneQueryTest.class);
 *      }
 *  }
 * </pre>
 * @author Oliver Lorenz
 */
@RunWith(Suite.class)
@SuiteClasses({
    AddArgumentCollectionFragment.class,
    AddArgumentCollectionModFragment.class,
    AddFieldCollectionFragment.class,
    AddFieldCollectionModFragment.class,
    AddArgumentArrayFragment.class,
    AddArgumentArrayModFragment.class,
    AddFieldArrayFragment.class,
    AddFieldArrayModFragment.class,
    AddArgumentStringFragment.class,
    AddArgumentStringModFragment.class,
    AddFieldStringFragment.class,
    AddFieldStringModFragment.class,
    AddRangeFragment.class,
    AddRangeStringStringModFragment.class,
    AddRangeIntIntModFragment.class,
    AddRangeDoubleDoubleModFragment.class,
    AddRangeFieldFragment.class,
    AddRangeFieldStringStringModFragment.class,
    AddRangeFieldIntIntModFragment.class,
    AddRangeFieldDoubleDoubleModFragment.class
})
public abstract class LuceneQueryTest {
    
    private static final String ERR_NO_PROVIDER = 
        "UnitProvider class not yet set, " +
        "set it with LuceneQueryTest.setUnitProvider(MyProvider.class) " +
        "in an @BeforeClass annotated static method";
    
    private static UnitProvider<? extends LuceneQuery> unitProvider;
    
    private LuceneQueryTest() {
        
    }
    
    /**
     * Returns a new {@code UnitProvider<LuceneQuery>}.
     * @return a new {@code UnitProvider<LuceneQuery>}
     */
    public static UnitProvider<? extends LuceneQuery> unitProvider() {
        return Preconditions.checkNotNull(unitProvider, ERR_NO_PROVIDER);
    }
    
    /**
     * <p> Sets the UnitProvider to the given class.
     * It is then created with newInstance(),
     * so it must have a standard constructor without parameters.
     * </p>
     * 
     * @param providerClass the class of the given provider, used for creation
     */
    public static void setUnitProvider(Class<? extends UnitProvider<? extends LuceneQuery>> providerClass) {
        Preconditions.checkNotNull(providerClass, "The given Provider class must not be null");
        try {
            unitProvider = providerClass.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
    
    /**
     * Cleans the AbstractLuceneQueryTest for the next test.
     */
    @AfterClass
    public static void unsetUnitProvider() {
        unitProvider = null;
    }
    
    
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
