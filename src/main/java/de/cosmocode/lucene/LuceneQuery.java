package de.cosmocode.lucene;

import java.util.Collection;


/**
 * <p> An interface that specifies a builder for Lucene queries.
 * </p>
 * <p> An abstract implemententation of this interface,
 * that takes care of the redirects to methods with
 * default values and those with old style "boolean mandatory" signature,
 * is available at {@link AbstractLuceneQuery}.
 * </p>
 * <p> Example for the usage:
 * </p>
 * <pre>
 *   import com.google.common.collect.Lists;
 *   ...
 *   
 *   // This example uses the default implemtation, but any other implementation works alike
 *   LuceneQuery builder = LuceneHelper.newQuery();
 *   builder.setModifier(QueryModifier.start().required().end());
 *   builder.addField("test", Lists.newArrayList("test1", "test2"));
 *   builder.addArgument("blubb");
 *   builder.addSubquery(LuceneHelper.newQuery().addField("sub", "test").addField("array", new int[] {1, 2}));
 *   System.out.println(builder.getQuery());
 *   // prints out: +test:(test1 test2) +blubb +(sub:test array:(1 2))
 * </pre>
 * 
 * @see AbstractLuceneQuery
 * 
 * @author Oliver Lorenz
 *
 */
public interface LuceneQuery {
    
    /**
     * The default fuzzyness. It is used by
     * <ul>
     *  <li> {@link LuceneQuery#addFuzzyArgument(String)}</li>
     *  <li> {@link LuceneQuery#addFuzzyArgument(String, boolean)}</li>
     *  <li> {@link LuceneQuery#addFuzzyArgument(String, QueryModifier)}</li>
     *  <li> {@link LuceneQuery#addFuzzyField(String, String)}</li>
     *  <li> {@link LuceneQuery#addFuzzyField(String, String, boolean)}</li>
     *  <li> {@link LuceneQuery#addFuzzyField(String, String, QueryModifier)}</li>
     * </ul>
     */
    double DEFAULT_FUZZYNESS = 0.5;
    
    String ERR_EMPTY_QUERY = "The resulting query is empty, no addField or addArgument methods were successful";
    
    // TODO: JavaDoc
    

