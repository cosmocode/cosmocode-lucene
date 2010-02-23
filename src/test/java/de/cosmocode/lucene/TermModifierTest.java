package de.cosmocode.lucene;

import junit.framework.Assert;

import org.junit.Test;

/**
 * This is a short test for {@link TermModifier}. It tests {@link TermModifier#getModifier()}.
 *  
 * @author olorenz
 */
public class TermModifierTest {

    /**
     * Tests {@link TermModifier#getModifier()} on {@link TermModifier#NONE}.
     */
    @Test
    public void testGetModifierNONE() {
        Assert.assertEquals("", TermModifier.NONE.getModifier());
    }

    /**
     * Tests {@link TermModifier#getModifier()} on {@link TermModifier#REQUIRED}.
     */
    @Test
    public void testGetModifierREQUIRED() {
        Assert.assertEquals("+", TermModifier.REQUIRED.getModifier());
    }

    /**
     * Tests {@link TermModifier#getModifier()} on {@link TermModifier#PROHIBITED}.
     */
    @Test
    public void testGetModifierPROHIBITED() {
        Assert.assertEquals("-", TermModifier.PROHIBITED.getModifier());
    }

}
