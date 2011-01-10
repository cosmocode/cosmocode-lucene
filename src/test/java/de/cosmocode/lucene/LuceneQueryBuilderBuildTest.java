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

import com.google.common.collect.Lists;

import de.cosmocode.junit.UnitProvider;

/**
 * Tests build() method of the {@link LuceneQueryBuilder}. 
 *
 * @author Oliver Lorenz
 */
public final class LuceneQueryBuilderBuildTest implements UnitProvider<LuceneQueryBuilder> {
    
    private static final QueryModifier TEST_MOD = QueryModifier.start().required().wildcarded().doSplit().build();
    
    @Override
    public LuceneQueryBuilder unit() {
        return new LuceneQueryBuilder();
    }
    
    /**
     * Tests build without any calls.
     */
    @Test
    public void build() {
        unit().build();
    }
    
    /**
     * Tests building with a custom QueryModifier.
     */
    @Test
    public void buildModifier() {
        final LuceneQueryBuilder builder = unit();
        builder.setModifier(TEST_MOD);
        final LuceneQuery query = builder.build();
        Assert.assertEquals(TEST_MOD, query.getModifier());
    }
    
    /**
     * Tests building with one calls to addField.
     */
    @Test
    public void buildWithOneField() {
        final LuceneQueryBuilder builder = unit();
        builder.addField("test", "test");
        final LuceneQuery query = builder.build();
        Assert.assertEquals(builder.getQuery().trim(), query.getQuery().trim());
    }
    
    /**
     * Tests building with several calls to addField.
     */
    @Test
    public void buildWithQuery() {
        final LuceneQueryBuilder builder = unit();
        builder.addField("test", "test");
        builder.addField("collection", Lists.newArrayList("blubb"), TEST_MOD);
        final LuceneQuery query = builder.build();
        Assert.assertEquals(builder.getQuery().trim(), query.getQuery().trim());
    }
    
    /**
     * Tests building with some calls to addField and a custom QueryModifier.
     */
    @Test
    public void buildWithQueryAndModifier() {
        final LuceneQueryBuilder builder = unit();
        builder.setModifier(TEST_MOD);
        builder.addField("test", "test");
        builder.addField("collection", Lists.newArrayList("blubb"), TEST_MOD);
        final LuceneQuery query = builder.build();
        Assert.assertEquals(builder.getQuery().trim(), query.getQuery().trim());
        Assert.assertEquals(TEST_MOD, query.getModifier());
    }

}
