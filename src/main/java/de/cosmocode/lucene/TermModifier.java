package de.cosmocode.lucene;

/**
 * The TermModifier is an enum over the possible modifiers that can be prepended
 *   before a term.  
 * <br>The TermModifier can only be used in conjunction with {@link QueryModifier}.
 * 
 * @since 1.0
 * @author Oliver Lorenz
 */
public enum TermModifier {
    
    /**
     * The affected Term/Field to be added is not affected in any way.
     * This means that all documents that contain the term 
     * come first in the returned result list.
     * But the returned list is not limited to these documents.
     * If this is the only TermModifier applied to the SolrQuery,
     * then all documents stored in the lucene/solr index are found by solr.
     * <br> <br>
     * The implementation should prepend nothing to the Term/Field,
     * or use {@link TermModifier#getModifier()}.
     */
    NONE(""),
    
    /**
     * The affected Term/Field to be added to the SolrQuery is marked as required.
     * This means that only those documents that contain the term
     * are in the returned result list, and none else.
     * The returned list is therefore limited to these documents.
     * <br> <br>
     * The implementation should prepend a "+" to the Term/Field,
     * or use {@link TermModifier#getModifier()}.
     */
    REQUIRED("+"),
    
    /**
     * The affected Term/Field to be added to the SolrQuery is marked as prohibited.
     * This means that all documents except those that contain the term
     * are in the returned result list.
     * <br> <br>
     * The implementation should prepend a "-" to the Term/Field,
     * or use {@link TermModifier#getModifier()}.
     */
    PROHIBITED("-");
    
    
    private final String modifier;
    
    private TermModifier(final String modifier) {
        this.modifier = modifier;
    }
    
    
    /**
     * Returns the modifier that should be prepended in front of the term/field to yield the desired result.
     * @return the modifier that should be prepended in front of the term/field to yield the desired result
     */
    public String getModifier() {
        return modifier;
    }

}
