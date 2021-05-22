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

        assertEquals(2, result.getAggregationStatementsResult().getStatements().size());
        assertEquals(AggregationStatementsResult.State.COMPLETED, result.getAggregationStatementsResult().getState());

        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());
        assertEquals(AggregationStatementResult.State.COMPLETED, result.getAggregationStatementsResult().getStatements().get(0).getState());

        assertEquals("revenue", result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());
        assertEquals(AggregationStatementResult.State.COMPLETED, result.getAggregationStatementsResult().getStatements().get(1).getState());

    }

    @Test
    public void aggregationStatementQuery_functionOnly() {
        SearchResult result = match("count");

        assertEquals(1, result.getAggregationStatementsResult().getStatements().size());
        assertEquals(AggregationStatementsResult.State.EXPRESSION, result.getAggregationStatementsResult().getState());

        assertNull(result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());
        assertEquals(AggregationStatementResult.State.FUNCTION, result.getAggregationStatementsResult().getStatements().get(0).getState());
    }

    @Test
    public void aggregationStatementQuery_functionOnlyWithOpeningBracket() {
        SearchResult result = match("count(");

        assertEquals(1, result.getAggregationStatementsResult().getStatements().size());
        assertEquals(AggregationStatementsResult.State.EXPRESSION, result.getAggregationStatementsResult().getState());

        assertNull(result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());
        assertEquals(AggregationStatementResult.State.FEATURE, result.getAggregationStatementsResult().getStatements().get(0).getState());
    }

    @Test
    public void aggregationStatementQuery_functionOnlyWithoutClosingBracket() {
        SearchResult result = match("count(sales");

        assertEquals(1, result.getAggregationStatementsResult().getStatements().size());
        assertEquals(AggregationStatementsResult.State.EXPRESSION, result.getAggregationStatementsResult().getState());

        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());
        assertEquals(AggregationStatementResult.State.FEATURE, result.getAggregationStatementsResult().getStatements().get(0).getState());
    }

    @Test
    public void aggregationStatementQuery_single() {
        SearchResult result = match("count(sales)");

        assertEquals(1, result.getAggregationStatementsResult().getStatements().size());
        assertEquals(AggregationStatementsResult.State.COMPLETED, result.getAggregationStatementsResult().getState());

        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());
        assertEquals(AggregationStatementResult.State.COMPLETED, result.getAggregationStatementsResult().getStatements().get(0).getState());
    }

    @Test
    public void aggregationStatementQuery_functionOnlyWithOpeningBracketAndComma() {
        SearchResult result = match("count(sales), ");

        assertEquals(1, result.getAggregationStatementsResult().getStatements().size());
        assertEquals(AggregationStatementsResult.State.EXPRESSION, result.getAggregationStatementsResult().getState());

        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());
        assertEquals(AggregationStatementResult.State.COMPLETED, result.getAggregationStatementsResult().getStatements().get(0).getState());
    }

    @Test
    public void aggregationStatementQuery_functionTwoStatements() {
        SearchResult result = match("count(sales), max");
        assertEquals(2, result.getAggregationStatementsResult().getStatements().size());
        assertEquals(AggregationStatementsResult.State.EXPRESSION, result.getAggregationStatementsResult().getState());

        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());
        assertEquals(AggregationStatementResult.State.COMPLETED, result.getAggregationStatementsResult().getStatements().get(0).getState());

        assertNull(result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());
        assertEquals(AggregationStatementResult.State.FUNCTION, result.getAggregationStatementsResult().getStatements().get(1).getState());
    }

    @Test
    public void aggregationStatementQuery_functionOnlyWithOpeningBracketAndTwoStatements() {
        SearchResult result = match("count(sales), max(");
        assertEquals(2, result.getAggregationStatementsResult().getStatements().size());

        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());

        assertNull(result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());
    }

    private SearchResult match(String statement) {
        return compiler.compile(new SearchQuery(statement));
    }
}