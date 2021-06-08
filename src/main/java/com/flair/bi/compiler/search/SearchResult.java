package com.flair.bi.compiler.search;

import java.util.Optional;

public class SearchResult {

    private AggregationStatementsResult aggregationStatementsResult;
    private ByStatementResult byStatementResult;
    private WhereStatementResult whereStatementResult;
    private OrderByStatementResult orderByStatementResult;

    public void setAggregationStatementsResult(AggregationStatementsResult aggregationStatementsResult) {
        this.aggregationStatementsResult = aggregationStatementsResult;
    }

    public void setByStatementResult(ByStatementResult byStatementResult) {
        this.byStatementResult = byStatementResult;
    }

    public void setWhereStatementResult(WhereStatementResult whereStatementResult) {
        this.whereStatementResult = whereStatementResult;
    }

    public void setOrderByStatementResult(OrderByStatementResult orderByStatementResult) {
        this.orderByStatementResult = orderByStatementResult;
    }

    public Optional<AggregationStatementsResult> asAggregationStatementsResult() {
        return Optional.ofNullable(aggregationStatementsResult);
    }

    public Optional<ByStatementResult> asByStatementResult() {
        return Optional.ofNullable(byStatementResult);
    }

    public Optional<WhereStatementResult> asWhereStatementResult() {
        return Optional.ofNullable(whereStatementResult);
    }

    public Optional<OrderByStatementResult> asOrderByStatementResult() {
        return Optional.ofNullable(orderByStatementResult);
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "aggregationStatementsResult=" + aggregationStatementsResult +
                ", byStatementResult=" + byStatementResult +
                ", whereStatementResult=" + whereStatementResult +
                ", orderByStatementResult=" + orderByStatementResult +
                '}';
    }
}