    /**
     * Returns true if this LuceneQuery appends a wildcard ("*") after each added argument, false otherwise.
     * <br>
     * <br><i>Implementation note</i>: This method should use isWildcarded() of {@link #getModifier()}
     * @return true if this LuceneQuery appends a wildcard ("*") after each added argument, false otherwise 
     */
    boolean isWildCarded();
    
    
    /**
     * The wildcard parameter.<br>
     * Set it to true to append a wildcard ("*") after each added argument,
     * false to turn this behaviour off (and just append each argument as is).
     * <br> <br>
     * <i>Implementation note</i>:
     * To provide a coherent user experience,
     * the implementation should alter the default QueryModifier
     * (i.e. set a new default QueryModifier with wildcarded set to the given value),
     * with {@link #setModifier(QueryModifier)} and {@link #getModifier()}.
     * @param wildCarded true to turn wildcarded behaviour on, false to turn it off.
     * 
     * @see QueryModifier.Builder#setWildcarded(boolean)
     * @see #setModifier(QueryModifier)
     */
    void setWildCarded(final boolean wildCarded);
    
    
    /**
     * <p> Sets a default QueryModifier that is used
     * whenever a method is invoked without a QueryModifier parameter.
     * </p>
     * <p> Please note that null as a parameter is not permitted and results in a NullPointerException.
     * If you want to set a default value, use {@link QueryModifier#DEFAULT} instead.
     * </p>
     * 
     * @param mod the default modifier to set
     * @throws NullPointerException if the parameter `mod` is null
     */
    void setModifier(final QueryModifier mod);
    
    
    /**
     * <p> Gets the default {@link QueryModifier} that is used
     * whenever a method is invoked without a QueryModifier parameter.
     * </p>
     * 
     * @return the default QueryModifier
     */
    QueryModifier getModifier();
    
    
    /**
     * <p> Returns the query which was built with the add...-methods.
     * It throws an IllegalStateException if no add...-methods were successful
     * and the resulting query is therefor empty.
     * </p>
     * 
     * @return the query which was built with the add...-methods
     * @throws IllegalStateException if no add...-methods were successful so that the query would be empty
     */
    String getQuery() throws IllegalStateException;
    
    
    //---------------------------
    //     addFuzzyArgument
    //---------------------------
    
    
    /**
     * Append a fuzzy term. <br>
     * fuzzy searches include terms that are in the levenshtein distance of the searched term.
     * <br><br>
     * This method uses the {@link #getModifier()} and {@link #DEFAULT_FUZZYNESS}.
     * 
     * @param value the value to search for
     * @return this
     */
    LuceneQuery addFuzzyArgument(String value);

    
    /**
     * Append a fuzzy term with default fuzzyness of 0.5. <br>
     * fuzzy searches include terms that are in the levenshtein distance of the searched term.
     * <br><br>
     * This method uses the {@link #DEFAULT_FUZZYNESS}.
     * 
     * @see #addFuzzyArgument(String, boolean, double)
     * @param value the value to search for
     * @param mandatory if true then the value must be found, otherwise it is just prioritized in the search results
     * @return this
     */
    LuceneQuery addFuzzyArgument(String value, boolean mandatory);
    
    
    /**
     * Append a fuzzy argument with the given fuzzyness. <br>
     * fuzzy searches include arguments that are in the levenshtein distance of the searched term.
     * 
     * @param value the value to search for
     * @param mandatory if true then the value must be found, otherwise it is just prioritized in the search results
     * @param fuzzyness the fuzzyness; must be between 0 (inclusive) and 1 (exclusive), so that: 0 <= fuzzyness < 1
     * @return this
     */
    LuceneQuery addFuzzyArgument(String value, boolean mandatory, double fuzzyness);
    
    
    /**
     * Append a fuzzy term with default fuzzyness of 0.5. <br>
     * fuzzy searches include arguments that are in the levenshtein distance of the searched term.
     * <br><br>
     * This method uses the {@link #DEFAULT_FUZZYNESS}.
     * 
     * @param value the value to search for
     * @param modifier the QueryModifier affects the way in that the argument is added.
     * @return this
     */
    LuceneQuery addFuzzyArgument(String value, QueryModifier modifier);
    
    
    /**
     * Append a fuzzy argument with the given fuzzyness. <br>
     * fuzzy searches include arguments that are in the levenshtein distance of the searched term.
     * 
     * @param value the value to search for
     * @param modifier the QueryModifier affects the way in that the argument is added.
     * @param fuzzyness the fuzzyness; must be between 0 (inclusive) and 1 (exclusive), so that: 0 <= fuzzyness < 1
     * @return this
     */
    LuceneQuery addFuzzyArgument(String value, QueryModifier modifier, double fuzzyness);

    
    
    //---------------------------
    //     addArgument
    //---------------------------
    

    
    /**
     * @param value
     * @return this
     */
    LuceneQuery addArgument(String value);
    
    
    /**
     * 
     * @param value
     * @param mandatory
     * @return this
     */
    LuceneQuery addArgument(String value, boolean mandatory);
    
    
    /**
     * <p> Adds a String term to this LuceneQuery.
     * The given modifier is applied to the value.
     * </p>
     * <p> The value can have any value, including null, but the modifier must not be null.
     * </p>
     * 
     * @param value the String value to add
     * @param modifier the QueryModifier for the value
     * @return this (for chaining)
     * @throws NullPointerException if modifier is null
     * 
     * @see QueryModifier
     */
    LuceneQuery addArgument(String value, QueryModifier modifier);
    
    
    /**
     * 
     * @param values
     * @return this
     */
    LuceneQuery addArgument(Collection<?> values);
    
    
    /**
     * @param values
     * @param mandatory
     * @return this
     */
    LuceneQuery addArgument(Collection<?> values, boolean mandatory);
    
    
    /**
     * 
     * @param values
     * @param modifier
     * @return this
     */
    LuceneQuery addArgument(Collection<?> values, QueryModifier modifier);
    
    
    /**
     * Add an array of Terms to this LuceneQuery.
     * <br><br>
     * This method uses the {@link #getModifier()}.
     * 
     * @param <K> generic element type
     * @param values an array of search terms
     * @return this
     * 
     * @see #addArgumentAsArray(Object[])
     */
    <K> LuceneQuery addArgument(K[] values);
    
    
    /**
     * <p> Add an array of Terms to this LuceneQuery.
     * </p>
     * <p> If the second parameter `mandatory` is true, then the array of terms are added as required
     * (i.e. the result contains only documents that match all values).
     * Otherwise the sub query is added as a "boost" so that all documents matching
     * at least one of the values are ordered to the top.
     * </p>
     * 
     * @param <K> generic element type
     * @param values an array of search terms
     * @param mandatory if true then the value must be found, otherwise it is just prioritized in the search results
     * @return this
     * 
     * @see #addArgumentAsArray(Object[])
     */
    <K> LuceneQuery addArgument(K[] values, boolean mandatory);
    
    
    /**
     * @param <K>
     * @param values
     * @param mandatory
     * @return this
     */
    <K> LuceneQuery addArgument(K[] values, QueryModifier modifier);
    
