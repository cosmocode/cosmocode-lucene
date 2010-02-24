package de.cosmocode.lucene;

import de.cosmocode.patterns.Builder;

public final class LuceneQueryBuilder extends ForwardingLuceneQuery 
    implements LuceneQuery, Builder<LuceneQuery> {
    
    private final LuceneQuery delegate;
    
    public LuceneQueryBuilder() {
        this.delegate = new DefaultLuceneQuery();
    }
    
    public LuceneQueryBuilder(final LuceneQuery delegate) {
        this.delegate = delegate;
    }
    
    @Override
    protected LuceneQuery delegate() {
        return delegate;
    }
    
    @Override
    public LuceneQuery build() {
        final LuceneQuery newQuery = new DefaultLuceneQuery();
        newQuery.setDefaultQueryModifier(getDefaultQueryModifier());
        newQuery.addUnescaped(delegate.getQuery(), false);
        return newQuery;
    }

}
