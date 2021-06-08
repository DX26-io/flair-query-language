package com.flair.bi.compiler.search;

import java.util.ArrayList;
import java.util.List;

public class WhereConditionResult {
    private final String feature;
    private final String condition;
    private final String statement;
    private final List<String> statements;
    private final State state;

    public WhereConditionResult(String feature, List<String> statements, State state) {
        this.feature = feature;
        this.condition = null;
        this.statements = statements;
        this.statement = null;
        this.state = state;
    }

    public WhereConditionResult(String feature, String condition, String statement, State state) {
        this.feature = feature;
        this.condition = condition;
        this.statement = statement;
        this.state = state;
        this.statements = new ArrayList<>();
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

    public List<String> getStatements() {
        return statements;
    }

    public enum State {
        FEATURE, STATEMENT, CONDITION, COMPLETED;
    }
}
