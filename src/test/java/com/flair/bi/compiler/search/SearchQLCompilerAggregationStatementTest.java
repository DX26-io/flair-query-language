package com.flair.bi.compiler.search;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SearchQLCompilerAggregationStatementTest {

    private SearchQLCompiler compiler;

    @Before
    public void setUp() {
        compiler = new SearchQLCompiler();
    }

    @Test
    public void aggregationStatementQuery_double() {
        SearchResult result = match("count(sales), max(revenue)");

        assertEquals(2, result.asAggregationStatementsResult().get().getStatements().size());
        assertEquals(AggregationStatementsResult.State.COMPLETED, result.asAggregationStatementsResult().get().getState());

        assertEquals("sales", result.asAggregationStatementsResult().get().getStatements().get(0).getFeature());
        assertEquals("count", result.asAggregationStatementsResult().get().getStatements().get(0).getFunction());
        assertEquals(AggregationStatementResult.State.COMPLETED, result.asAggregationStatementsResult().get().getStatements().get(0).getState());

        assertEquals("revenue", result.asAggregationStatementsResult().get().getStatements().get(1).getFeature());
        assertEquals("max", result.asAggregationStatementsResult().get().getStatements().get(1).getFunction());
        assertEquals(AggregationStatementResult.State.COMPLETED, result.asAggregationStatementsResult().get().getStatements().get(1).getState());

    }

    @Test
    public void aggregationStatementQuery_functionOnly() {
        SearchResult result = match("count");

        assertEquals(1, result.asAggregationStatementsResult().get().getStatements().size());
        assertEquals(AggregationStatementsResult.State.EXPRESSION, result.asAggregationStatementsResult().get().getState());

        assertNull(result.asAggregationStatementsResult().get().getStatements().get(0).getFeature());
        assertEquals("count", result.asAggregationStatementsResult().get().getStatements().get(0).getFunction());
        assertEquals(AggregationStatementResult.State.FUNCTION, result.asAggregationStatementsResult().get().getStatements().get(0).getState());
    }

    @Test
    public void aggregationStatementQuery_functionOnlyWithOpeningBracket() {
        SearchResult result = match("count(");

        assertEquals(1, result.asAggregationStatementsResult().get().getStatements().size());
        assertEquals(AggregationStatementsResult.State.EXPRESSION, result.asAggregationStatementsResult().get().getState());

        assertNull(result.asAggregationStatementsResult().get().getStatements().get(0).getFeature());
        assertEquals("count", result.asAggregationStatementsResult().get().getStatements().get(0).getFunction());
        assertEquals(AggregationStatementResult.State.FEATURE, result.asAggregationStatementsResult().get().getStatements().get(0).getState());
    }

    @Test
    public void aggregationStatementQuery_functionOnlyWithoutClosingBracket() {
        SearchResult result = match("count(sales");

        assertEquals(1, result.asAggregationStatementsResult().get().getStatements().size());
        assertEquals(AggregationStatementsResult.State.EXPRESSION, result.asAggregationStatementsResult().get().getState());

        assertEquals("sales", result.asAggregationStatementsResult().get().getStatements().get(0).getFeature());
        assertEquals("count", result.asAggregationStatementsResult().get().getStatements().get(0).getFunction());
        assertEquals(AggregationStatementResult.State.FEATURE, result.asAggregationStatementsResult().get().getStatements().get(0).getState());
    }

    @Test
    public void aggregationStatementQuery_single() {
        SearchResult result = match("count(sales)");

        assertEquals(1, result.asAggregationStatementsResult().get().getStatements().size());
        assertEquals(AggregationStatementsResult.State.COMPLETED, result.asAggregationStatementsResult().get().getState());

        assertEquals("sales", result.asAggregationStatementsResult().get().getStatements().get(0).getFeature());
        assertEquals("count", result.asAggregationStatementsResult().get().getStatements().get(0).getFunction());
        assertEquals(AggregationStatementResult.State.COMPLETED, result.asAggregationStatementsResult().get().getStatements().get(0).getState());
    }

    @Test
    public void aggregationStatementQuery_functionOnlyWithOpeningBracketAndComma() {
        SearchResult result = match("count(sales), ");

        assertEquals(1, result.asAggregationStatementsResult().get().getStatements().size());
        assertEquals(AggregationStatementsResult.State.EXPRESSION, result.asAggregationStatementsResult().get().getState());

        assertEquals("sales", result.asAggregationStatementsResult().get().getStatements().get(0).getFeature());
        assertEquals("count", result.asAggregationStatementsResult().get().getStatements().get(0).getFunction());
        assertEquals(AggregationStatementResult.State.COMPLETED, result.asAggregationStatementsResult().get().getStatements().get(0).getState());
    }

    @Test
    public void aggregationStatementQuery_functionTwoStatements() {
        SearchResult result = match("count(sales), max");
        assertEquals(2, result.asAggregationStatementsResult().get().getStatements().size());
        assertEquals(AggregationStatementsResult.State.EXPRESSION, result.asAggregationStatementsResult().get().getState());

        assertEquals("sales", result.asAggregationStatementsResult().get().getStatements().get(0).getFeature());
        assertEquals("count", result.asAggregationStatementsResult().get().getStatements().get(0).getFunction());
        assertEquals(AggregationStatementResult.State.COMPLETED, result.asAggregationStatementsResult().get().getStatements().get(0).getState());

        assertNull(result.asAggregationStatementsResult().get().getStatements().get(1).getFeature());
        assertEquals("max", result.asAggregationStatementsResult().get().getStatements().get(1).getFunction());
        assertEquals(AggregationStatementResult.State.FUNCTION, result.asAggregationStatementsResult().get().getStatements().get(1).getState());
    }

    @Test
    public void aggregationStatementQuery_functionOnlyWithOpeningBracketAndTwoStatements() {
        SearchResult result = match("count(sales), max(");
        assertEquals(2, result.asAggregationStatementsResult().get().getStatements().size());

        assertEquals("sales", result.asAggregationStatementsResult().get().getStatements().get(0).getFeature());
        assertEquals("count", result.asAggregationStatementsResult().get().getStatements().get(0).getFunction());

        assertNull(result.asAggregationStatementsResult().get().getStatements().get(1).getFeature());
        assertEquals("max", result.asAggregationStatementsResult().get().getStatements().get(1).getFunction());
    }

    private SearchResult match(String statement) {
        return compiler.compile(new SearchQuery(statement));
    }
}