    /**
     * <p> Add an array of doubles to this LuceneQuery.
     * This method uses the {@link #getModifier()}.
     * </p>
     * 
     * @param values the array of terms to search for
     * @return this
     */
    LuceneQuery addArgument(double[] values);
    
    /**
     * <p> Add an array of doubles to this LuceneQuery,
     * using the given QueryModifier.
     * </p>
     * 
     * @param values the array of terms to search for
     * @param modifier the modifier for the search of this term.
     * @return this
     */
    LuceneQuery addArgument(double[] values, QueryModifier modifier);
    
    /**
     * <p> Add an int array to this LuceneQuery.
     * This method uses the {@link #getModifier()}.
     * </p>
     * 
     * @param values the array of terms to search for
     * @return this
     */
    LuceneQuery addArgument(int[] values);
    
    /**
     * <p> Add an int array to this LuceneQuery, using the given QueryModifier.
     * </p>
     * 
     * @param values the array of terms to search for
     * @param modifier the modifier for the search of this term.
     * @return this
     */
    LuceneQuery addArgument(int[] values, QueryModifier modifier);
    
    
    
    //---------------------------
    //     addArgumentAs...
    //---------------------------
    
    
    /**
     * @param values
     * @return this
     */
    LuceneQuery addArgumentAsCollection(Collection<?> values);
    
    
    /**
     * 
     * @param values
     * @param modifier
     * @return this
     */
    LuceneQuery addArgumentAsCollection(Collection<?> values, QueryModifier modifier);
    
    
    /**
     * Add an array of Terms to this LuceneQuery.
     * <br><br>
     * This method uses the {@link #getModifier()}.
     * 
     * @param <K> generic element type
     * @param values an array of search terms
     * @return this
     */
    <K> LuceneQuery addArgumentAsArray(K[] values);
    
    
    /**
     * <p>
     * Add an array of Terms to this LuceneQuery.
     * The given QueryModifier specifies the way the terms are added.
     * </p>
     * 
     * @param <K> generic element type
     * @param values an array of search terms
     * @param modifier a QueryModifier that determines the way the terms are added
     * @return this
     */
    <K> LuceneQuery addArgumentAsArray(K[] values, QueryModifier modifier);
    
    
    
    //---------------------------
    //     addSubquery
    //---------------------------
    
    
    /**
     * <p> This method adds a LuceneQuery as a sub query to this LuceneQuery.
     * </p>
     * <p> If the parameter (`value`) is null,
     * then this LuceneQuery remains unchanged. No Exception will be thrown on this invocation.
     * If all other method calls don't change this LuceneQuery,
     * then {@link #getQuery()} will throw an IllegalStateException.
     * </p>
     * <p> This method uses the {@link #getModifier()}.
     * </p>
     * 
     * @param value the SubQuery to add
     * @return this
     */
    LuceneQuery addSubquery(LuceneQuery value);
    
    
    /**
     * <p> This method adds a LuceneQuery as a sub query to this LuceneQuery.
     * </p>
     * <p> If the first parameter (`value`) is null,
     * then this LuceneQuery remains unchanged. No Exception will be thrown on this invocation.
     * If all other method calls don't change this LuceneQuery,
     * then {@link #getQuery()} will throw an IllegalStateException.
     * </p>
     * <p> If the second parameter is true, then the sub query is added as required
     * (i.e. the result contains only documents that match the sub query).
     * Otherwise the sub query is added as a "boost" so that all documents matching
     * the sub query are ordered to the top.
     * </p>
     * 
     * @param value the subQuery to add
     * @param mandatory if true then the sub query restricts the results, otherwise only boosts them
     * @return this
     */
    LuceneQuery addSubquery(LuceneQuery value, boolean mandatory);
    
    
    /**
     * <p> This method adds a LuceneQuery as a sub query to this LuceneQuery.
     * </p>
     * <p> If the parameter (`value`) is null,
     * then this LuceneQuery remains unchanged. No Exception will be thrown on this invocation.
     * If all other method calls don't change this LuceneQuery,
     * then {@link #getQuery()} will throw an IllegalStateException.
     * </p>
     * <p> The second parameter `modifier` affects the way the sub query is added.
     * </p>
     * <p>If its {@link QueryModifier#getTermModifier()} is {@link TermModifier#REQUIRED},
     * then the sub query is added as required,
     * that means the result contains only documents that match the sub query.
     * </p>
     * <p> If its {@link QueryModifier#getTermModifier()} is {@link TermModifier#PROHIBITED},
     * then the sub query is added as prohibited,
     * that means the result contains only documents that do NOT match the sub query.
     * </p>
     * <p> Otherwise the sub query is added as a "boost" so that all documents matching
     * the sub query are ordered to the top. The number of the documents is not 
     * </p>
     * 
     * @param value the subQuery to add
     * @param modifier the {@link QueryModifier} that affects the way the sub query is added
     * @return this
     */
    LuceneQuery addSubquery(LuceneQuery value, QueryModifier modifier);
    
    
    
