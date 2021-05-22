package com.flair.bi.compiler.search;

import java.util.List;
import java.util.Optional;

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

    public Optional<AggregationStatementResult> lastStatement() {
        if (statements.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(statements.get(statements.size() - 1));
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
