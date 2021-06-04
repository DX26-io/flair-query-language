package com.flair.bi.compiler.search;

import org.junit.Before;
import org.junit.Test;

import static com.flair.bi.compiler.search.OrderByStatementResult.State.COMPLETED;
import static com.flair.bi.compiler.search.OrderByStatementResult.State.DIRECTION;
import static com.flair.bi.compiler.search.OrderByStatementResult.State.FEATURE;
import static com.flair.bi.compiler.search.OrderByStatementResult.State.START;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SearchQLCompilerOrderByStatementTest {

    private SearchQLCompiler compiler;

    @Before
    public void setUp() {
        compiler = new SearchQLCompiler();
    }

    @Test
    public void fullSearchQuery_orderByOnly() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA', 'CAN'), quantity_purchased >= 10000 order by");

        assertNull(result.asOrderByStatementResult().get().getFeature());
        assertNull(result.asOrderByStatementResult().get().getDirection());
        assertEquals(START, result.asOrderByStatementResult().get().getState());
    }

    @Test
    public void fullSearchQuery_orderByColumnOnly() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA', 'CAN'), quantity_purchased >= 10000 order by column");

        assertEquals("column", result.asOrderByStatementResult().get().getFeature());
        assertNull(result.asOrderByStatementResult().get().getDirection());
        assertEquals(FEATURE, result.asOrderByStatementResult().get().getState());
    }

    @Test
    public void fullSearchQuery_orderByColumnSpace() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA', 'CAN'), quantity_purchased >= 10000 order by column ");

        assertEquals("column", result.asOrderByStatementResult().get().getFeature());
        assertNull(result.asOrderByStatementResult().get().getDirection());
        assertEquals(DIRECTION, result.asOrderByStatementResult().get().getState());
    }

    @Test
    public void fullSearchQuery_orderByColumnDirectionIncomplete() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA', 'CAN'), quantity_purchased >= 10000 order by column de");

        assertEquals("column", result.asOrderByStatementResult().get().getFeature());
        assertNull(result.asOrderByStatementResult().get().getDirection());
        assertEquals(DIRECTION, result.asOrderByStatementResult().get().getState());
    }

    @Test
    public void fullSearchQuery_orderByColumnDirection() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA', 'CAN'), quantity_purchased >= 10000 order by column desc");

        assertEquals("column", result.asOrderByStatementResult().get().getFeature());
        assertEquals("desc", result.asOrderByStatementResult().get().getDirection());
        assertEquals(COMPLETED, result.asOrderByStatementResult().get().getState());
    }

    private SearchResult match(String statement) {
        return compiler.compile(new SearchQuery(statement));
    }
}