    //---------------------------
    //     addUnescaped-methods
    //---------------------------
    
    
    /**
     * Add a field with an argument unescaped.
     * <b>Attention</b>: Use with care, otherwise you get Exceptions on execution.
     * 
     * @param key the field name
     * @param value the value of the field; 
     * @param mandatory whether the field is mandatory or not
     * @return this
     */
    LuceneQuery addUnescapedField(String key, CharSequence value, boolean mandatory);
    
    
    /**
     * Add an argument unescaped.
     * If the parameter `value` is null then nothing happens.
     * <b>Attention</b>: Use with care, otherwise you get Exceptions on execution.
     * 
     * @param value the argument to add unescaped; omitted if null
     * @param mandatory whether the argument is mandatory or not
     * @return this
     */
    LuceneQuery addUnescaped(CharSequence value, boolean mandatory);
    
    
    
    //---------------------------
    //     addField(String, String, ...)
    //---------------------------
    
    
    /**
     * Add a field with the name `key` to the query.
     * The searched value is given as a String.
     * <br><br>
     * This method uses the {@link #getModifier()}.
     * 
     * @param key the name of the field
     * @param value the (string)-value of the field
     * @return this
     */
    LuceneQuery addField(String key, String value);
    
    
    /**
     * 
     * @param key
     * @param value
     * @param mandatoryKey
     * @return this
     */
    LuceneQuery addField(String key, String value, boolean mandatoryKey);
    
    
    /**
     * Append a field with a string value, and apply a boost afterwards .
     * 
     * @see LuceneQuery#addField(String, String, boolean)
     * 
     * @param key
     * @param value
     * @param mandatoryKey
     * @param boostFactor
     * @return this
     */
    LuceneQuery addField(String key, String value, boolean mandatoryKey, double boostFactor);
    
    
    /**
     * <p> Append a field with a string value with the specified QueryModifier.
     * </p>
     * <p> The first parameter, key, must be a valid field name
     * (i.e. it must no contain any special characters of Lucene).
     * </p>
     * <p> The second parameter, value, can be any valid String.
     * Blank or empty String or null value is permitted,
     * but then this method call has no effect on the final query.
     * </p>
     * <p> The third parameter, the QueryModifier `modifier`, must not be null.
     * A NullPointerException is thrown otherwise.
     * </p> 
     * 
     * @param key the name of the field
     * @param value the value for the field
     * @param modifier the {@link QueryModifier} to apply to the field
     * @return this
     * @throws NullPointerException if the third parameter, modifier, is null
     * 
     * @see QueryModifier
     */
    LuceneQuery addField(String key, String value, QueryModifier modifier);
    
    
    
    //---------------------------
    //     addField(String, Collection, ...)
    //---------------------------
    
    
    /**
     * Append a field with a collection of values.
     * 
     * @param key
     * @param mandatoryKey
     * @param value
     * @param mandatoryValue
     * @return this
     */
    LuceneQuery addField(String key, boolean mandatoryKey, Collection<?> value, boolean mandatoryValue);
    
    
    /**
     * Append a field with a collection of values, and apply a boost afterwards.
     * 
     * @see LuceneQuery#addField(String, boolean, Collection, boolean)
     * 
     * @param key
     * @param mandatoryKey
     * @param value
     * @param mandatoryValue
     * @param boostFactor
     * @return this
     */
    LuceneQuery addField(String key, boolean mandatoryKey, Collection<?> value, 
            boolean mandatoryValue, double boostFactor);
    

