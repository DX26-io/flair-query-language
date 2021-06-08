package com.flair.bi.compiler.search;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SearchQLCompilerWhereStatementTest {

    private SearchQLCompiler compiler;

    @Before
    public void setUp() {
        compiler = new SearchQLCompiler();
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA', 'CAN'), quantity_purchased >= 10000");

        assertEquals(2, result.asWhereStatementResult().get().getConditions().size());
        assertEquals(WhereStatementResult.State.COMPLETED, result.asWhereStatementResult().get().getState());

        assertEquals("'USA'", result.asWhereStatementResult().get().getConditions().get(0).getStatements().get(0));
        assertEquals("'CAN'", result.asWhereStatementResult().get().getConditions().get(0).getStatements().get(1));
        assertEquals("country", result.asWhereStatementResult().get().getConditions().get(0).getFeature());
        assertEquals(WhereConditionResult.State.COMPLETED, result.asWhereStatementResult().get().getConditions().get(0).getState());

        assertEquals("quantity_purchased", result.asWhereStatementResult().get().getConditions().get(1).getFeature());
        assertEquals(">=", result.asWhereStatementResult().get().getConditions().get(1).getCondition());
        assertEquals("10000", result.asWhereStatementResult().get().getConditions().get(1).getStatement());
        assertEquals(WhereConditionResult.State.COMPLETED, result.asWhereStatementResult().get().getConditions().get(1).getState());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyBy() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by");

        assertEquals(0, result.asWhereStatementResult().get().getConditions().size());
        assertEquals(WhereStatementResult.State.EXPRESSION, result.asWhereStatementResult().get().getState());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeature() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country");

        assertEquals(1, result.asWhereStatementResult().get().getConditions().size());
        assertEquals(WhereStatementResult.State.EXPRESSION, result.asWhereStatementResult().get().getState());

        assertEquals("country", result.asWhereStatementResult().get().getConditions().get(0).getFeature());
        assertNull(result.asWhereStatementResult().get().getConditions().get(0).getCondition());
        assertEquals(0, result.asWhereStatementResult().get().getConditions().get(0).getStatements().size());
        assertEquals(WhereConditionResult.State.FEATURE, result.asWhereStatementResult().get().getConditions().get(0).getState());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeatureBrace() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country(");

        assertEquals(1, result.asWhereStatementResult().get().getConditions().size());
        assertEquals(WhereStatementResult.State.EXPRESSION, result.asWhereStatementResult().get().getState());

        assertEquals("country", result.asWhereStatementResult().get().getConditions().get(0).getFeature());
        assertNull(result.asWhereStatementResult().get().getConditions().get(0).getCondition());
        assertEquals(0, result.asWhereStatementResult().get().getConditions().get(0).getStatements().size());
        assertEquals(WhereConditionResult.State.STATEMENT, result.asWhereStatementResult().get().getConditions().get(0).getState());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeatureBraceConditionInNoCommaInBrackets() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA'");

        assertEquals(1, result.asWhereStatementResult().get().getConditions().size());
        assertEquals(WhereStatementResult.State.EXPRESSION, result.asWhereStatementResult().get().getState());

        assertEquals("country", result.asWhereStatementResult().get().getConditions().get(0).getFeature());
        assertEquals("'USA'", result.asWhereStatementResult().get().getConditions().get(0).getStatements().get(0));
        assertNull(result.asWhereStatementResult().get().getConditions().get(0).getCondition());
        assertEquals(WhereConditionResult.State.STATEMENT, result.asWhereStatementResult().get().getConditions().get(0).getState());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeatureBraceConditionIn() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA',");

        assertEquals(1, result.asWhereStatementResult().get().getConditions().size());
        assertEquals(WhereStatementResult.State.EXPRESSION, result.asWhereStatementResult().get().getState());

        assertEquals("country", result.asWhereStatementResult().get().getConditions().get(0).getFeature());
        assertEquals("'USA'", result.asWhereStatementResult().get().getConditions().get(0).getStatements().get(0));
        assertNull(result.asWhereStatementResult().get().getConditions().get(0).getCondition());
        assertEquals(WhereConditionResult.State.STATEMENT, result.asWhereStatementResult().get().getConditions().get(0).getState());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeatureBraceConditionInNoComma() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA')");

        assertEquals(1, result.asWhereStatementResult().get().getConditions().size());
        assertEquals(WhereStatementResult.State.COMPLETED, result.asWhereStatementResult().get().getState());

        assertEquals("country", result.asWhereStatementResult().get().getConditions().get(0).getFeature());
        assertEquals("'USA'", result.asWhereStatementResult().get().getConditions().get(0).getStatements().get(0));
        assertNull(result.asWhereStatementResult().get().getConditions().get(0).getCondition());
        assertEquals(WhereConditionResult.State.COMPLETED, result.asWhereStatementResult().get().getConditions().get(0).getState());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeatureBraceConditionInNoCommaMultiple() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA', 'Hawaii')");

        assertEquals(1, result.asWhereStatementResult().get().getConditions().size());
        assertEquals(WhereStatementResult.State.COMPLETED, result.asWhereStatementResult().get().getState());

        assertEquals("country", result.asWhereStatementResult().get().getConditions().get(0).getFeature());
        assertEquals("'USA'", result.asWhereStatementResult().get().getConditions().get(0).getStatements().get(0));
        assertEquals("'Hawaii'", result.asWhereStatementResult().get().getConditions().get(0).getStatements().get(1));
        assertNull(result.asWhereStatementResult().get().getConditions().get(0).getCondition());
        assertEquals(WhereConditionResult.State.COMPLETED, result.asWhereStatementResult().get().getConditions().get(0).getState());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeatureBraceConditionInAndComma() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA'),");

        assertEquals(1, result.asWhereStatementResult().get().getConditions().size());
        assertEquals(WhereStatementResult.State.EXPRESSION, result.asWhereStatementResult().get().getState());

        assertEquals("country", result.asWhereStatementResult().get().getConditions().get(0).getFeature());
        assertEquals("'USA'", result.asWhereStatementResult().get().getConditions().get(0).getStatements().get(0));
        assertNull(result.asWhereStatementResult().get().getConditions().get(0).getCondition());
        assertEquals(WhereConditionResult.State.COMPLETED, result.asWhereStatementResult().get().getConditions().get(0).getState());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeatureBraceConditionInAndConditionCompare() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA'), quantity");

        assertEquals(2, result.asWhereStatementResult().get().getConditions().size());
        assertEquals(WhereStatementResult.State.EXPRESSION, result.asWhereStatementResult().get().getState());

        assertEquals("country", result.asWhereStatementResult().get().getConditions().get(0).getFeature());
        assertEquals("'USA'", result.asWhereStatementResult().get().getConditions().get(0).getStatements().get(0));
        assertNull(result.asWhereStatementResult().get().getConditions().get(0).getCondition());
        assertEquals(WhereConditionResult.State.COMPLETED, result.asWhereStatementResult().get().getConditions().get(0).getState());

        assertEquals("quantity", result.asWhereStatementResult().get().getConditions().get(1).getFeature());
        assertNull(result.asWhereStatementResult().get().getConditions().get(1).getStatement());
        assertNull(result.asWhereStatementResult().get().getConditions().get(1).getCondition());
        assertEquals(WhereConditionResult.State.FEATURE, result.asWhereStatementResult().get().getConditions().get(1).getState());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeatureBraceConditionInAndConditionCompareCondition() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA'), quantity >=");

        assertEquals(2, result.asWhereStatementResult().get().getConditions().size());
        assertEquals(WhereStatementResult.State.EXPRESSION, result.asWhereStatementResult().get().getState());

        assertEquals("country", result.asWhereStatementResult().get().getConditions().get(0).getFeature());
        assertEquals("'USA'", result.asWhereStatementResult().get().getConditions().get(0).getStatements().get(0));
        assertNull(result.asWhereStatementResult().get().getConditions().get(0).getCondition());
        assertEquals(WhereConditionResult.State.COMPLETED, result.asWhereStatementResult().get().getConditions().get(0).getState());

        assertEquals("quantity", result.asWhereStatementResult().get().getConditions().get(1).getFeature());
        assertNull(result.asWhereStatementResult().get().getConditions().get(1).getStatement());
        assertEquals(">=", result.asWhereStatementResult().get().getConditions().get(1).getCondition());
        assertEquals(WhereConditionResult.State.CONDITION, result.asWhereStatementResult().get().getConditions().get(1).getState());
    }

    @Test
    public void aggregationStatementAndByStatementAndWhereStatement_onlyByFeatureBraceConditionInAndConditionCompareConditionAndSpace() {
        SearchResult result = match("count(sales), max(revenue) by state, street filter by country('USA'), quantity >= ");

        assertEquals(2, result.asWhereStatementResult().get().getConditions().size());
        assertEquals(WhereStatementResult.State.EXPRESSION, result.asWhereStatementResult().get().getState());

        assertEquals("country", result.asWhereStatementResult().get().getConditions().get(0).getFeature());
        assertEquals("'USA'", result.asWhereStatementResult().get().getConditions().get(0).getStatements().get(0));
        assertNull(result.asWhereStatementResult().get().getConditions().get(0).getCondition());
        assertEquals(WhereConditionResult.State.COMPLETED, result.asWhereStatementResult().get().getConditions().get(0).getState());

        assertEquals("quantity", result.asWhereStatementResult().get().getConditions().get(1).getFeature());
        assertNull(result.asWhereStatementResult().get().getConditions().get(1).getStatement());
        assertEquals(">=", result.asWhereStatementResult().get().getConditions().get(1).getCondition());
        assertEquals(WhereConditionResult.State.STATEMENT, result.asWhereStatementResult().get().getConditions().get(1).getState());
    }

    private SearchResult match(String statement) {
        return compiler.compile(new SearchQuery(statement));
    }
}