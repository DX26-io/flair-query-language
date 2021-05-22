package com.flair.bi.compiler.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SearchResult {

    private final List<IStatementResult> results = new ArrayList<>();

    public List<IStatementResult> getResults() {
        return results;
    }

    public void addResult(IStatementResult result) {
        results.add(result);
    }

    public Optional<AggregationStatementsResult> asAggregationStatementsResult() {
        if (results.size() >= 1) {
            return Optional.ofNullable((AggregationStatementsResult) results.get(0));
        }
        return Optional.empty();
    }

    public Optional<ByStatementResult> asByStatementResult() {
        if (results.size() >= 2) {
            return Optional.ofNullable((ByStatementResult) results.get(1));
        }
        return Optional.empty();
    }

    public Optional<WhereStatementResult> asWhereStatementResult() {
        if (results.size() >= 3) {
            return Optional.ofNullable((WhereStatementResult) results.get(2));
        }
        return Optional.empty();
    }

    public Optional<OrderByStatementResult> asOrderByStatementResult() {
        if (results.size() >= 4) {
            return Optional.ofNullable((OrderByStatementResult) results.get(3));
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "{" +
                "results=" + results +
                '}';
    }
}
