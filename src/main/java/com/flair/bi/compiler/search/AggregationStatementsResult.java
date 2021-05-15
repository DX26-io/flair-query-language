package com.flair.bi.compiler.search;

import java.util.List;

public class AggregationStatementsResult {
    private final List<AggregationStatementResult> statements;

    public AggregationStatementsResult(List<AggregationStatementResult> statements) {
        this.statements = statements;
    }

    public List<AggregationStatementResult> getStatements() {
        return statements;
    }
}
