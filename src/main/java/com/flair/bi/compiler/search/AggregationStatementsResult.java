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

    public AggregationStatementResult lastStatement() {
        if (statements.isEmpty()) {
            return null;
        }
        return statements.get(statements.size() - 1);
    }

    public State getState() {
        return state;
    }

    public enum State {
        EXPRESSION, COMPLETED
    }

    @Override
    public String toString() {
        return "{" +
                "statements=" + statements +
                ", state=" + state +
                '}';
    }
}