    /**
     * Add a field with the name `key` to the query.
     * The values to search for are given in a collection.
     * <br><br>
     * This method uses the {@link #getModifier()}.
     * 
     * @param key the name of the field
     * @param value
     * @return this
     */
    LuceneQuery addField(String key, Collection<?> value);
    

    /**
     * Add a field with the name `key` to the query.
     * The values to search for are given in a collection.
     * 
     * @param key
     * @param value
     * @param modifier
     * @return this
     */
    LuceneQuery addField(String key, Collection<?> value, QueryModifier modifier);

    
    
    //---------------------------
    //     addField(String, Array, ...)
    //---------------------------
    

    
    /**
     * Add a field with the name `key` to the query.
     * The values to search for are given in an array.
     * <br><br>
     * This method uses the {@link #getModifier()}.
     * 
     * @param <K>
     * @param key the name of the field
     * @param value the values to be searched in the field
     * @return this
     */
    <K> LuceneQuery addField(String key, K[] value);
    
    
    /**
     * Add a field with the name `key` to the query.
     * The values to search for are given in an array.
     * 
     * @param <K>
     * @param key the name of the field
     * @param value the values to be searched in the field
     * @param modifier the query modifier
     * @return this
     */
    <K> LuceneQuery addField(String key, K[] value, QueryModifier modifier);
    
    

    //---------------------------
    //     addFuzzyField
    //---------------------------
    
    
    /**
     * Append a fuzzy search argument with the given fuzzyness for the given field.
     * <br>fuzzy searches include arguments that are in the levenshtein distance of the searched term.
     * <br>Less fuzzyness (closer to 0) means less accuracy, and vice versa
     *   (the closer to 1, solr yields less but accurater results)
     * <br>
     * <br>This method uses {@link #getModifier()} and the {@link #DEFAULT_FUZZYNESS}.
     * 
     * @param key the name of the field
     * @param value the value to search for
     * @return this
     */
    LuceneQuery addFuzzyField(String key, String value);
    
    
    /**
     * Append a fuzzy search argument with default fuzzyness (0.5) for the given field. <br>
     * fuzzy searches include arguments that are in the levenshtein distance of the searched term.
     * <br><br>
     * This method uses the {@link #DEFAULT_FUZZYNESS}.
     * 
     * @param key the name of the field
     * @param value the value to search for
     * @param mandatoryKey if true then the field must contain the given value, 
     *   otherwise it is just prioritized in the search results
     * @return this
     */
    LuceneQuery addFuzzyField(String key, String value, boolean mandatoryKey);
    
    
    /**
     * Append a fuzzy search argument with the given fuzzyness for the given field.
     * <br>fuzzy searches include arguments that are in the levenshtein distance of the searched term.
     * <br>Less fuzzyness (closer to 0) means less accuracy, and vice versa
     *   (the closer to 1, lucene yields less but accurater results)
     * 
     * @param key the name of the field
     * @param value the value to search for
     * @param mandatoryKey if true then the field must contain the given value,
     *   otherwise it is just prioritized in the search results
     * @param fuzzyness the fuzzyness; must be between 0 and 1, so that 0 <= fuzzyness < 1
     * @return this
     */
    LuceneQuery addFuzzyField(String key, String value, boolean mandatoryKey, double fuzzyness);
    
    
    /**
     * Append a fuzzy search argument with the given fuzzyness for the given field.
     * <br>fuzzy searches include arguments that are in the levenshtein distance of the searched term.
     * <br>Less fuzzyness (closer to 0) means less accuracy, and vice versa
     *   (the closer to 1, solr yields less but accurater results)
     * <br>
     * <br>This method uses the {@link #DEFAULT_FUZZYNESS}.
     * 
     * @param key the name of the field
     * @param value the value to search for
     * @param mod the modifiers to use
     * @return this
     */
    LuceneQuery addFuzzyField(String key, String value, QueryModifier mod);
    
    
    /**
     * Append a fuzzy search argument with the given fuzzyness for the given field.
     * <br>fuzzy searches include arguments that are in the levenshtein distance of the searched term.
     * <br>Less fuzzyness (closer to 0) means less accuracy, and vice versa
     *   (the closer to 1, solr yields less but accurater results)
     * 
     * @param key the name of the field
     * @param value the value to search for
     * @param mod the modifiers to use
     * @param fuzzyness the fuzzyness; must be between 0 and 1, so that 0 <= fuzzyness < 1
     * @return this
     */
    LuceneQuery addFuzzyField(String key, String value, QueryModifier mod, double fuzzyness);

    

