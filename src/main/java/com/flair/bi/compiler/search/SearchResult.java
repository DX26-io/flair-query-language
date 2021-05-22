package com.flair.bi.compiler.search;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

    private AggregationStatementsResult aggregationStatementsResult;
    private ByStatementResult byStatementResult;
    private WhereStatementResult whereStatementResult;
    private OrderByStatementResult orderByStatementResult;
    private final List<IStatementResult> results = new ArrayList<>();

    public AggregationStatementsResult getAggregationStatementsResult() {
        return aggregationStatementsResult;
    }

    public void setAggregationStatementsResult(AggregationStatementsResult aggregationStatementsResult) {
        this.aggregationStatementsResult = aggregationStatementsResult;
    }

    public ByStatementResult getByStatementResult() {
        return byStatementResult;
    }

    public void setByStatementResult(ByStatementResult byStatementResult) {
        this.byStatementResult = byStatementResult;
    }

    public WhereStatementResult getWhereStatementResult() {
        return whereStatementResult;
    }

    public void setWhereStatementResult(WhereStatementResult whereStatementResult) {
        this.whereStatementResult = whereStatementResult;
    }

    public OrderByStatementResult getOrderByStatementResult() {
        return orderByStatementResult;
    }

    public void setOrderByStatementResult(OrderByStatementResult orderByStatementResult) {
        this.orderByStatementResult = orderByStatementResult;
    }

    public List<IStatementResult> getResults() {
        return results;
    }

    public void addResult(IStatementResult result) {
        results.add(result);
    }
}
