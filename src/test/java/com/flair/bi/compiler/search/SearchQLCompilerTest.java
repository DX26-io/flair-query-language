package com.flair.bi.compiler.search;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SearchQLCompilerTest {

    private SearchQLCompiler compiler;

    @Before
    public void setUp() {
        compiler = new SearchQLCompiler();
    }

    @Test
    public void fullSearchQuery() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA', 'CAN'), quantity_purchased >= 10000 order by some_feature desc");
        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());

        assertEquals("revenue", result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());

        assertEquals("state", result.getByStatementResult().getFeature().get(0));
        assertEquals("street", result.getByStatementResult().getFeature().get(1));

        assertEquals("'USA','CAN'", result.getWhereStatementResult().getConditions().get(0).getStatement());
        assertEquals("country", result.getWhereStatementResult().getConditions().get(0).getFeature());

        assertEquals("quantity_purchased", result.getWhereStatementResult().getConditions().get(1).getFeature());
        assertEquals(">=", result.getWhereStatementResult().getConditions().get(1).getCondition());
        assertEquals("10000", result.getWhereStatementResult().getConditions().get(1).getStatement());

        assertEquals("some_feature", result.getOrderByStatementResult().getFeature());
        assertEquals("desc", result.getOrderByStatementResult().getDirection());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA', 'CAN'), quantity_purchased >= 10000");
        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());

        assertEquals("revenue", result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());

        assertEquals("state", result.getByStatementResult().getFeature().get(0));
        assertEquals("street", result.getByStatementResult().getFeature().get(1));

        assertEquals("'USA','CAN'", result.getWhereStatementResult().getConditions().get(0).getStatement());
        assertEquals("country", result.getWhereStatementResult().getConditions().get(0).getFeature());

        assertEquals("quantity_purchased", result.getWhereStatementResult().getConditions().get(1).getFeature());
        assertEquals(">=", result.getWhereStatementResult().getConditions().get(1).getCondition());
        assertEquals("10000", result.getWhereStatementResult().getConditions().get(1).getStatement());
    }

    @Test
    public void aggregationStatementAndByStatement() {
        SearchResult result = match("count(sales), max(revenue) by state, street");
        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());

        assertEquals("revenue", result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());

        assertEquals("state", result.getByStatementResult().getFeature().get(0));
        assertEquals("street", result.getByStatementResult().getFeature().get(1));
    }

    @Test
    public void aggregationStatementQuery() {
        SearchResult result = match("count(sales), max(revenue)");
        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());

        assertEquals("revenue", result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());
    }

    private SearchResult match(String statement) {
        return compiler.compile(new SearchQuery(statement));
    }
}