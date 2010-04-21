package de.cosmocode.lucene;

import de.cosmocode.patterns.Builder;

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
public final class LuceneQueryBuilder extends ForwardingLuceneQuery 
    implements LuceneQuery, Builder<LuceneQuery> {
    
    public static final String ERR_LOCKED = "LuceneQueryBuilder has been locked, no changes possible";
    
    private final LuceneQuery delegate;
    
    private boolean locked;
    
    public LuceneQueryBuilder() {
        this.delegate = new DefaultLuceneQuery();
    }
    
    public LuceneQueryBuilder(final LuceneQuery delegate) {
        this.delegate = delegate;
    }
    
    @Override
    protected LuceneQuery delegate() {
        if (locked) throw new IllegalStateException(ERR_LOCKED);
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
     * <p> <strong> Attention </strong>: The locking is not secure, because the lock can be broken
     * with Reflection. This is just a way to secure that this instance is only used as a builder
     * after the invocation of this method.
     * </p>
     * Only the following methods are allowed afterwards:
     * <ul>
     *   <li> {@link #getModifier()} </li>
     *   <li> {@link #getQuery()} </li>
     *   <li> {@link #isWildCarded()} </li>
     *   <li> {@link #lastSuccessful()} </li>
     * </ul>
     */
    public void lock() {
        this.locked = true;
    }
    
    @Override
    public LuceneQuery build() {
        final LuceneQuery newQuery = new DefaultLuceneQuery();
        newQuery.setModifier(this.getModifier());
        newQuery.addUnescaped(this.getQuery(), false);
        return newQuery;
    }

}
