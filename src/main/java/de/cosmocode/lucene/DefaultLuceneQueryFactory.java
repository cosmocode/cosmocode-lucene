package de.cosmocode.lucene;

import de.cosmocode.patterns.Factory;

/**
 * <p>
 * A Factory that creates default {@link LuceneQuery}s.
 * The created LuceneQuerys are not threadsafe.
 * </p>
 * 
 * @author Oliver Lorenz
 */
public final class DefaultLuceneQueryFactory implements Factory<LuceneQuery> {
    
    private final QueryModifier defaultQueryModifier;
    
    public DefaultLuceneQueryFactory() {
        this.defaultQueryModifier = QueryModifier.DEFAULT;
    }
    
    public DefaultLuceneQueryFactory(final QueryModifier mod) {
        this.defaultQueryModifier = mod;
    }
    
    @Override
    public LuceneQuery create() {
        final LuceneQuery newQuery = new DefaultLuceneQuery();
        newQuery.setDefaultQueryModifier(defaultQueryModifier);
        return newQuery;
    }

}
