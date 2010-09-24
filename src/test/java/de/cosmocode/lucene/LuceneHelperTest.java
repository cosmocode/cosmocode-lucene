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

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the static methods of {@link LuceneHelper}.
 *
 * @author Oliver Lorenz
 */
public class LuceneHelperTest {

    @Test
    public void testEscapeQuotes() {
        final String input = "test \"wichtig\" blubb{()}";
        final String expected = "test \\\"wichtig\\\" blubb{()}";
        final String actual = LuceneHelper.escapeQuotes(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEscapeQuotesNull() {
        final String input = null;
        final String expected = "";
        final String actual = LuceneHelper.escapeQuotes(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRemoveQuotes() {
        final String input = "test \"wichtig\" blubb{()}";
        final String expected = "test wichtig blubb{()}";
        final String actual = LuceneHelper.removeQuotes(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRemoveQuotesNull() {
        final String input = null;
        final String expected = "";
        final String actual = LuceneHelper.removeQuotes(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRemoveSpecialCharacters() {
        final String input = "bla %\"{]{/(CKD93jfs09 sdf}  {]";
        final String expected = "bla%/CKD93jfs09sdf";
        final String actual = LuceneHelper.removeSpecialCharacters(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRemoveSpecialCharactersNull() {
        final String input = null;
        final String expected = "";
        final String actual = LuceneHelper.removeSpecialCharacters(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEscapeInput() {
        final String input = "bla %\"{]{/(CKD93jfs09 sdf}  {]";
        final String expected = "bla\\ %\"\\{\\]\\{/\\(CKD93jfs09\\ sdf\\}\\ \\ \\{\\]";
        final String actual = LuceneHelper.escapeInput(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEscapeInputNull() {
        final String input = null;
        final String expected = "";
        final String actual = LuceneHelper.escapeInput(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEscapeAll() {
        final String input = "bla %\"{]{/(CKD93jfs09 sdf}  {]";
        final String expected = "bla\\ %\\\"\\{\\]\\{/\\(CKD93jfs09\\ sdf\\}\\ \\ \\{\\]";
        final String actual = LuceneHelper.escapeAll(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEscapeAllNull() {
        final String input = null;
        final String expected = "";
        final String actual = LuceneHelper.escapeAll(input);
        Assert.assertEquals(expected, actual);
    }

}
