package com.flair.bi.compiler.search;

import java.util.List;

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
