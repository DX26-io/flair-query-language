package com.flair.bi.compiler.search;

public class WhereConditionResult {
    private final String feature;
    private final String condition;
    private final String statement;

    public WhereConditionResult(String feature, String condition, String statement) {
        this.feature = feature;
        this.condition = condition;
        this.statement = statement;
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
}
