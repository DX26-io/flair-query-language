package com.flair.bi.compiler.search;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SearchQLCompilerByStatementTest {

    private SearchQLCompiler compiler;

    @Before
    public void setUp() {
        compiler = new SearchQLCompiler();
    }

    @Test
    public void aggregationStatementAndByStatement_double() {
        SearchResult result = match("count(sales), max(revenue) by state, street");

        assertEquals(2, result.asByStatementResult().get().getFeature().size());
        assertEquals(ByStatementResult.State.COMPLETED, result.asByStatementResult().get().getState());

        assertEquals("state", result.asByStatementResult().get().getFeature().get(0));
        assertEquals("street", result.asByStatementResult().get().getFeature().get(1));
    }

    @Test
    public void aggregationStatementAndByStatement_singleAndComma() {
        SearchResult result = match("count(sales), max(revenue) by state, ");

        assertEquals(1, result.asByStatementResult().get().getFeature().size());
        assertEquals(ByStatementResult.State.EXPRESSION, result.asByStatementResult().get().getState());

        assertEquals("state", result.asByStatementResult().get().getFeature().get(0));
    }

    @Test
    public void aggregationStatementAndByStatement_single() {
        SearchResult result = match("count(sales), max(revenue) by state");

        assertEquals(1, result.asByStatementResult().get().getFeature().size());
        assertEquals(ByStatementResult.State.COMPLETED, result.asByStatementResult().get().getState());

        assertEquals("state", result.asByStatementResult().get().getFeature().get(0));
    }

    @Test
    public void aggregationStatementAndByStatement_byOnly() {
        SearchResult result = match("count(sales), max(revenue) by");

        assertEquals(0, result.asByStatementResult().get().getFeature().size());
        assertEquals(ByStatementResult.State.EXPRESSION, result.asByStatementResult().get().getState());
    }

    @Test
    public void aggregationStatementAndByStatement_byComma() {
        SearchResult result = match("count(sales), max(revenue) by state, country,");

        assertEquals(2, result.asByStatementResult().get().getFeature().size());
        assertEquals(ByStatementResult.State.EXPRESSION, result.asByStatementResult().get().getState());

        assertEquals("state", result.asByStatementResult().get().getFeature().get(0));
        assertEquals("country", result.asByStatementResult().get().getFeature().get(1));
    }

    private SearchResult match(String statement) {
        return compiler.compile(new SearchQuery(statement));
    }
}