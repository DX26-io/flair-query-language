package com.flair.bi.compiler.search;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SearchQLCompilerTest {

    private SearchQLCompiler compiler;

    @Before
    public void setUp() {
        compiler = new SearchQLCompiler();
    }

    @Test
    public void fullSearchQuery() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA', 'CAN'), quantity_purchased >= 10000 order by some_feature desc");
        assertEquals("sales", result.asAggregationStatementsResult().get().getStatements().get(0).getFeature());
        assertEquals("count", result.asAggregationStatementsResult().get().getStatements().get(0).getFunction());

        assertEquals("revenue", result.asAggregationStatementsResult().get().getStatements().get(1).getFeature());
        assertEquals("max", result.asAggregationStatementsResult().get().getStatements().get(1).getFunction());

        assertEquals("state", result.asByStatementResult().get().getFeature().get(0));
        assertEquals("street", result.asByStatementResult().get().getFeature().get(1));

        assertEquals("'USA','CAN'", result.asWhereStatementResult().get().getConditions().get(0).getStatement());
        assertEquals("country", result.asWhereStatementResult().get().getConditions().get(0).getFeature());

        assertEquals("quantity_purchased", result.asWhereStatementResult().get().getConditions().get(1).getFeature());
        assertEquals(">=", result.asWhereStatementResult().get().getConditions().get(1).getCondition());
        assertEquals("10000", result.asWhereStatementResult().get().getConditions().get(1).getStatement());

        assertEquals("some_feature", result.asOrderByStatementResult().get().getFeature());
        assertEquals("desc", result.asOrderByStatementResult().get().getDirection());
    }

    @Test
    public void fullSearchQuery_orderByOnly() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA', 'CAN'), quantity_purchased >= 10000 order by");
        assertEquals("sales", result.asAggregationStatementsResult().get().getStatements().get(0).getFeature());
        assertEquals("count", result.asAggregationStatementsResult().get().getStatements().get(0).getFunction());

        assertEquals("revenue", result.asAggregationStatementsResult().get().getStatements().get(1).getFeature());
        assertEquals("max", result.asAggregationStatementsResult().get().getStatements().get(1).getFunction());

        assertEquals("state", result.asByStatementResult().get().getFeature().get(0));
        assertEquals("street", result.asByStatementResult().get().getFeature().get(1));

        assertEquals("'USA','CAN'", result.asWhereStatementResult().get().getConditions().get(0).getStatement());
        assertEquals("country", result.asWhereStatementResult().get().getConditions().get(0).getFeature());

        assertEquals("quantity_purchased", result.asWhereStatementResult().get().getConditions().get(1).getFeature());
        assertEquals(">=", result.asWhereStatementResult().get().getConditions().get(1).getCondition());
        assertEquals("10000", result.asWhereStatementResult().get().getConditions().get(1).getStatement());

        assertNull(result.asOrderByStatementResult().get().getFeature());
        assertNull(result.asOrderByStatementResult().get().getDirection());
    }

    @Test
    public void fullSearchQuery_orderByColumnOnly() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA', 'CAN'), quantity_purchased >= 10000 order by column");
        assertEquals("sales", result.asAggregationStatementsResult().get().getStatements().get(0).getFeature());
        assertEquals("count", result.asAggregationStatementsResult().get().getStatements().get(0).getFunction());

        assertEquals("revenue", result.asAggregationStatementsResult().get().getStatements().get(1).getFeature());
        assertEquals("max", result.asAggregationStatementsResult().get().getStatements().get(1).getFunction());

        assertEquals("state", result.asByStatementResult().get().getFeature().get(0));
        assertEquals("street", result.asByStatementResult().get().getFeature().get(1));

        assertEquals("'USA','CAN'", result.asWhereStatementResult().get().getConditions().get(0).getStatement());
        assertEquals("country", result.asWhereStatementResult().get().getConditions().get(0).getFeature());

        assertEquals("quantity_purchased", result.asWhereStatementResult().get().getConditions().get(1).getFeature());
        assertEquals(">=", result.asWhereStatementResult().get().getConditions().get(1).getCondition());
        assertEquals("10000", result.asWhereStatementResult().get().getConditions().get(1).getStatement());

        assertEquals("column", result.asOrderByStatementResult().get().getFeature());
        assertNull(result.asOrderByStatementResult().get().getDirection());
    }

    @Test
    public void aggregationStatementAndByStatement() {
        SearchResult result = match("count(sales), max(revenue) by state, street");
        assertEquals("sales", result.asAggregationStatementsResult().get().getStatements().get(0).getFeature());
        assertEquals("count", result.asAggregationStatementsResult().get().getStatements().get(0).getFunction());

        assertEquals("revenue", result.asAggregationStatementsResult().get().getStatements().get(1).getFeature());
        assertEquals("max", result.asAggregationStatementsResult().get().getStatements().get(1).getFunction());

        assertEquals("state", result.asByStatementResult().get().getFeature().get(0));
        assertEquals("street", result.asByStatementResult().get().getFeature().get(1));
    }

    @Test
    public void aggregationStatementAndByStatement_byOnly() {
        SearchResult result = match("count(sales), max(revenue) by");
        assertEquals("sales", result.asAggregationStatementsResult().get().getStatements().get(0).getFeature());
        assertEquals("count", result.asAggregationStatementsResult().get().getStatements().get(0).getFunction());

        assertEquals("revenue", result.asAggregationStatementsResult().get().getStatements().get(1).getFeature());
        assertEquals("max", result.asAggregationStatementsResult().get().getStatements().get(1).getFunction());

        assertEquals(0, result.asByStatementResult().get().getFeature().size());
    }

    @Test
    public void aggregationStatementAndByStatement_byComma() {
        SearchResult result = match("count(sales), max(revenue) by state, country,");
        assertEquals("sales", result.asAggregationStatementsResult().get().getStatements().get(0).getFeature());
        assertEquals("count", result.asAggregationStatementsResult().get().getStatements().get(0).getFunction());

        assertEquals("revenue", result.asAggregationStatementsResult().get().getStatements().get(1).getFeature());
        assertEquals("max", result.asAggregationStatementsResult().get().getStatements().get(1).getFunction());

        assertEquals(2, result.asByStatementResult().get().getFeature().size());
        assertEquals("state", result.asByStatementResult().get().getFeature().get(0));
        assertEquals("country", result.asByStatementResult().get().getFeature().get(1));
    }

    private SearchResult match(String statement) {
        return compiler.compile(new SearchQuery(statement));
    }
}