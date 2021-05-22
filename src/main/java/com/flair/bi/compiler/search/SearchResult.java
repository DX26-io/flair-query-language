package com.flair.bi.compiler.search;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

    private final List<IStatementResult> results = new ArrayList<>();

    public List<IStatementResult> getResults() {
        return results;
    }

    public void addResult(IStatementResult result) {
        results.add(result);
    }

    public AggregationStatementsResult asAggregationStatementsResult() {
        if (results.size() >= 1) {
            return (AggregationStatementsResult) results.get(0);
        }
        return null;
    }

    public ByStatementResult asByStatementResult() {
        if (results.size() >= 2) {
            return (ByStatementResult) results.get(1);
        }
        return null;
    }

    public WhereStatementResult asWhereStatementResult() {
        if (results.size() >= 3) {
            return (WhereStatementResult) results.get(2);
        }
        return null;
    }

    public OrderByStatementResult asOrderByStatementResult() {
        if (results.size() >= 4) {
            return (OrderByStatementResult) results.get(3);
        }
        return null;
    }

}
