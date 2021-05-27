package com.flair.bi.compiler.search;

import java.util.List;
import java.util.Optional;

public class WhereStatementResult implements IStatementResult {
    private final List<WhereConditionResult> conditions;
    private final State state;

    public WhereStatementResult(List<WhereConditionResult> conditions, State state) {
        this.conditions = conditions;
        this.state = state;
    }

    public List<WhereConditionResult> getConditions() {
        return conditions;
    }

    public Optional<WhereConditionResult> lastStatement() {
        if (conditions.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(conditions.get(conditions.size() - 1));
    }

    @Override
    public String toString() {
        return "{" +
                "conditions=" + conditions +
                '}';
    }

    public State getState() {
        return state;
    }

    public enum State {
        EXPRESSION, COMPLETED;
    }
}
