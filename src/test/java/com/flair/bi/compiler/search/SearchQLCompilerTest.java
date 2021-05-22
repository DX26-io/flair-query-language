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
    public void fullSearchQuery_orderByOnly() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA', 'CAN'), quantity_purchased >= 10000 order by");
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

        assertNull(result.getOrderByStatementResult().getFeature());
        assertNull(result.getOrderByStatementResult().getDirection());
    }

    @Test
    public void fullSearchQuery_orderByColumnOnly() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA', 'CAN'), quantity_purchased >= 10000 order by column");
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

        assertEquals("column", result.getOrderByStatementResult().getFeature());
        assertNull(result.getOrderByStatementResult().getDirection());
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
    public void aggregationStatementAndByStatementAndWhereStatement_onlyBy() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by");
        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());

        assertEquals("revenue", result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());

        assertEquals("state", result.getByStatementResult().getFeature().get(0));
        assertEquals("street", result.getByStatementResult().getFeature().get(1));

        assertEquals(0, result.getWhereStatementResult().getConditions().size());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeature() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country");
        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());

        assertEquals("revenue", result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());

        assertEquals("state", result.getByStatementResult().getFeature().get(0));
        assertEquals("street", result.getByStatementResult().getFeature().get(1));

        assertEquals(1, result.getWhereStatementResult().getConditions().size());
        assertEquals("country", result.getWhereStatementResult().getConditions().get(0).getFeature());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeatureBrace() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country(");
        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());

        assertEquals("revenue", result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());

        assertEquals("state", result.getByStatementResult().getFeature().get(0));
        assertEquals("street", result.getByStatementResult().getFeature().get(1));

        assertEquals(1, result.getWhereStatementResult().getConditions().size());
        assertEquals("country", result.getWhereStatementResult().getConditions().get(0).getFeature());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeatureBraceConditionIn() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA',");
        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());

        assertEquals("revenue", result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());

        assertEquals("state", result.getByStatementResult().getFeature().get(0));
        assertEquals("street", result.getByStatementResult().getFeature().get(1));

        assertEquals(1, result.getWhereStatementResult().getConditions().size());
        assertEquals("country", result.getWhereStatementResult().getConditions().get(0).getFeature());
        assertEquals("'USA'", result.getWhereStatementResult().getConditions().get(0).getStatement());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeatureBraceConditionInAndComma() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA'),");
        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());

        assertEquals("revenue", result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());

        assertEquals("state", result.getByStatementResult().getFeature().get(0));
        assertEquals("street", result.getByStatementResult().getFeature().get(1));

        assertEquals(1, result.getWhereStatementResult().getConditions().size());
        assertEquals("country", result.getWhereStatementResult().getConditions().get(0).getFeature());
        assertEquals("'USA'", result.getWhereStatementResult().getConditions().get(0).getStatement());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeatureBraceConditionInAndConditionCompare() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA'), quantity");
        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());

        assertEquals("revenue", result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());

        assertEquals("state", result.getByStatementResult().getFeature().get(0));
        assertEquals("street", result.getByStatementResult().getFeature().get(1));

        assertEquals(2, result.getWhereStatementResult().getConditions().size());
        assertEquals("country", result.getWhereStatementResult().getConditions().get(0).getFeature());
        assertEquals("'USA'", result.getWhereStatementResult().getConditions().get(0).getStatement());

        assertEquals("quantity", result.getWhereStatementResult().getConditions().get(1).getFeature());
        assertNull(result.getWhereStatementResult().getConditions().get(1).getStatement());
        assertNull(result.getWhereStatementResult().getConditions().get(1).getCondition());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeatureBraceConditionInAndConditionCompareCondition() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA'), quantity >=");
        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());

        assertEquals("revenue", result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());

        assertEquals("state", result.getByStatementResult().getFeature().get(0));
        assertEquals("street", result.getByStatementResult().getFeature().get(1));

        assertEquals(2, result.getWhereStatementResult().getConditions().size());
        assertEquals("country", result.getWhereStatementResult().getConditions().get(0).getFeature());
        assertEquals("'USA'", result.getWhereStatementResult().getConditions().get(0).getStatement());

        assertEquals("quantity", result.getWhereStatementResult().getConditions().get(1).getFeature());
        assertNull(result.getWhereStatementResult().getConditions().get(1).getStatement());
        assertEquals(">=", result.getWhereStatementResult().getConditions().get(1).getCondition());
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
    public void aggregationStatementAndByStatement_byOnly() {
        SearchResult result = match("count(sales), max(revenue) by");
        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());

        assertEquals("revenue", result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());

        assertEquals(0, result.getByStatementResult().getFeature().size());
    }

    @Test
    public void aggregationStatementAndByStatement_byComma() {
        SearchResult result = match("count(sales), max(revenue) by state, country,");
        assertEquals("sales", result.getAggregationStatementsResult().getStatements().get(0).getFeature());
        assertEquals("count", result.getAggregationStatementsResult().getStatements().get(0).getFunction());

        assertEquals("revenue", result.getAggregationStatementsResult().getStatements().get(1).getFeature());
        assertEquals("max", result.getAggregationStatementsResult().getStatements().get(1).getFunction());

        assertEquals(2, result.getByStatementResult().getFeature().size());
        assertEquals("state", result.getByStatementResult().getFeature().get(0));
        assertEquals("country", result.getByStatementResult().getFeature().get(1));
    }

    private SearchResult match(String statement) {
        return compiler.compile(new SearchQuery(statement));
    }
}