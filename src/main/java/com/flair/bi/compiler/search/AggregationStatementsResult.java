package com.flair.bi.compiler.search;

import java.util.List;

public class AggregationStatementsResult implements IStatementResult {
    private final List<AggregationStatementResult> statements;
    private final State state;

    public AggregationStatementsResult(List<AggregationStatementResult> statements, State state) {
        this.statements = statements;
        this.state = state;
    }

    public List<AggregationStatementResult> getStatements() {
        return statements;
    }

    public State getState() {
        return state;
    }

    public enum State {
        EXPRESSION, COMPLETED
    }
}
