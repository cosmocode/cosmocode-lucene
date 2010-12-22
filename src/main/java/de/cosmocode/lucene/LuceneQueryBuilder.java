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

import de.cosmocode.patterns.Builder;
import de.cosmocode.patterns.Factory;

/**
 * <p> This is a {@link Builder} that behaves like a {@link LuceneQuery}.
 * You can build templates this way, without repeating the same method calls over and over.
 * </p>
 * <p> It can be {@link #lock()}ed to prevent further modification.
 * </p>
 *
 * @since 1.0
 * @author Oliver Lorenz
 */
public final class LuceneQueryBuilder extends ForwardingLuceneQuery implements Builder<LuceneQuery> {
    
    public static final String ERR_LOCKED = "LuceneQueryBuilder has been locked, no changes possible";
    
    private final LuceneQuery delegate;
    
    private final Factory<LuceneQuery> template;
    
    private boolean locked;
    
    private boolean hasQuery;
    
    /**
     * Creates a standard LuceneQueryBuilder.
     * The underlying implementation is not threadsafe.
     */
    public LuceneQueryBuilder() {
        this.delegate = new DefaultLuceneQuery();
        this.template = LuceneHelper.DEFAULT_FACTORY;
    }
    
    /**
     * <p> Creates a LuceneQueryBuilder with a custom LuceneQuery template.
     * The given template class is used for the delegate process and
     * the {@link #build()} method.
     * </p>
     * <p> It must have a parameter-less constructor,
     * otherwise the constructor throws an IllegalArgumentException.
     * </p>
     * 
     * @param delegateClass the class from which concrete LuceneQuery instances are created
     * @throws IllegalArgumentException if the parameter-less constructor of the given class cannot be called
     */
    public LuceneQueryBuilder(final Class<? extends LuceneQuery> delegateClass) {
        this.template = new ClassFactory(delegateClass);
        this.delegate = this.template.create();
    }
    
    /**
     * <p> Creates a LuceneQueryBuilder with a custom LuceneQuery template.
     * The given {@code Factory<LuceneQuery>} is used for the delegate process and
     * the {@link #build()} method.
     * </p>
     * 
     * @param template this {@code Factory<LuceneQuery>} is called everytime an instance is needed
     */
    public LuceneQueryBuilder(final Factory<LuceneQuery> template) {
        this.template = template;
        this.delegate = this.template.create();
    }
    
    @Override
    protected LuceneQuery delegate() {
        if (locked) throw new IllegalStateException(ERR_LOCKED);
        // has a query if any previous call was successful
        hasQuery = hasQuery || lastSuccessful();
        return delegate;
    }
    
    @Override
    public QueryModifier getModifier() {
        return delegate.getModifier();
    }
    
    @Override
    public String getQuery() {
        return delegate.getQuery();
    }
    
    @Override
    public boolean isWildCarded() {
        return delegate.isWildCarded();
    }
    
    @Override
    public boolean lastSuccessful() {
        return delegate.lastSuccessful();
    }
    
    /**
     * <p> Instantly locks this LuceneQueryBuilder so that all methods that would alter this instance
     * will fail with an IllegalStateException.
     * </p>
     * <p> <strong> Attention </strong>: The locking is not 100% secure, because the lock can be broken
     * with Reflection. This is just one way to ensure that this instance is only used as a builder
     * after the invocation of this method.
     * </p>
     * Only the following methods are allowed afterwards:
     * <ul>
     *   <li> {@link #getModifier()} </li>
     *   <li> {@link #getQuery()} </li>
     *   <li> {@link #isWildCarded()} </li>
     *   <li> {@link #lastSuccessful()} </li>
     *   <li> {@link #build()} </li>
     *   <li> {@link #lock()} (but nothing different happens after the first call)</li>
     * </ul>
     */
    public void lock() {
        this.locked = true;
    }
    
    /**
     * <p> Builds a new LuceneQuery from this Builder.
     * </p>
     * <p> This means:
     * It has the same QueryModifier ({@link #getModifier()})
     * set as this class.
     * The {@link LuceneQuery#getQuery()} method returns an equivalent
     * query as this Builder.
     * </p>
     * <p> 
     * @return a LuceneQuery that has the query and QueryModifier from this builder
     */
    @Override
    public LuceneQuery build() {
        // check hasQuery again, because last addArgument/addField call went unnoticed
        if (!hasQuery) hasQuery = lastSuccessful();
        
        final LuceneQuery newQuery = this.template.create();
        newQuery.setModifier(this.getModifier());
        if (hasQuery) newQuery.addUnescaped(this.getQuery(), false);
        return newQuery;
    }
    
    /**
     * A {@code Factory<LuceneQuery>} that takes a {@code Class<? extends LuceneQuery}
     * and calls newInstance on every creation.
     *
     * @author Oliver Lorenz
     */
    private static class ClassFactory implements Factory<LuceneQuery> {
        
        private final Class<? extends LuceneQuery> template;
        
        public ClassFactory(final Class<? extends LuceneQuery> template) {
            this.template = template;
            try {
                // check the parameter-less constructor
                template.newInstance();
            } catch (InstantiationException e) {
                throw new IllegalArgumentException(
                    "The given class must have a constructor without arguments", e);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(
                    "The given class must have a constructor without arguments", e);
            }
        }
        
        @Override
        public LuceneQuery create() {
            try {
                return template.newInstance();
            } catch (InstantiationException e) {
                throw new IllegalStateException(
                    "The LuceneQuery could not be instantiated from the template class", e);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("The template class is not accessible", e);
            }
        }
        
    }

}
