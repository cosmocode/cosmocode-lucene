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
