package com.flair.bi.compiler.search;

public class AggregationStatementResult {
    private final String function;
    private final String feature;
    private final State state;

    public AggregationStatementResult(String function, String feature, State state) {
        this.function = function;
        this.feature = feature;
        this.state = state;
    }

    public String getFunction() {
        return function;
    }

    public String getFeature() {
        return feature;
    }

    public State getState() {
        return state;
    }

    public enum State {
        FUNCTION, FEATURE, COMPLETED
    }
}
