package com.flair.bi.compiler.search;

public class WhereConditionResult {
    private final String feature;
    private final String condition;
    private final String statement;
    private final State state;

    public WhereConditionResult(String feature, String condition, String statement, State state) {
        this.feature = feature;
        this.condition = condition;
        this.statement = statement;
        this.state = state;
    }

    public String getFeature() {
        return feature;
    }

    public String getStatement() {
        return statement;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return "{" +
                "feature='" + feature + '\'' +
                ", condition='" + condition + '\'' +
                ", statement='" + statement + '\'' +
                '}';
    }

    public State getState() {
        return state;
    }

    public enum State {
        FEATURE, STATEMENT, CONDITION, COMPLETED;
    }
}