    //---------------------------
    //     addFieldAs...
    //---------------------------
    
    /**
     * Adds a field named `key`, with the values of `value`.
     * 
     * <p> This method uses {@link #getModifier()}.
     * </p>
     * 
     * @param key the name of the field
     * @param value the collection of values for the field
     * @return this
     */
    LuceneQuery addFieldAsCollection(String key, Collection<?> value);
    
    
    /**
     * Add a field with the name `key` to the query.
     * The values to search for are given in a collection.
     * 
     * @param key
     * @param value
     * @param modifier
     * @return this
     */
    LuceneQuery addFieldAsCollection(String key, Collection<?> value, QueryModifier modifier);
    
    
    /**
     * Append a field with a collection of values, and apply a boost afterwards .
     * 
     * @see LuceneQuery#addFieldAsCollection(String, Collection, QueryModifier)
     * 
     * @param key
     * @param value
     * @param modifier
     * @param boost
     * @return this
     */
    LuceneQuery addFieldAsCollection(String key, Collection<?> value, QueryModifier modifier, double boost);
    
    
    /**
     * <p> Add a field with the name `key` to the query.
     * The values to search for are given in an array.
     * </p>
     * <p> This method uses {@link #getModifier()}.
     * </p>
     * 
     * @param <K>
     * @param key
     * @param value
     * @return this
     */
    <K> LuceneQuery addFieldAsArray(String key, K[] value);
    
    
    /**
     * Add a field with the name `key` to the query.
     * The values to search for are given in an array.
     * 
     * @param <K>
     * @param key
     * @param value
     * @param modifier
     * @return this
     */
    <K> LuceneQuery addFieldAsArray(String key, K[] value, QueryModifier modifier);
    
    
    /**
     * Add a field with the name `key` to the query.
     * The values to search for are given in an array.
     * 
     * @param key the name of the field
     * @param value the values to add
     * @return this
     */
    LuceneQuery addFieldAsArray(String key, Object value);
    
    
    /**
     * Add a field with the name `key` to the query.
     * The values to search for are given in an array.
     * 
     * @param key the name of the field
     * @param value the values to add
     * @param modifier the QueryModifier to apply
     * @return this
     */
    LuceneQuery addFieldAsArray(String key, Object value, QueryModifier modifier);
    
    
    
    //---------------------------------------
    //    startField, endField, addBoost
    //---------------------------------------
    
    
    /**
     * Starts a field with `key`:(.<br>
     * <b>Attention</b>: Use this method carefully and end all fields with 
     * {@link LuceneQuery#endField()},
     * or otherwise you get Exceptions on execution.
     * @param fieldName the name of the field; omitted if null
     * @param mandatory whether the field is mandatory for execution ("+" is prepended) or not.
     * @return this
     */
    LuceneQuery startField(String fieldName, boolean mandatory);
    
    
    /**
     * Starts a field with `key`:(.<br>
     * <b>Attention</b>: Use this method carefully and end all fields with 
     * {@link LuceneQuery#endField()},
     * or otherwise you get Exceptions on execution.
     * @param fieldName the name of the field; omitted if null
     * @param modifier the modifiers for the field (see QueryModifier for more details)
     * @return this
     */
    LuceneQuery startField(String fieldName, QueryModifier modifier);
    
    
    /**
     * Ends a previously started field. <br>
     * <b>Attention</b>: Use this method carefully and only end fields that have been started with
     * {@link LuceneQuery#startField(String, boolean)},
     * or otherwise you get Solr-Exceptions on execution.
     * @return this
     */
    LuceneQuery endField();
    
    
    /**
     * Add a boost factor to the current element. <br>
     * <b>Attention</b>: Don't use this method directly after calling startField(...), 
     * or otherwise you get Exceptions on execution.
     * @param boostFactor a positive double < 10.000.000 which boosts the previously added element
     * @return this
     */
    LuceneQuery addBoost(double boostFactor);
    

}
